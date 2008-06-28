package cz.cvut.fel.schematicEditor.launcher;

import cz.cvut.fel.schematicEditor.application.Gui;
import cz.cvut.fel.schematicEditor.core.Structures;

/**
 * This method implements stand alone launcher. It is used for stand alone launching of
 * <em>SchematicEditor</em> application.
 *
 * @author Urban Kravjanský
 */
public class Standalone {
    /**
     * @param args
     */
    public static void main(String[] args) {
        new Gui();

        Launcher.loadProperties();
        Launcher.setUI();

        Structures.getGui().getJFrame().setVisible(true);
    }
}
