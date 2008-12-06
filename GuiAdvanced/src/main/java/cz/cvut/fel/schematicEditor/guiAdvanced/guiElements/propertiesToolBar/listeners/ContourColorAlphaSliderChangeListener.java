package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesToolBar.listeners;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.properties.ElementProperties;
import cz.cvut.fel.schematicEditor.guiAdvanced.GuiAdvanced;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;

/**
 * This class implements {@link ChangeListener} for <code>contourColorAlphaSlider</code>.
 *
 * @author Urban Kravjansky
 */
public class ContourColorAlphaSliderChangeListener extends PropertiesToolBarListener implements
        ChangeListener {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;
    /**
     * Points to <code>contourColorAlphaSlider</code> instance.
     */
    private JSlider       contourColorAlphaSlider = null;

    /**
     * {@link ContourColorAlphaSliderChangeListener} constructor. It initializes
     * <code>contourColorAlphaSlider</code> field.
     *
     * @param contourColorAlphaSlider
     *            contour color alpha {@link JSlider} parameter.
     */
    public ContourColorAlphaSliderChangeListener(JSlider contourColorAlphaSlider) {
        logger = Logger.getLogger(GuiAdvanced.class.getName());
        setContourColorAlphaSlider(contourColorAlphaSlider);
    }

    /**
     * Method is invoked as a result to an action. It sets proper contour color alpha value and
     * updates properties.
     *
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     * @param ce
     *            {@link ChangeEvent} parameter. This parameter is only for implementing purposes,
     *            it is not used nor needed.
     */
    public void stateChanged(ChangeEvent ce) {
        ElementProperties ep = getElementProperties();

        ep.setContourColorAlpha(getContourColorAlphaSlider().getValue());

        // Select manipulation
        try {
            updateProperties(ep);
        } catch (UnknownManipulationException ume) {
            logger.error(ume.getMessage());
        }
    }

    /**
     * Setter for <code>contourColorAlphaSlider</code>.
     *
     * @param contourColorAlphaSlider
     *            the <code>contourColorAlphaSlider</code> to set.
     */
    private void setContourColorAlphaSlider(JSlider contourColorAlphaSlider) {
        this.contourColorAlphaSlider = contourColorAlphaSlider;
    }

    /**
     * Getter for <code>contourColorAlphaSlider</code>.
     *
     * @return The <code>contourColorAlphaSlider</code> instance.
     */
    private JSlider getContourColorAlphaSlider() {
        return this.contourColorAlphaSlider;
    }
}
