package cz.cvut.fel.schematicEditor.application;

import java.awt.BorderLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import cz.cvut.fel.schematicEditor.application.guiElements.DrawingToolBar;
import cz.cvut.fel.schematicEditor.application.guiElements.MenuBar;
import cz.cvut.fel.schematicEditor.application.guiElements.PropertiesToolBar;
import cz.cvut.fel.schematicEditor.core.Constants;
import cz.cvut.fel.schematicEditor.core.Structures;

/**
 * This class represents GUI of schematic editor.
 *
 * @author Urban Kravjansky
 */
public class Gui extends JApplet {
    /**
     * This field contains class UID for serialization.
     */
    private static final long serialVersionUID = 1L;

    private static Logger     logger;

    private JPanel      jContentPane      = null;
    /**
     * Main frame.
     */
    private JFrame      jFrame            = null;
    /**
     * JPanel containing scene.
     */
    private JPanel      sceneJContentPane = null;
    /**
     *
     */
    private JScrollPane sceneJScrollPane  = null;

    /**
     * This method initializes jContentPane
     *
     * @return javax.swing.JPanel
     */
    public JPanel getJContentPane() {
        if (this.jContentPane == null) {
            this.jContentPane = new JPanel();
            this.jContentPane.setLayout(new BorderLayout());

            Gui application = new Gui();
            application.getJFrame().setVisible(true);
            this.jContentPane.add(application);
        }
        return this.jContentPane;
    }

    /**
     * This method initializes jFrame
     *
     * @return javax.swing.JFrame
     */
    public JFrame getJFrame() {
        if (this.jFrame == null) {
            this.jFrame = new JFrame();
            this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.jFrame.setJMenuBar(MenuBar.getInstance());
            this.jFrame.setSize(566, 387);
            this.jFrame.setContentPane(getSceneJContentPane());
            this.jFrame.setTitle("Application");
        }
        return this.jFrame;
    }

    /**
     * This method initializes sceneJContentPane
     *
     * @return javax.swing.JPanel
     */
    private JPanel getSceneJContentPane() {
        if (this.sceneJContentPane == null) {
            this.sceneJContentPane = new JPanel();
            this.sceneJContentPane.setLayout(new BorderLayout());
            this.sceneJContentPane.add(getSceneJScrollPane(), BorderLayout.CENTER);
            // sceneJPanel has to be initialized
            this.sceneJContentPane.add(DrawingToolBar.getInstance(), BorderLayout.WEST);
            this.sceneJContentPane.add(PropertiesToolBar.getInstance(), BorderLayout.EAST);
            this.sceneJContentPane.add(Structures.getStatusBar(), BorderLayout.SOUTH);
        }
        return this.sceneJContentPane;
    }

    /**
     * This method initializes sceneJScrollPane
     *
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getSceneJScrollPane() {
        if (this.sceneJScrollPane == null) {
            this.sceneJScrollPane = new JScrollPane();

            this.sceneJScrollPane.setViewportView(Structures.getScenePanel());
        }
        return this.sceneJScrollPane;
    }
}
