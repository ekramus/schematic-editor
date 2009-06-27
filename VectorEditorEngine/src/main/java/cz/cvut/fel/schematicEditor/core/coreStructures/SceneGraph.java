package cz.cvut.fel.schematicEditor.core.coreStructures;

import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import cz.cvut.fel.schematicEditor.core.coreStructures.sceneGraph.SceneGraphUpdateEvent;
import cz.cvut.fel.schematicEditor.core.coreStructures.sceneGraph.SceneGraphUpdateListener;
import cz.cvut.fel.schematicEditor.graphNode.ElementNode;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.Node;
import cz.cvut.fel.schematicEditor.graphNode.NodeFactory;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.types.SceneGraphIterator;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

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
     * This is the default constructor.
     */
    public SceneGraph() {
        logger = Logger.getLogger(this.getClass().getName());

        // TODO add some stuff later
        this.editNode = null;

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
     * @param zoomFactor to use.
     */
    public void setEditNode(Rectangle2D.Double rectangle, double zoomFactor) {
        this.editNode = this.topNode.findHit(rectangle, zoomFactor);
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
     * @deprecated This method returns list of bounding Boxes of each group. It should be not used, as it does not
     *             provide direct link to Elements , which Bounding Box is linked to which element.
     * @return
     */
    @Deprecated
    public LinkedList<UnitRectangle> getBouningBoxList() {
        LinkedList<UnitRectangle> result = new LinkedList<UnitRectangle>();

        for (Node node : this) {
            if (node instanceof GroupNode) {
                result.add(((GroupNode) node).getBounds());
            }
        }

        return result;
    }

    /**
     * This method returns LinkedList of ElementNodes within given areaOfInterest.
     *
     * @param areaOfInterest rectangular area, in which are (possibly) some ElementNodes.
     * @return LinkedList of GroupNodes, which were located at least partly inside given areaOfInterest.
     */
    public LinkedList<ElementNode> getElementNodeList(Rectangle2D.Double areaOfInterest) {
        LinkedList<ElementNode> result = new LinkedList<ElementNode>();

        Unit boundModifier = new Pixel(0);
        for (Node node : this) {
            if (node instanceof GroupNode) {
                // get actual bound modifier from parameter node
                boundModifier = ((GroupNode) node).getChildrenParameterNode().getWidth();
            }
            if (node instanceof ElementNode) {
                ElementNode en = (ElementNode) node;
                if (areaOfInterest.contains(en.getBounds(boundModifier)) || areaOfInterest.intersects(en
                        .getBounds(boundModifier))) {
                    result.add((ElementNode) node);
                }
            }
        }

        return result;
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
     * Serializes given {@link GroupNode} into given file.
     *
     * @param file Path to file, where should be {@link GroupNode} serialized.
     */
    public void serialize(File file) {
        XStream xstream = new XStream(new DomDriver());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            processAnnotations(xstream);
            xstream.toXML(getTopNode(), bw);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Serializes given {@link GroupNode} and return it as a String.
     */
    public String serialize() {
        XStream xstream = new XStream(new DomDriver());
        String result;

        processAnnotations(xstream);
        result = xstream.toXML(getTopNode());

        return result;
    }

    /**
     * Deserializes {@link GroupNode} from given file.
     *
     * @param clazz Class of deserialized {@link GroupNode}.
     * @param file Path to file, where is serialized {@link GroupNode}.
     * @return Deserialized {@link GroupNode} class.
     */
    public static GroupNode deserialize(Class<? extends GroupNode> clazz, File file) {
        XStream xstream = new XStream(new DomDriver());

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            processAnnotations(xstream);
            return (GroupNode) xstream.fromXML(br);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Deserializes {@link PartNode} from given file.
     *
     * @param clazz Class of deserialized {@link PartNode}.
     * @param file Path to file, where is serialized {@link PartNode}.
     * @return Deserialized {@link PartNode} class.
     */
    public static PartNode deserialize(Class<? extends PartNode> clazz, File file) {
        XStream xstream = new XStream(new DomDriver());

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            processAnnotations(xstream);
            return (PartNode) xstream.fromXML(br);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Deserializes {@link GroupNode} from given file.
     *
     * @param clazz Class of deserialized {@link GroupNode}.
     * @param session Serialized session in form of {@link String}.
     * @return Deserialized {@link GroupNode} class.
     */
    public static GroupNode deserialize(Class<? extends GroupNode> clazz, String session) {
        XStream xstream = new XStream(new DomDriver());

        processAnnotations(xstream);
        return (GroupNode) xstream.fromXML(session);
    }

    /**
     * Processes all {@link XStream} annotations in entered classes.
     *
     * @param xstream {@link XStream} instance to configure.
     */
    private static void processAnnotations(XStream xstream) {
        xstream.processAnnotations(NodeFactory.getInstance().getClassArray());
        xstream.processAnnotations(Pixel.class);
    }
}
