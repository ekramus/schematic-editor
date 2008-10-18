package cz.cvut.fel.schematicEditor.element.properties;

import cz.cvut.fel.schematicEditor.element.element.part.Part;

/**
 * This class encapsulates properties specific for any {@link Part}.
 *
 * @author Urban Kravjansky
 */
public class PartProperties {
    /**
     * String containing part description.
     */
    private String partDescription;
    /**
     * String containing part variant.
     */
    private String partVariant;

    /**
     *
     */
    public PartProperties() {
        setPartDescription("");
        setPartVariant("");
    }

    /**
     * @return the partDescription
     */
    public String getPartDescription() {
        return this.partDescription;
    }

    /**
     * @return the partVariant
     */
    public String getPartVariant() {
        return this.partVariant;
    }

    /**
     * @param partDescription the partDescription to set
     */
    private void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    /**
     * @param partVariant the partVariant to set
     */
    private void setPartVariant(String partVariant) {
        this.partVariant = partVariant;
    }
}
