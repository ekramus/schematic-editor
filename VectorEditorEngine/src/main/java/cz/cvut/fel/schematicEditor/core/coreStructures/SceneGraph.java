package cz.cvut.fel.schematicEditor.core.coreStructures;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.core.PluginData;
import cz.cvut.fel.schematicEditor.core.coreStructures.sceneGraph.SceneGraphUpdateEvent;
import cz.cvut.fel.schematicEditor.core.coreStructures.sceneGraph.SceneGraphUpdateListener;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.Node;
import cz.cvut.fel.schematicEditor.graphNode.NodeFactory;
import cz.cvut.fel.schematicEditor.types.SceneGraphIterator;

/**
 * This class represents <em>Scene Graph</em>.
 *
 * @author Urban Kravjansky
 */
public class SceneGraph implements Iterable<Node> {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger                    logger;
    /**
     * This field represents <code>SceneGraph</code> iterator.
     */
    private SceneGraphIterator               sgi;
    /**
     * This field represents top node of <em>Scene Graph</em>.
     */
    private GroupNode                        topNode;
    /**
     * This field represents edited node of <em>SceneGraph</em>.
     */
    private GroupNode                        editNode;
    /**
     * {@link Vector} of {@link SceneGraphUpdateListener}s for monitoring update events. They cannot be serialized,
     * after deserialization instance of {@link SceneGraph} has to be updated with current update listeners.
     */
    private Vector<SceneGraphUpdateListener> listeners;
    /**
     * Plugin data.
     */
    private PluginData                       pluginData;

    /**
     * This is the default constructor.
     */
    public SceneGraph() {
        logger = Logger.getLogger(this.getClass().getName());

        // TODO add some stuff later
        this.editNode = null;
        setPluginData(new PluginData());

        setListeners(new Vector<SceneGraphUpdateListener>());
    }

    /**
     * @return the editNode
     */
    public GroupNode getEditNode() {
        return this.editNode;
    }

    /**
     * This method generates <code>ArrayList</code> from this <code>SceneGraph</code>.
     *
     * @return <code>ArrayList</code> of this <code>SceneGraph</code>.
     */
    public ArrayList<Node> getSceneGraphArray() {
        logger.trace("top node: " + this.topNode);
        logger.trace("node array: " + this.topNode.getNodeArray(null, null));
        return this.topNode.getNodeArray(null, null);
    }

    /**
     * Getter for <code>topNode</code>.
     *
     * @return The <code>topNode</code>.
     */
    public GroupNode getTopNode() {
        return this.topNode;
    }

    /**
     * This method implemets iterator throug nodes.
     *
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<Node> iterator() {
        // TODO add modification trigger to speed up iterator generation.
        this.sgi = new SceneGraphIterator(this);
        return this.sgi;
    }

    /**
     * This method creates empty static predefined working SceneGraph.
     */
    public void initSceneGraph() {
        if (this.topNode == null) {
            this.topNode = NodeFactory.createGroupNode();
            this.topNode.add(NodeFactory.createParameterNode());
        } else {
            this.topNode.init();
        }
    }

    /**
     * This method initializes {@link SceneGraph} with given topNode.
     *
     * @param topNode {@link GroupNode} used for initialization.
     */
    public void initSceneGraph(GroupNode topNode) {
        if (this.topNode == null) {
            this.topNode = topNode;
        } else {
            this.topNode.init(topNode);
        }
    }

    /**
     * @param editNode the editNode to set
     * @param g2d {@link Graphics2D} context.
     * @param zoomFactor to use.
     */
    public void setEditNode(Rectangle2D.Double rectangle, double zoomFactor, Graphics2D g2d) {
        this.editNode = this.topNode.findHit(rectangle, zoomFactor, g2d);
        fireSceneGraphUpdateEvent();
    }

    /**
     * @param topNode the topNode to set
     */
    public void setTopNode(GroupNode topNode) {
        this.topNode = topNode;
        fireSceneGraphUpdateEvent();
    }

    /**
     * @param listeners the listeners to set
     */
    private void setListeners(Vector<SceneGraphUpdateListener> listeners) {
        this.listeners = listeners;
    }

    /**
     * @return the listeners
     */
    private Vector<SceneGraphUpdateListener> getListeners() {
        return this.listeners;
    }

    /**
     * Register class, which implements {@link SceneGraphUpdateListener}.
     *
     * @param sceneGraphUpdateListener implementation to register.
     */
    public void addSceneGraphUpdateListener(SceneGraphUpdateListener sceneGraphUpdateListener) {
        getListeners().add(sceneGraphUpdateListener);
    }

    /**
     * Unregister {@link SceneGraphUpdateListener}.
     *
     * @param sceneGraphUpdateListener implementation to unregister.
     */
    // removes the listener
    public void removeSceneGraphUpdateListener(SceneGraphUpdateListener sceneGraphUpdateListener) {
        getListeners().remove(sceneGraphUpdateListener);
    }

    /**
     * Fire and dispatch event to all registered {@link SceneGraphUpdateListener} instances.
     */
    public void fireSceneGraphUpdateEvent() {
        for (SceneGraphUpdateListener sceneGraphUpdateListener : getListeners()) {
            SceneGraphUpdateEvent sceneGraphUpdateEvent = new SceneGraphUpdateEvent(this);
            sceneGraphUpdateListener.sceneGraphUpdateOccured(sceneGraphUpdateEvent);
        }
    }

    /**
     * @return the pluginData
     */
    public PluginData getPluginData() {
        return this.pluginData;
    }

    /**
     * @param pluginData the pluginData to set
     */
    private void setPluginData(PluginData pluginData) {
        this.pluginData = pluginData;
    }
}
