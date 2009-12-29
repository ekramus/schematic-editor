package cz.cvut.fel.schematicEditor.element.element.part;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.parts.PartProperties;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

/**
 * This class encapsulates electronic part informations. Shape of part is represented as <code>GroupNode</code>.
 *
 * @author Urban Kravjansky
 *
 */
public class Part extends Element {
    /**
     * Vector of connectors.
     */
    @Deprecated
    private Vector<UnitPoint> connectorVector;
    /**
     * Field containing part properties.
     */
    private PartProperties    partProperties;
    /**
     * Field containing part rotation center.
     */
    private UnitPoint         rotationCenter;

    /**
     * Default constructor. It initializes {@link Part} element.
     *
     * @param partProperties part properties containing part variant and part description.
     */
    public Part(PartProperties partProperties) {
        super();

        setConnectorVector(new Vector<UnitPoint>());
        setPartProperties(partProperties);

        // rotation center will be obtained either dynamically or, after setting, from rotationCenter variable
        setRotationCenter(null);
    }

    /**
     * @see Element#duplicate()
     */
    @Override
    public Element duplicate() {
        Part p = new Part(getPartProperties().duplicate());
        return p;
    }

    /**
     * @see Element#getBounds()
     */
    @Override
    public UnitRectangle getBounds(Graphics2D g2d) {
        // part as such has no bounds
        return null;
    }

    /**
     * @return the connectorVector
     */
    @Deprecated
    public Vector<UnitPoint> getConnectorVector() {
        return this.connectorVector;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.element.Element#getElementType()
     */
    @Override
    public ElementType getElementType() {
        return ElementType.T_PART;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.element.Element#getNumberOfCoordinates()
     */
    @Override
    public int getNumberOfCoordinates() {
        // part has zero coordinates
        return Element.ZERO_COORDINATES;
    }

    /**
     * @return the partProperties
     */
    public PartProperties getPartProperties() {
        return this.partProperties;
    }

    /**
     * @see Element#isHit(java.awt.geom.Rectangle2D)
     */
    @Override
    public boolean isHit(Rectangle2D rectangle) {
        // part cannot be hit
        return false;
    }

    /**
     * @param partProperties the partProperties to set
     */
    public void setPartProperties(PartProperties partProperties) {
        this.partProperties = partProperties;
    }

    /**
     * @param connectorVector the connectorVector to set
     */
    @Deprecated
    private void setConnectorVector(Vector<UnitPoint> connectorVector) {
        this.connectorVector = connectorVector;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.element.Element#getRotationCenter()
     */
    @Override
    public UnitPoint getRotationCenter() {
        return this.rotationCenter;
    }

    /**
     * @param rotationCenter the rotationCenter to set
     */
    public void setRotationCenter(UnitPoint rotationCenter) {
        this.rotationCenter = rotationCenter;
    }
}
