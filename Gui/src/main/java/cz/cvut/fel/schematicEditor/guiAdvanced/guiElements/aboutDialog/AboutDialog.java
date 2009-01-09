package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.aboutDialog;

import java.awt.BorderLayout;
import java.util.Properties;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import cz.cvut.fel.schematicEditor.configuration.Configuration;
import cz.cvut.fel.schematicEditor.core.Structures;

/**
 * @author Urban Kravjansky
 */
public final class AboutDialog extends JDialog {
    /**
     * {@link AboutDialog} instance.
     */
    private static AboutDialog aboutDialog          = null;
    /**
     * {@link JPanel} instance used in about dialog.
     */
    private JPanel             aboutContentPane     = null;
    /**
     * {@link JLabel} instance used in about dialog. It shows current version label.
     */
    private JLabel             aboutVersionLabel    = null;
    /**
     * {@link JTextArea} instance showing about plugins string.
     */
    private JTextArea          aboutPluginsTextArea = null;

    /**
     * Default constructor. It initializes only one instance of {@link AboutDialog}. It is called only from
     * <code>getInstance</code> medthod.
     */
    private AboutDialog() {
        super();
    }

    /**
     * Singleton getter. It initializes new {@link AboutDialog} instance only in case, it is not already defined.
     *
     * @return instance of {@link AboutDialog}
     */
    public static AboutDialog getInstance() {
        if (aboutDialog == null) {
            aboutDialog = new AboutDialog();
            aboutDialog.setLocationByPlatform(true);
            aboutDialog.setContentPane(aboutDialog.getAboutContentPane());
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
            this.aboutContentPane.add(getAboutVersionLabel(), BorderLayout.NORTH);
            this.aboutContentPane.add(getAboutPluginsTextArea(), BorderLayout.CENTER);
        }
        return this.aboutContentPane;
    }

    /**
     * Getter for <code>aboutVersionLabel</code>. In case it is not initialized, it creates new instance.
     *
     * @return instance of <code>aboutVersionLabel</code>.
     */
    private JLabel getAboutVersionLabel() {
        if (this.aboutVersionLabel == null) {
            this.aboutVersionLabel = new JLabel();
            Configuration conf = Configuration.getInstance();
            this.aboutVersionLabel.setText("Version " + conf.getVersion());
            this.aboutVersionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        return this.aboutVersionLabel;
    }

    /**
     * Getter for <code>aboutPluginsTextArea</code>. In case it is not initialized, it creates new instance.
     *
     * @return instance of <code>aboutPluginsTextArea</code>.
     */
    private JTextArea getAboutPluginsTextArea() {
        if (this.aboutPluginsTextArea == null) {
            this.aboutPluginsTextArea = new JTextArea();

            String buf = "";
            for (Properties properties : Structures.getLoadedPluginProperties()) {
                buf += properties.getProperty("name") + "\n" + "\t-" + properties.getProperty("description") + "\n";
            }

            this.aboutPluginsTextArea.setText("Currently used plugins:\n\n" + buf);
            this.aboutPluginsTextArea.setEditable(false);
        }
        return this.aboutPluginsTextArea;
    }
}
