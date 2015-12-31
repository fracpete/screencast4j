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
 * AbstractWebcamRecorder.java
 * Copyright (C) 2015 FracPete (fracpete at gmail dot com)
 */

package com.github.fracpete.screencast4j.record.webcam;

import com.github.fracpete.screencast4j.record.AbstractVideoRecorder;

import java.awt.Dimension;

/**
 * Ancestor for webcam recorders.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public abstract class AbstractWebcamRecorder
  extends AbstractVideoRecorder
  implements WebcamRecorder {

  /** the ID of the webcam to use. */
  protected String m_WebcamID;

  /** the size to use. */
  protected Dimension m_Size;

  /**
   * Initializes the members.
   */
  @Override
  protected void initialize() {
    super.initialize();

    m_WebcamID = "";
    m_Size     = new Dimension(320, 240);
  }

  /**
   * Sets the ID of the webcam to use. Empty string for default.
   *
   * @param value	the ID
   */
  public void setWebcamID(String value) {
    m_WebcamID = value;
  }

  /**
   * Returns the ID of the webcam in use. Empty string for default.
   *
   * @return		the ID
   */
  public String getWebcamID() {
    return m_WebcamID;
  }

  /**
   * Sets the size to use.
   *
   * @param value	the size
   */
  public void setSize(Dimension value) {
    m_Size = value;
  }

  /**
   * Returns the size in use.
   *
   * @return		the size
   */
  public Dimension getSize() {
    return m_Size;
  }
}
