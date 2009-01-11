package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar;

import java.awt.Event;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import cz.cvut.fel.schematicEditor.configuration.GuiConfiguration;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners.AboutMenuItemListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners.AddPartMenuItemListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners.AntialiasedCheckBoxMenuItemListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners.DebugCheckBoxMenuItemListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners.DoneEditPartMenuItemListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners.EditPartMenuItemListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners.ExitMenuItemListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners.GridMenuItemListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners.ImportMenuItemListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners.MirrorElementMenuItemListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners.NewPartMenuItemListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners.NewSchemeMenuItemListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners.OpenMenuItemListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners.RotateElementMenuItemListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners.SaveAsMenuItemListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners.SaveAsPartMenuItemListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners.SaveMenuItemListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners.SavePreferencesMenuItemListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners.ScaleMenuItemListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners.ShowGridCheckBoxMenuItemListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners.SnapToGridCheckBoxMenuItemListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners.ViewPartPropertiesMenuItemListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.resources.MenuBarResources;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

/**
 * This class implements menu bar. It is used for all menu based operations.
 *
 * @author Urban Kravjanský
 */
public final class MenuBar extends JMenuBar {
    /**
     * {@link MenuBar} instance.
     */
    private static MenuBar menuBar = null;

    /**
     * Getter of <code>menuBar</code> instance. As it is singleton, this method first checks whether it is null or not.
     * When needed, new <code>menuBar</code> instance is created.
     *
     * @return <code>menuBar</code> instance.
     */
    public static MenuBar getInstance() {
        if (menuBar == null) {
            menuBar = new MenuBar();
            menuBar.add(menuBar.getFileMenu());
            menuBar.add(menuBar.getEditMenu());
            menuBar.add(menuBar.getViewMenu());
            menuBar.add(menuBar.getPluginsMenu());
            menuBar.add(menuBar.getHHelpMenu());
            // menuBar.add(getExportMenu());
        }
        return menuBar;
    }

    /**
     * About menu item instance.
     */
    private JMenuItem         aboutMenuItem               = null;
    /**
     * Add menu item instance.
     */
    private JMenu             addMenu                     = null;
    /**
     * Add part menu item instance.
     */
    private JMenuItem         addPartMenuItem             = null;
    /**
     * Antialiased picture check box menu item instance.
     */
    private JCheckBoxMenuItem antialiasedCheckBoxMenuItem = null;
    /**
     * Copy menu item instance.
     */
    private JMenuItem         copyMenuItem                = null;
    /**
     * Cut menu item instance.
     */
    private JMenuItem         cutMenuItem                 = null;
    /**
     * Debug check box menu item instance.
     */
    private JCheckBoxMenuItem debugCheckBoxMenuItem       = null;
    /**
     * Edit menu instance.
     */
    private JMenu             editMenu                    = null;
    /**
     * Exit menu item instance.
     */
    private JMenuItem         exitMenuItem                = null;
    /**
     * File menu instance.
     */
    private JMenu             fileMenu                    = null;
    /**
     * Grid menu item instance.
     */
    private JMenuItem         gridMenuItem                = null;
    /**
     * Help menu instance.
     */
    private JMenu             helpMenu                    = null;
    /**
     * Import menu item instance.
     */
    private JMenuItem         importMenuItem              = null;
    /**
     * Open menu item instance.
     */
    private JMenuItem         openMenuItem                = null;
    /**
     * Paste menu item instance.
     */
    private JMenuItem         pasteMenuItem               = null;
    /**
     * Plugins menu instance.
     */
    private JMenu             pluginsMenu                 = null;
    /**
     * Mirror vertical menu item instance.
     */
    private JMenuItem         mirrorVerticalMenuItem      = null;
    /**
     * Mirror horizontal menu item instance.
     */
    private JMenuItem         mirrorHorizontalMenuItem    = null;
    /**
     * Rotate anticlockwise menu item instance.
     */
    private JMenuItem         rotateAnticlockwiseMenuItem = null;
    /**
     * Rotate clockwise menu item instance.
     */
    private JMenuItem         rotateClockwiseMenuItem     = null;
    /**
     * Save as menu item instance.
     */
    private JMenuItem         saveAsMenuItem              = null;
    /**
     * Save as Part menu item instance.
     */
    private JMenuItem         saveAsPartMenuItem          = null;
    /**
     * Save menu item instance.
     */
    private JMenuItem         saveMenuItem                = null;
    /**
     * Save preferences menu item instance.
     */
    private JMenuItem         savePreferencesMenuItem     = null;
    /**
     * Show grid check box menu item instance.
     */
    private JCheckBoxMenuItem showGridCheckBoxMenuItem    = null;
    /**
     * Snap to grid check box menu item instance.
     */
    private JCheckBoxMenuItem snapToGridCheckBoxMenuItem  = null;
    /**
     * View menu instance.
     */
    private JMenu             viewMenu                    = null;
    /**
     * View part properties menu item instance.
     */
    private JMenuItem         viewPartPropertiesMenuItem  = null;
    /**
     * New menu item instance.
     */
    private JMenu             newMenu                     = null;
    /**
     * New scheme menu item instance.
     */
    private JMenuItem         newSchemeMenuItem           = null;
    /**
     * New part menu item instance.
     */
    private JMenuItem         newPartMenuItem             = null;
    /**
     * Edit part menu item instance.
     */
    private JMenuItem         editPartMenuItem            = null;
    /**
     * Edit part menu item instance.
     */
    private JMenuItem         doneEditPartMenuItem        = null;
    /**
     * Scale menu item instance.
     */
    private JMenuItem         scaleMenuItem               = null;
    /**
     * Save menu instance.
     */
    private JMenu             saveMenu                    = null;
    /**
     * Edit part menu instance.
     */
    private JMenu             editPartMenu                = null;
    /**
     * Rotate menu instance.
     */
    private JMenu             rotateMenu                  = null;
    /**
     * Mirror menu instance.
     */
    private JMenu             mirrorMenu                  = null;

    /**
     * Default constructor. As {@link MenuBar} is singleton, it is defined as private.
     */
    private MenuBar() {
        super();
    }

    /**
     * Getter for <code>newMenu</code>.
     *
     * @return <code>newMenu</code> instance.
     */
    private JMenu getNewMenu() {
        if (this.newMenu == null) {
            this.newMenu = new JMenu();
            this.newMenu.setText(MenuBarResources.NEW_MENU.getText());
            this.newMenu.add(getNewSchemeMenuItem());
            this.newMenu.add(getNewPartMenuItem());
        }
        return this.newMenu;
    }

    /**
     * Getter for <code>addMenu</code>.
     *
     * @return <code>addMenu</code> instance.
     */
    private JMenu getAddMenu() {
        if (this.addMenu == null) {
            this.addMenu = new JMenu();
            this.addMenu.setText(MenuBarResources.ADD_MENU.getText());
            this.addMenu.add(getAddPartMenuItem());
            this.addMenu.add(getImportMenuItem());
        }
        return this.addMenu;
    }

    /**
     * Getter for <code>saveMenu</code>.
     *
     * @return <code>saveMenu</code> instance.
     */
    private JMenu getSaveMenu() {
        if (this.saveMenu == null) {
            this.saveMenu = new JMenu();
            this.saveMenu.setText(MenuBarResources.SAVE_MENU.getText());
            this.saveMenu.add(getSaveMenuItem());
            this.saveMenu.add(getSaveAsMenuItem());
            this.saveMenu.add(getSaveAsPartMenuItem());
        }
        return this.saveMenu;
    }

    /**
     * Getter for <code>scaleMenuItem</code>.
     *
     * @return <code>scaleMenuItem</code> instance.
     */
    private JMenuItem getScaleMenuItem() {
        if (this.scaleMenuItem == null) {
            this.scaleMenuItem = new JMenuItem();
            this.scaleMenuItem.setText(MenuBarResources.SCALE_MENU_ITEM.getText());
            this.scaleMenuItem.addActionListener(new ScaleMenuItemListener());
        }
        return this.scaleMenuItem;
    }

    /**
     * Getter for <code>newSchemeMenuItem</code>.
     *
     * @return <code>newSchemePropertiesMenuItem</code> instance.
     */
    private JMenuItem getNewSchemeMenuItem() {
        if (this.newSchemeMenuItem == null) {
            this.newSchemeMenuItem = new JMenuItem();
            this.newSchemeMenuItem.setText(MenuBarResources.NEW_SCHEME_MENU_ITEM.getText());
            this.newSchemeMenuItem.addActionListener(new NewSchemeMenuItemListener());
        }
        return this.newSchemeMenuItem;
    }

    /**
     * Getter for <code>newPartMenuItem</code>.
     *
     * @return <code>newPartPropertiesMenuItem</code> instance.
     */
    private JMenuItem getNewPartMenuItem() {
        if (this.newPartMenuItem == null) {
            this.newPartMenuItem = new JMenuItem();
            this.newPartMenuItem.setText(MenuBarResources.NEW_PART_MENU_ITEM.getText());
            this.newPartMenuItem.addActionListener(new NewPartMenuItemListener());
        }
        return this.newPartMenuItem;
    }

    /**
     * Getter for <code>pluginsMenu</code>.
     *
     * @return <code>pluginsMenu</code> instance.
     */
    public JMenu getPluginsMenu() {
        if (this.pluginsMenu == null) {
            this.pluginsMenu = new JMenu();
            this.pluginsMenu.setText(MenuBarResources.PLUGINS_MENU_ITEM.getText());
        }
        return this.pluginsMenu;
    }

    /**
     * Getter for <code>viewPartPropertiesMenuItem</code>.
     *
     * @return <code>viewPartPropertiesMenuItem</code> instance.
     */
    public JMenuItem getViewPartPropertiesMenuItem() {
        if (this.viewPartPropertiesMenuItem == null) {
            this.viewPartPropertiesMenuItem = new JMenuItem();
            this.viewPartPropertiesMenuItem.setText(MenuBarResources.VIEW_PART_PROPERTIES_MENU_ITEM.getText());
            this.viewPartPropertiesMenuItem.addActionListener(new ViewPartPropertiesMenuItemListener());
        }
        return this.viewPartPropertiesMenuItem;
    }

    /**
     * Set enable status of edit part menu item.
     *
     * @param enabled enable status to set.
     */
    public void setEditPartMenuItemEnabled(boolean enabled) {
        getEditPartMenuItem().setEnabled(enabled);
    }

    /**
     * Set enable status of done edit part menu item.
     *
     * @param enabled enable status to set.
     */
    public void setDoneEditPartMenuItemEnabled(boolean enabled) {
        getDoneEditPartMenuItem().setEnabled(enabled);
    }

    /**
     * Refreshes status of menu items.
     */
    public void refresh() {
        switch (Gui.getActiveScenePanelTab()) {
            case TAB_SCHEME:
                getAddPartMenuItem().setEnabled(true);
                getSaveMenuItem().setEnabled(true);
                getSaveAsPartMenuItem().setEnabled(false);
                break;
            case TAB_PART:
                getAddPartMenuItem().setEnabled(false);
                getSaveMenuItem().setEnabled(false);
                getSaveAsPartMenuItem().setEnabled(true);
                break;
            default:
                break;
        }

        // delete all menu item from plugins menu bar and add new for actual tab
        getPluginsMenu().removeAll();
        Vector<JMenuItem> menuItemVector = Structures.getPluginMenuItems().get(Gui.getActiveScenePanelTab());
        for (JMenuItem menuItem : menuItemVector) {
            getPluginsMenu().add(menuItem);
        }
    }

    /**
     * Getter for <code>aboutMenuItem</code>.
     *
     * @return <code>aboutMenuItem</code> instance.
     */
    private JMenuItem getAboutMenuItem() {
        if (this.aboutMenuItem == null) {
            this.aboutMenuItem = new JMenuItem();
            this.aboutMenuItem.setText(MenuBarResources.ABOUT_MENU_ITEM.getText());
            this.aboutMenuItem.addActionListener(new AboutMenuItemListener());
        }
        return this.aboutMenuItem;
    }

    /**
     * Getter for <code>addPartMenuItem</code>.
     *
     * @return <code>addPartMenuItem</code> instance.
     */
    private JMenuItem getAddPartMenuItem() {
        if (this.addPartMenuItem == null) {
            this.addPartMenuItem = new JMenuItem();
            this.addPartMenuItem.setText(MenuBarResources.ADD_PART_MENU_ITEM.getText());
            this.addPartMenuItem.addActionListener(new AddPartMenuItemListener());

        }
        return this.addPartMenuItem;
    }

    /**
     * Getter for <code>antialiasedCheckBoxMenuItem</code>.
     *
     * @return <code>antialiasedCheckBoxMenuItem</code> instance.
     */
    private JCheckBoxMenuItem getAntialiasedCheckBoxMenuItem() {
        if (this.antialiasedCheckBoxMenuItem == null) {
            this.antialiasedCheckBoxMenuItem = new JCheckBoxMenuItem();
            this.antialiasedCheckBoxMenuItem.setText(MenuBarResources.ANTIALIASED_CHECK_BOX_MENU_ITEM.getText());
            this.antialiasedCheckBoxMenuItem.setSelected(GuiConfiguration.getInstance().isSchemeAntialiased());
            this.antialiasedCheckBoxMenuItem.addActionListener(new AntialiasedCheckBoxMenuItemListener());
        }
        return this.antialiasedCheckBoxMenuItem;
    }

    /**
     * Getter for <code>copyMenuItem</code>.
     *
     * @return <code>copyMenuItem</code> instance.
     */
    private JMenuItem getCopyMenuItem() {
        if (this.copyMenuItem == null) {
            this.copyMenuItem = new JMenuItem();
            this.copyMenuItem.setText(MenuBarResources.COPY_MENU_ITEM.getText());
            this.copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK, true));
        }
        return this.copyMenuItem;
    }

    /**
     * Getter for <code>cutMenuItem</code>.
     *
     * @return <code>cutMenuItem</code> instance.
     */
    private JMenuItem getCutMenuItem() {
        if (this.cutMenuItem == null) {
            this.cutMenuItem = new JMenuItem();
            this.cutMenuItem.setText(MenuBarResources.CUT_MENU_ITEM.getText());
            this.cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK, true));

            this.cutMenuItem.setEnabled(false);
        }
        return this.cutMenuItem;
    }

    /**
     * Getter for <code>editPartMenu</code>.
     *
     * @return <code>editPartMenu</code> instance.
     */
    private JMenu getEditPartMenu() {
        if (this.editPartMenu == null) {
            this.editPartMenu = new JMenu();
            this.editPartMenu.setText(MenuBarResources.EDIT_PART_MENU.getText());

            this.editPartMenu.add(getEditPartMenuItem());
            this.editPartMenu.add(getDoneEditPartMenuItem());
        }
        return this.editPartMenu;
    }

    /**
     * Getter for <code>rotateMenu</code>.
     *
     * @return <code>rotateMenu</code> instance.
     */
    private JMenu getRotateMenu() {
        if (this.rotateMenu == null) {
            this.rotateMenu = new JMenu();
            this.rotateMenu.setText(MenuBarResources.ROTATE_MENU.getText());

            this.rotateMenu.add(getRotateAnticlockwiseMenuItem());
            this.rotateMenu.add(getRotateClockwiseMenuItem());
        }
        return this.rotateMenu;
    }

    /**
     * Getter for <code>mirrorMenu</code>.
     *
     * @return <code>mirrorMenu</code> instance.
     */
    private JMenu getMirrorMenu() {
        if (this.mirrorMenu == null) {
            this.mirrorMenu = new JMenu();
            this.mirrorMenu.setText(MenuBarResources.MIRROR_MENU.getText());

            this.mirrorMenu.add(getMirrorHorizontalMenuItem());
            this.mirrorMenu.add(getMirrorVerticalMenuItem());
        }
        return this.mirrorMenu;
    }

    /**
     * Getter for <code>debugCheckBoxMenuItem</code>.
     *
     * @return <code>debugCheckBoxMenuItem</code> instance.
     */
    private JCheckBoxMenuItem getDebugCheckBoxMenuItem() {
        if (this.debugCheckBoxMenuItem == null) {
            this.debugCheckBoxMenuItem = new JCheckBoxMenuItem();
            this.debugCheckBoxMenuItem.setText(MenuBarResources.DEBUG_CHECK_BOX_MENU_ITEM.getText());
            this.debugCheckBoxMenuItem.setSelected(GuiConfiguration.getInstance().isSchemeDebugged());
            this.debugCheckBoxMenuItem.addActionListener(new DebugCheckBoxMenuItemListener());
        }
        return this.debugCheckBoxMenuItem;
    }

    /**
     * Getter for <code>editMenu</code>.
     *
     * @return <code>editMenu</code> instance.
     */
    private JMenu getEditMenu() {
        if (this.editMenu == null) {
            this.editMenu = new JMenu();
            this.editMenu.setText(MenuBarResources.EDIT_MENU.getText());

            this.editMenu.add(getCopyMenuItem());
            this.editMenu.add(getCutMenuItem());
            this.editMenu.add(getPasteMenuItem());
            this.editMenu.addSeparator();
            this.editMenu.add(getEditPartMenu());
            this.editMenu.addSeparator();
            this.editMenu.add(getRotateMenu());
            this.editMenu.add(getMirrorMenu());
            this.editMenu.addSeparator();
            this.editMenu.add(getSnapToGridCheckBoxMenuItem());
            this.editMenu.add(getGridMenuItem());
        }
        return this.editMenu;
    }

    /**
     * Getter for <code>editPartMenuItem</code>.
     *
     * @return <code>editPartMenuItem</code> instance.
     */
    private JMenuItem getEditPartMenuItem() {
        if (this.editPartMenuItem == null) {
            this.editPartMenuItem = new JMenuItem();
            this.editPartMenuItem.setText(MenuBarResources.EDIT_PART_MENU_ITEM.getText());
            this.editPartMenuItem.addActionListener(new EditPartMenuItemListener());
        }
        return this.editPartMenuItem;
    }

    /**
     * Getter for <code>doneEditPartMenuItem</code>.
     *
     * @return <code>doneEditPartMenuItem</code> instance.
     */
    private JMenuItem getDoneEditPartMenuItem() {
        if (this.doneEditPartMenuItem == null) {
            this.doneEditPartMenuItem = new JMenuItem();
            this.doneEditPartMenuItem.setText(MenuBarResources.DONE_EDIT_PART_MENU_ITEM.getText());
            this.doneEditPartMenuItem.addActionListener(new DoneEditPartMenuItemListener());
            this.doneEditPartMenuItem.setEnabled(false);
        }
        return this.doneEditPartMenuItem;
    }

    /**
     * Getter for <code>exitMenuItem</code>.
     *
     * @return <code>exitMenuItem</code> instance.
     */
    private JMenuItem getExitMenuItem() {
        if (this.exitMenuItem == null) {
            this.exitMenuItem = new JMenuItem();
            this.exitMenuItem.setText(MenuBarResources.EXIT_MENU_ITEM.getText());
            this.exitMenuItem.addActionListener(new ExitMenuItemListener());
        }
        return this.exitMenuItem;
    }

    /**
     * Getter for <code>fileMenu</code>.
     *
     * @return <code>fileMenu</code> instance.
     */
    private JMenu getFileMenu() {
        if (this.fileMenu == null) {
            this.fileMenu = new JMenu();
            this.fileMenu.setText(MenuBarResources.FILE_MENU.getText());

            this.fileMenu.add(getNewMenu());
            this.fileMenu.add(getAddMenu());
            this.fileMenu.add(getOpenMenuItem());
            this.fileMenu.add(getSaveMenu());
            this.fileMenu.addSeparator();
            this.fileMenu.add(getSavePreferencesMenuItem());
            this.fileMenu.add(getExitMenuItem());
        }
        return this.fileMenu;
    }

    /**
     * Getter for <code>gridMenuItem</code>.
     *
     * @return <code>gridMenuItem</code> instance.
     */
    private JMenuItem getGridMenuItem() {
        if (this.gridMenuItem == null) {
            this.gridMenuItem = new JMenuItem();
            this.gridMenuItem.setText(MenuBarResources.GRID_MENU_ITEM.getText());
            this.gridMenuItem.addActionListener(new GridMenuItemListener());

        }
        return this.gridMenuItem;
    }

    /**
     * Getter for <code>helpMenu</code>.
     *
     * @return <code>helpMenu</code> instance.
     */
    private JMenu getHHelpMenu() {
        if (this.helpMenu == null) {
            this.helpMenu = new JMenu();
            this.helpMenu.setText(MenuBarResources.HELP_MENU.getText());
            this.helpMenu.add(getAboutMenuItem());
        }
        return this.helpMenu;
    }

    /**
     * Getter for <code>openMenuItem</code>.
     *
     * @return <code>openMenuItem</code> instance.
     */
    private JMenuItem getImportMenuItem() {
        if (this.importMenuItem == null) {
            this.importMenuItem = new JMenuItem();
            this.importMenuItem.setText(MenuBarResources.IMPORT_MENU_ITEM.getText());
            this.importMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.CTRL_MASK, true));
            this.importMenuItem.addActionListener(new ImportMenuItemListener());
        }
        return this.importMenuItem;
    }

    /**
     * Getter for <code>mirrorHorizontalMenuItem</code>.
     *
     * @return <code>mirrorHorizontalMenuItem</code> instance.
     */
    private JMenuItem getMirrorHorizontalMenuItem() {
        if (this.mirrorHorizontalMenuItem == null) {
            this.mirrorHorizontalMenuItem = new JMenuItem();
            this.mirrorHorizontalMenuItem.setText(MenuBarResources.MIRROR_HORIZONTAL_MENU_ITEM.getText());
            this.mirrorHorizontalMenuItem.addActionListener(new MirrorElementMenuItemListener(new UnitPoint(-1, 1)));
        }
        return this.mirrorHorizontalMenuItem;
    }

    /**
     * Getter for <code>mirrorVerticalMenuItem</code>.
     *
     * @return <code>mirrorVerticalMenuItem</code> instance.
     */
    private JMenuItem getMirrorVerticalMenuItem() {
        if (this.mirrorVerticalMenuItem == null) {
            this.mirrorVerticalMenuItem = new JMenuItem();
            this.mirrorVerticalMenuItem.setText(MenuBarResources.MIRROR_VERTICAL_MENU_ITEM.getText());
            this.mirrorVerticalMenuItem.addActionListener(new MirrorElementMenuItemListener(new UnitPoint(1, -1)));
        }
        return this.mirrorVerticalMenuItem;
    }

    /**
     * Getter for <code>openMenuItem</code>.
     *
     * @return <code>openMenuItem</code> instance.
     */
    private JMenuItem getOpenMenuItem() {
        if (this.openMenuItem == null) {
            this.openMenuItem = new JMenuItem();
            this.openMenuItem.setText(MenuBarResources.OPEN_MENU_ITEM.getText());
            this.openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK, true));
            this.openMenuItem.addActionListener(new OpenMenuItemListener());
        }
        return this.openMenuItem;
    }

    /**
     * Getter for <code>pasteMenuItem</code>.
     *
     * @return <code>pasteMenuItem</code> instance.
     */
    private JMenuItem getPasteMenuItem() {
        if (this.pasteMenuItem == null) {
            this.pasteMenuItem = new JMenuItem();
            this.pasteMenuItem.setText(MenuBarResources.PASTE_MENU_ITEM.getText());
            this.pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK, true));
        }
        return this.pasteMenuItem;
    }

    /**
     * Getter for <code>rotateAnticlockwiseMenuItem</code>.
     *
     * @return <code>rotateAnticlockwiseMenuItem</code> instance.
     */
    private JMenuItem getRotateAnticlockwiseMenuItem() {
        if (this.rotateAnticlockwiseMenuItem == null) {
            this.rotateAnticlockwiseMenuItem = new JMenuItem();
            this.rotateAnticlockwiseMenuItem.setText(MenuBarResources.ROTATE_ANTICLOCKWISE_MENU_ITEM.getText());
            this.rotateAnticlockwiseMenuItem.addActionListener(new RotateElementMenuItemListener(new UnitPoint(-1, 0)));
        }
        return this.rotateAnticlockwiseMenuItem;
    }

    /**
     * Getter for <code>rotateClockwiseMenuItem</code>.
     *
     * @return <code>rotateClockwiseMenuItem</code> instance.
     */
    private JMenuItem getRotateClockwiseMenuItem() {
        if (this.rotateClockwiseMenuItem == null) {
            this.rotateClockwiseMenuItem = new JMenuItem();
            this.rotateClockwiseMenuItem.setText(MenuBarResources.ROTATE_CLOCKWISE_MENU_ITEM.getText());
            this.rotateClockwiseMenuItem.addActionListener(new RotateElementMenuItemListener(new UnitPoint(1, 0)));
        }
        return this.rotateClockwiseMenuItem;
    }

    /**
     * Getter for <code>saveAsMenuItem<code>.
     *
     * @return <code>saveAsMenuItem<code> instance.
     */
    private JMenuItem getSaveAsMenuItem() {
        if (this.saveAsMenuItem == null) {
            this.saveAsMenuItem = new JMenuItem();
            this.saveAsMenuItem.setText(MenuBarResources.SAVE_AS_MENU_ITEM.getText());
            this.saveAsMenuItem.addActionListener(new SaveAsMenuItemListener());

        }
        return this.saveAsMenuItem;
    }

    /**
     * Getter for <code>saveAsPartMenuItem</code>.
     *
     * @return <code>saveAsPartMenuItem</code> instance.
     */
    private JMenuItem getSaveAsPartMenuItem() {
        if (this.saveAsPartMenuItem == null) {
            this.saveAsPartMenuItem = new JMenuItem();
            this.saveAsPartMenuItem.setText(MenuBarResources.SAVE_AS_PART_MENU_ITEM.getText());
            this.saveAsPartMenuItem.addActionListener(new SaveAsPartMenuItemListener());

        }
        return this.saveAsPartMenuItem;
    }

    /**
     * Getter for <code>saveMenuItem</code>.
     *
     * @return <code>saveMenuItem</code> instance.
     */
    private JMenuItem getSaveMenuItem() {
        if (this.saveMenuItem == null) {
            this.saveMenuItem = new JMenuItem();
            this.saveMenuItem.setText(MenuBarResources.SAVE_MENU_ITEM.getText());
            this.saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK, true));
            this.saveMenuItem.addActionListener(new SaveMenuItemListener());
        }
        return this.saveMenuItem;
    }

    /**
     * Getter for <code>savePreferencesMenuItem</code>.
     *
     * @return <code>savePreferencesMenuItem</code> instance.
     */
    private JMenuItem getSavePreferencesMenuItem() {
        if (this.savePreferencesMenuItem == null) {
            this.savePreferencesMenuItem = new JMenuItem();
            this.savePreferencesMenuItem.setText(MenuBarResources.SAVE_PREFERENCES_MENU_ITEM.getText());
            this.savePreferencesMenuItem.addActionListener(new SavePreferencesMenuItemListener());
        }
        return this.savePreferencesMenuItem;
    }

    /**
     * Getter for <code>showGridCheckBoxMenuItem</code>.
     *
     * @return <code>showGridCheckBoxMenuItem</code> instance.
     */
    private JCheckBoxMenuItem getShowGridCheckBoxMenuItem() {
        if (this.showGridCheckBoxMenuItem == null) {
            this.showGridCheckBoxMenuItem = new JCheckBoxMenuItem();
            this.showGridCheckBoxMenuItem.setText(MenuBarResources.SHOW_GRID_CHECK_BOX_MENU_ITEM.getText());
            this.showGridCheckBoxMenuItem.setSelected(GuiConfiguration.getInstance().isGridVisible());
            this.showGridCheckBoxMenuItem.addActionListener(new ShowGridCheckBoxMenuItemListener());
        }
        return this.showGridCheckBoxMenuItem;
    }

    /**
     * Getter for <code>snapToGridCheckBoxMenuItem</code>.
     *
     * @return <code>snapToGridCheckBoxMenuItem</code> instance.
     */
    private JCheckBoxMenuItem getSnapToGridCheckBoxMenuItem() {
        if (this.snapToGridCheckBoxMenuItem == null) {
            this.snapToGridCheckBoxMenuItem = new JCheckBoxMenuItem();
            this.snapToGridCheckBoxMenuItem.setText(MenuBarResources.SNAP_TO_GRID_CHECK_BOX_MENU_ITEM.getText());
            this.snapToGridCheckBoxMenuItem.setSelected(GuiConfiguration.getInstance().isSnapToGrid());
            this.snapToGridCheckBoxMenuItem.addActionListener(new SnapToGridCheckBoxMenuItemListener());
        }
        return this.snapToGridCheckBoxMenuItem;
    }

    /**
     * Getter for <code>viewMenu</code>.
     *
     * @return <code>viewMenu</code> instance.
     */
    private JMenu getViewMenu() {
        if (this.viewMenu == null) {
            this.viewMenu = new JMenu();
            this.viewMenu.setText(MenuBarResources.VIEW_MENU.getText());
            this.viewMenu.add(getAntialiasedCheckBoxMenuItem());
            this.viewMenu.add(getShowGridCheckBoxMenuItem());
            this.viewMenu.add(getDebugCheckBoxMenuItem());
            this.viewMenu.add(getViewPartPropertiesMenuItem());
            this.viewMenu.addSeparator();
            this.viewMenu.add(getScaleMenuItem());
        }
        return this.viewMenu;
    }
}
