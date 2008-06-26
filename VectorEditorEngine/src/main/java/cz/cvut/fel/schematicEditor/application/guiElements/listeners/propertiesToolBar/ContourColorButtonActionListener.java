package cz.cvut.fel.schematicEditor.application.guiElements.listeners.propertiesToolBar;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;

import cz.cvut.fel.schematicEditor.application.guiElements.PropertiesToolBar;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.core.coreStructures.ElementProperties;

/**
 * This class implements listener for {@link PropertiesToolBar} contourColorButton.
 * 
 * @author Urban Kravjansky
 */
public final class ContourColorButtonActionListener extends PropertiesToolBarListener implements
        ActionListener {

    /**
     * Contour color button title.
     */
    private static final String CONTOUR_COLOR_TITLE = "Select contour color";
    /**
     * Contour color {@link JButton} field.
     */
    private JButton             contourColorButton  = null;

    /**
     * {@link ContourColorButtonActionListener} constructor. It initializes
     * <code>contourColorButton</code> field with given parameter.
     * 
     * @param contourColorButton
     *            contour color {@link JButton} parameter.
     */
    public ContourColorButtonActionListener(final JButton contourColorButton) {
        setContourColorButton(contourColorButton);
    }

    /**
     * Method is invoked as result to an action. It initializes {@link JColorChooser} dialog window
     * enabling contour color selection.
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * 
     * @param ae
     *            {@link ActionEvent} parameter. This parameter is only for implementing purposes,
     *            it is not used nor needed.
     */
    public void actionPerformed(final ActionEvent ae) {
        ElementProperties ep = getElementProperties();

        Color c = JColorChooser.showDialog(Structures.getScenePanel(), CONTOUR_COLOR_TITLE, ep
                .getContourColor());
        if (c != null) {
            ep.setContourColor(c);
            getContourColorButton().setIcon(PropertiesToolBar.getColorIcon(c));
        }

        // update properties only when using Select manipulation
        updateProperties(ep);
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
     * @param contourColorButton
     *            the contourColorButton to set
     */
    private void setContourColorButton(final JButton contourColorButton) {
        this.contourColorButton = contourColorButton;
    }
}
