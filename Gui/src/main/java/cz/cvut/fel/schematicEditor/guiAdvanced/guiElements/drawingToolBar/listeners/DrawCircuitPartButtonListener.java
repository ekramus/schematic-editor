package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.drawingToolBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.element.element.part.Pin;
import cz.cvut.fel.schematicEditor.element.element.part.Wire;
import cz.cvut.fel.schematicEditor.graphNode.ConnectorNode;
import cz.cvut.fel.schematicEditor.graphNode.ElementNode;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.WireNode;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.guiAdvanced.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.PartPropertiesPanel;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

/**
 * This class implements listener for every circuit part drawing button. It is implemented by instantiating a new class.
 *
 * @author Urban Kravjansky
 */
public class DrawCircuitPartButtonListener implements ActionListener {
    /**
     * Element instance used by this listener.
     */
    private Element       element = null;
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;

    /**
     * {@link DrawCircuitPartButtonListener} constructor. It uses {@link Element} instance to instantiate new circuit
     * part.
     *
     * @param element instance used for new circuit part instantiation.
     */
    public DrawCircuitPartButtonListener(final Element element) {
        logger = Logger.getLogger(this.getClass().getName());

        setElement(element);
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
            ElementNode en = null;

            switch (getElement().getElementType()) {
                case T_CONNECTOR:
                    en = new ConnectorNode((Pin) getElement());
                    break;
                case T_WIRE:
                    en = new WireNode((Wire) getElement());
                    break;
                case T_PART:
                    // en = new PartNode((Part) getElement());
                    break;
                default:
                    break;
            }

            gn.add(pn);
            gn.add(en);

            // get all coordinates for snapping
            Vector<UnitPoint> snapCoordinates = new Vector<UnitPoint>();
            snapCoordinates = Gui.getActiveScenePanel().getSceneGraph().getTopNode().getPartsCoordinates();

            // create active manipulation
            Manipulation m = ManipulationFactory.create(ManipulationType.CREATE, Gui.getActiveScenePanel()
                    .getSceneGraph().getTopNode(), ae.getSource(), gn);
            m.setSnapCoordinates(snapCoordinates);
            Gui.getActiveScenePanel().setActiveManipulation(m);
            logger.trace(Gui.getActiveScenePanel().getActiveManipulation());

            // set selected element properties to null
            Gui.getActiveScenePanel().getSceneProperties().setSelectedElementProperties(null);
            // refresh status on properties toolbar
            PartPropertiesPanel.getInstance().refresh();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (UnknownManipulationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setter for element field.
     *
     * @param element the {@link Element} to set
     */
    private void setElement(final Element element) {
        this.element = element;
    }

    /**
     * Getter for element field.
     *
     * @return the element of this instance.
     */
    private Element getElement() {
        return this.element;
    }
}
