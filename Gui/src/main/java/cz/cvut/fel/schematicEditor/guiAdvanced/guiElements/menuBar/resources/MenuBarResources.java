package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.resources;

/**
 * This enumerator implements menu bar resources.
 *
 * TODO Implement resources as serialized XML objects, so they can be modified.
 *
 * @author Urban Kravjansky
 */
@SuppressWarnings("nls")
public enum MenuBarResources {
    /**
     * About menu item string.
     */
    ABOUT_MENU_ITEM("About"),
    /**
     * Add Part menu item string.
     */
    ADD_PART_MENU_ITEM("Add Part"),
    /**
     * Antialiased menu item string.
     */
    ANTIALIASED_CHECK_BOX_MENU_ITEM("Scheme Antialiased"),
    /**
     * Copy menu item string.
     */
    COPY_MENU_ITEM("Copy"),
    /**
     * Cut menu item string.
     */
    CUT_MENU_ITEM("Cut"),
    /**
     * Debug menu item string.
     */
    DEBUG_CHECK_BOX_MENU_ITEM("Scheme Debugged"),
    /**
     * Edit menu string.
     */
    EDIT_MENU("Edit"),
    /**
     * Exit menu item string.
     */
    EXIT_MENU_ITEM("Exit"),
    /**
     * Export menu string.
     */
    EXPORT_MENU("Export"),
    /**
     * Export to metapost menu item string.
     */
    EXPORT_TO_METAPOST_MENU_ITEM("MetaPost"),
    /**
     * Export to PS menu item string.
     */
    EXPORT_TO_PS_MENU_ITEM("PostScript (PS)"),
    /**
     * File menu string.
     */
    FILE_MENU("File"),
    /**
     * Grid properties menu item string.
     */
    GRID_MENU_ITEM("Grid Properties"),
    /**
     * Grid menu item dialog string.
     */
    GRID_MENU_ITEM_DIALOG("Enter grid size"),
    /**
     * Help menu string.
     */
    HELP_MENU("Help"),
    /**
     * Import menu item string.
     */
    IMPORT_MENU_ITEM("Import"),
    /**
     * Open menu item string.
     */
    OPEN_MENU_ITEM("Open"),
    /**
     * Paste menu item string.
     */
    PASTE_MENU_ITEM("Paste"),
    /**
     * Plugins menu item string.
     */
    PLUGINS_MENU_ITEM("Plugins"),
    /**
     * Save as menu item string.
     */
    SAVE_AS_MENU_ITEM("Save As"),
    /**
     * Save as Part menu item string.
     */
    SAVE_AS_PART_MENU_ITEM("Save As Part"),
    /**
     * Save menu item string.
     */
    SAVE_MENU_ITEM("Save"),
    /**
     * Save preferences menu item string.
     */
    SAVE_PREFERENCES_MENU_ITEM("Save Preferences"),
    /**
     * Show grid check box menu item string.
     */
    SHOW_GRID_CHECK_BOX_MENU_ITEM("Show Grid"),
    /**
     * Snap to grid check box menu item string.
     */
    SNAP_TO_GRID_CHECK_BOX_MENU_ITEM("Snap to grid"),
    /**
     * View menu string.
     */
    VIEW_MENU("View"),
    /**
     * View part properties menu item string.
     */
    VIEW_PART_PROPERTIES_MENU_ITEM("View part properties.."),
    /**
     * Rotate anticlockwise menu item string.
     */
    ROTATE_ANTICLOCKWISE_MENU_ITEM("Rotate Anticlockwise"),
    /**
     * Rotate clockwise menu item string.
     */
    ROTATE_CLOCKWISE_MENU_ITEM("Rotate Clockwise"),
    /**
     * Mirror horizontal menu item string.
     */
    MIRROR_HORIZONTAL_MENU_ITEM("Horizontal mirror"),
    /**
     * Mirror vertical menu item string.
     */
    MIRROR_VERTICAL_MENU_ITEM("Vertical mirror"),
    /**
     * New menu string.
     */
    NEW_MENU("New"),
    /**
     * New scheme menu item string.
     */
    NEW_SCHEME_MENU_ITEM("New scheme"),
    /**
     * New part menu item string.
     */
    NEW_PART_MENU_ITEM("New part"),
    /**
     * Edit part menu item string.
     */
    EDIT_PART_MENU_ITEM("Start"),
    /**
     * Done edit part menu item string.
     */
    DONE_EDIT_PART_MENU_ITEM("Stop"),
    /**
     * Add menu string.
     */
    ADD_MENU("Add.."),
    /**
     * Add menu string.
     */
    ROTATE_MENU("Rotate.."),
    /**
     * Add menu string.
     */
    MIRROR_MENU("Mirror.."),
    /**
     * Add menu string.
     */
    SAVE_MENU("Save.."),
    /**
     * Add menu string.
     */
    EDIT_PART_MENU("Edit part.."),
    /**
     * Scale menu item string.
     */
    SCALE_MENU_ITEM("Scale..");

    /**
     * String value of item.
     */
    private String text;

    /**
     * Constructor sets string value of enum item.
     *
     * @param text String value of item.
     */
    private MenuBarResources(String text) {
        setText(text);
    }

    /**
     * Getter for string value of item.
     *
     * @return String value of item.
     */
    public String getText() {
        return this.text;
    }

    /**
     * Setter of string value of item.
     *
     * @param text String value of item to set.
     */
    private void setText(String text) {
        this.text = text;
    }
}
