package cz.cvut.fel.schematicEditor.application.guiElements.listeners.drawingToolBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cz.cvut.fel.schematicEditor.application.guiElements.PropertiesToolBar;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.element.element.shape.Shape;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.manipulation.manipulation.ManipulationFactory;

/**
 * This class implements listener for every shape drawing button. It is implemented by instantiating a new class of
 * {@link Shape}.
 * 
 * @author Urban Kravjansky
 */
public class DrawShapeButtonListener implements ActionListener {

    /**
     * Shape instance used by this listener.
     */
    private Shape shape = null;

    /**
     * {@link DrawShapeButtonListener} constructor. It uses {@link Shape} instance to instantiate new {@link Shape}.
     * 
     * @param shape
     *            instance used for new {@link Shape} instantiation.
     */
    public DrawShapeButtonListener(final Shape shape) {
        setShape(shape);
    }

    /**
     * Method invoked as result to an action. It initializes properties inside {@link Structures} class.
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * 
     * @param ae
     *            {@link ActionEvent} parameter.
     */
    public final void actionPerformed(final ActionEvent ae) {
        try {
            Structures.getManipulationQueue().offer(ManipulationFactory.create(ManipulationType.CREATE, getShape().newInstance()));
            Structures.getSceneProperties().setSelectedElementProperties(null);
            // refresh status on properties toolbar
            PropertiesToolBar.refresh();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (UnknownManipulationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setter for shape field.
     * 
     * @param shape
     *            the shape to set
     */
    private void setShape(final Shape shape) {
        this.shape = shape;
    }

    /**
     * Getter for shape field.
     * 
     * @return the shape of this instance.
     */
    private Shape getShape() {
        return this.shape;
    }
}
