/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * SarxosWebcamRecorder.java
 * Copyright (C) 2015 FracPete (fracpete at gmail dot com)
 */

package com.github.fracpete.screencast4j.record.webcam;

import com.github.sarxos.webcam.Webcam;
import com.googlecode.jfilechooserbookmarks.core.Utils;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.ICodec.ID;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Webcam access using Sarxos.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class SarxosWebcamRecorder
  extends AbstractWebcamRecorder {

  /** the format used for capturing the video stream. */
  public final static ICodec.ID CAPTURE_FORMAT = ID.CODEC_ID_H264;

  /** the webcam in use. */
  protected Webcam m_Webcam;

  /** the webcam in use for grabbing images. */
  protected Webcam m_GrabWebcam;

  /** the writer in use. */
  protected IMediaWriter m_Writer;

  /**
   * Returns the type of BufferedImage to create.
   *
   * @return		the type
   */
  @Override
  protected int getBufferedImageType() {
    return BufferedImage.TYPE_3BYTE_BGR;
  }

  /**
   * Returns the default file extension to use.
   *
   * @return		the extension (no dot)
   */
  public String getDefaultExtension() {
    return "mp4";
  }

  /**
   * Tries to obtain the requested webcam.
   *
   * @return		the webcam, null if failed to obtain
   */
  protected Webcam getWebcam() {
    Webcam 	result;

    result = null;

    if (m_WebcamID.isEmpty()) {
      result = Webcam.getDefault();
    }
    else {
      for (Webcam webcam: Webcam.getWebcams()) {
	if (webcam.getName().equals(m_WebcamID)) {
	  result = webcam;
	  break;
	}
      }
    }

    return result;
  }

  /**
   * Performs a check of the setup.
   *
   * @return		null if OK, otherwise error message
   */
  @Override
  public String setUp() {
    String		result;

    result = super.setUp();

    if (result == null) {
      try {
	m_Webcam = getWebcam();
	if (m_Webcam == null)
	  return "No webcam found for ID: " + (m_WebcamID.isEmpty() ? "-default-" : m_WebcamID);
	m_Webcam.setViewSize(m_Size);
	m_Webcam.open();
      }
      catch (Exception e) {
	return "Failed to open webcam: " + Utils.throwableToString(e);
      }
      m_Writer = ToolFactory.makeWriter(m_Output.getAbsolutePath());
      m_Writer.addVideoStream(0, 0, CAPTURE_FORMAT, m_Size.width, m_Size.height);
    }

    return result;
  }

  /**
   * Stops the recording process.
   *
   * @throws Exception	if stopping fails
   */
  @Override
  protected void doStop() throws Exception {
    super.doStop();
    if (m_Webcam != null) {
      if (m_Webcam.isOpen())
	m_Webcam.close();
      m_Webcam = null;
    }
    synchronized(m_Writer) {
      m_Writer.close();
    }
    m_Writer = null;
  }

  /**
   * Performs the actual grabbing of the frame.
   *
   * @throws Exception	true if failed to grab frame
   */
  protected void doGrabFrame() throws Exception {
    BufferedImage	frame;

    frame = convertBufferedImage(m_Webcam.getImage());
    writeFrame(frame);
  }

  /**
   * Performs the actual grabbing of the image.
   *
   * @return 		the image
   * @throws Exception	if failed to grab image
   */
  protected BufferedImage doGrabImage() throws Exception {
    BufferedImage	result;

    if (m_GrabWebcam == null)
      m_GrabWebcam = getWebcam();
    if (m_GrabWebcam == null)
      throw new Exception("Failed to obtain webcam instance!");
    if (!m_GrabWebcam.isOpen() || !m_GrabWebcam.getViewSize().equals(m_Size)) {
      if (m_GrabWebcam.isOpen())
	m_GrabWebcam.close();
      m_GrabWebcam.setViewSize(m_Size);
      m_GrabWebcam.open();
    }
    result = convertBufferedImage(m_GrabWebcam.getImage());

    return result;
  }

  /**
   * Writes the frame out to disk.
   *
   * @param frame	the frame
   * @throws Exception	if writing fails
   */
  protected void writeFrame(BufferedImage frame) throws Exception {
    synchronized(m_Writer) {
      if (!isStopped())
	m_Writer.encodeVideo(0, frame, System.currentTimeMillis() - m_StartTime - m_CumulativePause, TimeUnit.MILLISECONDS);
    }
  }

  /**
   * Performs any left over clean up operations.
   */
  public void cleanUp() {
    super.cleanUp();
    if (m_GrabWebcam != null) {
      if (m_GrabWebcam.isOpen())
	m_GrabWebcam.close();
      m_GrabWebcam = null;
    }
  }

  /**
   * Just for testing.
   *
   * @param args	ignored
   */
  public static void main(String[] args) throws Exception {
    SarxosWebcamRecorder rec = new SarxosWebcamRecorder();
    rec.setOutput(new File(System.getProperty("java.io.tmpdir") + File.separator + "webcam.ts"));
    rec.setFramesPerSecond(25);
    rec.setSize(new Dimension(640, 480));
    rec.setUp();
    rec.start();
    for (int i = 0; i < 200; i++)
      Thread.sleep(100);
    rec.stop();
  }
}
