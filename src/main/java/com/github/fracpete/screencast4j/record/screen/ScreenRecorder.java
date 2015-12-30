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
 * ScreenRecorder.java
 * Copyright (C) 2015 FracPete (fracpete at gmail dot com)
 */

package com.github.fracpete.screencast4j.record.screen;

import com.github.fracpete.screencast4j.record.FileBasedRecorder;
import com.github.fracpete.screencast4j.record.VideoRecorder;

/**
 * Interface for screen recorders.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public interface ScreenRecorder
  extends FileBasedRecorder, VideoRecorder {

  /**
   * Sets the X position of the screen portion (0-based).
   *
   * @param value	the X position
   */
  public void setX(int value);

  /**
   * Returns the X position of the screen portion (0-based).
   *
   * @return		the Y position
   */
  public int getX();

  /**
   * Sets the Y position of the screen portion (0-based).
   *
   * @param value	the Y position
   */
  public void setY(int value);

  /**
   * Returns the Y position of the screen portion (0-based).
   *
   * @return		the Y position
   */
  public int getY();

  /**
   * Sets the width of the screen portion (-1 = remainder).
   *
   * @param value	the width
   */
  public void setWidth(int value);

  /**
   * Returns the width of the screen portion (-1 = remainder).
   *
   * @return		the width
   */
  public int getWidth();

  /**
   * Sets the height of the screen portion (-1 = remainder).
   *
   * @param value	the height
   */
  public void setHeight(int value);

  /**
   * Returns the height of the screen portion (-1 = remainder).
   *
   * @return		the height
   */
  public int getHeight();

  /**
   * Sets whether to capture the mouse cursor.
   *
   * @param value	true if to capture
   */
  public void setCaptureMouse(boolean value);

  /**
   * Returns whether to capture the mouse cursor.
   *
   * @return		true if to capture
   */
  public boolean getCaptureMouse();
}
