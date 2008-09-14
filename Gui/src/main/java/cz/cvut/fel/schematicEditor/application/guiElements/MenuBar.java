package cz.cvut.fel.schematicEditor.application.guiElements;

import java.awt.Event;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar.AboutMenuItemListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar.AddPartMenuItemListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar.AntialiasedCheckBoxMenuItemListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar.DebugCheckBoxMenuItemListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar.ExitMenuItemListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar.GridMenuItemListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar.ImportMenuItemListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar.OpenMenuItemListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar.SaveAsMenuItemListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar.SaveAsPartMenuItemListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar.SaveMenuItemListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar.SavePreferencesMenuItemListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar.ShowGridCheckBoxMenuItemListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar.SnapToGridCheckBoxMenuItemListener;
import cz.cvut.fel.schematicEditor.application.guiElements.resources.MenuBarResources;
import cz.cvut.fel.schematicEditor.configuration.GuiConfiguration;

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
    public static JMenuBar getInstance() {
        if (menuBar == null) {
            menuBar = new MenuBar();
            menuBar.add(menuBar.getFileMenu());
            menuBar.add(menuBar.getEditMenu());
            menuBar.add(menuBar.getViewMenu());
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
     * Default constructor. As {@link MenuBar} is singleton, it is defined as private.
     */
    private MenuBar() {
        super();
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
        }
        return this.cutMenuItem;
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
            this.editMenu.add(getCutMenuItem());
            this.editMenu.add(getCopyMenuItem());
            this.editMenu.add(getPasteMenuItem());
            this.editMenu.add(new JSeparator());
            this.editMenu.add(getSnapToGridCheckBoxMenuItem());
            this.editMenu.add(getGridMenuItem());
        }
        return this.editMenu;
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
            this.fileMenu.add(getOpenMenuItem());
            this.fileMenu.add(getImportMenuItem());
            this.fileMenu.add(getAddPartMenuItem());
            this.fileMenu.add(getSaveMenuItem());
            this.fileMenu.add(getSaveAsMenuItem());
            this.fileMenu.add(getSaveAsPartMenuItem());
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
        }
        return this.viewMenu;
    }
}
