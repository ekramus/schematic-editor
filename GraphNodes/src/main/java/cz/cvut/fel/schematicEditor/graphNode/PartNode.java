package cz.cvut.fel.schematicEditor.graphNode;

import cz.cvut.fel.schematicEditor.element.element.part.Part;

/**
 * This class represents Part Node. It contains link to {@link Part} and to
 * {@link GroupNode} with part's visual form.
 * 
 * @author Urban Kravjansky
 */
public class PartNode extends ElementNode {
	/**
	 * GroupNode containing graphic representation of part shape.
	 */
	private GroupNode partGroupNode;

	/**
	 * This is constructor.
	 * 
	 * @param part
	 *            <code>Part</code> stored in this <code>PartNode</code>.
	 */
	public PartNode(Part part, GroupNode partGroupNode) {
		super(part);
		setPartGroupNode(partGroupNode);
	}

	/**
	 * This is constructor.
	 * 
	 * @param part
	 *            <code>Part</code> stored in this <code>PartNode</code>.
	 * @param id
	 *            identifier of this <code>PartNode</code>.
	 */
	public PartNode(Part part, GroupNode partGroupNode, String id) {
		super(part, id);
		setPartGroupNode(partGroupNode);
	}

	/**
	 * @see cz.cvut.fel.schematicEditor.graphNode.Node#duplicate()
	 */
	@Override
	public Node duplicate() {
		PartNode result = new PartNode((Part) getElement(),
				(GroupNode) getPartGroupNode().duplicate());

		return result;
	}

	/**
	 * @param partGroupNode
	 *            the partGroupNode to set
	 */
	private void setPartGroupNode(GroupNode partGroupNode) {
		this.partGroupNode = partGroupNode;
	}

	/**
	 * @return the partGroupNode
	 */
	public GroupNode getPartGroupNode() {
		return partGroupNode;
	}
}
