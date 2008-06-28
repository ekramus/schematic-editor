package cz.cvut.fel.schematicEditor.manipulation;


public class Move extends Select {
    protected Move() {
        super();
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Select#getManipulationType()
     */
    @Override
    public ManipulationType getManipulationType() {
        return ManipulationType.MOVE;
    }
}
