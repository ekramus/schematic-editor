package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.guiAdvanced.listeners;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.drawingToolBar.DrawingToolBar;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.guiAdvanced.GuiAdvanced;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;

/**
 * This class implements change listener for Scene TabbedPane.
 *
 * @author Urban Kravjansky
 *
 */
public class SceneTabbedPaneChangeListener implements ChangeListener {

    /**
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */
    public void stateChanged(ChangeEvent e) {
        GuiAdvanced.getActiveScenePanel().schemeInvalidate(null);
        MenuBar.getInstance().refresh();
        DrawingToolBar.getInstance().refresh();
    }

}
