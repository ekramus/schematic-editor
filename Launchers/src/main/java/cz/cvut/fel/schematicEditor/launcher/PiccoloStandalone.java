package cz.cvut.fel.schematicEditor.launcher;

import cz.cvut.fel.schematicEditor.configuration.Configuration;
import cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.gui.Gui;

/**
 * This method implements stand alone launcher. It is used for stand alone launching of <em>SchematicEditor</em>
 * application.
 *
 * @author Urban Kravjansky
 */
public class PiccoloStandalone {
    /**
     * @param args
     */
    public static void main(String[] args) {
        Launcher.loadLog4JProperties();

        Gui gui = Gui.getInstance();

        Configuration.getInstance();
        Launcher.setUI();

        gui.getApplicationFrame().setVisible(true);
    }
}
