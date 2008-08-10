package cz.cvut.fel.schematicEditor.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * This class serves as application properties superclass. It implements methods used by child classes, which have are
 * responsible for properties of specified part of application.
 * 
 * @author Urban Kravjansky
 */
public class AppProperties {
    /**
     * This constant represents global properties file.
     */
    public static final String   GLOBAL_PROPERTIES = "properties.xml";
    /**
     * Application wide properties.
     */
    private Properties           properties;
    /**
     * {@link AppProperties} singleton instance.
     */
    private static AppProperties instance;
    /**
     * {@link Logger} instance for logging purposes.
     */
    static Logger                logger;

    /**
     * {@link AppProperties} constructor. It loads global properties file and log4j properties file.
     */
    protected AppProperties() {
        loadAppProperties();
        loadLog4JProperties();
    }

    /**
     * Loads general application properties.
     */
    private void loadAppProperties() {
        FileInputStream fis;

        try {
            fis = new FileInputStream(GLOBAL_PROPERTIES);
            getProperties().loadFromXML(fis);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidPropertiesFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Loads Log4J properties.
     */
    private void loadLog4JProperties() {
        DOMConfigurator.configure("log4j.xml");
        logger = Logger.getLogger(AppProperties.class.getName());
        logger.info("Log4J alive.");
    }

    /**
     * Getter for properties.
     * 
     * @return Properties defined in external file.
     */
    public Properties getProperties() {
        if (this.properties == null) {
            this.properties = new Properties();
        }
        return this.properties;
    }

    /**
     * @return the appProperties
     */
    public static AppProperties getInstance() {
        if (instance == null) {
            instance = new AppProperties();
        }
        return instance;
    }
}
