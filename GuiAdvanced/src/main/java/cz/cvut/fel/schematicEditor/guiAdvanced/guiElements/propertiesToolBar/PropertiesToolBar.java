package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesToolBar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import org.apache.log4j.Logger;

/**
 * This class implements properties tool bar.
 *
 * @author Urban Kravjansky
 */
public class PropertiesToolBar extends JToolBar {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger            logger;
    /**
     * Singleton instance of {@link PropertiesToolBar}.
     */
    private static PropertiesToolBar propertiesToolBar = null;
    private JTabbedPane              tabbedPane        = null;

    /**
     * Default constructor. It is private for {@link PropertiesToolBar} singleton instance.
     */
    private PropertiesToolBar() {
        super();

        logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * Getter for {@link PropertiesToolBar} singleton instance.
     *
     * @return {@link PropertiesToolBar} singleton instance.
     */
    public static PropertiesToolBar getInstance() {
        if (propertiesToolBar == null) {
            propertiesToolBar = new PropertiesToolBar();
            propertiesToolBar.setLayout(new BorderLayout());
            propertiesToolBar.setPreferredSize(new Dimension(225, 400));

            // create ToolBar
            JToolBar tb = new JToolBar(VERTICAL);
            JButton b = new JButton();

            BufferedImage bi = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = (Graphics2D) bi.getGraphics();

            g2d.setColor(Color.RED);
            g2d.rotate(Math.PI / 2.0);
            g2d.drawString("pokus", 10, -50);
            g2d.setColor(Color.BLUE);
            g2d.rotate(Math.PI);
            g2d.drawString("pokus", -50, 30);
            b.setIcon(new ImageIcon(bi));

            tb.add(b);

            // add elements
            propertiesToolBar.add(propertiesToolBar.getTabbedPane(), BorderLayout.CENTER);
            propertiesToolBar.add(tb, BorderLayout.EAST);
        }
        return propertiesToolBar;
    }

    /**
     * @return the tabbedPane
     */
    public JTabbedPane getTabbedPane() {
        if (this.tabbedPane == null) {
            this.tabbedPane = new JTabbedPane();
            this.tabbedPane.setVisible(true);
            this.tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

            // add elements
            this.tabbedPane.addTab("general properties", GeneralPropertiesPanel.getInstance());
            this.tabbedPane.addTab("part properties", PartPropertiesPanel.getInstance());
        }
        return this.tabbedPane;
    }
}
