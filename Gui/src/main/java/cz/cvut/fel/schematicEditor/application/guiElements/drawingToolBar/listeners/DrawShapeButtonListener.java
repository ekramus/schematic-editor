package cz.cvut.fel.schematicEditor.application.guiElements.drawingToolBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.application.guiElements.propertiesToolBar.PartPropertiesPanel;
import cz.cvut.fel.schematicEditor.application.guiElements.scenePanel.ScenePanel;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.element.element.shape.Shape;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.ShapeNode;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;

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
    private Shape         shape = null;
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;

    /**
     * {@link DrawShapeButtonListener} constructor. It uses {@link Shape} instance to instantiate new {@link Shape}.
     *
     * @param shape instance used for new {@link Shape} instantiation.
     */
    public DrawShapeButtonListener(final Shape shape) {
        logger = Logger.getLogger(this.getClass().getName());

        setShape(shape);
    }

    /**
     * Method invoked as result to an action. It initializes properties inside {@link Structures} class.
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param ae {@link ActionEvent} parameter.
     */
    public final void actionPerformed(final ActionEvent ae) {
        try {
            // create group node for create manipulation
            GroupNode gn = new GroupNode();
            ParameterNode pn = new ParameterNode();
            ShapeNode sn = new ShapeNode((Shape) getShape().duplicate());
            gn.add(pn);
            gn.add(sn);

            // create active manipulation
            Manipulation m = ManipulationFactory.create(ManipulationType.CREATE, ScenePanel.getInstance().getSchemeSG()
                    .getTopNode(), gn);
            Structures.setActiveManipulation(m);
            logger.trace(Structures.getActiveManipulation());

            // set selected element properties to null
            Structures.getSceneProperties().setSelectedElementProperties(null);
            // refresh status on properties toolbar
            PartPropertiesPanel.refresh();
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
     * @param shape the shape to set
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
