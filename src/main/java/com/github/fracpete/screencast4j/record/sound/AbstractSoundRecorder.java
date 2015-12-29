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
 * AbstractSoundRecorder.java
 * Copyright (C) 2015 FracPete (fracpete at gmail dot com)
 */

package com.github.fracpete.screencast4j.record.sound;

import com.github.fracpete.screencast4j.record.AbstractFileBasedRecorder;

/**
 * Ancestor for sound recorders.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public abstract class AbstractSoundRecorder
  extends AbstractFileBasedRecorder
  implements SoundRecorder {

  /** the frequency to use. */
  protected float m_Frequency;

  /**
   * Initializes the members.
   */
  @Override
  protected void initialize() {
    super.initialize();

    m_Frequency = 44100.0f;
  }

  /**
   * Sets the frequency to use.
   *
   * @param value	the frequency
   */
  public void setFrequency(float value) {
    if (value > 0)
      m_Frequency = value;
    else
      printError("Frequency must be > 0, provided: " + value);
  }

  /**
   * Returns the frequency in use.
   *
   * @return		the frequency
   */
  public float getFrequency() {
    return m_Frequency;
  }
}
