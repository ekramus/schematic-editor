package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanel.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.guiAdvanced.GuiAdvanced;
import cz.cvut.fel.schematicEditor.guiAdvanced.StatusBar;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanel.ScenePanel;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.support.Snap;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

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
        logger = Logger.getLogger(GuiAdvanced.class.getName());
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

            // manipulation is active
            if (m.isActive()) {
                UnitPoint up = new UnitPoint(e.getX(), e.getY());
                UnitPoint snap = Snap.getSnap(up, m.getSnapCoordinates());
                m.replaceLastManipulationCoordinates(snap.getUnitX(), snap.getUnitY());

                // repaint scene, it is much faster than full scene invalidate
                GuiAdvanced.getActiveScenePanel().repaint();
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
                UnitPoint up = new UnitPoint(e.getX(), e.getY());
                UnitPoint snap = Snap.getSnap(up, m.getSnapCoordinates());
                m.replaceLastManipulationCoordinates(snap.getUnitX(), snap.getUnitY());

                // repaint scene, it is much faster than full scene invalidate
                GuiAdvanced.getActiveScenePanel().repaint();
            }
        } catch (NullPointerException npe) {
            logger.trace("No active manipulation");
        }
    }
}
