package cz.cvut.fel.schematicEditor.piccolo.graphNode;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cz.cvut.fel.schematicEditor.support.Transformation;

/**
 * This class represents Transformation Node.
 *
 * @author uk
 */
@XStreamAlias("TransformationNode")
public class TransformationNode extends Node {
    /**
     * This field represents transformation, which is applied on children of this node.
     */
    Transformation transformation;

    /**
     * This is constructor.
     *
     * @param t transformation node.
     */
    protected TransformationNode(Transformation t) {
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
     * @param transformation the transformation to set
     */
    public void setTransformation(Transformation transformation) {
        this.transformation = transformation;
    }

    /**
     * @see graphNode.Node#toString()
     */
    @Override
    public String toString() {
        return this.id + " [TransformationNode: " + getTransformation() + "]";
    }

    /**
     * @see cz.cvut.fel.schematicEditor.original.graphNode.Node#duplicate()
     */
    @Override
    protected Node duplicate() {
        TransformationNode result = new TransformationNode(getTransformation());

        return result;
    }
}
