package cz.cvut.fel.schematicEditor.export;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.configuration.GuiConfiguration;
import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.element.ElementPotential;
import cz.cvut.fel.schematicEditor.element.element.Element;
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
import cz.cvut.fel.schematicEditor.graphNode.NodeFactory;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.graphNode.PinNode;
import cz.cvut.fel.schematicEditor.graphNode.TransformationNode;
import cz.cvut.fel.schematicEditor.parts.PartProperties;
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

    /**
     * Draws given element node. To draw it, this method uses parameter and transformation node
     * instances.
     * 
     * @param elementNode
     *            element node to be drawn.
     * @param parameterNode
     *            parameter node for given element node.
     * @param transformationNode
     *            transformation node for given element node.
     * @param bufferedImage
     *            {@link BufferedImage}, which will be used for drawing.
     */
    private void drawNode(ElementNode elementNode, ParameterNode parameterNode,
            TransformationNode transformationNode, BufferedImage bufferedImage) {
        UnitRectangle bounds = transformBounds(
                                               transformationNode.getTransformation(),
                                               elementNode.getBounds(
                                                                     parameterNode.getWidth(),
                                                                     (Graphics2D) bufferedImage.getGraphics()));
        BufferedImage nodeImg = new BufferedImage((int) bounds.getWidth(),
                (int) bounds.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D nodeG2D = (Graphics2D) nodeImg.getGraphics();

        if (isDebuged()) {
            nodeG2D.setColor(Color.ORANGE);
            nodeG2D.drawRect(0, 0, (int) bounds.getWidth() - 1, (int) bounds.getHeight() - 1);
        }

        // set stroke - rescaling on zoomin and modified ends and joins
        BasicStroke basicStroke = new BasicStroke(
                (float) (parameterNode.getWidth().floatValue() * (getZoomFactor() > 1
                                                                                     ? getZoomFactor()
                                                                                     : 1)),
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        nodeG2D.setStroke(basicStroke);
        logger.trace("Stroke width: " + basicStroke.getLineWidth());

        // this will be not necessary, as all elements will be drawn using correct set of
        // coordinates
        nodeG2D.translate(-bounds.getX(), -bounds.getY());

        if (isAntialiased()) {
            nodeG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                     RenderingHints.VALUE_ANTIALIAS_ON);
            logger.debug("AA ON");
        } else {
            nodeG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                     RenderingHints.VALUE_ANTIALIAS_OFF);
            logger.debug("AA OFF");
        }

        switch (elementNode.getElement().getElementType()) {
            case T_LINE:
                Line l = (Line) elementNode.getElement();
                Line2D.Double l2d = new Line2D.Double(l.getX().get(0).doubleValue()
                                                      * getZoomFactor(),
                        l.getY().get(0).doubleValue() * getZoomFactor(),
                        l.getX().get(1).doubleValue() * getZoomFactor(),
                        l.getY().get(1).doubleValue() * getZoomFactor());

                drawShape(nodeG2D, l2d, parameterNode.getColor(), parameterNode.getLineStyle(),
                          null, parameterNode.getFillStyle());
                

                break;

            case T_RECTANGLE:
                Rectangle rectangle = (Rectangle) elementNode.getElement();
                Rectangle2D.Double rectangle2d = new Rectangle2D.Double(rectangle.getTopLeftX()
                                                                        * getZoomFactor(),
                        rectangle.getTopLeftY() * getZoomFactor(), rectangle.getWidth()
                                                                   * getZoomFactor(),
                        rectangle.getHeight() * getZoomFactor());

                drawShape(nodeG2D, rectangle2d, parameterNode.getColor(),
                          parameterNode.getLineStyle(), parameterNode.getFill(),
                          parameterNode.getFillStyle());
                break;

            case T_ELLIPSE:
                Ellipse ellipse = (Ellipse) elementNode.getElement();
                Ellipse2D.Double ell = new Ellipse2D.Double(
                        ellipse.getTopLeftX() * getZoomFactor(), ellipse.getTopLeftY()
                                                                 * getZoomFactor(),
                        ellipse.getWidth() * getZoomFactor(), ellipse.getHeight() * getZoomFactor());

                drawShape(nodeG2D, ell, parameterNode.getColor(), parameterNode.getLineStyle(),
                          parameterNode.getFill(), parameterNode.getFillStyle());
                break;

            case T_ARC:
                Arc arc = (Arc) elementNode.getElement();
                Arc2D.Double arc2d = new Arc2D.Double(arc.getTopLeftX() * getZoomFactor(),
                        arc.getTopLeftY() * getZoomFactor(), arc.getWidth() * getZoomFactor(),
                        arc.getHeight() * getZoomFactor(), arc.getStartAngle(), arc.getArcAngle(),
                        Arc2D.PIE);

                drawShape(nodeG2D, arc2d, parameterNode.getColor(), parameterNode.getLineStyle(),
                          parameterNode.getFill(), parameterNode.getFillStyle());
                break;

            case T_ARC_SEGMENT:
                Arc arcS = (Arc) elementNode.getElement();
                Arc2D.Double arcS2d = new Arc2D.Double(arcS.getTopLeftX() * getZoomFactor(),
                        arcS.getTopLeftY() * getZoomFactor(), arcS.getWidth() * getZoomFactor(),
                        arcS.getHeight() * getZoomFactor(), arcS.getStartAngle(),
                        arcS.getArcAngle(), Arc2D.OPEN);

                drawShape(nodeG2D, arcS2d, parameterNode.getColor(), parameterNode.getLineStyle(),
                          parameterNode.getFill(), parameterNode.getFillStyle());
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
                drawShape(nodeG2D, p, parameterNode.getColor(), parameterNode.getLineStyle(),
                          parameterNode.getFill(), parameterNode.getFillStyle());
                break;

            case T_POLYLINE:
                Polyline poly = (Polyline) elementNode.getElement();

                Vector<Unit> xPo = poly.getX();
                Vector<Unit> yPo = poly.getY();

                for (int i = 0; i < xPo.size() - 1; i++) {
                    Line2D.Double line2d = new Line2D.Double(xPo.get(i).doubleValue()
                                                             * getZoomFactor(),
                            yPo.get(i).doubleValue() * getZoomFactor(),
                            xPo.get(i + 1).doubleValue() * getZoomFactor(),
                            yPo.get(i + 1).doubleValue() * getZoomFactor());
                    drawShape(nodeG2D, line2d, parameterNode.getColor(),
                              parameterNode.getLineStyle(), null, parameterNode.getFillStyle());
                }
                break;

            case T_BEZIER:
                BezierCurve bC = (BezierCurve) elementNode.getElement();

                CubicCurve2D.Double quadCurve = new CubicCurve2D.Double(bC.getStart().getX()
                                                                        * getZoomFactor(),
                        bC.getStart().getY() * getZoomFactor(), bC.getControl1().getX()
                                                                * getZoomFactor(),
                        bC.getControl1().getY() * getZoomFactor(), bC.getControl2().getX()
                                                                   * getZoomFactor(),
                        bC.getControl2().getY(), bC.getEnd().getX(), bC.getEnd().getY()
                                                                     * getZoomFactor());

                drawShape(nodeG2D, quadCurve, parameterNode.getColor(),
                          parameterNode.getLineStyle(), parameterNode.getFill(),
                          parameterNode.getFillStyle());

                break;

            case T_TEXT:
                Text text = (Text) elementNode.getElement();
                drawText(nodeG2D, text.getValue(), new UnitPoint(text.getX().firstElement(),
                        text.getY().firstElement()), parameterNode.getColor(),
                         parameterNode.getFont());
                break;


                
         //       Used to be HOR-VER wire     
            case T_WIRE:
                Wire wire = (Wire) elementNode.getElement();
                
//                wire.getPinPotential(0);
                Vector<Unit> xWi = wire.getX();
                Vector<Unit> yWi = wire.getY();

                for (int i = 0; i < xWi.size() - 1; i++) {
                    Line2D.Double line2d = new Line2D.Double(xWi.get(i).doubleValue()
                                                             * getZoomFactor(),
                            yWi.get(i).doubleValue() * getZoomFactor(),
                            xWi.get(i + 1).doubleValue() * getZoomFactor(),
                            yWi.get(i + 1).doubleValue() * getZoomFactor());
                    drawShape(nodeG2D, line2d, parameterNode.getColor(), ElementStyle.DASHED, null,
                              parameterNode.getFillStyle());
                    //drawPinText(null, elementNode.getElement().getPinPotential(elementNode.getElement().hashCode()), nodeG2D);
                }
                break;
              //  **

            case T_PIN:
                Pin pin = (Pin) elementNode.getElement();
                
                if (pin.getVisible())
                drawPin(pin, parameterNode, nodeG2D);
           
                if(GuiConfiguration.getInstance().getPotentialVisible())
                	drawPinText(pin, parameterNode, transformationNode, bufferedImage);
                
          		break;

            case T_JUNCTION:
                Junction junction = (Junction) elementNode.getElement();
                logger.trace("drawing connector at coordinates: "
                             + new UnitPoint(junction.getX().firstElement(),
                                     junction.getY().firstElement()));

                Ellipse2D.Double e2d = new Ellipse2D.Double(
                        (junction.getX().firstElement().doubleValue() * getZoomFactor()) - 2,
                        (junction.getY().firstElement().doubleValue() * getZoomFactor()) - 2, 5, 5);
                drawShape(nodeG2D, e2d, parameterNode.getColor(), ElementStyle.NORMAL, Color.BLACK,
                          ElementStyle.NORMAL);
                
                //drawPinText(new Pin(junction.getX(), junction.getY()), junction.getPinPotential(junction.hashCode()),nodeG2D);
              /*/  drawText(nodeG2D, junction.getPinPotential(junction.hashCode()), new UnitPoint(junction.getX().firstElement().doubleValue() * getZoomFactor(),
                		junction.getX().firstElement().doubleValue() * getZoomFactor()),
                		Color.BLACK, parameterNode.getFont()); //*/
                
                if(GuiConfiguration.getInstance().getPotentialVisible())
                drawPinText(junction, parameterNode, transformationNode, bufferedImage);
                
                break;

            case T_PART:
                // TODO draw part specific descriptions and symbols, e.g. connector descriptions
                PartNode partNode = (PartNode) elementNode;

                // TODO retrieve pins and match them to correct names
                // for now, connectors are in fixed order as are they returned by
                // getPartConnectors() method.

                // draw name and value
                //if (GuiConfiguration.getInstance().isConnectorNamesVisible()) 
                {
                    drawPartText(((Part) partNode.getElement()).getPartProperties(),
                                 partNode.getRotationCenter(), nodeG2D);
                }

                ArrayList<String> connectorValues = ((Part) partNode.getElement()).getPartProperties().getPartPinValues();
                // search for pins, draw them and display their names
                int i = 0;
                for (PinNode cn : partNode.getPartPins()) {
                    Pin c = (Pin) cn.getElement();
                    drawPin(c, parameterNode, nodeG2D);
                    // check, whether connector names should be printed
               //   if (GuiConfiguration.getInstance().isConnectorNamesVisible()) {
                    	if(GuiConfiguration.getInstance().getPotentialVisible())
                        drawPinText(c, c.getPinPotential(c.hashCode()), nodeG2D);
                   // } //*/
                    
                   // drawPinText(c, parameterNode, transformationNode, bufferedImage);     
                    
                    i++;
                }

                // draw partRotationCenter, if different from [0,0]
                UnitPoint rotationCenter = partNode.getRotationCenter();
                if ((rotationCenter.getX() != 0) && (rotationCenter.getY() != 0)) {
                    nodeG2D.setColor(Color.WHITE);
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
     * @param g2d
     *            graphic context, in which all drawing is done.
     * @param shape
     *            shape to be visualized.
     * @param strokeColor
     *            color of stroke.
     * @param strokeStyle
     *            style of stroke.
     * @param fillColor
     *            color of fill.
     * @param fillStyle
     *            style of fill.
     */
    private void drawShape(Graphics2D g2d, Shape shape, Color strokeColor,
            ElementStyle strokeStyle, Color fillColor, ElementStyle fillStyle) {

        logger.trace("stroke: " + g2d.getStroke());

        if (fillStyle != ElementStyle.NONE) {
            g2d.setColor(fillColor);
            g2d.fill(shape);
        }

        if (strokeStyle != ElementStyle.NONE) {
            g2d.setColor(strokeColor);
            if (strokeStyle == ElementStyle.DOTTED) {
                float[] dots = { 1, 2 };
                g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1,
                        dots, 0));
            }
            g2d.draw(shape);
        }
    }
    
   // private void drawLabel

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
     * @param zoomFactor
     *            the zoomFactor to set
     */
    private void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    /**
     * Draws pin. Currently pin is set to be red filled circle.
     * 
     * @param pin
     *            {@link Pin} to draw.
     * @param parameterNode
     *            {@link ParameterNode} of {@link Pin}.
     * @param nodeG2D
     *            {@link Graphics2D} for painting.
     */
    private void drawPin(final Pin pin, ParameterNode parameterNode, Graphics2D nodeG2D) {
        if(!pin.getVisible())return;
    	logger.trace("drawing connector at coordinates: "
                     + new UnitPoint(pin.getX().firstElement(), pin.getY().firstElement()));

        Ellipse2D.Double e2d = new Ellipse2D.Double(
                (pin.getX().firstElement().doubleValue() * getZoomFactor()) - 2,
                (pin.getY().firstElement().doubleValue() * getZoomFactor()) - 2, 5, 5);
        drawShape(nodeG2D, e2d, Color.RED, ElementStyle.NORMAL, Color.RED, ElementStyle.NORMAL);
    }

    private void drawText(Graphics2D nodeG2D, String text, UnitPoint coordinates, Color color,
            Font font) {
        logger.trace("drawing text <" + text + "> at coordinates: " + coordinates);

        nodeG2D.setColor(color);
        nodeG2D.setFont(font);
        nodeG2D.drawString(text, coordinates.getUnitX().floatValue(),
                           coordinates.getUnitY().floatValue());
    }
    
    
    private void drawPinText(Element element, ParameterNode parameterNode, TransformationNode transformationNode, BufferedImage bufferedImage){
    	/**
    	 * @author Karel K�rber
    	 * 
    	 */
    	UnitPoint up = new UnitPoint((int) (element.getX().firstElement().floatValue()), 
				(int)(element.getY().firstElement().floatValue()));
		
    	Element em = new Text(up, element.getPinPotential(0));
        ElementNode en = NodeFactory.createElementNode(em);
        
        /*
        BufferedImage novy = new BufferedImage((int)(bufferedImage.getWidth() * getZoomFactor()),
        		(int)(bufferedImage.getWidth()*getZoomFactor()), bufferedImage.getType());
        
        bufferedImage.setData(novy.getData());
       
        //*/
        
    	drawNode(en, parameterNode, transformationNode, bufferedImage);
    	
    	
    	
    }

    private void drawPinText(Pin pin, String connectorName, Graphics2D nodeG2D) {
        nodeG2D.setColor(Color.BLACK);
        nodeG2D.drawString(connectorName,
                           (int) (pin.getX().firstElement().floatValue() * getZoomFactor()) +5,    // - 1 ; -10
                           (int) (pin.getY().firstElement().floatValue() * getZoomFactor()) + 15);
    }

    private void drawPartText(PartProperties pp, UnitPoint rc, Graphics2D nodeG2D) {
        nodeG2D.setColor(Color.BLACK);
        nodeG2D.drawString(pp.getProperty("name"), (int) (rc.getX() * getZoomFactor()) - 1,
                           (int) (rc.getY() * getZoomFactor()) - 20);
        if(pp.getProperty("value")!=null)
        nodeG2D.drawString(pp.getProperty("value"), (int) (rc.getX() * getZoomFactor()) - 1,
                           (int) (rc.getY() * getZoomFactor()) + 20);
    }
}
