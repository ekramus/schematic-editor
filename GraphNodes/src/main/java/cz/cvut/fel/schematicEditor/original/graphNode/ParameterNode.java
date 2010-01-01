package cz.cvut.fel.schematicEditor.original.graphNode;

import java.awt.Color;
import java.awt.Font;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cz.cvut.fel.schematicEditor.element.properties.ElementProperties;
import cz.cvut.fel.schematicEditor.element.properties.ElementStyle;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;

/**
 * This class represents ParameterNode.
 *
 * @author uk
 */
@XStreamAlias("ParameterNode")
public class ParameterNode extends Node {
    /**
     * Singleton {@link ElementProperties} instance.
     */
    private ElementProperties properties = null;

    /**
     * Private getter for element properties instance.
     *
     * @return initialized element properties.
     */
    public ElementProperties getProperties() {
        if (this.properties == null) {
            this.properties = new ElementProperties();
        }
        return this.properties;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return getProperties().getContourColor();
    }

    /**
     * This is default constructor.
     */
    protected ParameterNode() {
        getProperties();
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        getProperties().setContourColor(color);
    }

    /**
     * @param fill the color of fill to set
     */
    public void setFill(Color fill) {
        getProperties().setFillColor(fill);
    }

    /**
     * @return the color of fill
     */
    public Color getFill() {
        return getProperties().getFillColor();
    }

    /**
     * @param width the width to set
     */
    public void setWidth(Unit width) {
        getProperties().setContourLineWidth(width);
    }

    /**
     * @return the width
     */
    public Unit getWidth() {
        return getProperties().getContourLineWidth();
    }

    /**
     * This method combines parametres from this node with given ones.
     *
     * @param p parametres to combine.
     * @return Combined parametres.
     */
    public ParameterNode combine(ParameterNode p) {
        // TODO finish implementation
        ParameterNode result = new ParameterNode();

        if (getColor() == null) {
            result.setColor(p.getColor());
        } else {
            result.setColor(getColor());
        }

        if (getFill() == null) {
            result.setFill(p.getFill());
        } else {
            result.setFill(getFill());
        }

        // TODO make proper combining
        result.setWidth(p.getWidth());
        result.setFillStyle(p.getFillStyle());
        result.setLineStyle(p.getLineStyle());
        result.setFont(p.getFont());

        result.setDisabled(p.isDisabled());

        return result;
    }

    /**
     * @see graphNode.Node#toString()
     */
    @Override
    public String toString() {
        return this.id + " [ParameterNode]";
    }

    public ElementStyle getFillStyle() {
        return getProperties().getFillStyle();
    }

    public ElementStyle getLineStyle() {
        return getProperties().getContourStyle();
    }

    public Font getFont() {
        return getProperties().getFont();
    }

    public void setFont(Font font) {
        getProperties().setFont(font);
    }

    public void setFillStyle(ElementStyle fillStyle) {
        getProperties().setFillStyle(fillStyle);
    }

    public void setLineStyle(ElementStyle lineStyle) {
        getProperties().setContourStyle(lineStyle);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.original.graphNode.Node#duplicate()
     */
    @Override
    protected Node duplicate() {
        ParameterNode result = new ParameterNode();

        result.setProperties(getProperties().duplicate());

        return result;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(ElementProperties properties) {
        this.properties = properties;
    }
}
