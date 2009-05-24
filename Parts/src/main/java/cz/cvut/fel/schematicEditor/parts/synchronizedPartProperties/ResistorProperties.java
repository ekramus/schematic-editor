package cz.cvut.fel.schematicEditor.parts.synchronizedPartProperties;

import java.util.Vector;

import cz.cvut.fel.schematicEditor.parts.PartType;
import cz.cvut.fel.schematicEditor.parts.SynchronizedPartProperties;

/**
 * This class implements properties with are unique for resistor part.
 *
 * @author Urban Kravjansky
 */
public class ResistorProperties extends SynchronizedPartProperties {
    /**
     * Prototype of netlist string, where codes will be replaced with values.
     */
    private final String netlistPrototype        = "r<name> <connectorP> <connectorM> [<value>] [<model>] [l=<length>] [w=<width>] [temp=<temperature>]";
    /**
     * Name of ini file for remote configuration.
     */
    private final String remoteConfigurationName = "r.ini";

    /**
     * This method instantiates new instance.
     */
    public ResistorProperties() {
        super();
    }

    /**
     * @see SynchronizedPartProperties#getNetlist()
     */
    @Override
    public String getNetlist() {
        return expandPrototype(this.netlistPrototype, this);
    }

    /**
     * @see SynchronizedPartProperties#getPartType()
     */
    @Override
    public PartType getPartType() {
        return PartType.RESISTOR;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.parts.SynchronizedPartProperties#getPartPinNames()
     */
    @Override
    public Vector<String> getPartPinNames() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see SynchronizedPartProperties#getRemoteConfigurationName()
     */
    @Override
    public String getRemoteConfigurationName() {
        return this.remoteConfigurationName;
    }
}
