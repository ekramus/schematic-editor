package cz.cvut.fel.schematicEditor.core.coreStructures;

import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.element.element.shape.Ellipse;
import cz.cvut.fel.schematicEditor.element.element.shape.Line;
import cz.cvut.fel.schematicEditor.element.element.shape.Rectangle;
import cz.cvut.fel.schematicEditor.element.element.shape.Text;
import cz.cvut.fel.schematicEditor.graphNode.ElementNode;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.Node;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.graphNode.ShapeNode;
import cz.cvut.fel.schematicEditor.graphNode.TransformationNode;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import cz.cvut.fel.schematicEditor.support.Transformation;
import cz.cvut.fel.schematicEditor.types.SceneGraphIterator;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

/**
 * This class represents <em>Scene Graph</em>.
 *
 * @author Urban Kravjansky
 */
public class SceneGraph implements Iterable<Node> {
    /**
     * This field represents <code>SceneGraph</code> iterator.
     */
    private SceneGraphIterator sgi;
    /**
     * This field represents top node of <em>Scene Graph</em>.
     */
    private GroupNode          topNode;
    /**
     * This field represents edited node of <em>SceneGraph</em>.
     */
    private GroupNode          editNode;

    /**
     * This is the default constructor.
     */
    public SceneGraph() {
        // TODO add some stuff later
        this.editNode = null;
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
     * This method creates static pre-defined <code>SceneGraph</code>. For developement purposes.
     */
    @Deprecated
    public void manualCreateSceneGraph3() {
        this.topNode = new GroupNode();

        GroupNode g1 = new GroupNode();
        GroupNode g2 = new GroupNode();
        GroupNode g3 = new GroupNode();
        GroupNode g4 = new GroupNode();
        GroupNode g5 = new GroupNode();
        GroupNode g6 = new GroupNode();
        GroupNode g7 = new GroupNode();
        GroupNode g8 = new GroupNode();
        GroupNode g9 = new GroupNode();

        // R vzor
        Rectangle rec = new Rectangle(new UnitPoint(0, 0), new UnitPoint(70, 20));
        ParameterNode pRec = new ParameterNode();
        pRec.setColor(Color.BLACK);
        pRec.setWidth(new Pixel(2));
        ShapeNode sRec = new ShapeNode(rec);

        // C vzor
        Line line = new Line(new UnitPoint(0, 0), new UnitPoint(50, 0));
        Line line2 = new Line(new UnitPoint(0, 9), new UnitPoint(50, 9));
        ParameterNode pLine = new ParameterNode();
        pLine.setColor(Color.BLACK);
        pLine.setWidth(new Pixel(5));
        ShapeNode sLine = new ShapeNode(line);
        ShapeNode sLine2 = new ShapeNode(line2);

        // U vzor
        // Arc arc = new Arc(new
        // cz.cvut.fel.schematicEditor.types.Point2D.Double(0,0), 50, 0, 359);
        Ellipse arc = new Ellipse(new UnitPoint(0, 0), new UnitPoint(50, 50));
        Line uLine1 = new Line(new UnitPoint(25, 10), new UnitPoint(25, 40));
        Line uLine2 = new Line(new UnitPoint(25, 40), new UnitPoint(15, 30));
        Line uLine3 = new Line(new UnitPoint(25, 40), new UnitPoint(35, 30));

        TransformationNode tArc = new TransformationNode(Transformation.getShift(25, 240));
        ParameterNode pArc = new ParameterNode();
        pArc.setColor(Color.BLACK);
        pArc.setWidth(new Pixel(1));
        ShapeNode sArc = new ShapeNode(arc);
        ShapeNode sULine1 = new ShapeNode(uLine1);
        ShapeNode sULine2 = new ShapeNode(uLine2);
        ShapeNode sULine3 = new ShapeNode(uLine3);

        // wiress
        ShapeNode l1 = new ShapeNode(new Line(new UnitPoint(50, 160), new UnitPoint(102, 160)));
        ShapeNode l2 = new ShapeNode(new Line(new UnitPoint(50, 160), new UnitPoint(50, 240)));
        ShapeNode l3 = new ShapeNode(new Line(new UnitPoint(50, 290), new UnitPoint(50, 375)));
        ShapeNode l4 = new ShapeNode(new Line(new UnitPoint(50, 375), new UnitPoint(240, 375)));
        ShapeNode l5 = new ShapeNode(new Line(new UnitPoint(240, 375), new UnitPoint(240, 340)));
        ShapeNode l6 = new ShapeNode(new Line(new UnitPoint(172, 160), new UnitPoint(240, 160)));
        ShapeNode l7 = new ShapeNode(new Line(new UnitPoint(240, 330), new UnitPoint(240, 270)));
        ShapeNode l8 = new ShapeNode(new Line(new UnitPoint(240, 200), new UnitPoint(240, 160)));
        ParameterNode pWires = new ParameterNode();
        pWires.setColor(Color.BLACK);
        pWires.setWidth(new Pixel(1));
        TransformationNode tWires = new TransformationNode(Transformation.getIdentity());

        ShapeNode text1 = new ShapeNode(new Text(new UnitPoint(125, 145), "R1", 15));
        ShapeNode text2 = new ShapeNode(new Text(new UnitPoint(260, 230), "R2", 15));
        ShapeNode text3 = new ShapeNode(new Text(new UnitPoint(270, 340), "C1", 15));
        ShapeNode text4 = new ShapeNode(new Text(new UnitPoint(80, 260), "Uc", 15));
        TransformationNode tText = new TransformationNode(Transformation.getIdentity());
        ParameterNode pText = new ParameterNode();
        pText.setColor(Color.BLUE);

        g1.add(sRec);
        g1.add(pRec);
        g1.add(new TransformationNode(Transformation.getShift(250, 200)));
        g1.add(new TransformationNode(Transformation.getRotation(Math.PI / 2)));

        g2.add(sLine);
        g2.add(sLine2);
        g2.add(pLine);
        g2.add(new TransformationNode(Transformation.getShift(213, 330)));

        g3.add(sArc);
        g3.add(sULine1);
        g3.add(sULine2);
        g3.add(sULine3);
        g3.add(tArc);
        g3.add(pArc);

        g4.add(sRec);
        g4.add(pRec);
        g4.add(new TransformationNode(Transformation.getShift(102, 150)));

        g5.add(l1);
        g5.add(l2);
        g5.add(l3);
        g5.add(l4);
        g5.add(l5);
        g5.add(l6);
        g5.add(l7);
        g5.add(l8);
        g5.add(pWires);
        g5.add(tWires);

        g6.add(text1);
        g6.add(pText);
        g6.add(tText);

        g7.add(text2);
        g7.add(pText);
        g7.add(tText);

        g8.add(text3);
        g8.add(pText);
        g8.add(tText);

        g9.add(text4);
        g9.add(pText);
        g9.add(tText);

        this.topNode.add(g1);
        this.topNode.add(g2);
        this.topNode.add(g3);
        this.topNode.add(g4);
        this.topNode.add(g5);
        this.topNode.add(g6);
        this.topNode.add(g7);
        this.topNode.add(g8);
        this.topNode.add(g9);
    }

    /**
     * This method creates static predefined working SceneGraph. For development purposes only.
     */
    public void manualCreateSceneGraph2() {
        this.topNode = new GroupNode();

        GroupNode gn = new GroupNode();

        GroupNode partGroupNode = new GroupNode();
        ShapeNode sn1 = new ShapeNode(new Line(new UnitPoint(10, 10), new UnitPoint(200, 200)));
        ShapeNode sn2 = new ShapeNode(new Line(new UnitPoint(200, 10), new UnitPoint(10, 200)));
        partGroupNode.add(sn1);
        partGroupNode.add(sn2);

        Part part = new Part();

        PartNode partNode = new PartNode(part, partGroupNode, "pokus");
        gn.add(new ParameterNode());
        gn.add(partNode);

        this.topNode.add(gn);
        this.topNode.add(new ParameterNode());
    }

    /**
     * This method creates empty static predefined working SceneGraph.
     */
    public void manualCreateSceneGraph() {
        this.topNode = new GroupNode();

        this.topNode.add(new ParameterNode());
    }

    /**
     * @param editNode the editNode to set
     */
    public void setEditNode(Rectangle2D.Double rectangle) {
        this.editNode = this.topNode.findHit(rectangle);
    }

    /**
     * @param topNode the topNode to set
     */
    public void setTopNode(GroupNode topNode) {
        this.topNode = topNode;
    }

    /**
     * @deprecated This method returns list of Boundig Boxes of each group. It should be not used, as it does not
     *             provide direct link to Elements , which Bounding Box is linked to which element.
     * @return
     */
    @Deprecated
    public LinkedList<UnitRectangle> getBouningBoxList() {
        LinkedList<UnitRectangle> result = new LinkedList<UnitRectangle>();

        for (Node node : this) {
            if (node instanceof GroupNode)
                result.add(((GroupNode) node).getBounds());
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
}
