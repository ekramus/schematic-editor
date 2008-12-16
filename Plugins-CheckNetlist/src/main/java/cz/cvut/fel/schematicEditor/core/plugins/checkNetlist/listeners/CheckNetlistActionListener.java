package cz.cvut.fel.schematicEditor.core.plugins.checkNetlist.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JOptionPane;

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.element.element.part.Pin;
import cz.cvut.fel.schematicEditor.element.element.part.Wire;
import cz.cvut.fel.schematicEditor.graphNode.ConnectorNode;
import cz.cvut.fel.schematicEditor.graphNode.Node;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.graphNode.WireNode;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

/**
 * This class implements action listener for Elements count plugin menu item.
 *
 * @author Urban Kravjansky
 *
 */
public class CheckNetlistActionListener implements ActionListener {
    /**
     * {@link SceneGraph} instance.
     */
    SceneGraph sceneGraph;

    /**
     * This method instantiates new instance.
     */
    public CheckNetlistActionListener(SceneGraph sceneGraph) {
        super();

        setSceneGraph(sceneGraph);
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
        for (Node node : getSceneGraph().getSceneGraphArray()) {
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
            Vector<String> partConnectors = part.getPartProperties().getPartPinNames();

            result.append(" -part: " + part.getPartProperties().getProperty("name") + "\n");
            result.append("  -connectors: " + partConnectors + "\n");

            result.append("  -not connected connectors: " + checkPart(partNode, partNodeVector, wireVector) + "\n");
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

    /**
     * Checks part, how is it connected.
     *
     * @param partNode
     * @param wireVector
     * @return Number of not connected part connectors.
     */
    private final int checkPart(final PartNode partNode, final Vector<PartNode> partNodes, final Vector<Wire> wireVector) {
        int notConnectedConnectors = 0;
        Vector<String> connectorNames = ((Part) partNode.getElement()).getPartProperties().getPartPinNames();

        int i = 0;
        for (ConnectorNode connectorNode : partNode.getPartConnectors()) {
            Pin pin = (Pin) connectorNode.getElement();
            Wire wire = getWireForConnector(pin, wireVector);
            // wire was found, now we can validate all connectors on that wire
            if (wire != null) {
                Vector<String> matchedConnectorNames = getConnectorNamesForConnectorAndWire(pin, wire, partNodes);
                // compare all retrieved connectors (names) with name of connector
                if (!matchedConnectorNames.isEmpty()) {
                    for (String matchedConnectorName : matchedConnectorNames) {
                        if (!matchedConnectorName.equals(connectorNames.get(i))) {
                            System.err.println("Pin " + connectorNames.get(i)
                                    + " does not match its conterpart "
                                    + matchedConnectorName);
                        }
                    }
                } else {
                    System.err.println("Pin " + connectorNames.get(i) + " is not connected anywhere");
                }
            }
            // wire was not found
            else {
                notConnectedConnectors++;
            }

            i++;
        }

        return notConnectedConnectors;
    }

    /**
     * @param c
     * @param wire
     */
    private Vector<String> getConnectorNamesForConnectorAndWire(Pin pin, Wire wire, Vector<PartNode> partNodes) {
        Vector<String> result = new Vector<String>();

        UnitPoint cup = new UnitPoint(pin.getX().firstElement(), pin.getY().firstElement());
        for (int i = 0; i < wire.getX().size(); i++) {
            UnitPoint wup = new UnitPoint(wire.getX().get(i), wire.getY().get(i));
            // cup is not wup, so there can be another part attached (I suggest only one part is attached to connector,
            // which can be wrong, but will be messy anyway)
            if (!wup.equals(cup)) {
                String s = getConnectorNameForUnitPoint(wup, partNodes);
                if (s != null) {
                    result.add(s);
                }
            }
        }

        return result;
    }

    /**
     * Searches for connector in all part nodes from partNodeVector.
     *
     * @param wup
     * @param partNodes
     * @return
     */
    private String getConnectorNameForUnitPoint(UnitPoint wup, Vector<PartNode> partNodes) {
        for (PartNode partNode : partNodes) {
            Vector<ConnectorNode> cnv = partNode.getPartConnectors();
            Vector<String> connectorNames = ((Part) partNode.getElement()).getPartProperties().getPartPinNames();
            int i = 0;
            // search for connector name
            for (ConnectorNode cn : cnv) {
                Pin c = (Pin) cn.getElement();
                UnitPoint cup = new UnitPoint(c.getX().firstElement(), c.getY().firstElement());
                if (cup.equals(wup)) {
                    return connectorNames.get(i);
                }
                i++;
            }
        }

        // nothing found
        return null;
    }

    /**
     * Retrieves {@link Wire} attached to given {@link Pin}.
     *
     * @param pin
     * @param wireVector
     * @return
     */
    private final Wire getWireForConnector(Pin pin, Vector<Wire> wireVector) {
        UnitPoint cup = new UnitPoint(pin.getX().firstElement(), pin.getY().firstElement());
        for (Wire wire : wireVector) {
            for (int i = 0; i < wire.getX().size(); i++) {
                UnitPoint wup = new UnitPoint(wire.getX().get(i), wire.getY().get(i));
                if (wup.equals(cup)) {
                    return wire;
                }
            }
        }

        return null;
    }

    /**
     * @param sceneGraph the sceneGraph to set
     */
    private void setSceneGraph(SceneGraph sceneGraph) {
        this.sceneGraph = sceneGraph;
    }

    /**
     * @return the sceneGraph
     */
    private SceneGraph getSceneGraph() {
        return this.sceneGraph;
    }
}
