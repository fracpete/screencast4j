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
 * AbstractFileBasedRecorder.java
 * Copyright (C) 2015 FracPete (fracpete at gmail dot com)
 */

package com.github.fracpete.screencast4j.record;

import java.io.File;

/**
 * Ancestor for file-based recorders.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public abstract class AbstractFileBasedRecorder
  extends AbstractRecorder
  implements FileBasedRecorder {

  /** the output file. */
  protected File m_Output;

  /**
   * Initializes the recorder.
   */
  protected AbstractFileBasedRecorder() {
    initialize();
    reset();
  }

  /**
   * Initializes members.
   */
  protected void initialize() {
    super.initialize();
    m_Output = new File(".");
  }

  /**
   * Returns the default file extension to use.
   *
   * @return		the extension (no dot)
   */
  public abstract String getDefaultExtension();

  /**
   * Sets the output file to use.
   *
   * @param value	the output file
   */
  public void setOutput(File value) {
    if (m_State == RecorderState.NONE)
      m_Output = value;
    else
      printError("Cannot set output file once recording has commenced!");
  }

  /**
   * Returns the output file.
   *
   * @return		the output file
   */
  public File getOutput() {
    return m_Output;
  }

  /**
   * Performs a check of the setup.
   *
   * @return		null if OK, otherwise error message
   */
  public String setUp() {
    String	result;

    result = super.setUp();

    if (result == null) {
      if (m_Output.isDirectory())
	return printError("check", "Output is pointing to a directory: " + m_Output);
    }

    return result;
  }
}
