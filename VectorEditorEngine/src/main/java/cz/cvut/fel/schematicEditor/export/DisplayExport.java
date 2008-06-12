package cz.cvut.fel.schematicEditor.export;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Arc2D;

import java.awt.image.BufferedImage;
import java.util.Vector;
import java.awt.geom.AffineTransform; // import java.awt.geom.Rectangle2D.Double;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.application.Gui;
import cz.cvut.fel.schematicEditor.core.coreStructures.ElementStyle;
import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;

import cz.cvut.fel.schematicEditor.types.ElementType;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

import cz.cvut.fel.schematicEditor.graphNode.ElementNode;
import cz.cvut.fel.schematicEditor.graphNode.Node;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.TransformationNode;
import cz.cvut.fel.schematicEditor.element.shape.Arc;
import cz.cvut.fel.schematicEditor.element.shape.BezierCurve;
import cz.cvut.fel.schematicEditor.element.shape.Ellipse;
import cz.cvut.fel.schematicEditor.element.shape.Line;
import cz.cvut.fel.schematicEditor.element.shape.Polyline;
import cz.cvut.fel.schematicEditor.element.shape.Rectangle;
import cz.cvut.fel.schematicEditor.element.shape.Text;

/**
 * This class encapsulates export of elements on display.
 *
 * @author Urban Kravjansky
 */
public class DisplayExport implements Export {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger        logger;
    /**
     * This field represent zoom factor.
     */
    private double               zoomFactor;
    /**
     * Reference to <code>DisplayExport</code> instance.
     */
    private static DisplayExport displayExport = null;
    /**
     * Indicates whether scene is antialiased.
     */
    private boolean              antialiased;
    /**
     * Indicates whether drawing is done in debug mode.
     */
    private boolean              debuged;

    /**
     * This is constructor.
     */
    private DisplayExport() {
        logger = Logger.getLogger(Gui.class.getName());

        this.zoomFactor = 1;
        this.antialiased = true;
    }

    /**
     * This method instanitates one instance of <code>DisplayExport</code>.
     *
     * @return <code>DisplayExport</code> instance to use.
     */
    public static DisplayExport getExport() {
        if (displayExport == null) {
            displayExport = new DisplayExport();
        }
        return displayExport;
    }

    public void export(SceneGraph sg, Object output) {
        BufferedImage img = (BufferedImage) output;
        TransformationNode tn = null;
        ParameterNode pn = null;
        ElementNode en = null;

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

    private void drawNode(ElementNode en, ParameterNode pn, TransformationNode tn, BufferedImage img) {
        UnitRectangle bounds = en.getBounds(pn.getWidth());
        BufferedImage nodeImg = new BufferedImage((int) bounds.getWidth(),
                (int) bounds.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D nodeG2D = (Graphics2D) nodeImg.getGraphics();

        if (debuged) {
            nodeG2D.setColor(Color.ORANGE);
            nodeG2D.drawRect(0, 0, (int) bounds.getWidth() - 1, (int) bounds.getHeight() - 1);
        }

        BasicStroke basicStroke = new BasicStroke(pn.getWidth().floatValue());
        if (basicStroke != null) {
            nodeG2D.setStroke(basicStroke);
        }

        nodeG2D.translate(-bounds.getX(), -bounds.getY());

        if (antialiased) {
            nodeG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                     RenderingHints.VALUE_ANTIALIAS_ON);
            logger.debug("ON");
        } else {
            nodeG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                     RenderingHints.VALUE_ANTIALIAS_OFF);
            logger.debug("OFF");
        }

        switch (en.getElementType()) {
            case ElementType.T_LINE:
                Line l = (Line) en.getElement();
                Line2D.Double l2d = new Line2D.Double(l.getX().get(0).doubleValue(),
                        l.getY().get(0).doubleValue(), l.getX().get(1).doubleValue(),
                        l.getY().get(1).doubleValue());

                drawShape(nodeG2D, l2d, pn.getColor(), pn.getLineStyle(), null, pn.getFillStyle());

                break;

            case ElementType.T_RECTANGLE:
                Rectangle rectangle = (Rectangle) en.getElement();
                Rectangle2D.Double rectangle2d = new Rectangle2D.Double(rectangle.getTopLeftX(),
                        rectangle.getTopLeftY(), rectangle.getWidth(), rectangle.getHeight());

                drawShape(nodeG2D, rectangle2d, pn.getColor(), pn.getLineStyle(), pn.getFill(),
                          pn.getFillStyle());
                break;

            case ElementType.T_ELLIPSE:
                Ellipse ellipse = (Ellipse) en.getElement();
                Ellipse2D.Double ell = new Ellipse2D.Double(ellipse.getTopLeftX(),
                        ellipse.getTopLeftY(), ellipse.getWidth(), ellipse.getHeight());

                drawShape(nodeG2D, ell, pn.getColor(), pn.getLineStyle(), pn.getFill(),
                          pn.getFillStyle());
                break;

            case ElementType.T_ARC:
                Arc arc = (Arc) en.getElement();
                Arc2D.Double arc2d = new Arc2D.Double(arc.getTopLeftX(), arc.getTopLeftY(),
                        arc.getWidth(), arc.getHeight(), arc.getStartAngle(), arc.getArcAngle(),
                        Arc2D.PIE);

                drawShape(nodeG2D, arc2d, pn.getColor(), pn.getLineStyle(), pn.getFill(),
                          pn.getFillStyle());
                break;

            case ElementType.T_ARC_SEGMENT:
                Arc arcS = (Arc) en.getElement();
                Arc2D.Double arcS2d = new Arc2D.Double(arcS.getTopLeftX(), arcS.getTopLeftY(),
                        arcS.getWidth(), arcS.getHeight(), arcS.getStartAngle(),
                        arcS.getArcAngle(), Arc2D.OPEN);

                drawShape(nodeG2D, arcS2d, pn.getColor(), pn.getLineStyle(), pn.getFill(),
                          pn.getFillStyle());
                break;

            case ElementType.T_TRIANGLE:
            case ElementType.T_POLYGON:
                Polyline pg = (Polyline) en.getElement();
                Polygon p = new Polygon();

                Vector<Unit> xPg = pg.getX();
                Vector<Unit> yPg = pg.getY();

                for (int i = 0; i < xPg.size(); i++) {
                    p.addPoint(xPg.get(i).intValue(), yPg.get(i).intValue());
                }
                drawShape(nodeG2D, p, pn.getColor(), pn.getLineStyle(), pn.getFill(),
                          pn.getFillStyle());
                break;

            case ElementType.T_POLYLINE:
                Polyline poly = (Polyline) en.getElement();

                Vector<Unit> xPo = poly.getX();
                Vector<Unit> yPo = poly.getY();

                for (int i = 0; i < xPo.size() - 1; i++) {
                    Line2D.Double line2d = new Line2D.Double(xPo.get(i).doubleValue(),
                            yPo.get(i).doubleValue(), xPo.get(i + 1).doubleValue(),
                            yPo.get(i + 1).doubleValue());
                    drawShape(nodeG2D, line2d, pn.getColor(), pn.getLineStyle(), null,
                              pn.getFillStyle());
                }
                break;

            case ElementType.T_BEZIER:
                BezierCurve bC = (BezierCurve) en.getElement();

                CubicCurve2D.Double quadCurve = new CubicCurve2D.Double(bC.getStart().getX(),
                        bC.getStart().getY(), bC.getControl1().getX(), bC.getControl1().getY(),
                        bC.getControl2().getX(), bC.getControl2().getY(), bC.getEnd().getX(),
                        bC.getEnd().getY());

                drawShape(nodeG2D, quadCurve, pn.getColor(), pn.getLineStyle(), pn.getFill(),
                          pn.getFillStyle());

                break;

            case ElementType.T_TEXT:

                Text text = (Text) en.getElement();

                Font font = new Font(text.getFontName(), text.getStyle(), text.getSize());
                FontRenderContext frc = nodeG2D.getFontRenderContext();

                TextLayout layout = new TextLayout(text.getText(), font, frc);

                Rectangle2D rec = layout.getBounds();
                bounds = new UnitRectangle(rec.getX() + text.getX().get(0).doubleValue(),
                        rec.getY() + text.getY().get(0).doubleValue(), rec.getWidth(),
                        rec.getHeight());
                nodeImg = new BufferedImage((int) rec.getWidth(), (int) rec.getHeight(),
                        BufferedImage.TYPE_INT_ARGB);

                nodeG2D = (Graphics2D) nodeImg.getGraphics();
                nodeG2D.translate(-bounds.getX(), -bounds.getY());

                if (pn.getColor() != null) {
                    nodeG2D.setColor(pn.getColor());
                }

                layout.draw(nodeG2D, text.getX().get(0).floatValue(),
                            text.getY().get(0).floatValue());
                break;

            default:
                break;

        }

        double m[][] = tn.getTransformation().getTransformationMatrix();

        Graphics2D g2d = (Graphics2D) img.getGraphics();

        g2d.setTransform(new AffineTransform(m[0][0], m[1][0], m[0][1], m[1][1], m[0][2], m[1][2]));
        g2d.translate(bounds.getX(), bounds.getY());
        g2d.drawImage(nodeImg, 0, 0, null);

    }

    private void drawShape(Graphics2D g, Shape s, Color strokeColor, ElementStyle strokeStyle,
            Color fillColor, ElementStyle fillStyle) {

        if (fillStyle != ElementStyle.NONE) {
            g.setColor(fillColor);
            g.fill(s);
        }

        if (strokeStyle != ElementStyle.NONE) {
            g.setColor(strokeColor);
            g.draw(s);
        }
    }

    /**
     * @param antialiased
     *            the antialiased to set
     */
    public void setAntialiased(boolean antialiased) {
        this.antialiased = antialiased;
    }

    /**
     * @param debugged
     *            the debugged to set
     */
    public void setDebugged(boolean debugged) {
        this.debuged = debugged;
    }
}
