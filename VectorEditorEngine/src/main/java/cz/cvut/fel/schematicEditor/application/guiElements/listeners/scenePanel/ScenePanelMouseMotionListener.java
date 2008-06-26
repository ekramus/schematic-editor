package cz.cvut.fel.schematicEditor.application.guiElements.listeners.scenePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.application.Gui;
import cz.cvut.fel.schematicEditor.application.guiElements.ScenePanel;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.support.Snap;
import cz.cvut.fel.schematicEditor.support.Support;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.TransformationNode;
import cz.cvut.fel.schematicEditor.manipulation.Create;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.Move;
import cz.cvut.fel.schematicEditor.manipulation.Select;
import cz.cvut.fel.schematicEditor.types.Transformation;

/**
 * This class impelements {@link MouseMotionListener} for {@link ScenePanel}.
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
        Structures.getStatusBar().setCoordinatesJLabel("X: " + e.getX() + " Y: " + e.getY());
        Snap s = new Snap(Structures.getScenePanel().getGridSize(), Structures.getScenePanel().isSnapToGrid());

        // manipulation is create
        ManipulationType mt = Structures.getManipulation().getManipulationType();
        if (mt == ManipulationType.CREATE) {
            Create create = (Create) Structures.getManipulation();
            create.replaceLastManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));

            // just repaint (it takes care of element in progress)
            Structures.getScenePanel().processActualManipulationStep();
        }
        // manipulation is MOVE
        else if (mt == ManipulationType.MOVE) {
            Move move = (Move) Structures.getManipulation();

            move.replaceLastManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));

            // compute delta
            int i = move.getX().size() - 2;
            Point2D delta = new Point2D.Double(move.getX().lastElement().doubleValue() - move
                    .getX().get(i).doubleValue(), move.getY().lastElement().doubleValue() - move
                    .getY().get(i).doubleValue());

            // create transformation node using delta
            TransformationNode tn = new TransformationNode(Transformation.getShift(delta.getX(),
                                                                                   delta.getY()));
            // replace last transformation
            GroupNode gn = move.getManipulatedGroup();
            gn.removeLastTransformation();
            gn.add(tn);

            // just repaint (it takes care of element in progress)
            Structures.getScenePanel().processActualManipulationStep();
        }
    }

    /**
     * Method for mouse move events processing.
     * 
     * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
     */
    public void mouseMoved(MouseEvent e) {
        Structures.getStatusBar().setCoordinatesJLabel("X: " + e.getX() + " Y: " + e.getY());
        Snap s = new Snap(Structures.getScenePanel().getGridSize(), Structures.getScenePanel().isSnapToGrid());

        // manipulation is active
        if (Structures.getManipulation().isActive()) {
            // manipulation is create
            if (Structures.getManipulation().getManipulationType() == ManipulationType.CREATE) {
                Create create = (Create) Structures.getManipulation();
                // manipulation is not in stage one (not the first part of shape is drawn)
                if (create.getStage() != Create.STAGE_ONE) {
                    create.replaceLastManipulationCoordinates(s.getSnap(e.getX()), s.getSnap(e.getY()));

                    Structures.getScenePanel().processActualManipulationStep();
                }
            }
        }
    }
}
