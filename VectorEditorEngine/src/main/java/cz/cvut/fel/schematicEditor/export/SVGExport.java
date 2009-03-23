package cz.cvut.fel.schematicEditor.export;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.element.element.part.Junction;
import cz.cvut.fel.schematicEditor.element.element.part.Pin;
import cz.cvut.fel.schematicEditor.element.element.part.Wire;
import cz.cvut.fel.schematicEditor.element.element.shape.Arc;
import cz.cvut.fel.schematicEditor.element.element.shape.BezierCurve;
import cz.cvut.fel.schematicEditor.element.element.shape.Ellipse;
import cz.cvut.fel.schematicEditor.element.element.shape.Line;
import cz.cvut.fel.schematicEditor.element.element.shape.Polygon;
import cz.cvut.fel.schematicEditor.element.element.shape.Polyline;
import cz.cvut.fel.schematicEditor.element.element.shape.Rectangle;
import cz.cvut.fel.schematicEditor.element.element.shape.Text;
import cz.cvut.fel.schematicEditor.element.properties.ElementStyle;
import cz.cvut.fel.schematicEditor.graphNode.ElementNode;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.JunctionNode;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.graphNode.PinNode;
import cz.cvut.fel.schematicEditor.graphNode.ShapeNode;
import cz.cvut.fel.schematicEditor.graphNode.TransformationNode;
import cz.cvut.fel.schematicEditor.graphNode.WireNode;
import cz.cvut.fel.schematicEditor.support.Transformation;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

/**
 * This class represents export to the SVG graphic format.
 *
 * @author Zdenek Straka
 * @author Urban Kravjansky
 */
public class SVGExport implements Export {
    /**
     * This field represents monochromatic status.
     */
    private boolean     monochromaticColor;
    /**
     * Represents output {@link PrintStream} for SVG output.
     */
    private PrintStream out;

    /**
     * Starts exporting into SVG
     *
     * @param sg actual SceneGraph instance.
     * @param zoomFactor this field is omitted.
     * @param output output {@link File} object.
     */
    public void export(SceneGraph sg, double zoomFactor, Object output) {
        File file = (File) output;
        FileOutputStream fos = null;

        // open file
        try {
            fos = new FileOutputStream(file);
            this.out = new PrintStream(fos, false, "utf-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // print header
        print(getHead(1000, 1000, 1000, 1000));
        //
        search(sg.getTopNode());
        // print footer
        printFooter();
        // finish
        this.out.close();
    }

    /**
     * Creates style string for given {@link ParameterNode}.
     *
     * @param pn given {@link ParameterNode}.
     * @return String representing style attribute.
     */
    private String createStyleString(ParameterNode pn) {
        String styleString;
        Color col;
        String colorString;
        String opacity;
        String fillString;
        String widthString;
        int width;

        if (pn == null) {
            styleString = "";
            return styleString;
        }

        // stroking color
        if (this.monochromaticColor) { // if export has to be monochromatic
            col = Color.BLACK;
        } else {
            col = pn.getColor();
        }
        if (col != null) {
            colorString = "stroke:rgb(" + String.valueOf(col.getRed() + ","
                    + col.getGreen()
                    + ","
                    + col.getBlue()
                    + ")");
        } else {
            colorString = "stroke:none";
        }

        // fill color
        col = pn.getFill();
        if (col != null) {
            fillString = "fill:rgb(" + String.valueOf(col.getRed() + "," + col.getGreen() + "," + col.getBlue() + ")");
        } else {
            fillString = "fill:none";
        }

        try {
            // fill opacity
            opacity = " opacity=\"" + ((double) col.getAlpha() / 255) + "\"";
        } catch (NullPointerException e) {
            opacity = " opacity=\"1\"";
        }

        // stroking width
        width = pn.getWidth().intValue();
        if (width > 0) {
            widthString = "stroke-width:" + String.valueOf(width);
        } else {
            widthString = "stroke-width:1";
        }

        styleString = opacity + " style=\"" + fillString + ";" + colorString + ";" + widthString + "\"";

        return styleString;
    }

    /**
     * This method creates transformation string from given Transformation.
     *
     * @param t transformation to process.
     * @return String representing transform attribute
     */
    private String createTransformString(Transformation t) {
        String transformString = "";

        // TODO reimplement this with correct transformation string for SVG

        if (t == null) {
            return transformString;
        } else if (!t.isIdentity()) {
            transformString = " transform=\"matrix(" + t.toString() + ")\"";
        } else {
            transformString = "";
        }

        return transformString;
    }

    /**
     * Draw arc
     */
    // TODO finish arc
    private void drawArc(Arc arc, ParameterNode pn, Transformation tn) {

        double difEndX = Math.cos(Math.toRadians(arc.getArcAngle()));
        double difEndY = Math.sin(Math.toRadians(arc.getArcAngle()));

        String param = "";

        if (difEndX >= 0) {
            double x = arc.getWidth() / 2 * difEndX;
            difEndX = -((arc.getWidth() / 2) - x);
        } else {
            difEndX = -Math.abs(difEndX * (arc.getWidth() / 2)) - arc.getWidth() / 2;
        }

        if (difEndY > 0) {
            difEndY = -difEndY * (arc.getWidth() / 2);
        } else {
            difEndY = Math.abs(difEndY * (arc.getWidth() / 2));
        }

        if (arc.getArcAngle() < 180) {
            param = "0,0 ";
        } else {
            param = "1,0 ";
        }

        double semiWidth = arc.getWidth() / 2;

        this.out.println("<path transform=\"translate(" + (arc.getX().get(0).doubleValue() + semiWidth)
                + ","
                + (arc.getY().get(0).doubleValue() + semiWidth)
                + "),rotate("
                + (-arc.getStartAngle())
                + "),translate("
                + (-(arc.getX().get(0).doubleValue() + semiWidth))
                + ","
                + (-(arc.getY().get(0).doubleValue() + semiWidth))
                + ")\" d=\"M"
                + (arc.getX().get(0).doubleValue() + arc.getWidth())
                + ","
                + (arc.getY().get(0).doubleValue() + arc.getWidth() / 2)
                + " a"
                + semiWidth
                + ","
                + semiWidth
                + " 0 "
                + param
                + ""
                + difEndX
                + ","
                + difEndY
                + " \""
                + createTransformString(tn)
                + ""
                + createStyleString(pn)
                + " />");

    }

    /**
     * Draw BezierCurve
     */
    private void drawCubicCurve(BezierCurve bC, ParameterNode pn, Transformation tn) {
        this.out.println("<path d=\"M" + bC.getX().get(0).doubleValue()
                + ","
                + bC.getY().get(0).doubleValue()
                + " C"
                + bC.getX().get(2).doubleValue()
                + ","
                + bC.getY().get(2).doubleValue()
                + " "
                + bC.getX().get(3).doubleValue()
                + ","
                + bC.getY().get(3).doubleValue()
                + " "
                + bC.getX().get(1).doubleValue()
                + ","
                + bC.getY().get(1).doubleValue()
                + "\" "
                + createTransformString(tn)
                + ""
                + createStyleString(pn)
                + " />");
    }

    /**
     * Draw ellipse defined by center point a with x,y radius
     */
    private void drawEllipse(Point2D.Double center, double radiusX, double radiusY, ParameterNode pn, Transformation tn) {
        this.out.println("<ellipse cx=\"" + center.x
                + "\" cy=\""
                + center.y
                + "\" rx=\""
                + radiusX
                + "\" ry=\""
                + radiusY
                + "\""
                + createTransformString(tn)
                + ""
                + createStyleString(pn)
                + "/>");

    }

    /*
     * Draw line.
     */
    private void drawLine(Point2D.Double start, Point2D.Double finish, ParameterNode pn, Transformation tn) {
        this.out.println("<line x1=\"" + start.getX()
                + "\" y1=\""
                + start.getY()
                + "\" x2=\""
                + finish.getX()
                + "\" y2=\""
                + finish.getY()
                + "\""
                + createTransformString(tn)
                + ""
                + createStyleString(pn)
                + "/>");
    }

    /**
     * This method draw node with its transformation and parameters
     *
     * @param en
     * @param pn
     * @param tn
     */
    private void drawNode(ElementNode en, ParameterNode pn, Transformation tn) {

        switch (en.getElement().getElementType()) {

            case T_LINE:
                Line l = (Line) en.getElement();
                drawLine(new Point2D.Double(l.getX().get(0).doubleValue(), l.getY().get(0).doubleValue()),
                         new Point2D.Double(l.getX().get(1).doubleValue(), l.getY().get(1).doubleValue()), pn, tn);
                break;

            case T_RECTANGLE:
                Rectangle r = (Rectangle) en.getElement();
                drawRectangle(new Point2D.Double(r.getTopLeftX(), r.getTopLeftY()), r.getWidth(), r.getHeight(), pn, tn);
                break;

            case T_ARC:
            case T_ARC_SEGMENT:
                Arc arc = (Arc) en.getElement();
                drawArc(arc, pn, tn);
                break;

            case T_ELLIPSE:
                Ellipse ellipse = (Ellipse) en.getElement();
                drawEllipse(new Point2D.Double(ellipse.getX().get(0).doubleValue() + (ellipse.getWidth() / 2), ellipse
                        .getY().get(0).doubleValue() + (ellipse.getHeight() / 2)), ellipse.getWidth() / 2, ellipse
                        .getHeight() / 2, pn, tn);
                break;

            case T_TRIANGLE:
            case T_POLYGON:
                Polygon polygon = (Polygon) en.getElement();
                drawPoly(true, polygon.getX(), polygon.getY(), pn, tn);
                break;

            case T_POLYLINE:
                Polyline d = (Polyline) en.getElement();
                drawPoly(false, d.getX(), d.getY(), pn, tn);
                break;

            case T_BEZIER:
                BezierCurve bC = (BezierCurve) en.getElement();
                drawCubicCurve(bC, pn, tn);
                break;

            case T_TEXT:
                Text tx = (Text) en.getElement();
                drawText(tx, new Point2D.Double(tx.getX().get(0).doubleValue(), tx.getY().get(0).doubleValue()), pn, tn);
                break;

            case T_WIRE:
                Wire w = (Wire) en.getElement();
                ParameterNode wpn = new ParameterNode();
                wpn.setFillStyle(ElementStyle.NONE);
                wpn.setFill(null);
                drawPoly(false, w.getX(), w.getY(), wpn, tn);
                break;

            case T_PART:
                PartNode partNode = (PartNode) en;
                for (PinNode up : partNode.getPartPins()) {
                    Pin pin = (Pin) up.getElement();
                    UnitPoint pinCenter = new UnitPoint(pin.getX().firstElement(), pin.getY().firstElement());
                    drawPin(pinCenter, pn, tn);
                }
                break;

            case T_JUNCTION:
                Junction junction = (Junction) en.getElement();
                drawJunction(new UnitPoint(junction.getX().firstElement(), junction.getY().firstElement()), pn, tn);
                break;

            default:
                System.out.println(en.getElement().getElementType());
                break;
        }

    }

    /**
     * Draws either polyline or polygon. Decision is based on parameter <code>closedPath</code>.
     *
     * @param closedPath true for polygon, false for polyline.
     * @param x {@link Vector} of <code>x</code> coordinates.
     * @param y {@link Vector} of <code>y</code> coordinates.
     * @param pn Instance of {@link ParameterNode} for this shape.
     * @param tn Instance of {@link TransformationNode} for this shape.
     */
    private void drawPoly(boolean closedPath, Vector<Unit> x, Vector<Unit> y, ParameterNode pn, Transformation tn) {
        StringBuilder buf = new StringBuilder();
        int size = x.size();

        // select either polyline or polygon
        if (closedPath) {
            buf.append("<polygon points=\"");
            // this.out.print("<polygon points=\"");
            // last element (which is same as first), will not be included
            size -= 1;
        } else {
            buf.append("<polyline points=\"");
        }

        // iterate through all coordinates
        for (int i = 0; i < size; i++) {
            buf.append(x.get(i).doubleValue() + "," + y.get(i).doubleValue() + " ");
        }
        buf.deleteCharAt(buf.length() - 1);

        // finish quote
        buf.append("\" ");

        // append transform string
        buf.append(createTransformString(tn) + "" + createStyleString(pn) + "/>");

        // output string
        this.out.print(buf);
    }

    /**
     * Draws rectangle.
     */
    private void drawRectangle(Point2D.Double start, double width, double height, ParameterNode pn, Transformation tn) {
        this.out.println("<rect x=\"" + start.getX()
                + "\" y=\""
                + start.getY()
                + "\" width=\""
                + width
                + "\" height=\""
                + height
                + "\""
                + createTransformString(tn)
                + ""
                + createStyleString(pn)
                + "/>");
    }

    /*
     * Draw text
     */
    private void drawText(Text text, Point2D.Double start, ParameterNode pn, Transformation tn) {
        Color color = pn.getColor();

        if (color == null) {
            color = Color.BLACK;
        }

        this.out.println("<text x=\"" + start.getX()
                + "\" y=\""
                + start.getY()
                + "\" font-size=\""
                + text.getSize()
                + "\" font-family=\"Helvetica\" fill=\"rgb("
                + String.valueOf(color.getRed() + ","
                        + color.getGreen()
                        + ","
                        + color.getBlue()
                        + ")\""
                        + createTransformString(tn)
                        + " >"));

        this.out.println(text.getText());

        this.out.println("</text>");

    }

    /**
     * @return the out
     */
    private final PrintStream getOut() {
        return this.out;
    }

    /**
     * @return the monochromaticColor
     */
    private boolean isMonochromaticColor() {
        return this.monochromaticColor;
    }

    /**
     * Prints given {@link String} into <code>output</code>.
     *
     * @param s {@link String} to print.
     */
    private final void print(final String s) {
        getOut().print(s);
    }

    /**
     * Print <i>svg</i> tag to end of document
     */
    private void printFooter() {
        this.out.println("</svg>");
    }

    /**
     * Prints head of SVG document with necessary information.
     *
     * @param width
     * @param height
     * @param viewBoxWidth
     * @param viewBoxHeight
     * @return
     */
    private String getHead(int width, int height, int viewBoxWidth, int viewBoxHeight) {
        StringBuilder result = new StringBuilder();

        result.append("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\" ?>\n");
        result
                .append("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n");
        result.append("<svg width=\"" + width
                + "px\" height=\""
                + height
                + "px\" viewBox=\"0 0 "
                + viewBoxWidth
                + " "
                + viewBoxHeight
                + "\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">");

        return result.toString();
    }

    // TODO modify this to use same approach as <code>DisplayExport</code>.
    /**
     * Depth-first search of Node tree
     */
    private String search(GroupNode gN) {
        StringBuilder result = new StringBuilder();

        Transformation t = gN.getTransformation();
        ParameterNode p = gN.getChildrenParameterNode();
        String helpOutputString = null;

        int childrenElementCount = gN.getChildrenElementList().size();
        int childrenGroupNodeCount = gN.getChildrenGroupList().size();

        if ((childrenElementCount != 1) || (childrenGroupNodeCount != 0)) {
            helpOutputString = "<g" + createTransformString(t);
            helpOutputString = helpOutputString + createStyleString(p);
            String outputString = helpOutputString + ">";
            this.out.println(outputString);
        }

        for (ElementNode child : gN.getChildrenElementList()) {
            if (child instanceof ShapeNode) {
                if ((childrenElementCount == 1) && (childrenGroupNodeCount == 0)) {
                    drawNode(child, p, t);
                } else {
                    drawNode(child, null, null);
                }
            } else if (child instanceof PartNode) {
                drawNode(child, p, t);
                search(((PartNode) child).getPartGroupNode());
            } else if (child instanceof WireNode) {
                drawNode(child, p, t);
            } else if (child instanceof PinNode) {
                drawNode(child, p, t);
            } else if (child instanceof JunctionNode) {
                drawNode(child, p, t);
            }
        }

        for (GroupNode child : gN.getChildrenGroupList()) {
            search(child);

        }

        if ((childrenElementCount != 1) || (childrenGroupNodeCount != 0)) {
            this.out.println("</g>");
        }

        return result.toString();
    }

    /**
     * @param monochromaticColor the monochromaticColor to set
     */
    private void setMonochromaticColor(boolean monochromaticColor) {
        this.monochromaticColor = monochromaticColor;
    }

    /**
     * @param out the out to set
     */
    private final void setOut(final PrintStream out) {
        this.out = out;
    }

    /**
     * Draw pin defined by center point.
     */
    private void drawPin(UnitPoint center, ParameterNode pn, Transformation tn) {
        ParameterNode pinPN = new ParameterNode();
        pinPN.setColor(Color.BLACK);
        pinPN.setFill(null);
        this.out.println("<ellipse cx=\"" + center.getX()
                + "\" cy=\""
                + center.getY()
                + "\" rx=\"2\" ry=\"2\""
                + createTransformString(tn)
                + ""
                + createStyleString(pinPN)
                + "/>");

    }

    /**
     * Draw junction defined by center point.
     */
    private void drawJunction(UnitPoint center, ParameterNode pn, Transformation tn) {
        ParameterNode junctionPN = new ParameterNode();
        junctionPN.setColor(Color.BLACK);
        junctionPN.setFill(Color.BLACK);
        this.out.println("<ellipse cx=\"" + center.getX()
                + "\" cy=\""
                + center.getY()
                + "\" rx=\"2\" ry=\"2\""
                + createTransformString(tn)
                + ""
                + createStyleString(junctionPN)
                + "/>");

    }
}
