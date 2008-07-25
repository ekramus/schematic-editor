package cz.cvut.fel.schematicEditor.application.guiElements.listeners.scenePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import javax.swing.JPopupMenu;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.application.Gui;
import cz.cvut.fel.schematicEditor.application.guiElements.PropertiesToolBar;
import cz.cvut.fel.schematicEditor.application.guiElements.ScenePanel;
import cz.cvut.fel.schematicEditor.application.guiElements.ScenePanelDrawingPopup;
import cz.cvut.fel.schematicEditor.core.Constants;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.support.Snap;
import cz.cvut.fel.schematicEditor.support.Support;
import cz.cvut.fel.schematicEditor.support.Transformation;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.TransformationNode;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationQueue;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.manipulation.manipulation.Create;
import cz.cvut.fel.schematicEditor.manipulation.manipulation.Delete;
import cz.cvut.fel.schematicEditor.manipulation.manipulation.Edit;
import cz.cvut.fel.schematicEditor.manipulation.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.manipulation.Move;
import cz.cvut.fel.schematicEditor.manipulation.manipulation.Select;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

/**
 * This class implements {@link MouseListener} for {@link ScenePanel}.
 * 
 * @author Urban Kravjansky
 */
public class ScenePanelMouseListener implements MouseListener {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger  logger;

    /**
     * Point of <code>mousePressed</code> event.
     */
    private Point2D.Double mousePressedPoint  = null;
    /**
     * Point of <code>mouseReleased</code> event.
     */
    private Point2D.Double mouseReleasedPoint = null;

    /**
     * Default constructor. It initializes super instance and logger.
     */
    public ScenePanelMouseListener() {
        super();
        logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * Method for mouse click events processing.
     * 
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    @SuppressWarnings("unused")
    public void mouseClicked(MouseEvent e) {
        // nothing to do
    }

    /**
     * Method for mouse enter events processing.
     * 
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    @SuppressWarnings("unused")
    public void mouseEntered(MouseEvent e) {
        logger.debug("mouse entered");
        // request focus for ScenePanel
        ScenePanel.getInstance().requestFocusInWindow();
    }

    /**
     * Method for mouse exit events processing.
     * 
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    @SuppressWarnings("unused")
    public void mouseExited(MouseEvent e) {
        logger.debug("mouse left");
    }

    /**
     * Method for mouse press events processing.
     * 
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent e) {
        try {
            setMousePressedPoint(new Point2D.Double(e.getPoint().getX(), e.getPoint().getY()));

            // get pointer rectangle
            Rectangle2D.Double r2d = Support.createPointerRectangle(new Point2D.Double(e.getX(),
                    e.getY()), Constants.POINT_SIZE);

            ManipulationType mt;
            mt = Structures.getManipulationQueue().peek().getManipulationType();
            if (mt == ManipulationType.CREATE) {
                createManipulationStart(e);
            } else if (mt == ManipulationType.SELECT) {
                selectManipulationStart(e, r2d);
            } else if (mt == ManipulationType.DELETE) {
                deleteManipulationStart(r2d);
            } else {
                throw new UnknownManipulationException(mt);
            }
        } catch (UnknownManipulationException ume) {
            logger.error(ume.getMessage());
        }
    }

    /**
     * Method for mouse release events processing.
     * 
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent e) {
        try {
            Snap.setGridSize(ScenePanel.getInstance().getGridSize());
            Snap.setSnappy(ScenePanel.getInstance().isSnapToGrid());
            ManipulationQueue mq = Structures.getManipulationQueue();

            setMouseReleasedPoint(new Point2D.Double(e.getPoint().getX(), e.getPoint().getY()));

            // get pointer rectangle
            Rectangle2D.Double r2d = Support.createPointerRectangle(new Point2D.Double(e.getX(),
                    e.getY()), Constants.POINT_SIZE);

            if (getMouseReleasedPoint().equals(getMousePressedPoint())) {
                logger.debug("Mouse CLICKED");
            }

            ManipulationType mt = Structures.getManipulationQueue().peek().getManipulationType();
            if (mt == ManipulationType.CREATE) {
                createManipulationEnd(e);
            } else if (mt == ManipulationType.EDIT) {
                editManipulationEnd(e, r2d);
            } else if (mt == ManipulationType.SELECT) {
                selectManipulationEnd(r2d);
            } else if (mt == ManipulationType.MOVE) {
                mq.peek().manipulationEnd(e, r2d, mq);
            } else if (mt == ManipulationType.DELETE) {
                deleteManipulationEnd(r2d);
            } else {
                throw new UnknownManipulationException(mt);
            }
        } catch (UnknownManipulationException ume) {
            logger.error(ume.getMessage());
        }
    }

    @Deprecated
    private void createManipulationEnd(MouseEvent e) throws UnknownManipulationException {
        Snap.setGridSize(ScenePanel.getInstance().getGridSize());
        Snap.setSnappy(ScenePanel.getInstance().isSnapToGrid());

        Create create = (Create) Structures.getManipulationQueue().peek();
        // check, what to do
        switch (create.getPointsLeft()) {
            case Element.ZERO_COORDINATES:
                create.setFinished(true);
                break;
            case Element.INFINITE_COORDINATES:
                // if button3 pressed, show popup menu
                if (e.getButton() == MouseEvent.BUTTON3) {
                    JPopupMenu popup = ScenePanelDrawingPopup.getScenePanelDrawingPopup();
                    popup.show(ScenePanel.getInstance(), e.getX(), e.getY());
                }
                // add temporary element, which can be replaced in mouseMoved
                else {
                    create.setStage(Create.STAGE_TWO);
                    create.addManipulationCoordinates(Snap.getSnap(e.getX()),
                                                      Snap.getSnap(e.getY()));
                }
                break;
            // add temporary element, which can be replaced in mouseMoved
            default:
                // if button3 pressed, show popup menu
                if (e.getButton() == MouseEvent.BUTTON3) {
                    JPopupMenu popup = ScenePanelDrawingPopup.getScenePanelDrawingPopup();
                    popup.show(ScenePanel.getInstance(), e.getX(), e.getY());
                } else {
                    create.setStage(Create.STAGE_TWO);
                    create.addManipulationCoordinates(Snap.getSnap(e.getX()),
                                                      Snap.getSnap(e.getY()));
                }
                break;
        }

        // finalize, if possible
        if (create.isFinished()) {
            ScenePanel.getInstance().processFinalManipulationStep();
        }
    }

    @Deprecated
    private void createManipulationStart(MouseEvent e) throws UnknownManipulationException {
        logger.debug("manipulation is instance of Create");

        Create create = (Create) Structures.getManipulationQueue().peek();
        create.setActive(true);

        Snap.setGridSize(ScenePanel.getInstance().getGridSize());
        Snap.setSnappy(ScenePanel.getInstance().isSnapToGrid());

        // mouse press is used only for first stage
        if (create.getStage() == Create.STAGE_ONE) {
            // add two pairs of coordinates (each element needs two)
            create.addManipulationCoordinates(Snap.getSnap(e.getX()), Snap.getSnap(e.getY()));
            create.addManipulationCoordinates(Snap.getSnap(e.getX()), Snap.getSnap(e.getY()));
        }
        // mouse press is for at least second time
        else {
            // nothing to do
        }
    }

    @Deprecated
    private void deleteManipulationEnd(Rectangle2D.Double r2d) throws UnknownManipulationException {
        Delete delete = (Delete) Structures.getManipulationQueue().peek();

        if (isMouseClicked() && delete.isActive()) {
            if (ScenePanel.getInstance().getSchemeSG().getTopNode().deleteHit(r2d)) {
                ScenePanel.getInstance().processFinalManipulationStep();
            }
        }
    }

    @Deprecated
    private void deleteManipulationStart(Rectangle2D.Double r2d)
            throws UnknownManipulationException {
        // this method is not really necessary, but it can be used in the future
        Delete delete = (Delete) Structures.getManipulationQueue().peek();
        delete.setActive(true);
    }

    @Deprecated
    private void editManipulationEnd(MouseEvent e, Rectangle2D.Double r2d)
            throws UnknownManipulationException {
        Edit edit = (Edit) Structures.getManipulationQueue().peek();
        Snap.setGridSize(ScenePanel.getInstance().getGridSize());
        Snap.setSnappy(ScenePanel.getInstance().isSnapToGrid());

        if (edit.isActive()) {
            logger.trace("object EDITED");

            // replace last manipulation coordinates for delta
            edit.replaceLastManipulationCoordinates(Snap.getSnap(e.getX()), Snap.getSnap(e.getY()));

            // get manipulated group
            GroupNode gn = edit.getManipulatedGroup();

            // enable manipulated group
            gn.setDisabled(false);

            // create select manipulation, so manipulation can proceed as select
            Select select = (Select) ManipulationFactory.create(ManipulationType.SELECT);
            select.setManipulatedGroup(gn);
            Structures.getManipulationQueue().offer(select);

            // add and execute manipulation
            Structures.getManipulationQueue().offer(edit);
            Structures.getManipulationQueue().execute();

            ScenePanel.getInstance().processFinalManipulationStep();
        }
    }

    @Deprecated
    private void editManipulationStart(MouseEvent e, Rectangle2D.Double r2d)
            throws UnknownManipulationException {
        Edit edit = (Edit) Structures.getManipulationQueue().peek();
        Snap.setGridSize(ScenePanel.getInstance().getGridSize());
        Snap.setSnappy(ScenePanel.getInstance().isSnapToGrid());

        logger.debug("creating EDIT manipulation");

        if (edit.isActive()) {
            GroupNode gn = edit.getManipulatedGroup();

            // add actual coordinates, to be able to calculate delta later
            edit.addManipulationCoordinates(Snap.getSnap(e.getX()), Snap.getSnap(e.getY()));
            // add two copies of same coordinates to be able to replace last one
            edit.addManipulationCoordinates(Snap.getSnap(e.getX()), Snap.getSnap(e.getY()));

            // set manipulated group disabled
            gn.setDisabled(true);
            ScenePanel.getInstance().schemeInvalidate(gn.getBounds());
        } else {
            Structures.getManipulationQueue().offer(
                                                    ManipulationFactory.create(ManipulationType.SELECT));
        }
    }

    @Deprecated
    private void moveManipulationStart(MouseEvent e, Double r2d)
            throws UnknownManipulationException {
        Move move = (Move) Structures.getManipulationQueue().peek();
        Snap.setGridSize(ScenePanel.getInstance().getGridSize());
        Snap.setSnappy(ScenePanel.getInstance().isSnapToGrid());

        GroupNode gn = ScenePanel.getInstance().getSchemeSG().getTopNode().findHit(r2d);

        // check, whether move is possible or not
        if (move.isActive() && move.getManipulatedGroup() == gn) {
            // add identity transformation, so it can be later changed
            gn.add(new TransformationNode(Transformation.getIdentity()));

            // add two copies of same coordinates to be able to replace last one
            move.addManipulationCoordinates(Snap.getSnap(e.getX()), Snap.getSnap(e.getY()));
            move.addManipulationCoordinates(Snap.getSnap(e.getX()), Snap.getSnap(e.getY()));

            // set manipulated group disabled
            gn.setDisabled(true);
            ScenePanel.getInstance().schemeInvalidate(gn.getBounds());
        }
        // move is not possible - fall back to Select manipulation
        else {
            Structures.getManipulationQueue().offer(
                                                    ManipulationFactory.create(ManipulationType.SELECT));
        }
    }

    /**
     * Method called on <code>mouseReleased</code> event when {@link Select} manipulation is
     * selected.
     * 
     * @param r2d
     *            pointer rectangle.
     */
    @Deprecated
    private void selectManipulationEnd(final Rectangle2D.Double r2d)
            throws UnknownManipulationException {
        Select select = (Select) Structures.getManipulationQueue().peek();

        // mouse clicked and hit something
        if (isMouseClicked()) {
            // some group is hit
            if (ScenePanel.getInstance().getSchemeSG().getTopNode().isHit(r2d)) {
                logger.debug("object SELECTED");

                // create new select object
                Structures.getManipulationQueue().offer(
                                                        ManipulationFactory.create(ManipulationType.SELECT));
                select = (Select) Structures.getManipulationQueue().peek();
                // activate selection
                select.setActive(true);
                // set selected group
                GroupNode gn = ScenePanel.getInstance().getSchemeSG().getTopNode().findHit(r2d);
                select.setManipulatedGroup(gn);

                ScenePanel.getInstance().schemeInvalidate(select.getManipulatedGroup().getBounds());

                // get parameter node and set properties panel according to it
                ParameterNode pn = gn.getChildrenParameterNode();
                Structures.getSceneProperties().setSelectedElementProperties(pn.getProperties());
                PropertiesToolBar.refresh();
            }
            // no group is hit
            else {
                logger.debug("nothing SELECTED");

                // create new select object
                Structures.getManipulationQueue().offer(
                                                        ManipulationFactory.create(ManipulationType.SELECT));
                select = (Select) Structures.getManipulationQueue().peek();

                ScenePanel.getInstance().schemeInvalidate(null);

                Structures.getSceneProperties().setSelectedElementProperties(null);
                PropertiesToolBar.refresh();
            }
        }
    }

    /**
     * When select manipulation starts, this method is called. It initializes all necessary data.
     * 
     * @param e
     * @param r2d
     */
    @Deprecated
    private void selectManipulationStart(final MouseEvent e, final Rectangle2D.Double r2d)
            throws UnknownManipulationException {
        Select select = (Select) Structures.getManipulationQueue().peek();
        Snap.setGridSize(ScenePanel.getInstance().getGridSize());
        Snap.setSnappy(ScenePanel.getInstance().isSnapToGrid());

        // manipulation is not active
        if (!select.isActive()) {
            // nothing to do
        }
        // select is active AND GroupNode is already selected
        else if (ScenePanel.getInstance().getSchemeSG().getTopNode().findHit(r2d) == select.getManipulatedGroup()) {
            // select is in edit active zone
            if (select.getManipulatedGroup().startEditing(r2d)) {
                // create Edit manipulation
                Edit edit = (Edit) ManipulationFactory.create(ManipulationType.EDIT);
                edit.setManipulatedGroup(select.getManipulatedGroup());
                edit.setActive(true);

                // set active manipulation edit
                Structures.getManipulationQueue().offer(edit);

                // continue with edit manipulation start
                editManipulationStart(e, r2d);
            }
            // select is in rotate active zone
            else if (select.getManipulatedGroup().isRotateZone(r2d)) {
                // TODO implement rotate active zone
            }
            // select can be used for move
            else {
                logger.trace("creating MOVE manipulation");

                // create Move manipulation
                Move move = (Move) ManipulationFactory.create(ManipulationType.MOVE);
                move.setManipulatedGroup(select.getManipulatedGroup());
                move.setActive(true);

                // set active manipulation move
                Structures.getManipulationQueue().offer(move);

                // continue with move manipulation start
                moveManipulationStart(e, r2d);
            }
        }
    }

    private Point2D.Double getMousePressedPoint() {
        return mousePressedPoint;
    }

    private Point2D.Double getMouseReleasedPoint() {
        return mouseReleasedPoint;
    }

    private boolean isMouseClicked() {
        return getMousePressedPoint().equals(getMouseReleasedPoint());
    }

    private void setMousePressedPoint(Point2D.Double mousePressedPoint) {
        this.mousePressedPoint = mousePressedPoint;
    }

    private void setMouseReleasedPoint(Point2D.Double mouseReleasedPoint) {
        this.mouseReleasedPoint = mouseReleasedPoint;
    }
}
