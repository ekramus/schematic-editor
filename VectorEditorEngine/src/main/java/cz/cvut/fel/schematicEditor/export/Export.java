package cz.cvut.fel.schematicEditor.export;

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;

/**
 * Interface for export classes. This classes are responsible for {@link SceneGraph} visualisation.
 * 
 * @author urban.kravjansky
 * 
 */
public interface Export {

    /**
     * Exports data obtained from {@link SceneGraph} into <code>output</code> device. Exporting methods depend on export
     * filter.
     * 
     * @param sceneGraph scene graph being drawn.
     * @param output output device, where drawing is generated.
     */
    public void export(SceneGraph sceneGraph, Object output);
}
