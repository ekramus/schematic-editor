package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel;

import javax.swing.table.AbstractTableModel;

/**
 * This class implements table model for part properties.
 *
 * @author Urban Kravjansky
 *
 */
public class PartPropertiesTableModel extends AbstractTableModel {
    private final String[] columnNames    = { "Name", "Value" };
    private Object[]       partProperties = null;

    /**
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return this.columnNames.length;
    }

    /**
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        try {
            return getPartProperties().length / 2;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    /**
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        return getPartProperties()[rowIndex * getColumnCount() + columnIndex];
    }

    /**
     * @param partProperties the partProperties to set
     */
    public void setPartProperties(Object[] partProperties) {
        this.partProperties = partProperties;
    }

    /**
     * @return the partProperties
     */
    private Object[] getPartProperties() {
        return this.partProperties;
    }
}
