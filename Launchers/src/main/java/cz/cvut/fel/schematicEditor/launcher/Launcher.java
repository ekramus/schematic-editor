package cz.cvut.fel.schematicEditor.launcher;

import java.net.URL;

import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.apache.log4j.helpers.Loader;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * This class implements methods used in all {@link Launcher} classes. This methods are mainly used for configuration.
 *
 * @author Urban Kravjansky
 */
public abstract class Launcher {
    /**
     * {@link Logger} instance for logging purposes.
     */
    static Logger logger;

    /**
     * Set user interface to preferred one (in this case PlasticXP).
     */
    static final void setUI() {
        try {
            logger = Logger.getLogger(Launcher.class.getName());
            // UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            logger.info("Application started");
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }

    /**
     * Loads Log4J properties.
     */
    static void loadLog4JProperties() {
        try {
            URL url = Loader.getResource("META-INF/log4j.xml");
            DOMConfigurator.configure(url);

            logger = Logger.getLogger(Launcher.class.getName());
            logger.info("Log4J alive.");
        } catch (NullPointerException e) {
            System.err.println("Logger not initialized");
        }
    }
}
