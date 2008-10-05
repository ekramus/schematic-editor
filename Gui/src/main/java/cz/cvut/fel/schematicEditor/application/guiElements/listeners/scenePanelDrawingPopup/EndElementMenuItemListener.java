package cz.cvut.fel.schematicEditor.application.guiElements.listeners.scenePanelDrawingPopup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.application.Gui;
import cz.cvut.fel.schematicEditor.application.guiElements.ScenePanel;
import cz.cvut.fel.schematicEditor.application.guiElements.ScenePanelDrawingPopup;
import cz.cvut.fel.schematicEditor.core.Structures;
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
            Create create = (Create) Structures.getActiveManipulation();
            create.setFinished(true);
            ScenePanel.getInstance().tryFinishManipulation(getE(), getR2d(), Structures.getManipulationQueue(), false);
        } catch (UnknownManipulationException e) {
            logger.error(e.getMessage());
        }
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
