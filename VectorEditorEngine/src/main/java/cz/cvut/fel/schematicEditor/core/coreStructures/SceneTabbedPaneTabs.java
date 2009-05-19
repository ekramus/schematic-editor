package cz.cvut.fel.schematicEditor.core.coreStructures;

/**
 *This enumerator contains types of tabs available in <code>sceneTabbedPane</code>.
 *
 * @author Urban Kravjansky
 *
 */
public enum SceneTabbedPaneTabs {
    /**
     * Scheme tab enum.
     */
    TAB_SCHEME(0),
    /**
     * Part tab enum.
     */
    TAB_PART(1);

    /**
     * Order of tab.
     */
    private int order;

    /**
     * This method instantiates new instance.
     *
     * @param order order of tab.
     */
    SceneTabbedPaneTabs(int order) {
        setOrder(order);
    }

    /**
     * @return the order to get.
     */
    public int getOrder() {
        return this.order;
    }

    /**
     * @param order the order to set.
     */
    private void setOrder(int order) {
        this.order = order;
    }
}
