package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel;

import java.util.Iterator;

import javax.swing.table.AbstractTableModel;

import cz.cvut.fel.schematicEditor.parts.PartProperties;
import cz.cvut.fel.schematicEditor.parts.PartProperty;
import cz.cvut.fel.schematicEditor.parts.PropertiesCategory;

/**
 * This class implements table model for part properties.
 *
 * @author Urban Kravjansky
 *
 */
public class PartPropertiesTableModel extends AbstractTableModel {
    /**
     * Names of columns.
     */
    private final String[] columnNames    = { "Name", "Value" };
    /**
     * Part properties collection.
     */
    private PartProperties partProperties = null;
    /**
     * Size of part properties object.
     */
    private int            partPropertiesSize;

    /**
     * @return the partPropertiesSize
     */
    private int getPartPropertiesSize() {
        return this.partPropertiesSize;
    }

    /**
     * @param partPropertiesSize the partPropertiesSize to set
     */
    private void setPartPropertiesSize(int partPropertiesSize) {
        this.partPropertiesSize = partPropertiesSize;
    }

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
        int result = 0;

        // add one row for netlist and partPropertiesSize
        result = 1 + getPartPropertiesSize();

        return result;
    }

    /**
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        // check, if not netlist is wanted
        if (rowIndex >= getPartPropertiesSize()) {
            return (columnIndex == 0) ? "netlist" : getPartProperties().getNetlist();
        }
        PartProperty<String, String> pp = getPartProperty(rowIndex);
        return (columnIndex == 0) ? pp.getKey() : pp.getValue();
    }

    /**
     * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
     */
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        setPartProperty((String) value, rowIndex);
    }

    /**
     * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (columnIndex > 0) ? true : false;
    }

    /**
     * @param partProperties the partProperties to set
     */
    public void setPartProperties(PartProperties partProperties) {
        this.partProperties = partProperties;

        // set partPropertiesSize
        int pps = 0;
        try {
            for (PropertiesCategory pc : partProperties.getPartPropertiesArray().getCategoriesForPropertiesArray()) {
                pps += pc.getPropertiesForCategory().size();
            }
        } catch (NullPointerException e) {
            setPartPropertiesSize(0);
        }
        setPartPropertiesSize(pps);
    }

    /**
     * @return the partProperties
     */
    private PartProperties getPartProperties() {
        return this.partProperties;
    }

    /**
     * @param index
     * @return
     */
    private PartProperty<String, String> getPartProperty(int index) {
        int i = 0;

        Iterator<PropertiesCategory> it = getPartProperties().getPartPropertiesArray()
                .getCategoriesForPropertiesArray().iterator();
        PropertiesCategory pc = it.next();
        while (i < index) {
            // i has to be maximally equal to index, so we can advance to the next category
            if (i + pc.getPropertiesForCategory().size() <= index) {
                i += pc.getPropertiesForCategory().size();
                pc = it.next();
            } else {
                break;
            }
        }

        return pc.getPropertiesForCategory().get(index - i);
    }

    /**
     * @param value
     * @param index
     */
    private void setPartProperty(String value, int index) {
        int i = 0;

        Iterator<PropertiesCategory> it = getPartProperties().getPartPropertiesArray()
                .getCategoriesForPropertiesArray().iterator();
        PropertiesCategory pc = it.next();
        while (i < index) {
            // i has to be maximally equal to index, so we can advance to the next category
            if (i + pc.getPropertiesForCategory().size() <= index) {
                i += pc.getPropertiesForCategory().size();
                pc = it.next();
            } else {
                break;
            }
        }

        PartProperty<String, String> pp = pc.getPropertiesForCategory().get(index - i);
        PartProperty<String, String> newPP = new PartProperty<String, String>(pp.getKey(), value);

        pc.getPropertiesForCategory().set(index - i, newPP);
    }
}
