package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.drawingToolBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.Select;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;

/**
 * This class implements listener for {@link Select} manipulation button.
 *
 * @author Urban Kravjansky
 */
public final class SelectButtonListener implements ActionListener {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;

    /**
     * The default constructor.
     */
    public SelectButtonListener() {
        super();

        logger = Logger.getLogger(Gui.class.getName());
    }

    /**
     * Method is invoked as result to an action. It initializes new {@link Select} instance.
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param ae {@link ActionEvent} parameter. This parameter is only for implementing purposes, it is not used nor
     *            needed.
     */
    public void actionPerformed(final ActionEvent ae) {
        try {
            Manipulation m = ManipulationFactory.create(ManipulationType.SELECT, Gui.getActiveScenePanel()
                    .getSceneGraph().getTopNode(), ae.getSource());
            Gui.getActiveScenePanel().setActiveManipulation(m);
            
        
            
        } catch (UnknownManipulationException ume) {
            logger.error(ume.getMessage());
        }
    }

}
