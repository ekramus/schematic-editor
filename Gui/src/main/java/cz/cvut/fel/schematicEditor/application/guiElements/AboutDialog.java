package cz.cvut.fel.schematicEditor.application.guiElements;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import cz.cvut.fel.schematicEditor.application.Application;

/**
 * @author Urban Kravjansky
 */
public final class AboutDialog extends JDialog {
    /**
     * {@link AboutDialog} instance.
     */
    private static AboutDialog aboutDialog       = null;
    /**
     * {@link JPanel} instance used in about dialog.
     */
    private JPanel             aboutContentPane  = null;
    /**
     * {@link JLabel} instance used in about dialog. It shows current version label.
     */
    private JLabel             aboutVersionLabel = null;

    /**
     * Default constructor. It initializes only one instance of {@link AboutDialog}. It is called
     * only from <code>getInstance</code> medthod.
     */
    private AboutDialog() {
        super();
    }

    /**
     * Singleton getter. It initializes new {@link AboutDialog} instance only in case, it is not
     * already defined.
     * 
     * @return instance of {@link AboutDialog}
     */
    public static AboutDialog getInstance() {
        if (aboutDialog == null) {
            aboutDialog = new AboutDialog();
            aboutDialog.setLocationByPlatform(true);
            aboutDialog.setContentPane(aboutDialog.getAboutContentPane());
            aboutDialog.getContentPane().add(aboutDialog.getAboutVersionLabel());
        }
        return aboutDialog;
    }

    /**
     * Getter for <code>aboutContentPane</code>. In case it is not initialized, it creates new
     * <code>aboutContentPane</code> instance.
     * 
     * @return instance of <code>aboutContentPane</code>.
     */
    private JPanel getAboutContentPane() {
        if (this.aboutContentPane == null) {
            this.aboutContentPane = new JPanel();
            this.aboutContentPane.setLayout(new BorderLayout());
            this.aboutContentPane.add(getAboutVersionLabel(), BorderLayout.CENTER);
        }
        return this.aboutContentPane;
    }

    /**
     * Getter for <code>aboutVersionLabel</code>. In case it is not initialized, it creates new
     * instance.
     * 
     * @return instance of <code>aboutVersionLabel</code>.
     */
    private JLabel getAboutVersionLabel() {
        if (this.aboutVersionLabel == null) {
            this.aboutVersionLabel = new JLabel();
            this.aboutVersionLabel.setText("Version " + Application.version);
            this.aboutVersionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        return this.aboutVersionLabel;
    }
}
