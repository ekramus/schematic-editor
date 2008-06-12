package cz.cvut.fel.schematicEditor.export;

import cz.cvut.fel.schematicEditor.graphNode.ElementNode;
import cz.cvut.fel.schematicEditor.graphNode.Node;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.TransformationNode;

import java.awt.Color;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;

import cz.cvut.fel.schematicEditor.types.ElementType;
import cz.cvut.fel.schematicEditor.types.Transformation;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.element.shape.*;

public class PSExport implements Export {

    private boolean monochromaticColor = false;

    PrintStream     out;
    File            file;

    public void export(SceneGraph sg, Object output) {

        file = (File) output;
        FileOutputStream fos = null;
        TransformationNode tn = null;
        ParameterNode pn = null;
        ElementNode en = null;

        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        out = new PrintStream(fos);

        printHead(new Point2D.Float(0, 0), new Point2D.Float(1000, 1000));

        for (Node node : sg) {
            if (node instanceof TransformationNode) {
                tn = (TransformationNode) node;
            } else if (node instanceof ParameterNode) {
                pn = (ParameterNode) node;
            } else if (node instanceof ElementNode) {
                en = (ElementNode) node;
                drawNode(en, pn, tn);
            }
        }

        printFooter();

        out.close();
    }

    private void drawNode(ElementNode en, ParameterNode pn, TransformationNode tn) {

        switch (en.getElementType()) {

            case ElementType.T_LINE:
                Line l = (Line) en.getElement();
                drawLine(new Point2D.Double(l.getX().get(0).doubleValue(), l.getY().get(0)
                        .doubleValue()), new Point2D.Double(l.getX().get(1).doubleValue(), l.getY()
                        .get(1).doubleValue()), pn, tn.getTransformation());
                break;
            case ElementType.T_RECTANGLE:
                Rectangle r = (Rectangle) en.getElement();
                drawRectangle(new Point2D.Double(r.getTopLeftX(), r.getTopLeftY()), r.getWidth(), r
                        .getHeight(), pn, tn.getTransformation());
                break;
            case ElementType.T_ARC:
                Arc ar = (Arc) en.getElement();
                drawArc(ar, pn, tn.getTransformation());
                break;
            case ElementType.T_ELLIPSE:
                Ellipse el = (Ellipse) en.getElement();
                drawEllipse(el, pn, tn.getTransformation());
                break;
            case ElementType.T_TRIANGLE:
            case ElementType.T_POLYGON:
                Polygon p = (Polygon) en.getElement();
                drawPoly(true, p.getX(), p.getY(), pn, tn.getTransformation());
                break;
            case ElementType.T_POLYLINE:
                Polyline d = (Polyline) en.getElement();
                drawPoly(false, d.getX(), d.getY(), pn, tn.getTransformation());
                break;

            case ElementType.T_BEZIER:
                BezierCurve bZ = (BezierCurve) en.getElement();
                drawBezier(bZ, pn, tn.getTransformation());
                break;

            case ElementType.T_TEXT:
                Text t = (Text) en.getElement();
                drawText(t.getText(), t.getSize(), new Point2D.Double(
                        t.getX().get(0).doubleValue(), t.getY().get(0).doubleValue()), pn, tn
                        .getTransformation());
                break;

            default:
                break;
        }

    }

    private void printHead(Point2D.Float startBoundingBox, Point2D.Float endBoundingBox) {

        out.println("%!PS-Adobe-3.0 EPSF-3.0");

        out.println("%%BoundingBox: " + (int) startBoundingBox.getX()
                + " "
                + (int) startBoundingBox.getX()
                + " "
                + (int) endBoundingBox.getX()
                + " "
                + (int) endBoundingBox.getY());
        out.println("%%Title: " + this.file.getName());
        out.println("%%Creator: Schematic Editor");
        out.println("%%Pages: 1");

        out.println("%%EndProlog");
        out.println("%%Page: 1 1");

        out.println("1 -1 scale");
        out.println("0 -" + endBoundingBox.getX() + " translate");
        out.println("gsave");

    }

    private void printFooter() {
        out.println("grestore");
        out.println(" showpage");
        out.println("%%EOF");

    }

    private void drawPoly(boolean closedPath, Vector<Unit> x, Vector<Unit> y, ParameterNode pn,
            Transformation tn) {

        out.println(" gsave");

        this.printTransformString(tn);

        printStyleString(pn);

        out.print(" newpath");

        int size;

        if (closedPath) {
            size = x.size() - 1;
        } else {
            size = x.size();
        }

        for (int i = 0; i < size; i++) {
            if (i == 0) {
                out.print(" " + x.get(i) + " " + y.get(i) + " moveto");
            }
            out.print(" " + x.get(i) + " " + y.get(i) + " lineto");
        }

        if (closedPath) {
            out.print(" closepath");
        }

        printFill(pn.getColor(), pn.getFill());

        out.println(" grestore");
    }

    private void drawEllipse(Ellipse el, ParameterNode pn, Transformation tn) {

        float ratio = (float) el.getHeight() / (float) el.getWidth();

        out.println(" gsave");

        this.printTransformString(tn);
        out.println("1 " + "" + ratio + "" + " scale");
        printStyleString(pn);

        out.println("newpath");
        out.println((el.getX().get(0).doubleValue() + el.getWidth() / 2) + " "
                + ((el.getY().get(0).doubleValue() / ratio + el.getWidth() / 2))
                + " "
                + el.getWidth()
                / 2
                + " "
                + " 0 "
                + " "
                + " 360"
                + " arc");

        printFill(pn.getColor(), pn.getFill());

        out.println(" grestore");
    }

    private void drawRectangle(Point2D.Double start, double width, double height, ParameterNode pn,
            Transformation tn) {
        out.println(" gsave");

        this.printTransformString(tn);

        printStyleString(pn);

        out.print(start.getX() + " " + start.getY() + " " + width + " " + height + " rectstroke");

        out.println(" grestore");
    }

    private void drawLine(Point2D start, Point2D finish, ParameterNode pn, Transformation tn) {

        out.println(" gsave");

        this.printTransformString(tn);

        printStyleString(pn);

        out.print(" newpath");
        out.print(" " + finish.getX() + " " + finish.getY() + " moveto");
        out.print(" " + start.getX() + " " + start.getY() + " lineto");

        printFill(pn.getColor(), pn.getFill());

        out.println(" grestore");
    }

    private void drawBezier(BezierCurve bC, ParameterNode pn, Transformation tn) {
        out.println(" gsave");

        this.printTransformString(tn);
        printStyleString(pn);

        out.println(bC.getX().get(0) + " " + bC.getY().get(0) + " moveto");
        out.println(bC.getX().get(2) + " "
                + bC.getY().get(2)
                + " "
                + bC.getX().get(3)
                + " "
                + bC.getY().get(3)
                + " "
                + bC.getX().get(1)
                + " "
                + bC.getY().get(1)
                + " curveto");

        printFill(pn.getColor(), pn.getFill());

        out.println(" grestore");
    }

    private void drawArc(Arc arc, ParameterNode pn, Transformation tn) {
        out.println(" gsave");

        this.printTransformString(tn);
        printStyleString(pn);

        out.println("newpath");
        out.println((arc.getX().get(0).doubleValue() + arc.getWidth() / 2) + " "
                + (arc.getY().get(0).doubleValue() + arc.getWidth() / 2)
                + " "
                + arc.getWidth()
                / 2
                + " "
                + (-arc.getStartAngle())
                + " "
                + (-(arc.getArcAngle() + arc.getStartAngle()))
                + " arcn");

        printFill(pn.getColor(), pn.getFill());
        out.println(" grestore");
    }

    private void drawText(String text, int size, Point2D.Double start, ParameterNode pn,
            Transformation tn) {

        out.println("gsave");

        this.printTransformString(tn);
        out.println(start.getX() + " " + start.getY() + " moveto");
        printStyleString(pn);

        out.println("-1 1 scale");
        out.println("/Helvetica findfont");
        out.println("-" + size + " scalefont setfont");
        out.println("(" + text + ") show");

        out.println("grestore");

    }

    private void printFill(Color strokeColor, Color fillColor) {

        if ((strokeColor != null) && (fillColor == null)) {
            out.println(" stroke");
        } else if ((strokeColor == null) && (fillColor != null)) {
            out.println((fillColor.getRed() / 255.0) + " "
                    + (fillColor.getGreen() / 255.0)
                    + " "
                    + (fillColor.getBlue() / 255.0)
                    + " "
                    + " setrgbcolor");
            out.println(" fill");
        } else if ((strokeColor != null) && (fillColor != null)) {
            out.println("gsave");

            out.println((fillColor.getRed() / 255.0) + " "
                    + (fillColor.getGreen() / 255.0)
                    + " "
                    + (fillColor.getBlue() / 255.0)
                    + " "
                    + " setrgbcolor");
            out.println("fill");

            out.println("grestore");

            out.println("stroke");
        }

    }

    private void printTransformString(Transformation t) {

        if (t == null || t.isIdentity()) {
            return;
        }

        out.println("[" + t.toString() + "] concat");

    }

    private void printStyleString(ParameterNode pn) {

        if (pn == null) {
            return;
        }

        Color col;

        // stroking color
        if (this.monochromaticColor) {
            col = Color.BLACK;
        } else {
            col = pn.getColor();
        }

        if (col != null) {
            out.println((col.getRed() / 255.0) + " "
                    + (col.getGreen() / 255.0)
                    + " "
                    + (col.getBlue() / 255.0)
                    + " "
                    + " setrgbcolor");
        }

        // stroking width
        int width = pn.getWidth().intValue();
        if (width != 1) {
            out.println(width + " setlinewidth");
        }

    }

}
