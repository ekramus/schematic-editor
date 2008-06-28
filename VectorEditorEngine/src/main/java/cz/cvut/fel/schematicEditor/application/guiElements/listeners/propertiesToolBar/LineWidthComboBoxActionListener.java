package cz.cvut.fel.schematicEditor.application.guiElements.listeners.propertiesToolBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import cz.cvut.fel.schematicEditor.core.coreStructures.ElementProperties;
import cz.cvut.fel.schematicEditor.unit.UnitType;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;

/**
 * This class implements {@link ActionListener} for <code>LineWidthComboBox</code>.
 * 
 * @author Urban Kravjansky
 */
public class LineWidthComboBoxActionListener extends PropertiesToolBarListener implements
        ActionListener {
    /**
     * Points to <code>lineWidthComboBox</code> instance.
     */
    private JComboBox lineWidthComboBox = null;

    /**
     * {@link LineWidthComboBoxActionListener} constructor. It initializes
     * <code>lineWidthComboBox</code> field.
     * 
     * @param lineWidthComboBox
     *            line width {@link JComboBox} parameter.
     */
    public LineWidthComboBoxActionListener(final JComboBox lineWidthComboBox) {
        setLineWidthComboBox(lineWidthComboBox);
    }

    /**
     * Method is invoked as result to an action. It sets line width according to selected value.
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param ae
     *            {@link ActionEvent} parameter. This parameter is only for implementing purposes,
     *            it is not used nor needed.
     */
    public final void actionPerformed(final ActionEvent ae) {
        ElementProperties ep = getElementProperties();

        Unit width = UnitType.parseUnit((String) getLineWidthComboBox().getSelectedItem());
        // enable width change only for positive width value
        if (width.getValue() > 0) {
            ep.setContourLineWidth(width);
        }

        // Select manipulation
        updateProperties(ep);
    }

    /**
     * Setter for <code>lineWidthComboBox</code>.
     * 
     * @param lineWidthComboBox
     *            the lineWidthComboBox to set
     */
    private void setLineWidthComboBox(final JComboBox lineWidthComboBox) {
        this.lineWidthComboBox = lineWidthComboBox;
    }

    /**
     * Getter for <code>lineWidthComboBox</code>.
     * 
     * @return the lineWidthComboBox
     */
    private JComboBox getLineWidthComboBox() {
        return this.lineWidthComboBox;
    }
}
