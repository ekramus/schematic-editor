package cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.propertiesPanel.resources;

/**
 * @author Urban Kravjansky
 */
public enum PropertiesToolBarResources {
    /**
     * Contour check box string.
     */
    CONTOUR_CHECK_BOX("draw shape contour"),
    /**
     * Contour color button string.
     */
    CONTOUR_COLOR_BTN("fg"),
    /**
     * Fill check box string.
     */
    FILL_CHECK_BOX("draw shape fill"),
    /**
     * Fill color button string.
     */
    FILL_COLOR_BTN("bg");

    /**
     * String value of item.
     */
    private String text;

    /**
     * Constructor sets string value of enum item.
     *
     * @param text
     *            String value of item.
     */
    private PropertiesToolBarResources(String text) {
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
     * @param text
     *            String value of item to set.
     */
    private void setText(String text) {
        this.text = text;
    }
}
