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
 * Recorder.java
 * Copyright (C) 2015 FracPete (fracpete at gmail dot com)
 */

package com.github.fracpete.screencast4j.record;

/**
 * Interface for recorders.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public interface Recorder {

  /**
   * Configures the recorder.
   *
   * @return		null if OK, otherwise error message
   */
  public String setUp();

  /**
   * Starts the recording process.
   *
   * @return		null if OK, otherwise error message
   */
  public String start();
  /**
   * Checks whether currently recording (but not paused).
   *
   * @return		true if recording
   */
  public boolean isRecording();

  /**
   * Pauses the recording.
   *
   * @return		null if OK, otherwise error message
   */
  public String pause();

  /**
   * Checks whether recording was paused.
   *
   * @return		true if paused
   */
  public boolean isPaused();

  /**
   * Resumes the recording.
   *
   * @return		null if OK, otherwise error message
   */
  public String resume();

  /**
   * Stops the recording, cleans up.
   *
   * @return		null if OK, otherwise error message
   */
  public String stop();

  /**
   * Checks whether recording was stopped.
   *
   * @return		true if stopped
   */
  public boolean isStopped();
}
