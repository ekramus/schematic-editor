package cz.cvut.fel.schematicEditor.launcher;

import javax.swing.JApplet;

import cz.cvut.fel.schematicEditor.application.Gui;
import cz.cvut.fel.schematicEditor.properties.AppProperties;

/**
 * This class implements applet launcher. It is used to launch <em>SchematicEditor</em> as applet.
 * 
 * @author Urban Kravjanský
 */
public class Applet extends JApplet {
    /**
     * This method overrides {@link JApplet} <code>init</code> method. It is used to initialize applet.
     */
    @Override
    public void init() {
        Gui gui = new Gui();

        AppProperties.getInstance();
        Launcher.setUI();

        this.setSize(300, 200);
        this.setContentPane(gui.getJContentPane());
    }
}
