/**
 *
 */
package cz.cvut.fel.schematicEditor.element.properties;

import java.awt.Color;
import java.awt.Font;

import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;

/**
 * @author uk
 */
public class ElementProperties {
    /**
     * Width of contour line.
     */
    private Unit         contourLineWidth;
    /**
     * Line style of contour.
     */
    private ElementStyle contourStyle;
    /**
     * Color of contour.
     */
    private Color        contourColor;
    /**
     * Style of fill.
     */
    private ElementStyle fillStyle;
    /**
     * Color of fill.
     */
    private Color        fillColor;
    /**
     * Font used for text displaying.
     */
    private Font         font = null;

    /**
     *
     */
    public ElementProperties() {
        setContourStyle(ElementStyle.NORMAL);
        setContourLineWidth(new Pixel(1));
        setContourColor(Color.BLACK);
        setFillStyle(ElementStyle.NORMAL);
        setFillColor(Color.WHITE);
        setFont(new Font("Monospaced", Font.PLAIN, 13));
    }

    /**
     * @return the contourLineWidth
     */
    public final Unit getContourLineWidth() {
        return this.contourLineWidth;
    }

    /**
     * @param contourLineWidth the contourLineWidth to set
     */
    public final void setContourLineWidth(Unit contourLineWidth) {
        this.contourLineWidth = contourLineWidth;
    }

    /**
     * @return the contourColor
     */
    public final Color getContourColor() {
        return this.contourColor;
    }

    /**
     * @param contourColor the contourColor to set
     */
    public final void setContourColor(Color contourColor) {
        this.contourColor = contourColor;
    }

    /**
     * @return the fillColor
     */
    public final Color getFillColor() {
        return this.fillColor;
    }

    /**
     * @param fillColor the fillColor to set
     */
    public final void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * @return the contourColorAlpha
     */
    public int getContourColorAlpha() {
        return this.contourColor.getAlpha();
    }

    /**
     * @return the fillColorAlpha
     */
    public int getFillColorAlpha() {
        return this.fillColor.getAlpha();
    }

    /**
     * @param contourColorAlpha the contourColorAlpha to set
     */
    public void setContourColorAlpha(int contourColorAlpha) {
        this.contourColor = new Color(this.contourColor.getRed(), this.contourColor.getGreen(), this.contourColor
                .getBlue(), contourColorAlpha);
    }

    /**
     * @param fillColorAlpha the fillColorAlpha to set
     */
    public void setFillColorAlpha(int fillColorAlpha) {
        this.fillColor = new Color(this.fillColor.getRed(), this.fillColor.getGreen(), this.fillColor.getBlue(),
                fillColorAlpha);
    }

    public void setContourStyle(ElementStyle contourStyle) {
        this.contourStyle = contourStyle;
    }

    public ElementStyle getContourStyle() {
        return this.contourStyle;
    }

    public ElementStyle getFillStyle() {
        return this.fillStyle;
    }

    public void setFillStyle(ElementStyle fillStyle) {
        this.fillStyle = fillStyle;
    }

    /**
     * @param font the font to set
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * @return the font
     */
    public Font getFont() {
        return this.font;
    }
}
