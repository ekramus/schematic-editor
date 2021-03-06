package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanel.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.configuration.GuiConfiguration;
import cz.cvut.fel.schematicEditor.element.ElementModificator;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.guiAdvanced.StatusBar;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanel.ScenePanel;
import cz.cvut.fel.schematicEditor.manipulation.Copy;
import cz.cvut.fel.schematicEditor.manipulation.Create;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.Paste;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.support.Support;

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
                Manipulation manipulation = Gui.getActiveScenePanel().getActiveManipulation();
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
                Gui.getActiveScenePanel().getManipulationQueue().unexecute();

                // invalidate scheme
                Gui.getActiveScenePanel().sceneInvalidate(null);

                // add manipulation before unexecuted manipulation as activeManipulation
                Gui.getActiveScenePanel().setActiveManipulation(
                                                                ManipulationFactory.createNext(Gui
                                                                        .getActiveScenePanel().getManipulationQueue()
                                                                        .getActiveManipulation()));

                logger.trace("Manipulation queue:\n" + Gui.getActiveScenePanel().getManipulationQueue());
            }
            // CTRL + Y - redo
            else if ((e.isControlDown()) && (e.getKeyCode() == KeyEvent.VK_Y)) {
                logger.trace("REexecuting...");

                // reexecute manipulation
                Gui.getActiveScenePanel().getManipulationQueue().reexecute();

                // invalidate scheme
                Gui.getActiveScenePanel().sceneInvalidate(null);

                // add reexecuted manipulation as activeManipulation
                Gui.getActiveScenePanel().setActiveManipulation(
                                                                ManipulationFactory.createNext(Gui
                                                                        .getActiveScenePanel().getManipulationQueue()
                                                                        .getActiveManipulation()));

                logger.trace("Manipulation queue:\n" + Gui.getActiveScenePanel().getManipulationQueue());
            }
            // CTRL + C - copy
            else if ((e.isControlDown()) && (e.getKeyCode() == KeyEvent.VK_C)) {
                logger.trace("COPYing...");

                // set active manipulation
                Copy copy = (Copy) ManipulationFactory.create(ManipulationType.COPY, Gui.getActiveScenePanel()
                        .getSceneGraph().getTopNode(), e.getSource());
                // set manipulated group from select manipulation
                copy.setManipulatedGroup(Gui.getActiveScenePanel().getActiveManipulation().getManipulatedGroup());
                copy.manipulationStop(null, null, Gui.getActiveScenePanel().getManipulationQueue(), Gui
                        .getActiveScenePanel().getZoomFactor(), false, Gui.getActiveGraphics2D());

                // set copy as active manipulation
                Gui.getActiveScenePanel().setActiveManipulation(copy);

                // try finish manipulation
                Gui.getActiveScenePanel()
                        .tryFinishManipulation(null, null, Gui.getActiveScenePanel().getManipulationQueue(), false);
            }
            // CTRL + V - paste
            else if ((e.isControlDown()) && (e.getKeyCode() == KeyEvent.VK_V)) {
                logger.trace("PASTEing...");

                // set active manipulation
                Paste paste = (Paste) ManipulationFactory.create(ManipulationType.PASTE, Gui.getActiveScenePanel()
                        .getSceneGraph().getTopNode(), e.getSource());
                paste.manipulationStop(null, null, Gui.getActiveScenePanel().getManipulationQueue(), Gui
                        .getActiveScenePanel().getZoomFactor(), false, Gui.getActiveGraphics2D());

                // set paste as active manipulation
                Gui.getActiveScenePanel().setActiveManipulation(paste);

                // try finish manipulation
                Gui.getActiveScenePanel()
                        .tryFinishManipulation(null, null, Gui.getActiveScenePanel().getManipulationQueue(), false);
            }
            // DEL or BACKSPACE
            else if ((e.getKeyCode() == KeyEvent.VK_DELETE) || (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
                Manipulation manipulation = Gui.getActiveScenePanel().getActiveManipulation();
                if (manipulation.getManipulationType() == ManipulationType.SELECT) {
                    GroupNode selected = manipulation.getManipulatedGroup();

                    // prepare necessary data
                    Element el = selected.getChildrenElementList().getFirst().getElement();
                    Rectangle2D r2d = Support.createPointerRectangle(new Point2D.Double(el.getX().firstElement()
                            .doubleValue(), el.getY().firstElement().doubleValue()), GuiConfiguration.getInstance()
                            .getPointerRectangle());

                    // prepare manipulation
                    Manipulation m = ManipulationFactory.create(ManipulationType.DELETE, Gui.getActiveScenePanel()
                            .getSceneGraph().getTopNode(), e.getSource());
                    manipulation.setActive(false);

                    // execute manipulation
                    Gui.getActiveScenePanel().setActiveManipulation(m);
                    m.manipulationStart(null, r2d, null, Gui.getActiveScenePanel().getZoomFactor(), true, Gui
                            .getActiveGraphics2D());
                    m.manipulationStop(null, r2d, null, Gui.getActiveScenePanel().getZoomFactor(), true, Gui
                            .getActiveGraphics2D());
                    Gui.getActiveScenePanel().getManipulationQueue().execute(m);

                    // redraw scheme and update scene graph
                    Gui.getActiveScenePanel().sceneInvalidate(null);
                    Gui.getActiveScenePanel().getSceneGraph().fireSceneGraphUpdateEvent();

                    // prepare previous manipulation
                    manipulation.setManipulatedGroup(null);
                    Gui.getActiveScenePanel().setActiveManipulation(ManipulationFactory.duplicate(manipulation));
                }
            }
            // SPACE for relative coordinates start set
            else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                Gui.getActiveScenePanel().setRelativeStart();
                StatusBar.getInstance().refresh();
            }
            
            // R like Reload
            else if (e.getKeyChar() == 'r'){
            	Gui.getInstance().getSchemeScenePanel().repaint();
            }
        } catch (UnknownManipulationException ume) {
            logger.error(ume.getMessage());
        } catch (NullPointerException npe) {
            logger.debug("Probably no manipulation");
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
