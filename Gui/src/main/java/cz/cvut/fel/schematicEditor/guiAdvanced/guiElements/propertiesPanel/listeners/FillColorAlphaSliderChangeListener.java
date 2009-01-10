package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.listeners;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.properties.ElementProperties;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.guiAdvanced.Gui;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;

/**
 * This class implements {@link ChangeListener} for <code>fillColorAlphaSlider</code>.
 *
 * @author Urban Kravjansky
 */
public class FillColorAlphaSliderChangeListener extends PropertiesToolBarListener implements
        ChangeListener {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;
    /**
     * Points to <code>fillColorAlphaSlider</code> instance.
     */
    private JSlider       fillColorAlphaSlider = null;

    /**
     * {@link FillColorAlphaSliderChangeListener} constructor. It initializes
     * <code>fillColorAlphaSlider</code> field.
     *
     * @param fillColorAlphaSlider
     *            fill color alpha {@link JSlider} parameter.
     */
    public FillColorAlphaSliderChangeListener(JSlider fillColorAlphaSlider) {
        logger = Logger.getLogger(Gui.class.getName());
        setFillColorAlphaSlider(fillColorAlphaSlider);
    }

    /**
     * Method is invoked as a result to an action. It sets proper fill color alpha value and updates
     * properties.
     *
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     * @param ce
     *            {@link ChangeEvent} parameter. This parameter is only for implementing purposes,
     *            it is not used nor needed.
     */
    public void stateChanged(ChangeEvent ce) {
        ElementProperties ep = getElementProperties();

        ep.setFillColorAlpha(getFillColorAlphaSlider().getValue());

        // Select manipulation
        try {
            updateProperties(ep);
        } catch (UnknownManipulationException ume) {
            logger.error(ume.getMessage());
        }
    }

    /**
     * Setter for <code>fillColorAlphaSlider</code>.
     *
     * @param fillColorAlphaSlider
     *            the <code>fillColorAlphaSlider</code> to set.
     */
    private void setFillColorAlphaSlider(JSlider fillColorAlphaSlider) {
        this.fillColorAlphaSlider = fillColorAlphaSlider;
    }

    /**
     * Getter for <code>fillColorAlphaSlider</code>.
     *
     * @return The <code>fillColorAlphaSlider</code> instance.
     */
    private JSlider getFillColorAlphaSlider() {
        return this.fillColorAlphaSlider;
    }
}
