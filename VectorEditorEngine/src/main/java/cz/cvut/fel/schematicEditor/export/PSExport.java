package cz.cvut.fel.schematicEditor.export;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Vector;

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.element.element.shape.Arc;
import cz.cvut.fel.schematicEditor.element.element.shape.BezierCurve;
import cz.cvut.fel.schematicEditor.element.element.shape.Ellipse;
import cz.cvut.fel.schematicEditor.element.element.shape.Line;
import cz.cvut.fel.schematicEditor.element.element.shape.Polygon;
import cz.cvut.fel.schematicEditor.element.element.shape.Polyline;
import cz.cvut.fel.schematicEditor.element.element.shape.Rectangle;
import cz.cvut.fel.schematicEditor.element.element.shape.Text;
import cz.cvut.fel.schematicEditor.graphNode.ElementNode;
import cz.cvut.fel.schematicEditor.graphNode.Node;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.TransformationNode;
import cz.cvut.fel.schematicEditor.support.Transformation;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;

public class PSExport implements Export {

    private final boolean monochromaticColor = false;

    PrintStream           out;
    File                  file;

    public void export(SceneGraph sg, Object output) {

        this.file = (File) output;
        FileOutputStream fos = null;
        TransformationNode tn = null;
        ParameterNode pn = null;
        ElementNode en = null;

        try {
            fos = new FileOutputStream(this.file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.out = new PrintStream(fos);

        printHead(new Point2D.Float(0, 0), new Point2D.Float(500, 500));

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

        this.out.close();
    }

    private void drawNode(ElementNode en, ParameterNode pn, TransformationNode tn) {

        switch (en.getElement().getElementType()) {

            case T_LINE:
                Line l = (Line) en.getElement();
                drawLine(new Point2D.Double(l.getX().get(0).doubleValue(), l.getY().get(0).doubleValue()),
                         new Point2D.Double(l.getX().get(1).doubleValue(), l.getY().get(1).doubleValue()), pn, tn
                                 .getTransformation());
                break;
            case T_RECTANGLE:
                Rectangle r = (Rectangle) en.getElement();
                drawRectangle(new Point2D.Double(r.getTopLeftX(), r.getTopLeftY()), r.getWidth(), r.getHeight(), pn, tn
                        .getTransformation());
                break;
            case T_ARC:
                Arc ar = (Arc) en.getElement();
                drawArc(ar, pn, tn.getTransformation());
                break;
            case T_ELLIPSE:
                Ellipse el = (Ellipse) en.getElement();
                drawEllipse(el, pn, tn.getTransformation());
                break;
            case T_TRIANGLE:
            case T_POLYGON:
                Polygon p = (Polygon) en.getElement();
                drawPoly(true, p.getX(), p.getY(), pn, tn.getTransformation());
                break;
            case T_POLYLINE:
                Polyline d = (Polyline) en.getElement();
                drawPoly(false, d.getX(), d.getY(), pn, tn.getTransformation());
                break;

            case T_BEZIER:
                BezierCurve bZ = (BezierCurve) en.getElement();
                drawBezier(bZ, pn, tn.getTransformation());
                break;

            case T_TEXT:
                Text t = (Text) en.getElement();
                drawText(t.getText(), t.getSize(), new Point2D.Double(t.getX().get(0).doubleValue(), t.getY().get(0)
                        .doubleValue()), pn, tn.getTransformation());
                break;

            default:
                break;
        }

    }

    private void printHead(Point2D.Float startBoundingBox, Point2D.Float endBoundingBox) {

        this.out.println("%!PS-Adobe-3.0 EPSF-3.0");

        this.out.println("%%BoundingBox: " + (int) startBoundingBox.getX()
                + " "
                + (int) startBoundingBox.getX()
                + " "
                + (int) endBoundingBox.getX()
                + " "
                + (int) endBoundingBox.getY());
        this.out.println("%%Title: " + this.file.getName());
        this.out.println("%%Creator: Schematic Editor");
        this.out.println("%%Pages: 1");

        this.out.println("%%EndProlog");
        this.out.println("%%Page: 1 1");

        this.out.println("1 -1 scale");
        this.out.println("0 -" + endBoundingBox.getX() + " translate");
        this.out.println("gsave");

    }

    private void printFooter() {
        this.out.println("grestore");
        this.out.println(" showpage");
        this.out.println("%%EOF");

    }

    private void drawPoly(boolean closedPath, Vector<Unit> x, Vector<Unit> y, ParameterNode pn, Transformation tn) {

        this.out.println(" gsave");

        this.printTransformString(tn);

        printStyleString(pn);

        this.out.print(" newpath");

        int size;

        if (closedPath) {
            size = x.size() - 1;
        } else {
            size = x.size();
        }

        for (int i = 0; i < size; i++) {
            if (i == 0) {
                this.out.print(" " + x.get(i).doubleValue() + " " + y.get(i).doubleValue() + " moveto");
            }
            this.out.print(" " + x.get(i).doubleValue() + " " + y.get(i).doubleValue() + " lineto");
        }

        if (closedPath) {
            this.out.println(" closepath");
        }

        printFill(pn.getColor(), pn.getFill());

        this.out.println(" grestore");
    }

    private void drawEllipse(Ellipse el, ParameterNode pn, Transformation tn) {

        float ratio = (float) el.getHeight() / (float) el.getWidth();

        this.out.println(" gsave");

        this.printTransformString(tn);
        this.out.println("1 " + "" + ratio + "" + " scale");
        printStyleString(pn);

        this.out.println("newpath");
        this.out.println((el.getX().get(0).doubleValue() + el.getWidth() / 2) + " "
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

        this.out.println(" grestore");
    }

    private void drawRectangle(Point2D.Double start, double width, double height, ParameterNode pn, Transformation tn) {
        this.out.println(" gsave");

        this.printTransformString(tn);

        printStyleString(pn);

        this.out.print(start.getX() + " " + start.getY() + " " + width + " " + height + " rectstroke");

        this.out.println(" grestore");
    }

    private void drawLine(Point2D start, Point2D finish, ParameterNode pn, Transformation tn) {

        this.out.println(" gsave");

        this.printTransformString(tn);

        printStyleString(pn);

        this.out.print(" newpath");
        this.out.print(" " + finish.getX() + " " + finish.getY() + " moveto");
        this.out.println(" " + start.getX() + " " + start.getY() + " lineto");

        printFill(pn.getColor(), pn.getFill());

        this.out.println(" grestore");
    }

    private void drawBezier(BezierCurve bC, ParameterNode pn, Transformation tn) {
        this.out.println(" gsave");

        this.printTransformString(tn);
        printStyleString(pn);

        this.out.println(bC.getX().get(0) + " " + bC.getY().get(0) + " moveto");
        this.out.println(bC.getX().get(2) + " "
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

        this.out.println(" grestore");
    }

    private void drawArc(Arc arc, ParameterNode pn, Transformation tn) {
        this.out.println(" gsave");

        this.printTransformString(tn);
        printStyleString(pn);

        this.out.println("newpath");
        this.out.println((arc.getX().get(0).doubleValue() + arc.getWidth() / 2) + " "
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
        this.out.println(" grestore");
    }

    private void drawText(String text, int size, Point2D.Double start, ParameterNode pn, Transformation tn) {

        this.out.println("gsave");

        this.printTransformString(tn);
        this.out.println(start.getX() + " " + start.getY() + " moveto");
        printStyleString(pn);

        this.out.println("-1 1 scale");
        this.out.println("/Helvetica findfont");
        this.out.println("-" + size + " scalefont setfont");
        this.out.println("(" + text + ") show");

        this.out.println("grestore");

    }

    private void printFill(Color strokeColor, Color fillColor) {

        if ((strokeColor != null) && (fillColor == null)) {
            this.out.println(" stroke");
        } else if ((strokeColor == null) && (fillColor != null)) {
            this.out.println((fillColor.getRed() / 255.0) + " "
                    + (fillColor.getGreen() / 255.0)
                    + " "
                    + (fillColor.getBlue() / 255.0)
                    + " "
                    + " setrgbcolor");
            this.out.println(" fill");
        } else if ((strokeColor != null) && (fillColor != null)) {
            this.out.println("gsave");

            this.out.println((fillColor.getRed() / 255.0) + " "
                    + (fillColor.getGreen() / 255.0)
                    + " "
                    + (fillColor.getBlue() / 255.0)
                    + " "
                    + " setrgbcolor");
            this.out.println("fill");

            this.out.println("grestore");

            this.out.println("stroke");
        }

    }

    private void printTransformString(Transformation t) {

        if ((t == null) || t.isIdentity()) {
            return;
        }

        this.out.println("[" + t.toString() + "] concat");

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
            this.out.println((col.getRed() / 255.0) + " "
                    + (col.getGreen() / 255.0)
                    + " "
                    + (col.getBlue() / 255.0)
                    + " "
                    + " setrgbcolor");
        }

        // stroking width
        int width = pn.getWidth().intValue();
        if (width != 1) {
            this.out.println(width + " setlinewidth");
        }

    }

}
