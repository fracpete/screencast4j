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
 * AbstractRecorder.java
 * Copyright (C) 2015 FracPete (fracpete at gmail dot com)
 */

package com.github.fracpete.screencast4j.record;

import com.googlecode.jfilechooserbookmarks.core.Utils;

/**
 * Ancestor for recorders.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public abstract class AbstractRecorder
  implements Recorder {

  /** the state. */
  protected RecorderState m_State;

  /**
   * Initializes the recorder.
   */
  protected AbstractRecorder() {
    initialize();
    reset();
  }

  /**
   * Initializes members.
   */
  protected void initialize() {
  }

  /**
   * Resets the recorder's state (but not parameters).
   */
  public void reset() {
    m_State = RecorderState.NONE;
  }

  /**
   * Prefixes the error messages with the class name, outputs it on stderr
   * and returns the extended message.
   *
   * @param source	the source
   * @param msg		the message to prinnt
   * @return		the extended message
   */
  protected String printError(String source, String msg) {
    return printError("[" + source + "] " + msg);
  }

  /**
   * Prefixes the error messages with the class name, outputs it on stderr
   * and returns the extended message.
   *
   * @param msg		the message to prinnt
   * @return		the extended message
   */
  protected String printError(String msg) {
    String	result;

    result = getClass().getName() + ": " + msg;
    System.err.println(result);

    return result;
  }

  /**
   * Performs a check of the setup.
   *
   * @return		null if OK, otherwise error message
   */
  public String setUp() {
    return null;
  }

  /**
   * Starts the actual recording process.
   *
   * @throws Exception	if starting of recording fails
   */
  protected abstract void doStart() throws Exception;

  /**
   * Starts the recording process.
   *
   * @return		null if OK, otherwise error message
   */
  public String start() {
    if (m_State == RecorderState.NONE) {
      try {
	doStart();
	m_State = RecorderState.RECORDING;
	return null;
      }
      catch (Exception e) {
	return printError("start", "Failed to start recording: " + Utils.throwableToString(e));
      }
    }
    else {
      return printError("start", "Recording already started");
    }
  }

  /**
   * Checks whether currently recording (but not paused).
   *
   * @return		true if recording
   */
  public boolean isRecording() {
    return (m_State == RecorderState.RECORDING);
  }

  /**
   * Stops the recording process.
   *
   * @throws Exception	if stopping fails
   */
  protected abstract void doStop() throws Exception;

  /**
   * Stops the recording.
   *
   * @return		null if OK, otherwise error message
   */
  public synchronized String stop() {
    if ((m_State == RecorderState.RECORDING) || (m_State == RecorderState.PAUSED)) {
      try {
	doStop();
	m_State = RecorderState.STOPPED;
	return null;
      }
      catch (Exception e) {
	return printError("stop", "Failed to stop recording: " + Utils.throwableToString(e));
      }
    }
    else {
      return printError("stop", "Not recording currently");
    }
  }

  /**
   * Checks whether recording was stopped.
   *
   * @return		true if stopped
   */
  public boolean isStopped() {
    return (m_State == RecorderState.STOPPED);
  }

  /**
   * Pauses the recording process.
   *
   * @throws Exception	if pausing fails
   */
  protected abstract void doPause() throws Exception;

  /**
   * Pauses the recording.
   *
   * @return		null if OK, otherwise error message
   */
  public synchronized String pause() {
    if (m_State == RecorderState.RECORDING) {
      try {
	doPause();
	m_State = RecorderState.PAUSED;
	return null;
      }
      catch (Exception e) {
	return printError("pause", "Failed to pause recording: " + Utils.throwableToString(e));
      }
    }
    else {
      if (m_State == RecorderState.PAUSED)
	return printError("pause", "Already paused");
      else
	return printError("pause", "Not recording currently");
    }
  }

  /**
   * Checks whether recording was paused.
   *
   * @return		true if paused
   */
  public boolean isPaused() {
    return (m_State == RecorderState.PAUSED);
  }

  /**
   * Resumes the recording process.
   *
   * @throws Exception	if resuming fails
   */
  protected abstract void doResume() throws Exception;

  /**
   * Resumes the recording.
   *
   * @return		null if OK, otherwise error message
   */
  public synchronized String resume() {
    if (m_State == RecorderState.PAUSED) {
      try {
	doResume();
	m_State = RecorderState.RECORDING;
	return null;
      }
      catch (Exception e) {
	return printError("resume", "Failed to resume recording: " + Utils.throwableToString(e));
      }
    }
    else {
      return printError("resume", "Not paused currently");
    }
  }

  /**
   * Outputs a short description of the state.
   *
   * @return		the state
   */
  public String toString() {
    return getClass().getName() + ": state=" + m_State;
  }
}
