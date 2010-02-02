package cz.cvut.fel.schematicEditor.manipulation;

import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.original.graphNode.GroupNode;

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
     * @param source button, which created manipulation.
     * @return {@link Manipulation} of correct {@link ManipulationType}.
     * @throws UnknownManipulationException In case of unknown manipulation type.
     */
    public static Manipulation create(ManipulationType type, GroupNode topNode, Object source)
            throws UnknownManipulationException {
        switch (type) {
            case DELETE:
                return new Delete(topNode, source);
            case MOVE:
                return new Move(topNode, source);
            case SELECT:
                return new Select(topNode, source);
            case EDIT:
                return new Edit(topNode, source);
            case ROTATE:
                return new Rotate(topNode, source);
            case MIRROR:
                return new Mirror(topNode, source);
            case COPY:
                return new Copy(topNode, source);
            case CUT:
                // return new Cut(topNode);
                throw new UnknownManipulationException(type);
            case PASTE:
                return new Paste(topNode, source);
            case SELECT_ROTATION_CENTER:
                return new SelectRotationCenter(topNode, source);
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
     * @param source button, which created manipulation.
     * @return {@link Manipulation} of correct {@link ManipulationType}.
     * @throws UnknownManipulationException In case of unknown manipulation type.
     */
    public static Manipulation create(ManipulationType type, GroupNode topNode, Object source, Object data)
            throws UnknownManipulationException {
        switch (type) {
            case CREATE:
                return new Create(topNode, (GroupNode) data, source);
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
