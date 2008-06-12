package cz.cvut.fel.schematicEditor.application.guiElements.listeners.drawingToolBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.manipulation.Delete;

/**
 * This class implements listener for {@link Delete} manipulation button.
 *
 * @author Urban Kravjansky
 */
public final class DeleteButtonListener implements ActionListener {

    /**
     * The default constructor.
     */
    public DeleteButtonListener() {
        super();
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
        Structures.setManipulation(new Delete());
    }

}
