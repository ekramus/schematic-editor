package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesToolBar;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

/**
 * This class implements properties panel.
 *
 * @author Urban Kravjansky
 */
public class PropertiesPanel extends JPanel {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger          logger;
    /**
     * Singleton instance of {@link PropertiesPanel}.
     */
    private static PropertiesPanel propertiesPanel = null;

    /**
     * Default constructor. It is private for {@link PropertiesPanel} singleton instance.
     */
    private PropertiesPanel() {
        super();

        logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * Getter for {@link PropertiesPanel} singleton instance.
     *
     * @return {@link PropertiesPanel} singleton instance.
     */
    public static PropertiesPanel getInstance() {
        if (propertiesPanel == null) {
            propertiesPanel = new PropertiesPanel();
            propertiesPanel.setLayout(new BorderLayout());
            // propertiesPanel.setPreferredSize(new Dimension(225, 400));

            // add elements by updating properties tool bar
            propertiesPanel.add(PropertiesSelectorToolBar.getInstance(), BorderLayout.EAST);
            propertiesPanel.update();
        }
        return propertiesPanel;
    }

    /**
     * Updates {@link PropertiesPanel}.
     */
    public void update() {
        // set visibility of correct panel only. The panel has to be added, as in CENTER only one panel can exist.
        switch (PropertiesSelectorToolBar.getSelectedButton()) {
            case PropertiesSelectorToolBar.GENERAL_PROPERTIES:
                add(GeneralPropertiesPanel.getInstance(), BorderLayout.CENTER);
                GeneralPropertiesPanel.getInstance().setVisible(true);
                PartPropertiesPanel.getInstance().setVisible(false);
                break;
            case PropertiesSelectorToolBar.PART_PROPERTIES:
                add(PartPropertiesPanel.getInstance(), BorderLayout.CENTER);
                GeneralPropertiesPanel.getInstance().setVisible(false);
                PartPropertiesPanel.getInstance().setVisible(true);
                break;
            case PropertiesSelectorToolBar.NONE:
            default:
                GeneralPropertiesPanel.getInstance().setVisible(false);
                PartPropertiesPanel.getInstance().setVisible(false);
                break;
        }

        // update properties selector tool bar
        PropertiesSelectorToolBar.getInstance().update();
    }
}
