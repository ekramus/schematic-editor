package cz.cvut.fel.schematicEditor.application.guiElements;

import java.net.URL;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import cz.cvut.fel.schematicEditor.application.guiElements.listeners.drawingToolBar.DeleteButtonListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.drawingToolBar.DrawCircuitPartButtonListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.drawingToolBar.DrawShapeButtonListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.drawingToolBar.SelectButtonListener;
import cz.cvut.fel.schematicEditor.element.element.part.Connector;
import cz.cvut.fel.schematicEditor.element.element.shape.Arc;
import cz.cvut.fel.schematicEditor.element.element.shape.ArcSegment;
import cz.cvut.fel.schematicEditor.element.element.shape.BezierCurve;
import cz.cvut.fel.schematicEditor.element.element.shape.Ellipse;
import cz.cvut.fel.schematicEditor.element.element.shape.Line;
import cz.cvut.fel.schematicEditor.element.element.shape.Polygon;
import cz.cvut.fel.schematicEditor.element.element.shape.Polyline;
import cz.cvut.fel.schematicEditor.element.element.shape.Rectangle;

/**
 * This class implements drawing tool bar. It is used for drawing tool
 * selection.
 *
 * @author Urban Kravjansky
 */
public final class DrawingToolBar extends JToolBar {
    // TODO move resources into enum
    /**
     * Arc button caption.
     */
    private static final String ARC_BTN = "arc";
    /**
     * Arc segment button caption.
     */
    private static final String ARC_SEGMENT_BTN = "arc segment";
    /**
     * Bezier curve button caption.
     */
    private static final String BEZIER_BTN = "beziere";
    /**
     * Line tool tip button text.
     */
    private static final String LINE_TOOLTIP_BTN = "line";
    /**
     * Polygon button caption.
     */
    private static final String POLYGON_BTN = "polygone";
    /**
     * Polyline button caption.
     */
    private static final String POLYLINE_BTN = "polyline";
    /**
     * Rectangle button caption.
     */
    private static final String RECT_BTN = "rectangle";
    /**
     * Select button caption.
     */
    private static final String SELECT_BTN = "select";
    /**
     * Delete button caption.
     */
    private static final String DELETE_BTN = "delete";
    /**
     * Connector button caption.
     */
    private static final String CONNECTOR_BTN = "connector";

    /**
     * {@link DrawingToolBar} instance.
     */
    private static DrawingToolBar drawingToolBar = null;

    /**
     * {@link DrawingToolBar} is singleton. This method returns the only one
     * instance, that exists. If none exist, one is created.
     *
     * @return instance of {@link DrawingToolBar}.
     */
    public static JToolBar getInstance() {
        if (drawingToolBar == null) {
            // create drawing tool bar
            drawingToolBar = new DrawingToolBar();
            drawingToolBar.setLayout(new BoxLayout(drawingToolBar,
                    BoxLayout.Y_AXIS));
            // add buttons to the tool bar
            drawingToolBar.add(drawingToolBar.getSelectButton());
            drawingToolBar.add(drawingToolBar.getDeleteButton());
            drawingToolBar.add(drawingToolBar.getLineButton());
            drawingToolBar.add(drawingToolBar.getBeziereButton());
            drawingToolBar.add(drawingToolBar.getPolylineButton());
            drawingToolBar.add(drawingToolBar.getPolygonButton());
            drawingToolBar.add(drawingToolBar.getEllipseButton());
            drawingToolBar.add(drawingToolBar.getArcButton());
            drawingToolBar.add(drawingToolBar.getArcSegmentButton());
            drawingToolBar.add(drawingToolBar.getRectButton());
            drawingToolBar.add(drawingToolBar.getConnectorButton());
        }
        return drawingToolBar;
    }

    /**
     * Arc {@link JButton} instance.
     */
    private JButton arcButton = null;
    /**
     * Arc {@link JButton} instance.
     */
    private JButton arcSegmentButton = null;
    /**
     * Beziere curve {@link JButton} instance.
     */
    private JButton beziereButton = null;
    /**
     * Edit {@link JButton} instance.
     */
    private JButton editButton = null;
    /**
     * Ellipse {@link JButton} instance.
     */
    private JButton ellipseButton = null;
    /**
     * Line {@link JButton} instance.
     */
    private JButton lineButton = null;
    /**
     * Polygon {@link JButton} instance.
     */
    private JButton polygonButton = null;
    /**
     * Polyline {@link JButton} instance.
     */
    private JButton polylineButton = null;
    /**
     * Rectangle {@link JButton} instance.
     */
    private JButton rectButton = null;
    /**
     * Select {@link JButton} instance.
     */
    private JButton selectButton = null;
    /**
     * Delete {@link JButton} instance.
     */
    private JButton deleteButton = null;
    /**
     * Connector {@link JButton} instance.
     */
    private JButton connectorButton = null;

    /**
     * Default constructor. It calls default constructor of super class. It is
     * private, because {@link DrawingToolBar} is singleton.
     */
    private DrawingToolBar() {
        super();
    }

    /**
     * <code>arcButton</code> getter.
     *
     * @return <code>arcButton</code> instance.
     */
    private JButton getArcButton() {
        if (this.arcButton == null) {
            this.arcButton = new JButton();
            this.arcButton.setText(ARC_BTN);
            this.arcButton.addActionListener(new DrawShapeButtonListener(
                    new Arc()));
        }
        return this.arcButton;
    }

    /**
     * <code>arcSegmentButton</code> getter.
     *
     * @return <code>arcSegmentButton</code> instance.
     */
    private JButton getArcSegmentButton() {
        if (this.arcSegmentButton == null) {
            this.arcSegmentButton = new JButton();
            this.arcSegmentButton.setText(ARC_SEGMENT_BTN);
            this.arcSegmentButton
                    .addActionListener(new DrawShapeButtonListener(
                            new ArcSegment()));
        }
        return this.arcSegmentButton;
    }

    /**
     * <code>connectorButton</code> getter.
     *
     * @return <code>connectorButton</code> instance.
     */
    private JButton getConnectorButton() {
        if (this.connectorButton == null) {
            this.connectorButton = new JButton();
            this.connectorButton.setText(CONNECTOR_BTN);
            this.connectorButton
                    .addActionListener(new DrawCircuitPartButtonListener(
                            new Connector()));
        }
        return this.connectorButton;
    }

    /**
     * <code>beziereButton</code> getter.
     *
     * @return <code>beziereButton</code> instance.
     */
    private JButton getBeziereButton() {
        if (this.beziereButton == null) {
            this.beziereButton = new JButton();
            this.beziereButton.setText(BEZIER_BTN);
            this.beziereButton.addActionListener(new DrawShapeButtonListener(
                    new BezierCurve()));
        }
        return this.beziereButton;
    }

    /**
     * <code>ellipseButton</code> getter.
     *
     * @return <code>ellipseButton</code> instance.
     */
    private JButton getEllipseButton() {
        if (this.ellipseButton == null) {
            this.ellipseButton = new JButton();
            // read resource from jar file
            URL url = this.getClass().getResource("resources/ellipse_32.png");
            this.ellipseButton.setIcon(new ImageIcon(url));
            this.ellipseButton.addActionListener(new DrawShapeButtonListener(
                    new Ellipse()));
        }
        return this.ellipseButton;
    }

    /**
     * <code>lineButton</code> getter.
     *
     * @return <code>lineButton</code> instance.
     */
    private JButton getLineButton() {
        if (this.lineButton == null) {
            this.lineButton = new JButton();
            // read resource from jar file
            URL url = this.getClass().getResource("resources/line_32.png");
            this.lineButton.setIcon(new ImageIcon(url));
            this.lineButton.setToolTipText(LINE_TOOLTIP_BTN);
            this.lineButton.addActionListener(new DrawShapeButtonListener(
                    new Line()));
        }
        return this.lineButton;
    }

    /**
     * <code>polygonButton</code> getter.
     *
     * @return <code>polygonButton</code> instance.
     */
    private JButton getPolygonButton() {
        if (this.polygonButton == null) {
            this.polygonButton = new JButton();
            this.polygonButton.setText(POLYGON_BTN);
            this.polygonButton.addActionListener(new DrawShapeButtonListener(
                    new Polygon()));
        }
        return this.polygonButton;
    }

    /**
     * <code>polylineButton</code> getter.
     *
     * @return <code>polylineButton</code> instance.
     */
    private JButton getPolylineButton() {
        if (this.polylineButton == null) {
            this.polylineButton = new JButton();
            this.polylineButton.setText(POLYLINE_BTN);
            this.polylineButton.addActionListener(new DrawShapeButtonListener(
                    new Polyline()));
        }
        return this.polylineButton;
    }

    /**
     * <code>rectButton</code> getter.
     *
     * @return <code>rectButton</code> instance.
     */
    private JButton getRectButton() {
        if (this.rectButton == null) {
            this.rectButton = new JButton();
            this.rectButton.setText(RECT_BTN);
            this.rectButton.addActionListener(new DrawShapeButtonListener(
                    new Rectangle()));
        }
        return this.rectButton;
    }

    /**
     * <code>selectButton</code> getter.
     *
     * @return <code>selectButton</code> instance.
     */
    private JButton getSelectButton() {
        if (this.selectButton == null) {
            this.selectButton = new JButton();
            this.selectButton.setText(SELECT_BTN);
            this.selectButton.addActionListener(new SelectButtonListener());
        }
        return this.selectButton;
    }

    /**
     * <code>selectButton</code> getter.
     *
     * @return <code>selectButton</code> instance.
     */
    private JButton getDeleteButton() {
        if (this.deleteButton == null) {
            this.deleteButton = new JButton();
            this.deleteButton.setText(DELETE_BTN);
            this.deleteButton.addActionListener(new DeleteButtonListener());
        }
        return this.deleteButton;
    }
}
