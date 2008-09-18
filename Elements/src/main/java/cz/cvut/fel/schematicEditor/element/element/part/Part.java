package cz.cvut.fel.schematicEditor.element.element.part;

import java.awt.geom.Rectangle2D;
import java.util.Vector;

import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
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
    private Vector<UnitPoint> connectorVector;
    /**
     * String containing part description.
     */
    private String            partDescription;
    /**
     * String containing part variant.
     */
    private String            partVariant;

    /**
     * Default constructor. It initializes {@link Part} element.
     */
    public Part() {
        super();

        setConnectorVector(new Vector<UnitPoint>());
        setPartDescription("");
        setPartVariant("");
    }

    public UnitRectangle getBounds() {
        // part as such has no bounds
        return null;
    }

    /**
     * @see element.Element#isHit(java.awt.geom.cz.cvut.fel.schematicEditor.types .Point2D.Double)
     */
    @Override
    public boolean isHit(Rectangle2D.Double rectangle) {
        // part cannot be hit
        return false;
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
     * @see cz.cvut.fel.schematicEditor.element.Element#newInstance()
     */
    @Override
    public Element duplicate() {
        Part p = new Part();
        return p;
    }

    /**
     * @param connectorVector the connectorVector to set
     */
    private void setConnectorVector(Vector<UnitPoint> connectorVector) {
        this.connectorVector = connectorVector;
    }

    /**
     * @return the connectorVector
     */
    public Vector<UnitPoint> getConnectorVector() {
        return connectorVector;
    }

    /**
     * @param partDescription the partDescription to set
     */
    private void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    /**
     * @return the partDescription
     */
    public String getPartDescription() {
        return partDescription;
    }

    /**
     * @param partVariant the partVariant to set
     */
    private void setPartVariant(String partVariant) {
        this.partVariant = partVariant;
    }

    /**
     * @return the partVariant
     */
    public String getPartVariant() {
        return partVariant;
    }
}
