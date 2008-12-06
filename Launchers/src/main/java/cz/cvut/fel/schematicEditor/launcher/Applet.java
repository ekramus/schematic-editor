package cz.cvut.fel.schematicEditor.launcher;

import javax.swing.JApplet;

import cz.cvut.fel.schematicEditor.configuration.Configuration;
import cz.cvut.fel.schematicEditor.guiAdvanced.GuiAdvanced;

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
        GuiAdvanced gui = new GuiAdvanced();

        Launcher.loadLog4JProperties();
        Configuration.getInstance();
        Launcher.setUI();

        this.setContentPane(gui.getAppletPanel());
    }
}
