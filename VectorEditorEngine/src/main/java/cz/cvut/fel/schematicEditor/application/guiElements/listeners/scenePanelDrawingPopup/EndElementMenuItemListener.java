package cz.cvut.fel.schematicEditor.application.guiElements.listeners.scenePanelDrawingPopup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cz.cvut.fel.schematicEditor.application.guiElements.ScenePanelDrawingPopup;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.manipulation.Create;

/**
 * This class implements listener for {@link ScenePanelDrawingPopup}.
 *
 * @author Urban Kravjansky
 */
public class EndElementMenuItemListener implements ActionListener {

    /**
     * Default constructor.
     */
    public EndElementMenuItemListener() {
        super();
    }

    /**
     * Method is invoked as result to an action. It invokes final manipulation step.
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param ae
     *            {@link ActionEvent} parameter. This parameter is only for implementing purposes,
     *            it is not used nor needed.
     */
    public final void actionPerformed(final ActionEvent ae) {
        Create create = (Create) Structures.getManipulation();
        create.setFinished(true);
        Structures.getScenePanel().processFinalManipulationStep();
    }

}
