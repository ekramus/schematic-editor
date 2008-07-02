package cz.cvut.fel.schematicEditor.graphNode;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Vector;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.application.Gui;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.manipulation.Select;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.support.Support;
import cz.cvut.fel.schematicEditor.types.Transformation;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

/**
 * This class represents Group Node in scene graph.
 * 
 * @author uk
 */
public class GroupNode extends Node {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger                  logger;
    /**
     * This field represents list of all children group nodes.
     */
    private LinkedList<GroupNode>          childrenGroupList;
    /**
     * This field represents list of all children element nodes.
     */
    private Vector<ElementNode>            childrenElementList;
    /**
     * This field represents list of all transformation nodes
     */
    private LinkedList<TransformationNode> childrenTransformationList;
    /**
     * This field represents parameters node
     */
    private ParameterNode                  chidrenParameterNode;

    /**
     * This is default constructor.
     */
    public GroupNode() {
        super();

        logger = Logger.getLogger(Gui.class.getName());

        childrenGroupList = new LinkedList<GroupNode>();
        childrenElementList = new Vector<ElementNode>();
        childrenTransformationList = new LinkedList<TransformationNode>();
        chidrenParameterNode = null;
    }

    /**
     * This method adds child node into this node.
     * 
     * @param child
     *            child node to add.
     */
    public void add(Node child) {
        if (child instanceof GroupNode) {
            // System.out.println("Adding group node: " + child.toString());
            this.childrenGroupList.add((GroupNode) child);
        } else if (child instanceof ElementNode) {
            // System.out.println("Adding element node: " + child.toString());
            this.childrenElementList.add((ElementNode) child);
        } else if (child instanceof TransformationNode) {
            // System.out.println("Adding transformation node: " + child.toString());
            this.childrenTransformationList.add((TransformationNode) child);
        } else if (child instanceof ParameterNode) {
            // System.out.println("Adding parameter node: " + child.toString());
            this.chidrenParameterNode = (ParameterNode) child;
        } else {
            // TODO add handling
        }
        child.setParent(this);
    }

    /**
     * This method returns transformation applied on this group.
     * 
     * @return
     */
    public Transformation getTransformation() {
        Transformation t = new Transformation(Transformation.getIdentity());
        for (Node child : childrenTransformationList) {
            t = Transformation.multiply(t, ((TransformationNode) child).getTransformation());
        }
        return t;
    }

    /**
     * Getter for group
     * 
     * @return group node.
     */
    public GroupNode getGroup() {
        GroupNode result = null;

        for (Node child : childrenGroupList) {
            // TODO shape manipulation
        }

        return result;
    }

    public boolean isHit(Rectangle2D.Double rectangle) {
        if (isDisabled()) {
            return false;
        }

        // TODO implement hit trigger into elements, so selection can be faster
        for (int i = getChildrenElementList().size() - 1; i >= 0; i--) {
            ElementNode child = getChildrenElementList().get(i);
            if (child.isHit(getTransformation().shiftInverse(rectangle))) {
                logger.debug("element HIT: " + child + " transformation: " + getTransformation());
                return true;
            }
        }
        for (int i = getChildrenGroupList().size() - 1; i >= 0; i--) {
            GroupNode child = getChildrenGroupList().get(i);
            if (child.isHit(rectangle)) {
                return true;
            }
        }

        return false;
    }

    public boolean deleteSelected() {
        if (isDisabled()) {
            return false;
        }

        Select select;
        try {
            select = (Select) Structures.getManipulation();
            GroupNode gn = select.getManipulatedGroup();
            GroupNode pgn = gn.getParent();

            for (int i = pgn.getChildrenGroupList().size() - 1; i >= 0; i--) {
                GroupNode child = pgn.getChildrenGroupList().get(i);
                if (child == gn) {
                    pgn.getChildrenGroupList().remove(i);
                    return true;
                }
            }
        } catch (UnknownManipulationException e) {
            logger.error(e.getMessage());
        }

        return false;
    }

    /**
     * Delete element, which is hit.
     * 
     * @param r2d
     * @return <code>true</code>, if hit and deleted, else <code>false</code>.
     */
    public boolean deleteHit(Rectangle2D.Double r2d) {
        if (isDisabled()) {
            return false;
        }

        for (int i = getChildrenElementList().size() - 1; i >= 0; i--) {
            ElementNode child = getChildrenElementList().get(i);
            if (child.isHit(getTransformation().shiftInverse(r2d))) {
                getChildrenElementList().remove(i);
                return true;
            }
        }
        for (int i = getChildrenGroupList().size() - 1; i >= 0; i--) {
            GroupNode child = getChildrenGroupList().get(i);
            if (child.deleteHit(r2d)) {
                return true;
            }
        }

        return false;
    }

    /**
     * This method returns list of GroupNode children
     * 
     * @return childrenGroupList;
     */
    public LinkedList<GroupNode> getChildrenGroupList() {
        return this.childrenGroupList;
    }

    /**
     * This method returns list of ElementNode children
     * 
     * @return childrenElementList;
     */
    public Vector<ElementNode> getChildrenElementList() {
        return this.childrenElementList;
    }

    /**
     * This method returns ParameterNode
     * 
     * @return childrenParameterNode;
     */
    public ParameterNode getChildrenParameterNode() {
        return this.chidrenParameterNode;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String result = id + "[GroupNode]{";
        result += "#GroupList:";
        for (GroupNode g : childrenGroupList) {
            result += "(" + g.toString() + ")";
        }
        result += "#ElementList:";
        for (ElementNode e : childrenElementList) {
            result += "(" + e.toString() + ")";
        }
        result += "}";
        return result;
    }

    /**
     * Getter for <code>NodeArray</code> with corrected <code>TransformationNode</code> and
     * <code>ParameterNode</code>.
     * 
     * @param tn
     *            transformation node.
     * @param pn
     *            parameter node.
     * @return <code>ArrayList</code> of nodes.
     */
    public ArrayList<Node> getNodeArray(TransformationNode tn, ParameterNode pn) {
        ArrayList<Node> result = new ArrayList<Node>();
        ParameterNode p = null;
        TransformationNode t = null;

        // get correct ParameterNode for this level.
        if (pn == null) {
            p = chidrenParameterNode;
        } else {
            p = chidrenParameterNode.combine(pn);
        }
        // get correct Transformation for this level
        if (tn == null) {
            t = new TransformationNode(getTransformation());
        } else {
            t = new TransformationNode(Transformation.multiply(getTransformation(), tn.getTransformation()));
        }

        result.add(t);
        result.add(p);
        for (ElementNode elementNode : childrenElementList) {
            if (!elementNode.isDisabled()) {
                result.add(elementNode);
            }
        }
        for (GroupNode groupNode : childrenGroupList) {
            // result.add(t);
            // result.add(p);
            if (!groupNode.isDisabled()) {
                result.addAll(groupNode.getNodeArray(t, p));
            }
        }

        return result;
    }

    /**
     * @param point
     * @return
     */
    public GroupNode findHit(Rectangle2D.Double rectangle) {
        if (isDisabled()) {
            return null;
        }

        for (int i = getChildrenElementList().size() - 1; i >= 0; i--) {
            ElementNode child = getChildrenElementList().get(i);
            if (child.isHit(getTransformation().shiftInverse(rectangle))) {
                return this;
            }
        }
        for (int i = getChildrenGroupList().size() - 1; i >= 0; i--) {
            GroupNode child = getChildrenGroupList().get(i);
            GroupNode gn = child.findHit(rectangle);
            if (gn != null) {
                return gn;
            }
        }

        return null;
    }

    public UnitRectangle getBounds() {
        // get bounds of first element, so there are some defined
        UnitRectangle bounds = getChildrenElementList().firstElement().getBounds(getChildrenParameterNode().getWidth());

        UnitRectangle result = new UnitRectangle(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());

        logger.debug("1st getBounds: " + result);

        for (ElementNode child : getChildrenElementList()) {
            // union child bounds with result
            result = (UnitRectangle) result.createUnion(bounds);
            logger.debug("getBounds: " + result);
        }

        return result;
    }

    /**
     * 
     */
    public void removeLastTransformation() {
        try {
            childrenTransformationList.removeLast();
        } catch (NoSuchElementException nsee) {
            logger.debug("no transformation to remove");
        }
    }

    /**
     * Detects, whether given rectangle is in edit zone. If it is, it means, edit should be invoked.
     * 
     * @param r2d
     *            rectangle around pointer.
     * @return <code>true</code> if given rectangle contains any edit point from group, else <code>false</code>.
     */
    public boolean isEditZone(Rectangle2D.Double r2d) {
        // TODO implement functionality
        return false;
    }

    /**
     * Detects, whether given rectangle is in rotate zone. If it is, it means, rotate should be invoked.
     * 
     * @param r2d
     *            rectangle around pointer.
     * @return <code>true</code> if given rectangle is in rotate point of whole group, else <code>false</code>.
     */
    public boolean isRotateZone(Rectangle2D.Double r2d) {
        // TODO implement functionality
        return false;
    }
}
