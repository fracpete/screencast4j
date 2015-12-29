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

/**
 * SarxosWebcamRecorder.java
 * Copyright (C) 2015 University of Waikato, Hamilton, NZ
 */

package com.github.fracpete.screencast4j.record;

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
 * @version $Revision$
 */
public class SarxosWebcamRecorder
  extends AbstractWebcamRecorder {

  /** the format used for capturing the video stream. */
  public final static ICodec.ID CAPTURE_FORMAT = ICodec.ID.CODEC_ID_H264;

  /** the output format. */
  protected ID m_Format;

  /** the webcam in use. */
  protected Webcam m_Webcam;

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
    return "ts";
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
	m_Webcam = Webcam.getDefault();
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
    m_Writer.close();
    m_Writer = null;
    // convert into final format
    if (m_Format != CAPTURE_FORMAT) {
      // TODO
    }
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
   * Writes the frame out to disk.
   *
   * @param frame	the frame
   * @throws Exception	if writing fails
   */
  protected void writeFrame(BufferedImage frame) throws Exception {
    if (!isStopped())
      m_Writer.encodeVideo(0, frame, System.currentTimeMillis() - m_StartTime, TimeUnit.MILLISECONDS);
  }

  /**
   * Just for testing.
   *
   * @param args	ignored
   */
  public static void main(String[] args) {
    final SarxosWebcamRecorder rec = new SarxosWebcamRecorder();
    rec.setOutput(new File(System.getProperty("java.io.tmpdir") + File.separator + "webcam.ts"));
    rec.setFramesPerSecond(25);
    rec.setSize(new Dimension(640, 480));
    rec.setUp();
    rec.start();
    new Thread(() -> {
      try {
	for (int i = 0; i < 200; i++)
	  Thread.sleep(100);
	rec.stop();
      }
      catch (Exception e) {
	e.printStackTrace();
      }
    }).start();
  }
}
