/**
 *
 */
package cz.cvut.fel.schematicEditor.core.coreStructures;

import java.awt.Color;

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
     * Alpha level of contour color.
     */
    private int          contourColorAlpha;
    /**
     * Style of fill.
     */
    private ElementStyle fillStyle;
    /**
     * Color of fill.
     */
    private Color        fillColor;
    /**
     * Alpha level of fill color.
     */
    private int          fillColorAlpha;

    /**
     *
     */
    public ElementProperties() {
        setContourStyle(ElementStyle.NORMAL);
        setContourLineWidth(new Pixel(1));
        setContourColor(Color.BLACK);
        setContourColorAlpha(255);
        setFillStyle(ElementStyle.NORMAL);
        setFillColor(Color.WHITE);
        setFillColorAlpha(255);
    }

    /**
     * @return the contourLineWidth
     */
    public final Unit getContourLineWidth() {
        return this.contourLineWidth;
    }

    /**
     * @param contourLineWidth
     *            the contourLineWidth to set
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
     * @param contourColor
     *            the contourColor to set
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
     * @param fillColor
     *            the fillColor to set
     */
    public final void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * @return the contourColorAlpha
     */
    public int getContourColorAlpha() {
        return this.contourColorAlpha;
    }

    /**
     * @return the fillColorAlpha
     */
    public int getFillColorAlpha() {
        return this.fillColorAlpha;
    }

    /**
     * @param contourColorAlpha
     *            the contourColorAlpha to set
     */
    public void setContourColorAlpha(int contourColorAlpha) {
        this.contourColorAlpha = contourColorAlpha;
    }

    /**
     * @param fillColorAlpha
     *            the fillColorAlpha to set
     */
    public void setFillColorAlpha(int fillColorAlpha) {
        this.fillColorAlpha = fillColorAlpha;
    }

    public void setContourStyle(ElementStyle contourStyle) {
        this.contourStyle = contourStyle;
    }

    public ElementStyle getContourStyle() {
        return contourStyle;
    }

    public ElementStyle getFillStyle() {
        return fillStyle;
    }

    public void setFillStyle(ElementStyle fillStyle) {
        this.fillStyle = fillStyle;
    }
}
