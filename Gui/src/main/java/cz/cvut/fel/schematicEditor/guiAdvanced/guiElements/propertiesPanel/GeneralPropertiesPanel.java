package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.properties.ElementProperties;
import cz.cvut.fel.schematicEditor.element.properties.ElementStyle;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.listeners.ContourCheckBoxListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.listeners.ContourColorAlphaSliderChangeListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.listeners.ContourColorButtonActionListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.listeners.FillCheckBoxListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.listeners.FillColorAlphaSliderChangeListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.listeners.FillColorButtonActionListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.listeners.LineWidthComboBoxActionListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.resources.PropertiesToolBarResources;

/**
 * This class implements properties tool bar.
 *
 * @author Urban Kravjansky
 */
public class GeneralPropertiesPanel extends JPanel {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger                 logger;
    /**
     * Singleton instance of {@link GeneralPropertiesPanel}.
     */
    private static GeneralPropertiesPanel propertiesToolBar       = null;
    /**
     * Size of subpanels.
     */
    private static final Dimension        DIM                     = new Dimension(200, 40);

    /**
     * Contour width {@link JComboBox} instance.
     */
    private JComboBox                     contourWidthComboBox    = null;
    /**
     * Contour width {@link JPanel} instance.
     */
    private final JPanel                  contourWidthPanel       = null;
    /**
     * Contour width predefined values.
     */
    private static final String[]         CONTOUR_WIDTH           = { "1 px",
            "2 px",
            "3 px",
            "4 px",
            "5 px",
            "6 px",
            "7 px",
            "8 px",
            "9 px",
            "10 px"                                              };
    /**
     * Value for maximum alpha value (minimal transparency).
     */
    private static final int              ALPHA_MAX               = 255;
    /**
     * Value for minimum alpha value (maximal transparency).
     */
    private static final int              ALPHA_MIN               = 0;

    /**
     * Contour {@link JCheckBox} instance.
     */
    private JCheckBox                     contourCheckBox         = null;

    /**
     * Contour color {@link JButton} instance.
     */
    private JButton                       contourColorButton      = null;
    /**
     * Contour color alpha {@link JSlider} instance.
     */
    private JSlider                       contourColorAlphaSlider = null;
    /**
     * Contour color {@link JPanel} instance.
     */
    private final JPanel                  contourColorPanel       = null;

    /**
     * Fill {@link JCheckBox} instance.
     */
    private JCheckBox                     fillCheckBox            = null;

    /**
     * Fill color {@link JButton} instance.
     */
    private JButton                       fillColorButton         = null;
    /**
     * Fill color alpha {@link JSlider} instance.
     */
    private JSlider                       fillColorAlphaSlider    = null;
    /**
     * Fill color {@link JPanel} instance.
     */
    private final JPanel                  fillColorPanel          = null;
    /**
     * Name panel text field instance.
     */
    private JTextField                    namePanelTextField      = null;
    /**
     * Contour {@link JPanel} instance.
     */
    private JPanel                        contourPanel            = null;
    /**
     * Fill {@link JPanel} instance.
     */
    private JPanel                        fillPanel               = null;

    /**
     * Default constructor. It is private for {@link GeneralPropertiesPanel} singleton instance.
     */
    private GeneralPropertiesPanel() {
        super();

        logger = Logger.getLogger(Gui.class.getName());
    }

    /**
     * Getter for <code>contourPanel</code>.
     *
     * @return <code>contourPanel</code> instance.
     */
    private final JPanel getContourPanel() {
        if (this.contourPanel == null) {
            // create and set JPanel instance
            this.contourPanel = new JPanel();
            this.contourPanel.setBorder(BorderFactory.createTitledBorder("Contour"));

            // attach MiG layout to panel
            this.contourPanel.setLayout(new MigLayout("wrap 2"));

            // add components in left to right order
            this.contourPanel.add(getContourColorButton());
            this.contourPanel.add(getContourColorAlphaSlider(), "width 50:100:150");
            this.contourPanel.add(new JLabel("contour width:"));
            this.contourPanel.add(getContourWidthComboBox());
        }
        return this.contourPanel;
    }

    /**
     * Getter for <code>fillPanel</code>.
     *
     * @return <code>fillPanel</code> instance.
     */
    private final JPanel getFillPanel() {
        if (this.fillPanel == null) {
            // create and set JPanel instance
            this.fillPanel = new JPanel();
            this.fillPanel.setBorder(BorderFactory.createTitledBorder("Fill"));

            // attach MiG layout to panel
            this.fillPanel.setLayout(new MigLayout("wrap 2"));

            // add components in left to right order
            this.fillPanel.add(getFillColorButton());
            this.fillPanel.add(getFillColorAlphaSlider(), "width 50:100:150");
        }
        return this.fillPanel;
    }

    /**
     * Getter for {@link GeneralPropertiesPanel} singleton instance.
     *
     * @return {@link GeneralPropertiesPanel} singleton instance.
     */
    public static GeneralPropertiesPanel getInstance() {
        if (propertiesToolBar == null) {
            propertiesToolBar = new GeneralPropertiesPanel();
            // propertiesToolBar.setLayout(new BoxLayout(propertiesToolBar, BoxLayout.PAGE_AXIS));
            propertiesToolBar.setLayout(new MigLayout("wrap 1"));
            propertiesToolBar.setPreferredSize(new Dimension(210, 0));

            // add elements
            propertiesToolBar.add(Box.createVerticalStrut(20));
            propertiesToolBar.add(propertiesToolBar.getContourPanel());
            propertiesToolBar.add(propertiesToolBar.getFillPanel());
            propertiesToolBar.add(new JLabel("Fill"));
            propertiesToolBar.add(propertiesToolBar.getFillCheckBox());
            propertiesToolBar.add(propertiesToolBar.getNamePanel());
        }
        return propertiesToolBar;
    }

    /**
     * @return
     */
    private JPanel getNamePanel() {
        JPanel result = new JPanel();

        JLabel label = new JLabel("Name: ");

        // add components
        result.add(label);
        result.add(getNamePanelTextField());

        return result;
    }

    /**
     * Getter for text filed on name panel.
     *
     * @return
     */
    private JTextField getNamePanelTextField() {
        if (this.namePanelTextField == null) {
            this.namePanelTextField = new JTextField();
            this.namePanelTextField.setEditable(false);
            this.namePanelTextField.setPreferredSize(new Dimension(100, 20));
        }
        return this.namePanelTextField;
    }

    /**
     * Refresh {@link GeneralPropertiesPanel} according to scene or selected element properties.
     */
    public void refresh() {
        ElementProperties ep;

        if (Gui.getActiveScenePanel().getSceneProperties().getSelectedElementProperties() == null) {
            ep = Gui.getActiveScenePanel().getSceneProperties().getSceneElementProperties();
        } else {
            ep = Gui.getActiveScenePanel().getSceneProperties().getSelectedElementProperties();
        }

        getContourWidthComboBox().setSelectedItem(String.valueOf(ep.getContourLineWidth()));
        getContourCheckBox().setSelected(ep.getContourStyle() == ElementStyle.NONE ? false : true);
        getFillCheckBox().setSelected(ep.getFillStyle() == ElementStyle.NONE ? false : true);
        getContourColorButton().setIcon(getColorIcon(ep.getContourColor(), ep.getContourColorAlpha()));
        getFillColorButton().setIcon(getColorIcon(ep.getFillColor(), ep.getFillColorAlpha()));
        getContourColorAlphaSlider().setValue(ep.getContourColorAlpha());
        getFillColorAlphaSlider().setValue(ep.getFillColorAlpha());
        getNamePanelTextField()
                .setText(Gui.getActiveScenePanel().getActiveManipulation().getManipulatedGroup().getId());

        logger.debug("Contour style: " + ep.getContourStyle());
    }

    /**
     * Getter for <code>contourCheckBox</code>.
     *
     * @return <code>contourCheckBox</code> instance.
     */
    private JCheckBox getContourCheckBox() {
        if (this.contourCheckBox == null) {
            this.contourCheckBox = new JCheckBox(PropertiesToolBarResources.CONTOUR_CHECK_BOX.getText());
            this.contourCheckBox.setSelected(true);
            this.contourCheckBox.addActionListener(new ContourCheckBoxListener(this.contourCheckBox));
        }
        return this.contourCheckBox;
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
            this.contourColorAlphaSlider.addChangeListener(new ContourColorAlphaSliderChangeListener(
                    this.contourColorAlphaSlider));
        }
        return this.contourColorAlphaSlider;
    }

    /**
     * Getter for <code>fillCheckBox</code> instance.
     *
     * @return <code>fillCheckBox</code> instance.
     */
    private JCheckBox getFillCheckBox() {
        if (this.fillCheckBox == null) {
            this.fillCheckBox = new JCheckBox(PropertiesToolBarResources.FILL_CHECK_BOX.getText());
            this.fillCheckBox.setSelected(true);
            this.fillCheckBox.addActionListener(new FillCheckBoxListener(this.fillCheckBox));

            this.fillCheckBox.setMaximumSize(DIM);
        }
        return this.fillCheckBox;
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
     * Getter for <code>contourWidthComboBox</code>.
     *
     * @return <code>contourWidthComboBox</code> instance.
     */
    private final JComboBox getContourWidthComboBox() {
        if (this.contourWidthComboBox == null) {
            this.contourWidthComboBox = new JComboBox(CONTOUR_WIDTH);
            this.contourWidthComboBox.setEditable(true);
            this.contourWidthComboBox.addActionListener(new LineWidthComboBoxActionListener(this.contourWidthComboBox));
        }
        return this.contourWidthComboBox;
    }

    /**
     * Getter for <code>contourColorButton</code>.
     *
     * @return <code>contourColorButton</code> instance.
     */
    private final JButton getContourColorButton() {
        if (this.contourColorButton == null) {
            this.contourColorButton = new JButton();
            // this.contourColorButton.setText(PropertiesToolBarResources.CONTOUR_COLOR_BTN.getText());
            this.contourColorButton.setIcon(getColorIcon(Gui.getActiveScenePanel().getSceneProperties()
                    .getSceneElementProperties().getContourColor(), Gui.getActiveScenePanel().getSceneProperties()
                    .getSceneElementProperties().getContourColorAlpha()));
            this.contourColorButton.addActionListener(new ContourColorButtonActionListener(this.contourColorButton));
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
            this.fillColorButton = new JButton();
            // this.fillColorButton.setText(PropertiesToolBarResources.FILL_COLOR_BTN.getText());
            this.fillColorButton.setIcon(getColorIcon(Gui.getActiveScenePanel().getSceneProperties()
                    .getSceneElementProperties().getFillColor(), Gui.getActiveScenePanel().getSceneProperties()
                    .getSceneElementProperties().getFillColorAlpha()));
            this.fillColorButton.addActionListener(new FillColorButtonActionListener(this.fillColorButton));
        }
        return this.fillColorButton;
    }

    /**
     * Getter for image representing selected color on button. TODO Should be moved into some adequate package.
     *
     * @param color color to be present on image.
     * @param alpha alpha of given color.
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
