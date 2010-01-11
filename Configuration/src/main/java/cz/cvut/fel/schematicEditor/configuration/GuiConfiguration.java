package cz.cvut.fel.schematicEditor.configuration;

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
    private Unit           gridSize              = new Pixel(25);
    /**
     * Delta for snapping on special elements.
     */
    private final Unit     snapDelta             = new Pixel(5);
    /**
     * Size of snap symbol size. Ideally the same as snapDelta value.
     */
    private final Unit     snapSymbolSize        = new Pixel(5);
    /**
     * Grid visibility indicator.
     */
    private boolean        gridVisible           = true;
    /**
     * Size of pointer used in Manipulations.
     */
    private Point2D.Double pointerRectangle      = new Point2D.Double(5, 5);
    /**
     * Background color of ScenePanel.
     */
    private Color          sceneBackgroundColor  = new Color(255, 255, 255);
    /**
     * Scheme ScenePanel X and Y dimension.
     */
    private Dimension      schemeDim             = new Dimension(1280, 1024);
    /**
     * Scheme zoom factor.
     */
    private float          schemeZoomFactor      = 1;
    /**
     * Part zoom factor.
     */
    private float          partZoomFactor        = 10;
    /**
     * Part ScenePanel X and Y dimension.
     */
    private Dimension      partDim               = new Dimension(150, 150);
    /**
     * Scheme antialiasing indicator.
     */
    private boolean        schemeAntialiased     = true;
    /**
     * Indicates, whether is scheme debugged.
     */
    private boolean        schemeDebugged        = false;
    /**
     * Indicate, whether is snap to grid or not.
     */
    private boolean        snapToGrid            = false;
    /**
     * Indicate, whether are connector names visible or not.
     */
    private boolean        connectorNamesVisible = false;

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
     * @return the schemeDim
     */
    public Dimension getSchemeDim() {
        return this.schemeDim;
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
     * @param schemeDim the schemeDim to set
     */
    public void setSchemeDim(Dimension schemeDim) {
        this.schemeDim = schemeDim;
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

    /**
     * @param connectorNamesVisible the connectorNamesVisible to set
     */
    public void setConnectorNamesVisible(boolean connectorNamesVisible) {
        this.connectorNamesVisible = connectorNamesVisible;
    }

    /**
     * @return the snapDelta to get.
     */
    public Unit getSnapDelta() {
        return this.snapDelta;
    }

    /**
     * @return the snapSymbolSize
     */
    public Unit getSnapSymbolSize() {
        return this.snapSymbolSize;
    }

    /**
     * @return the onnectorNamesVisible.
     */
    public boolean isConnectorNamesVisible() {
        return this.connectorNamesVisible;
    }

    /**
     * @param partDim the partDim to set
     */
    public void setPartDim(Dimension partDim) {
        this.partDim = partDim;
    }

    /**
     * @return the partDim
     */
    public Dimension getPartDim() {
        return this.partDim;
    }

    /**
     * @param partZoomFactor the partZoomFactor to set
     */
    public void setPartZoomFactor(float partZoomFactor) {
        this.partZoomFactor = partZoomFactor;
    }

    /**
     * @return the partZoomFactor
     */
    public float getPartZoomFactor() {
        return this.partZoomFactor;
    }

    /**
     * @param schemeZoomFactor the schemeZoomFactor to set
     */
    public void setSchemeZoomFactor(float schemeZoomFactor) {
        this.schemeZoomFactor = schemeZoomFactor;
    }

    /**
     * @return the schemeZoomFactor
     */
    public float getSchemeZoomFactor() {
        return this.schemeZoomFactor;
    }
}
