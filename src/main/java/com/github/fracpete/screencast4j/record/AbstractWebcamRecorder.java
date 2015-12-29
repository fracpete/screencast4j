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
 * AbstractWebcamRecorder.java
 * Copyright (C) 2015 University of Waikato, Hamilton, NZ
 */

package com.github.fracpete.screencast4j.record;

import java.awt.Dimension;

/**
 * TODO: What class does.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public abstract class AbstractWebcamRecorder
  extends AbstractVideoRecorder {

  /** the size to use. */
  protected Dimension m_Size;

  /**
   * Initializes the members.
   */
  @Override
  protected void initialize() {
    super.initialize();

    m_Size = new Dimension(320, 240);
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

  /**
   * Pauses the recording process.
   *
   * @throws Exception	if pausing fails
   */
  @Override
  protected void doPause() throws Exception {
    // does nothing
  }

  /**
   * Resumes the recording process.
   *
   * @throws Exception	if resuming fails
   */
  @Override
  protected void doResume() throws Exception {
    // does nothing
  }
}
