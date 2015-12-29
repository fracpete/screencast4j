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
 * BaseDirectoryChooser.java
 * Copyright (C) 2010-2015 University of Waikato, Hamilton, New Zealand
 */

package com.github.fracpete.screencast4j.gui;

import com.jidesoft.swing.FolderChooser;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Extended version of the com.jidesoft.swing.FolderChooser to
 * handle File objects.
 *
 * @author FracPete (fracpete at waikat dot ac dot nz)
 */
public class BaseDirectoryChooser
  extends FolderChooser {

  /** for serialization. */
  private static final long serialVersionUID = -7252242971482953986L;

  /** the bookmarks. */
  protected FileChooserBookmarksPanel m_PanelBookmarks;

  /** the button for showing/hiding the bookmarks. */
  protected JButton m_ButtonBookmarks;

  /**
   * Creates a BaseDirectoryChooser pointing to the user's home directory.
   */
  public BaseDirectoryChooser() {
    super();
    initialize();
  }

  /**
   * Creates a BaseDirectoryChooser using the given File as the path.
   *
   * @param currentDirectory	the directory to start in
   */
  public BaseDirectoryChooser(File currentDirectory) {
    super(currentDirectory.getAbsoluteFile());
    initialize();
  }

  /**
   * Creates a BaseDirectoryChooser using the given current directory and
   * FileSystemView.
   *
   * @param currentDirectory	the directory to start in
   * @param fsv			the view to use
   */
  public BaseDirectoryChooser(File currentDirectory, FileSystemView fsv) {
    super(currentDirectory.getAbsoluteFile(), fsv);
    initialize();
  }

  /**
   * Creates a BaseDirectoryChooser using the given FileSystemView.
   *
   * @param fsv			the view to use
   */
  public BaseDirectoryChooser(FileSystemView fsv) {
    super(fsv);
    initialize();
  }

  /**
   * Creates a BaseDirectoryChooser using the given path.
   *
   * @param currentDirectoryPath	the directory to start in
   */
  public BaseDirectoryChooser(String currentDirectoryPath) {
    super(new File(currentDirectoryPath).getAbsolutePath());
    initialize();
  }

  /**
   * Creates a BaseDirectoryChooser using the given path and FileSystemView.
   *
   * @param currentDirectoryPath	the directory to start in
   * @param fsv				the view to use
   */
  public BaseDirectoryChooser(String currentDirectoryPath, FileSystemView fsv) {
    super(new File(currentDirectoryPath).getAbsolutePath(), fsv);
    initialize();
  }

  /**
   * For initializing some stuff.
   * <br><br>
   * Default implementation does nothing.
   */
  protected void initialize() {
    JComponent		accessory;

    setRecentListVisible(false);

    accessory = createAccessoryPanel();
    if (accessory != null)
      setAccessory(accessory);

    showBookmarks(false);
    setPreferredSize(new Dimension(400, 500));
  }

  /**
   * Creates an accessory panel displayed next to the files.
   *
   * @return		the panel or null if none available
   */
  protected JComponent createAccessoryPanel() {
    JPanel	result;
    JPanel	panel;

    result = new JPanel(new BorderLayout());

    m_ButtonBookmarks = new JButton(GUIHelper.getIcon("arrow-up.png"));
    m_ButtonBookmarks.setBorder(BorderFactory.createEmptyBorder());
    m_ButtonBookmarks.setPreferredSize(new Dimension(18, 18));
    m_ButtonBookmarks.setBorderPainted(false);
    m_ButtonBookmarks.setContentAreaFilled(false);
    m_ButtonBookmarks.setFocusPainted(false);
    m_ButtonBookmarks.addActionListener((ActionEvent e) -> showBookmarks(!m_PanelBookmarks.isVisible()));

    panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    panel.add(m_ButtonBookmarks);
    result.add(panel, BorderLayout.NORTH);

    m_PanelBookmarks = new FileChooserBookmarksPanel();
    m_PanelBookmarks.setOwner(this);
    m_PanelBookmarks.setBorder(BorderFactory.createEmptyBorder(2, 5, 0, 0));

    result.add(m_PanelBookmarks, BorderLayout.CENTER);

    return result;
  }

  /**
   * Either displays or hides the bookmarks.
   *
   * @param value	true if to show bookmarks
   */
  protected void showBookmarks(boolean value) {
    m_PanelBookmarks.setVisible(value);
    if (m_PanelBookmarks.isVisible())
      m_ButtonBookmarks.setIcon(GUIHelper.getIcon("arrow-up.png"));
    else
      m_ButtonBookmarks.setIcon(GUIHelper.getIcon("arrow-down.png"));
  }

  /**
   * Does nothing.
   *
   * @param filter	ignored
   */
  @Override
  public void addChoosableFileFilter(FileFilter filter) {
  }

  /**
   * Sets the selected file. If the file's parent directory is
   * not the current directory, changes the current directory
   * to be the file's parent directory.
   *
   * @param file the selected file
   * @see #getSelectedFile
   */
  @Override
  public void setSelectedFile(File file) {
    File	selFile;

    selFile = null;

    if (file != null)
      selFile = new File(file.getAbsolutePath());

    super.setSelectedFile(selFile);
  }

  /**
   * Sets the list of selected files if the file chooser is
   * set to allow multiple selection.
   *
   * @param selectedFiles	the files to select initially
   */
  @Override
  public void setSelectedFiles(File[] selectedFiles) {
    File[]	files;
    int		i;

    files = null;
    if (selectedFiles != null) {
      files = new File[selectedFiles.length];
      for (i = 0; i < selectedFiles.length; i++)
	files[i] = new File(selectedFiles[i].getAbsolutePath());
    }

    super.setSelectedFiles(files);
  }

  /**
   * Returns the current directory.
   *
   * @return the current directory, as File
   * @see #setCurrentDirectory
   */
  @Override
  public File getCurrentDirectory() {
    File	current;

    current = super.getCurrentDirectory();
    if (current == null)
      return null;
    else
      return current;
  }

  /**
   * Sets the current directory. Passing in <code>null</code> sets the
   * file chooser to point to the user's default directory.
   * This default depends on the operating system. It is
   * typically the "My Documents" folder on Windows, and the user's
   * home directory on Unix.
   *
   * If the file passed in as <code>currentDirectory</code> is not a
   * directory, the parent of the file will be used as the currentDirectory.
   * If the parent is not traversable, then it will walk up the parent tree
   * until it finds a traversable directory, or hits the root of the
   * file system.
   *
   * @param dir the current directory to point to
   * @see #getCurrentDirectory
   */
  @Override
  public void setCurrentDirectory(File dir) {
    if (dir == null)
      super.setCurrentDirectory(null);
    else
      super.setCurrentDirectory(dir.getAbsoluteFile());
  }

  /**
   * For testing only.
   *
   * @param args	ignored
   */
  public static void main(String[] args) {
    BaseDirectoryChooser chooser = new BaseDirectoryChooser();
    chooser.setCurrentDirectory(new File("${TMP}"));
    if (chooser.showOpenDialog(null) == BaseDirectoryChooser.APPROVE_OPTION)
      System.out.println(chooser.getSelectedFile());
  }
}
