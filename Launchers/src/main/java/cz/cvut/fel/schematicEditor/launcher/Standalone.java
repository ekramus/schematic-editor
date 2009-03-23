package cz.cvut.fel.schematicEditor.launcher;

import cz.cvut.fel.schematicEditor.configuration.Configuration;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;

/**
 * This method implements stand alone launcher. It is used for stand alone launching of <em>SchematicEditor</em>
 * application.
 *
 * @author Urban Kravjanský
 */
public class Standalone {
    /**
     * @param args
     */
    public static void main(String[] args) {
        Gui gui = Gui.getInstance();

        Launcher.loadLog4JProperties();
        Configuration.getInstance();
        Launcher.setUI();

        gui.getApplicationFrame().setVisible(true);
    }
}
