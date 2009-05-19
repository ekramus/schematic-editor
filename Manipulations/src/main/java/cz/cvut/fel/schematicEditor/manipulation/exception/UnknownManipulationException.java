package cz.cvut.fel.schematicEditor.manipulation.exception;

import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;

/**
 * This class implements unknown manipulation exception. It is raised, when unknown manipulation is being used.
 * 
 * @author Urban Kravjansky
 */
public class UnknownManipulationException extends Exception {
    /**
     * Type of manipulation causing exception.
     */
    private ManipulationType manipulationType = null;

    /**
     * Constructor used to create exception.
     * 
     * @param manipulationType name of manipulation causing exception.
     */
    public UnknownManipulationException(ManipulationType manipulationType) {
        setManipulationType(manipulationType);
    }

    /**
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage() {
        return "Unknown manipulation: " + getManipulationType();
    }

    /**
     * @return the manipulationType
     */
    private ManipulationType getManipulationType() {
        return this.manipulationType;
    }

    /**
     * @param manipulationType the manipulationType to set
     */
    private void setManipulationType(ManipulationType manipulationType) {
        this.manipulationType = manipulationType;
    }
}
