package cz.cvut.fel.schematicEditor.core;

import java.util.HashMap;

/**
 * This class implements support plugin data structures.
 *
 * @author Urban Kravjansky
 */
public class PluginData {
    /**
     * Plugin data structure.
     */
    private HashMap<String, HashMap<String, Object>> dataMap;

    /**
     * This method instantiates new instance.
     *
     */
    public PluginData() {
        setDataMap(new HashMap<String, HashMap<String, Object>>());
    }

    /**
     * @param pluginData the pluginData to set
     */
    private void setDataMap(HashMap<String, HashMap<String, Object>> pluginData) {
        this.dataMap = pluginData;
    }

    /**
     * @return the pluginData
     */
    private HashMap<String, HashMap<String, Object>> getDataMap() {
        return this.dataMap;
    }

    /**
     * Getter for data stored for given key.
     *
     * @param key plugin name.
     * @return collection of stored data.
     */
    public HashMap<String, Object> getData(String key) {
        return getDataMap().get(key);
    }

    /**
     * Setter for data stored for given key.
     *
     * @param key plugin name.
     * @param data collection of data to be stored.
     */
    public void setData(String key, HashMap<String, Object> data) {
        getDataMap().put(key, data);
    }
}
