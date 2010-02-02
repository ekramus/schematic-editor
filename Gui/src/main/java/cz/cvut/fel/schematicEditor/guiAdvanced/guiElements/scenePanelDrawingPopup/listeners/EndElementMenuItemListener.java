package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanelDrawingPopup.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanelDrawingPopup.ScenePanelDrawingPopup;
import cz.cvut.fel.schematicEditor.manipulation.Create;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;

/**
 * This class implements listener for {@link ScenePanelDrawingPopup}.
 *
 * @author Urban Kravjansky
 */
public class EndElementMenuItemListener implements ActionListener {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger      logger;
    /**
     * {@link Rectangle2D} around actual pointer.
     */
    private Rectangle2D.Double r2d;
    /**
     * {@link MouseEvent} before this menu.
     */
    private MouseEvent         e;

    /**
     * Default MenuItem listener constructor.
     *
     * @param e {@link MouseEvent}, which preceded this {@link Listener} construction.
     * @param r2d Pointer square, where {@link MouseEvent} occured.
     */
    public EndElementMenuItemListener(MouseEvent e, Rectangle2D.Double r2d) {
        super();
        logger = Logger.getLogger(Gui.class.getName());
        setR2d(r2d);
        setE(e);
    }

    /**
     * Method is invoked as result to an action. It invokes final manipulation step.
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param ae {@link ActionEvent} parameter. This parameter is only for implementing purposes, it is not used nor
     *            needed.
     */
    public final void actionPerformed(final ActionEvent ae) {
        try {
         
            Create create = (Create) Gui.getActiveScenePanel().getActiveManipulation();
            create.setFinished(true);
            
            Gui.getActiveScenePanel().tryFinishManipulation(
                                                                    getE(),
                                                                    getR2d(),
                                                                    Gui.getActiveScenePanel()
                                                                            .getManipulationQueue(), false);
        } catch (UnknownManipulationException e) {
            logger.error(e.getMessage());
        }
        
        
    	Gui.getActiveScenePanel().getManipulationQueue().getActiveManipulation().setActive(false);
    	//Gui.getActiveScenePanel().getManipulationQueue().unexecute();

    }

    /**
     * @return the r2d
     */
    private Rectangle2D.Double getR2d() {
        return this.r2d;
    }

    /**
     * @param r2d the r2d to set
     */
    private void setR2d(Rectangle2D.Double r2d) {
        this.r2d = r2d;
    }

    /**
     * @return the e
     */
    private MouseEvent getE() {
        return this.e;
    }

    /**
     * @param e the e to set
     */
    private void setE(MouseEvent e) {
        this.e = e;
    }

}
