package cz.cvut.fel.schematicEditor.application.guiElements.listeners.scenePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.application.Gui;
import cz.cvut.fel.schematicEditor.application.StatusBar;
import cz.cvut.fel.schematicEditor.application.guiElements.ScenePanel;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.support.Snap;
import cz.cvut.fel.schematicEditor.support.Support;
import cz.cvut.fel.schematicEditor.manipulation.Create;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.Move;

/**
 * This class implements {@link MouseMotionListener} for {@link ScenePanel}.
 * 
 * @author Urban Kravjansky
 */
public class ScenePanelMouseMotionListener implements MouseMotionListener {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;

    /**
     * Default constructor. It initializes super instance and logger.
     */
    public ScenePanelMouseMotionListener() {
        super();
        logger = Logger.getLogger(Gui.class.getName());
    }

    /**
     * Method for mouse drag events processing.
     * 
     * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
     */
    public void mouseDragged(MouseEvent e) {
        try {
            StatusBar.getInstance().setCoordinatesJLabel("X: " + e.getX() + " Y: " + e.getY());

            Manipulation m = Structures.getActiveManipulation();
            ManipulationType mt = m.getManipulationType();

            // manipulation is create
            if (mt == ManipulationType.CREATE) {
                createMouseMoved(m, e);
            }
            // manipulation is MOVE
            else if (mt == ManipulationType.MOVE) {
                moveMouseMoved(m, e);
            }
        } catch (NullPointerException npe) {
            logger.trace("No manipulation in manipulation queue");
        }
    }

    /**
     * Method for mouse move events processing.
     * 
     * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
     */
    public void mouseMoved(MouseEvent e) {
        try {
            StatusBar.getInstance().setCoordinatesJLabel("X: " + e.getX() + " Y: " + e.getY());

            Manipulation m = Structures.getActiveManipulation();

            // manipulation is active
            if (m.isActive()) {
                // manipulation is create
                if (m.getManipulationType() == ManipulationType.CREATE) {
                    createMouseMoved(m, e);
                }
            }
        } catch (NullPointerException npe) {
            logger.trace("No manipulation in manipulation queue");
        }
    }

    /**
     * MouseMoved part specific for {@link Create} manipulation.
     * 
     * @param m {@link Manipulation} to be modified.
     * @param e {@link MouseEvent} containing coordinates to be added.
     */
    private void createMouseMoved(Manipulation m, MouseEvent e) {
        Snap s = Snap.getInstance();

        m.replaceLastManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));

        // repaint scene
        ScenePanel.getInstance().repaint();
    }

    /**
     * MouseMoved part specific for {@link Move} manipulation.
     * 
     * @param m {@link Manipulation} to be modified.
     * @param e {@link MouseEvent} containing coordinates to be added.
     */
    private void moveMouseMoved(Manipulation m, MouseEvent e) {
        Snap s = Snap.getInstance();

        m.replaceLastManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));

        // just repaint (it takes care of element in progress)
        ScenePanel.getInstance().repaint();
    }
}
