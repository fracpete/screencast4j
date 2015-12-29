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

import com.github.fracpete.screencast4j.record.MultiRecorder;
import com.github.fracpete.screencast4j.record.Recorder;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.awt.event.ActionEvent;
import java.net.URI;

public class ScreencastPanel
  extends BasePanel {

  private static final long serialVersionUID = -1323993618955317051L;

  public static final String HOMEPAGE = "https://github.com/fracpete/screencast4j";

  /** directory chooser for selecting the project. */
  protected BaseDirectoryChooser m_DirChooser;

  /** the menubar. */
  protected JMenuBar m_MenuBar;

  /** the "new" menu item. */
  protected JMenuItem m_MenuItemNew;

  /** the "record" menu item. */
  protected JMenuItem m_MenuItemRecord;

  /** the "pause/resume" menu item. */
  protected JMenuItem m_MenuItemPauseResume;

  /** the "stop" menu item. */
  protected JMenuItem m_MenuItemStop;

  /** the current recorder. */
  protected Recorder m_Recorder;

  /**
   * Initializes the members.
   */
  @Override
  protected void initialize() {
    super.initialize();

    m_DirChooser = new BaseDirectoryChooser();
    m_Recorder   = new MultiRecorder();
  }

  /**
   * Finishes the initialization.
   */
  @Override
  protected void finishInit() {
    super.finishInit();

    newRecording();
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

    // File/New
    menuitem = new JMenuItem("New", GUIHelper.getIcon("new.png"));
    menuitem.setAccelerator(KeyStroke.getKeyStroke("ctrl pressed N"));
    menuitem.addActionListener((ActionEvent e) -> newRecording());
    menu.add(menuitem);

    // File/Close
    menuitem = new JMenuItem("Close", GUIHelper.getIcon("close.png"));
    menuitem.setAccelerator(KeyStroke.getKeyStroke("ctrl pressed Q"));
    menuitem.addActionListener((ActionEvent e) -> close());
    menu.addSeparator();
    menu.add(menuitem);

    // Record
    menu = new JMenu("Record");
    m_MenuBar.add(menu);
    menu.setMnemonic('R');
    menu.addChangeListener((ChangeEvent e) -> updateMenu());

    // Record/Start
    menuitem = new JMenuItem("Start", GUIHelper.getIcon("play.png"));
    menuitem.setAccelerator(KeyStroke.getKeyStroke("F2"));
    menuitem.addActionListener((ActionEvent e) -> startRecording());
    menu.add(menuitem);
    m_MenuItemRecord = menuitem;

    // Record/PauseResume
    menuitem = new JMenuItem("Pause", GUIHelper.getIcon("pause.png"));
    menuitem.setAccelerator(KeyStroke.getKeyStroke("F3"));
    menuitem.addActionListener((ActionEvent e) -> pauseResumeRecording());
    menu.add(menuitem);
    m_MenuItemPauseResume = menuitem;

    // Record/Stop
    menuitem = new JMenuItem("Stop", GUIHelper.getIcon("stop.png"));
    menuitem.setAccelerator(KeyStroke.getKeyStroke("F4"));
    menuitem.addActionListener((ActionEvent e) -> stopRecording());
    menu.add(menuitem);
    m_MenuItemStop = menuitem;

    // Help
    menu = new JMenu("Help");
    m_MenuBar.add(menu);
    menu.setMnemonic('H');
    menu.addChangeListener((ChangeEvent e) -> updateMenu());

    // Help/Homepage
    menuitem = new JMenuItem("Homepage", GUIHelper.getIcon("home.png"));
    menuitem.setAccelerator(KeyStroke.getKeyStroke("F1"));
    menuitem.addActionListener((ActionEvent e) -> gotoHomepage());
    menu.add(menuitem);

    return m_MenuBar;
  }

  /**
   * Updates the state of the menu items.
   */
  protected void updateMenu() {
    if (m_MenuBar == null)
      return;

    m_MenuItemRecord.setEnabled(!m_Recorder.isPaused() && !m_Recorder.isRecording());
    m_MenuItemPauseResume.setEnabled(m_Recorder.isPaused() || m_Recorder.isRecording());
    m_MenuItemStop.setEnabled(m_Recorder.isPaused() || m_Recorder.isRecording());

    // TODO
  }

  /**
   * Creates a new recording.
   */
  public void newRecording() {
    // TODO
    updateMenu();
  }

  /**
   * Configures the recorder and starts the recording.
   */
  public void startRecording() {
    // TODO
    updateMenu();
  }

  /**
   * Pauses/resumes the recording
   */
  public void pauseResumeRecording() {
    // TODO
    updateMenu();
  }

  /**
   * Stops the recording.
   */
  public void stopRecording() {
    // TODO
    updateMenu();
  }

  /**
   * Launches browser with homepage.
   */
  public void gotoHomepage() {
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Action.BROWSE)) {
      try {
	Desktop.getDesktop().browse(new URI(HOMEPAGE));
      }
      catch (Exception e) {
	System.err.println("Failed to launch browser with homepage!");
	e.printStackTrace();
      }
    }
  }

  /**
   * Closes the dialog.
   */
  public void close() {
    GUIHelper.closeParent(this);
  }
}
