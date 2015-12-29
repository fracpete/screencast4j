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
 * VideoRecorder.java
 * Copyright (C) 2015 University of Waikato, Hamilton, NZ
 */

package com.github.fracpete.screencast4j.record;

/**
 * Interface for video recorders.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public interface VideoRecorder
  extends Recorder {

  /**
   * Sets the frames per second to use.
   *
   * @param value	the fps
   */
  public void setFramesPerSecond(double value);
  /**
   * Returns the frames per second.
   *
   * @return		the fps
   */
  public double getFramesPerSecond();

  /**
   * Grabs a frame.
   *
   * @return		null if OK, otherwise error message
   */
  public String grabFrame();
}
