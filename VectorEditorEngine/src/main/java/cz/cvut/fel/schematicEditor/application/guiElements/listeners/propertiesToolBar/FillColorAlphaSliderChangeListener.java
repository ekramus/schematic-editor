package cz.cvut.fel.schematicEditor.application.guiElements.listeners.propertiesToolBar;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cz.cvut.fel.schematicEditor.core.coreStructures.ElementProperties;

/**
 * This class implements {@link ChangeListener} for <code>fillColorAlphaSlider</code>.
 *
 * @author Urban Kravjansky
 */
public class FillColorAlphaSliderChangeListener extends PropertiesToolBarListener implements
        ChangeListener {
    /**
     * Points to <code>fillColorAlphaSlider</code> instance.
     */
    private JSlider fillColorAlphaSlider = null;

    /**
     * {@link FillColorAlphaSliderChangeListener} constructor. It initializes
     * <code>fillColorAlphaSlider</code> field.
     *
     * @param fillColorAlphaSlider
     *            fill color alpha {@link JSlider} parameter.
     */
    public FillColorAlphaSliderChangeListener(JSlider fillColorAlphaSlider) {
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
        updateProperties(ep);
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
