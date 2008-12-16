package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.guiAdvanced.GuiAdvanced;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

/**
 * This class implements {@link ActionListener} for <code>viewPartPropertiesMenuItem</code> in {@link MenuBar}.
 *
 * @author Urban Kravjansky
 */
public class RotateElementMenuItemListener implements ActionListener {
    /**
     * Rotation orientation.
     */
    private UnitPoint orientation;

    /**
     * This method instantiates new instance.
     *
     * @param orientation orientation of rotation.
     */
    public RotateElementMenuItemListener(UnitPoint orientation) {
        super();

        setOrientation(orientation);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        if (Structures.getActiveManipulation().getManipulationType() == ManipulationType.SELECT) {
            if (Structures.getActiveManipulation().getManipulatedGroup() != null) {
                try {
                    Manipulation m = ManipulationFactory.create(ManipulationType.ROTATE, GuiAdvanced.getActiveScenePanel()
                            .getSceneGraph().getTopNode());
                    m.setManipulatedGroup(Structures.getActiveManipulation().getManipulatedGroup());
                    m.addManipulationCoordinates(getOrientation().getUnitX(), getOrientation().getUnitY());
                    Structures.getManipulationQueue().execute(m);

                    GuiAdvanced.getActiveScenePanel().schemeInvalidate(null);
                } catch (UnknownManipulationException ume) {
                    ume.printStackTrace();
                }
            }
        }
    }

    /**
     * @param orientation the orientation to set
     */
    private void setOrientation(UnitPoint orientation) {
        this.orientation = orientation;
    }

    /**
     * @return the orientation
     */
    private UnitPoint getOrientation() {
        return this.orientation;
    }
}
