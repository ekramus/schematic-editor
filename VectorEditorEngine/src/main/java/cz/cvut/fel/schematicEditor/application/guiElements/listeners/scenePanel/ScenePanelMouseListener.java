package cz.cvut.fel.schematicEditor.application.guiElements.listeners.scenePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.lang.management.ManagementFactory;

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
import cz.cvut.fel.schematicEditor.element.Element;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.TransformationNode;
import cz.cvut.fel.schematicEditor.manipulation.Create;
import cz.cvut.fel.schematicEditor.manipulation.Delete;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.Move;
import cz.cvut.fel.schematicEditor.manipulation.Select;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.types.Transformation;

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
        logger = Logger.getLogger(Gui.class.getName());
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
        Structures.getScenePanel().requestFocusInWindow();
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
            Rectangle2D.Double r2d = Support.createPointerRectangle(new Point2D.Double(e.getX(), e.getY()),
                                                                    Constants.POINT_SIZE);

            ManipulationType mt;
            mt = Structures.getManipulation().getManipulationType();
            if (mt == ManipulationType.CREATE) {
                createManipulationStart(e);
            } else if (mt == ManipulationType.EDIT) {
                editManipulationStart(e, r2d);
            } else if (mt == ManipulationType.SELECT) {
                selectManipulationStart(e, r2d);
            } else if (mt == ManipulationType.MOVE) {
                moveManipulationStart(e, r2d);
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
            setMouseReleasedPoint(new Point2D.Double(e.getPoint().getX(), e.getPoint().getY()));

            // get pointer rectangle
            Rectangle2D.Double r2d = Support.createPointerRectangle(new Point2D.Double(e.getX(), e.getY()),
                                                                    Constants.POINT_SIZE);

            if (getMouseReleasedPoint().equals(getMousePressedPoint())) {
                logger.debug("Mouse CLICKED");
            }

            ManipulationType mt = Structures.getManipulation().getManipulationType();
            if (mt == ManipulationType.CREATE) {
                createManipulationEnd(e);
            } else if (mt == ManipulationType.EDIT) {
                editManipulationEnd();
            } else if (mt == ManipulationType.SELECT) {
                selectManipulationEnd(r2d);
            } else if (mt == ManipulationType.MOVE) {
                moveManipulationEnd(e, r2d);
            } else if (mt == ManipulationType.DELETE) {
                deleteManipulationEnd(r2d);
            } else {
                throw new UnknownManipulationException(mt);
            }
        } catch (UnknownManipulationException ume) {
            logger.error(ume.getMessage());
        }
    }

    private void createManipulationEnd(MouseEvent e) throws UnknownManipulationException {
        Snap s = new Snap(Structures.getScenePanel().getGridSize(), Structures.getScenePanel().isSnapToGrid());
        logger.trace(s);

        Create create = (Create) Structures.getManipulation();
        // check, what to do
        switch (create.getPointsLeft()) {
            case Element.ZERO_COORDINATES:
                create.setFinished(true);
                break;
            case Element.INFINITE_COORDINATES:
                // if button3 pressed, show popup menu
                if (e.getButton() == MouseEvent.BUTTON3) {
                    JPopupMenu popup = ScenePanelDrawingPopup.getScenePanelDrawingPopup();
                    popup.show(Structures.getScenePanel(), e.getX(), e.getY());
                }
                // add temporary element, which can be replaced in mouseMoved
                else {
                    create.setStage(Create.STAGE_TWO);
                    create.addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));
                }
                break;
            // add temporary element, which can be replaced in mouseMoved
            default:
                // if button3 pressed, show popup menu
                if (e.getButton() == MouseEvent.BUTTON3) {
                    JPopupMenu popup = ScenePanelDrawingPopup.getScenePanelDrawingPopup();
                    popup.show(Structures.getScenePanel(), e.getX(), e.getY());
                } else {
                    create.setStage(Create.STAGE_TWO);
                    create.addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));
                }
                break;
        }

        // finalize, if possible
        if (create.isFinished()) {
            Structures.getScenePanel().processFinalManipulationStep();
        }
    }

    private void createManipulationStart(MouseEvent e) throws UnknownManipulationException {
        logger.debug("manipulation is instance of Create");

        Create create = (Create) Structures.getManipulation();
        create.setActive(true);

        Snap s = new Snap(Structures.getScenePanel().getGridSize(), Structures.getScenePanel().isSnapToGrid());
        logger.trace(s);

        // mouse press is used only for first stage
        if (create.getStage() == Create.STAGE_ONE) {
            // add two pairs of coordinates (each element needs two)
            create.addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));
            create.addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));
        }
        // mouse press is for at least second time
        else {
            // nothing to do
        }
    }

    private void deleteManipulationEnd(Rectangle2D.Double r2d) throws UnknownManipulationException {
        Delete delete = (Delete) Structures.getManipulation();

        if (isMouseClicked() && delete.isActive()) {
            if (Structures.getScenePanel().getSchemeSG().getTopNode().deleteHit(r2d)) {
                Structures.getScenePanel().processFinalManipulationStep();
            }
        }
    }

    private void deleteManipulationStart(Rectangle2D.Double r2d) throws UnknownManipulationException {
        // this method is not really necessary, but it can be used in the future
        Delete delete = (Delete) Structures.getManipulation();
        delete.setActive(true);
    }

    private void editManipulationEnd() {
        // nothing to do
    }

    private void editManipulationStart(MouseEvent e, Rectangle2D.Double r2d) {
        logger.info("Edit element");
        // Edit edit = (Edit) Structures.getManipulation();

        // in case element is hit
        if (Structures.getScenePanel().getSchemeSG().getTopNode().isHit(r2d)) {
            logger.info("Element hit");
        }
    }

    private void moveManipulationEnd(MouseEvent e, Double r2d) throws UnknownManipulationException {
        Move move = (Move) Structures.getManipulation();
        Snap s = new Snap(Structures.getScenePanel().getGridSize(), Structures.getScenePanel().isSnapToGrid());

        if (move.isActive()) {
            logger.debug("object MOVED");

            move.replaceLastManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));

            // compute delta
            int i = move.getX().size() - 2;
            Point2D delta = new Point2D.Double(move.getX().lastElement().doubleValue() - move.getX().get(i)
                    .doubleValue(), move.getY().lastElement().doubleValue() - move.getY().get(i).doubleValue());

            // create transformation node using delta
            TransformationNode tn = new TransformationNode(Transformation.getShift(delta.getX(), delta.getY()));
            // replace last transformation
            GroupNode gn = move.getManipulatedGroup();
            gn.removeLastTransformation();
            gn.add(tn);

            // enable manipulated group
            gn.setDisabled(false);

            logger.debug("MOVE transformation: " + tn.getTransformation());

            Structures.getScenePanel().processFinalManipulationStep();
        }
    }

    private void moveManipulationStart(MouseEvent e, Double r2d) throws UnknownManipulationException {
        Move move = (Move) Structures.getManipulation();
        Snap s = new Snap(Structures.getScenePanel().getGridSize(), Structures.getScenePanel().isSnapToGrid());

        GroupNode gn = Structures.getScenePanel().getSchemeSG().getTopNode().findHit(r2d);
        // check, whether move is possible or not
        if (move.isActive() && move.getManipulatedGroup() == gn) {
            // add identity transformation, so it can be later changed
            gn.add(new TransformationNode(Transformation.getIdentity()));

            // add two copies of same coordinates to be able to replace last one
            move.addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));
            move.addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));

            // set manipulated group disabled
            gn.setDisabled(true);
            Structures.getScenePanel().schemeInvalidate(gn.getBounds());
        }
        // move is not possible - fall back to Select manipulation
        else {
            Structures.setManipulation(ManipulationFactory.create(ManipulationType.SELECT));
        }
    }

    /**
     * Method called on <code>mouseReleased</code> event when {@link Select} manipulation is selected.
     * 
     * @param r2d
     *            pointer rectangle.
     */
    private void selectManipulationEnd(final Rectangle2D.Double r2d) throws UnknownManipulationException {
        Select select = (Select) Structures.getManipulation();

        // mouse clicked and hit something
        if (isMouseClicked()) {
            // some group is hit
            if (Structures.getScenePanel().getSchemeSG().getTopNode().isHit(r2d)) {
                logger.debug("object SELECTED");

                // create new select object
                Structures.setManipulation(ManipulationFactory.create(ManipulationType.SELECT));
                select = (Select) Structures.getManipulation();
                // activate selection
                select.setActive(true);
                // set selected group
                GroupNode gn = Structures.getScenePanel().getSchemeSG().getTopNode().findHit(r2d);
                select.setManipulatedGroup(gn);

                Structures.getScenePanel().schemeInvalidate(select.getManipulatedGroup().getBounds());

                // get parameter node and set properties panel according to it
                ParameterNode pn = gn.getChildrenParameterNode();
                Structures.getSceneProperties().setSelectedElementProperties(pn.getProperties());
                PropertiesToolBar.refresh();
            }
            // no group is hit
            else {
                logger.debug("nothing SELECTED");

                // create new select object
                Structures.setManipulation(ManipulationFactory.create(ManipulationType.SELECT));
                select = (Select) Structures.getManipulation();

                Structures.getScenePanel().schemeInvalidate(null);

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
    private void selectManipulationStart(final MouseEvent e, final Rectangle2D.Double r2d)
            throws UnknownManipulationException {
        Select select = (Select) Structures.getManipulation();
        Snap s = new Snap(Structures.getScenePanel().getGridSize(), Structures.getScenePanel().isSnapToGrid());

        // manipulation is not active
        if (!select.isActive()) {
            // nothing to do
        }
        // select is active AND GroupNode is already selected
        else if (Structures.getScenePanel().getSchemeSG().getTopNode().findHit(r2d) == select.getManipulatedGroup()) {
            logger.debug("creating MOVE manipulation");

            // create Move manipulation
            Move move = (Move) ManipulationFactory.create(ManipulationType.MOVE);

            GroupNode gn = select.getManipulatedGroup();

            // add identity transformation, so it can be later changed
            gn.add(new TransformationNode(Transformation.getIdentity()));

            move.setManipulatedGroup(gn);
            // add two copies of same coordinates to be able to replace last one
            move.addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));
            move.addManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));
            // set manipulation active
            move.setActive(true);

            // set manipulated group disabled
            gn.setDisabled(true);
            Structures.getScenePanel().schemeInvalidate(gn.getBounds());

            Structures.setManipulation(move);
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
