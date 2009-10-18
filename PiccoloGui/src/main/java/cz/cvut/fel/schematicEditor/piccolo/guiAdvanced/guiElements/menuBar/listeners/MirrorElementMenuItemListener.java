package cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.menuBar.MenuBar;
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
                    // create manipulation
                    Manipulation m = ManipulationFactory.create(ManipulationType.MIRROR, Gui.getActiveScenePanel()
                            .getSceneGraph().getTopNode(), e.getSource());

                    // create unit point
                    UnitPoint up = m.getScaledUnitPoint(getMirrorAxe().getX(), getMirrorAxe().getY(), Gui
                            .getActiveScenePanel().getZoomFactor());

                    // set up manipulation
                    m.setManipulatedGroup(Gui.getActiveScenePanel().getActiveManipulation().getManipulatedGroup());
                    m.addManipulationCoordinates(up.getUnitX(), up.getUnitY());

                    // execute manipualtion
                    Gui.getActiveScenePanel().getManipulationQueue().execute(m);

                    Gui.getActiveScenePanel().sceneInvalidate(null);
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
