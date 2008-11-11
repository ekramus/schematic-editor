package cz.cvut.fel.schematicEditor.application.guiElements.propertiesToolBar;

import java.awt.FlowLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.application.Gui;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.element.properties.ElementProperties;
import cz.cvut.fel.schematicEditor.element.properties.ElementStyle;
import cz.cvut.fel.schematicEditor.element.properties.PartType;

/**
 * This class implements properties tool bar.
 *
 * @author Urban Kravjansky
 */
public class PartPropertiesPanel extends JPanel {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger              logger;
    /**
     * Singleton instance of {@link PartPropertiesPanel}.
     */
    private static PartPropertiesPanel partPropertiesPanel = null;

    /**
     * Line width {@link JComboBox} instance.
     */
    private JComboBox                  partTypeComboBox    = null;
    /**
     * Contour {@link JCheckBox} instance.
     */
    private JCheckBox                  editingPartCheckBox = null;

    /**
     * Default constructor. It is private for {@link PartPropertiesPanel} singleton instance.
     */
    private PartPropertiesPanel() {
        super();

        logger = Logger.getLogger(Gui.class.getName());
    }

    /**
     * Getter for {@link PartPropertiesPanel} singleton instance.
     *
     * @return {@link PartPropertiesPanel} singleton instance.
     */
    public static PartPropertiesPanel getInstance() {
        if (partPropertiesPanel == null) {
            partPropertiesPanel = new PartPropertiesPanel();
            partPropertiesPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 25));

            // add elements
            partPropertiesPanel.add(partPropertiesPanel.getEditingPartCheckBox());
            partPropertiesPanel.add(partPropertiesPanel.getPartTypeComboBox());
        }
        return partPropertiesPanel;
    }

    /**
     * Refresh {@link PartPropertiesPanel} according to scene or selected element properties.
     */
    public static void refresh() {
        ElementProperties ep;

        if (Structures.getSceneProperties().getSelectedElementProperties() == null) {
            ep = Structures.getSceneProperties().getSceneElementProperties();
        } else {
            ep = Structures.getSceneProperties().getSelectedElementProperties();
        }

        partPropertiesPanel.getPartTypeComboBox().setSelectedItem(String.valueOf(ep.getContourLineWidth()));
        partPropertiesPanel.getEditingPartCheckBox().setSelected(
                                                                 ep.getContourStyle() == ElementStyle.NONE ? false
                                                                         : true);

        logger.debug("Contour style: " + ep.getContourStyle());
    }

    public JCheckBox getEditingPartCheckBox() {
        if (this.editingPartCheckBox == null) {
            this.editingPartCheckBox = new JCheckBox("Editing part");
            this.editingPartCheckBox.setSelected(false);
        }
        return this.editingPartCheckBox;
    }

    /**
     * Getter for <code>lineWidthComboBox</code>.
     *
     * @return <code>lineWidthComboBox</code> instance.
     */
    public final JComboBox getPartTypeComboBox() {
        if (this.partTypeComboBox == null) {
            this.partTypeComboBox = new JComboBox(PartType.values());
            this.partTypeComboBox.setEditable(false);
        }
        return this.partTypeComboBox;
    }

}
