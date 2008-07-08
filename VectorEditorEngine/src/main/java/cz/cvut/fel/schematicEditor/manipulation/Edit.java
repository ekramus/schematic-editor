package cz.cvut.fel.schematicEditor.manipulation;

import cz.cvut.fel.schematicEditor.element.Element;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.types.Transformation;

/**
 * @author uk
 */
public class Edit extends Manipulation {
    private GroupNode manipulatedGroup = null;

    /**
     * @return the manipulatedGroup
     */
    public GroupNode getManipulatedGroup() {
        return this.manipulatedGroup;
    }

    /**
     * @param manipulatedGroup
     *            the groupNode to set
     */
    public void setManipulatedGroup(GroupNode manipulatedGroup) {
        this.manipulatedGroup = manipulatedGroup;
    }

    /**
     * 
     */
    protected Edit(Element element) {
        super(element);
        setManipulatedGroup(null);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#getManipulationType()
     */
    @Override
    public ManipulationType getManipulationType() {
        return ManipulationType.EDIT;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#isManipulatingElements()
     */
    @Override
    public boolean isManipulatingElements() {
        // TODO Auto-generated method stub
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#isManipulatingGroups()
     */
    @Override
    public boolean isManipulatingGroups() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.manipulation.Manipulation#newInstance(cz.cvut.fel.schematicEditor.manipulation.Manipulation)
     */
    @Override
    protected Manipulation duplitate() {
        Edit e = new Edit(getManipulatedElement());
        return e;
    }
}
