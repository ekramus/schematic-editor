package cz.cvut.fel.schematicEditor.core.plugins.checkNetlist.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JOptionPane;

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.element.element.part.Wire;
import cz.cvut.fel.schematicEditor.graphNode.Node;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.graphNode.WireNode;

/**
 * This class implements action listener for Elements count plugin menu item.
 *
 * @author Urban Kravjansky
 *
 */
public class CheckNetlistActionListener implements ActionListener {
    /**
     * This method instantiates new instance.
     */
    public CheckNetlistActionListener() {
        super();
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {
        String status;

        status = checkNetlist();

        JOptionPane.showMessageDialog(null, "Netlist status:\n" + status);
    }

    /**
     * Checks netlist, whether are parts correctly connected.
     *
     * @return
     */
    private final String checkNetlist() {
        Vector<PartNode> partNodeVector = new Vector<PartNode>();
        Vector<Wire> wireVector = new Vector<Wire>();
        StringBuilder result = new StringBuilder();
        int partsFound = 0;

        // build wire and part vectors for checking purposes
        for (Node node : SceneGraph.getInstance().getSceneGraphArray()) {
            if (node instanceof PartNode) {
                partNodeVector.add((PartNode) node);
                partsFound++;
            } else if (node instanceof WireNode) {
                wireVector.add((Wire) ((WireNode) node).getElement());
            }
        }

        // go through part vector and check one part after another
        for (PartNode partNode : partNodeVector) {
            Part part = (Part) partNode.getElement();
            Vector<String> partConnectors = part.getPartProperties().getPartConnectors();

            result.append(" -part: " + part.getPartProperties().getProperty("name") + "\n");
            result.append("  -connectors: " + partConnectors + "\n");

            checkPartNetlist(partNode, wireVector);
        }

        // if there was no part found, inform about it
        if (partsFound == 0) {
            result.append("\t-no part found!");
        }
        // at least one part was found
        else {
            result.append("\t-parts processed: " + partsFound);
        }

        return result.toString();
    }

    private final int checkPartNetlist(final PartNode partNode, final Vector<Wire> wireVector) {
        int notConnectedConnectors = 0;

        return notConnectedConnectors;
    }

}
