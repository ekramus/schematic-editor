package cz.cvut.fel.schematicEditor.graphNode;

import cz.cvut.fel.schematicEditor.support.Transformation;

/**
 * This class represents Transformation Node.
 * 
 * @author uk
 */
public class TransformationNode extends Node {
    /**
     * This field represents transformation, which is applied on children of this node.
     */
    Transformation transformation;

    /**
     * This is constructor.
     * 
     * @param t
     *            transformation node.
     */
    public TransformationNode(Transformation t) {
        this.transformation = t;
    }

    /**
     * Getter for transformation.
     * 
     * @return the transformation
     */
    public Transformation getTransformation() {
        return this.transformation;
    }

    /**
     * Setter for transformation.
     * 
     * @param transformation
     *            the transformation to set
     */
    public void setTransformation(Transformation transformation) {
        this.transformation = transformation;
    }

    /**
     * @see graphNode.Node#toString()
     */
    @Override
    public String toString() {
        return id + " [TransformationNode]";
    }
}
