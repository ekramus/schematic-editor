package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.listeners.PropertiesSelectorToolBarButtonActionListener;

/**
 * Implements selector toolbar for properties panels.
 *
 * @author Urban Kravjansky
 *
 */
public class PropertiesSelectorToolBar extends JToolBar {
    /**
     * No button selected.
     */
    public static final int                  NONE                    = 0;
    /**
     * Part properties button selected.
     */
    public static final int                  PART_PROPERTIES         = 1;
    /**
     * General properties button selected.
     */
    public static final int                  GENERAL_PROPERTIES      = 2;

    /**
     * Actually selected button.
     */
    private static int                       selectedButton          = NONE;
    /**
     * General properties button instance.
     */
    private JToggleButton                    generalPropertiesButton = null;
    /**
     * Part properties button instance.
     */
    private JToggleButton                    partPropertiesButton    = null;

    /**
     * Instance of {@link PropertiesSelectorToolBar}.
     */
    private static PropertiesSelectorToolBar instance                = null;

    /**
     * This method instantiates new instance.
     *
     */
    private PropertiesSelectorToolBar() {
        super(VERTICAL);
    }

    /**
     * Getter for singleton instance.
     *
     * @return the instance
     */
    public static PropertiesSelectorToolBar getInstance() {
        if (instance == null) {
            instance = new PropertiesSelectorToolBar();

            // set tool bar layout
            instance.setLayout(new BoxLayout(instance, BoxLayout.PAGE_AXIS));

            // add tool bar buttons
            instance.add(instance.getGeneralPropertiesButton());
            instance.add(instance.getPartPropertiesButton());
        }
        return instance;
    }

    /**
     * Generates part properties button.
     *
     * @return Instance of part properties button
     */
    private JToggleButton getPartPropertiesButton() {
        if (this.partPropertiesButton == null) {
            this.partPropertiesButton = new JToggleButton();
            this.partPropertiesButton.addActionListener(new PropertiesSelectorToolBarButtonActionListener(
                    PART_PROPERTIES));

            this.partPropertiesButton.setToolTipText("Part properties");
            this.partPropertiesButton.setIcon(getImageIcon("Part properties"));
        }
        return this.partPropertiesButton;
    }

    /**
     * Generates general properties button.
     *
     * @return Instance of general properties button.
     */
    private JToggleButton getGeneralPropertiesButton() {
        if (this.generalPropertiesButton == null) {
            this.generalPropertiesButton = new JToggleButton();
            this.generalPropertiesButton.addActionListener(new PropertiesSelectorToolBarButtonActionListener(
                    GENERAL_PROPERTIES));

            this.generalPropertiesButton.setToolTipText("General properties");
            this.generalPropertiesButton.setIcon(getImageIcon("General properties"));
        }
        return this.generalPropertiesButton;
    }

    /**
     * Generates {@link ImageIcon} according to given {@link String}.
     *
     * @param text {@link String} to render.
     * @return {@link ImageIcon} with rendered {@link String}.
     */
    private ImageIcon getImageIcon(String text) {
        // create image
        BufferedImage bi = new BufferedImage(16, 108, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) bi.getGraphics();

        FontMetrics metrics = g2d.getFontMetrics();
        int width = metrics.stringWidth(text);

        g2d.setColor(Color.BLACK);
        g2d.rotate(Math.PI / 2.0);
        g2d.drawString(text, (108 - width) / 2, -4);

        return new ImageIcon(bi);
    }

    /**
     * @return the selectedButton
     */
    public static int getSelectedButton() {
        return selectedButton;
    }

    /**
     * @param selectedButton the selectedButton to set
     */
    public static void setSelectedButton(int selectedButton) {
        PropertiesSelectorToolBar.selectedButton = selectedButton;
    }

    /**
     * Updates this properties selector tool bar.
     */
    public void refresh() {
        switch (getSelectedButton()) {
            case GENERAL_PROPERTIES:
                getGeneralPropertiesButton().setSelected(true);
                getPartPropertiesButton().setSelected(false);
                break;
            case PART_PROPERTIES:
                getGeneralPropertiesButton().setSelected(false);
                getPartPropertiesButton().setSelected(true);
                break;
            case NONE:
            default:
                getGeneralPropertiesButton().setSelected(false);
                getPartPropertiesButton().setSelected(false);
                break;
        }

        repaint();
    }
}
