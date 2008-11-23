package cz.cvut.fel.schematicEditor.application;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.application.guiElements.drawingToolBar.DrawingToolBar;
import cz.cvut.fel.schematicEditor.application.guiElements.menuBar.MenuBar;
import cz.cvut.fel.schematicEditor.application.guiElements.propertiesToolBar.PropertiesToolBar;
import cz.cvut.fel.schematicEditor.application.guiElements.scenePanel.ScenePanel;
import cz.cvut.fel.schematicEditor.core.Plugin;
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
    private static final long serialVersionUID  = 1L;

    /**
     * Logger instance for logging purposes.
     */
    private static Logger     logger;
    /**
     * Content pane for application.
     */
    private JPanel            jContentPane      = null;
    /**
     * Main frame.
     */
    private JFrame            jFrame            = null;
    /**
     * JPanel containing scene.
     */
    private JPanel            sceneJContentPane = null;
    /**
     *
     */
    private JScrollPane       sceneJScrollPane  = null;

    /**
     * Default singleton constructor.
     */
    public Gui() {
        logger = Logger.getLogger(this.getClass());
    }

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
            this.jFrame.setSize(800, 600);
            this.jFrame.setContentPane(getSceneJContentPane());
            this.jFrame.setTitle("Application");

            initializePlugins(MenuBar.getInstance().getPluginsMenu(), DrawingToolBar.getInstance());
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
            this.sceneJContentPane.add(StatusBar.getInstance(), BorderLayout.SOUTH);
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

            this.sceneJScrollPane.setViewportView(ScenePanel.getInstance());
        }
        return this.sceneJScrollPane;
    }

    /**
     * Initializes all plugins found in plugin folder.
     *
     * @param pluginsMenu plugins menu used for displaying plugin menu items.
     * @param drawingToolBar tool bar for drawing buttons.
     * @param sceneGraph scene graph for access to scene elements.
     */
    private void initializePlugins(JMenu pluginsMenu, JToolBar drawingToolBar) {
        Collection<String> pluginsCollection = findPlugins("plugins");

        for (String pluginPath : pluginsCollection) {
            try {
                URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[] { new File(pluginPath).toURI()
                        .toURL() });
                try {
                    // create and load plugin properties
                    Properties pluginProperties = new Properties();
                    pluginProperties.loadFromXML(urlClassLoader.getResourceAsStream("META-INF/plugin.xml"));

                    // instantiate plugin object based on information in plugin xml
                    Class<?> pluginClass = urlClassLoader.loadClass(pluginProperties.getProperty("classpath") + "."
                            + pluginProperties.getProperty("classname"));
                    Plugin plugin = (Plugin) pluginClass.newInstance();

                    // register plugin via its properties
                    Structures.getLoadedPluginProperties().add(pluginProperties);

                    // activate plugin
                    plugin.activate(pluginsMenu, drawingToolBar);

                    logger.trace("plugin loaded");
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InvalidPropertiesFormatException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Finds all available plugins in given directory and returns {@link Collection} of paths.
     *
     * @param directory directory, in which search for plugins will be conducted.
     * @return {@link Collection} of paths to available plugins.
     */
    private final Collection<String> findPlugins(String directory) {
        Vector<String> result = new Vector<String>();

        File pluginsDirectory = new File(directory);
        for (String fileName : pluginsDirectory.list()) {
            File file = new File(directory + File.separator + fileName);
            if (file.isDirectory()) {
                logger.trace("folder " + fileName + " will be examined");
                result.addAll(findPlugins(directory + File.separator + fileName));
            } else if (file.isFile()) {
                logger.trace("plugin " + fileName + " added");
                result.add(directory + File.separator + fileName);
            }
        }

        return result;
    }
}
