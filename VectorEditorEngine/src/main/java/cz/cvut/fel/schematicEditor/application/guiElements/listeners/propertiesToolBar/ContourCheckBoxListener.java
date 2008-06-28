package cz.cvut.fel.schematicEditor.application.guiElements.listeners.propertiesToolBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.application.Gui;
import cz.cvut.fel.schematicEditor.core.coreStructures.ElementProperties;
import cz.cvut.fel.schematicEditor.core.coreStructures.ElementStyle;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;

/**
 * This class implements {@link ChangeListener} for <code>contourCheckBox</code>.
 *
 * @author Urban Kravjansky
 */
public class ContourCheckBoxListener extends PropertiesToolBarListener implements ActionListener {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;
    /**
     * Points to <code>contourCheckBox</code> instance.
     */
    private JCheckBox     contourCheckBox = null;

    /**
     * {@link ContourCheckBoxListener} constructor. It initializes <code>contourCheckBox</code>
     * field.
     *
     * @param contourCheckBox
     *            contour style {@link JCheckBox} parameter.
     */
    public ContourCheckBoxListener(JCheckBox contourCheckBox) {
        logger = Logger.getLogger(Gui.class.getName());
        setContourCheckBox(contourCheckBox);
    }

    /**
     * Method is invoked as a result to an action. It sets proper contour state and updates
     * properties.
     *
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     * @param ae
     *            {@link ChangeEvent} parameter. This parameter is only for implementing purposes,
     *            it is not used nor needed.
     */
    public void actionPerformed(ActionEvent ae) {
        ElementProperties ep = getElementProperties();

        ep.setContourStyle(getContourCheckBox().isSelected() ? ElementStyle.NORMAL
                                                            : ElementStyle.NONE);

        // update properties
        try {
            updateProperties(ep);
        } catch (UnknownManipulationException ume) {
            logger.error(ume.getMessage());
        }
    }

    /**
     * Setter for <code>contourCheckBox</code>.
     *
     * @param contourCheckBox
     *            the <code>contourCheckBox</code> to set.
     */
    private void setContourCheckBox(JCheckBox contourCheckBox) {
        this.contourCheckBox = contourCheckBox;
    }

    /**
     * Getter for <code>contourCheckBox</code>.
     *
     * @return The <code>contourCheckBox</code> instance.
     */
    private JCheckBox getContourCheckBox() {
        return this.contourCheckBox;
    }
}
