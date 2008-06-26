package cz.cvut.fel.schematicEditor.application.guiElements.listeners.propertiesToolBar;

import cz.cvut.fel.schematicEditor.application.guiElements.PropertiesToolBar;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.core.coreStructures.ElementProperties;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.Select;

/**
 * This abstract class implements listener model for {@link PropertiesToolBar} other listeners.
 *
 * @author Urban Kravjansky
 */
public abstract class PropertiesToolBarListener {

    /**
     * Selects active element {@link ElementProperties} instance. If there is no active element,
     * this method returns global scene {@link ElementProperties}.
     *
     * @return active element {@link ElementProperties} instance in case there is active element,
     *         otherwise global scene {@link ElementProperties}.
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
     * Update active element {@link ElementProperties} instance, if necessary. Update is executed
     * only in case of {@link Select} manipulation.
     *
     * @param elementProperties
     *            properties to update.
     */
    protected final void updateProperties(final ElementProperties elementProperties) {
        // refresh all elements on properties toolbar
        PropertiesToolBar.refresh();

        if (Structures.getManipulation().getManipulationType() == ManipulationType.SELECT) {
            Select select = (Select) Structures.getManipulation();
            if (select.getManipulatedGroup() != null) {
                select.getManipulatedGroup().getChildrenParameterNode().setProperties(
                                                                                      elementProperties);

                Structures.getScenePanel().schemeInvalidate(
                                                            select.getManipulatedGroup().getBounds());
            }
        }
    }
}
