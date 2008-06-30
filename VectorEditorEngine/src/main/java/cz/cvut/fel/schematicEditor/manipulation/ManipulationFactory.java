package cz.cvut.fel.schematicEditor.manipulation;

import cz.cvut.fel.schematicEditor.element.Element;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;

/**
 * This class implements factory pattern for {@link Manipulation} creation.
 *
 * @author Urban Kravjansky
 */
public class ManipulationFactory {
    /**
     * Default constructor, for future implementation purposes only. It is private to force static
     * use only.
     */
    private ManipulationFactory() {
        // nothing to do
    }

    /**
     * Creates {@link Manipulation} according to given {@link ManipulationType}.
     *
     * @param type
     *            type of manipulation.
     * @return {@link Manipulation} of correct {@link ManipulationType}.
     * @throws UnknownManipulationException
     *             In case of unknown manipulation type.
     */
    public static Manipulation create(ManipulationType type) throws UnknownManipulationException {
        switch (type) {
            case DELETE:
                return new Delete();
            case EDIT:
                return new Edit();
            case MOVE:
                return new Move();
            case SELECT:
                return new Select();
            default:
                throw new UnknownManipulationException(type);
        }
    }

    /**
     * Creates {@link Manipulation}, which needs additional data.
     *
     * @param type
     *            type of manipulation.
     * @param data
     *            additional requested data.
     * @return {@link Manipulation} of correct {@link ManipulationType}.
     * @throws UnknownManipulationException
     *             In case of unknown manipulation type.
     */
    public static Manipulation create(ManipulationType type, Object data)
            throws UnknownManipulationException {
        switch (type) {
            case CREATE:
                return new Create((Element) data);
            default:
                throw new UnknownManipulationException(type);
        }
    }

    public static Manipulation duplicate(Manipulation manipulation) {
        return manipulation.duplitate();
    }
}
