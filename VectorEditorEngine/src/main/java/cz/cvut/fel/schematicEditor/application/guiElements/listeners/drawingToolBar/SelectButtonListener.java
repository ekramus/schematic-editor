package cz.cvut.fel.schematicEditor.application.guiElements.listeners.drawingToolBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.manipulation.Select;

/**
 * This class implements listener for {@link Select} manipulation button.
 * 
 * @author Urban Kravjansky
 */
public final class SelectButtonListener implements ActionListener {

    /**
     * The default constructor.
     */
    public SelectButtonListener() {
        super();
    }

    /**
     * Method is invoked as result to an action. It initializes new {@link Select} instance.
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * 
     * @param ae
     *            {@link ActionEvent} parameter. This parameter is only for implementing purposes,
     *            it is not used nor needed.
     */
    public void actionPerformed(final ActionEvent ae) {
        Structures.setManipulation(new Select());
    }

}
