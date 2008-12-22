package cz.cvut.fel.schematicEditor.graphNode;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.ElementModificator;
import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.support.Transformation;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

/**
 * This class represents <em>Group node</em> in scene graph. Group node is node which groups other nodes as its
 * children, allowing existence of tree-like scheme structures.
 *
 * @author Urban Kravjansky
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
    private LinkedList<ElementNode>        childrenElementList;
    /**
     * This field represents list of all transformation nodes
     */
    @Deprecated
    private LinkedList<TransformationNode> childrenTransformationList;
    /**
     * This field represents parameters node
     */
    private ParameterNode                  chidrenParameterNode;
    /**
     * Contains element, which is edited in this group.
     */
    private Element                        editedElement;

    /**
     * Default constructor. It initializes all fields with default values, thus creating empty {@link GroupNode}.
     */
    public GroupNode() {
        super();

        logger = Logger.getLogger(this.getClass().getName());

        init();
    }

    /**
     * Initialize new {@link GroupNode}.
     */
    public void init() {
        setChildrenGroupList(new LinkedList<GroupNode>());
        setChildrenElementList(new LinkedList<ElementNode>());
        setChildrenTransformationList(new LinkedList<TransformationNode>());
        setChidrenParameterNode(null);
        setEditedElement(null);
    }

    /**
     * Initialize {@link GroupNode} with given {@link GroupNode}. Children elements are not duplicated.
     *
     * @param topNode {@link GroupNode} used for initialization of this node.
     */
    public void init(GroupNode topNode) {
        setChildrenGroupList(topNode.getChildrenGroupList());
        setChildrenElementList(topNode.getChildrenElementList());
        setChildrenTransformationList(topNode.getChildrenTransformationList());
        setChidrenParameterNode(topNode.getChildrenParameterNode());
        setEditedElement(null);
    }

    /**
     * Adds any {@link Node} as child into this {@link GroupNode}. Node is saved into correct field.
     *
     * @param child child node to add.
     */
    public void add(Node child) {
        if (child instanceof GroupNode) {
            this.childrenGroupList.add((GroupNode) child);
        } else if (child instanceof ElementNode) {
            this.childrenElementList.add((ElementNode) child);
        } else if (child instanceof TransformationNode) {
            logger.trace("Adding transformation node: " + child.toString());
            logger.trace("List of transformation nodes: " + child);

            addSpecificTransformation(((TransformationNode) child).getTransformation());
        } else if (child instanceof ParameterNode) {
            this.chidrenParameterNode = (ParameterNode) child;
        } else {
            // TODO add handling
        }
        child.setParent(this);
    }

    private void addSpecificTransformation(Transformation t) {
        for (ElementNode elementNode : getChildrenElementList()) {
            elementNode.modifyCoordinates(t);
        }

        for (GroupNode groupNode : getChildrenGroupList()) {
            groupNode.addSpecificTransformation(t);
        }
    }

    /**
     * Returns transformation applied on this {@link GroupNode}.
     *
     * @return Transformation made as matrix multiplication of all contained transformations.
     */
    @Deprecated
    public Transformation getTransformation() {
        Transformation t = new Transformation(Transformation.getIdentity());
        for (Node child : this.childrenTransformationList) {
            t = Transformation.multiply(t, ((TransformationNode) child).getTransformation());
        }
        return t;
    }

    /**
     * Indicates, whether {@link GroupNode} is hit by mouse cursor or not.
     *
     * @param rectangle
     * @return
     */
    public boolean isHit(Rectangle2D rectangle) {
        if (isDisabled()) {
            return false;
        }

        // TODO implement hit trigger into elements, so selection can be faster
        for (int i = getChildrenElementList().size() - 1; i >= 0; i--) {
            ElementNode child = getChildrenElementList().get(i);
            if (child.isHit(rectangle)) {
                logger.trace("element HIT: " + child);
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

    public boolean delete(GroupNode selectedGroupNode) {
        if (isDisabled()) {
            return false;
        }

        GroupNode pgn = selectedGroupNode.getParent();

        for (int i = pgn.getChildrenGroupList().size() - 1; i >= 0; i--) {
            GroupNode child = pgn.getChildrenGroupList().get(i);
            if (child == selectedGroupNode) {
                // disable group (it acts as remove, but it stays in Scene
                // Graph)
                child.setDisabled(true);
                // pgn.getChildrenGroupList().remove(i);
                return true;
            }
        }

        return false;
    }

    public boolean undelete(GroupNode selectedGroupNode) {
        if (isDisabled()) {
            return false;
        }

        selectedGroupNode.setDisabled(false);

        return true;
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
     * @return children GroupList
     */
    public LinkedList<GroupNode> getChildrenGroupList() {
        return this.childrenGroupList;
    }

    /**
     * This method returns GroupNode with enabled Nodes only.
     *
     * @return GroupNode with only enabled Nodes
     */
    public GroupNode getEnabledOnly() {
        GroupNode result = (GroupNode) duplicate();

        if (result.isDisabled()) {
            return null;
        }

        for (int i = result.getChildrenElementList().size() - 1; i >= 0; i--) {
            ElementNode child = result.getChildrenElementList().get(i);
            if (child.isDisabled()) {
                result.getChildrenElementList().remove(i);
            }
        }
        for (int i = result.getChildrenGroupList().size() - 1; i >= 0; i--) {
            GroupNode child = result.getChildrenGroupList().get(i);
            if (child.isDisabled()) {
                result.getChildrenGroupList().remove(i);
            } else {
                result.getChildrenGroupList().set(i, child.getEnabledOnly());
            }
        }

        return result;
    }

    /**
     * This method returns list of ElementNode children
     *
     * @return childrenElementList;
     */
    public LinkedList<ElementNode> getChildrenElementList() {
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
        String result = this.id + "[GroupNode]{";
        result += "#GroupList:";
        for (GroupNode g : this.childrenGroupList) {
            result += "(" + g.toString() + ")";
        }
        result += "#ElementList:";
        for (ElementNode e : this.childrenElementList) {
            result += "(" + e.toString() + ")";
        }
        result += "}";
        return result;
    }

    /**
     * Getter for <code>NodeArray</code> with corrected <code>TransformationNode</code> and <code>ParameterNode</code>.
     *
     * @param tn transformation node.
     * @param pn parameter node.
     * @return <code>ArrayList</code> of nodes.
     */
    public ArrayList<Node> getNodeArray(TransformationNode tn, ParameterNode pn) {
        ArrayList<Node> result = new ArrayList<Node>();
        ParameterNode p = null;
        TransformationNode t = null;

        // get correct ParameterNode for this level.
        if (pn == null) {
            p = this.chidrenParameterNode;
        } else {
            p = this.chidrenParameterNode.combine(pn);
        }
        // get correct Transformation for this level
        if (tn == null) {
            t = new TransformationNode(getTransformation());
        } else {
            t = new TransformationNode(Transformation.multiply(getTransformation(), tn.getTransformation()));
        }

        result.add(t);
        result.add(p);
        for (ElementNode elementNode : this.childrenElementList) {
            if (!elementNode.isDisabled()) {
                if (elementNode.getElement().getElementType() == ElementType.T_PART) {
                    PartNode partNode = (PartNode) elementNode;
                    result.addAll(partNode.getPartGroupNode().getNodeArray(t, p));
                    // TODO optimize, this doubles data value (as complete part node is sent twice into queue)
                    // partNode is sent as second, because of exporting priority
                    result.add(partNode);
                } else {
                    result.add(elementNode);
                }
            }
        }
        for (GroupNode groupNode : this.childrenGroupList) {
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
    public GroupNode findHit(Rectangle2D rectangle, double zoomFactor) {
        if (isDisabled()) {
            return null;
        }

        // modify rectangle if zoomFactor != 1
        if (zoomFactor != 1) {
            rectangle = new Rectangle2D.Double(rectangle.getX() / zoomFactor, rectangle.getY() / zoomFactor, rectangle
                    .getWidth(), rectangle.getHeight());
        }

        for (int i = getChildrenElementList().size() - 1; i >= 0; i--) {
            ElementNode child = getChildrenElementList().get(i);
            if (child.isHit(getTransformation().shiftInverse(rectangle))) {
                return this;
            }
        }
        for (int i = getChildrenGroupList().size() - 1; i >= 0; i--) {
            GroupNode child = getChildrenGroupList().get(i);
            GroupNode gn = child.findHit(rectangle, 1);
            if (gn != null) {
                return gn;
            }
        }

        return null;
    }

    /**
     * Computes bounds of all elements contained inside this {@link GroupNode}.
     *
     * @return bounds union of all elements contained in this {@link GroupNode}.
     */
    public UnitRectangle getBounds() {
        UnitRectangle result = null;

        if (isDisabled()) {
            return result;
        }

        // get bounds from child elements
        Unit width = getChildrenParameterNode().getWidth();
        for (ElementNode child : getChildrenElementList()) {
            result = new UnitRectangle(child.getBounds(width).createUnion(result));
        }

        // get bounds from child group nodes
        for (GroupNode groupNode : getChildrenGroupList()) {
            result = new UnitRectangle(groupNode.getBounds().createUnion(result));
        }

        return result;
    }

    /**
     * Detects, whether given rectangle is in edit zone. If it is, it means, edit should be invoked.
     *
     * @param r2d rectangle around pointer.
     * @return <code>true</code> if given rectangle contains any edit point from group, else <code>false</code>.
     */
    public boolean startEdit(Rectangle2D r2d) {
        if (isDisabled()) {
            return false;
        }

        // TODO implement hit trigger into elements, so selection can be faster
        for (int i = getChildrenElementList().size() - 1; i >= 0; i--) {
            ElementNode child = getChildrenElementList().get(i);
            setEditedElement(child.startEdit(getTransformation().shiftInverse(r2d)));
            if (getEditedElement() != null) {
                logger.debug("element edit zone HIT: " + child + " transformation: " + getTransformation());
                return true;
            }
        }
        for (int i = getChildrenGroupList().size() - 1; i >= 0; i--) {
            GroupNode child = getChildrenGroupList().get(i);
            if (child.startEdit(r2d)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Detects, whether given rectangle is in rotate zone. If it is, it means, rotate should be invoked.
     *
     * @param r2d rectangle around pointer.
     * @return <code>true</code> if given rectangle is in rotate point of whole group, else <code>false</code>.
     */
    public boolean isRotateZone(Rectangle2D r2d) {
        // TODO implement functionality
        return false;
    }

    public boolean isEdited() {
        for (int i = getChildrenElementList().size() - 1; i >= 0; i--) {
            ElementNode child = getChildrenElementList().get(i);
            if (child.isEdited()) {
                return true;
            }
        }
        for (int i = getChildrenGroupList().size() - 1; i >= 0; i--) {
            GroupNode child = getChildrenGroupList().get(i);
            if (child.isEdited()) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param ep
     */
    public void stopEdit(UnitPoint delta) {
        if (isEdited()) {
            for (int i = getChildrenElementList().size() - 1; i >= 0; i--) {
                ElementNode child = getChildrenElementList().get(i);
                if (child.isEdited()) {
                    child.stopEdit(delta);
                    return;
                }
            }
            for (int i = getChildrenGroupList().size() - 1; i >= 0; i--) {
                GroupNode child = getChildrenGroupList().get(i);
                if (child.isEdited()) {
                    child.stopEdit(delta);
                    return;
                }
            }
        }
    }

    /**
     * @param ep
     */
    public void switchEdit(UnitPoint delta) {
        if (isEdited()) {
            for (int i = getChildrenElementList().size() - 1; i >= 0; i--) {
                ElementNode child = getChildrenElementList().get(i);
                if (child.isEdited()) {
                    child.switchEdit(delta);
                    return;
                }
            }
            for (int i = getChildrenGroupList().size() - 1; i >= 0; i--) {
                GroupNode child = getChildrenGroupList().get(i);
                if (child.isEdited()) {
                    child.switchEdit(delta);
                    return;
                }
            }
        }
    }

    /**
     * @see cz.cvut.fel.schematicEditor.graphNode.Node#duplicate()
     */
    @Override
    public Node duplicate() {
        GroupNode result = new GroupNode();
        ParameterNode chPN = null;

        result.setDisabled(isDisabled());

        try {
            chPN = (ParameterNode) getChildrenParameterNode().duplicate();
        } catch (NullPointerException npe) {
            // nothing to do
        }
        result.setChidrenParameterNode(chPN);

        LinkedList<ElementNode> chEL = new LinkedList<ElementNode>();
        for (ElementNode elementNode : getChildrenElementList()) {
            chEL.add((ElementNode) elementNode.duplicate());
        }
        result.setChildrenElementList(chEL);

        LinkedList<GroupNode> chGL = new LinkedList<GroupNode>();
        for (GroupNode groupNode : getChildrenGroupList()) {
            chGL.add((GroupNode) groupNode.duplicate());
        }
        result.setChildrenGroupList(chGL);

        LinkedList<TransformationNode> chTL = new LinkedList<TransformationNode>();
        for (TransformationNode transformationNode : getChildrenTransformationList()) {
            chTL.add((TransformationNode) transformationNode.duplicate());
        }
        result.setChildrenTransformationList(chTL);

        logger.trace(result);

        return result;
    }

    /**
     * @param chidrenParameterNode the chidrenParameterNode to set
     */
    private void setChidrenParameterNode(ParameterNode chidrenParameterNode) {
        this.chidrenParameterNode = chidrenParameterNode;
    }

    /**
     * @param childrenElementList the childrenElementList to set
     */
    private void setChildrenElementList(LinkedList<ElementNode> childrenElementList) {
        this.childrenElementList = childrenElementList;
    }

    /**
     * @param childrenGroupList the childrenGroupList to set
     */
    private void setChildrenGroupList(LinkedList<GroupNode> childrenGroupList) {
        this.childrenGroupList = childrenGroupList;
    }

    /**
     * @param childrenTransformationList the childrenTransformationList to set
     */
    private void setChildrenTransformationList(LinkedList<TransformationNode> childrenTransformationList) {
        this.childrenTransformationList = childrenTransformationList;
    }

    /**
     * @return the childrenTransformationList
     */
    private LinkedList<TransformationNode> getChildrenTransformationList() {
        return this.childrenTransformationList;
    }

    /**
     * Calculates number of coordinates used for construction of {@link GroupNode}.
     *
     * @return
     */
    public int getNumberOfCoordinates() {
        int result = 0;

        for (ElementNode elementNode : getChildrenElementList()) {
            result += elementNode.getElement().getNumberOfCoordinates();
        }

        return result;
    }

    /**
     * Getter for {@link GroupNode} elements {@link ElementModificator}.
     *
     * @return
     */
    public ElementModificator getElementModificator() {
        ElementModificator result = ElementModificator.SYMMETRIC_ELEMENT;

        for (ElementNode elementNode : getChildrenElementList()) {
            result = result.or(elementNode.getElement().getElementModificator());
        }

        return result;
    }

    public void setElementModificator(ElementModificator elementModificator) {
        for (ElementNode elementNode : getChildrenElementList()) {
            elementNode.getElement().setElementModificator(elementModificator);
        }
    }

    public void setXY(Vector<Unit> x, Vector<Unit> y) {
        // TODO this implementation changes only coordinates for first element.
        // Change it!
        Element element = getChildrenElementList().getFirst().getElement();

        element.setX(new Vector<Unit>(x));
        element.setY(new Vector<Unit>(y));
    }

    public ElementType getElementType() {
        // TODO this implementation gets only element type for first element.
        // Change it!
        return getChildrenElementList().getFirst().getElement().getElementType();
    }

    /**
     * @param editedElement the editedElement to set
     */
    private void setEditedElement(Element editedElement) {
        this.editedElement = editedElement;
    }

    /**
     * @return the editedElement
     */
    public Element getEditedElement() {
        return this.editedElement;
    }

    /**
     * @return {@link Vector} of coordinates of all {@link Part} elements.
     */
    public Vector<UnitPoint> getPartsCoordinates() {
        Vector<UnitPoint> result = new Vector<UnitPoint>();

        if (isDisabled()) {
            return result;
        }

        for (ElementNode childNode : getChildrenElementList()) {
            switch (childNode.getElement().getElementType()) {
                case T_CONNECTOR:
                case T_WIRE:
                    Element element = childNode.getElement();
                    for (int i = 0; i < element.getX().size(); i++) {
                        result.add(new UnitPoint(element.getX().get(i), element.getY().get(i)));
                    }
                    break;
                case T_PART:
                    PartNode partNode = (PartNode) childNode;
                    // add all parts coordinates from part group node
                    result.addAll(partNode.getPartGroupNode().getPartsCoordinates());

                    // add all part connector coordinates
                    result.addAll(partNode.getPartConnectorsCoordinates());
                    break;
                default:
                    break;
            }

        }

        for (GroupNode groupNode : getChildrenGroupList()) {
            result.addAll(groupNode.getPartsCoordinates());
        }

        return result;
    }

    /**
     * Retrieves {@link Vector} of connector nodes and removes them from group node, so they do not duplicate.
     *
     * @return
     */
    public Vector<ConnectorNode> getAndRemoveConnectorNodes() {
        Vector<ConnectorNode> result = new Vector<ConnectorNode>();

        if (isDisabled()) {
            return result;
        }

        for (int i = getChildrenElementList().size() - 1; i >= 0; i--) {
            ElementNode childNode = getChildrenElementList().get(i);
            switch (childNode.getElement().getElementType()) {
                case T_CONNECTOR:
                    result.add((ConnectorNode) childNode.duplicate());
                    getChildrenElementList().remove(i);
                    break;
                default:
                    break;
            }
        }

        for (GroupNode groupNode : getChildrenGroupList()) {
            result.addAll(groupNode.getAndRemoveConnectorNodes());
        }

        if (getChildrenElementList().isEmpty() && getChildrenGroupList().isEmpty()) {
            setDisabled(true);
        }

        return result;
    }
}
