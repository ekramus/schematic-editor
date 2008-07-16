package cz.cvut.fel.schematicEditor.export;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

import cz.cvut.fel.schematicEditor.support.Transformation;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.shape.Arc;
import cz.cvut.fel.schematicEditor.element.element.shape.BezierCurve;
import cz.cvut.fel.schematicEditor.element.element.shape.Ellipse;
import cz.cvut.fel.schematicEditor.element.element.shape.Line;
import cz.cvut.fel.schematicEditor.element.element.shape.Polygon;
import cz.cvut.fel.schematicEditor.element.element.shape.Polyline;
import cz.cvut.fel.schematicEditor.element.element.shape.Rectangle;
import cz.cvut.fel.schematicEditor.element.element.shape.Text;
import cz.cvut.fel.schematicEditor.graphNode.ElementNode;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.graphNode.ShapeNode;
import cz.cvut.fel.schematicEditor.graphNode.WireNode;

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
    private boolean isMonochromaticColor = false;
    /**
     * This field reperesents output PrintStream for SVG output.
     */
    PrintStream     out;

    /**
     * Starts exporting into SVG
     * 
     * @param sg
     *            actual SceneGraph instance.
     * @param output
     *            output file name.
     */
    public void export(SceneGraph sg, Object output) {
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
        printHead(500, 500, 500, 500);
        //
        search(sg.getTopNode());
        // print footer
        printFooter();
        // finish
        this.out.close();
    }

    /**
     * This method creates style string for given ParameterNode.
     * 
     * @param pn
     *            given ParameterNode.
     * @return String representing style attribute
     */
    private String createStyleString(ParameterNode pn) {
        String styleString;
        Color col;
        String colorString;
        String fillString;
        String widthString;
        int width;

        if (pn == null) {
            styleString = "";
            return styleString;
        }

        // stroking color
        if (this.isMonochromaticColor) { // if export has to be monochromatic
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
            fillString = "fill:rgb(" + String.valueOf(col.getRed() + ","
                    + col.getGreen()
                    + ","
                    + col.getBlue()
                    + ")");
        } else {
            fillString = "fill:none";
        }

        // stroking width
        width = pn.getWidth().intValue();
        if (width > 0) {
            widthString = "stroke-width:" + String.valueOf(width);
        } else {
            widthString = "stroke-width:1";
        }

        styleString = " style=\"" + fillString + ";" + colorString + ";" + widthString + "\"";

        return styleString;
    }

    /**
     * This method creates transformation string from given Transformation.
     * 
     * @param t
     *            transformation to process.
     * @return String representing transform attribute
     */
    private String createTransformString(Transformation t) {
        String transformString = "";

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

        out.println("<path transform=\"translate(" + (arc.getX().get(0).doubleValue() + semiWidth)
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
        out.println("<path d=\"M" + bC.getX().get(0)
                + ","
                + bC.getY().get(0)
                + " C"
                + bC.getX().get(1)
                + ","
                + bC.getY().get(1)
                + " "
                + bC.getX().get(2)
                + ","
                + bC.getY().get(2)
                + " "
                + bC.getX().get(3)
                + ","
                + bC.getY().get(3)
                + "\" "
                + createTransformString(tn)
                + ""
                + createStyleString(pn)
                + " />");
    }

    /**
     * Draw ellipse defined by center point a with x,y radius
     */
    private void drawEllipse(Point2D.Double center, double radiusX, double radiusY,
            ParameterNode pn, Transformation tn) {
        out.println("<ellipse cx=\"" + center.x
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
    private void drawLine(Point2D.Double start, Point2D.Double finish, ParameterNode pn,
            Transformation tn) {
        out.println("<line x1=\"" + start.getX()
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

        switch (en.getElementType()) {

            case ElementType.T_LINE:
                Line l = (Line) en.getElement();
                drawLine(new Point2D.Double(l.getX().get(0).doubleValue(), l.getY().get(0)
                        .doubleValue()), new Point2D.Double(l.getX().get(1).doubleValue(), l.getY()
                        .get(1).doubleValue()), pn, tn);
                break;

            case ElementType.T_RECTANGLE:
                Rectangle r = (Rectangle) en.getElement();
                drawRectangle(new Point2D.Double(r.getTopLeftX(), r.getTopLeftY()), r.getWidth(), r
                        .getHeight(), pn, tn);
                break;

            case ElementType.T_ARC:
                Arc arc = (Arc) en.getElement();
                drawArc(arc, pn, tn);
                break;

            case ElementType.T_ELLIPSE:
                Ellipse ellipse = (Ellipse) en.getElement();
                drawEllipse(new Point2D.Double(ellipse.getX().get(0).doubleValue() + (ellipse
                        .getWidth() / 2), ellipse.getY().get(0).doubleValue() + (ellipse
                        .getHeight() / 2)), ellipse.getWidth() / 2, ellipse.getHeight() / 2, pn, tn);
                break;

            case ElementType.T_TRIANGLE:
            case ElementType.T_POLYGON:
                Polygon polygon = (Polygon) en.getElement();
                drawPoly(true, polygon.getX(), polygon.getY(), pn, tn);
                break;

            case ElementType.T_POLYLINE:
                Polyline d = (Polyline) en.getElement();
                drawPoly(false, d.getX(), d.getY(), pn, tn);
                break;

            case ElementType.T_BEZIER:
                BezierCurve bC = (BezierCurve) en.getElement();
                drawCubicCurve(bC, pn, tn);
                break;
            case ElementType.T_TEXT:
                Text tx = (Text) en.getElement();
                drawText(tx, new Point2D.Double(tx.getX().get(0).doubleValue(), tx.getY().get(0)
                        .doubleValue()), pn, tn);
                break;
            default:
                System.out.println(en.getElementType());
                break;
        }

    }

    /**
     * This method draw polyline or polygon
     * 
     * @param closedPath
     *            true for polygon, false for polyline
     * @param x
     * @param y
     * @param pn
     * @param tn
     */

    private void drawPoly(boolean closedPath, Vector<Unit> x, Vector<Unit> y, ParameterNode pn,
            Transformation tn) {

        int size;

        if (closedPath) {
            out.print("<polygon points=\"");
            size = x.size() - 1;
        } else {
            out.print("<polyline points=\"");
            size = x.size();
        }

        for (int i = 0; i < size; i++) {
            out.print(x.get(i) + "," + y.get(i) + " ");
        }

        out.print("\" ");

        out.print(createTransformString(tn) + "" + createStyleString(pn) + "/>");

    }

    /*
     * Draw rectangle.
     */
    private void drawRectangle(Point2D.Double start, double width, double height, ParameterNode pn,
            Transformation tn) {
        out.println("<rect x=\"" + start.getX()
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

        out.println("<text x=\"" + start.getX()
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

        out.println(text.getText());

        out.println("</text>");

    }

    /**
     * Print <i>svg</i> tag to end of document
     */
    private void printFooter() {
        out.println("</svg>");
    }

    /**
     * Prints head of SVG document with neccesary information
     */
    private void printHead(int width, int height, int viewBoxWidth, int viewBoxHeight) {
        out.println("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\" ?>");
        out
                .println("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">");
        out.println("<svg width=\"" + width
                + "px\" height=\""
                + height
                + "px\" viewBox=\"0 0 "
                + viewBoxWidth
                + " "
                + viewBoxHeight
                + "\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">");
    }

    /**
     * Depth-first search of Node tree
     */
    private void search(GroupNode gN) {

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
                // nothing to do yet
            } else if (child instanceof WireNode) {
                // nothing to do yet
            }
        }

        for (GroupNode child : gN.getChildrenGroupList()) {
            this.search(child);

        }

        if ((childrenElementCount != 1) || (childrenGroupNodeCount != 0)) {
            this.out.println("</g>");
        }

    }

}
