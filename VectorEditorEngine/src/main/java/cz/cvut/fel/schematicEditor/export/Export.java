package cz.cvut.fel.schematicEditor.export;

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;

/**
 * Interface for export classes.
 * 
 * @author urban.kravjansky
 * 
 */
public interface Export {

    /**
     * This method exports data obtained from <code>sg</code> into <code>output</code>.
     * Exporting methods depend on export filtre.
     * 
     * @param sg
     *            scene graph.
     * @param output
     *            output device.
     */
    public void export(SceneGraph sg, Object output);
}
