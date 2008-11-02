package cz.cvut.fel.schematicEditor.application.guiElements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class extends {@link JDialog} so it is possible to set properties on {@link PartPropertiesPanel}.
 *
 * @author Urban Kravjansky
 *
 */
public class PartPropertiesDialog extends JDialog implements ActionListener {
    private JPanel  myPanel   = null;
    private JButton yesButton = null;
    private JButton noButton  = null;
    boolean         answer;

    public boolean getAnswer() {
        return this.answer;
    }

    /**
     * This method instantiates new instance.
     *
     * @param frame parent frame.
     * @param modal modal flag.
     */
    public PartPropertiesDialog(JFrame frame, boolean modal) {
        super(frame, modal);

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
            System.err.println("User chose yes.");
            this.answer = true;
            setVisible(false);
        } else if (this.noButton == e.getSource()) {
            System.err.println("User chose no.");
            this.answer = false;
            setVisible(false);
        }
    }

}
