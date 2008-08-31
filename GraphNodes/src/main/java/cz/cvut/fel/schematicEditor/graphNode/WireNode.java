package cz.cvut.fel.schematicEditor.graphNode;

import cz.cvut.fel.schematicEditor.element.element.part.Wire;

/**
 * This class represents Wire Node.
 * 
 * @author Urban Kravjansky
 */
public class WireNode extends ElementNode {
	/**
	 * This is constructor.
	 * 
	 * @param wire
	 *            <code>Wire</code> stored in this <code>WireNode</code>.
	 */
	public WireNode(Wire wire) {
		super(wire);
	}

	/**
	 * This is constructor.
	 * 
	 * @param wire
	 *            <code>Wire</code> stored in this <code>WireNode</code>.
	 * @param id
	 *            identifier of this <code>WireNode</code>.
	 */
	public WireNode(Wire wire, String id) {
		super(wire, id);
	}

	/**
	 * @see cz.cvut.fel.schematicEditor.graphNode.Node#duplicate()
	 */
	@Override
	public Node duplicate() {
		WireNode result = new WireNode((Wire) getElement());

		return result;
	}
}
