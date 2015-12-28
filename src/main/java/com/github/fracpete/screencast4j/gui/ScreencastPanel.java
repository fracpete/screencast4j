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

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;

public class ScreencastPanel
  extends BasePanel {

  private static final long serialVersionUID = -1323993618955317051L;

  /** directory chooser for selecting the project. */
  protected BaseDirectoryChooser m_DirChooser;

  /** the menubar. */
  protected JMenuBar m_MenuBar;

  /**
   * Initializes the members.
   */
  @Override
  protected void initialize() {
    super.initialize();

    m_DirChooser = new BaseDirectoryChooser();
  }

  /**
   * Returns/creates the menubar for JFrames.
   *
   * @return		the menu bar
   */
  public JMenuBar getMenuBar() {
    JMenu	menu;
    JMenuItem	menuitem;

    if (m_MenuBar != null)
      return m_MenuBar;

    m_MenuBar = new JMenuBar();

    // File
    menu = new JMenu("File");
    m_MenuBar.add(menu);
    menu.setMnemonic('F');
    menu.addChangeListener((ChangeEvent e) -> updateMenu());

    // File/Close
    menuitem = new JMenuItem("Close", GUIHelper.getIcon("close.png"));
    menuitem.addActionListener((ActionEvent e) -> close());
    menu.add(menuitem);

    return m_MenuBar;
  }

  /**
   * Updates the state of the menu items.
   */
  protected void updateMenu() {
    // TODO
  }

  /**
   * Closes the dialog.
   */
  public void close() {
    GUIHelper.closeParent(this);
  }
}
