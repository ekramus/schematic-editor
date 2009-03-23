package cz.cvut.fel.schematicEditor.graphNode;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Vector;

import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.element.element.part.Pin;
import cz.cvut.fel.schematicEditor.support.Transformation;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

/**
 * This class represents Part Node. It contains link to {@link Part} and to {@link GroupNode} with part's visual form.
 *
 * @author Urban Kravjansky
 */
public class PartNode extends ElementNode {
    /**
     * GroupNode containing graphic representation of part shape.
     */
    private GroupNode             partGroupNode;
    /**
     * {@link ParameterNode} containing parameters of part shape.
     */
    private ParameterNode         partParameterNode;
    /**
     * {@link Vector} of part connectors.
     */
    private Vector<PinNode> partConnectors;

    /**
     * Constructor with default set of parameters. It instantiates new {@link PartNode}.
     *
     * @param part <code>Part</code> stored in this <code>PartNode</code>.
     * @param partGroupNode graphical representation of part.
     */
    public PartNode(Part part, GroupNode partGroupNode) {
        super(part);

        initialize(partGroupNode);
    }

    /**
     * Constructor with extra parameter for id.
     *
     * @param part <code>Part</code> stored in this <code>PartNode</code>.
     * @param partGroupNode graphical representation of part.
     * @param id identifier of this <code>PartNode</code>.
     */
    public PartNode(Part part, GroupNode partGroupNode, String id) {
        super(part, id);

        initialize(partGroupNode);
    }

    /**
     * Initializes all necessary variables.
     *
     * @param partGroupNode {@link GroupNode} describing shape of {@link Part}.
     */
    public void initialize(GroupNode partGroupNode) {
        ParameterNode pn = partGroupNode.getChildrenParameterNode();
        // set parameter node if not set
        if (pn == null) {
            pn = new ParameterNode();
            pn.setColor(Color.RED);
            partGroupNode.add(pn);
        }

        setPartConnectors(partGroupNode.getAndRemovePinNodes());
        setPartGroupNode(partGroupNode.getEnabledOnly());
    }

    /**
     * @see cz.cvut.fel.schematicEditor.graphNode.ElementNode#isHit(java.awt.geom.Rectangle2D, double)
     */
    @Override
    protected boolean isHit(Rectangle2D point) {
        return getPartGroupNode().isHit(point, 1);
    }

    /**
     * Getter for element bounds.
     *
     * @param boundModifier modifier which affects boundary size of element.
     * @return Bounds of element.
     */
    @Override
    public UnitRectangle getBounds(Unit boundModifier) {
        UnitRectangle result;

        double x = getPartGroupNode().getBounds().getX() - 50 * boundModifier.doubleValue();
        double y = getPartGroupNode().getBounds().getY() - 50 * boundModifier.doubleValue();
        double w = getPartGroupNode().getBounds().getWidth() + 2 * 50 * boundModifier.doubleValue();
        double h = getPartGroupNode().getBounds().getHeight() + 2 * 50 * boundModifier.doubleValue();

        result = new UnitRectangle(x, y, w, h);

        for (PinNode cn : getPartPins()) {
            Pin c = (Pin) cn.getElement();
            result = (UnitRectangle) result.createUnion(c.getBounds());
        }

        return result;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.graphNode.Node#duplicate()
     */
    @Override
    public Node duplicate() {
        PartNode result = new PartNode((Part) getElement(), (GroupNode) getPartGroupNode().duplicate());

        Vector<PinNode> partConnectors = new Vector<PinNode>();
        for (PinNode pinNode : getPartPins()) {
            partConnectors.add((PinNode) pinNode.duplicate());
        }
        result.setPartConnectors(partConnectors);

        return result;
    }

    /**
     * @param partGroupNode the partGroupNode to set
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
     * @see cz.cvut.fel.schematicEditor.graphNode.ElementNode#toString()
     */
    @Override
    public String toString() {
        return "[PartNode]";
    }

    /**
     * @see cz.cvut.fel.schematicEditor.graphNode.ElementNode#modifyCoordinates(cz.cvut.fel.schematicEditor.support.Transformation)
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
        part.setRotationCenter(Transformation.multiply(t, part.getRotationCenter()));
    }

    /**
     * @param partConnectors the partConnectors to set
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
}
