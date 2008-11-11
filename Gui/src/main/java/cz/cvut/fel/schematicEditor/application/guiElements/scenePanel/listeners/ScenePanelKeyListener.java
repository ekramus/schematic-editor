package cz.cvut.fel.schematicEditor.application.guiElements.scenePanel.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.application.Gui;
import cz.cvut.fel.schematicEditor.application.StatusBar;
import cz.cvut.fel.schematicEditor.application.guiElements.scenePanel.ScenePanel;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.element.ElementModificator;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.manipulation.Copy;
import cz.cvut.fel.schematicEditor.manipulation.Create;
import cz.cvut.fel.schematicEditor.manipulation.Delete;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.Paste;
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
            // CTRL alone was pressed - size locking
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
            // CTRL + Z - undo
            else if ((e.isControlDown()) && (e.getKeyCode() == KeyEvent.VK_Z)) {
                logger.trace("UNexecuting...");

                // unexecute manipulation
                Structures.getManipulationQueue().unexecute();

                // invalidate scheme
                ScenePanel.getInstance().schemeInvalidate(null);

                // add manipulation before unexecuted manipulation as activeManipulation
                Structures.setActiveManipulation(ManipulationFactory.createNext(Structures.getManipulationQueue()
                        .getActiveManipulation()));

                logger.trace("Manipulation queue:\n" + Structures.getManipulationQueue());
            }
            // CTRL + Y - redo
            else if ((e.isControlDown()) && (e.getKeyCode() == KeyEvent.VK_Y)) {
                logger.trace("REexecuting...");

                // reexecute manipulation
                Structures.getManipulationQueue().reexecute();

                // invalidate scheme
                ScenePanel.getInstance().schemeInvalidate(null);

                // add reexecuted manipulation as activeManipulation
                Structures.setActiveManipulation(ManipulationFactory.createNext(Structures.getManipulationQueue()
                        .getActiveManipulation()));

                logger.trace("Manipulation queue:\n" + Structures.getManipulationQueue());
            }
            // CTRL + C - copy
            else if ((e.isControlDown()) && (e.getKeyCode() == KeyEvent.VK_C)) {
                logger.trace("COPYing...");

                // set active manipulation
                Copy copy = (Copy) ManipulationFactory.create(ManipulationType.COPY, ScenePanel.getInstance()
                        .getSchemeSG().getTopNode());
                // set manipulated group from select manipulation
                copy.setManipulatedGroup(Structures.getActiveManipulation().getManipulatedGroup());
                copy.manipulationStop(null, null, Structures.getManipulationQueue(), false);

                // set copy as active manipulation
                Structures.setActiveManipulation(copy);

                // try finish manipulation
                ScenePanel.getInstance().tryFinishManipulation(null, null, Structures.getManipulationQueue(), false);
            }
            // CTRL + V - paste
            else if ((e.isControlDown()) && (e.getKeyCode() == KeyEvent.VK_V)) {
                logger.trace("PASTEing...");

                // set active manipulation
                Paste paste = (Paste) ManipulationFactory.create(ManipulationType.PASTE, ScenePanel.getInstance()
                        .getSchemeSG().getTopNode());
                paste.manipulationStop(null, null, Structures.getManipulationQueue(), false);

                // set paste as active manipulation
                Structures.setActiveManipulation(paste);

                // try finish manipulation
                ScenePanel.getInstance().tryFinishManipulation(null, null, Structures.getManipulationQueue(), false);
            }
            // DEL or BACKSPACE
            else if ((e.getKeyCode() == KeyEvent.VK_DELETE) || (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
                Manipulation manipulation = Structures.getActiveManipulation();
                if (manipulation.getManipulationType() == ManipulationType.SELECT) {
                    GroupNode selected = manipulation.getManipulatedGroup();
                    if (ScenePanel.getInstance().getSchemeSG().getTopNode().delete(selected)) {
                        Delete delete = (Delete) ManipulationFactory.create(ManipulationType.DELETE, ScenePanel
                                .getInstance().getSchemeSG().getTopNode());
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
    public void keyReleased(KeyEvent e) {
        // nothing to do
    }

    /**
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    public void keyTyped(KeyEvent e) {
        // nothing to do
    }

}
