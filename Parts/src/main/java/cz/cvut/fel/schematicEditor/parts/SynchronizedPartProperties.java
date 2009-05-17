package cz.cvut.fel.schematicEditor.parts;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Urban Kravjansky
 *
 *
 */
public class SynchronizedPartProperties {
    /**
     * {@link HashMap} containing all relevant properties.
     */
    private HashSet<String> synchronizedPartProperties;

    /**
     * This method instantiates new instance.
     *
     */
    public SynchronizedPartProperties() {
        setSynchronizedPartProperties(new HashSet<String>());
    }

    /**
     * Updates this instance with given {@link SynchronizedPartProperties}.
     *
     * @param spp {@link SynchronizedPartProperties} to use for update.
     */
    public void update(SynchronizedPartProperties spp) {
        // TODO implement
    }



    /**
     * @return the synchronizedPartProperties
     */
    private HashSet<String> getSynchronizedPartProperties() {
        return this.synchronizedPartProperties;
    }

    /**
     * @param synchronizedPartProperties the synchronizedPartProperties to set
     */
    private void setSynchronizedPartProperties(HashSet<String> synchronizedPartProperties) {
        this.synchronizedPartProperties = synchronizedPartProperties;
    }
}
