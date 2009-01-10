package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
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
        if (Gui.getActiveScenePanel().getActiveManipulation().getManipulationType() == ManipulationType.SELECT) {
            if (Gui.getActiveScenePanel().getActiveManipulation().getManipulatedGroup() != null) {
                try {
                    Manipulation m = ManipulationFactory.create(ManipulationType.MIRROR, Gui
                            .getActiveScenePanel().getSceneGraph().getTopNode(), e.getSource());
                    m.setManipulatedGroup(Gui.getActiveScenePanel().getActiveManipulation()
                            .getManipulatedGroup());
                    m.addManipulationCoordinates(getMirrorAxe().getUnitX(), getMirrorAxe().getUnitY(), Gui
                            .getActiveScenePanel().getZoomFactor());
                    Gui.getActiveScenePanel().getManipulationQueue().execute(m);

                    Gui.getActiveScenePanel().schemeInvalidate(null);
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
