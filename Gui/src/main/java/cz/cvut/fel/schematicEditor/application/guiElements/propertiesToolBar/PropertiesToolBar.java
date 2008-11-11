package cz.cvut.fel.schematicEditor.application.guiElements.propertiesToolBar;

import javax.swing.BoxLayout;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import org.apache.log4j.Logger;

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
            propertiesToolBar.setLayout(new BoxLayout(propertiesToolBar, BoxLayout.Y_AXIS));

            // add elements
            propertiesToolBar.add(propertiesToolBar.getTabbedPane());
        }
        return propertiesToolBar;
    }

    /**
     * @return the tabbedPane
     */
    public JTabbedPane getTabbedPane() {
        if (this.tabbedPane == null) {
            this.tabbedPane = new JTabbedPane();

            // add elements
            this.tabbedPane.addTab("general properties", GeneralPropertiesPanel.getInstance());
            this.tabbedPane.addTab("part properties", PartPropertiesPanel.getInstance());
        }
        return this.tabbedPane;
    }
}
