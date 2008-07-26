package cz.cvut.fel.schematicEditor.manipulation.manipulation;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D.Double;

import cz.cvut.fel.schematicEditor.element.ElementModificator;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationQueue;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.ManipulationExecutionException;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.support.Snap;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;

/**
 * This class represents create {@link Manipulation}. It is created, when user presses any button
 * for new shape creation.
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
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#execute()
     */
    @Override
    public void execute() throws ManipulationExecutionException {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#unexecute()
     */
    @Override
    public void unexecute() throws ManipulationExecutionException {
        // TODO Auto-generated method stub

    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#manipulationEnd(MouseEvent,
     *      Rectangle2D.Double, ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public void manipulationEnd(MouseEvent e, Double r2d, ManipulationQueue manipulationQueue,
            GroupNode gn, boolean isMouseClicked) throws UnknownManipulationException {

        Snap s = Snap.getInstance();

        // Create create = (Create) Structures.getManipulationQueue().peek();
        // check, what to do
        switch (getPointsLeft()) {
            case Element.ZERO_COORDINATES:
                setFinished(true);
                break;
            case Element.INFINITE_COORDINATES:
                // if button3 pressed, show popup menu
                // TODO add popup menu somehow
                if (e.getButton() == MouseEvent.BUTTON3) {
                    // JPopupMenu popup = ScenePanelDrawingPopup.getScenePanelDrawingPopup();
                    // popup.show(ScenePanel.getInstance(), e.getX(), e.getY());
                }
                // add temporary element, which can be replaced in mouseMoved
                else {
                    setStage(Create.STAGE_TWO);
                    addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));
                }
                break;
            // add temporary element, which can be replaced in mouseMoved
            default:
                // if button3 pressed, show popup menu
                // TODO add popup menu somehow
                if (e.getButton() == MouseEvent.BUTTON3) {
                    // JPopupMenu popup = ScenePanelDrawingPopup.getScenePanelDrawingPopup();
                    // popup.show(ScenePanel.getInstance(), e.getX(), e.getY());
                } else {
                    setStage(Create.STAGE_TWO);
                    addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));
                }
                break;
        }

        // finalize, if possible
        if (isFinished()) {
            // ScenePanel.getInstance().processFinalManipulationStep();
        }
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#manipulationStart(MouseEvent,
     *      Rectangle2D.Double, ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public void manipulationStart(MouseEvent e, Double r2d, ManipulationQueue manipulationQueue,
            GroupNode gn, boolean isMouseClicked) throws UnknownManipulationException {
        Snap s = Snap.getInstance();

        // logger.debug("manipulation is instance of Create");

        // Create create = (Create) Structures.getManipulationQueue().peek();
        setActive(true);

        // Snap.setGridSize(ScenePanel.getInstance().getGridSize());
        // Snap.setSnappy(ScenePanel.getInstance().isSnapToGrid());

        // mouse press is used only for first stage
        if (getStage() == Create.STAGE_ONE) {
            // add two pairs of coordinates (each element needs two)
            addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));
            addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));
        }
        // mouse press is for at least second time
        else {
            // nothing to do
        }
    }
}
