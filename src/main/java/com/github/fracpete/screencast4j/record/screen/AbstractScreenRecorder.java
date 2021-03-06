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
 * AbstractScreenRecorder.java
 * Copyright (C) 2015 FracPete (fracpete at gmail dot com)
 */

package com.github.fracpete.screencast4j.record.screen;

import com.github.fracpete.screencast4j.gui.GUIHelper;
import com.github.fracpete.screencast4j.record.AbstractVideoRecorder;
import com.googlecode.jfilechooserbookmarks.core.Utils;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

/**
 * Ancestor for screen recorders.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public abstract class AbstractScreenRecorder
  extends AbstractVideoRecorder
  implements ScreenRecorder {

  /** the normal mouse cursor. */
  public final static String MOUSE_CURSOR = "cursor.png";

  /** the X position of the screen portion to grab (0-based). */
  protected int m_X;

  /** the Y position of the screen portion to grab (0-based). */
  protected int m_Y;

  /** the width of the screen portion to grab (-1 = remainder). */
  protected int m_Width;

  /** the height of the screen portion to grab (-1 = remainder). */
  protected int m_Height;

  /** whether to capture the mouse cursor. */
  protected boolean m_CaptureMouse;

  /** performs the screenshots. */
  protected Robot m_Robot;

  /** the screen portion to grab. */
  protected Rectangle m_ScreenPortion;

  /** the image of the normal cursor. */
  protected Image m_Cursor;

  /** the background color. */
  protected Color m_BackgroundColor;

  /**
   * Initializes the members.
   */
  @Override
  protected void initialize() {
    ImageIcon	image;

    super.initialize();

    m_X       = 0;
    m_Y       = 0;
    m_Width   = -1;
    m_Height  = -1;
    image     = GUIHelper.getIcon(MOUSE_CURSOR);
    if (image != null)
      m_Cursor = image.getImage();
    m_BackgroundColor = new Color(255, 255, 255, 0);
  }

  /**
   * Resets the recorder's state (but not parameters).
   */
  @Override
  public void reset() {
    super.reset();

    m_ScreenPortion = null;
  }

  /**
   * Sets the X position of the screen portion (0-based).
   *
   * @param value	the X position
   */
  public void setX(int value) {
    if (value >= 0)
      m_X = value;
    else
      printError("X must be >= 0, provided: " + value);
  }

  /**
   * Returns the X position of the screen portion (0-based).
   *
   * @return		the Y position
   */
  public int getX() {
    return m_X;
  }

  /**
   * Sets the Y position of the screen portion (0-based).
   *
   * @param value	the Y position
   */
  public void setY(int value) {
    if (value >= 0)
      m_Y = value;
    else
      printError("Y must be >= 0, provided: " + value);
  }

  /**
   * Returns the Y position of the screen portion (0-based).
   *
   * @return		the Y position
   */
  public int getY() {
    return m_Y;
  }

  /**
   * Sets the width of the screen portion (-1 = remainder).
   *
   * @param value	the width
   */
  public void setWidth(int value) {
    if (value >= -1)
      m_Width = value;
    else
      printError("Width must be >= -1, provided: " + value);
  }

  /**
   * Returns the width of the screen portion (-1 = remainder).
   *
   * @return		the width
   */
  public int getWidth() {
    return m_Width;
  }

  /**
   * Sets the height of the screen portion (-1 = remainder).
   *
   * @param value	the height
   */
  public void setHeight(int value) {
    if (value >= -1)
      m_Height = value;
    else
      printError("Height must be >= -1, provided: " + value);
  }

  /**
   * Returns the height of the screen portion (-1 = remainder).
   *
   * @return		the height
   */
  public int getHeight() {
    return m_Height;
  }

  /**
   * Sets whether to capture the mouse cursor.
   *
   * @param value	true if to capture
   */
  public void setCaptureMouse(boolean value) {
    m_CaptureMouse = value;
  }

  /**
   * Returns whether to capture the mouse cursor.
   *
   * @return		true if to capture
   */
  public boolean getCaptureMouse() {
    return m_CaptureMouse;
  }

  /**
   * Calculates the screen portion to grab.
   *
   * @return		the screen portion
   */
  protected Rectangle calcScreenPortion() {
    Rectangle 		result;
    GraphicsDevice	device;
    Rectangle		bounds;
    int 		actWidth;
    int 		actHeight;

    if ((m_Width == -1) || (m_Height == -1)) {
      device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
      bounds = device.getDefaultConfiguration().getBounds();
      actWidth = (int) bounds.getWidth()  - m_X;
      actHeight = (int) bounds.getHeight() - m_Y;
    }
    else {
      actWidth = m_Width;
      actHeight = m_Height;
    }
    result = new Rectangle(m_X, m_Y, actWidth, actHeight);

    return result;
  }

  /**
   * Performs a check of the setup.
   *
   * @return		null if OK, otherwise error message
   */
  @Override
  public String setUp() {
    String	result;

    result = super.setUp();

    if (result == null) {
      if (m_Cursor == null)
	return "Failed to load mouse cursor image: " + MOUSE_CURSOR;
      if (m_Width == 0)
	return "Width must be -1 or > 0!";
      if (m_Height == 0)
	return "Height must be -1 or > 0!";
      m_ScreenPortion = calcScreenPortion();
      try {
	m_Robot = new Robot();
      }
      catch (Exception e) {
	return "Failed to instantiate robot: " + Utils.throwableToString(e);
      }
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
    m_Robot = null;
  }

  /**
   * Writes the frame out to disk.
   *
   * @param frame	the frame
   * @throws Exception	if writing fails
   */
  protected abstract void writeFrame(BufferedImage frame) throws Exception;

  /**
   * Draws the cursor on the frame, if required.
   *
   * @param frame	the frame to update
   * @return		the (potentially) updated frame
   */
  protected BufferedImage drawCursor(BufferedImage frame) {
    PointerInfo 	pointer;

    if (m_CaptureMouse && (m_Cursor != null)) {
      pointer = MouseInfo.getPointerInfo();
      frame.getGraphics().drawImage(
	m_Cursor,
	(int) pointer.getLocation().getX() - m_X,
	(int) pointer.getLocation().getY() - m_Y,
	m_Cursor.getWidth(null),
	m_Cursor.getHeight(null),
	m_BackgroundColor,
	null);
    }

    return frame;
  }

  /**
   * Performs the actual grabbing of the frame.
   *
   * @throws Exception	true if failed to grab frame
   */
  @Override
  protected void doGrabFrame() throws Exception {
    BufferedImage 	frame;

    frame = m_Robot.createScreenCapture(m_ScreenPortion);
    frame = convertBufferedImage(frame);
    drawCursor(frame);
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
    Robot		robot;
    Rectangle 		portion;

    portion = calcScreenPortion();
    robot   = new Robot();
    result  = robot.createScreenCapture(portion);
    result  = convertBufferedImage(result);
    drawCursor(result);

    return result;
  }

  /**
   * Outputs a short description of the state.
   *
   * @return		the state
   */
  public String toString() {
    return super.toString()
      + ", x=" + m_X + ", y=" + m_Y
      + ", w=" + m_Width + ", h=" + m_Height;
  }
}
