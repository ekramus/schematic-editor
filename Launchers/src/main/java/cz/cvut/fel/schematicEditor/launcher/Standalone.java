package cz.cvut.fel.schematicEditor.launcher;

import cz.cvut.fel.schematicEditor.application.Gui;
import cz.cvut.fel.schematicEditor.properties.Configuration;

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
        Gui gui = new Gui();

        Configuration.getInstance();
        Launcher.setUI();

        gui.getJFrame().setVisible(true);
    }
}
