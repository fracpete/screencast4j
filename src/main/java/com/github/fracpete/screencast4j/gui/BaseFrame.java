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
 * BaseFrame.java
 * Copyright (C) 2008-2015 University of Waikato, Hamilton, New Zealand
 */

package com.github.fracpete.screencast4j.gui;

import javax.swing.JFrame;
import java.awt.GraphicsConfiguration;

/**
 * A frame that loads the size and location from the props file automatically.
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 */
public class BaseFrame
  extends JFrame {

  /** for serialization. */
  private static final long serialVersionUID = -4853427519044621963L;

  /**
   * Initializes the frame with no title.
   */
  public BaseFrame() {
    this("");
  }

  /**
   * Initializes the frame with the given title.
   *
   * @param title	the title of the frame
   */
  public BaseFrame(String title) {
    super(title);

    performInitialization();
  }

  /**
   * Initializes the frame with no title.
   *
   * @param gc		the graphics configuration to use
   */
  public BaseFrame(GraphicsConfiguration gc) {
    this("", gc);
  }

  /**
   * Initializes the frame with the specified title.
   *
   * @param title	the title of the frame
   * @param gc		the graphics configuration to use
   */
  public BaseFrame(String title, GraphicsConfiguration gc) {
    super(title, gc);

    performInitialization();
  }

  /**
   * Contains all the initialization steps to perform.
   */
  protected void performInitialization() {
    initialize();
    initGUI();
    finishInit();
  }

  /**
   * For initializing members.
   */
  protected void initialize() {
  }

  /**
   * For initializing the GUI.
   */
  protected void initGUI() {
    setIconImage(GUIHelper.getIcon("monitor.png").getImage());
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }

  /**
   * finishes the initialization, by setting size/location.
   */
  protected void finishInit() {
  }

  /**
   * Hook method just before the dialog is made visible.
   */
  protected void beforeShow() {
  }

  /**
   * Hook method just after the dialog was made visible.
   */
  protected void afterShow() {
  }

  /**
   * Hook method just before the dialog is hidden.
   */
  protected void beforeHide() {
  }

  /**
   * Hook method just after the dialog was hidden.
   */
  protected void afterHide() {
  }

  /**
   * closes/shows the dialog.
   *
   * @param value	if true then display the dialog, otherwise close it
   */
  @Override
  public void setVisible(boolean value) {
    if (value)
      beforeShow();
    else
      beforeHide();

    super.setVisible(value);

    if (value)
      afterShow();
    else
      afterHide();
  }
}
