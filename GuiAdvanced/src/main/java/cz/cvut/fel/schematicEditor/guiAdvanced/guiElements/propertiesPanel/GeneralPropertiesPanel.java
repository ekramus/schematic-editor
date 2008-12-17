package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.properties.ElementProperties;
import cz.cvut.fel.schematicEditor.element.properties.ElementStyle;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.guiAdvanced.GuiAdvanced;
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
     * Line width {@link JComboBox} instance.
     */
    private JComboBox                     lineWidthComboBox       = null;
    /**
     * Line width {@link JPanel} instance.
     */
    private JPanel                        lineWidthPanel          = null;
    /**
     * Line width predefined values.
     */
    private static final String[]         LINE_WIDTH              = { "1 px",
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
    private JPanel                        contourColorPanel       = null;

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
    private JPanel                        fillColorPanel          = null;
    /**
     * Name panel text field instance.
     */
    private JTextField                    namePanelTextField      = null;

    /**
     * Default constructor. It is private for {@link GeneralPropertiesPanel} singleton instance.
     */
    private GeneralPropertiesPanel() {
        super();

        logger = Logger.getLogger(GuiAdvanced.class.getName());
    }

    /**
     * Getter for {@link GeneralPropertiesPanel} singleton instance.
     *
     * @return {@link GeneralPropertiesPanel} singleton instance.
     */
    public static GeneralPropertiesPanel getInstance() {
        if (propertiesToolBar == null) {
            propertiesToolBar = new GeneralPropertiesPanel();
            propertiesToolBar.setLayout(new BoxLayout(propertiesToolBar, BoxLayout.PAGE_AXIS));
            propertiesToolBar.setPreferredSize(new Dimension(200, 0));

            // add elements
            propertiesToolBar.add(Box.createVerticalStrut(20));
            propertiesToolBar.add(propertiesToolBar.getNamePanel());
            propertiesToolBar.add(propertiesToolBar.getLineWidthPanel());
            propertiesToolBar.add(propertiesToolBar.getContourCheckBox());
            propertiesToolBar.add(propertiesToolBar.getContourColorPanel());
            propertiesToolBar.add(propertiesToolBar.getFillCheckBox());
            propertiesToolBar.add(propertiesToolBar.getFillColorPanel());
            propertiesToolBar.add(propertiesToolBar.getFillStylePanel());
        }
        return propertiesToolBar;
    }

    /**
     * @return
     */
    private Component getFillStylePanel() {
        JPanel result = new JPanel();

        result.setMaximumSize(DIM);
        result.setBackground(Color.RED);

        return result;
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

        result.setBackground(Color.RED);
        result.setMaximumSize(DIM);

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
    public void update() {
        ElementProperties ep;

        if (GuiAdvanced.getActiveScenePanel().getSceneProperties().getSelectedElementProperties() == null) {
            ep = GuiAdvanced.getActiveScenePanel().getSceneProperties().getSceneElementProperties();
        } else {
            ep = GuiAdvanced.getActiveScenePanel().getSceneProperties().getSelectedElementProperties();
        }

        getLineWidthComboBox().setSelectedItem(String.valueOf(ep.getContourLineWidth()));
        getContourCheckBox().setSelected(ep.getContourStyle() == ElementStyle.NONE ? false : true);
        getFillCheckBox().setSelected(ep.getFillStyle() == ElementStyle.NONE ? false : true);
        getContourColorButton().setIcon(getColorIcon(ep.getContourColor(), ep.getContourColorAlpha()));
        getFillColorButton().setIcon(getColorIcon(ep.getFillColor(), ep.getFillColorAlpha()));
        getContourColorAlphaSlider().setValue(ep.getContourColorAlpha());
        getFillColorAlphaSlider().setValue(ep.getFillColorAlpha());
        getNamePanelTextField().setText(
                                        GuiAdvanced.getActiveScenePanel().getActiveManipulation().getManipulatedGroup()
                                                .getId());

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

            this.lineWidthPanel.setMaximumSize(DIM);
            this.lineWidthPanel.setBackground(Color.BLUE);
        }
        return this.lineWidthPanel;
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

            this.contourCheckBox.setMaximumSize(DIM);
        }
        return this.contourCheckBox;
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

            this.contourColorPanel.setMaximumSize(DIM);
            this.contourColorPanel.setBackground(Color.RED);
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

            this.fillColorPanel.setMaximumSize(DIM);
            this.fillColorPanel.setBackground(Color.BLUE);
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
            this.lineWidthComboBox.addActionListener(new LineWidthComboBoxActionListener(this.lineWidthComboBox));
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
            this.contourColorButton.setIcon(getColorIcon(GuiAdvanced.getActiveScenePanel().getSceneProperties()
                    .getSceneElementProperties().getContourColor(), GuiAdvanced.getActiveScenePanel()
                    .getSceneProperties().getSceneElementProperties().getContourColorAlpha()));
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
            this.fillColorButton = new JButton(PropertiesToolBarResources.FILL_COLOR_BTN.getText());
            this.fillColorButton.setIcon(getColorIcon(GuiAdvanced.getActiveScenePanel().getSceneProperties()
                    .getSceneElementProperties().getFillColor(), GuiAdvanced.getActiveScenePanel().getSceneProperties()
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
