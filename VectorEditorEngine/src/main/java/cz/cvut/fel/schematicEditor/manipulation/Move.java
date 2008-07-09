package cz.cvut.fel.schematicEditor.manipulation;

public class Move extends Manipulation {
    protected Move() {
        super(null);
        setManipulatedGroup(null);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Select#getManipulationType()
     */
    @Override
    public ManipulationType getManipulationType() {
        return ManipulationType.MOVE;
    }

    @Override
    protected Manipulation duplitate() {
        Move m = new Move();
        return m;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#isManipulatingElements()
     */
    @Override
    public boolean isManipulatingElements() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#isManipulatingGroups()
     */
    @Override
    public boolean isManipulatingGroups() {
        // TODO Auto-generated method stub
        return true;
    }
}
