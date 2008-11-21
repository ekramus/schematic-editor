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
     * @param topNode top node of scene graph.
     * @return {@link Manipulation} of correct {@link ManipulationType}.
     * @throws UnknownManipulationException In case of unknown manipulation type.
     */
    public static Manipulation create(ManipulationType type, GroupNode topNode) throws UnknownManipulationException {
        switch (type) {
            case DELETE:
                return new Delete(topNode);
            case MOVE:
                return new Move(topNode);
            case SELECT:
                return new Select(topNode);
            case EDIT:
                return new Edit(topNode);
            case ROTATE:
                return new Rotate(topNode);
            case MIRROR:
                return new Mirror(topNode);
            case COPY:
                return new Copy(topNode);
            case CUT:
                // return new Cut(topNode);
                throw new UnknownManipulationException(type);
            case PASTE:
                return new Paste(topNode);
            default:
                throw new UnknownManipulationException(type);
        }
    }

    /**
     * Creates {@link Manipulation}, which needs additional data.
     *
     * @param type type of manipulation.
     * @param topNode top node of scene graph.
     * @param data additional requested data.
     * @return {@link Manipulation} of correct {@link ManipulationType}.
     * @throws UnknownManipulationException In case of unknown manipulation type.
     */
    public static Manipulation create(ManipulationType type, GroupNode topNode, Object data)
            throws UnknownManipulationException {
        switch (type) {
            case CREATE:
                return new Create(topNode, (GroupNode) data);
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
