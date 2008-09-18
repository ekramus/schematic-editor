package cz.cvut.fel.schematicEditor.export;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
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

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;

import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

import cz.cvut.fel.schematicEditor.graphNode.ElementNode;
import cz.cvut.fel.schematicEditor.graphNode.Node;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.TransformationNode;
import cz.cvut.fel.schematicEditor.element.element.part.Wire;
import cz.cvut.fel.schematicEditor.element.element.shape.Arc;
import cz.cvut.fel.schematicEditor.element.element.shape.BezierCurve;
import cz.cvut.fel.schematicEditor.element.element.shape.Ellipse;
import cz.cvut.fel.schematicEditor.element.element.shape.Line;
import cz.cvut.fel.schematicEditor.element.element.shape.Polyline;
import cz.cvut.fel.schematicEditor.element.element.shape.Rectangle;
import cz.cvut.fel.schematicEditor.element.element.shape.Text;
import cz.cvut.fel.schematicEditor.element.properties.ElementStyle;

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
	private static Logger logger;

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
	private double zoomFactor;

	/**
	 * This is constructor.
	 */
	private DisplayExport() {
		logger = Logger.getLogger(this.getClass().getName());

		setZoomFactor(1);
		setAntialiased(true);
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
	 * Draws given element node. To draw it, this method uses parameter and
	 * transformation node instances.
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
		UnitRectangle bounds = elementNode.getBounds(parameterNode.getWidth());
		BufferedImage nodeImg = new BufferedImage((int) bounds.getWidth(),
				(int) bounds.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D nodeG2D = (Graphics2D) nodeImg.getGraphics();

		if (isDebuged()) {
			nodeG2D.setColor(Color.ORANGE);
			nodeG2D.drawRect(0, 0, (int) bounds.getWidth() - 1, (int) bounds
					.getHeight() - 1);
		}

		BasicStroke basicStroke = new BasicStroke(parameterNode.getWidth()
				.floatValue());
		nodeG2D.setStroke(basicStroke);

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

		switch (elementNode.getElementType()) {
		case T_LINE:
			Line l = (Line) elementNode.getElement();
			Line2D.Double l2d = new Line2D.Double(
					l.getX().get(0).doubleValue(), l.getY().get(0)
							.doubleValue(), l.getX().get(1).doubleValue(), l
							.getY().get(1).doubleValue());

			drawShape(nodeG2D, l2d, parameterNode.getColor(), parameterNode
					.getLineStyle(), null, parameterNode.getFillStyle());

			break;

		case T_RECTANGLE:
			Rectangle rectangle = (Rectangle) elementNode.getElement();
			Rectangle2D.Double rectangle2d = new Rectangle2D.Double(rectangle
					.getTopLeftX(), rectangle.getTopLeftY(), rectangle
					.getWidth(), rectangle.getHeight());

			drawShape(nodeG2D, rectangle2d, parameterNode.getColor(),
					parameterNode.getLineStyle(), parameterNode.getFill(),
					parameterNode.getFillStyle());
			break;

		case T_ELLIPSE:
			Ellipse ellipse = (Ellipse) elementNode.getElement();
			Ellipse2D.Double ell = new Ellipse2D.Double(ellipse.getTopLeftX(),
					ellipse.getTopLeftY(), ellipse.getWidth(), ellipse
							.getHeight());

			drawShape(nodeG2D, ell, parameterNode.getColor(), parameterNode
					.getLineStyle(), parameterNode.getFill(), parameterNode
					.getFillStyle());
			break;

		case T_ARC:
			Arc arc = (Arc) elementNode.getElement();
			Arc2D.Double arc2d = new Arc2D.Double(arc.getTopLeftX(), arc
					.getTopLeftY(), arc.getWidth(), arc.getHeight(), arc
					.getStartAngle(), arc.getArcAngle(), Arc2D.PIE);

			drawShape(nodeG2D, arc2d, parameterNode.getColor(), parameterNode
					.getLineStyle(), parameterNode.getFill(), parameterNode
					.getFillStyle());
			break;

		case T_ARC_SEGMENT:
			Arc arcS = (Arc) elementNode.getElement();
			Arc2D.Double arcS2d = new Arc2D.Double(arcS.getTopLeftX(), arcS
					.getTopLeftY(), arcS.getWidth(), arcS.getHeight(), arcS
					.getStartAngle(), arcS.getArcAngle(), Arc2D.OPEN);

			drawShape(nodeG2D, arcS2d, parameterNode.getColor(), parameterNode
					.getLineStyle(), parameterNode.getFill(), parameterNode
					.getFillStyle());
			break;

		case T_TRIANGLE:
		case T_POLYGON:
			Polyline pg = (Polyline) elementNode.getElement();
			Polygon p = new Polygon();

			Vector<Unit> xPg = pg.getX();
			Vector<Unit> yPg = pg.getY();

			for (int i = 0; i < xPg.size(); i++) {
				p.addPoint(xPg.get(i).intValue(), yPg.get(i).intValue());
			}
			drawShape(nodeG2D, p, parameterNode.getColor(), parameterNode
					.getLineStyle(), parameterNode.getFill(), parameterNode
					.getFillStyle());
			break;

		case T_POLYLINE:
			Polyline poly = (Polyline) elementNode.getElement();

			Vector<Unit> xPo = poly.getX();
			Vector<Unit> yPo = poly.getY();

			for (int i = 0; i < xPo.size() - 1; i++) {
				Line2D.Double line2d = new Line2D.Double(xPo.get(i)
						.doubleValue(), yPo.get(i).doubleValue(), xPo
						.get(i + 1).doubleValue(), yPo.get(i + 1).doubleValue());
				drawShape(nodeG2D, line2d, parameterNode.getColor(),
						parameterNode.getLineStyle(), null, parameterNode
								.getFillStyle());
			}
			break;

		case T_BEZIER:
			BezierCurve bC = (BezierCurve) elementNode.getElement();

			CubicCurve2D.Double quadCurve = new CubicCurve2D.Double(bC
					.getStart().getX(), bC.getStart().getY(), bC.getControl1()
					.getX(), bC.getControl1().getY(), bC.getControl2().getX(),
					bC.getControl2().getY(), bC.getEnd().getX(), bC.getEnd()
							.getY());

			drawShape(nodeG2D, quadCurve, parameterNode.getColor(),
					parameterNode.getLineStyle(), parameterNode.getFill(),
					parameterNode.getFillStyle());

			break;

		case T_TEXT:

			Text text = (Text) elementNode.getElement();

			Font font = new Font(text.getFontName(), text.getStyle(), text
					.getSize());
			FontRenderContext frc = nodeG2D.getFontRenderContext();

			TextLayout layout = new TextLayout(text.getText(), font, frc);

			Rectangle2D rec = layout.getBounds();
			bounds = new UnitRectangle(rec.getX()
					+ text.getX().get(0).doubleValue(), rec.getY()
					+ text.getY().get(0).doubleValue(), rec.getWidth(), rec
					.getHeight());
			nodeImg = new BufferedImage((int) rec.getWidth(), (int) rec
					.getHeight(), BufferedImage.TYPE_INT_ARGB);

			nodeG2D = (Graphics2D) nodeImg.getGraphics();
			nodeG2D.translate(-bounds.getX(), -bounds.getY());

			if (parameterNode.getColor() != null) {
				nodeG2D.setColor(parameterNode.getColor());
			}

			layout.draw(nodeG2D, text.getX().get(0).floatValue(), text.getY()
					.get(0).floatValue());
			break;

		case T_WIRE:
			Wire wire = (Wire) elementNode.getElement();

			Vector<Unit> xWi = wire.getX();
			Vector<Unit> yWi = wire.getY();

			for (int i = 0; i < xWi.size() - 1; i++) {
				Line2D.Double line2d = new Line2D.Double(xWi.get(i)
						.doubleValue(), yWi.get(i).doubleValue(), xWi
						.get(i + 1).doubleValue(), yWi.get(i + 1).doubleValue());
				drawShape(nodeG2D, line2d, parameterNode.getColor(),
						ElementStyle.DOTTED, null, parameterNode.getFillStyle());
			}
			break;

		case T_PART:
			// TODO draw part specific parts (connectors, description, etc)
			break;

		default:
			break;
		}

		double m[][] = transformationNode.getTransformation()
				.getTransformationMatrix();

		Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();

		g2d.setTransform(new AffineTransform(m[0][0], m[1][0], m[0][1],
				m[1][1], m[0][2], m[1][2]));
		g2d.translate(bounds.getX(), bounds.getY());
		g2d.drawImage(nodeImg, 0, 0, null);

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

		if (fillStyle != ElementStyle.NONE) {
			g2d.setColor(fillColor);
			g2d.fill(shape);
		}

		if (strokeStyle != ElementStyle.NONE) {
			g2d.setColor(strokeColor);
			if (strokeStyle == ElementStyle.DOTTED) {
				float[] dots = { 1, 2 };
				Stroke stroke = new BasicStroke(1, BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_ROUND, 1, dots, 0);
				g2d.setStroke(stroke);
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
	 * @param zoomFactor
	 *            the zoomFactor to set
	 */
	private void setZoomFactor(double zoomFactor) {
		this.zoomFactor = zoomFactor;
	}
}
