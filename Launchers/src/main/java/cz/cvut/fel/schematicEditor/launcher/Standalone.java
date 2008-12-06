package cz.cvut.fel.schematicEditor.launcher;

import cz.cvut.fel.schematicEditor.configuration.Configuration;
import cz.cvut.fel.schematicEditor.guiAdvanced.GuiAdvanced;

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
        GuiAdvanced gui = new GuiAdvanced();

        Launcher.loadLog4JProperties();
        Configuration.getInstance();
        Launcher.setUI();

        gui.getApplicationFrame().setVisible(true);
    }
}
