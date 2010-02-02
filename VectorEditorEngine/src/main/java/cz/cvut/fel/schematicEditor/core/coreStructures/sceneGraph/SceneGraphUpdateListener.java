package cz.cvut.fel.schematicEditor.core.coreStructures.sceneGraph;

import java.util.EventListener;
import java.util.EventObject;

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;

/**
 * Listener interface for {@link SceneGraphUpdateEvent}.
 *
 * @author Urban Kravjansky
 */
public interface SceneGraphUpdateListener extends EventListener {
    /**
     * When {@link SceneGraph} update occurs, this method is invoked.
     *
     * @param e {@link EventObject} associated with event.
     */
    public void sceneGraphUpdateOccured(SceneGraphUpdateEvent e);
}
