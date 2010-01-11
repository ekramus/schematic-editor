package cz.cvut.fel.schematicEditor.types;

import java.util.ArrayList;
import java.util.Iterator;

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.original.graphNode.Node;


/**
 * @author uk
 */
public class SceneGraphIterator implements Iterator<Node> {
    /**
     * This field points to <code>SceneGraph</code> structure.
     */
    private SceneGraph      sg;
    /**
     * This field represents <code>ArrayList</code> representation of <code>Node</code> in
     * <code>SceneGraph</code>.
     */
    private ArrayList<Node> nodeArray;
    /**
     * Iterator of <code>nodeArray</code>.
     */
    private Iterator<Node>  it;

    /**
     * This is constructor.
     * 
     * @param sg
     *            pointer to <code>SceneGraph</code>.
     */
    public SceneGraphIterator(SceneGraph sg) {
        this.sg = sg;
        nodeArray = this.sg.getSceneGraphArray();
        it = nodeArray.iterator();
    }

    /**
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
        return it.hasNext();
    }

    /**
     * @see java.util.Iterator#next()
     */
    public Node next() {
        return it.next();
    }

    /**
     * @see java.util.Iterator#remove()
     */
    public void remove() {
        it.remove();
    }

    /**
     * This method regenerates this <code>SceneGraph</code> iterator.
     */
    public void regenerate() {
        // TODO add regeneration code
    }
}
