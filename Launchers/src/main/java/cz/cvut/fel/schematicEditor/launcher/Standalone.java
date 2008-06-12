package cz.cvut.fel.schematicEditor.launcher;

import javax.swing.SwingUtilities;

import cz.cvut.fel.schematicEditor.application.Gui;
import cz.cvut.fel.schematicEditor.core.Structures;

/**
 * @author uk
 */
public class Standalone {
    public static void main(String[] args) {
        // SwingUtilities.invokeLater(new Runnable() {
        // public void run() {
        Gui gui = new Gui();

        Launcher.loadProperties();

        Structures.getGui().getJFrame().setVisible(true);
        // }
        // });
    }
}
