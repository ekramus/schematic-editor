package cz.cvut.fel.schematicEditor.launcher;

import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.jgoodies.plaf.plastic.PlasticXPLookAndFeel;

/**
 * This class implements methods used in all {@link Launcher} classes. This methods are mainly used for configuration.
 * 
 * @author Urban Kravjanský
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
            UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
            logger.info("Application started");
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
    }
}
