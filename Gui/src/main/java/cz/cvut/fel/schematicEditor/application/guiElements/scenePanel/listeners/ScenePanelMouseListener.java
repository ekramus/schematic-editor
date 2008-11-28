package cz.cvut.fel.schematicEditor.application.guiElements.scenePanel.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPopupMenu;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.application.guiElements.scenePanel.ScenePanel;
import cz.cvut.fel.schematicEditor.application.guiElements.scenePanelDrawingPopup.ScenePanelDrawingPopup;
import cz.cvut.fel.schematicEditor.configuration.GuiConfiguration;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.manipulation.Create;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationQueue;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.support.Support;

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
     * Getter for <code>mousePressedPoint</code>.
     *
     * @return {@link Point2D.Double} value of <code>mousePressedPoint</code>.
     */
    private Point2D.Double getMousePressedPoint() {
        return this.mousePressedPoint;
    }

    /**
     * Getter for <code>mouseReleasedPoint</code>.
     *
     * @return {@link Point2D.Double} value of <code>mouseReleasedPoint</code>.
     */
    private Point2D.Double getMouseReleasedPoint() {
        return this.mouseReleasedPoint;
    }

    /**
     * Indicates, whether mouse was clicked or not.
     *
     * @return <code>true</code>, if mouse was clicked, <code>false</code> else.
     */
    private boolean isMouseClicked() {
        return getMousePressedPoint().equals(getMouseReleasedPoint());
    }

    /**
     * Method for mouse click events processing.
     *
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent e) {
        // nothing to do
    }

    /**
     * Method for mouse enter events processing.
     *
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent e) {
        logger.trace("mouse entered");

        // request focus for ScenePanel
        ScenePanel.getInstance().requestFocusInWindow();
    }

    /**
     * Method for mouse exit events processing.
     *
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent e) {
        logger.trace("mouse left");
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
                                                                    GuiConfiguration.getInstance()
                                                                            .getPointerRectangle());

            Manipulation m = Structures.getActiveManipulation();
            try {
                if (!m.isActive() || (m.getManipulationType() == ManipulationType.SELECT)) {
                    // start manipulation and set result as active manipulation
                    Structures.setActiveManipulation(m.manipulationStart(e, r2d, Structures.getManipulationQueue(),
                                                                         isMouseClicked()));
                }
            } catch (NullPointerException npe) {
                logger.warn("No manipulation");
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
            ManipulationQueue mq = Structures.getManipulationQueue();

            setMouseReleasedPoint(new Point2D.Double(e.getPoint().getX(), e.getPoint().getY()));

            // get pointer rectangle
            Rectangle2D.Double r2d = Support.createPointerRectangle(new Point2D.Double(e.getX(), e.getY()),
                                                                    GuiConfiguration.getInstance()
                                                                            .getPointerRectangle());

            GroupNode topNode = ScenePanel.getInstance().getSchemeSG().getTopNode();

            // mouse was clicked
            if (getMouseReleasedPoint().equals(getMousePressedPoint())) {
                logger.debug("Mouse CLICKED");

                Manipulation manipulation = Structures.getActiveManipulation();
                try {
                    ManipulationType mt = manipulation.getManipulationType();

                    switch (mt) {
                        case CREATE:
                            Create create = (Create) manipulation;
                            // right mouse button is clicked
                            if (e.getButton() == MouseEvent.BUTTON3) {
                                // element has infinite coordinates
                                if (create.getPointsLeft() == Element.INFINITE_COORDINATES) {
                                    JPopupMenu popup = ScenePanelDrawingPopup.getScenePanelDrawingPopup(e, r2d);
                                    popup.show(ScenePanel.getInstance(), e.getX(), e.getY());
                                    logger.trace("Show right-click popup");
                                } else if (create.getManipulatedGroup().getElementType() == ElementType.T_BEZIER) {
                                    JPopupMenu popup = ScenePanelDrawingPopup.getScenePanelDrawingPopup(e, r2d);
                                    popup.show(ScenePanel.getInstance(), e.getX(), e.getY());
                                    logger.trace("Show right-click popup");
                                }
                            }
                            // left mouse button is clicked
                            else if (e.getButton() == MouseEvent.BUTTON1) {
                                ScenePanel.getInstance().tryFinishManipulation(e, r2d, mq, true);
                            }
                            break;
                        case SELECT:
                        case DELETE:
                        case EDIT:
                        case MOVE:
                            ScenePanel.getInstance().tryFinishManipulation(e, r2d, mq, true);
                            break;
                        default:
                            break;
                    }
                } catch (NullPointerException npe) {
                    logger.warn("No manipulation.");
                }
            }
            // mouse button was just released after drag
            else {
                ScenePanel.getInstance().tryFinishManipulation(e, r2d, mq, false);
            }
        } catch (UnknownManipulationException ume) {
            logger.error(ume.getMessage());
        }
    }

    private void setMousePressedPoint(Point2D.Double mousePressedPoint) {
        this.mousePressedPoint = mousePressedPoint;
    }

    private void setMouseReleasedPoint(Point2D.Double mouseReleasedPoint) {
        this.mouseReleasedPoint = mouseReleasedPoint;
    }
}
