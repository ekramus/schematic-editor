package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesToolBar;

import java.awt.BorderLayout;

import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.guiAdvanced.GuiAdvanced;

/**
 * This class implements properties tool bar.
 *
 * @author Urban Kravjansky
 */
public class PropertiesToolBar extends JToolBar {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger            logger;
    /**
     * Singleton instance of {@link PropertiesToolBar}.
     */
    private static PropertiesToolBar propertiesToolBar = null;
    private JTabbedPane              tabbedPane        = null;

    /**
     * Default constructor. It is private for {@link PropertiesToolBar} singleton instance.
     */
    private PropertiesToolBar() {
        super();

        logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * Getter for {@link PropertiesToolBar} singleton instance.
     *
     * @return {@link PropertiesToolBar} singleton instance.
     */
    public static PropertiesToolBar getInstance() {
        if (propertiesToolBar == null) {
            propertiesToolBar = new PropertiesToolBar();
            propertiesToolBar.setLayout(new BorderLayout());
            // propertiesToolBar.setPreferredSize(new Dimension(225, 400));

            // add elements by updating properties tool bar
            propertiesToolBar.update();
        }
        return propertiesToolBar;
    }

    /**
     * @return the tabbedPane
     */
    public JTabbedPane getTabbedPane() {
        if (this.tabbedPane == null) {
            this.tabbedPane = new JTabbedPane();
            this.tabbedPane.setVisible(true);
            this.tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

            // add elements
            this.tabbedPane.addTab("general properties", GeneralPropertiesPanel.getInstance());
            this.tabbedPane.addTab("part properties", PartPropertiesPanel.getInstance());
        }
        return this.tabbedPane;
    }

    /**
     * Updates {@link PropertiesToolBar}.
     */
    public void update() {
        // clear panel
        removeAll();

        switch (PropertiesSelectorToolBar.getSelectedButton()) {
            case PropertiesSelectorToolBar.GENERAL_PROPERTIES:
                add(GeneralPropertiesPanel.getInstance(), BorderLayout.CENTER);
                // GeneralPropertiesPanel.getInstance().validate();
                // GeneralPropertiesPanel.getInstance().repaint();
                break;
            case PropertiesSelectorToolBar.PART_PROPERTIES:
                add(PartPropertiesPanel.getInstance(), BorderLayout.CENTER);
                // PartPropertiesPanel.getInstance().validate();
                // PartPropertiesPanel.getInstance().repaint();
                break;
            case PropertiesSelectorToolBar.NONE:
            default:
                break;
        }

        // update and add properties selector tool bar
        PropertiesSelectorToolBar.getInstance().update();
        add(PropertiesSelectorToolBar.getInstance(), BorderLayout.EAST);

        // repaint
        GuiAdvanced.getInstance().validate();
        validate();
        repaint();
    }
}
