package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesToolBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesToolBar.PropertiesSelectorToolBar;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesToolBar.PropertiesToolBar;

/**
 * {@link ActionListener} for part properties button.
 *
 * @author Urban Kravjansky
 *
 */
public class PropertiesSelectorToolBarButtonActionListener implements ActionListener {
    /**
     * Button, for which is this listener responsible.
     */
    private int button;

    /**
     * @return the button
     */
    private int getButton() {
        return this.button;
    }

    /**
     * @param button the button to set
     */
    private void setButton(int button) {
        this.button = button;
    }

    /**
     * This method instantiates new instance.
     *
     *@param button Button, for which is this {@link PropertiesSelectorToolBarButtonActionListener} responsible.
     */
    public PropertiesSelectorToolBarButtonActionListener(int button) {
        setButton(button);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {
        if (PropertiesSelectorToolBar.getSelectedButton() != getButton()) {
            PropertiesSelectorToolBar.setSelectedButton(getButton());
        } else {
            PropertiesSelectorToolBar.setSelectedButton(PropertiesSelectorToolBar.NONE);
        }

        PropertiesToolBar.getInstance().update();
    }

}
