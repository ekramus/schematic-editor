package cz.cvut.fel.schematicEditor.guiAdvanced;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.BevelBorder;

/**
 * This class represents status bar GUI elements.
 *
 * @author Urban Kravjanský
 */
public class StatusBar extends JPanel {
    /**
     * Status bar singleton instance used for status bar representation.
     */
    private static StatusBar statusBar         = null;
    /**
     * JLabel for showing coordinates.
     */
    private static JLabel    coordinatesJLabel = null;
    private static JLabel    sizeLockingLabel  = null; ;

    /**
     * Default constructor for singleton class.
     */
    private StatusBar() {
        super();
    }

    /**
     * Getter for singleton instance.
     *
     * @return the statusBar
     */
    public static StatusBar getInstance() {
        if (statusBar == null) {
            statusBar = new StatusBar();
            statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
            statusBar.setLayout(new FlowLayout(FlowLayout.LEADING));
            statusBar.add(statusBar.getCoordinatesLabel());
            statusBar.add(new JSeparator());
            statusBar.add(statusBar.getSizeLockingLabel());
        }
        return statusBar;
    }

    private JLabel getCoordinatesLabel() {
        if (coordinatesJLabel == null) {
            coordinatesJLabel = new JLabel("X: Y: ");
        }
        return coordinatesJLabel;
    }

    public void setCoordinatesJLabel(String text) {
        coordinatesJLabel.setText(text);
    }

    private JLabel getSizeLockingLabel() {
        if (sizeLockingLabel == null) {
            sizeLockingLabel = new JLabel("to enable size locking, press CTRL");
        }
        return sizeLockingLabel;
    }

    /**
     * @param sizeLockingLabel
     *            the sizeLockingLabel to set
     */
    public void setSizeLockingLabel(String text) {
        sizeLockingLabel.setText(text);
    }
}
