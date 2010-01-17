package cz.cvut.fel.schematicEditor.original.graphNode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.element.element.part.Pin;
import cz.cvut.fel.schematicEditor.element.element.shape.Text;
import cz.cvut.fel.schematicEditor.support.Support;
import cz.cvut.fel.schematicEditor.support.Transformation;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

/**
 * This class represents Part Node. It contains link to {@link Part} and to {@link GroupNode} with
 * part's visual form.
 * 
 * @author Urban Kravjansky
 */
@XStreamAlias("PartNode")
public class PartNode extends ElementNode {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger   logger;
    /**
     * GroupNode containing graphic representation of part shape.
     */
    private GroupNode       partGroupNode       = null;
    /**
     * {@link ParameterNode} containing parameters of part shape.
     */
    private ParameterNode   partParameterNode   = null;
    /**
     * {@link Vector} of part connectors.
     */
    private Vector<PinNode> partConnectors      = null;
    /**
     * Top {@link GroupNode} for part labels.
     */
    private GroupNode       partLabelsGroupNode = null;

    /**
     * Static logger instance initialization.
     */
    {
        logger = Logger.getLogger(PartNode.class);
    }

    /**
     * @param partLabelsGroupNode
     *            the partLabels to set
     */
    private void setPartLabelsGroupNode(GroupNode partLabelsGroupNode) {
        this.partLabelsGroupNode = partLabelsGroupNode;
    }

    /**
     * Constructor with default set of parameters. It instantiates new {@link PartNode}.
     * 
     * @param part
     *            <code>Part</code> stored in this <code>PartNode</code>.
     * @param partGroupNode
     *            graphical representation of part.
     */
    protected PartNode(Part part, GroupNode partGroupNode) {
        this(part, partGroupNode, "");
    }

    /**
     * @return the partLabels
     */
    public GroupNode getPartLabelsGroupNode() {
        return this.partLabelsGroupNode;
    }

    /**
     * Constructor with extra parameter for id.
     * 
     * @param part
     *            <code>Part</code> stored in this <code>PartNode</code>.
     * @param partGroupNode
     *            graphical representation of part.
     * @param id
     *            identifier of this <code>PartNode</code>.
     */
    protected PartNode(Part part, GroupNode partGroupNode, String id) {
        super(part, id);

        // logger = Logger.getLogger(this.getClass().getName());

        initialize(partGroupNode);
    }

    /**
     * Initializes all necessary variables.
     * 
     * @param partGroupNode
     *            {@link GroupNode} describing shape of {@link Part}.
     */
    public void initialize(GroupNode partGroupNode) {
        ParameterNode pn = partGroupNode.getChildrenParameterNode();
        // set parameter node if not set
        if (pn == null) {
            pn = new ParameterNode();
            pn.setColor(Color.RED);
            partGroupNode.add(pn);
        }

        // set part connectors and part group node
        setPartConnectors(partGroupNode.getAndRemovePinNodes());
        setPartGroupNode(partGroupNode.getEnabledOnly());

        // set part labels
        GroupNode partLabelsGroupNode = new GroupNode();

        Part part = (Part) getElement();
        String[] index = { "name", "value", "n1", "n2", "n3", "n4" };
        GroupNode g = new GroupNode();
        for (String i : index) {
            String value = part.getPartProperties().getProperty(i);

            ShapeNode s = new ShapeNode(new Text(new UnitPoint(0, 0), value));

            ParameterNode p = new ParameterNode();
            g.add(p);
            g.add(s);
            partLabelsGroupNode.add(g);
        }

        setPartLabelsGroupNode(partLabelsGroupNode);
    }

    /**
     * @see graphNode.ElementNode#isHit(java.awt.geom.Rectangle2D, Graphics2D)
     */
    @Override
    protected boolean isHit(Rectangle2D point, Graphics2D g2d) {
        // TODO fix for scaling
        boolean hit = false;

        try {
            for (GroupNode gn : getPartLabelsGroupNode().getChildrenGroupList()) {
                hit = hit || gn.isHit(point, 1, g2d);
            }
        } catch (NullPointerException e) {
            // nothing to do
        }

        return getPartGroupNode().isHit(point, 1, g2d) || hit;
    }

    /**
     * Getter for element bounds.
     * 
     * @param boundModifier
     *            modifier which affects boundary size of element.
     * @param g2d
     *            Graphics2D context.
     * @return Bounds of element.
     */
    @Override
    public UnitRectangle getBounds(Unit boundModifier, Graphics2D g2d) {
        UnitRectangle result;

        double x = getPartGroupNode().getBounds(g2d).getX() - 50 * boundModifier.doubleValue();
        double y = getPartGroupNode().getBounds(g2d).getY() - 50 * boundModifier.doubleValue();
        double w = getPartGroupNode().getBounds(g2d).getWidth() + 2 * 50
                   * boundModifier.doubleValue();
        double h = getPartGroupNode().getBounds(g2d).getHeight() + 2 * 50
                   * boundModifier.doubleValue();

        result = new UnitRectangle(x, y, w, h);

        for (PinNode cn : getPartPins()) {
            Pin c = (Pin) cn.getElement();
            result = (UnitRectangle) result.createUnion(c.getBounds(g2d));
        }

        return result;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.original.graphNode.Node#duplicate()
     */
    @Override
    protected Node duplicate() {
        PartNode result = new PartNode((Part) getElement(),
                (GroupNode) getPartGroupNode().duplicate());

        // duplicate partConnectors
        Vector<PinNode> partConnectors = new Vector<PinNode>();
        for (PinNode pinNode : getPartPins()) {
            partConnectors.add((PinNode) NodeFactory.duplicate(pinNode));
        }
        result.setPartConnectors(partConnectors);

        // duplicate partLabels
        GroupNode partLabelsGroupNode = new GroupNode();
        for (GroupNode groupNode : getPartLabelsGroupNode().getChildrenGroupList()) {
            partLabelsGroupNode.add((GroupNode) NodeFactory.duplicate(groupNode));
        }
        result.setPartLabelsGroupNode(partLabelsGroupNode);

        return result;
    }

    /**
     * @param partGroupNode
     *            the partGroupNode to set
     */
    private void setPartGroupNode(GroupNode partGroupNode) {
        this.partGroupNode = partGroupNode;
    }

    /**
     * @return the partGroupNode
     */
    public GroupNode getPartGroupNode() {
        return this.partGroupNode;
    }

    /**
     * @return the partParameterNode
     */
    public ParameterNode getPartParameterNode() {
        return this.partParameterNode;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.original.graphNode.ElementNode#toString()
     */
    @Override
    public String toString() {
        return "[PartNode]";
    }

    /**
     * @see cz.cvut.fel.schematicEditor.original.graphNode.ElementNode#modifyCoordinates(cz.cvut.fel.schematicEditor.support.Transformation)
     */
    @Override
    public void modifyCoordinates(Transformation t) {
        // modify coordinates of graphic representation of part
        getPartGroupNode().add(new TransformationNode(t));

        // modify coordinates of part connectors
        for (PinNode pinNode : getPartPins()) {
            pinNode.modifyCoordinates(t);
        }

        // modify coordinates of rotation center
        Part part = (Part) getElement();
        part.setRotationCenter(Transformation.multiply(t, getRotationCenter()));
    }

    /**
     * @param partConnectors
     *            the partConnectors to set
     */
    public void setPartConnectors(Vector<PinNode> partConnectors) {
        this.partConnectors = partConnectors;
    }

    /**
     * @return the partConnectors
     */
    public Vector<PinNode> getPartPins() {
        return this.partConnectors;
    }

    /**
     * @return
     */
    public Collection<? extends UnitPoint> getPartConnectorsCoordinates() {
        Vector<UnitPoint> result = new Vector<UnitPoint>();

        for (PinNode pinNode : getPartPins()) {
            Pin c = (Pin) pinNode.getElement();
            for (int i = 0; i < c.getX().size(); i++) {
                result.add(new UnitPoint(c.getX().get(i), c.getY().get(i)));
            }
        }

        return result;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.original.graphNode.ElementNode#getRotationCenter()
     */
    @Override
    public UnitPoint getRotationCenter() {
        UnitPoint result = getElement().getRotationCenter();

        if (result == null) {
            ArrayList<Node> arrayList = getPartGroupNode().getNodeArray(null,
                                                                        getPartParameterNode());
            for (Node node : arrayList) {
                if (node instanceof ElementNode) {
                    result = Support.middle(result, ((ElementNode) node).getRotationCenter());
                }
            }
            if (result == null) {
                result = new UnitPoint();
            }
        }
        return result;
    }

    /**
     * Start edit with support for labels.
     */
    protected Element startEdit(Rectangle2D.Double r2d) {
        if (isDisabled()) {
            return null;
        }

        for (int i = getPartLabelsGroupNode().getChildrenGroupList().size() - 1; i >= 0; i--) {
            GroupNode child = getPartLabelsGroupNode().getChildrenGroupList().get(i);
            if (child.startEdit(r2d, 1)) {
                return child.getEditedElement();
            }
        }

        setEdited(true);
        return getElement().startEdit(r2d);
    }

    /**
     * Stop edit with support for labels.
     */
    protected void stopEdit(UnitPoint delta) {
        if (isEdited()) {
            getElement().setEditedCoordinate(delta);
            setEdited(false);
        } else {
            for (int i = getPartLabelsGroupNode().getChildrenGroupList().size() - 1; i >= 0; i--) {
                GroupNode child = getPartLabelsGroupNode().getChildrenGroupList().get(i);
                if (child.isEdited()) {
                    child.stopEdit(delta);
                }
            }
        }
    }
}
