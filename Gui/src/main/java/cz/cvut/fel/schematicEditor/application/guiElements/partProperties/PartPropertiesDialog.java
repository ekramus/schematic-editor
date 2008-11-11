package cz.cvut.fel.schematicEditor.application.guiElements.partProperties;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.properties.PartProperties;

/**
 * This class extends {@link JDialog} so it is possible to set properties on {@link PartPropertiesPanel}.
 *
 * @author Urban Kravjansky
 *
 */
public class PartPropertiesDialog extends JDialog implements ActionListener {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;
    private JPanel        myPanel   = null;
    private JButton       yesButton = null;
    private JButton       noButton  = null;
    /**
     * Accept status of dialog.
     */
    private boolean       accepted  = false;

    /**
     * This method instantiates new instance.
     *
     * @param frame parent frame.
     * @param modal modal flag.
     */
    public PartPropertiesDialog(JFrame frame, boolean modal) {
        super(frame, modal);

        logger = Logger.getLogger(this.getClass().getName());

        // this.myPanel = PartPropertiesPanel.getInstance();
        this.myPanel = new JPanel();
        getContentPane().add(this.myPanel);

        this.myPanel.add(PartPropertiesPanel.getInstance());

        this.yesButton = new JButton("Yes");
        this.yesButton.addActionListener(this);
        this.myPanel.add(this.yesButton);
        this.noButton = new JButton("No");
        this.noButton.addActionListener(this);
        this.myPanel.add(this.noButton);
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        if (this.yesButton == e.getSource()) {
            logger.trace("User chose yes.");
            PartPropertiesPanel.getInstance().actualizeProperties();
            setAccepted(true);
        } else if (this.noButton == e.getSource()) {
            logger.trace("User chose no.");
            setAccepted(false);
        }
        setVisible(false);
    }

    /**
     * @return the accepted
     */
    public boolean isAccepted() {
        return this.accepted;
    }

    /**
     * @param accepted the accepted to set
     */
    private void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
