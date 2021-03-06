package cz.cvut.fel.schematicEditor.element.element.shape;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

public class Text extends Shape {

    private String value = "";
    private Color  color = null;

    /**
     * @see cz.cvut.fel.schematicEditor.element.element.Element#getBounds(java.awt.Graphics2D)
     */
    @Override
    public UnitRectangle getBounds(Graphics2D g2d) {
        FontMetrics metrics = g2d.getFontMetrics();
        Rectangle2D r2d = metrics.getStringBounds(getValue(), g2d);

        // get x
        double x = getX().firstElement().doubleValue();
        // get y (it is on the baseline, it is necessary to put into the top left corner)
        double y = getY().firstElement().doubleValue() - r2d.getHeight() - metrics.getAscent();

        return new UnitRectangle(x - 1, y, r2d.getWidth() + 2, r2d.getHeight()
                                                               + metrics.getAscent()
                                                               + metrics.getDescent() + 2);
    }

    public FontMetrics getFontMetrics(Graphics2D g2d) {
        return g2d.getFontMetrics();
    }

    public Text() {
        this(new UnitPoint(0, 0), "Mqtlá∑†¥¥¨˙©ƒ∂ßååœ∑™¡£¢∞");
        // this(new UnitPoint(0, 0), "Mqtlá");
    }

    public Text(UnitPoint start, String value) {
        super();

        // TODO remove, obsolete
        getX().add(start.getUnitX());
        getY().add(start.getUnitY());

        setValue(value);
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        if (this.value == null) {
            return "";
        }
        return this.value;
    }

    /**
     * @see Element#isHit(Rectangle2D, Graphics2D)
     */
    @Override
    public boolean isHit(Rectangle2D rectangle, Graphics2D g2d) {
        if (getBounds(g2d).contains(rectangle)) {
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see cz.cvut.fel.schematicEditor.element.Element#getElementType()
     */
    @Override
    public ElementType getElementType() {
        return ElementType.T_TEXT;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.element.Element#getNumberOfCoordinates()
     */
    @Override
    public int getNumberOfCoordinates() {
        return 2;
    }

    /*
     * (non-Javadoc)
     * @see cz.cvut.fel.schematicEditor.element.Element#newInstance()
     */
    @Override
    public Element duplicate() {
        Text t = new Text(new UnitPoint(getX().firstElement(), getY().firstElement()), getValue());

        t.duplicateCoordinates(this);

        return t;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "TEXT";
    }

    /**
     * @param color
     *            the color to set
     */
    public void setColor(Color color) {
        if (color == null) {
            this.color = Color.BLACK;
        } else {
            this.color = color;
        }
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return this.color;
    }
}
