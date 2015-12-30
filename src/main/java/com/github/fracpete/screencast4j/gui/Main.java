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
 * Main.java
 * Copyright (C) 2015 FracPete (fracpete at gmail dot com)
 *
 */

package com.github.fracpete.screencast4j.gui;

import java.awt.BorderLayout;

/**
 * Frame for recording screencasts.
 */
public class Main
  extends BaseFrame {

  private static final long serialVersionUID = -276357211631040195L;

  /** the screencast panel. */
  protected ScreencastPanel m_PanelScreencast;

  /**
   * Initializes the widgets.
   */
  @Override
  protected void initGUI() {
    super.initGUI();

    setTitle("screencast4j");
    getContentPane().setLayout(new BorderLayout());
    m_PanelScreencast = new ScreencastPanel();
    getContentPane().add(m_PanelScreencast, BorderLayout.CENTER);
    setJMenuBar(m_PanelScreencast.getMenuBar());
    setSize(1024, 768);
  }

  /**
   * Starts the main GUI.
   *
   * @param args ignored
   */
  public static void main(String[] args) {
    Main frame = new Main();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
