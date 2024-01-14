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
 * ImagePanel.java
 * Copyright (C) 2015-2024 FracPete (fracpete at gmail dot com)
 */

package com.github.fracpete.screencast4j.gui;

import nz.ac.waikato.cms.gui.core.BasePanel;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Panel for displaying an image.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class ImagePanel
  extends BasePanel {

  private static final long serialVersionUID = 4801801306532755340L;

  /**
   * The panel that draws the image.
   *
   * @author FracPete (fracpete at waikato dot ac dot nz)
   */
  public static class PaintablePanel
    extends JPanel {

    private static final long serialVersionUID = 470562883350529378L;

    /** the owner. */
    protected ImagePanel m_Owner;

    /**
     * Initializes the panel.
     *
     * @param owner	the owning panel
     */
    public PaintablePanel(ImagePanel owner) {
      super();
      m_Owner = owner;
    }

    /**
     * Paints the image or just a white background.
     *
     * @param g		the graphics context
     */
    @Override
    public void paint(Graphics g) {
      g.setColor(getBackground());
      g.fillRect(0, 0, getWidth(), getHeight());

      if (m_Owner.getImage() != null) {
	if (m_Owner.getScale()) {
	  // TODO
	  //((Graphics2D) g).scale(m_Scale, m_Scale);
	}
	g.drawImage(m_Owner.getImage(), 0, 0, getBackground(), null);
      }
    }
  }

  /** the default unit increment for the scrollbars. */
  public final static int UNIT_INCREMENT = 20;

  /** the default block increment for the scrollbars. */
  public final static int BLOCK_INCREMENT = 100;

  /** the current image. */
  protected BufferedImage m_Image;

  /** whether to scale the image. */
  protected boolean m_Scale;

  /** for displaying the coordinates. */
  protected JLabel m_LabelCoordinates;

  /** the panel that draws the image. */
  protected PaintablePanel m_PaintPanel;

  /** the scroll pane. */
  protected JScrollPane m_ScrollPane;

  /**
   * Initializes the members.
   */
  @Override
  protected void initialize() {
    super.initialize();

    m_Image = null;
  }

  /**
   * Initializes the widgets.
   */
  @Override
  protected void initGUI() {
    JPanel	panel;

    super.initGUI();

    setLayout(new BorderLayout());

    m_PaintPanel = new PaintablePanel(this);
    m_ScrollPane = new JScrollPane(m_PaintPanel);
    m_ScrollPane.getVerticalScrollBar().setUnitIncrement(UNIT_INCREMENT);
    m_ScrollPane.getHorizontalScrollBar().setUnitIncrement(UNIT_INCREMENT);
    m_ScrollPane.getVerticalScrollBar().setBlockIncrement(BLOCK_INCREMENT);
    m_ScrollPane.getHorizontalScrollBar().setBlockIncrement(BLOCK_INCREMENT);
    m_ScrollPane.setWheelScrollingEnabled(true);
    add(m_ScrollPane, BorderLayout.CENTER);

    panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    m_LabelCoordinates = new JLabel(" ");
    panel.add(m_LabelCoordinates);
    add(panel, BorderLayout.SOUTH);

    m_PaintPanel.addMouseMotionListener(new MouseAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
	updateCoordinates(e.getPoint());
	super.mouseMoved(e);
      }
    });
  }

  /**
   * Sets the image to display.
   *
   * @param value	the image
   */
  public void setImage(BufferedImage value) {
    Dimension	dim;

    m_Image = value;

    if (m_Image != null) {
      dim = new Dimension(value.getWidth(), value.getHeight());
      m_PaintPanel.setSize(dim);
      m_PaintPanel.setPreferredSize(dim);
      m_PaintPanel.setMinimumSize(dim);
    }

    repaint();
  }

  /**
   * Returns the current image on display.
   *
   * @return		the image, null if none available
   */
  public BufferedImage getImage() {
    return m_Image;
  }

  /**
   * Sets whether to scale the image, ie resize to fit panel.
   *
   * @param value	true if to make it fit the panel
   */
  public void setScale(boolean value) {
    m_Scale = value;
    repaint();
  }

  /**
   * Returns whether the image is scaled, ie resized to fit the panel.
   *
   * @return		true if scaled to fit panel
   */
  public boolean getScale() {
    return m_Scale;
  }

  /**
   * Updates the coordinates.
   */
  protected void updateCoordinates(Point pos) {
    m_LabelCoordinates.setText(
      "X: " + (int) pos.getX() + "   " + "Y: " + (int) pos.getY());
  }
}
