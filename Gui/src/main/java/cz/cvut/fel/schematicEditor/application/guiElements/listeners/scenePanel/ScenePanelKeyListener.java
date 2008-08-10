package cz.cvut.fel.schematicEditor.application.guiElements.listeners.scenePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.application.Gui;
import cz.cvut.fel.schematicEditor.application.StatusBar;
import cz.cvut.fel.schematicEditor.application.guiElements.ScenePanel;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.element.ElementModificator;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.manipulation.Create;
import cz.cvut.fel.schematicEditor.manipulation.Delete;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.Select;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;

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
                Manipulation manipulation = Structures.getActiveManipulation();
                if (manipulation.getManipulationType() == ManipulationType.CREATE) {
                    Create create = (Create) manipulation;
                    logger.debug("manipulation is instance of Create");
                    if (create.getElementModificator() == ElementModificator.NO_MODIFICATION) {
                        create.setElementModificator(ElementModificator.SYMMETRIC_ELEMENT);
                        // TODO externalize string
                        StatusBar.getInstance().setSizeLockingLabel("to disable size locking, press CTRL");
                    } else {
                        create.setElementModificator(ElementModificator.NO_MODIFICATION);
                        // TODO externalize string
                        StatusBar.getInstance().setSizeLockingLabel("to enable size locking, press CTRL");
                    }
                }
            }
            // CTRL + Z
            else if ((e.isControlDown()) && (e.getKeyCode() == KeyEvent.VK_Z)) {
                logger.trace("UNexecuting...");

                // unexecute manipulation
                Structures.getManipulationQueue().unexecute(ScenePanel.getInstance().getSchemeSG().getTopNode());

                // invalidate scheme
                ScenePanel.getInstance().schemeInvalidate(null);

                // add manipulation before unexecuted manipulation as activeManipulation
                Structures.setActiveManipulation(ManipulationFactory.createNext(Structures.getManipulationQueue()
                        .getActiveManipulation()));

                logger.trace("Manipulation queue:\n" + Structures.getManipulationQueue());
            }
            // CTRL + Y
            else if ((e.isControlDown()) && (e.getKeyCode() == KeyEvent.VK_Y)) {
                logger.trace("REexecuting...");

                // reexecute manipulation
                Structures.getManipulationQueue().reexecute(ScenePanel.getInstance().getSchemeSG().getTopNode());

                // invalidate scheme
                ScenePanel.getInstance().schemeInvalidate(null);

                // add reexecuted manipulation as activeManipulation
                Structures.setActiveManipulation(ManipulationFactory.createNext(Structures.getManipulationQueue()
                        .getActiveManipulation()));

                logger.trace("Manipulation queue:\n" + Structures.getManipulationQueue());
            }
            // DEL or BACKSPACE
            else if ((e.getKeyCode() == KeyEvent.VK_DELETE) || (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
                Manipulation manipulation = Structures.getActiveManipulation();
                if (manipulation.getManipulationType() == ManipulationType.SELECT) {
                    GroupNode selected = manipulation.getManipulatedGroup();
                    if (ScenePanel.getInstance().getSchemeSG().getTopNode().delete(selected)) {
                        Delete delete = (Delete) ManipulationFactory.create(ManipulationType.DELETE);
                        delete.setActive(true);
                        Structures.getActiveManipulation();
                        // ScenePanel.getInstance().processFinalManipulationStep();
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
