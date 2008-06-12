/**
 * 
 */
package cz.cvut.fel.schematicEditor.core.coreStructures;

/**
 * @author uk
 */
public class SceneProperties {
    /**
     * Element properties of current selected element.
     */
    private ElementProperties selectedElementProperties;
    /**
     * Element properties of scene.
     */
    private ElementProperties sceneElementProperties;

    /**
     * 
     */
    public SceneProperties() {
        setSelectedElementProperties(null);
        setSceneElementProperties(new ElementProperties());
    }

    /**
     * @return the selectedElementProperties
     */
    public final ElementProperties getSelectedElementProperties() {
        return this.selectedElementProperties;
    }

    /**
     * @param selectedElementProperties
     *            the selectedElementProperties to set
     */
    public final void setSelectedElementProperties(ElementProperties selectedElementProperties) {
        this.selectedElementProperties = selectedElementProperties;
    }

    /**
     * @return the sceneElementProperties
     */
    public final ElementProperties getSceneElementProperties() {
        return this.sceneElementProperties;
    }

    /**
     * @param sceneElementProperties
     *            the sceneElementProperties to set
     */
    public final void setSceneElementProperties(ElementProperties sceneElementProperties) {
        this.sceneElementProperties = sceneElementProperties;
    }
}
