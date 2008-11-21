package cz.cvut.fel.schematicEditor.graphNode;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.support.Transformation;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
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
    private GroupNode     partGroupNode;
    /**
     * {@link ParameterNode} containing parameters of part shape.
     */
    private ParameterNode partParameterNode;

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
    private void initialize(GroupNode partGroupNode) {
        ParameterNode pn = partGroupNode.getChildrenParameterNode();
        // set parameter node if not set
        if (pn == null) {
            pn = new ParameterNode();
            pn.setColor(Color.RED);
            partGroupNode.add(pn);
        }

        setPartGroupNode(partGroupNode);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.graphNode.ElementNode#isHit(java.awt.geom.Rectangle2D.Double)
     */
    @Override
    protected boolean isHit(Rectangle2D point) {
        return getPartGroupNode().isHit(point);
    }

    /**
     * Getter for element bounds.
     *
     * @param boundModifier modifier which affects boundary size of element.
     * @return Bounds of element.
     */
    @Override
    public UnitRectangle getBounds(Unit boundModifier) {
        double x = getPartGroupNode().getBounds().getX() - 50 * boundModifier.doubleValue();
        double y = getPartGroupNode().getBounds().getY() - 50 * boundModifier.doubleValue();
        double w = getPartGroupNode().getBounds().getWidth() + 2 * 50 * boundModifier.doubleValue();
        double h = getPartGroupNode().getBounds().getHeight() + 2 * 50 * boundModifier.doubleValue();

        return new UnitRectangle(x, y, w, h);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.graphNode.Node#duplicate()
     */
    @Override
    public Node duplicate() {
        PartNode result = new PartNode((Part) getElement(), (GroupNode) getPartGroupNode().duplicate());

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
        getPartGroupNode().add(new TransformationNode(t));
    }
}
