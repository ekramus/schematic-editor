package cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.propertiesPanel;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.partBrowser.PartBrowserPanel;
import cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.partPropertiesDialog.PartPropertiesDialogPanel;

/**
 * This class implements part tree tool bar.
 *
 * @author Urban Kravjansky
 */
public class PartTreePanel extends JPanel {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger        logger;
    /**
     * Singleton instance of {@link PartTreePanel}.
     */
    private static PartTreePanel instance = null;

    /**
     * Default constructor. It is private for {@link PartPropertiesDialogPanel} singleton instance.
     */
    private PartTreePanel() {
        super();

        logger = Logger.getLogger(Gui.class.getName());
    }

    /**
     * Getter for {@link PartPropertiesDialogPanel} singleton instance.
     *
     * @return {@link PartPropertiesDialogPanel} singleton instance.
     */
    public static PartTreePanel getInstance() {
        if (instance == null) {
            instance = new PartTreePanel();
            instance.setLayout(new MigLayout("wrap 1"));

            // add elements
            instance.add(PartBrowserPanel.getInstance(), "width 210, height 600");
        }
        return instance;
    }

    /**
     * Updates {@link PartPropertiesDialogPanel} according to scene or selected element properties.
     */
    public void refresh() {
        // nothing to do
    }
}
