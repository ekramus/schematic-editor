package cz.cvut.fel.schematicEditor.properties;

import java.awt.Color;

/**
 * This class encapsulates GUI properties.
 * 
 * @author Urban Kravjansky
 */
public class GuiConfiguration extends Configuration {
    /**
     * File name, where should be this class serialized.
     */
    private static final String     FILE                 = "config/gui.xml";
    /**
     * {@link GuiConfiguration} singleton instance.
     */
    private static GuiConfiguration instance             = null;
    /**
     * ScenePanel X dimension.
     */
    private int                     sceneXDim            = 1280;
    /**
     * ScenePanel Y dimension.
     */
    private int                     sceneYDim            = 1024;
    /**
     * ScenePanel grid size.
     */
    private double                  gridSize             = 25;
    /**
     * Scheme antialiasing indicator.
     */
    private boolean                 schemeAntialiased    = true;
    /**
     * Background color of ScenePanel.
     */
    private Color                   sceneBackgroundColor = new Color(255, 255, 255);
    /**
     * Grid visibility indicator.
     */
    private boolean                 gridVisible          = true;
    /**
     * Indicates, whether is scheme debugged.
     */
    private boolean                 schemeDebugged       = false;

    /**
     * 
     */
    public GuiConfiguration() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param sceneXDim the sceneXDim to set
     */
    public void setSceneXDim(int sceneXDim) {
        this.sceneXDim = sceneXDim;
    }

    /**
     * @return the sceneXDim
     */
    public int getSceneXDim() {
        return this.sceneXDim;
    }

    /**
     * @param sceneYDim the sceneYDim to set
     */
    public void setSceneYDim(int sceneYDim) {
        this.sceneYDim = sceneYDim;
    }

    /**
     * @return the sceneYDim
     */
    public int getSceneYDim() {
        return this.sceneYDim;
    }

    /**
     * @param gridSize the gridSize to set
     */
    public void setGridSize(double gridSize) {
        this.gridSize = gridSize;
    }

    /**
     * @return the gridSize
     */
    public double getGridSize() {
        return this.gridSize;
    }

    /**
     * @param schemeAntialiased the schemeAntialiased to set
     */
    public void setSchemeAntialiased(boolean schemeAntialiased) {
        this.schemeAntialiased = schemeAntialiased;
    }

    /**
     * @return the schemeAntialiased
     */
    public boolean isSchemeAntialiased() {
        return this.schemeAntialiased;
    }

    /**
     * @param sceneBackgroundColor the sceneBackgroundColor to set
     */
    public void setSceneColor(Color sceneBackgroundColor) {
        this.sceneBackgroundColor = sceneBackgroundColor;
    }

    /**
     * @return the sceneBackgroundColor
     */
    public Color getSceneBackgroundColor() {
        return this.sceneBackgroundColor;
    }

    /**
     * @param gridVisible the gridVisible to set
     */
    public void setGridVisible(boolean gridVisible) {
        this.gridVisible = gridVisible;
    }

    /**
     * @return the gridVisible
     */
    public boolean isGridVisible() {
        return this.gridVisible;
    }

    /**
     * @param schemeDebugged the schemeDebugged to set
     */
    public void setSchemeDebugged(boolean schemeDebugged) {
        this.schemeDebugged = schemeDebugged;
    }

    /**
     * @return the schemeDebugged
     */
    public boolean isSchemeDebugged() {
        return this.schemeDebugged;
    }

    /**
     * @return the instance
     */
    public static GuiConfiguration getInstance() {
        if (instance == null) {
            instance = (GuiConfiguration) Configuration.deserialize(FILE);
            if (instance == null) {
                instance = new GuiConfiguration();
            }
        }
        return instance;
    }

    /**
     * @return the FILE
     */
    public static String getFile() {
        return GuiConfiguration.FILE;
    }
}
