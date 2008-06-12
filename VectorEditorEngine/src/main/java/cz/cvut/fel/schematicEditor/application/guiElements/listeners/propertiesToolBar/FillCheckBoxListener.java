package cz.cvut.fel.schematicEditor.application.guiElements.listeners.propertiesToolBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cz.cvut.fel.schematicEditor.core.coreStructures.ElementProperties;
import cz.cvut.fel.schematicEditor.core.coreStructures.ElementStyle;

/**
 * This class implements {@link ChangeListener} for <code>fillCheckBox</code>.
 *
 * @author Urban Kravjansky
 */
public class FillCheckBoxListener extends PropertiesToolBarListener implements ActionListener {
    /**
     * Points to <code>fillCheckBox</code> instance.
     */
    private JCheckBox fillCheckBox = null;

    /**
     * {@link FillCheckBoxListener} constructor. It initializes <code>fillCheckBox</code> field.
     *
     * @param fillCheckBox
     *            fill style {@link JCheckBox} parameter.
     */
    public FillCheckBoxListener(JCheckBox fillCheckBox) {
        setFillCheckBox(fillCheckBox);
    }

    /**
     * Method is invoked as a result to an action. It sets proper fill state and updates properties.
     *
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     * @param ae
     *            {@link ChangeEvent} parameter. This parameter is only for implementing purposes,
     *            it is not used nor needed.
     */
    public void actionPerformed(ActionEvent ae) {
        ElementProperties ep = getElementProperties();

        ep.setFillStyle(getFillCheckBox().isSelected() ? ElementStyle.NORMAL : ElementStyle.NONE);

        // update properties
        updateProperties(ep);
    }

    /**
     * Setter for <code>fillCheckBox</code>.
     *
     * @param fillCheckBox
     *            the <code>fillCheckBox</code> to set.
     */
    private void setFillCheckBox(JCheckBox fillCheckBox) {
        this.fillCheckBox = fillCheckBox;
    }

    /**
     * Getter for <code>fillCheckBox</code>.
     *
     * @return The <code>fillCheckBox</code> instance.
     */
    private JCheckBox getFillCheckBox() {
        return this.fillCheckBox;
    }
}
