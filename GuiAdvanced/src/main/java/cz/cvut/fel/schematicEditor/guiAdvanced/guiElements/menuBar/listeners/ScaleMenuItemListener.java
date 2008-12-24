package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.guiAdvanced.GuiAdvanced;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;

/**
 * This class implements {@link ActionListener} for <code>scaleMenuItem</code> in {@link MenuBar}.
 *
 * @author Urban Kravjansky
 */
public final class ScaleMenuItemListener implements ActionListener {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public ScaleMenuItemListener() {
        super();

        logger = Logger.getLogger(this.getClass().getName());
    }

    // FIXME add externalized strings

    /**
     * Method invoked as result to an action. It initializes new {@link JDialog} instance to enter scale factor.
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param e {@link ActionEvent} parameter. This parameter is only for implementing purposes, it is not used nor
     *            needed.
     */
    public void actionPerformed(ActionEvent e) {
        String result = JOptionPane.showInputDialog(GuiAdvanced.getActiveScenePanel(),
                                                    "Choose scale factor (in percents)", "Scale factor",
                                                    JOptionPane.QUESTION_MESSAGE);

        double value;
        try {
            value = Double.parseDouble(result);
        } catch (NumberFormatException nfe) {
            logger.trace("Wrong scale value");
            return;
        }

        GuiAdvanced.getActiveScenePanel().setZoomFactor(value / 100);
        GuiAdvanced.getActiveScenePanel().setGridValid(false);
        GuiAdvanced.getActiveScenePanel().schemeInvalidate(null);
    }
}
