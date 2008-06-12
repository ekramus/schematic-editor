package cz.cvut.fel.schematicEditor.element.shape;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Font;

import cz.cvut.fel.schematicEditor.types.ElementType;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

public class Text extends Shape {

    public static final int PLAIN  = Font.PLAIN;
    public static final int BOLD   = Font.BOLD;
    public static final int ITALIC = Font.ITALIC;

    String                  textValue;
    String                  fontName;
    int                     size;
    int                     style;

    @Override
    public UnitRectangle getBounds() {
        return new UnitRectangle(0, 0, 1, 1);
    }

    public Text(UnitPoint start, String text, int size) {
        super();

        this.x.add(start.getUnitX());
        this.y.add(start.getUnitY());

        setText(text);
        setSize(size);
        setStyle(Text.PLAIN);
        setFontName("Helvetica");
    }

    public Text(UnitPoint start, String text, int size, int style, String fontName) {
        super();

        this.x.add(start.getUnitX());
        this.y.add(start.getUnitY());

        setText(text);
        setSize(size);
        setFontName(fontName);
        setStyle(style);
    }

    public void setText(String textValue) {
        this.textValue = textValue;
    }

    public String getText() {
        return this.textValue;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getFontName() {
        return this.fontName;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getStyle() {
        return this.style;
    }

    /*
     * (non-Javadoc)
     * 
     * @see element.Element#isHit(java.awt.geom.cz.cvut.fel.schematicEditor.types.Point2D.Double)
     */
    @Override
    public boolean isHit(Rectangle2D.Double rectangle) {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.element.Element#getElementType()
     */
    @Override
    public int getElementType() {
        return ElementType.T_TEXT;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.Element#getNumberOfCoordinates()
     */
    @Override
    public int getNumberOfCoordinates() {
        return 2;
    }
}
