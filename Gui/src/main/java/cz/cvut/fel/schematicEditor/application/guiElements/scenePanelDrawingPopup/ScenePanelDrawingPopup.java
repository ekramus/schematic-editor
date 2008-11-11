/**
 * 
 */
package cz.cvut.fel.schematicEditor.application.guiElements.scenePanelDrawingPopup;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import cz.cvut.fel.schematicEditor.application.guiElements.scenePanelDrawingPopup.listeners.EndElementMenuItemListener;

/**
 * @author uk
 */
public class ScenePanelDrawingPopup extends JPopupMenu {
    private static ScenePanelDrawingPopup scenePanelDrawingPopup = null;

    private static final String           ADD_POINT_TEXT         = "add point";
    private static final String           END_ELEMENT_TEXT       = "end element";

    private JMenuItem                     addPointMenuItem       = null;
    private JMenuItem                     endElementMenuItem     = null;

    private ScenePanelDrawingPopup() {
        // nothing to do
    }

    public static final JPopupMenu getScenePanelDrawingPopup(MouseEvent e, Rectangle2D.Double r2d) {
        if (scenePanelDrawingPopup == null) {
            scenePanelDrawingPopup = new ScenePanelDrawingPopup();
        }
        scenePanelDrawingPopup.add(scenePanelDrawingPopup.getEndElementMenuItem(e, r2d));
        return scenePanelDrawingPopup;
    }

    /**
     * @return
     */
    private JMenuItem getEndElementMenuItem(MouseEvent e, Rectangle2D.Double r2d) {
        if (this.endElementMenuItem == null) {
            this.endElementMenuItem = new JMenuItem();
            this.endElementMenuItem.setText(END_ELEMENT_TEXT);
        }
        this.endElementMenuItem.addActionListener(new EndElementMenuItemListener(e, r2d));
        return this.endElementMenuItem;
    }

    /**
     * @return
     */
    private JMenuItem getAddPointMenuItem() {
        if (addPointMenuItem == null) {
            addPointMenuItem = new JMenuItem();
            addPointMenuItem.setText(ADD_POINT_TEXT);
        }
        return addPointMenuItem;
    }
}
