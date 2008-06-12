package cz.cvut.fel.schematicEditor.graphNode;

/**
 * This class implements Node and all generic methods.
 *
 * @author uk
 */
public abstract class Node {
    /**
     * This field represents ID of node.
     */
    String          id;
    /**
     * This field represents parent of current node.
     */
    Node            parent;
    /**
     * Indicates, whether node is or is not disabled.
     */
    private boolean disabled;

    /**
     * This is default constructor.
     */
    public Node() {
        id = Long.toString(System.currentTimeMillis());
        setDisabled(false);
    }

    /**
     * This is constructor.
     *
     * @param id
     *            identifier of node.
     */
    public Node(String id) {
        this.id = id;
        setDisabled(false);
    }

    /**
     * This method duplicates
     *
     * @return Copy of node with it's children.
     */
    public Node duplicate() {
        return null;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return id + " [Node]";
    }

    /**
     * Setter for parent variable.
     *
     * @param parent
     *            the parent to set
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * @param disabled
     *            the disabled to set
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    /**
     * @return the disabled
     */
    public boolean isDisabled() {
        return this.disabled;
    }
}
