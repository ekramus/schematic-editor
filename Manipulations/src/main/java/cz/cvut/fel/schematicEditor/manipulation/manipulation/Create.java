package cz.cvut.fel.schematicEditor.manipulation.manipulation;

import cz.cvut.fel.schematicEditor.element.ElementModificator;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;

/**
 * This class represents create {@link Manipulation}. It is created, when user presses any button for new shape
 * creation.
 * 
 * @author Urban Kravjansky
 */
public class Create extends Manipulation {
    // TODO rewrite as enum
    /**
     * First stage of element create.
     */
    public static final int STAGE_ONE   = 1;
    /**
     * Second stage of element create.
     */
    public static final int STAGE_TWO   = 2;
    /**
     * Third stage of element create.
     */
    public static final int STAGE_THREE = 3;

    /**
     * Stage of current manipulation.
     */
    private int             stage;
    /**
     * Number of points left for given shape.
     */
    private int             pointsLeft;
    private boolean         finished;

    /**
     * @param manipulatedElement
     */
    protected Create(Element manipulatedElement) {
        super(manipulatedElement);
        setFinished(false);
        setStage(STAGE_ONE);
        setPointsLeft(manipulatedElement.getNumberOfCoordinates());
        setElementModificator(ElementModificator.NO_MODIFICATION);
        // Structures.getStatusBar().setSizeLockingLabel("to enable size locking, press CTRL");
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#addManipulationCoordinates(java.lang.Double,
     *      java.lang.Double)
     */
    @Override
    public void addManipulationCoordinates(Unit x, Unit y) {
        super.addManipulationCoordinates(x, y);
        if (getPointsLeft() != Element.INFINITE_COORDINATES) {
            setPointsLeft(getPointsLeft() - 1);
        }
    }

    /**
     * @return the stage
     */
    public int getStage() {
        return this.stage;
    }

    /**
     * @param stage
     *            the stage to set
     */
    public void setStage(int stage) {
        this.stage = stage;
    }

    /**
     * @return the pointsLeft
     */
    public int getPointsLeft() {
        return this.pointsLeft;
    }

    /**
     * @param pointsLeft
     *            the pointsLeft to set
     */
    public void setPointsLeft(int pointsLeft) {
        this.pointsLeft = pointsLeft;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#getManipulationType()
     */
    @Override
    public ManipulationType getManipulationType() {
        return ManipulationType.CREATE;
    }

    /**
     * @return
     */
    @Override
    public boolean isManipulatingElements() {
        // TODO Auto-generated method stub
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#isManipulatingGroups()
     */
    @Override
    public boolean isManipulatingGroups() {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @return the elementModificator
     */
    public ElementModificator getElementModificator() {
        return getManipulatedElement().getElementModificator();
    }

    /**
     * @param elementModificator
     *            the elementModificator to set
     */
    public void setElementModificator(ElementModificator elementModificator) {
        getManipulatedElement().setElementModificator(elementModificator);
    }

    /**
     * @return the finished
     */
    public final boolean isFinished() {
        return this.finished;
    }

    /**
     * @param finished
     *            the finished to set
     */
    public final void setFinished(boolean finished) {
        this.finished = finished;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#newInstance(cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation)
     */
    @Override
    protected Manipulation duplitate() {
        Create c = new Create(getManipulatedElement().newInstance());
        return c;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#execute()
     */
    @Override
    public void execute() throws ManipulationExecutionException {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#unexecute()
     */
    @Override
    public void unexecute() throws ManipulationExecutionException {
        // TODO Auto-generated method stub

    }
}
