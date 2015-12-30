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
 * AbstractPreviewPanel.java
 * Copyright (C) 2015 University of Waikato, Hamilton, NZ
 */

package com.github.fracpete.screencast4j.gui;

import com.github.fracpete.screencast4j.record.VideoRecorder;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

/**
 * Ancestor for preview panels.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class PreviewPanel
  extends BasePanel {

  private static final long serialVersionUID = -7012308044086160882L;

  /** the recorder to obtain the preview image with. */
  protected ImagePanel m_PanelImage;

  /** whether the preview is active. */
  protected boolean m_Running;

  /** whether to update the preview. */
  protected boolean m_Update;

  /** the update interval in msec. */
  protected int m_Interval;

  /** the update thread. */
  protected Thread m_Thread;

  /** the recorder to use for obtaining the preview image. */
  protected VideoRecorder m_Recorder;

  /**
   * Initializes the members.
   */
  @Override
  protected void initialize() {
    super.initialize();

    m_Update   = false;
    m_Interval = 1000;
    m_Recorder = null;

    m_Thread = new Thread(() -> {
      m_Running = true;
      while (m_Running) {
	try {
	  synchronized (this) {
	    wait(m_Interval);
	  }
	}
	catch (Exception e) {
	  // ignored
	}

	if (!m_Update || (m_Recorder == null))
	  continue;

	BufferedImage image = m_Recorder.grabImage();
	m_PanelImage.setImage(image);
	// TODO
	System.err.println(m_Recorder.getClass().getSimpleName() + " - Image? " + (image != null));
      }
    });
  }

  /**
   * Initializes the widgets.
   */
  @Override
  protected void initGUI() {
    super.initGUI();

    setLayout(new BorderLayout());

    m_PanelImage = new ImagePanel();
    add(m_PanelImage, BorderLayout.CENTER);
  }

  /**
   * Finishes up the initialization.
   */
  @Override
  protected void finishInit() {
    super.finishInit();
    m_Thread.start();
  }

  /**
   * Sets the recorder to use for obtaining the preview image.
   *
   * @param value	the recorder
   */
  public void setRecorder(VideoRecorder value) {
    m_Recorder = value;
  }

  /**
   * Returns the recorder in use for obtaining the preview image.
   *
   * @return		the recorder, null if none set
   */
  public VideoRecorder getRecorder() {
    return m_Recorder;
  }

  /**
   * Sets the update interval to use.
   *
   * @param value	the interval in msec
   */
  public void setInterval(int value) {
    if (m_Interval > 0)
      m_Interval = value;
    else
      System.err.println("Update intervall must >0, provided: " + value);
  }

  /**
   * Returns the update interval in use.
   *
   * @return		the interval in msec
   */
  public int getInterval() {
    return m_Interval;
  }

  /**
   * Sets whether to scale the image, ie resize to fit panel.
   *
   * @param value	true if to make it fit the panel
   */
  public void setScale(boolean value) {
    m_PanelImage.setScale(value);
  }

  /**
   * Returns whether the image is scaled, ie resized to fit the panel.
   *
   * @return		true if scaled to fit panel
   */
  public boolean getScale() {
    return m_PanelImage.getScale();
  }

  /**
   * Sets whether to update the preview.
   *
   * @param value	true if to update the preview
   */
  public void setUpdate(boolean value) {
    m_Update = value;
  }

  /**
   * Returns whether to update the preview.
   *
   * @return		true if to update the preview
   */
  public boolean getUpdate() {
    return m_Update;
  }

  /**
   * Stops the update thread.
   */
  public void stop() {
    m_Running = false;
  }
}
