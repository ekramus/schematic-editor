package cz.cvut.fel.schematicEditor.launcher;

import javax.swing.JApplet;

import cz.cvut.fel.schematicEditor.application.Gui;

/**
 * @author Urban Kravjanský
 */
public class Applet extends JApplet {
    /**
     * This method overrides {@link JApplet} <code>init</code> method. It is used to initialize
     * applet.
     */
    @Override
    public void init() {
        Gui gui = new Gui();

        Launcher.loadProperties();

        this.setSize(300, 200);
        this.setContentPane(gui.getJContentPane());
    }
}
