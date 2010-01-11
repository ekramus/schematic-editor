package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.listeners;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.PartPropertiesTableModel;

/**
 * @author Urban Kravjansky
 *
 */
public class PartPropertiesTableModelListener implements TableModelListener {

    /**
     * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
     */
    public void tableChanged(TableModelEvent arg0) {
        PartPropertiesTableModel pptm = (PartPropertiesTableModel) arg0.getSource();
        pptm.fireTableRowsUpdated(arg0.getFirstRow(), arg0.getLastRow());
    }

}
