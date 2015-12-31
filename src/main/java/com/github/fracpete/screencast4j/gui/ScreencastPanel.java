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
import com.github.fracpete.screencast4j.record.screen.ScreenRecorder;
import com.github.fracpete.screencast4j.record.screen.XuggleScreenRecorder;
import com.github.fracpete.screencast4j.record.sound.SampledSoundRecorder;
import com.github.fracpete.screencast4j.record.sound.SoundRecorder;
import com.github.fracpete.screencast4j.record.webcam.SarxosWebcamRecorder;
import com.github.fracpete.screencast4j.record.webcam.WebcamRecorder;
import com.github.sarxos.webcam.Webcam;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ScreencastPanel
  extends BasePanel {

  private static final long serialVersionUID = -1323993618955317051L;

  public static final String HOMEPAGE = "https://github.com/fracpete/screencast4j";

  public static final String SIZE_SEPARATOR = " x ";

  public static final String SUFFIX_SOUND = "-sound";

  public static final String SUFFIX_WEBCAM = "-webcam";

  public static final String SUFFIX_SCREEN = "-screen";

  protected static boolean PREVIEW_ENABLED = false;

  /** directory chooser for selecting the project. */
  protected JFileChooser m_DirChooser;

  /** the menubar. */
  protected JMenuBar m_MenuBar;

  /** the "record" menu item. */
  protected JMenuItem m_MenuItemRecord;

  /** the "pause/resume" menu item. */
  protected JMenuItem m_MenuItemPauseResume;

  /** the "stop" menu item. */
  protected JMenuItem m_MenuItemStop;

  /** the panel with the basic options. */
  protected JPanel m_PanelBasic;

  /** the "project name". */
  protected JTextField m_TextProjectName;

  /** the text field for the output directory. */
  protected JTextField m_TextOutputDir;

  /** the button for selecting the output directory. */
  protected JButton m_ButtonOutputDir;

  /** the checkbox for the sound. */
  protected JCheckBox m_CheckBoxSound;

  /** the checkbox for the webcam. */
  protected JCheckBox m_CheckBoxWebcam;

  /** the checkbox for the screen. */
  protected JCheckBox m_CheckBoxScreen;

  /** the tabbed pane with the setup. */
  protected JTabbedPane m_TabbedPane;

  /** the panel for the sound options. */
  protected JPanel m_PanelSound;

  /** the input for the sound frequency. */
  protected JTextField m_TextSoundFrequency;

  /** the panel for the webcam options. */
  protected JPanel m_PanelWebcam;

  /** the combobox of the available webcams. */
  protected JComboBox m_ComboBoxWebcamAvailable;

  /** the combobox model for the available webcams. */
  protected DefaultComboBoxModel<String> m_ModelWebcamAvailable;

  /** the button for refreshing the webcams. */
  protected JButton m_ButtonWebcamRefresh;

  /** the combobox of the sizes available for the selected webcam. */
  protected JComboBox m_ComboBoxWebcamSizes;

  /** the combobox model for the sizes available for the selected webcam. */
  protected DefaultComboBoxModel<String> m_ModelWebcamSizes;

  /** the frames per second for the webcam. */
  protected JSpinner m_SpinnerWebcamFPS;

  /** the preview for the webcam. */
  protected PreviewPanel m_PanelWebcamPreview;

  /** the panel for the screen options. */
  protected JPanel m_PanelScreen;

  /** the input for the X position. */
  protected JSpinner m_SpinnerScreenX;

  /** the input for the Y position. */
  protected JSpinner m_SpinnerScreenY;

  /** the input for the width. */
  protected JSpinner m_SpinnerScreenWidth;

  /** the input for the height. */
  protected JSpinner m_SpinnerScreenHeight;

  /** whether to capture the mouse cursor. */
  protected JCheckBox m_CheckBoxScreenCaptureMouse;

  /** the frames per second for the screen. */
  protected JSpinner m_SpinnerScreenFPS;

  /** the preview for the screen. */
  protected PreviewPanel m_PanelScreenPreview;

  /** the current recorder. */
  protected MultiRecorder m_Recorder;

  /** the "new" counter. */
  protected int m_NewCounter;

  /**
   * Initializes the members.
   */
  @Override
  protected void initialize() {
    super.initialize();

    m_DirChooser = createDirectoryChooser();
    m_Recorder   = new MultiRecorder();
    m_NewCounter = 0;
  }

  /**
   * Initializes the basic options.
   */
  protected void initGUIBasic() {
    JPanel		panel;
    JPanel		panel2;
    JPanel		panelAll;
    JLabel		label;
    List<JLabel>	labels;

    labels   = new ArrayList<>();
    panelAll = new JPanel(new GridLayout(5, 1));
    panelAll.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    m_PanelBasic.add(panelAll, BorderLayout.CENTER);

    // project name
    panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    m_TextProjectName = new JTextField(20);
    label = new JLabel("Project name ");
    label.setDisplayedMnemonic('P');
    label.setLabelFor(m_TextProjectName);
    labels.add(label);
    panel.add(label);
    panel.add(m_TextProjectName);
    panelAll.add(panel);

    // output dir
    panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    panel2 = new JPanel(new BorderLayout(0, 0));
    m_TextOutputDir = new JTextField(20);
    m_TextOutputDir.setEditable(false);
    m_TextOutputDir.setText(".");
    m_ButtonOutputDir = new JButton("...");
    m_ButtonOutputDir.addActionListener((ActionEvent e) -> selectOutputDir());
    label = new JLabel("Output directory ");
    label.setDisplayedMnemonic('d');
    label.setLabelFor(m_TextOutputDir);
    labels.add(label);
    panel2.add(label, BorderLayout.WEST);
    panel2.add(m_TextOutputDir, BorderLayout.CENTER);
    panel2.add(m_ButtonOutputDir, BorderLayout.EAST);
    panel2.setPreferredSize(new Dimension(500, 20));
    panel.add(panel2);
    panelAll.add(panel);

    // things to record
    m_CheckBoxSound = new JCheckBox("Sound");
    m_CheckBoxSound.setSelected(true);
    m_CheckBoxSound.addActionListener((ActionEvent e) -> updateTabs());
    panelAll.add(m_CheckBoxSound);

    m_CheckBoxWebcam = new JCheckBox("Webcam");
    m_CheckBoxWebcam.setSelected(true);
    m_CheckBoxWebcam.addActionListener((ActionEvent e) -> updateTabs());
    panelAll.add(m_CheckBoxWebcam);

    m_CheckBoxScreen = new JCheckBox("Screen");
    m_CheckBoxScreen.setSelected(true);
    m_CheckBoxScreen.addActionListener((ActionEvent e) -> updateTabs());
    panelAll.add(m_CheckBoxScreen);

    // fix label sizes
    panelAll.doLayout();
    GUIHelper.adjustLabelSizes(labels);
  }

  /**
   * Initializes the sound tab.
   */
  protected void initGUISound() {
    JPanel	panel;
    JPanel	panel2;
    JLabel	label;

    m_TextSoundFrequency = new JTextField(10);
    label = new JLabel("Frequency");
    label.setDisplayedMnemonic('q');
    label.setLabelFor(m_TextSoundFrequency);
    panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.add(label);
    panel.add(m_TextSoundFrequency);
    panel2 = new JPanel(new GridLayout(1, 1));
    panel2.add(panel);

    m_PanelSound.add(panel2, BorderLayout.NORTH);
  }

  /**
   * Initializes the webcam tab.
   */
  protected void initGUIWebcam() {
    JPanel		panel;
    JPanel		panel2;
    JLabel		label;
    List<JLabel>	labels;

    labels = new ArrayList<>();
    panel2 = new JPanel(new GridLayout(3, 1));

    // webcams
    m_ModelWebcamAvailable    = new DefaultComboBoxModel<>();
    m_ComboBoxWebcamAvailable = new JComboBox<>(m_ModelWebcamAvailable);
    m_ButtonWebcamRefresh = new JButton(GUIHelper.getIcon("refresh.png"));
    m_ButtonWebcamRefresh.addActionListener((ActionEvent e) -> refreshWebcams());
    m_ComboBoxWebcamAvailable.addActionListener((ActionEvent e) -> refreshWebcamSizes());
    label = new JLabel("Webcam");
    label.setDisplayedMnemonic('c');
    label.setLabelFor(m_CheckBoxWebcam);
    labels.add(label);
    panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.add(label);
    panel.add(m_ComboBoxWebcamAvailable);
    panel.add(m_ButtonWebcamRefresh);
    panel2.add(panel);

    // sizes
    m_ModelWebcamSizes    = new DefaultComboBoxModel<>();
    m_ComboBoxWebcamSizes = new JComboBox<>(m_ModelWebcamSizes);
    m_ComboBoxWebcamSizes.addActionListener((ActionEvent e) -> updatePreviewRecorders());
    label = new JLabel("Size");
    label.setLabelFor(m_ComboBoxWebcamSizes);
    labels.add(label);
    panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.add(label);
    panel.add(m_ComboBoxWebcamSizes);
    panel2.add(panel);

    // FPS
    m_SpinnerWebcamFPS = new JSpinner();
    ((SpinnerNumberModel) m_SpinnerWebcamFPS.getModel()).setMinimum(1);
    ((SpinnerNumberModel) m_SpinnerWebcamFPS.getModel()).setMaximum(50);
    m_SpinnerWebcamFPS.setPreferredSize(new Dimension(100, 20));
    label = new JLabel("Frames per second");
    label.setLabelFor(m_SpinnerWebcamFPS);
    labels.add(label);
    panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.add(label);
    panel.add(m_SpinnerWebcamFPS);
    panel2.add(panel);

    m_PanelWebcam.add(panel2, BorderLayout.NORTH);

    // preview
    if (PREVIEW_ENABLED) {
      m_PanelWebcamPreview = new PreviewPanel();
      m_PanelWebcamPreview.setScale(false);
      m_PanelWebcamPreview.setRecorder(new SarxosWebcamRecorder());
      m_PanelWebcam.add(m_PanelWebcamPreview, BorderLayout.CENTER);
    }

    // fix label sizes
    m_PanelWebcam.doLayout();
    GUIHelper.adjustLabelSizes(labels);
  }

  /**
   * Initializes the screen tab.
   */
  protected void initGUIScreen() {
    JPanel		panel;
    JPanel		panel2;
    JLabel		label;
    List<JLabel>	labels;
    GraphicsDevice	device;
    Rectangle		bounds;

    labels = new ArrayList<>();
    panel2 = new JPanel(new GridLayout(6, 1));
    device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    bounds = device.getDefaultConfiguration().getBounds();

    // X
    m_SpinnerScreenX = new JSpinner();
    ((SpinnerNumberModel) m_SpinnerScreenX.getModel()).setMinimum(0);
    ((SpinnerNumberModel) m_SpinnerScreenX.getModel()).setMaximum((int) bounds.getWidth() - 2);
    m_SpinnerScreenX.setPreferredSize(new Dimension(100, 20));
    label = new JLabel("X");
    label.setLabelFor(m_SpinnerScreenX);
    labels.add(label);
    panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.add(label);
    panel.add(m_SpinnerScreenX);
    panel2.add(panel);

    // Y
    m_SpinnerScreenY = new JSpinner();
    ((SpinnerNumberModel) m_SpinnerScreenY.getModel()).setMinimum(0);
    ((SpinnerNumberModel) m_SpinnerScreenY.getModel()).setMaximum((int) bounds.getHeight() - 2);
    m_SpinnerScreenY.setPreferredSize(new Dimension(100, 20));
    label = new JLabel("Y");
    label.setLabelFor(m_SpinnerScreenY);
    labels.add(label);
    panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.add(label);
    panel.add(m_SpinnerScreenY);
    panel2.add(panel);

    // Width
    m_SpinnerScreenWidth = new JSpinner();
    ((SpinnerNumberModel) m_SpinnerScreenWidth.getModel()).setMinimum(-1);
    ((SpinnerNumberModel) m_SpinnerScreenWidth.getModel()).setMaximum((int) bounds.getWidth());
    m_SpinnerScreenWidth.setPreferredSize(new Dimension(100, 20));
    label = new JLabel("Width");
    label.setLabelFor(m_SpinnerScreenWidth);
    labels.add(label);
    panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.add(label);
    panel.add(m_SpinnerScreenWidth);
    panel2.add(panel);

    // Height
    m_SpinnerScreenHeight = new JSpinner();
    ((SpinnerNumberModel) m_SpinnerScreenHeight.getModel()).setMinimum(-1);
    ((SpinnerNumberModel) m_SpinnerScreenHeight.getModel()).setMaximum((int) bounds.getHeight());
    m_SpinnerScreenHeight.setPreferredSize(new Dimension(100, 20));
    label = new JLabel("Height");
    label.setLabelFor(m_SpinnerScreenHeight);
    labels.add(label);
    panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.add(label);
    panel.add(m_SpinnerScreenHeight);
    panel2.add(panel);

    // capture
    m_CheckBoxScreenCaptureMouse = new JCheckBox();
    m_CheckBoxScreenCaptureMouse.setSelected(true);
    label = new JLabel("Capture mouse");
    label.setLabelFor(m_CheckBoxScreenCaptureMouse);
    labels.add(label);
    panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.add(label);
    panel.add(m_CheckBoxScreenCaptureMouse);
    panel2.add(panel);

    // FPS
    m_SpinnerScreenFPS = new JSpinner();
    ((SpinnerNumberModel) m_SpinnerScreenFPS.getModel()).setMinimum(1);
    ((SpinnerNumberModel) m_SpinnerScreenFPS.getModel()).setMaximum(50);
    m_SpinnerScreenFPS.setPreferredSize(new Dimension(100, 20));
    label = new JLabel("Frames per second");
    label.setLabelFor(m_SpinnerScreenFPS);
    labels.add(label);
    panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.add(label);
    panel.add(m_SpinnerScreenFPS);
    panel2.add(panel);

    m_PanelScreen.add(panel2, BorderLayout.NORTH);

    // preview
    if (PREVIEW_ENABLED) {
      m_PanelScreenPreview = new PreviewPanel();
      m_PanelScreenPreview.setScale(false);
      m_PanelScreenPreview.setRecorder(new XuggleScreenRecorder());
      m_PanelScreen.add(m_PanelScreenPreview, BorderLayout.CENTER);
    }

    // fix label sizes
    m_PanelScreen.doLayout();
    GUIHelper.adjustLabelSizes(labels);
  }

  /**
   * Initializes the widgets.
   */
  @Override
  protected void initGUI() {
    super.initGUI();

    setLayout(new BorderLayout());

    // basic options
    m_PanelBasic = new JPanel(new BorderLayout());
    add(m_PanelBasic, BorderLayout.NORTH);
    initGUIBasic();

    // tabbed pane
    m_TabbedPane = new JTabbedPane();
    add(m_TabbedPane, BorderLayout.CENTER);

    // sound panel
    m_PanelSound = new JPanel(new BorderLayout());
    initGUISound();

    // webcam panel
    m_PanelWebcam = new JPanel(new BorderLayout());
    initGUIWebcam();

    // screen panel
    m_PanelScreen = new JPanel(new BorderLayout());
    initGUIScreen();
  }

  /**
   * Finishes the initialization.
   */
  @Override
  protected void finishInit() {
    super.finishInit();

    newRecording();
    updateTabs();
    refreshWebcams();
    updatePreviewRecorders();
  }

  /**
   * Returns the directory chooser to use.
   *
   * @return		the chooser
   */
  protected JFileChooser createDirectoryChooser() {
    return new BaseDirectoryChooser();
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
    m_MenuItemPauseResume.setEnabled(m_Recorder.canPauseAndResume() && (m_Recorder.isPaused() || m_Recorder.isRecording()));
    if (m_Recorder.isPaused()) {
      m_MenuItemPauseResume.setText("Resume");
      m_MenuItemPauseResume.setIcon(GUIHelper.getIcon("resume.png"));
    }
    else {
      m_MenuItemPauseResume.setText("Pause");
      m_MenuItemPauseResume.setIcon(GUIHelper.getIcon("pause.png"));
    }
    m_MenuItemStop.setEnabled(m_Recorder.isPaused() || m_Recorder.isRecording());
  }

  /**
   * Updates the visible tabs in the tabbed pane.
   */
  protected void updateTabs() {
    m_TabbedPane.removeAll();
    if (m_CheckBoxSound.isSelected())
      m_TabbedPane.addTab("Sound", m_PanelSound);
    if (m_CheckBoxWebcam.isSelected())
      m_TabbedPane.addTab("Webcam", m_PanelWebcam);
    if (m_CheckBoxScreen.isSelected())
      m_TabbedPane.addTab("Screen", m_PanelScreen);
  }

  /**
   * Updates the recorders of the previews.
   */
  protected void updatePreviewRecorders() {
    MultiRecorder	recorder;

    recorder = fieldsToRecorder();
    for (Recorder rec: recorder.getRecorders()) {
      if (rec instanceof WebcamRecorder) {
	if (PREVIEW_ENABLED) {
	  m_PanelWebcamPreview.setUpdate(m_ComboBoxWebcamAvailable.getSelectedIndex() > -1);
	  if (m_PanelWebcamPreview.getRecorder() != null)
	    m_PanelWebcamPreview.getRecorder().cleanUp();
	  m_PanelWebcamPreview.setRecorder((WebcamRecorder) rec);
	}
      }
      if (rec instanceof ScreenRecorder) {
	if (PREVIEW_ENABLED) {
	  m_PanelScreenPreview.setUpdate(true);
	  if (m_PanelScreenPreview.getRecorder() != null)
	    m_PanelScreenPreview.getRecorder().cleanUp();
	  m_PanelScreenPreview.setRecorder((ScreenRecorder) rec);
	}
      }
    }
  }

  /**
   * Maps the recorder setup onto the fields.
   *
   * @param rec		the recorder to map
   */
  protected void recorderToFields(MultiRecorder rec) {
    SoundRecorder	sound;
    WebcamRecorder	webcam;
    String		size;
    ScreenRecorder	screen;

    m_TextProjectName.setText("New" + m_NewCounter);

    m_CheckBoxSound.setSelected(false);
    m_CheckBoxWebcam.setSelected(false);
    m_CheckBoxScreen.setSelected(false);

    for (Recorder r: rec.getRecorders()) {
      if (r instanceof SoundRecorder) {
	sound = (SoundRecorder) r;
	m_TextSoundFrequency.setText("" + sound.getFrequency());
	m_CheckBoxSound.setSelected(true);
      }
      if (r instanceof WebcamRecorder) {
	webcam = (WebcamRecorder) r;
	if (m_ModelWebcamAvailable.getSize() > 0) {
	  if (m_ModelWebcamAvailable.getIndexOf(webcam.getWebcamID()) > -1)
	    m_ComboBoxWebcamAvailable.setSelectedIndex(m_ModelWebcamAvailable.getIndexOf(webcam.getWebcamID()));
	  else
	    m_ComboBoxWebcamAvailable.setSelectedIndex(0);
	  if (m_ModelWebcamSizes.getSize() > 0) {
	    size = (int) webcam.getSize().getWidth() + SIZE_SEPARATOR + (int) webcam.getSize().getHeight();
	    if (m_ModelWebcamSizes.getIndexOf(size) > -1)
	      m_ComboBoxWebcamSizes.setSelectedIndex(m_ModelWebcamSizes.getIndexOf(size));
	    else
	      m_ComboBoxWebcamSizes.setSelectedIndex(0);
	  }
	}
	m_SpinnerWebcamFPS.setValue(webcam.getFramesPerSecond());
	m_CheckBoxWebcam.setSelected(true);
      }
      if (r instanceof ScreenRecorder) {
	screen = (ScreenRecorder) r;
	m_SpinnerScreenX.setValue(screen.getX());
	m_SpinnerScreenY.setValue(screen.getY());
	m_SpinnerScreenWidth.setValue(screen.getWidth());
	m_SpinnerScreenHeight.setValue(screen.getHeight());
	m_CheckBoxScreenCaptureMouse.setSelected(screen.getCaptureMouse());
	m_SpinnerScreenFPS.setValue(screen.getFramesPerSecond());
	m_CheckBoxScreen.setSelected(true);
      }
    }
  }

  /**
   * Creates an output file using the specified suffix and extension.
   *
   * @param suffix	the suffix to use
   * @param ext		the extension to use (no dot)
   * @return		the generated output file
   */
  protected File createOutputFile(String suffix, String ext) {
    return new File(m_TextOutputDir.getText() + File.separator + m_TextProjectName.getText() + suffix + "." + ext);
  }

  /**
   * Turns the input fields back into a recorder.
   *
   * @return		the recorder generated from the input
   */
  protected MultiRecorder fieldsToRecorder() {
    MultiRecorder	result;
    List<Recorder> 	recorders;
    SoundRecorder	sound;
    WebcamRecorder	webcam;
    String[]		parts;
    Dimension size;
    ScreenRecorder	screen;

    result    = new MultiRecorder();
    recorders = new ArrayList<>();

    // webcam
    if (m_CheckBoxWebcam.isSelected()) {
      webcam = new SarxosWebcamRecorder();
      webcam.setOutput(createOutputFile(SUFFIX_WEBCAM, webcam.getDefaultExtension()));
      if (m_ComboBoxWebcamAvailable.getSelectedIndex() > -1)
	webcam.setWebcamID((String) m_ComboBoxWebcamAvailable.getSelectedItem());
      if (m_ComboBoxWebcamSizes.getSelectedIndex() > -1) {
	try {
	  parts = ((String) m_ComboBoxWebcamSizes.getSelectedItem()).split(SIZE_SEPARATOR);
	  if (parts.length == 2) {
	    size = new Dimension(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
	    webcam.setSize(size);
	  }
	}
	catch (Exception e) {
	  JOptionPane.showMessageDialog(this, "Invalid size: " + m_ComboBoxWebcamAvailable.getSelectedItem(), "Error", JOptionPane.ERROR_MESSAGE);
	}
      }
      webcam.setFramesPerSecond(((Number) m_SpinnerWebcamFPS.getValue()).intValue());
      recorders.add(webcam);
    }

    // sound
    if (m_CheckBoxSound.isSelected()) {
      sound = new SampledSoundRecorder();
      sound.setOutput(createOutputFile(SUFFIX_SOUND, sound.getDefaultExtension()));
      try {
	sound.setFrequency(Float.valueOf(m_TextSoundFrequency.getText()));
      }
      catch (Exception e) {
	JOptionPane.showMessageDialog(this, "Invalid frequency: " + m_TextSoundFrequency.getText(), "Error", JOptionPane.ERROR_MESSAGE);
      }
      recorders.add(sound);
    }

    // screen
    if (m_CheckBoxScreen.isSelected()) {
      screen = new XuggleScreenRecorder();
      screen.setOutput(createOutputFile(SUFFIX_SCREEN, screen.getDefaultExtension()));
      screen.setX(((Number) m_SpinnerScreenX.getValue()).intValue());
      screen.setY(((Number) m_SpinnerScreenY.getValue()).intValue());
      screen.setWidth(((Number) m_SpinnerScreenWidth.getValue()).intValue());
      screen.setHeight(((Number) m_SpinnerScreenHeight.getValue()).intValue());
      screen.setCaptureMouse(m_CheckBoxScreenCaptureMouse.isSelected());
      screen.setFramesPerSecond(((Number) m_SpinnerScreenFPS.getValue()).intValue());
      recorders.add(screen);
    }

    result.setRecorders(recorders.toArray(new Recorder[recorders.size()]));

    return result;
  }

  /**
   * Minimizes the frame.
   */
  protected void minimizeFrame() {
    if (GUIHelper.getParentFrame(this) != null)
      GUIHelper.getParentFrame(this).setExtendedState(JFrame.ICONIFIED);
  }

  /**
   * Creates a new recording.
   */
  public void newRecording() {
    m_NewCounter++;
    m_Recorder = new MultiRecorder();
    m_Recorder.setRecorders(new Recorder[]{
      new SampledSoundRecorder(),
      new SarxosWebcamRecorder(),
      new XuggleScreenRecorder()
    });
    recorderToFields(m_Recorder);
    updateMenu();
  }

  /**
   * Configures the recorder and starts the recording.
   */
  public void startRecording() {
    String	msg;

    m_Recorder = fieldsToRecorder();

    // setup
    msg = m_Recorder.setUp();
    if (msg != null) {
      JOptionPane.showMessageDialog(this, "Failed to prepare recording:\n" + msg, "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    // start
    msg = m_Recorder.start();
    if (msg != null) {
      JOptionPane.showMessageDialog(this, "Failed to start recording:\n" + msg, "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    // pause previews
    if (PREVIEW_ENABLED)
      m_PanelWebcamPreview.setUpdate(false);
    if (PREVIEW_ENABLED)
      m_PanelScreenPreview.setUpdate(false);

    minimizeFrame();
    updateMenu();
  }

  /**
   * Pauses/resumes the recording
   */
  public void pauseResumeRecording() {
    if (m_Recorder.isRecording()) {
      m_Recorder.pause();
    }
    else if (m_Recorder.isPaused()) {
      m_Recorder.resume();
      minimizeFrame();
    }
    updateMenu();
  }

  /**
   * Stops the recording.
   */
  public void stopRecording() {
    m_Recorder.stop();
    if (PREVIEW_ENABLED)
      m_PanelWebcamPreview.setUpdate(true);
    if (PREVIEW_ENABLED)
      m_PanelScreenPreview.setUpdate(true);
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
    if (m_Recorder.isRecording() || m_Recorder.isPaused())
      m_Recorder.stop();
    if (PREVIEW_ENABLED)
      m_PanelScreenPreview.stop();
    if (PREVIEW_ENABLED)
      m_PanelWebcamPreview.stop();
    GUIHelper.closeParent(this);
  }

  /**
   * Lets the user select the output directory.
   */
  protected void selectOutputDir() {
    int		retVal;

    m_DirChooser.setSelectedFile(new File(m_TextOutputDir.getText()));
    retVal = m_DirChooser.showOpenDialog(this);
    if (retVal != BaseDirectoryChooser.APPROVE_OPTION)
      return;
    m_TextOutputDir.setText(m_DirChooser.getSelectedFile().getAbsolutePath());
  }

  /**
   * Refreshes the combobox with the available
   */
  protected void refreshWebcams() {
    m_ModelWebcamAvailable.removeAllElements();
    for (Webcam webcam: Webcam.getWebcams())
      m_ModelWebcamAvailable.addElement(webcam.getName());
    if (m_ModelWebcamAvailable.getSize() > 0)
      m_ComboBoxWebcamAvailable.setSelectedIndex(0);
    updatePreviewRecorders();
  }

  /**
   * Refreshes the combobox with the available sizes for the currently selected
   * webcam.
   */
  protected void refreshWebcamSizes() {
    String 	id;

    if ((m_ModelWebcamAvailable.getSize() == 0 ) || (m_ComboBoxWebcamAvailable.getSelectedIndex() == -1))
      return;

    m_ModelWebcamSizes.removeAllElements();
    id = (String) m_ComboBoxWebcamAvailable.getSelectedItem();
    for (Webcam webcam: Webcam.getWebcams()) {
      if (id.equals(webcam.getName())) {
	for (Dimension dim: webcam.getViewSizes())
	  m_ModelWebcamSizes.addElement((int) dim.getWidth() + SIZE_SEPARATOR + (int) dim.getHeight());
	break;
      }
    }
    updatePreviewRecorders();
  }
}
