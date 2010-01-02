package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.properties.ElementProperties;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.GeneralPropertiesPanel;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.PropertiesPanel;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;

/**
 * This class implements listener for {@link PropertiesPanel} fontColorButton.
 *
 * @author Urban Kravjansky
 */
public class FontButtonActionListener extends PropertiesToolBarListener implements ActionListener {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger       logger;
    /**
     * Font button title.
     */
    private static final String FILL_COLOR_TITLE = "Select font";
    /**
     * Font {@link JButton} field.
     */
    private JButton             fontButton  = null;

    /**
     * {@link FontButtonActionListener} constructor. It initializes <code>fontColorButton</code> field with given
     * parameter.
     *
     * @param fontColorButton font {@link JButton} parameter.
     */
    public FontButtonActionListener(final JButton fontButton) {
        logger = Logger.getLogger(Gui.class.getName());
        setFillColorButton(fontButton);
    }

    /**
     * Method is invoked as result to an action. It initializes {@link JColorChooser} dialog window enabling fill color
     * selection.
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param ae {@link ActionEvent} parameter. This parameter is only for implementing purposes, it is not used nor
     *            needed.
     */
    public final void actionPerformed(final ActionEvent ae) {
        ElementProperties ep = getElementProperties();

        Color c = JColorChooser.showDialog(Gui.getActiveScenePanel(), FILL_COLOR_TITLE, ep.getFillColor());
        if (c != null) {
            ep.setFillColor(c);
            getFillColorButton().setIcon(GeneralPropertiesPanel.getColorIcon(c, ep.getFillColorAlpha()));
        }

        // update properties only when using Select manipulation
        try {
            updateProperties(ep);
        } catch (UnknownManipulationException ume) {
            logger.error(ume.getMessage());
        }
    }

    /**
     * Getter for <code>fillColorButton</code>.
     *
     * @return the fillColorButton
     */
    private JButton getFillColorButton() {
        return this.fontButton;
    }

    /**
     * Setter for <code>fillColorButton</code>.
     *
     * @param fontButton the fillColorButton to set
     */
    private void setFillColorButton(final JButton fillColorButton) {
        this.fontButton = fillColorButton;
    }
}
