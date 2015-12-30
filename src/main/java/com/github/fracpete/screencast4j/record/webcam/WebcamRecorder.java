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
 * WebcamRecorder.java
 * Copyright (C) 2015 FracPete (fracpete at gmail dot com)
 */

package com.github.fracpete.screencast4j.record.webcam;

import com.github.fracpete.screencast4j.record.FileBasedRecorder;
import com.github.fracpete.screencast4j.record.VideoRecorder;

import java.awt.Dimension;

/**
 * Interface for webcam recorders.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public interface WebcamRecorder
  extends FileBasedRecorder, VideoRecorder {

  /**
   * Sets the ID of the webcam to use. Empty string for default.
   *
   * @param value	the ID
   */
  public void setWebcamID(String value);

  /**
   * Returns the ID of the webcam in use. Empty string for default.
   *
   * @return		the ID
   */
  public String getWebcamID();

  /**
   * Sets the size to use.
   *
   * @param value	the size
   */
  public void setSize(Dimension value);

  /**
   * Returns the size in use.
   *
   * @return		the size
   */
  public Dimension getSize();
}
