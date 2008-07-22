/**
 * 
 */
package cz.cvut.fel.schematicEditor.application.guiElements;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import cz.cvut.fel.schematicEditor.application.guiElements.listeners.scenePanelDrawingPopup.EndElementMenuItemListener;

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

    public static final JPopupMenu getScenePanelDrawingPopup() {
        if (scenePanelDrawingPopup == null) {
            scenePanelDrawingPopup = new ScenePanelDrawingPopup();
            // scenePanelDrawingPopup.add(scenePanelDrawingPopup.getAddPointMenuItem());
            scenePanelDrawingPopup.add(scenePanelDrawingPopup.getEndElementMenuItem());
        }
        return scenePanelDrawingPopup;
    }

    /**
     * @return
     */
    private JMenuItem getEndElementMenuItem() {
        if (endElementMenuItem == null) {
            endElementMenuItem = new JMenuItem();
            endElementMenuItem.setText(END_ELEMENT_TEXT);
            endElementMenuItem.addActionListener(new EndElementMenuItemListener());
        }
        return endElementMenuItem;
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
