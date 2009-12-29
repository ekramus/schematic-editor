package cz.cvut.fel.schematicEditor.element.element.shape;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

public class Text extends Shape {

    private String value = null;
    private Font   font  = null;
    private Color  color = null;

    /**
     * @see cz.cvut.fel.schematicEditor.element.element.Element#getBounds(java.awt.Graphics2D)
     */
    @Override
    public UnitRectangle getBounds(Graphics2D g2d) {
        FontMetrics metrics = g2d.getFontMetrics();
        Rectangle2D r2d = metrics.getStringBounds(getValue(), g2d);

        return new UnitRectangle(0, 0, r2d.getWidth(), r2d.getHeight());
    }

    public FontMetrics getFontMetrics(Graphics2D g2d) {
        return g2d.getFontMetrics();
    }

    public Text() {
        this(new UnitPoint(0, 0), "asd");
    }

    public Text(UnitPoint start, String value) {
        super();

        getX().add(start.getUnitX());
        getY().add(start.getUnitY());

        setValue(value);
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    /**
     * @param font the font to set
     */
    public void setFont(Font font) {
        if (font == null) {
            this.font = new Font("Monospaced", Font.PLAIN, 13);
        } else {
            this.font = font;
        }
    }

    /**
     * @return the font
     */
    public Font getFont() {
        return this.font;
    }

    /**
     * @see element.Element#isHit(Rectangle2D.Double, double)
     */
    @Override
    public boolean isHit(Rectangle2D rectangle) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     *
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
     *
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
     * @param color the color to set
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
