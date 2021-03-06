package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
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

import cz.cvut.fel.schematicEditor.configuration.GuiConfiguration;
import cz.cvut.fel.schematicEditor.core.Plugin;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.core.coreStructures.SceneTabbedPaneTabs;
import cz.cvut.fel.schematicEditor.core.coreStructures.sceneGraph.SceneGraphUpdateListener;
import cz.cvut.fel.schematicEditor.element.ElementPotential;
import cz.cvut.fel.schematicEditor.guiAdvanced.StatusBar;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.drawingToolBar.DrawingToolBar;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.listeners.SceneTabbedPaneChangeListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.PropertiesSelectorToolBar;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanel.ScenePanel;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;

/**
 * This class represents GUI of schematic editor.
 *
 * @author Urban Kravjansky
 */
public class Gui extends JApplet {
    /**
     * This field contains class UID for serialization.
     */
    private static final long serialVersionUID     = 1L;
    /**
     * Singleton instance field.
     */
    private static Gui        instance             = null;
    /**
     * Logger instance for logging purposes.
     */
    private static Logger     logger;
    /**
     * {@link JPanel} for applet.
     */
    private JPanel            appletPanel          = null;
    /**
     * Main frame.
     */
    private JFrame            applicationFrame     = null;
    /**
     * JPanel containing scene.
     */
    private JPanel            schemePartRootPanel  = null;
    /**
     * Scroll pane containing scheme scene.
     */
    private JScrollPane       schemeJScrollPane    = null;
    /**
     * JPanel containing part.
     */
    private JPanel            partRootPanel        = null;
    /**
     * Scroll pane containing part scene.
     */
    private JScrollPane       partJScrollPane      = null;
    /**
     * Scheme {@link ScenePanel} instance.
     */
    private ScenePanel        schemeScenePanel     = null;
    /**
     * Part {@link ScenePanel} instance.
     */
    private ScenePanel        partScenePanel       = null;
    /**
     * Scene {@link JTabbedPane} instance.
     */
    private JTabbedPane       schemePartTabbedPane = null;

    private static Manipulation doAfter = null;
    
    public static ElementPotential voltageLevel = null;
    
    /**
     * Default singleton constructor.
     */
    private Gui() {
        logger = Logger.getLogger(this.getClass());
        voltageLevel = new ElementPotential();
    }

    /**
     * Singleton getter for {@link Gui} instance.
     *
     * @return Instance of {@link Gui}.
     */
    public static Gui getInstance() {
        if (instance == null) {
            instance = new Gui();
        }
        return instance;
    }

    /**
     * Resets {@link Gui} instance to <code>null</code>.
     */
    public static void resetInstance() {
        instance = null;
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
                initializePlugins(getScenePanelCollection(), MenuBar.getInstance().getPluginsMenu(), DrawingToolBar
                        .getInstance());
            } catch (NullPointerException npe) {
                logger.error("Error while loading plugins");
            }
        }
        return this.appletPanel;
    }

    /**
     * Getter for collection of scene panels.
     *
     * @return {@link Collection} of {@link ScenePanel}s.
     */
    private Collection<ScenePanel> getScenePanelCollection() {
        Vector<ScenePanel> result = new Vector<ScenePanel>();

        result.add(getInstance().getSchemeScenePanel());
        result.add(getInstance().getPartScenePanel());

        return result;
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
            // TODO initialize all ScenePanels
            try {
                initializePlugins(getScenePanelCollection(), MenuBar.getInstance().getPluginsMenu(), DrawingToolBar
                        .getInstance());
            } catch (NullPointerException npe) {
                logger.error("Error while loading plugins");
            }

            this.refresh();
        }
        return this.applicationFrame;
    }

    /**
     * Refresh gui components.
     */
    public void refresh() {
        MenuBar.getInstance().refresh();
        DrawingToolBar.getInstance().refresh();
        getActiveScenePanel().repaint();
    }

    /**
     * Initializes container with prepared components, so both jFrame for standalone application and appletPanel for
     * applet application looks same.
     *
     * @param container root container for specified application.
     */
    private void initRootComponent(Container container) {
        // set container parameters
        container.setMinimumSize(new Dimension(700, 700));
        container.setLayout(new BorderLayout());

        // add components
        container.add(MenuBar.getInstance(), BorderLayout.NORTH);
        container.add(DrawingToolBar.getInstance(), BorderLayout.EAST);
        //container.add(DrawingToolBar.getInstance(), BorderLayout.WEST);
        
        container.add(PropertiesSelectorToolBar.getInstance(), BorderLayout.WEST);
        container.add(StatusBar.getInstance(), BorderLayout.SOUTH);
        container.add(getSchemePartTabbedPane(), BorderLayout.CENTER);
    }

    /**
     * @return the schemePartTabbedPane
     */
    private JTabbedPane getSchemePartTabbedPane() {
        if (this.schemePartTabbedPane == null) {
            this.schemePartTabbedPane = new JTabbedPane(SwingConstants.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
            this.schemePartTabbedPane.addChangeListener(new SceneTabbedPaneChangeListener());
            this.schemePartTabbedPane.addTab("scheme", getSchemePartRootPanel());
            this.schemePartTabbedPane.addTab("part", getPartRootPanel());
        }
        return this.schemePartTabbedPane;
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
     * This method initializes schemePartRootPanel
     *
     * @return javax.swing.JPanel
     */
    private JPanel getSchemePartRootPanel() {
        if (this.schemePartRootPanel == null) {
            this.schemePartRootPanel = new JPanel();
            this.schemePartRootPanel.setLayout(new BorderLayout());
            this.schemePartRootPanel.add(getSchemeJScrollPane(), BorderLayout.CENTER);
        }
        return this.schemePartRootPanel;
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
     * This method initializes schemeJScrollPane
     *
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getSchemeJScrollPane() {
        if (this.schemeJScrollPane == null) {
            this.schemeJScrollPane = new JScrollPane();

            this.schemeJScrollPane.setViewportView(getSchemeScenePanel());
        }
        return this.schemeJScrollPane;
    }

    /**
     * Getter for <code>schemeScenePanel</code>.
     *
     * @return {@link ScenePanel} instance.
     */
    public ScenePanel getSchemeScenePanel() {
        if (this.schemeScenePanel == null) {
            GuiConfiguration config = GuiConfiguration.getInstance();

            this.schemeScenePanel = new ScenePanel(config.getSchemeDim());
            this.schemeScenePanel.setZoomFactor(config.getSchemeZoomFactor());
        }
        return this.schemeScenePanel;
    }

    /**
     * Getter for <code>partScenePanel</code>.
     *
     * @return {@link ScenePanel} instance.
     */
    public ScenePanel getPartScenePanel() {
        if (this.partScenePanel == null) {
            GuiConfiguration config = GuiConfiguration.getInstance();

            this.partScenePanel = new ScenePanel(config.getPartDim());
            this.partScenePanel.setZoomFactor(config.getPartZoomFactor());
        }
        return this.partScenePanel;
    }

    /**
     * Getter for active <code>ScenePanel</code>.
     *
     * @return {@link ScenePanel} instance.
     */
    public static ScenePanel getActiveScenePanel() {
        if (getActiveScenePanelTab() == SceneTabbedPaneTabs.TAB_SCHEME) {
            return getInstance().getSchemeScenePanel();
        }
        return getInstance().getPartScenePanel();
    }

    /**
     * Getter for active <code>Graphics2D</code>.
     *
     * @return active {@link Graphics2D} context.
     */
    public static Graphics2D getActiveGraphics2D() {
        return (Graphics2D) getActiveScenePanel().getGraphics();
    }

    /**
     * Getter for active scene tab.
     *
     * @return {@link SceneTabbedPaneTabs} value.
     */
    public static SceneTabbedPaneTabs getActiveScenePanelTab() {
        for (SceneTabbedPaneTabs sceneTab : SceneTabbedPaneTabs.values()) {
            if (sceneTab.getOrder() == getInstance().getSchemePartTabbedPane().getSelectedIndex()) {
                return sceneTab;
            }
        }
        return null;
    }

    /**
     * Getter for preferred scene panel.
     *
     * @param sceneTab tab to look for scene panel.
     * @return instance of requested scene panel.
     */
    private ScenePanel getScenePanel(SceneTabbedPaneTabs sceneTab) {
        if (sceneTab == SceneTabbedPaneTabs.TAB_SCHEME) {
            return getInstance().getSchemeScenePanel();
        }
        return getInstance().getPartScenePanel();
    }

    /**
     * Initializes all plugins found in plugin folder.
     *
     * @param scenePanelCollection collection of {@link ScenePanel}s, which will be accessible to plugins.
     * @param pluginsMenu plugins menu used for displaying plugin menu items.
     * @param drawingToolBar tool bar for drawing buttons.
     */
    private void initializePlugins(Collection<ScenePanel> scenePanelCollection, JMenu pluginsMenu,
            JToolBar drawingToolBar) {
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

                    // register plugin via its properties
                    Structures.getLoadedPluginProperties().add(pluginProperties);

                    for (SceneTabbedPaneTabs sceneTab : SceneTabbedPaneTabs.values()) {
                        // create plugin for each one scene panel
                        Plugin plugin = (Plugin) pluginClass.newInstance();

                        SceneGraph sg = getInstance().getScenePanel(sceneTab).getSceneGraph();

                        // activate plugin
                        plugin.activate(sg);

                        // add menu item if applicable
                        if (plugin.providesMenuItem()) {
                            Structures.getPluginMenuItems().get(sceneTab).add(plugin.getMenuItem());
                        }

                        // TODO add drawing toolbar item if applicable

                        // if plugin implements scene graph update listener, register it
                        if (plugin.implementsSceneGraphUpdateListener()) {
                            sg.addSceneGraphUpdateListener((SceneGraphUpdateListener) plugin);
                        }
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
    
    public static Manipulation getDoAfter() {
    	return Gui.doAfter;
    }
    
    public static boolean isDoAfterActive(){
    	return (Gui.doAfter == null);
    }
    
    public static void clearDoAfter(){
    	Gui.doAfter = null;
    }
    
    public static void setDoAfter(Manipulation inserted){
    	Gui.doAfter = inserted;
    	Gui.getActiveScenePanel().getManipulationQueue().execute(Gui.doAfter);
    	Gui.getActiveScenePanel().sceneInvalidate(null);
    	
    }
}
