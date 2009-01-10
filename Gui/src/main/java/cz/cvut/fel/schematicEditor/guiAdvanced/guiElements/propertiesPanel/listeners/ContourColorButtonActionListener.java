package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.properties.ElementProperties;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.guiAdvanced.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.GeneralPropertiesPanel;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.PropertiesPanel;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanel.ScenePanel;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;

/**
 * This class implements listener for {@link PropertiesPanel} contourColorButton.
 *
 * @author Urban Kravjansky
 */
public final class ContourColorButtonActionListener extends PropertiesToolBarListener implements ActionListener {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger       logger;
    /**
     * Contour color button title.
     */
    private static final String CONTOUR_COLOR_TITLE = "Select contour color";
    /**
     * Contour color {@link JButton} field.
     */
    private JButton             contourColorButton  = null;

    /**
     * {@link ContourColorButtonActionListener} constructor. It initializes <code>contourColorButton</code> field with
     * given parameter.
     *
     * @param contourColorButton contour color {@link JButton} parameter.
     */
    public ContourColorButtonActionListener(final JButton contourColorButton) {
        logger = Logger.getLogger(Gui.class.getName());
        setContourColorButton(contourColorButton);
    }

    /**
     * Method is invoked as result to an action. It initializes {@link JColorChooser} dialog window enabling contour
     * color selection.
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param ae {@link ActionEvent} parameter. This parameter is only for implementing purposes, it is not used nor
     *            needed.
     */
    public void actionPerformed(final ActionEvent ae) {
        ElementProperties ep = getElementProperties();

        Color c = JColorChooser.showDialog(Gui.getActiveScenePanel(), CONTOUR_COLOR_TITLE, ep.getContourColor());
        if (c != null) {
            ep.setContourColor(c);
            getContourColorButton().setIcon(GeneralPropertiesPanel.getColorIcon(c, ep.getContourColorAlpha()));
        }

        // update properties only when using Select manipulation
        try {
            updateProperties(ep);
        } catch (UnknownManipulationException ume) {
            logger.error(ume.getMessage());
        }
    }

    /**
     * Getter for <code>contourColorButton</code>.
     *
     * @return the contourColorButton
     */
    private JButton getContourColorButton() {
        return this.contourColorButton;
    }

    /**
     * Setter for <code>contourColorButton</code>.
     *
     * @param contourColorButton the contourColorButton to set
     */
    private void setContourColorButton(final JButton contourColorButton) {
        this.contourColorButton = contourColorButton;
    }
}
