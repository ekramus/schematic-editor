package cz.cvut.fel.schematicEditor.properties;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * This class serves as application properties superclass. It implements methods used by child classes, which have are
 * responsible for properties of specified part of application.
 * 
 * @author Urban Kravjansky
 */
public class Configuration {
    /**
     * File name, where should be this class serialized.
     */
    private static final String  FILE     = "config/application.xml";
    /**
     * {@link Configuration} singleton instance.
     */
    private static Configuration instance = null;

    /**
     * @return the appProperties
     */
    public static Configuration getInstance() {
        if (instance == null) {
            instance = Configuration.deserialize(FILE);
            if (instance == null) {
                instance = new Configuration();
            }
        }
        return instance;
    }

    /**
     * {@link Configuration} constructor. It loads global properties file and log4j properties file.
     */
    protected Configuration() {
        // nothing to do
    }

    /**
     * Deserializes configuration from given file.
     * 
     * @param file Path to file, where is serialized {@link Configuration}.
     * @return Deserialized {@link Configuration} class.
     */
    public static Configuration deserialize(String file) {
        XStream xstream = new XStream(new DomDriver());

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            return (Configuration) xstream.fromXML(br);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Serializes given configuration into given file.
     * 
     * @param configuration {@link Configuration} file to serialize.
     * @param file Path to file, where should be {@link Configuration} serialized.
     */
    public static void serialize(Configuration configuration, String file) {
        XStream xstream = new XStream(new DomDriver());

        // xstream.alias("person", Person.class);
        // xstream.alias("phonenumber", PhoneNumber.class);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            xstream.toXML(configuration, bw);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @return the FILE
     */
    public static String getFile() {
        return Configuration.FILE;
    }
}
