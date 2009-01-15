package cz.cvut.fel.schematicEditor.export;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.element.element.part.Junction;
import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.element.element.part.Pin;
import cz.cvut.fel.schematicEditor.element.element.part.Wire;
import cz.cvut.fel.schematicEditor.element.element.shape.Arc;
import cz.cvut.fel.schematicEditor.element.element.shape.BezierCurve;
import cz.cvut.fel.schematicEditor.element.element.shape.Ellipse;
import cz.cvut.fel.schematicEditor.element.element.shape.Line;
import cz.cvut.fel.schematicEditor.element.element.shape.Polyline;
import cz.cvut.fel.schematicEditor.element.element.shape.Rectangle;
import cz.cvut.fel.schematicEditor.element.element.shape.Text;
import cz.cvut.fel.schematicEditor.element.properties.ElementStyle;
import cz.cvut.fel.schematicEditor.graphNode.ElementNode;
import cz.cvut.fel.schematicEditor.graphNode.Node;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.graphNode.PinNode;
import cz.cvut.fel.schematicEditor.graphNode.TransformationNode;
import cz.cvut.fel.schematicEditor.support.Transformation;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

/**
 * This class encapsulates export of elements on display.
 *
 * @author Urban Kravjansky
 */
public class DisplayExport implements Export {
    /**
     * Reference to <code>DisplayExport</code> instance.
     */
    private static DisplayExport instance = null;
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger        logger;

    /**
     * This method instanitates one instance of <code>DisplayExport</code>.
     *
     * @return <code>DisplayExport</code> instance to use.
     */
    public static DisplayExport getInstance() {
        if (instance == null) {
            instance = new DisplayExport();
        }
        return instance;
    }

    /**
     * Indicates whether scene is antialiased.
     */
    private boolean antialiased;
    /**
     * Indicates whether drawing is done in debug mode.
     */
    private boolean debuged;
    /**
     * This field represent zoom factor.
     */
    private double  zoomFactor;

    /**
     * This is constructor.
     */
    private DisplayExport() {
        logger = Logger.getLogger(this.getClass().getName());

        setZoomFactor(1);
        setAntialiased(true);
    }

    /**
     * @return the zoomFactor
     */
    private double getZoomFactor() {
        return this.zoomFactor;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.export.Export#export(cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph,
     *      double, java.lang.Object)
     */
    public void export(SceneGraph sg, double zoomFactor, Object output) {
        BufferedImage img = (BufferedImage) output;
        TransformationNode tn = null;
        ParameterNode pn = null;
        ElementNode en = null;

        setZoomFactor(zoomFactor);

        for (Node node : sg) {
            if (node instanceof TransformationNode) {
                tn = (TransformationNode) node;
            } else if (node instanceof ParameterNode) {
                pn = (ParameterNode) node;
            } else if (node instanceof ElementNode) {
                en = (ElementNode) node;
                drawNode(en, pn, tn, img);
            }
        }
    }

    /**
     * @param antialiased the antialiased to set
     */
    public void setAntialiased(boolean antialiased) {
        this.antialiased = antialiased;
    }

    /**
     * @param debugged the debugged to set
     */
    public void setDebugged(boolean debugged) {
        this.debuged = debugged;
    }

    /**
     * Draws given element node. To draw it, this method uses parameter and transformation node instances.
     *
     * @param elementNode element node to be drawn.
     * @param parameterNode parameter node for given element node.
     * @param transformationNode transformation node for given element node.
     * @param bufferedImage {@link BufferedImage}, which will be used for drawing.
     */
    private void drawNode(ElementNode elementNode, ParameterNode parameterNode, TransformationNode transformationNode,
            BufferedImage bufferedImage) {
        UnitRectangle bounds = transformBounds(transformationNode.getTransformation(), elementNode
                .getBounds(parameterNode.getWidth()));
        BufferedImage nodeImg = new BufferedImage((int) bounds.getWidth(), (int) bounds.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D nodeG2D = (Graphics2D) nodeImg.getGraphics();

        if (isDebuged()) {
            nodeG2D.setColor(Color.ORANGE);
            nodeG2D.drawRect(0, 0, (int) bounds.getWidth() - 1, (int) bounds.getHeight() - 1);
        }

        BasicStroke basicStroke = new BasicStroke(parameterNode.getWidth().floatValue());
        nodeG2D.setStroke(basicStroke);
        logger.trace("Stroke width: " + basicStroke.getLineWidth());

        // this will be not necessary, as all elements will be drawn using correct set of coordinates
        nodeG2D.translate(-bounds.getX(), -bounds.getY());

        if (isAntialiased()) {
            nodeG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            logger.debug("AA ON");
        } else {
            nodeG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            logger.debug("AA OFF");
        }

        switch (elementNode.getElement().getElementType()) {
            case T_LINE:
                Line l = (Line) elementNode.getElement();
                Line2D.Double l2d = new Line2D.Double(l.getX().get(0).doubleValue() * getZoomFactor(), l.getY().get(0)
                        .doubleValue() * getZoomFactor(), l.getX().get(1).doubleValue() * getZoomFactor(), l.getY()
                        .get(1).doubleValue() * getZoomFactor());

                drawShape(nodeG2D, l2d, parameterNode.getColor(), parameterNode.getLineStyle(), null, parameterNode
                        .getFillStyle());

                break;

            case T_RECTANGLE:
                Rectangle rectangle = (Rectangle) elementNode.getElement();
                Rectangle2D.Double rectangle2d = new Rectangle2D.Double(rectangle.getTopLeftX() * getZoomFactor(),
                        rectangle.getTopLeftY() * getZoomFactor(), rectangle.getWidth() * getZoomFactor(), rectangle
                                .getHeight() * getZoomFactor());

                drawShape(nodeG2D, rectangle2d, parameterNode.getColor(), parameterNode.getLineStyle(), parameterNode
                        .getFill(), parameterNode.getFillStyle());
                break;

            case T_ELLIPSE:
                Ellipse ellipse = (Ellipse) elementNode.getElement();
                Ellipse2D.Double ell = new Ellipse2D.Double(ellipse.getTopLeftX() * getZoomFactor(), ellipse
                        .getTopLeftY() * getZoomFactor(), ellipse.getWidth() * getZoomFactor(),
                        ellipse.getHeight() * getZoomFactor());

                drawShape(nodeG2D, ell, parameterNode.getColor(), parameterNode.getLineStyle(),
                          parameterNode.getFill(), parameterNode.getFillStyle());
                break;

            case T_ARC:
                Arc arc = (Arc) elementNode.getElement();
                Arc2D.Double arc2d = new Arc2D.Double(arc.getTopLeftX() * getZoomFactor(),
                        arc.getTopLeftY() * getZoomFactor(), arc.getWidth() * getZoomFactor(),
                        arc.getHeight() * getZoomFactor(), arc.getStartAngle(), arc.getArcAngle(), Arc2D.PIE);

                drawShape(nodeG2D, arc2d, parameterNode.getColor(), parameterNode.getLineStyle(), parameterNode
                        .getFill(), parameterNode.getFillStyle());
                break;

            case T_ARC_SEGMENT:
                Arc arcS = (Arc) elementNode.getElement();
                Arc2D.Double arcS2d = new Arc2D.Double(arcS.getTopLeftX() * getZoomFactor(),
                        arcS.getTopLeftY() * getZoomFactor(), arcS.getWidth() * getZoomFactor(),
                        arcS.getHeight() * getZoomFactor(), arcS.getStartAngle(), arcS.getArcAngle(), Arc2D.OPEN);

                drawShape(nodeG2D, arcS2d, parameterNode.getColor(), parameterNode.getLineStyle(), parameterNode
                        .getFill(), parameterNode.getFillStyle());
                break;

            case T_TRIANGLE:
            case T_POLYGON:
                Polyline pg = (Polyline) elementNode.getElement();
                Polygon p = new Polygon();

                Vector<Unit> xPg = pg.getX();
                Vector<Unit> yPg = pg.getY();

                for (int i = 0; i < xPg.size(); i++) {
                    p.addPoint((int) (xPg.get(i).doubleValue() * getZoomFactor()),
                               (int) (yPg.get(i).doubleValue() * getZoomFactor()));
                }
                drawShape(nodeG2D, p, parameterNode.getColor(), parameterNode.getLineStyle(), parameterNode.getFill(),
                          parameterNode.getFillStyle());
                break;

            case T_POLYLINE:
                Polyline poly = (Polyline) elementNode.getElement();

                Vector<Unit> xPo = poly.getX();
                Vector<Unit> yPo = poly.getY();

                for (int i = 0; i < xPo.size() - 1; i++) {
                    Line2D.Double line2d = new Line2D.Double(xPo.get(i).doubleValue() * getZoomFactor(), yPo.get(i)
                            .doubleValue() * getZoomFactor(), xPo.get(i + 1).doubleValue() * getZoomFactor(), yPo
                            .get(i + 1).doubleValue() * getZoomFactor());
                    drawShape(nodeG2D, line2d, parameterNode.getColor(), parameterNode.getLineStyle(), null,
                              parameterNode.getFillStyle());
                }
                break;

            case T_BEZIER:
                BezierCurve bC = (BezierCurve) elementNode.getElement();

                CubicCurve2D.Double quadCurve = new CubicCurve2D.Double(bC.getStart().getX() * getZoomFactor(), bC
                        .getStart().getY() * getZoomFactor(), bC.getControl1().getX() * getZoomFactor(), bC
                        .getControl1().getY() * getZoomFactor(), bC.getControl2().getX() * getZoomFactor(), bC
                        .getControl2().getY(), bC.getEnd().getX(), bC.getEnd().getY() * getZoomFactor());

                drawShape(nodeG2D, quadCurve, parameterNode.getColor(), parameterNode.getLineStyle(), parameterNode
                        .getFill(), parameterNode.getFillStyle());

                break;

            case T_TEXT:
                // Text text = (Text) elementNode.getElement();
                //
                // Font font = new Font(text.getFontName(), text.getStyle(), text.getSize());
                // FontRenderContext frc = nodeG2D.getFontRenderContext();
                //
                // TextLayout layout = new TextLayout(text.getText(), font, frc);
                //
                // Rectangle2D rec = layout.getBounds();
                // bounds = new UnitRectangle(rec.getX() + text.getX().get(0).doubleValue(), rec.getY() + text.getY()
                // .get(0).doubleValue(), rec.getWidth(), rec.getHeight());
                // nodeImg = new BufferedImage((int) rec.getWidth(), (int) rec.getHeight(),
                // BufferedImage.TYPE_INT_ARGB);
                //
                // nodeG2D = (Graphics2D) nodeImg.getGraphics();
                // nodeG2D.translate(-bounds.getX(), -bounds.getY());
                //
                // if (parameterNode.getColor() != null) {
                // nodeG2D.setColor(parameterNode.getColor());
                // }
                //
                // layout.draw(nodeG2D, text.getX().get(0).floatValue(), text.getY().get(0).floatValue());
                // break;
                Text text = (Text) elementNode.getElement();
                drawText(text.getText(), new UnitPoint(text.getX().firstElement(), text.getY().firstElement()), nodeG2D);
                break;

            case T_WIRE:
                Wire wire = (Wire) elementNode.getElement();

                Vector<Unit> xWi = wire.getX();
                Vector<Unit> yWi = wire.getY();

                for (int i = 0; i < xWi.size() - 1; i++) {
                    Line2D.Double line2d = new Line2D.Double(xWi.get(i).doubleValue() * getZoomFactor(), yWi.get(i)
                            .doubleValue() * getZoomFactor(), xWi.get(i + 1).doubleValue() * getZoomFactor(), yWi
                            .get(i + 1).doubleValue() * getZoomFactor());
                    drawShape(nodeG2D, line2d, parameterNode.getColor(), ElementStyle.DASHED, null, parameterNode
                            .getFillStyle());
                }
                break;

            case T_PIN:
                Pin pin = (Pin) elementNode.getElement();
                drawPin(pin, parameterNode, nodeG2D);
                break;

            case T_JUNCTION:
                Junction junction = (Junction) elementNode.getElement();
                logger.trace("drawing connector at coordinates: " + new UnitPoint(junction.getX().firstElement(),
                        junction.getY().firstElement()));

                Ellipse2D.Double e2d = new Ellipse2D.Double(
                        (junction.getX().firstElement().doubleValue() * getZoomFactor()) - 2, (junction.getY()
                                .firstElement().doubleValue() * getZoomFactor()) - 2, 5, 5);
                drawShape(nodeG2D, e2d, parameterNode.getColor(), ElementStyle.NORMAL, Color.BLACK, ElementStyle.NORMAL);
                break;

            case T_PART:
                // TODO draw part specific descriptions and symbols, e.g. connector descriptions
                PartNode partNode = (PartNode) elementNode;

                // TODO retrieve connectors and match them to correct names
                // for now, connectors are in fixed order as are they returned by getPartConnectors() method.

                Vector<String> connectorNames = ((Part) partNode.getElement()).getPartProperties().getPartPinNames();
                // search for connectors, draw them and display their names
                int i = 0;
                for (PinNode cn : partNode.getPartPins()) {
                    Pin c = (Pin) cn.getElement();
                    drawPin(c, parameterNode, nodeG2D);
                    drawPinText(c, connectorNames.get(i), nodeG2D);
                    i++;
                }

                // draw partRotationCenter, if different from [0,0]
                UnitPoint rotationCenter = partNode.getElement().getRotationCenter();
                if ((rotationCenter.getX() != 0) && (rotationCenter.getY() != 0)) {
                    nodeG2D.setColor(Color.RED);
                    nodeG2D.drawOval((int) (rotationCenter.getX() * getZoomFactor()) - 2,
                                     (int) (rotationCenter.getY() * getZoomFactor()) - 2, 4, 4);
                }

                // TODO draw all necessary info

                break;

            default:
                break;
        }

        Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();

        g2d.translate(bounds.getX(), bounds.getY());
        g2d.drawImage(nodeImg, 0, 0, null);

    }

    /**
     * @param transformation
     * @param bounds
     * @return
     */
    private UnitRectangle transformBounds(Transformation transformation, UnitRectangle bounds) {
        // get transformation modification
        UnitPoint a = Transformation.multiply(transformation, bounds.getTopLeft());
        UnitPoint b = Transformation.multiply(transformation, bounds.getTopRight());
        UnitPoint c = Transformation.multiply(transformation, bounds.getBottomLeft());
        UnitPoint d = Transformation.multiply(transformation, bounds.getBottomRight());

        // get scale modification
        a = new UnitPoint(a.getX() * getZoomFactor(), a.getY() * getZoomFactor());
        b = new UnitPoint(b.getX() * getZoomFactor(), b.getY() * getZoomFactor());
        c = new UnitPoint(c.getX() * getZoomFactor(), c.getY() * getZoomFactor());
        d = new UnitPoint(d.getX() * getZoomFactor(), d.getY() * getZoomFactor());

        return new UnitRectangle(a, b, c, d);
    }

    /**
     * Draws shape using {@link Graphics2D} <code>draw</code> method.
     *
     * @param g2d graphic context, in which all drawing is done.
     * @param shape shape to be visualized.
     * @param strokeColor color of stroke.
     * @param strokeStyle style of stroke.
     * @param fillColor color of fill.
     * @param fillStyle style of fill.
     */
    private void drawShape(Graphics2D g2d, Shape shape, Color strokeColor, ElementStyle strokeStyle, Color fillColor,
            ElementStyle fillStyle) {

        if (fillStyle != ElementStyle.NONE) {
            g2d.setColor(fillColor);
            g2d.fill(shape);
        }

        if (strokeStyle != ElementStyle.NONE) {
            g2d.setColor(strokeColor);
            if (strokeStyle == ElementStyle.DOTTED) {
                float[] dots = { 1, 2 };
                g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1, dots, 0));
            }
            g2d.draw(shape);
        }
    }

    /**
     * @return the antialiased
     */
    private boolean isAntialiased() {
        return this.antialiased;
    }

    /**
     * @return the debuged
     */
    private boolean isDebuged() {
        return this.debuged;
    }

    /**
     * @param zoomFactor the zoomFactor to set
     */
    private void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    /**
     * Draws pin.
     *
     * @param pin
     */
    private void drawPin(final Pin pin, ParameterNode parameterNode, Graphics2D nodeG2D) {
        logger.trace("drawing connector at coordinates: " + new UnitPoint(pin.getX().firstElement(), pin.getY()
                .firstElement()));

        Ellipse2D.Double e2d = new Ellipse2D.Double((pin.getX().firstElement().doubleValue() * getZoomFactor()) - 2,
                (pin.getY().firstElement().doubleValue() * getZoomFactor()) - 2, 5, 5);
        drawShape(nodeG2D, e2d, parameterNode.getColor(), ElementStyle.NORMAL, null, parameterNode.getFillStyle());
    }

    private void drawText(String text, UnitPoint coordinates, Graphics2D nodeG2D) {
        logger.trace("drawing text <" + text + "> at coordinates: " + coordinates);

        nodeG2D.setColor(Color.BLACK);
        nodeG2D.drawString(text, coordinates.getUnitX().floatValue(), coordinates.getUnitY().floatValue());
    }

    private void drawPinText(Pin pin, String connectorName, Graphics2D nodeG2D) {
        nodeG2D.setColor(Color.BLACK);
        nodeG2D.drawString(connectorName, (int) (pin.getX().firstElement().floatValue() * getZoomFactor()) - 10,
                           (int) (pin.getY().firstElement().floatValue() * getZoomFactor()) - 10);
    }
}
