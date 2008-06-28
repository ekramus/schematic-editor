package cz.cvut.fel.schematicEditor.application.guiElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.application.Gui;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.propertiesToolBar.ContourCheckBoxListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.propertiesToolBar.ContourColorAlphaSliderChangeListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.propertiesToolBar.ContourColorButtonActionListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.propertiesToolBar.FillCheckBoxListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.propertiesToolBar.FillColorAlphaSliderChangeListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.propertiesToolBar.FillColorButtonActionListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.propertiesToolBar.LineWidthComboBoxActionListener;
import cz.cvut.fel.schematicEditor.application.guiElements.resources.PropertiesToolBarResources;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.core.coreStructures.ElementProperties;
import cz.cvut.fel.schematicEditor.core.coreStructures.ElementStyle;

/**
 * This class implements properties tool bar.
 *
 * @author Urban Kravjansky
 */
public class PropertiesToolBar extends JToolBar {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger            logger;
    /**
     * Singleton instance of {@link PropertiesToolBar}.
     */
    private static PropertiesToolBar propertiesToolBar       = null;

    /**
     * Line width {@link JComboBox} instance.
     */
    private JComboBox                lineWidthComboBox       = null;
    /**
     * Line width {@link JPanel} instance.
     */
    private JPanel                   lineWidthPanel          = null;
    /**
     * Line width predefined values.
     */
    private static final String[]    LINE_WIDTH              = { "1 px", "2 px", "3 px", "4 px",
            "5 px", "6 px", "7 px", "8 px", "9 px", "10 px" };
    /**
     * Value for maximum alpha value (minimal transparency).
     */
    private static final int         ALPHA_MAX               = 255;
    /**
     * Value for minimum alpha value (maximal transparency).
     */
    private static final int         ALPHA_MIN               = 0;

    /**
     * Contour {@link JCheckBox} instance.
     */
    private JCheckBox                contourCheckBox         = null;

    /**
     * Contour color {@link JButton} instance.
     */
    private JButton                  contourColorButton      = null;
    /**
     * Contour color alpha {@link JSlider} instance.
     */
    private JSlider                  contourColorAlphaSlider = null;
    /**
     * Contour color {@link JPanel} instance.
     */
    private JPanel                   contourColorPanel       = null;

    /**
     * Fill {@link JCheckBox} instance.
     */
    private JCheckBox                fillCheckBox            = null;

    /**
     * Fill color {@link JButton} instance.
     */
    private JButton                  fillColorButton         = null;
    /**
     * Fill color alpha {@link JSlider} instance.
     */
    private JSlider                  fillColorAlphaSlider    = null;
    /**
     * Fill color {@link JPanel} instance.
     */
    private JPanel                   fillColorPanel          = null;

    /**
     * Default constructor. It is private for {@link PropertiesToolBar} singleton instance.
     */
    private PropertiesToolBar() {
        super();

        logger = Logger.getLogger(Gui.class.getName());
    }

    /**
     * Getter for {@link PropertiesToolBar} singleton instance.
     *
     * @return {@link PropertiesToolBar} singleton instance.
     */
    public static PropertiesToolBar getInstance() {
        if (propertiesToolBar == null) {
            propertiesToolBar = new PropertiesToolBar();
            propertiesToolBar.setLayout(new BoxLayout(propertiesToolBar, BoxLayout.Y_AXIS));

            // add elements
            propertiesToolBar.add(propertiesToolBar.getLineWidthPanel());
            propertiesToolBar.add(propertiesToolBar.getContourCheckBox());
            propertiesToolBar.add(propertiesToolBar.getContourColorPanel());
            propertiesToolBar.add(propertiesToolBar.getFillCheckBox());
            propertiesToolBar.add(propertiesToolBar.getFillColorPanel());
        }
        return propertiesToolBar;
    }

    /**
     * Refresh {@link PropertiesToolBar} according to scene or selected element properties.
     */
    public static void refresh() {
        ElementProperties ep;

        if (Structures.getSceneProperties().getSelectedElementProperties() == null) {
            ep = Structures.getSceneProperties().getSceneElementProperties();
        } else {
            ep = Structures.getSceneProperties().getSelectedElementProperties();
        }

        propertiesToolBar.getLineWidthComboBox().setSelectedItem(
                                                                 String.valueOf(ep.getContourLineWidth()));
        propertiesToolBar.getContourCheckBox().setSelected(
                                                           ep.getContourStyle() == ElementStyle.NONE
                                                                                                    ? false
                                                                                                    : true);
        propertiesToolBar.getFillCheckBox().setSelected(
                                                        ep.getFillStyle() == ElementStyle.NONE
                                                                                              ? false
                                                                                              : true);
        propertiesToolBar.getContourColorButton().setIcon(
                                                          getColorIcon(ep.getContourColor(),
                                                                       ep.getContourColorAlpha()));
        propertiesToolBar.getFillColorButton().setIcon(
                                                       getColorIcon(ep.getFillColor(),
                                                                    ep.getFillColorAlpha()));
        propertiesToolBar.getContourColorAlphaSlider().setValue(ep.getContourColorAlpha());
        propertiesToolBar.getFillColorAlphaSlider().setValue(ep.getFillColorAlpha());

        logger.debug("Contour style: " + ep.getContourStyle());
    }

    /**
     * Getter for <code>lineWidthComboBox</code>.
     *
     * @return <code>lineWidthComboBox</code> instance.
     */
    private final JPanel getLineWidthPanel() {
        if (this.lineWidthPanel == null) {
            // create JPanel instance
            this.lineWidthPanel = new JPanel();

            // TODO add JLabel instance

            // add lineWidthComboBox into lineWidthPanel
            this.lineWidthPanel.add(getLineWidthComboBox());
        }
        return this.lineWidthPanel;
    }

    private JCheckBox getContourCheckBox() {
        if (this.contourCheckBox == null) {
            this.contourCheckBox = new JCheckBox(
                    PropertiesToolBarResources.CONTOUR_CHECK_BOX.getText());
            this.contourCheckBox.setSelected(true);
            this.contourCheckBox.addActionListener(new ContourCheckBoxListener(this.contourCheckBox));
        }
        return contourCheckBox;
    }

    /**
     * Getter for <code>contourColorPanel</code>.
     *
     * @return <code>contourColorPanel</code> instance.
     */
    private final JPanel getContourColorPanel() {
        if (this.contourColorPanel == null) {
            // create JPanel instance
            this.contourColorPanel = new JPanel();

            // TODO add JLabel instance

            // add all into contourColorPanel
            this.contourColorPanel.add(getContourColorButton());
            this.contourColorPanel.add(getContourColorAlphaSlider());
        }
        return this.contourColorPanel;
    }

    /**
     * Getter for <code>contourColorAlphaSlider</code>.
     *
     * @return <code>contourColorAlphaSlider</code> instance.
     */
    private JSlider getContourColorAlphaSlider() {
        if (this.contourColorAlphaSlider == null) {
            this.contourColorAlphaSlider = new JSlider(ALPHA_MIN, ALPHA_MAX);
            this.contourColorAlphaSlider.setValue(ALPHA_MAX);
            this.contourColorAlphaSlider.setPreferredSize(new Dimension(60, 20));
            this.contourColorAlphaSlider.addChangeListener(new ContourColorAlphaSliderChangeListener(
                    this.contourColorAlphaSlider));
        }
        return this.contourColorAlphaSlider;
    }

    private JCheckBox getFillCheckBox() {
        if (this.fillCheckBox == null) {
            this.fillCheckBox = new JCheckBox(PropertiesToolBarResources.FILL_CHECK_BOX.getText());
            this.fillCheckBox.setSelected(true);
            this.fillCheckBox.addActionListener(new FillCheckBoxListener(this.fillCheckBox));
        }
        return fillCheckBox;
    }

    /**
     * Getter for <code>fillColorAlphaSlider</code>.
     *
     * @return <code>fillColorAlphaSlider</code> instance.
     */
    private JSlider getFillColorAlphaSlider() {
        if (this.fillColorAlphaSlider == null) {
            this.fillColorAlphaSlider = new JSlider(ALPHA_MIN, ALPHA_MAX);
            this.fillColorAlphaSlider.setValue(ALPHA_MAX);
            this.fillColorAlphaSlider.setPreferredSize(new Dimension(60, 20));
            this.fillColorAlphaSlider.addChangeListener(new FillColorAlphaSliderChangeListener(
                    this.fillColorAlphaSlider));
        }
        return this.fillColorAlphaSlider;
    }

    /**
     * Getter for <code>fillColorPanel</code>.
     *
     * @return <code>fillColorPanel</code> instance.
     */
    private final JPanel getFillColorPanel() {
        if (this.fillColorPanel == null) {
            // create JPanel instance
            this.fillColorPanel = new JPanel();

            // TODO add JLabel instance

            // add all into JPanel
            this.fillColorPanel.add(getFillColorButton());
            this.fillColorPanel.add(getFillColorAlphaSlider());
        }
        return this.fillColorPanel;
    }

    /**
     * Getter for <code>lineWidthComboBox</code>.
     *
     * @return <code>lineWidthComboBox</code> instance.
     */
    private final JComboBox getLineWidthComboBox() {
        if (this.lineWidthComboBox == null) {
            this.lineWidthComboBox = new JComboBox(LINE_WIDTH);
            this.lineWidthComboBox.setEditable(true);
            this.lineWidthComboBox.addActionListener(new LineWidthComboBoxActionListener(
                    this.lineWidthComboBox));
        }
        return this.lineWidthComboBox;
    }

    /**
     * Getter for <code>contourColorButton</code>.
     *
     * @return <code>contourColorButton</code> instance.
     */
    private final JButton getContourColorButton() {
        if (this.contourColorButton == null) {
            this.contourColorButton = new JButton();
            this.contourColorButton.setText(PropertiesToolBarResources.CONTOUR_COLOR_BTN.getText());
            this.contourColorButton.setIcon(getColorIcon(Structures.getSceneProperties().getSceneElementProperties().getContourColor(),
                                                         Structures.getSceneProperties().getSceneElementProperties().getContourColorAlpha()));
            this.contourColorButton.addActionListener(new ContourColorButtonActionListener(
                    this.contourColorButton));
        }
        return this.contourColorButton;
    }

    /**
     * Getter for <code>fillColorButton</code>.
     *
     * @return <code>fillColorButton</code> instance.
     */
    private JButton getFillColorButton() {
        if (this.fillColorButton == null) {
            this.fillColorButton = new JButton(PropertiesToolBarResources.FILL_COLOR_BTN.getText());
            this.fillColorButton.setIcon(getColorIcon(Structures.getSceneProperties().getSceneElementProperties().getFillColor(),
                                                      Structures.getSceneProperties().getSceneElementProperties().getFillColorAlpha()));
            this.fillColorButton.addActionListener(new FillColorButtonActionListener(
                    this.fillColorButton));
        }
        return this.fillColorButton;
    }

    /**
     * Getter for image representing selected color on button. TODO Should be moved into some
     * adequate package.
     *
     * @param color
     *            color to be present on image.
     * @param alpha
     *            alpha of given color.
     * @return Image with given color.
     */
    @Deprecated
    public static ImageIcon getColorIcon(Color color, int alpha) {
        BufferedImage result = new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = (Graphics2D) result.getGraphics();

        // draw transparent triangles
        g2d.setColor(Color.WHITE);
        int[] xP1 = { 0, 25, 25 };
        int[] yP1 = { 0, 0, 25 };
        g2d.fillPolygon(xP1, yP1, 3);
        g2d.setColor(Color.BLACK);
        int[] xP2 = { 0, 25, 25 };
        int[] yP2 = { 25, 25, 25 };
        g2d.fillPolygon(xP2, yP2, 3);

        Color c = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
        g2d.setColor(c);
        g2d.fillRect(0, 0, 25, 25);

        return new ImageIcon(result);
    }
}
