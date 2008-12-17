package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.guiAdvanced.GuiAdvanced;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.PartPropertiesPanel;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;

/**
 * This class implements listener for {@link PartPropertiesPanel} partRotationCenterButton.
 *
 * @author Urban Kravjansky
 */
public class PartRotationCenterButtonActionListener implements ActionListener {

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        if (GuiAdvanced.getActiveScenePanel().getActiveManipulation().getManipulationType() == ManipulationType.SELECT) {
            if (GuiAdvanced.getActiveScenePanel().getActiveManipulation().getManipulatedGroup() != null) {
                try {
                    Manipulation m = ManipulationFactory.create(ManipulationType.SELECT_ROTATION_CENTER, GuiAdvanced
                            .getActiveScenePanel().getSceneGraph().getTopNode());
                    m.setManipulatedGroup(GuiAdvanced.getActiveScenePanel().getActiveManipulation()
                            .getManipulatedGroup());
                    m.setActive(true);

                    GuiAdvanced.getActiveScenePanel().setActiveManipulation(m);
                    // Structures.getManipulationQueue().execute(m);
                    //
                    // ScenePanel.getInstance().schemeInvalidate(null);
                } catch (UnknownManipulationException ume) {
                    ume.printStackTrace();
                }
            }
        }
    }
}
