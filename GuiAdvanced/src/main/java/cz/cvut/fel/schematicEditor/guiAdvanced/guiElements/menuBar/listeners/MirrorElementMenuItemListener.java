package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.guiAdvanced.GuiAdvanced;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

/**
 * This class implements {@link ActionListener} for <code>mirrorMenuItem</code> in {@link MenuBar}.
 *
 * @author Urban Kravjansky
 */
public class MirrorElementMenuItemListener implements ActionListener {
    /**
     * Mirroring axe.
     */
    private UnitPoint mirrorAxe;

    /**
     * This method instantiates new instance.
     *
     * @param mirrorAxe {@link UnitPoint} specifying where mirror axe will be.
     */
    public MirrorElementMenuItemListener(UnitPoint mirrorAxe) {
        setMirrorAxe(mirrorAxe);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        if (GuiAdvanced.getActiveScenePanel().getActiveManipulation().getManipulationType() == ManipulationType.SELECT) {
            if (GuiAdvanced.getActiveScenePanel().getActiveManipulation().getManipulatedGroup() != null) {
                try {
                    Manipulation m = ManipulationFactory.create(ManipulationType.MIRROR, GuiAdvanced
                            .getActiveScenePanel().getSceneGraph().getTopNode(), e.getSource());
                    m.setManipulatedGroup(GuiAdvanced.getActiveScenePanel().getActiveManipulation()
                            .getManipulatedGroup());
                    m.addManipulationCoordinates(getMirrorAxe().getUnitX(), getMirrorAxe().getUnitY(), GuiAdvanced
                            .getActiveScenePanel().getZoomFactor());
                    GuiAdvanced.getActiveScenePanel().getManipulationQueue().execute(m);

                    GuiAdvanced.getActiveScenePanel().schemeInvalidate(null);
                } catch (UnknownManipulationException ume) {
                    ume.printStackTrace();
                }
            }
        }
    }

    /**
     * @param mirrorAxe the mirrorAxe to set
     */
    private void setMirrorAxe(UnitPoint mirrorAxe) {
        this.mirrorAxe = mirrorAxe;
    }

    /**
     * @return the mirrorAxe
     */
    private UnitPoint getMirrorAxe() {
        return this.mirrorAxe;
    }
}
