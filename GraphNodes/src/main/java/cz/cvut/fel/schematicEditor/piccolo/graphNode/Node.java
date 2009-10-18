package cz.cvut.fel.schematicEditor.piccolo.graphNode;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This class implements Node and all generic methods.
 *
 * @author uk
 */
@XStreamAlias("Node")
public abstract class Node {
    /**
     * This field represents ID of node.
     */
    String          id;
    /**
     * This field represents parent of current node.
     */
    GroupNode       parent;
    /**
     * Indicates, whether node is or is not disabled.
     */
    private boolean disabled;

    /**
     * This is default constructor.
     */
    protected Node() {
        this.id = Long.toString(System.currentTimeMillis());
        setDisabled(false);
    }

    /**
     * This is constructor.
     *
     * @param id identifier of node.
     */
    protected Node(String id) {
        this.id = id;
        setDisabled(false);
    }

    /**
     * This method duplicates
     *
     * @return Copy of node with it's children.
     */
    protected abstract Node duplicate();

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.id + " [Node]";
    }

    /**
     * Setter for parent variable.
     *
     * @param parent the parent to set
     */
    public void setParent(GroupNode parent) {
        this.parent = parent;
    }

    /**
     * @param disabled the disabled to set
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

    /**
     * Getter for parent node.
     *
     * @return the parent
     */
    public GroupNode getParent() {
        return this.parent;
    }

    /**
     * Getter for id.
     *
     * @return
     */
    public String getId() {
        return this.id;
    }
}
