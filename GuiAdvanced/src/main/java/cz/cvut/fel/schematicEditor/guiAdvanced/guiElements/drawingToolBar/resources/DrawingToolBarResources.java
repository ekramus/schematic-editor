package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.drawingToolBar.resources;

/**
 * This enumerator implements drawing tool bar resources.
 *
 * TODO Implement resources as serialized XML objects, so they can be modified.
 *
 * @author Urban Kravjansky
 */
@SuppressWarnings("nls")
public enum DrawingToolBarResources {
    /**
     * Arc button caption.
     */
    ARC_BUTTON("Arc", "resources/line_32.png"),
    /**
     * Arc segment button caption.
     */
    ARC_SEGMENT_BUTTON("Arc segment", "resources/line_32.png"),
    /**
     * Text button caption.
     */
    TEXT_BUTTON("Text", "resources/line_32.png"),
    /**
     * Bezier curve button caption.
     */
    BEZIER_CURVE_BUTTON("Bezier", "resources/line_32.png"),
    /**
     * Line button caption.
     */
    LINE_BUTTON("Line", "resources/line_32.png"),
    /**
     * Polygon button caption.
     */
    POLYGON_BUTTON("Polygon", "resources/line_32.png"),
    /**
     * Polyline button caption.
     */
    POLYLINE_BUTTON("Polyline", "resources/line_32.png"),
    /**
     * Rectangle button caption.
     */
    RECTANGLE_BUTTON("Rectangle", "resources/line_32.png"),
    /**
     * Select button caption.
     */
    SELECT_BUTTON("Select", "resources/line_32.png"),
    /**
     * Delete button caption.
     */
    DELETE_BUTTON("Delete", "resources/line_32.png"),
    /**
     * Pin button caption.
     */
    PIN_BUTTON("Pin", "resources/line_32.png"),
    /**
     * Junction button caption.
     */
    JUNCTION_BUTTON("Junction", "resources/line_32.png"),
    /**
     * Wire button caption.
     */
    WIRE_BUTTON("Wire", "resources/line_32.png"),
    /**
     * Ellipse button caption.
     */
    ELLIPSE_BUTTON("Ellipse", "resources/ellipse_32.png");

    /**
     * String value of item.
     */
    private String text;
    /**
     * Resource path string.
     */
    private String resource;

    /**
     * Constructor sets string value of enum item.
     *
     * @param text String value of item.
     */
    private DrawingToolBarResources(String text) {
        setText(text);
    }

    /**
     * Constructor sets string value and resource path of enum item.
     *
     * @param text String value of item.
     * @param resource Path to resource.
     */
    private DrawingToolBarResources(String text, String resource) {
        setText(text);
        setResource(resource);
    }

    /**
     * Getter for string value of item.
     *
     * @return String value of item.
     */
    public String getText() {
        return this.text;
    }

    /**
     * Setter of string value of item.
     *
     * @param text String value of item to set.
     */
    private void setText(String text) {
        this.text = text;
    }

    /**
     * Getter of resource value.
     *
     * @return the resource
     */
    public String getResource() {
        return this.resource;
    }

    /**
     * Setter of resource value.
     *
     * @param resource the resource to set
     */
    public void setResource(String resource) {
        this.resource = resource;
    }
}
