package cz.cvut.fel.schematicEditor.core.plugins.elementsCount.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * This class implements action listener for Elements count plugin menu item.
 *
 * @author Urban Kravjansky
 *
 */
public class ElementsCountActionListener implements ActionListener {

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {
        JOptionPane.showMessageDialog(null, "Hello plugin world.");
    }

}
