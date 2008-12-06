package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesToolBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.properties.ElementProperties;
import cz.cvut.fel.schematicEditor.guiAdvanced.GuiAdvanced;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
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
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;
    /**
     * Points to <code>lineWidthComboBox</code> instance.
     */
    private JComboBox     lineWidthComboBox = null;

    /**
     * {@link LineWidthComboBoxActionListener} constructor. It initializes
     * <code>lineWidthComboBox</code> field.
     *
     * @param lineWidthComboBox
     *            line width {@link JComboBox} parameter.
     */
    public LineWidthComboBoxActionListener(final JComboBox lineWidthComboBox) {
        logger = Logger.getLogger(GuiAdvanced.class.getName());
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
        try {
            updateProperties(ep);
        } catch (UnknownManipulationException ume) {
            logger.error(ume.getMessage());
        }
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
