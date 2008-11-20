package cz.cvut.fel.schematicEditor.support;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

/**
 * This class represents transformation.
 *
 * @author uk
 */
public class Transformation {
    /**
     * Transformation matrix.
     */
    private double[][] matrix;

    /**
     * This is default constructor.
     */
    public Transformation() {
        this.matrix = new double[3][3];
    }

    /**
     * This is constructor.
     *
     * @param t transformation matrix.
     */
    public Transformation(Transformation t) {
        this.matrix = t.matrix;
    }

    /**
     * This method generates identity.
     *
     * @return Identity transformation.
     */
    public static Transformation getIdentity() {
        Transformation t = new Transformation();
        for (int i = 0; i < 3; i++) {
            t.matrix[i][i] = 1;
        }
        return t;
    }

    /**
     * This method generates rotation.
     *
     * @param angle angle of rotation in degrees.
     * @return Rotation transformation.
     */
    public static Transformation getRotation(final double angle) {
        // convert given angle from degrees to radians
        double a = angle / 180 * Math.PI;

        Transformation t = new Transformation();
        t.matrix[0][0] = Math.cos(a);
        t.matrix[0][1] = -Math.sin(a);
        t.matrix[1][0] = Math.sin(a);
        t.matrix[1][1] = Math.cos(a);
        t.matrix[2][2] = 1;
        return t;
    }

    /**
     * This method generates shift.
     *
     * @param x x shift.
     * @param y y shift.
     * @return Shift transformation.
     */
    public static Transformation getShift(double x, double y) {
        Transformation t = new Transformation();
        t.matrix[0][0] = 1;
        t.matrix[1][1] = 1;
        t.matrix[2][2] = 1;
        t.matrix[0][2] = x;
        t.matrix[1][2] = y;
        return t;
    }

    /**
     * This method generates scale.
     *
     * @param x x scale.
     * @param y y scale.
     * @return Scale transformation.
     */
    public static Transformation getScale(double x, double y) {
        Transformation t = new Transformation();
        t.matrix[0][0] = x;
        t.matrix[1][1] = y;
        t.matrix[2][2] = 1;
        return t;
    }

    public Point2D.Double shift(Point2D.Double point) {
        Point2D.Double result = null;

        result = multiply(this, point);

        return result;
    }

    public Rectangle2D.Double shift(Rectangle2D rectangle) {
        Rectangle2D.Double result = null;
        Point2D.Double p = new Point2D.Double(rectangle.getX(), rectangle.getY());

        p = multiply(this, p);
        result = new Rectangle2D.Double(p.getX(), p.getY(), rectangle.getWidth(), rectangle.getHeight());

        return result;
    }

    public Rectangle2D.Double shiftInverse(Rectangle2D rectangle) {
        Rectangle2D.Double result = null;
        Point2D.Double p = new Point2D.Double(rectangle.getX(), rectangle.getY());

        p = multiply(this.getInverse(), p);
        result = new Rectangle2D.Double(p.getX(), p.getY(), rectangle.getWidth(), rectangle.getHeight());

        return result;
    }

    /**
     * Computes inverse transformation to current transformation. It uses following formula:
     *
     * <pre>
     * | a11 a12 a13 |-1             |   a33a22-a32a23  -(a33a12-a32a13)   a23a12-a22a13  |
     * | a21 a22 a23 |    =  1/DET * | -(a33a21-a31a23)   a33a11-a31a13  -(a23a11-a21a13) |
     * | a31 a32 a33 |               |   a32a21-a31a22  -(a32a11-a31a12)   a22a11-a21a12  |
     * with DET  =  a11(a33a22-a32a23)-a21(a33a12-a32a13)+a31(a23a12-a22a13)
     * </pre>
     *
     * @return
     */
    public Transformation getInverse() {
        Transformation result = new Transformation();
        double[][] r = new double[3][3];
        double[][] t = getMatrix();

        r[0][0] = t[2][2] * t[1][1] - t[2][1] * t[1][2];
        r[0][1] = -(t[2][2] * t[0][1] - t[2][1] * t[0][2]);
        r[0][2] = t[1][2] * t[0][1] - t[1][1] * t[0][2];

        r[1][0] = -(t[2][2] * t[1][0] - t[2][0] * t[1][2]);
        r[1][1] = t[2][2] * t[0][0] - t[2][0] * t[0][2];
        r[1][2] = -(t[1][2] * t[0][0] - t[1][0] * t[0][2]);

        r[2][0] = t[2][1] * t[1][0] - t[2][0] * t[1][1];
        r[2][1] = -(t[2][1] * t[0][0] - t[2][0] * t[0][1]);
        r[2][2] = t[1][1] * t[0][0] - t[1][0] * t[0][1];

        result.setMatrix(r);
        result = Transformation.multiply(result, 1 / determinant(this));

        return result;
    }

    public Point2D.Double invert(Point2D.Double point) {
        Point2D.Double result = null;

        // result = multiply(, point)

        return null;
    }

    /**
     * Computes determinant of given {@link Transformation} matrix using formula:
     *
     * <pre>
     * DET = a11(a33a22 - a32a23) - a21(a33a12 - a32a13) + a31(a23a12 - a22a13)
     * </pre>
     *
     * @param t Transformation, to which determinant is needed.
     * @return Value of determinant of given {@link Transformation}.
     */
    private double determinant(Transformation t) {
        double result = 0;
        double[][] m = t.getMatrix();

        result = m[0][0] * (m[2][2] * m[1][1] - m[2][1] * m[1][2]);
        result -= m[1][0] * (m[2][2] * m[0][1] - m[2][1] * m[0][2]);
        result += m[2][0] * (m[1][2] * m[0][1] - m[1][1] * m[0][2]);

        return result;
    }

    @Override
    public String toString() {
        return print();

    }

    private String print() {
        return String.valueOf("[ " + this.matrix[0][0]) + ", "
                + String.valueOf(this.matrix[0][1])
                + ", "
                + String.valueOf(this.matrix[0][2])
                + " ] [ "
                + String.valueOf(this.matrix[1][0])
                + ", "
                + String.valueOf(this.matrix[1][1])
                + ", "
                + String.valueOf(this.matrix[1][2])
                + " ]";
    }

    public boolean isIdentity() {

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if ((i == j) && (this.matrix[i][j] == 1)) {
                    continue;
                } else if ((i != j) && (this.matrix[i][j] == 0)) {
                    continue;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method returns transformation matrix.
     *
     * @return Transformation matrix.
     */
    public double[][] getTransformationMatrix() {
        return this.matrix;
    }

    /**
     * This method multiplies transformations.
     *
     * @param a 1st transformation.
     * @param b 2nd transformation.
     * @return Multiplied tranformation.
     */
    public static Transformation multiply(Transformation a, Transformation b) {
        Transformation t = new Transformation();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    t.matrix[i][j] += a.matrix[i][k] * b.matrix[k][j];
                }
            }
        }
        return t;
    }

    /**
     * This method multiplies transformations.
     *
     * @param t {@link Transformation}.
     * @param p {@link UnitPoint}.
     * @return Multiplied transformation.
     */
    public static UnitPoint multiply(Transformation t, UnitPoint p) {
        UnitPoint result = new UnitPoint();

        result.setX(t.matrix[0][0] * p.getX() + t.matrix[0][1] * p.getY() + t.matrix[0][2]);
        result.setY(t.matrix[1][0] * p.getX() + t.matrix[1][1] * p.getY() + t.matrix[1][2]);

        return result;
    }

    /**
     * This method multiplies transformations.
     *
     * @param t {@link Transformation}.
     * @param p {@link Point2D.Double}.
     * @return Multiplied transformation.
     */
    public static Point2D.Double multiply(Transformation t, Point2D.Double p) {
        Point2D.Double result = new Point2D.Double();

        result.x = t.matrix[0][0] * p.getX() + t.matrix[0][1] * p.getY() + t.matrix[0][2];
        result.y = t.matrix[1][0] * p.getX() + t.matrix[1][1] * p.getY() + t.matrix[1][2];

        return result;
    }

    /**
     * This method multiplies transformations.
     *
     * @param t {@link Transformation}.
     * @param d {@link Double}.
     * @return Multiplied transformation.
     */
    public static Transformation multiply(Transformation t, Double d) {
        Transformation result = new Transformation();
        double[][] m = t.getMatrix();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                m[i][j] *= d;
            }
        }

        result.setMatrix(m);
        return result;
    }

    public void printMatrix() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(this.matrix[i][j] + " ");

            }
            System.out.println();
        }
    }

    private double[][] getMatrix() {
        return this.matrix;
    }

    private void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }
}
