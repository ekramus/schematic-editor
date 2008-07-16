package cz.cvut.fel.schematicEditor.application.guiElements.listeners.scenePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.application.Gui;
import cz.cvut.fel.schematicEditor.application.guiElements.ScenePanel;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.element.ElementModificator;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.manipulation.manipulation.Create;
import cz.cvut.fel.schematicEditor.manipulation.manipulation.Delete;
import cz.cvut.fel.schematicEditor.manipulation.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.manipulation.Select;

/**
 * This class implements {@link KeyListener} for {@link ScenePanel}.
 * 
 * @author Urban Kravjansky
 */
public class ScenePanelKeyListener implements KeyListener {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;

    /**
     * Default constructor. It initializes super instance and logger.
     */
    public ScenePanelKeyListener() {
        super();
        logger = Logger.getLogger(Gui.class.getName());
    }

    /**
     * Key listener for {@link ScenePanel}. It scans pressed keys and invokes action based on keycodes.
     * 
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    public void keyPressed(KeyEvent e) {
        try {
            if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                if (Structures.getManipulation().getManipulationType() == ManipulationType.CREATE) {
                    Create create = (Create) Structures.getManipulation();
                    logger.debug("manipulation is instance of Create");
                    if (create.getElementModificator() == ElementModificator.NO_MODIFICATION) {
                        create.setElementModificator(ElementModificator.SYMMETRIC_ELEMENT);
                        // TODO externalize string
                        Structures.getStatusBar().setSizeLockingLabel("to disable size locking, press CTRL");
                    } else {
                        create.setElementModificator(ElementModificator.NO_MODIFICATION);
                        // TODO externalize string
                        Structures.getStatusBar().setSizeLockingLabel("to enable size locking, press CTRL");
                    }
                }
            } else if ((e.isControlDown()) && (e.getKeyCode() == KeyEvent.VK_Z)) {
                logger.trace("UNexecuting...");
                Structures.getManipulationQueue().unexecute();
                Structures.getScenePanel().processFinalManipulationStep();
            } else if ((e.isControlDown()) && (e.getKeyCode() == KeyEvent.VK_Y)) {
                logger.trace("REexecuting...");
                Structures.getManipulationQueue().reexecute();
                Structures.getScenePanel().processFinalManipulationStep();
            } else if ((e.getKeyCode() == KeyEvent.VK_DELETE) || (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
                if (Structures.getManipulation().getManipulationType() == ManipulationType.SELECT) {
                    GroupNode selected = Structures.getManipulation().getManipulatedGroup();
                    if (Structures.getScenePanel().getSchemeSG().getTopNode().delete(selected)) {
                        Delete delete = (Delete) ManipulationFactory.create(ManipulationType.DELETE);
                        delete.setActive(true);
                        Structures.setManipulation(delete);
                        Structures.getScenePanel().processFinalManipulationStep();
                    }
                }
            }
        } catch (UnknownManipulationException ume) {
            logger.error(ume.getMessage());
        }
    }

    /**
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    @SuppressWarnings("unused")
    public void keyReleased(KeyEvent e) {
        // nothing to do
    }

    /**
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    @SuppressWarnings("unused")
    public void keyTyped(KeyEvent e) {
        // nothing to do
    }

}
