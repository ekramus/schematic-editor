package cz.cvut.fel.schematicEditor.graphNode;

import java.awt.Color;

import cz.cvut.fel.schematicEditor.element.properties.ElementProperties;
import cz.cvut.fel.schematicEditor.element.properties.ElementStyle;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;

/**
 * This class represents ParameterNode.
 * 
 * @author uk
 */
public class ParameterNode extends Node {
    private ElementStyle lineStyle;
    private ElementStyle fillStyle;

    /**
     * This field represents color.
     */
    private Color        color;

    /**
     * This field represents the fill of node.
     */
    private Color        fill;

    /**
     * This field represents the width of stroke.
     */
    private Unit         width;

    /**
     * @return the color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * This is default constructor.
     */
    public ParameterNode() {
        this.setColor(Color.BLACK);
        this.setWidth(new Pixel(1));
        this.setFill(null);
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @param fill the color of fill to set
     */
    public void setFill(Color fill) {
        this.fill = fill;
    }

    /**
     * @return the color of fill
     */
    public Color getFill() {
        return this.fill;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(Unit width) {
        this.width = width;
    }

    /**
     * @return the width
     */
    public Unit getWidth() {
        return this.width;
    }

    /**
     * This method combines parametres from this node with given ones.
     * 
     * @param p parametres to combine.
     * @return Combined parametres.
     */
    public ParameterNode combine(ParameterNode p) {
        ParameterNode result = new ParameterNode();

        if (this.color == null) {
            result.setColor(p.getColor());
        }

        if (this.fill == null) {
            result.setFill(p.getFill());
        }

        return result;
    }

    /**
     * Set properties of {@link ParameterNode} usint {@link ElementProperties}.
     * 
     * @param elementProperties instance of properties.
     */
    public final void setProperties(ElementProperties elementProperties) {
        Color c = elementProperties.getContourColor();
        Color color = new Color(c.getRed(), c.getGreen(), c.getBlue(), elementProperties.getContourColorAlpha());

        c = elementProperties.getFillColor();
        Color fill = new Color(c.getRed(), c.getGreen(), c.getBlue(), elementProperties.getFillColorAlpha());

        setLineStyle(elementProperties.getContourStyle());
        setColor(color);
        setFillStyle(elementProperties.getFillStyle());
        setFill(fill);
        setWidth(elementProperties.getContourLineWidth());
    }

    /**
     * This method returns {@link ElementProperties} instance.
     * 
     * @return {@link ElementProperties} instance representing properties of this {@link ParameterNode}.
     */
    public final ElementProperties getProperties() {
        ElementProperties result = new ElementProperties();

        result.setContourStyle(getLineStyle());
        result.setContourColor(new Color(getColor().getRGB()));
        result.setContourColorAlpha(getColor().getAlpha());
        result.setFillStyle(getFillStyle());
        result.setFillColor(new Color(getFill().getRGB()));
        result.setFillColorAlpha(getFill().getAlpha());
        result.setContourLineWidth(getWidth());

        return result;
    }

    /**
     * @see graphNode.Node#toString()
     */
    @Override
    public String toString() {
        return id + " [ParameterNode]";
    }

    public ElementStyle getFillStyle() {
        return fillStyle;
    }

    public ElementStyle getLineStyle() {
        return lineStyle;
    }

    public void setFillStyle(ElementStyle fillStyle) {
        this.fillStyle = fillStyle;
    }

    public void setLineStyle(ElementStyle lineStyle) {
        this.lineStyle = lineStyle;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.graphNode.Node#duplicate()
     */
    @Override
    public Node duplicate() {
        ParameterNode result = new ParameterNode();

        result.setProperties(getProperties());

        return result;
    }
}
