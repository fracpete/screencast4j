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
 * FileBasedRecorder.java
 * Copyright (C) 2015 FracPete (fracpete at gmail dot com)
 */

package com.github.fracpete.screencast4j.record;

import java.io.File;

/**
 * Interface for file-based recorders.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public interface FileBasedRecorder
  extends Recorder {

  /**
   * Sets the output file to use.
   *
   * @param value	the output file
   */
  public void setOutput(File value);

  /**
   * Returns the output file.
   *
   * @return		the output file
   */
  public File getOutput();

  /**
   * Returns the default file extension to use.
   *
   * @return		the extension (no dot)
   */
  public String getDefaultExtension();
}
