package cz.cvut.fel.schematicEditor.launcher;

import javax.swing.JApplet;

import cz.cvut.fel.schematicEditor.configuration.Configuration;
import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.export.NetListExport;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
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
        Launcher.loadLog4JProperties();
    }

    /**
     * @see java.applet.Applet#start()
     */
    @Override
    public void start() {
        Gui gui = Gui.getInstance();

        Configuration.getInstance();
        Launcher.setUI();

        this.setContentPane(gui.getAppletPanel());

        super.start();
    }

    /**
     * @see java.applet.Applet#stop()
     */
    @Override
    public void stop() {
        Gui.resetInstance();

        super.stop();
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
     * Setter for XML serialized session.
     *
     * @param session XML serialized session.
     */
    public void setSession(String session) {
        SceneGraph sg = Gui.getActiveScenePanel().getSceneGraph();
        sg.setTopNode(SceneGraph.deserialize(GroupNode.class, session));
        Gui.getInstance().refresh();
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
