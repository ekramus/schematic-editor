package cz.cvut.fel.schematicEditor.manipulation;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;

/**
 * This class implements factory pattern for {@link Manipulation} creation.
 * 
 * @author Urban Kravjansky
 */
public class ManipulationFactory {
    /**
     * Default constructor, for future implementation purposes only. It is private to force static use only.
     */
    private ManipulationFactory() {
        // nothing to do
    }

    /**
     * Creates {@link Manipulation} according to given {@link ManipulationType}.
     * 
     * @param type type of manipulation.
     * @return {@link Manipulation} of correct {@link ManipulationType}.
     * @throws UnknownManipulationException In case of unknown manipulation type.
     */
    public static Manipulation create(ManipulationType type) throws UnknownManipulationException {
        switch (type) {
            case DELETE:
                return new Delete();
            case MOVE:
                return new Move();
            case SELECT:
                return new Select();
            case EDIT:
                return new Edit();
            default:
                throw new UnknownManipulationException(type);
        }
    }

    /**
     * Creates {@link Manipulation}, which needs additional data.
     * 
     * @param type type of manipulation.
     * @param data additional requested data.
     * @return {@link Manipulation} of correct {@link ManipulationType}.
     * @throws UnknownManipulationException In case of unknown manipulation type.
     */
    public static Manipulation create(ManipulationType type, Object data) throws UnknownManipulationException {
        switch (type) {
            case CREATE:
                return new Create((GroupNode) data);
            default:
                throw new UnknownManipulationException(type);
        }
    }

    /**
     * Duplicates given {@link Manipulation}.
     * 
     * @param manipulation {@link Manipulation} instance to be duplicated.
     * @return New duplicated {@link Manipulation}.
     */
    public static Manipulation duplicate(Manipulation manipulation) {
        return manipulation.duplicate();
    }

    /**
     * Creates {@link Manipulation} which should be active after given.
     * 
     * @param manipulation {@link Manipulation}, according which new one will be created.
     * @return New {@link Manipulation} according to creation order.
     */
    public static Manipulation createNext(Manipulation manipulation) {
        return manipulation.createNext();
    }
}
