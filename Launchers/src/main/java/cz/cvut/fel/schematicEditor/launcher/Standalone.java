package cz.cvut.fel.schematicEditor.launcher;

import cz.cvut.fel.schematicEditor.application.Gui;

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
        Gui gui = new Gui();

        Launcher.loadProperties();
        Launcher.setUI();

        gui.getJFrame().setVisible(true);
    }
}
