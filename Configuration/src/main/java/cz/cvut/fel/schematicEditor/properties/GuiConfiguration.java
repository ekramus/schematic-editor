package cz.cvut.fel.schematicEditor.properties;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Point2D;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;

/**
 * This class encapsulates GUI properties.
 * 
 * @author Urban Kravjansky
 */
@XStreamAlias("guiConfiguration")
public class GuiConfiguration extends Configuration {
    /**
     * File name, where should be this class serialized.
     */
    private static final String     FILE     = "config/gui.xml";
    /**
     * {@link GuiConfiguration} singleton instance.
     */
    private static GuiConfiguration instance = null;

    /**
     * @return the FILE
     */
    public static String getFile() {
        return GuiConfiguration.FILE;
    }

    /**
     * @return the instance
     */
    public static GuiConfiguration getInstance() {
        if (instance == null) {
            instance = (GuiConfiguration) Configuration.deserialize(GuiConfiguration.class, FILE);
            if (instance == null) {
                instance = new GuiConfiguration();
            }
        }
        return instance;
    }

    /**
     * ScenePanel grid size.
     */
    private Unit           gridSize             = new Pixel(25);
    /**
     * Grid visibility indicator.
     */
    private boolean        gridVisible          = true;
    /**
     * Size of pointer used in Manipulations.
     */
    private Point2D.Double pointerRectangle     = new Point2D.Double(5, 5);
    /**
     * Background color of ScenePanel.
     */
    private Color          sceneBackgroundColor = new Color(255, 255, 255);
    /**
     * ScenePanel X and Y dimension.
     */
    private Dimension      sceneDim             = new Dimension(1280, 1024);
    /**
     * Scheme antialiasing indicator.
     */
    private boolean        schemeAntialiased    = true;
    /**
     * Indicates, whether is scheme debugged.
     */
    private boolean        schemeDebugged       = false;
    /**
     * Indicate, whether is snap to grid or not.
     */
    private boolean        snapToGrid           = false;

    /**
     * 
     */
    public GuiConfiguration() {
        // nothing to do
    }

    /**
     * @return the gridSize
     */
    public Unit getGridSize() {
        return this.gridSize;
    }

    /**
     * @return the pointerRectangle
     */
    public Point2D.Double getPointerRectangle() {
        return this.pointerRectangle;
    }

    /**
     * @return the sceneBackgroundColor
     */
    public Color getSceneBackgroundColor() {
        return this.sceneBackgroundColor;
    }

    /**
     * @return the sceneDim
     */
    public Dimension getSceneDim() {
        return this.sceneDim;
    }

    /**
     * @return the gridVisible
     */
    public boolean isGridVisible() {
        return this.gridVisible;
    }

    /**
     * @return the schemeAntialiased
     */
    public boolean isSchemeAntialiased() {
        return this.schemeAntialiased;
    }

    /**
     * @return the schemeDebugged
     */
    public boolean isSchemeDebugged() {
        return this.schemeDebugged;
    }

    /**
     * @return the snapToGrid
     */
    public boolean isSnapToGrid() {
        return this.snapToGrid;
    }

    /**
     * @param gridSize the gridSize to set
     */
    public void setGridSize(Unit gridSize) {
        this.gridSize = gridSize;
    }

    /**
     * @param gridVisible the gridVisible to set
     */
    public void setGridVisible(boolean gridVisible) {
        this.gridVisible = gridVisible;
    }

    /**
     * @param pointerRectangle the pointerRectangle to set
     */
    public void setPointerRectangle(Point2D.Double pointerRectangle) {
        this.pointerRectangle = pointerRectangle;
    }

    /**
     * @param sceneBackgroundColor the sceneBackgroundColor to set
     */
    public void setSceneColor(Color sceneBackgroundColor) {
        this.sceneBackgroundColor = sceneBackgroundColor;
    }

    /**
     * @param sceneDim the sceneDim to set
     */
    public void setSceneDim(Dimension sceneDim) {
        this.sceneDim = sceneDim;
    }

    /**
     * @param schemeAntialiased the schemeAntialiased to set
     */
    public void setSchemeAntialiased(boolean schemeAntialiased) {
        this.schemeAntialiased = schemeAntialiased;
    }

    /**
     * @param schemeDebugged the schemeDebugged to set
     */
    public void setSchemeDebugged(boolean schemeDebugged) {
        this.schemeDebugged = schemeDebugged;
    }

    /**
     * @param snapToGrid the snapToGrid to set
     */
    public void setSnapToGrid(boolean snapToGrid) {
        this.snapToGrid = snapToGrid;
    }
}
