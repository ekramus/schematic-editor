package cz.cvut.fel.schematicEditor.application.guiElements.listeners.scenePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPopupMenu;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.application.guiElements.ScenePanel;
import cz.cvut.fel.schematicEditor.application.guiElements.ScenePanelDrawingPopup;
import cz.cvut.fel.schematicEditor.core.Constants;
import cz.cvut.fel.schematicEditor.core.Structures;
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
        logger.trace("mouse entered");

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
                                                                    Constants.POINT_SIZE);

            Manipulation m = Structures.getManipulationQueue().peek();
            if (!m.isActive()) {
                m.manipulationStart(e, r2d, Structures.getManipulationQueue(), ScenePanel.getInstance().getSchemeSG()
                        .getTopNode(), isMouseClicked());
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
                                                                    Constants.POINT_SIZE);

            GroupNode topNode = ScenePanel.getInstance().getSchemeSG().getTopNode();

            // mouse was clicked
            if (getMouseReleasedPoint().equals(getMousePressedPoint())) {
                logger.debug("Mouse CLICKED");

                // it is Create manipulation
                Manipulation manipulation = mq.peek();
                if (manipulation.getManipulationType() == ManipulationType.CREATE) {
                    Create create = (Create) manipulation;
                    // right mouse button is clicked
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        // element has infinite coordinates
                        if (create.getPointsLeft() == Element.INFINITE_COORDINATES) {
                            JPopupMenu popup = ScenePanelDrawingPopup.getScenePanelDrawingPopup(e, r2d);
                            popup.show(ScenePanel.getInstance(), e.getX(), e.getY());
                            logger.trace("Show right-click popup");
                        }
                    }
                    // left mouse button is clicked
                    else if (e.getButton() == MouseEvent.BUTTON1) {
                        tryFinishManipulation(e, r2d, mq, topNode, false);
                    }
                }
            }
            // mouse button was just released after drag
            else {
                tryFinishManipulation(e, r2d, mq, topNode, false);
            }
        } catch (UnknownManipulationException ume) {
            logger.error(ume.getMessage());
        }
    }

    private void tryFinishManipulation(MouseEvent e, Rectangle2D.Double r2d, ManipulationQueue manipulationQueue,
            GroupNode topNode, boolean isMouseClicked) throws UnknownManipulationException {
        // try to finish manipulation
        if (manipulationQueue.peek().manipulationEnd(e, r2d, manipulationQueue, topNode, isMouseClicked())) {
            manipulationQueue.execute();
            ScenePanel.getInstance().schemeInvalidate(null);
        }
        // manipulation is not finished yet
        else {
            logger.trace("Waiting for manipulation end");
        }
    }

    private void setMousePressedPoint(Point2D.Double mousePressedPoint) {
        this.mousePressedPoint = mousePressedPoint;
    }

    private void setMouseReleasedPoint(Point2D.Double mouseReleasedPoint) {
        this.mouseReleasedPoint = mouseReleasedPoint;
    }
}
