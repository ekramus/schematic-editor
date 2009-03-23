package cz.cvut.fel.schematicEditor.configuration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;

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
     * Application version string.
     */
    private final String         version  = "1.3.1";

    /**
     * Deserializes configuration from given file.
     *
     * @param clazz Class of deserialized configuration.
     * @param file Path to file, where is serialized {@link Configuration}.
     * @return Deserialized {@link Configuration} class.
     */
    public static Configuration deserialize(Class<? extends Configuration> clazz, String file) {
        XStream xstream = new XStream(new DomDriver());

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            processAnnotations(xstream, clazz);
            return (Configuration) xstream.fromXML(br);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * @return the FILE
     */
    public static String getFile() {
        return Configuration.FILE;
    }

    /**
     * @return the appProperties
     */
    public static Configuration getInstance() {
        if (instance == null) {
            instance = Configuration.deserialize(Configuration.class, FILE);
            if (instance == null) {
                instance = new Configuration();
            }
        }
        return instance;
    }

    /**
     * Serializes given configuration into given file.
     *
     * @param configuration {@link Configuration} file to serialize.
     * @param file Path to file, where should be {@link Configuration} serialized.
     */
    public static void serialize(Configuration configuration, String file) {
        XStream xstream = new XStream(new DomDriver());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            processAnnotations(xstream, configuration.getClass());
            xstream.toXML(configuration, bw);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Processes all {@link XStream} annotations in entered classes.
     *
     * @param xstream {@link XStream} instance to configure.
     * @param clazz Class of configuration object.
     */
    private static void processAnnotations(XStream xstream, Class<? extends Configuration> clazz) {
        xstream.processAnnotations(clazz);
        xstream.processAnnotations(Unit.class);
        xstream.processAnnotations(Pixel.class);
    }

    /**
     * {@link Configuration} constructor. It loads global properties file and log4j properties file.
     */
    protected Configuration() {
        // nothing to do
    }

    /**
     * @return version instance.
     */
    public String getVersion() {
        return this.version;
    }
}
