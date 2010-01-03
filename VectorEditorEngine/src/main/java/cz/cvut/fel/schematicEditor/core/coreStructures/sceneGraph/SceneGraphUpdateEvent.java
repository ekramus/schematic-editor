package cz.cvut.fel.schematicEditor.core.coreStructures.sceneGraph;

import java.util.EventObject;

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;

/**
 * This class implements {@link SceneGraph} update event.
 *
 * @author Urban Kravjansky
 *
 */
public class SceneGraphUpdateEvent extends EventObject {

    /**
     * This method instantiates new instance.
     *
     * @param source
     */
    public SceneGraphUpdateEvent(Object source) {
        super(source);
    }

}
