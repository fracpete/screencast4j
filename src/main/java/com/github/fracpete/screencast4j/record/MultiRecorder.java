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
 * MultiRecorder.java
 * Copyright (C) 2015 University of Waikato, Hamilton, NZ
 */

package com.github.fracpete.screencast4j.record;

import java.awt.Dimension;
import java.io.File;

/**
 * Combines multiple recorders.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class MultiRecorder
  extends AbstractRecorder {

  /** the recorders to use. */
  protected Recorder[] m_Recorders;

  /**
   * Sets the recorders to use.
   *
   * @param value	the recorders
   */
  public void setRecorders(Recorder[] value) {
    m_Recorders = value;
  }

  /**
   * Returns the recorders in use.
   *
   * @return		the recorders
   */
  public Recorder[] getRecorders() {
    return m_Recorders;
  }

  /**
   * Performs a check of the setup.
   *
   * @return		null if OK, otherwise error message
   */
  @Override
  public String setUp() {
    String	result;

    result = super.setUp();

    if (result == null) {
      for (Recorder rec: m_Recorders) {
	result = rec.setUp();
	if (result != null)
	  break;
      }
    }

    return result;
  }

  /**
   * Starts the actual recording process.
   *
   * @throws Exception	if starting of recording fails
   */
  @Override
  protected void doStart() throws Exception {
    for (Recorder rec: m_Recorders)
      rec.start();
  }

  /**
   * Stops the recording process.
   *
   * @throws Exception	if stopping fails
   */
  @Override
  protected void doStop() throws Exception {
    for (Recorder rec: m_Recorders)
      rec.stop();
  }

  /**
   * Pauses the recording process.
   *
   * @throws Exception	if pausing fails
   */
  @Override
  protected void doPause() throws Exception {
    for (Recorder rec: m_Recorders)
      rec.pause();
  }

  /**
   * Resumes the recording process.
   *
   * @throws Exception	if resuming fails
   */
  @Override
  protected void doResume() throws Exception {
    for (Recorder rec: m_Recorders)
      rec.resume();
  }

  /**
   * Just for testing.
   *
   * @param args	ignored
   */
  public static void main(String[] args) throws Exception {
    XuggleScreenRecorder screen = new XuggleScreenRecorder();
    screen.setOutput(new File(System.getProperty("java.io.tmpdir") + File.separator + "screen.ts"));
    screen.setCaptureMouse(true);
    screen.setFramesPerSecond(25);
    SarxosWebcamRecorder webcam = new SarxosWebcamRecorder();
    webcam.setOutput(new File(System.getProperty("java.io.tmpdir") + File.separator + "webcam.ts"));
    webcam.setFramesPerSecond(25);
    webcam.setSize(new Dimension(640, 480));
    SampledSoundRecorder sound = new SampledSoundRecorder();
    sound.setOutput(new File(System.getProperty("java.io.tmpdir") + File.separator + "sound.wav"));
    sound.setFrequency(44100.0f);
    MultiRecorder multi = new MultiRecorder();
    multi.setRecorders(new Recorder[]{screen, webcam, sound});
    multi.setUp();
    multi.start();
    for (int i = 0; i < 200; i++)
      Thread.sleep(100);
    multi.stop();
  }
}
