package cz.cvut.fel.schematicEditor.element;

/**
 * @author uk
 */
public class ElementType {
    // TODO change ElementType into enum
    /**
     * Identifier for line element type.
     */
    public static final int T_LINE        = 0;
    /**
     * Identifier for ellipse element type.
     */
    public static final int T_ELLIPSE     = T_LINE + 1;
    /**
     * Identifier for rectangular element type.
     */
    public static final int T_RECTANGLE   = T_ELLIPSE + 1;
    /**
     * Identifier for polyline element type.
     */
    public static final int T_POLYLINE    = T_RECTANGLE + 1;
    /**
     * Identifier for triangle element type.
     */
    public static final int T_TRIANGLE    = T_POLYLINE + 1;
    /**
     * Identifier for polygon element type.
     */
    public static final int T_POLYGON     = T_TRIANGLE + 1;
    /**
     * Identifier for arc element type.
     */
    public static final int T_ARC         = T_POLYGON + 1;
    /**
     * Identifier for bezier curve element type.
     */
    public static final int T_BEZIER      = T_ARC + 1;
    /**
     * Identifier for text element type.
     */
    public static final int T_TEXT        = T_BEZIER + 1;
    /**
     * Identifier for part element type.
     */
    public static final int T_PART        = T_TEXT + 1;
    /**
     * Identifier for wire element type.
     */
    public static final int T_WIRE        = T_PART + 1;
    /**
     * Identifier for arc segment element type.
     */
    public static final int T_ARC_SEGMENT = T_WIRE + 1;
}
