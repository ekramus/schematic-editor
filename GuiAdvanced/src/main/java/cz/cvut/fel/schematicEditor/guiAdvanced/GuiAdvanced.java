package cz.cvut.fel.schematicEditor.guiAdvanced;

import java.awt.BorderLayout;
import java.awt.Container;
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
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.core.Plugin;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.core.coreStructures.sceneGraph.SceneGraphUpdateListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.drawingToolBar.DrawingToolBar;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesToolBar.PropertiesToolBar;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanel.ScenePanel;

/**
 * This class represents GUI of schematic editor.
 *
 * @author Urban Kravjansky
 */
public class GuiAdvanced extends JApplet {
    /**
     * This field contains class UID for serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Logger instance for logging purposes.
     */
    private static Logger     logger;
    /**
     * {@link JPanel} for applet.
     */
    private JPanel            appletPanel      = null;
    /**
     * Main frame.
     */
    private JFrame            applicationFrame = null;
    /**
     * JPanel containing scene.
     */
    private JPanel            sceneRootPanel   = null;
    /**
     *
     */
    private JScrollPane       sceneJScrollPane = null;

    /**
     * Default singleton constructor.
     */
    public GuiAdvanced() {
        logger = Logger.getLogger(this.getClass());
    }

    /**
     * This method initializes appletPanel. This method is implemented for the purpose of applet launching.
     *
     * @return javax.swing.JPanel
     */
    public JPanel getAppletPanel() {
        if (this.appletPanel == null) {
            this.appletPanel = new JPanel();

            // finalize appletPanel initialization
            initRootComponent(this.appletPanel);

            // initialize plugins
            try {
                initializePlugins(MenuBar.getInstance().getPluginsMenu(), DrawingToolBar.getInstance());
            } catch (NullPointerException npe) {
                logger.error("Error while loading plugins");
            }
        }
        return this.appletPanel;
    }

    /**
     * Initializes main application {@link JFrame}.
     *
     * @return javax.swing.JFrame
     */
    public JFrame getApplicationFrame() {
        if (this.applicationFrame == null) {
            this.applicationFrame = new JFrame();

            // set parameters of JFrame
            this.applicationFrame.setTitle("Application");
            this.applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // finalize jFrame initialization
            initRootComponent(this.applicationFrame);

            // initialize plugins
            try {
                initializePlugins(MenuBar.getInstance().getPluginsMenu(), DrawingToolBar.getInstance());
            } catch (NullPointerException npe) {
                logger.error("Error while loading plugins");
            }
        }
        return this.applicationFrame;
    }

    /**
     * Initializes container with prepared components, so both jFrame for standalone application and appletPanel for
     * applet application looks same.
     *
     * @param container root container for specified application.
     */
    private void initRootComponent(Container container) {
        // set container parameters
        container.setSize(800, 600);
        container.setLayout(new BorderLayout());

        // prepare scene tabbed pane
        JTabbedPane sceneTabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        sceneTabbedPane.add(getSceneRootPanel(), "scheme");
        sceneTabbedPane.add(new JPanel(), "part");

        // add components
        container.add(MenuBar.getInstance(), BorderLayout.NORTH);
        container.add(DrawingToolBar.getInstance(), BorderLayout.WEST);
        container.add(PropertiesToolBar.getInstance(), BorderLayout.EAST);
        container.add(StatusBar.getInstance(), BorderLayout.SOUTH);
        container.add(sceneTabbedPane, BorderLayout.CENTER);
    }

    /**
     * This method initializes sceneRootPanel
     *
     * @return javax.swing.JPanel
     */
    private JPanel getSceneRootPanel() {
        if (this.sceneRootPanel == null) {
            this.sceneRootPanel = new JPanel();
            this.sceneRootPanel.setLayout(new BorderLayout());
            this.sceneRootPanel.add(getSceneJScrollPane(), BorderLayout.CENTER);
        }
        return this.sceneRootPanel;
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

                    if (plugin.implementsSceneGraphUpdateListener()) {
                        SceneGraph.getInstance().addSceneGraphUpdateListener((SceneGraphUpdateListener) plugin);
                    }

                    logger.trace("plugin loaded");
                } catch (NullPointerException npe) {
                    logger.error("plugin " + pluginPath + " was not loaded!");
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
                String pfn = file.getName();
                if (pfn.substring(pfn.length() - 3, pfn.length()).equalsIgnoreCase("jar")) {
                    logger.trace("plugin " + fileName + " added");
                    result.add(directory + File.separator + fileName);
                }
            }
        }

        return result;
    }
}
