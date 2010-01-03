package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.listeners;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.element.shape.Text;
import cz.cvut.fel.schematicEditor.element.properties.ElementProperties;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.GeneralPropertiesPanel;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.PropertiesPanel;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;

/**
 * This class implements listener for {@link PropertiesPanel} textApplyButton.
 * 
 * @author Urban Kravjansky
 */
public class TextApplyButtonActionListener extends PropertiesToolBarListener implements
        ActionListener {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger       logger;
    /**
     * Font button title.
     */
    private static final String TEXT_APPLY_TITLE = "Apply";
    /**
     * Text apply {@link JButton} field.
     */
    private JButton             textApplyButton  = null;

    /**
     * {@link TextApplyButtonActionListener} constructor. It initializes
     * <code>textApplyButton</code> field with given parameter.
     * 
     * @param textApplyButton
     *            text apply {@link JButton} parameter.
     */
    public TextApplyButtonActionListener(final JButton textApplyButton) {
        logger = Logger.getLogger(Gui.class.getName());
        setTextApplyButton(textApplyButton);
        getTextApplyButton().setText(TEXT_APPLY_TITLE);
    }

    /**
     * Method is invoked as result to an action. It applies the change of text.
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param ae
     *            {@link ActionEvent} parameter. This parameter is only for implementing purposes,
     *            it is not used nor needed.
     */
    public final void actionPerformed(final ActionEvent ae) {
        ElementProperties ep = getElementProperties();

        ep.setFont(new Font(
                GeneralPropertiesPanel.getInstance().getTextFontTextField().getText(),
                Font.PLAIN,
                Integer.valueOf(GeneralPropertiesPanel.getInstance().getTextSizeTextField().getText())));
        Text text = (Text) Gui.getActiveScenePanel().getActiveManipulation().getManipulatedGroup().getChildrenElementList().getFirst().getElement();
        text.setValue(GeneralPropertiesPanel.getInstance().getTextValueTextField().getText());

        // update properties only when using Select manipulation
        try {
            updateProperties(ep);
        } catch (UnknownManipulationException ume) {
            logger.error(ume.getMessage());
        }
    }

    /**
     * Getter for <code>textApplyButton</code>.
     * 
     * @return the textApplyButton
     */
    private JButton getTextApplyButton() {
        return this.textApplyButton;
    }

    /**
     * Setter for <code>textApplyButton</code>.
     * 
     * @param textApplyButton
     *            the textApplyButton to set
     */
    private void setTextApplyButton(final JButton textApplyButton) {
        this.textApplyButton = textApplyButton;
    }
}
