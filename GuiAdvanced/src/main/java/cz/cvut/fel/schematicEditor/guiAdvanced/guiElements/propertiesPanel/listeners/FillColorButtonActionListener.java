package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.properties.ElementProperties;
import cz.cvut.fel.schematicEditor.guiAdvanced.GuiAdvanced;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.GeneralPropertiesPanel;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.PropertiesPanel;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanel.ScenePanel;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;

/**
 * This class implements listener for {@link PropertiesPanel} fillColorButton.
 *
 * @author Urban Kravjansky
 */
public class FillColorButtonActionListener extends PropertiesToolBarListener implements ActionListener {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger       logger;
    /**
     * Fill color button title.
     */
    private static final String FILL_COLOR_TITLE = "Select fill color";
    /**
     * Fill color {@link JButton} field.
     */
    private JButton             fillColorButton  = null;

    /**
     * {@link FillColorButtonActionListener} constructor. It initializes <code>fillColorButton</code> field with given
     * parameter.
     *
     * @param fillColorButton fill color {@link JButton} parameter.
     */
    public FillColorButtonActionListener(final JButton fillColorButton) {
        logger = Logger.getLogger(GuiAdvanced.class.getName());
        setFillColorButton(fillColorButton);
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

        Color c = JColorChooser.showDialog(GuiAdvanced.getActiveScenePanel(), FILL_COLOR_TITLE, ep.getFillColor());
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
        return this.fillColorButton;
    }

    /**
     * Setter for <code>fillColorButton</code>.
     *
     * @param fillColorButton the fillColorButton to set
     */
    private void setFillColorButton(final JButton fillColorButton) {
        this.fillColorButton = fillColorButton;
    }
}
