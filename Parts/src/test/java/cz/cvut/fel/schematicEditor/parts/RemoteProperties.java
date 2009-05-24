package cz.cvut.fel.schematicEditor.parts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * @author Urban Kravjansky
 *
 */
public class RemoteProperties {
    private HashMap<String, HashMap<String, String>> instance;

    /**
     * This method instantiates new instance.
     *
     */
    public RemoteProperties() {
        setInstance(new HashMap<String, HashMap<String, String>>());
    }

    /**
     * Loads remote properties from given input stream.
     *
     * @param inputStream
     */
    public void load(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String buf;
        String key = null;
        HashMap<String, String> propertiesBlock = new HashMap<String, String>();

        try {
            for (buf = br.readLine(); buf != null; buf = br.readLine()) {
                // named block
                if (buf.indexOf("[") == 0) {
                    // store named properties block and reinitialize properties block hash map
                    if (key != null) {
                        getInstance().put(key, propertiesBlock);
                        propertiesBlock = new HashMap<String, String>();
                    }
                    // initialize new key
                    key = buf.replaceFirst("\\[(.*)\\]", "$1");
                }
                // property field
                else if (buf.indexOf("=") != -1) {
                    int i = buf.indexOf("=");
                    String name = buf.substring(0, i);
                    String value = buf.substring(i + 1, buf.length());
                    propertiesBlock.put(name, value);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @return the instance
     */
    public HashMap<String, HashMap<String, String>> getInstance() {
        return this.instance;
    }

    /**
     * @param instance the instance to set
     */
    public void setInstance(HashMap<String, HashMap<String, String>> instance) {
        this.instance = instance;
    }
}
