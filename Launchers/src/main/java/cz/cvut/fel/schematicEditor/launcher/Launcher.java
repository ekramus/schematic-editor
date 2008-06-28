package cz.cvut.fel.schematicEditor.launcher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.jgoodies.plaf.plastic.PlasticXPLookAndFeel;

import cz.cvut.fel.schematicEditor.application.Gui;
import cz.cvut.fel.schematicEditor.core.Constants;
import cz.cvut.fel.schematicEditor.core.Structures;

/**
 * This class implements methods used in all {@link Launcher} classes. This methods are mainly used
 * for configuration.
 *
 * @author Urban Kravjanský
 */
public abstract class Launcher {
    /**
     * {@link Logger} instance for logging purposes.
     */
    static Logger logger;

    /**
     * Method used for properties loading. It loads global properties file and log4j properties
     * file.
     */
    static final void loadProperties() {
        // load properties
        Structures.setProperties(new Properties());
        FileInputStream fis;

        try {
            fis = new FileInputStream(Constants.GLOBAL_PROPERTIES);
            Structures.getProperties().loadFromXML(fis);
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

        // PropertyConfigurator.configure("log4j.properties");
        DOMConfigurator.configure("log4j.xml");
        logger = Logger.getLogger(Gui.class.getName());
        logger.info("Application started.");
    }

    /**
     * Set user interface to preferred one (in this case PlasticXP).
     */
    static final void setUI() {
        try {
            UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }
}
