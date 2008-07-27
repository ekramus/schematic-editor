package cz.cvut.fel.schematicEditor.manipulation.manipulation;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.ElementModificator;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.element.element.shape.Shape;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.ShapeNode;
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
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;

    /**
     * Stage of {@link Manipulation}. It indicates, what will be the next manipulation operation.
     * 
     * @author Urban Kravjansky
     */
    private enum Stage {
        /**
         * First stage of element create.
         */
        STAGE_ONE,
        /**
         * Second stage of element create.
         */
        STAGE_TWO,
        /**
         * Third stage of element create.
         */
        STAGE_THREE
    }

    /**
     * Stage of current manipulation.
     */
    private Stage   stage;
    /**
     * Number of points left for given shape.
     */
    private int     pointsLeft;
    /**
     * Indicates, whether {@link Create} manipulation is finished or not.
     */
    private boolean finished;

    /**
     * @param manipulatedElement
     */
    protected Create(Element manipulatedElement) {
        super(manipulatedElement);

        logger = Logger.getLogger(this.getClass().getName());

        setFinished(false);
        setStage(Stage.STAGE_ONE);
        setPointsLeft(manipulatedElement.getNumberOfCoordinates());
        setElementModificator(ElementModificator.NO_MODIFICATION);
        // TODO add info text into status bar
        // Structures.getStatusBar().setSizeLockingLabel("to enable size locking, press CTRL");
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#addManipulationCoordinates(Unit,Unit)
     */
    @Override
    public void addManipulationCoordinates(Unit x, Unit y) {
        super.addManipulationCoordinates(x, y);
        if (getPointsLeft() != Element.INFINITE_COORDINATES) {
            setPointsLeft(getPointsLeft() - 1);
        }

        logger.trace("added manipulation coordinates, points left: " + getPointsLeft());
    }

    /**
     * @return the stage
     */
    public Stage getStage() {
        return this.stage;
    }

    /**
     * @param stage
     *            the stage to set
     */
    public void setStage(Stage stage) {
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

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#isManipulatingGroups()
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
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#duplicate()
     */
    @Override
    protected Manipulation duplicate() {
        Create c = new Create(getManipulatedElement().newInstance());
        return c;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#execute()
     */
    @Override
    public void execute() throws ManipulationExecutionException {
        logger.trace(this + " executed");
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#unexecute()
     */
    @Override
    public void unexecute() throws ManipulationExecutionException {
        logger.trace(this + " unexecuted");
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#manipulationEnd(MouseEvent,
     *      Rectangle2D.Double, ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public boolean manipulationEnd(MouseEvent e, Rectangle2D.Double r2d,
            ManipulationQueue manipulationQueue, GroupNode groupNode, boolean isMouseClicked)
            throws UnknownManipulationException {
        logger.trace(this + " manipulation END");

        Snap s = Snap.getInstance();

        // Create create = (Create) Structures.getManipulationQueue().peek();
        // check, what to do
        switch (getPointsLeft()) {
            case Element.ZERO_COORDINATES:
                setFinished(true);
                break;
            case Element.INFINITE_COORDINATES:
                // add temporary coordinates, which can be replaced in mouseMoved
                if (e.getButton() != MouseEvent.BUTTON3) {
                    setStage(Stage.STAGE_TWO);
                    addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));
                }
                break;
            // add temporary coordinates, which can be replaced in mouseMoved
            default:
                if (e.getButton() != MouseEvent.BUTTON3) {
                    setStage(Stage.STAGE_TWO);
                    addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));
                }
                break;
        }

        // finalize, if possible
        if (isFinished()) {
            Element child = null;
            GroupNode gn;
            ShapeNode sn;
            ParameterNode pn;

            logger.debug("processing final manipulation step");

            Manipulation m = manipulationQueue.peek();

            child = m.getManipulatedElement();
            m.setActive(false);

            sn = new ShapeNode((Shape) child);
            pn = new ParameterNode();

            // pn.setProperties(Structures.getSceneProperties().getSceneElementProperties());

            logger.debug("Nodes created");

            gn = new GroupNode();
            gn.add(pn);
            gn.add(sn);

            groupNode.add(gn);
        }

        return isFinished();
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation#manipulationStart(MouseEvent,
     *      Rectangle2D.Double, ManipulationQueue, GroupNode, boolean)
     */
    @Override
    public void manipulationStart(MouseEvent e, Rectangle2D.Double r2d,
            ManipulationQueue manipulationQueue, GroupNode gn, boolean isMouseClicked)
            throws UnknownManipulationException {
        logger.trace(this + " manipulation START");

        Snap s = Snap.getInstance();

        // Create create = (Create) Structures.getManipulationQueue().peek();
        setActive(true);

        // Snap.setGridSize(ScenePanel.getInstance().getGridSize());
        // Snap.setSnappy(ScenePanel.getInstance().isSnapToGrid());

        // mouse press is used only for first stage
        if (getStage() == Stage.STAGE_ONE) {
            // add two pairs of coordinates (each element needs two)
            addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));
            addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));
        }
        // mouse press is for at least second time
        else {
            // nothing to do
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Create(" + getManipulatedElement() + ")";
    }
}
