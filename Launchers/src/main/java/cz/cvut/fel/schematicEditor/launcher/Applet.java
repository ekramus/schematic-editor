package cz.cvut.fel.schematicEditor.launcher;

import javax.swing.JApplet;

import cz.cvut.fel.schematicEditor.configuration.Configuration;
import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.export.NetListExport;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;

/**
 * This class implements applet launcher. It is used to launch <em>SchematicEditor</em> as applet.
 *
 * @author Urban Kravjansky
 */
public class Applet extends JApplet {
    /**
     * This method overrides {@link JApplet} <code>init</code> method. It is used to initialize applet.
     */
    @Override
    public void init() {
        Gui gui = Gui.getInstance();

        Launcher.loadLog4JProperties();
        Configuration.getInstance();
        Launcher.setUI();

        this.setContentPane(gui.getAppletPanel());
    }

    /**
     * Getter for XML serialized session.
     *
     * @return XML serialized session.
     */
    public String getSession() {
        SceneGraph sg = Gui.getActiveScenePanel().getSceneGraph();
        return sg.serialize();
    }

    /**
     * Getter for scene netlist.
     *
     * @return scene netlist.
     */
    public String getNetlist() {
        SceneGraph sg = Gui.getActiveScenePanel().getSceneGraph();
        NetListExport nle = new NetListExport();
        return nle.export(sg);
    }

}
