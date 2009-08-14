package cz.cvut.fel.schematicEditor.core.plugins.checkNetlist.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.element.element.part.Junction;
import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.element.element.part.Pin;
import cz.cvut.fel.schematicEditor.element.element.part.Wire;
import cz.cvut.fel.schematicEditor.graphNode.JunctionNode;
import cz.cvut.fel.schematicEditor.graphNode.Node;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.graphNode.PinNode;
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
        Vector<Junction> junctionVector = new Vector<Junction>();

        StringBuilder result = new StringBuilder();
        int partsFound = 0;

        // build wire, part and junction vectors for checking purposes
        for (Node node : getSceneGraph().getSceneGraphArray()) {
            if (node instanceof PartNode) {
                partNodeVector.add((PartNode) node);
                partsFound++;
            } else if (node instanceof WireNode) {
                wireVector.add((Wire) ((WireNode) node).getElement());
            } else if (node instanceof JunctionNode) {
                junctionVector.add((Junction) ((JunctionNode) node).getElement());
            }
        }

        // go through part vector and check one part after another
        for (PartNode partNode : partNodeVector) {
            Part part = (Part) partNode.getElement();
            ArrayList<String> partPins = part.getPartProperties().getPartPinValues();

            result.append(" -part: " + part.getPartProperties().getProperty("name") + "\n");
            result.append("  -connectors: " + partPins + "\n");

            result.append("  -not connected connectors: " + checkPart(partNode, partNodeVector, wireVector,
                                                                      junctionVector)
                    + "\n");
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
     * @param partNode {@link Vector} of part nodes.
     * @param wireVector {@link Vector} of {@link Wire}s.
     * @param junctionVector {@link Vector} of {@link Junction}s.
     * @return Number of not connected part connectors.
     */
    private final int checkPart(final PartNode partNode, final Vector<PartNode> partNodes,
            final Vector<Wire> wireVector, final Vector<Junction> junctionVector) {
        int notConnectedConnectors = 0;
        ArrayList<String> pinNames = ((Part) partNode.getElement()).getPartProperties().getPartPinValues();

        int i = 0;
        for (PinNode pinNode : partNode.getPartPins()) {
            Pin pin = (Pin) pinNode.getElement();
            Wire wire = getWireForPin(pin, wireVector);
            // wire was found, now we can validate all pins on that wire
            if (wire != null) {
                Vector<String> matchedPinNames = getPinNamesForPinAndWire(pin, wire, partNodes, wireVector,
                                                                          junctionVector, 2);
                // compare all retrieved connectors (names) with name of connector
                if (!matchedPinNames.isEmpty()) {
                    for (String matchedPinName : matchedPinNames) {
                        if (!matchedPinName.equals(pinNames.get(i))) {
                            System.err.println("Pin " + pinNames.get(i)
                                    + " does not match its counterpart "
                                    + matchedPinName);
                        }
                    }
                } else {
                    System.err.println("Pin " + pinNames.get(i) + " is not connected anywhere");
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
     * Retrieves all pin names of pins attached to specified pin via specified wire.
     *
     * @param pin specified {@link Pin}.
     * @param wire specified {@link Wire} connected to specified pin.
     * @param partNodes {@link Vector} of part nodes.
     * @param junctionVector {@link Vector} of {@link Junction}s.
     * @param wireVector {@link Vector} of {@link Wire}s.
     * @param recursionDepth indicates, how many recursive calls of this method are allowed.
     *
     * @return Pin names of pins connected to specified pin by given wire.
     */
    private Vector<String> getPinNamesForPinAndWire(Pin pin, Wire wire, Vector<PartNode> partNodes,
            Vector<Wire> wireVector, Vector<Junction> junctionVector, int recursionDepth) {
        Vector<String> result = new Vector<String>();

        UnitPoint pup = new UnitPoint(pin.getX().firstElement(), pin.getY().firstElement());
        for (int i = 0; i < wire.getX().size(); i++) {
            UnitPoint wup = new UnitPoint(wire.getX().get(i), wire.getY().get(i));
            // pup is not wup, so there can be another part or junction attached (I suggest only one part is attached to
            // connector, which can be wrong, but will be messy anyway)
            if (!wup.equals(pup)) {
                Junction j = getJunctionForUnitPoint(wup, junctionVector);
                if (j != null) {
                    // get wire for junction
                    Vector<Wire> junctionWireVector = getWiresForJunctionAndWire(j, wireVector);
                    // add pin names of all wires attached to junction, recursion limit avoids circural dependencies to
                    // crash plugin
                    if (!junctionWireVector.isEmpty() && (recursionDepth > 0)) {
                        for (Wire w : junctionWireVector) {
                            result.addAll(getPinNamesForPinAndWire(pin, w, partNodes, wireVector, junctionVector,
                                                                   recursionDepth - 1));
                        }
                    }
                } else {
                    // add pin name, if pin found
                    String s = getPinNameForUnitPoint(wup, partNodes);
                    if (s != null) {
                        result.add(s);
                    }
                }
            }
        }

        return result;
    }

    /**
     * Finds {@link Junction} on given {@link UnitPoint} and returns it.
     *
     * @param up referenced {@link UnitPoint}.
     * @param junctionVector {@link Vector} of {@link Junction}s.
     *
     * @return Found {@link Junction} reference or null in case nothing was found.
     */
    private Junction getJunctionForUnitPoint(UnitPoint up, Vector<Junction> junctionVector) {
        for (Junction junction : junctionVector) {
            UnitPoint jup = new UnitPoint(junction.getX().firstElement(), junction.getY().firstElement());
            if (jup.equals(up)) {
                return junction;
            }
        }
        return null;
    }

    /**
     * Finds all {@link Wire}s attached to given {@link Junction}.
     *
     * @param junction referenced {@link Junction}.
     * @param wireVector {@link Vector} of {@link Wire}s used for searching.
     * @return {@link Vector} of {@link Wire}s attached to given {@link Junction}.
     */
    private Vector<Wire> getWiresForJunctionAndWire(Junction junction, Vector<Wire> wireVector) {
        Vector<Wire> result = new Vector<Wire>();

        UnitPoint jup = new UnitPoint(junction.getX().firstElement(), junction.getY().firstElement());
        for (Wire wire : wireVector) {
            for (int i = 0; i < wire.getX().size(); i++) {
                UnitPoint wup = new UnitPoint(wire.getX().get(i), wire.getY().get(i));
                if (jup.equals(wup)) {
                    result.add(wire);
                    break;
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
    private String getPinNameForUnitPoint(UnitPoint wup, Vector<PartNode> partNodes) {
        for (PartNode partNode : partNodes) {
            Vector<PinNode> cnv = partNode.getPartPins();
            ArrayList<String> pinNames = ((Part) partNode.getElement()).getPartProperties().getPartPinValues();
            int i = 0;
            // search for connector name
            for (PinNode cn : cnv) {
                Pin c = (Pin) cn.getElement();
                UnitPoint cup = new UnitPoint(c.getX().firstElement(), c.getY().firstElement());
                if (cup.equals(wup)) {
                    return pinNames.get(i);
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
    private final Wire getWireForPin(Pin pin, Vector<Wire> wireVector) {
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
