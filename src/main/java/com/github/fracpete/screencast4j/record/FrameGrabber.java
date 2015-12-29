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
 * FrameGrabber.java
 * Copyright (C) 2015 FracPete (fracpete at gmail dot com)
 */

package com.github.fracpete.screencast4j.record;

/**
 * Runnable for grabbing frames.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class FrameGrabber
  implements Runnable {

  /** the owner. */
  protected VideoRecorder m_Owner;

  /** whether frame grabbing is active. */
  protected boolean m_Running;

  /** whether frame grabbing is paused. */
  protected boolean m_Paused;

  /**
   * Initializes the runnable.
   */
  public FrameGrabber(VideoRecorder owner) {
    m_Owner   = owner;
    m_Running = false;
    m_Paused  = false;
  }

  /**
   * Performs the frame grabbing.
   */
  @Override
  public void run() {
    int 	msec;
    long	before;
    long	after;
    long	left;

    m_Running = true;
    msec = (int) (1000.0 / m_Owner.getFramesPerSecond());

    while (m_Running) {
      // idle while paused
      if (m_Paused) {
	try {
	  synchronized (this) {
	    wait(50);
	  }
	}
	catch (Exception e) {
	  // ignored
	}
	continue;
      }

      if (!m_Running)
	continue;

      // grab frame
      before = System.currentTimeMillis();
      m_Owner.grabFrame();
      after = System.currentTimeMillis();

      if (!m_Running)
	continue;

      // wait for next frame
      left = msec - (after - before);
      if (left > 0) {
	try {
	  synchronized(this) {
	    wait(left);
	  }
	}
	catch (Exception e) {
	  // ignored
	}
      }
    }
  }

  /**
   * Returns whether the frame grabbing is active.
   *
   * @return		true if active
   */
  public boolean isRunning() {
    return m_Running;
  }

  /**
   * Stops the frame grabbing.
   */
  public void stop() {
    m_Running = false;
    m_Paused  = false;
  }

  /**
   * Pauses the frame grabbing (if running).
   */
  public void pause() {
    if (m_Running)
      m_Paused = true;
  }

  /**
   * Resumes the frame grabbing (if paused)
   */
  public void resume() {
    if (m_Paused)
      m_Paused = false;
  }

  /**
   * Returns whether the frame grabbing is paused.
   *
   * @return		true if paused
   */
  public boolean isPaused() {
    return m_Paused;
  }
}
