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
 * SampledSoundRecorder.java
 * Copyright (C) 2015 FracPete (fracpete at gmail dot com)
 */

package com.github.fracpete.screencast4j.record.sound;

import com.googlecode.jfilechooserbookmarks.core.Utils;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import java.io.File;

/**
 * For recording sound using Java's sampled.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class SampledSoundRecorder
  extends AbstractSoundRecorder {

  /** the audio format in use. */
  protected AudioFormat m_AudioFormat;

  /** the line info. */
  protected DataLine.Info m_DataLineInfo;

  /** the target line. */
  protected TargetDataLine m_TargetDataLine;

  /** the input stream. */
  protected AudioInputStream m_AudioInputStream;

  /**
   * Returns the default file extension to use.
   *
   * @return		the extension (no dot)
   */
  @Override
  public String getDefaultExtension() {
    return "wav";
  }

  @Override
  public String setUp() {
    String	result;

    result = super.setUp();

    if (result == null) {
      m_AudioFormat = new AudioFormat(
	AudioFormat.Encoding.PCM_SIGNED,
	m_Frequency, 16, 2, 4, m_Frequency, false);
      m_DataLineInfo = new DataLine.Info(TargetDataLine.class, m_AudioFormat);
      try {
	m_TargetDataLine = (TargetDataLine) AudioSystem.getLine(m_DataLineInfo);
	m_TargetDataLine.open(m_AudioFormat);
      }
      catch (Exception e) {
	return "Unable to get recording line: " + Utils.throwableToString(e);
      }
      m_AudioInputStream = new AudioInputStream(m_TargetDataLine);
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
    m_TargetDataLine.start();
    new Thread(() -> {
      try {
	AudioSystem.write(m_AudioInputStream, AudioFileFormat.Type.WAVE, m_Output);
      }
      catch (Exception e) {
	printError("record", "Failed to record: " + Utils.throwableToString(e));
      }
    }).start();
  }

  /**
   * Stops the recording process.
   *
   * @throws Exception	if stopping fails
   */
  @Override
  protected void doStop() throws Exception {
    m_TargetDataLine.stop();
    m_TargetDataLine.close();
  }

  /**
   * Indicates whether recorder can be paused and resumed.
   *
   * @return		true if pause/resume supported
   */
  public boolean canPauseAndResume() {
    return false;
  }

  /**
   * Does nothing.
   *
   * @throws Exception	if pausing fails
   */
  @Override
  protected void doPause() throws Exception {
  }

  /**
   * Does nothing.
   *
   * @throws Exception	if resuming fails
   */
  @Override
  protected void doResume() throws Exception {
  }

  /**
   * Just for testing.
   *
   * @param args	ignored
   */
  public static void main(String[] args) throws Exception {
    SampledSoundRecorder rec = new SampledSoundRecorder();
    rec.setOutput(new File(System.getProperty("java.io.tmpdir") + File.separator + "sound.wav"));
    rec.setFrequency(44100.0f);
    rec.setUp();
    rec.start();
    for (int i = 0; i < 200; i++)
      Thread.sleep(100);
    rec.stop();
  }
}
