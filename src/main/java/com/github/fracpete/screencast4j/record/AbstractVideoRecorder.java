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
 * AbstractVideoRecorder.java
 * Copyright (C) 2015 FracPete (fracpete at gmail dot com)
 */

package com.github.fracpete.screencast4j.record;

import com.googlecode.jfilechooserbookmarks.core.Utils;

import java.awt.image.BufferedImage;

/**
 * Ancestor for video recorders.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public abstract class AbstractVideoRecorder
  extends AbstractFileBasedRecorder
  implements VideoRecorder {

  /** the frames per second to use. */
  protected double m_FramesPerSecond;

  /** the start time. */
  protected long m_StartTime;

  /** the runnable. */
  protected FrameGrabber m_Grabber;

  /**
   * Initializes the members.
   */
  @Override
  protected void initialize() {
    super.initialize();

    m_FramesPerSecond = 25;
    m_Grabber         = null;
  }

  /**
   * Resets the recorder's state (but not parameters).
   */
  @Override
  public void reset() {
    super.reset();

    m_StartTime = 0;
  }

  /**
   * Sets the frames per second to use.
   *
   * @param value	the fps
   */
  public void setFramesPerSecond(double value) {
    m_FramesPerSecond = value;
  }

  /**
   * Returns the frames per second.
   *
   * @return		the fps
   */
  public double getFramesPerSecond() {
    return m_FramesPerSecond;
  }

  /**
   * Starts the actual recording process.
   *
   * @throws Exception	if starting of recording fails
   */
  @Override
  protected void doStart() throws Exception {
    m_StartTime = System.currentTimeMillis();
    m_Grabber   = new FrameGrabber(this);
    new Thread(m_Grabber).start();
  }

  /**
   * Stops the recording process.
   *
   * @throws Exception	if stopping fails
   */
  @Override
  protected void doStop() throws Exception {
    m_Grabber.stop();
    m_Grabber = null;
  }

  /**
   * Returns the type of BufferedImage to create.
   *
   * @return		the type
   */
  protected abstract int getBufferedImageType();

  /**
   * Converts the BufferedImage if the image type is not the same as the
   * one specified by {@link #getBufferedImageType()}.
   *
   * @param frame	the frame to (potentially) correct
   * @return 		the corrected image
   */
  protected BufferedImage convertBufferedImage(BufferedImage frame) {
    BufferedImage 	newFrame;

    if (frame.getType() != getBufferedImageType()) {
      newFrame = new BufferedImage(frame.getWidth(), frame.getHeight(), getBufferedImageType());
      newFrame.getGraphics().drawImage(frame, 0, 0, null);
      frame = newFrame;
    }

    return frame;
  }

  /**
   * Performs the actual grabbing of the frame.
   *
   * @throws Exception	true if failed to grab frame
   */
  protected abstract void doGrabFrame() throws Exception;

  /**
   * Grabs a frame.
   *
   * @return		null if OK, otherwise error message
   */
  public String grabFrame() {
    try {
      doGrabFrame();
      return null;
    }
    catch (Exception e) {
      return printError("grabFrame", "Failed to grab frame!\n" + Utils.throwableToString(e));
    }
  }
}
