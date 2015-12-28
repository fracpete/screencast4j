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
 * ScreencastPanel.java
 * Copyright (C) 2015 FracPete (fracpete at gmail dot com)
 *
 */

package com.github.fracpete.screencast4j.gui;

import javax.swing.JPanel;

/**
 * Basic panel.
 */
public class BasePanel
  extends JPanel {

  private static final long serialVersionUID = 4849972964340278417L;

  /**
   * Instantiates the panel.
   */
  public BasePanel() {
    initialize();
    initGUI();
    finishInit();
  }

  /**
   * Initializes the members.
   */
  protected void initialize() {
  }

  /**
   * Initializes the widgets.
   */
  protected void initGUI() {
  }

  /**
   * Finishes the initialization.
   */
  protected void finishInit() {
  }
}
