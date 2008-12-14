package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.listeners;

import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.element.properties.ElementProperties;
import cz.cvut.fel.schematicEditor.guiAdvanced.GuiAdvanced;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.PartPropertiesPanel;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.PropertiesPanel;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.Select;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;

/**
 * This abstract class implements listener model for {@link PropertiesPanel} other listeners.
 *
 * @author Urban Kravjansky
 */
public abstract class PropertiesToolBarListener {

    /**
     * Selects active element {@link ElementProperties} instance. If there is no active element, this method returns
     * global scene {@link ElementProperties}.
     *
     * @return active element {@link ElementProperties} instance in case there is active element, otherwise global scene
     *         {@link ElementProperties}.
     */
    protected final ElementProperties getElementProperties() {
        ElementProperties ep;

        if (Structures.getSceneProperties().getSelectedElementProperties() == null) {
            ep = Structures.getSceneProperties().getSceneElementProperties();
        } else {
            ep = Structures.getSceneProperties().getSelectedElementProperties();
        }
        return ep;
    }

    /**
     * Update active element {@link ElementProperties} instance, if necessary. Update is executed only in case of
     * {@link Select} manipulation.
     *
     * @param elementProperties properties to update.
     */
    protected final void updateProperties(final ElementProperties elementProperties)
            throws UnknownManipulationException {
        // refresh all elements on properties toolbar
        PartPropertiesPanel.getInstance().update();

        Manipulation m = Structures.getActiveManipulation();

        if (m.getManipulationType() == ManipulationType.SELECT) {
            Select select = (Select) m;
            if (select.getManipulatedGroup() != null) {
                select.getManipulatedGroup().getChildrenParameterNode().setProperties(elementProperties);

                GuiAdvanced.getActiveScenePanel().schemeInvalidate(select.getManipulatedGroup().getBounds());
            }
        }
    }
}
