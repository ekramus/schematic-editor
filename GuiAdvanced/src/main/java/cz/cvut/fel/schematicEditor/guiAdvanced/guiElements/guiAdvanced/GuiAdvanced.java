package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.guiAdvanced;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
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
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.core.Plugin;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.core.coreStructures.sceneGraph.SceneGraphUpdateListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.StatusBar;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.drawingToolBar.DrawingToolBar;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.guiAdvanced.listeners.SceneTabbedPaneChangeListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.PropertiesPanel;
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
    private static final long  serialVersionUID = 1L;
    /**
     * Singleton instance field.
     */
    private static GuiAdvanced instance         = null;
    /**
     * Logger instance for logging purposes.
     */
    private static Logger      logger;
    /**
     * {@link JPanel} for applet.
     */
    private JPanel             appletPanel      = null;
    /**
     * Main frame.
     */
    private JFrame             applicationFrame = null;
    /**
     * JPanel containing scene.
     */
    private JPanel             sceneRootPanel   = null;
    /**
     *
     */
    private JScrollPane        sceneJScrollPane = null;
    /**
     * JPanel containing part.
     */
    private JPanel             partRootPanel    = null;
    /**
     * Scroll panle containing part scheme.
     */
    private JScrollPane        partJScrollPane  = null;
    /**
     * Scene {@link ScenePanel} instance.
     */
    private static ScenePanel  sceneScenePanel  = null;
    /**
     * Part {@link ScenePanel} instance.
     */
    private static ScenePanel  partScenePanel   = null;
    /**
     * Scene {@link JTabbedPane} instance.
     */
    private JTabbedPane        sceneTabbedPane  = null;

    /**
     * Default singleton constructor.
     */
    private GuiAdvanced() {
        logger = Logger.getLogger(this.getClass());
    }

    /**
     * Singleton getter for {@link GuiAdvanced} instance.
     *
     * @return Instance of {@link GuiAdvanced}.
     */
    public static GuiAdvanced getInstance() {
        if (instance == null) {
            instance = new GuiAdvanced();
        }
        return instance;
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
                initializePlugins(getActiveScenePanel().getSceneGraph(), MenuBar.getInstance().getPluginsMenu(),
                                  DrawingToolBar.getInstance());
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
            this.applicationFrame.setSize(new Dimension(800, 600));

            // finalize jFrame initialization
            initRootComponent(this.applicationFrame);

            // initialize plugins
            try {
                initializePlugins(getActiveScenePanel().getSceneGraph(), MenuBar.getInstance().getPluginsMenu(),
                                  DrawingToolBar.getInstance());
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
        container.setMinimumSize(new Dimension(1000, 800));
        container.setLayout(new BorderLayout());

        // add components
        container.add(MenuBar.getInstance(), BorderLayout.NORTH);
        container.add(DrawingToolBar.getInstance(), BorderLayout.WEST);
        container.add(PropertiesPanel.getInstance(), BorderLayout.EAST);
        container.add(StatusBar.getInstance(), BorderLayout.SOUTH);
        container.add(getSceneTabbedPane(), BorderLayout.CENTER);
    }

    /**
     * @return the sceneTabbedPane
     */
    private JTabbedPane getSceneTabbedPane() {
        if (this.sceneTabbedPane == null) {
            this.sceneTabbedPane = new JTabbedPane(SwingConstants.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
            this.sceneTabbedPane.addChangeListener(new SceneTabbedPaneChangeListener());
            this.sceneTabbedPane.addTab("scheme", getSceneRootPanel());
            this.sceneTabbedPane.addTab("part", getPartRootPanel());
        }
        return this.sceneTabbedPane;
    }

    /**
     * This method initializes partRootPanel
     *
     * @return javax.swing.JPanel
     */
    private JPanel getPartRootPanel() {
        if (this.partRootPanel == null) {
            this.partRootPanel = new JPanel();
            this.partRootPanel.setLayout(new BorderLayout());
            this.partRootPanel.add(getPartJScrollPane(), BorderLayout.CENTER);
        }
        return this.partRootPanel;
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
     * This method initializes panelJScrollPane
     *
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getPartJScrollPane() {
        if (this.partJScrollPane == null) {
            this.partJScrollPane = new JScrollPane();

            this.partJScrollPane.setViewportView(getPartScenePanel());
        }
        return this.partJScrollPane;
    }

    /**
     * This method initializes sceneJScrollPane
     *
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getSceneJScrollPane() {
        if (this.sceneJScrollPane == null) {
            this.sceneJScrollPane = new JScrollPane();

            this.sceneJScrollPane.setViewportView(getSceneScenePanel());
        }
        return this.sceneJScrollPane;
    }

    private ScenePanel getSceneScenePanel() {
        if (sceneScenePanel == null) {
            sceneScenePanel = new ScenePanel();
        }
        return sceneScenePanel;
    }

    private ScenePanel getPartScenePanel() {
        if (partScenePanel == null) {
            partScenePanel = new ScenePanel();
        }
        return partScenePanel;
    }

    public static ScenePanel getActiveScenePanel() {
        if (getInstance().getSceneTabbedPane().getSelectedIndex() == 0) {
            return getInstance().getSceneScenePanel();
        }
        return getInstance().getPartScenePanel();
    }

    /**
     * Initializes all plugins found in plugin folder.
     *
     * @param pluginsMenu plugins menu used for displaying plugin menu items.
     * @param drawingToolBar tool bar for drawing buttons.
     */
    private void initializePlugins(SceneGraph sg, JMenu pluginsMenu, JToolBar drawingToolBar) {
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
                    plugin.activate(sg, pluginsMenu, drawingToolBar);

                    if (plugin.implementsSceneGraphUpdateListener()) {
                        getActiveScenePanel().getSceneGraph()
                                .addSceneGraphUpdateListener((SceneGraphUpdateListener) plugin);
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
