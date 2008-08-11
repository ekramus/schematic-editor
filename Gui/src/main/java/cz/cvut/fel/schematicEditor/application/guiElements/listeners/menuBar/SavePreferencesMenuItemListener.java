package cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cz.cvut.fel.schematicEditor.application.guiElements.MenuBar;
import cz.cvut.fel.schematicEditor.properties.Configuration;

/**
 * This class implements {@link ActionListener} for <code>savePreferencesMenuItem</code> in {@link MenuBar}.
 * 
 * @author Urban Kravjansky
 */
public final class SavePreferencesMenuItemListener implements ActionListener {

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public SavePreferencesMenuItemListener() {
        super();
    }

    // FIXME add externalized strings

    /**
     * Method invoked as result to an action. It exports global properties.
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param e {@link ActionEvent} parameter. This parameter is only for implementing purposes, it is not used nor
     *            needed.
     */
    public void actionPerformed(ActionEvent e) {
        FileOutputStream fos;

        // Properties p = Structures.getProperties();
        // p.setProperty("sceneXDim", "1280");
        // p.setProperty("sceneYDim", "1024");
        // p.setProperty("sceneColor", String.valueOf(Color.WHITE));
        // p.setProperty("isGridVisible", String.valueOf(true));
        // p.setProperty("gridSize", "25");
        // p.setProperty("isSchemeAntialiased", String.valueOf(true));
        // p.setProperty("isSchemeDebugged", String.valueOf(false));

        try {
            fos = new FileOutputStream(Configuration.GLOBAL_PROPERTIES);
            Configuration.getInstance().getProperties().storeToXML(fos, String.valueOf(System.currentTimeMillis()));
        } catch (FileNotFoundException fnfe) {
            // TODO Auto-generated catch block
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            // TODO Auto-generated catch block
            ioe.printStackTrace();
        }
    }
}
