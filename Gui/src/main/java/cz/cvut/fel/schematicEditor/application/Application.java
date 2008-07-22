package cz.cvut.fel.schematicEditor.application;

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;


/**
 * @author Urban Kravjansky
 */
public class Application {
    /**
     * <code>version</code> of application.
     */
    public static final String version = "0.0.5";

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("This is Schematic Editor v." + version);

        SceneGraph sg = new SceneGraph();
        sg.manualCreateSceneGraph2();
    }
}

