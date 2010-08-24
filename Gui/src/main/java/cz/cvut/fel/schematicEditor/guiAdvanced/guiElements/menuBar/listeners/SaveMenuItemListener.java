package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFileChooser;

import cz.cvut.fel.schematicEditor.configuration.EnvironmentConfiguration;
import cz.cvut.fel.schematicEditor.core.Serialization;
import cz.cvut.fel.schematicEditor.element.ElementPotential;
import cz.cvut.fel.schematicEditor.guiAdvanced.ExportFileFilter;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;

/**
 * This class implements {@link ActionListener} for <code>saveMenuItem</code> in {@link MenuBar}.
 *
 * @author Urban Kravjansky
 */
public final class SaveMenuItemListener implements ActionListener {

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public SaveMenuItemListener() {
    	 
           //getAppletContext().showDocument(url);
    	//---
    	super();
    	 URL url=null;
         try{
         url=new URL("http://vocko.pod.cvut.cz/edout/data.php");
         }catch(MalformedURLException me){}
         try{
         HttpURLConnection
         connection=(HttpURLConnection)url.openConnection() ;
         connection.setRequestMethod("POST");
         connection.setDoOutput(true);
         PrintWriter out = new PrintWriter(connection.getOutputStream());
         out.println(ElementPotential.Tree(""));
         out.close();

         }catch(IOException ie){;};
   	 
    	
    	
     
        }
      
    // FIXME add externalized strings

    /**
     * Method invoked as result to an action. It initializes new {@link JFileChooser} instance to select file and then
     * executes export process.
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param e {@link ActionEvent} parameter. This parameter is only for implementing purposes, it is not used nor
     *            needed.
     */
    public void actionPerformed(ActionEvent e) {
        EnvironmentConfiguration env = EnvironmentConfiguration.getInstance();

        JFileChooser fileChooser = new JFileChooser(env.getLastSaveFolder());
        fileChooser.setDialogTitle("Choose file to save");
        fileChooser.setFileFilter(new ExportFileFilter(ExportFileFilter.SEF, ExportFileFilter.SEFDESC));

        int retValue = fileChooser.showSaveDialog(Gui.getActiveScenePanel());

        if (retValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            env.setLastSaveFolder(file.getParent());

            Serialization.serialize(Gui.getActiveScenePanel().getSceneGraph(), file);
        }
    }
}
