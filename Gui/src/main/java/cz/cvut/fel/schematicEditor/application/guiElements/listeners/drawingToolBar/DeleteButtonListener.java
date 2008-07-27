package cz.cvut.fel.schematicEditor.application.guiElements.listeners.drawingToolBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.application.Gui;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.manipulation.manipulation.Delete;
import cz.cvut.fel.schematicEditor.manipulation.manipulation.ManipulationFactory;

/**
 * This class implements listener for {@link Delete} manipulation button.
 *
 * @author Urban Kravjansky
 */
public final class DeleteButtonListener implements ActionListener {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;

    /**
     * The default constructor.
     */
    public DeleteButtonListener() {
        super();

        logger = Logger.getLogger(Gui.class.getName());
    }

    /**
     * Method is invoked as result to an action. It initializes new {@link Delete} instance.
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param ae
     *            {@link ActionEvent} parameter. This parameter is only for implementing purposes,
     *            it is not used nor needed.
     */
    public void actionPerformed(final ActionEvent ae) {
        try {
            Structures.getManipulationQueue().offer(ManipulationFactory.create(ManipulationType.DELETE));
        } catch (UnknownManipulationException ume) {
            logger.error(ume.getMessage());
        }
    }

}