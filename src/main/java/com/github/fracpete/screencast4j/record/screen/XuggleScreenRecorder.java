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
 * XuggleScreenRecorder.java
 * Copyright (C) 2015 FracPete (fracpete at gmail dot com)
 */

package com.github.fracpete.screencast4j.record.screen;

import com.github.fracpete.screencast4j.record.RecorderState;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.ICodec.ID;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Records the screen.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class XuggleScreenRecorder
  extends AbstractScreenRecorder {

  /** the format used for capturing the video stream. */
  public final static ICodec.ID CAPTURE_FORMAT = ID.CODEC_ID_MPEG2VIDEO;

  /** the output format. */
  protected ID m_Format;

  /** the writer in use. */
  protected IMediaWriter m_Writer;

  /**
   * Initializes the members.
   */
  @Override
  protected void initialize() {
    super.initialize();

    m_Format = ID.CODEC_ID_MPEG4;
  }

  /**
   * Sets the final video format to use.
   *
   * @param value	the format
   */
  public void setFormat(ID value) {
    if (m_State == RecorderState.NONE)
      m_Format = value;
    else
      printError("Cannot set video format once recording has commenced!");
  }

  /**
   * Returns the final video format.
   *
   * @return		the format
   */
  public ID getFormat() {
    return m_Format;
  }

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
    return "mpeg";
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
      m_Writer = ToolFactory.makeWriter(m_Output.getAbsolutePath());
      m_Writer.addVideoStream(0, 0, CAPTURE_FORMAT, m_ScreenPortion.width, m_ScreenPortion.height);
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
    synchronized(m_Writer) {
      m_Writer.close();
    }
    m_Writer = null;
    // convert into final format
    if (m_Format != CAPTURE_FORMAT) {
      // TODO
    }
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
   * Just for testing.
   *
   * @param args	ignored
   */
  public static void main(String[] args) throws Exception {
    XuggleScreenRecorder rec = new XuggleScreenRecorder();
    rec.setOutput(new File(System.getProperty("java.io.tmpdir") + File.separator + "screen.ts"));
    rec.setCaptureMouse(true);
    rec.setFramesPerSecond(25);
    rec.setUp();
    rec.start();
    for (int i = 0; i < 200; i++)
      Thread.sleep(100);
    rec.stop();
  }
}
