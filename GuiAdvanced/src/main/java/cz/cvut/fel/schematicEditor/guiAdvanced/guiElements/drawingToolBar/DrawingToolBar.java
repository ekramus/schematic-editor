package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.drawingToolBar;

import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import cz.cvut.fel.schematicEditor.element.element.part.Pin;
import cz.cvut.fel.schematicEditor.element.element.part.Wire;
import cz.cvut.fel.schematicEditor.element.element.shape.Arc;
import cz.cvut.fel.schematicEditor.element.element.shape.ArcSegment;
import cz.cvut.fel.schematicEditor.element.element.shape.BezierCurve;
import cz.cvut.fel.schematicEditor.element.element.shape.Ellipse;
import cz.cvut.fel.schematicEditor.element.element.shape.Line;
import cz.cvut.fel.schematicEditor.element.element.shape.Polygon;
import cz.cvut.fel.schematicEditor.element.element.shape.Polyline;
import cz.cvut.fel.schematicEditor.element.element.shape.Rectangle;
import cz.cvut.fel.schematicEditor.element.element.shape.Text;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.drawingToolBar.listeners.DeleteButtonListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.drawingToolBar.listeners.DrawCircuitPartButtonListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.drawingToolBar.listeners.DrawShapeButtonListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.drawingToolBar.listeners.SelectButtonListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.drawingToolBar.resources.DrawingToolBarResources;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.guiAdvanced.GuiAdvanced;

/**
 * This class implements drawing tool bar. It is used for drawing tool selection.
 *
 * @author Urban Kravjansky
 */
public final class DrawingToolBar extends JToolBar {
    /**
     * {@link DrawingToolBar} instance.
     */
    private static DrawingToolBar drawingToolBar = null;

    /**
     * {@link DrawingToolBar} is singleton. This method returns the only one instance, that exists. If none exist, one
     * is created.
     *
     * @return instance of {@link DrawingToolBar}.
     */
    public static DrawingToolBar getInstance() {
        if (drawingToolBar == null) {
            // create drawing tool bar
            drawingToolBar = new DrawingToolBar();

            // create and set layout manager
            BoxLayout bl = new BoxLayout(drawingToolBar, BoxLayout.PAGE_AXIS);
            drawingToolBar.setLayout(bl);

            // add buttons to the tool bar
            drawingToolBar.add(drawingToolBar.getButton(DrawingToolBarResources.SELECT_BUTTON));
            drawingToolBar.add(drawingToolBar.getButton(DrawingToolBarResources.DELETE_BUTTON));
            drawingToolBar.add(Box.createVerticalStrut(20));
            drawingToolBar.add(drawingToolBar.getButton(DrawingToolBarResources.LINE_BUTTON));
            drawingToolBar.add(drawingToolBar.getButton(DrawingToolBarResources.BEZIER_CURVE_BUTTON));
            drawingToolBar.add(drawingToolBar.getButton(DrawingToolBarResources.POLYLINE_BUTTON));
            drawingToolBar.add(drawingToolBar.getButton(DrawingToolBarResources.POLYGON_BUTTON));
            drawingToolBar.add(drawingToolBar.getButton(DrawingToolBarResources.ELLIPSE_BUTTON));
            drawingToolBar.add(drawingToolBar.getButton(DrawingToolBarResources.ARC_BUTTON));
            drawingToolBar.add(drawingToolBar.getButton(DrawingToolBarResources.ARC_SEGMENT_BUTTON));
            drawingToolBar.add(drawingToolBar.getButton(DrawingToolBarResources.RECTANGLE_BUTTON));
            drawingToolBar.add(drawingToolBar.getButton(DrawingToolBarResources.TEXT_BUTTON));
            drawingToolBar.add(Box.createVerticalStrut(20));
            drawingToolBar.add(drawingToolBar.getButton(DrawingToolBarResources.PIN_BUTTON));
            drawingToolBar.add(drawingToolBar.getButton(DrawingToolBarResources.WIRE_BUTTON));
        }
        return drawingToolBar;
    }

    /**
     * Refreshes status of menu items.
     */
    public void refresh() {
        switch (GuiAdvanced.getActiveScenePanelTab()) {
            case TAB_SCHEME:
                // wire button
                getComponent(14).setEnabled(true);
                break;
            case TAB_PART:
                // wire button
                getComponent(14).setEnabled(false);
                break;
            default:
                break;
        }
    }

    /**
     * Default constructor. It calls default constructor of super class. It is private, because {@link DrawingToolBar}
     * is singleton.
     */
    private DrawingToolBar() {
        super();
    }

    /**
     * Getter for buttons.
     *
     * @param buttonType Type of button to generate.
     * @return Generated button.
     */
    private JButton getButton(DrawingToolBarResources buttonType) {
        JButton result = new JButton();
        result.setAlignmentX(CENTER_ALIGNMENT);

        // set resources and tooltip
        URL url = this.getClass().getResource(buttonType.getResource());
        result.setIcon(new ImageIcon(url));
        result.setToolTipText(buttonType.getText());

        // setListener
        ActionListener l = null;
        switch (buttonType) {
            case ARC_BUTTON:
                l = new DrawShapeButtonListener(new Arc());
                break;
            case ARC_SEGMENT_BUTTON:
                l = new DrawShapeButtonListener(new ArcSegment());
                break;
            case TEXT_BUTTON:
                l = new DrawShapeButtonListener(new Text());
                break;
            case PIN_BUTTON:
                l = new DrawCircuitPartButtonListener(new Pin());
                break;
            case WIRE_BUTTON:
                l = new DrawCircuitPartButtonListener(new Wire());
                break;
            case BEZIER_CURVE_BUTTON:
                l = new DrawShapeButtonListener(new BezierCurve());
                break;
            case ELLIPSE_BUTTON:
                l = new DrawShapeButtonListener(new Ellipse());
                break;
            case LINE_BUTTON:
                l = new DrawShapeButtonListener(new Line());
                break;
            case POLYGON_BUTTON:
                l = new DrawShapeButtonListener(new Polygon());
                break;
            case POLYLINE_BUTTON:
                l = new DrawShapeButtonListener(new Polyline());
                break;
            case RECTANGLE_BUTTON:
                l = new DrawShapeButtonListener(new Rectangle());
                break;
            case SELECT_BUTTON:
                l = new SelectButtonListener();
                break;
            case DELETE_BUTTON:
                l = new DeleteButtonListener();
                break;
            case JUNCTION_BUTTON:
                // l = new DrawShapeButtonListener(new Junction());
                break;
            default:
                break;
        }
        result.addActionListener(l);

        return result;
    }
}
