package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.element.properties.PartType;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.partPropertiesDialog.PartPropertiesDialogPanel;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.listeners.PartRotationCenterButtonActionListener;

/**
 * @author urban.kravjansky
 *
 */
/**
 * This class implements properties tool bar.
 *
 * @author Urban Kravjansky
 */
public class PartPropertiesPanel extends JPanel {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger              logger;
    /**
     * Singleton instance of {@link PartPropertiesPanel}.
     */
    private static PartPropertiesPanel partPropertiesPanel      = null;

    /**
     * Line width {@link JComboBox} instance.
     */
    private JComboBox                  partTypeComboBox         = null;
    /**
     * {@link JButton} for setting part rotation center.
     */
    private JButton                    partRotationCenterButton = null;
    /**
     * {@link JLabel} displaying part rotation center.
     */
    private JLabel                     partRotationCenterLabel  = null;

    /**
     * Default constructor. It is private for {@link PartPropertiesDialogPanel} singleton instance.
     */
    private PartPropertiesPanel() {
        super();

        logger = Logger.getLogger(Gui.class.getName());
    }

    /**
     * Getter for {@link PartPropertiesDialogPanel} singleton instance.
     *
     * @return {@link PartPropertiesDialogPanel} singleton instance.
     */
    public static PartPropertiesPanel getInstance() {
        if (partPropertiesPanel == null) {
            partPropertiesPanel = new PartPropertiesPanel();
            partPropertiesPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 25));
            partPropertiesPanel.setPreferredSize(new Dimension(210, 600));

            // add elements
            partPropertiesPanel.add(partPropertiesPanel.getPartTypeComboBox());
            partPropertiesPanel.add(partPropertiesPanel.getPartRotationCenterButton());
            partPropertiesPanel.add(partPropertiesPanel.getPartRotationCenterLabel());
        }
        return partPropertiesPanel;
    }

    /**
     * Updates {@link PartPropertiesDialogPanel} according to scene or selected element properties.
     */
    public void refresh() {
        try {
            if (Gui.getActiveScenePanel().getActiveManipulation().getManipulatedGroup().getElementType() == ElementType.T_PART) {
                Part part = (Part) Gui.getActiveScenePanel().getActiveManipulation().getManipulatedGroup()
                        .getChildrenElementList().getFirst().getElement();
                getInstance().getPartRotationCenterLabel().setText(part.getRotationCenter().toString());
            }
        } catch (NullPointerException npe) {
            logger.error("Probably no manipulation.");
        }
    }

    /**
     * Getter for <code>lineWidthComboBox</code>.
     *
     * @return <code>lineWidthComboBox</code> instance.
     */
    public final JComboBox getPartTypeComboBox() {
        if (this.partTypeComboBox == null) {
            this.partTypeComboBox = new JComboBox(PartType.values());
            this.partTypeComboBox.setEditable(false);
        }
        return this.partTypeComboBox;
    }

    /**
     * @return the partRotationCenterButton
     */
    private JButton getPartRotationCenterButton() {
        if (this.partRotationCenterButton == null) {
            this.partRotationCenterButton = new JButton("Rotation Center");
            this.partRotationCenterButton.addActionListener(new PartRotationCenterButtonActionListener());
        }
        return this.partRotationCenterButton;
    }

    /**
     * @return the partRotationCenterLabel
     */
    private JLabel getPartRotationCenterLabel() {
        if (this.partRotationCenterLabel == null) {
            this.partRotationCenterLabel = new JLabel();
        }
        return this.partRotationCenterLabel;
    }
}
