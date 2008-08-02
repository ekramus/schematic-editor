package cz.cvut.fel.schematicEditor.application.guiElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.application.guiElements.listeners.scenePanel.ScenePanelKeyListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.scenePanel.ScenePanelMouseListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.scenePanel.ScenePanelMouseMotionListener;
import cz.cvut.fel.schematicEditor.core.Constants;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.element.element.shape.Line;
import cz.cvut.fel.schematicEditor.element.element.shape.Shape;
import cz.cvut.fel.schematicEditor.export.DisplayExport;
import cz.cvut.fel.schematicEditor.graphNode.ElementNode;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.ShapeNode;
import cz.cvut.fel.schematicEditor.manipulation.Create;
import cz.cvut.fel.schematicEditor.manipulation.Delete;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationQueue;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.Select;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.support.Snap;
import cz.cvut.fel.schematicEditor.support.Transformation;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

/**
 * This class encapsulates scene JPanel. All main listeners are implemented here. This class is applications main
 * drawing interface.
 * 
 * @author Urban Kravjansky
 */
public class ScenePanel extends JPanel {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger     logger;
    /**
     * Singleton {@link ScenePanel} instance.
     */
    private static ScenePanel sceneJPanel = null;

    /**
     * This method instantiates one instance of <code>SceneJPanel</code>.
     * 
     * @return <code>SceneJPanel</code> instance to use.
     */
    public static ScenePanel getInstance() {
        if (getSceneJPanel() == null) {
            setSceneJPanel(new ScenePanel());
            // enable mouse actions
            getSceneJPanel().addMouseListener(new ScenePanelMouseListener());
            getSceneJPanel().addMouseMotionListener(new ScenePanelMouseMotionListener());
            // enable key actions
            getSceneJPanel().addKeyListener(new ScenePanelKeyListener());
            getSceneJPanel().setFocusable(true);
        }
        return getSceneJPanel();
    }

    /**
     * @return the sceneJPanel
     */
    private static final ScenePanel getSceneJPanel() {
        return sceneJPanel;
    }

    /**
     * @param sceneJPanel the sceneJPanel to set
     */
    private static final void setSceneJPanel(ScenePanel sceneJPanel) {
        ScenePanel.sceneJPanel = sceneJPanel;
    }

    /**
     * This field represents grid.
     */
    private BufferedImage grid;
    /**
     * This field represents grid validity.
     */
    private boolean       gridValid;

    /**
     * This field indicates visibility of grid.
     */
    private boolean       gridVisible;

    /**
     * This field represents scheme.
     */
    private BufferedImage scheme;

    /**
     * This field indicates whether scheme is antialiased or not.
     */
    private boolean       schemeAntialiased;
    /**
     * This field indicates whether scheme is being debugged or not.
     */
    private boolean       schemeDebugged;
    /**
     * Graph of whole scene.
     */
    private SceneGraph    schemeSG;
    /**
     * This field represents validity of scheme.
     */
    private boolean       schemeValid;
    /**
     * This field represents invalid rectangle of scheme.
     */
    @SuppressWarnings("unused")
    private UnitRectangle schemeInvalidRect;

    /**
     * This is the default constructor
     */
    private ScenePanel() {
        super();

        logger = Logger.getLogger(this.getClass().getName());

        init();
    }

    /**
     * Draws edit frame onto {@link BufferedImage}.
     * 
     * @return {@link BufferedImage} with edit frame.
     * @throws UnknownManipulationException In case of unknown manipulation.
     */
    private BufferedImage drawEditFrame() throws UnknownManipulationException {
        // create new edit frame
        BufferedImage editFrame = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        Manipulation m = Structures.getActiveManipulation();
        GroupNode gn = m.getManipulatedGroup();
        Transformation t = m.getManipulatedGroup().getTransformation();

        Graphics2D g2d = (Graphics2D) editFrame.getGraphics();
        Rectangle2D.Double rect = new Rectangle2D.Double(gn.getBounds().getX(), gn.getBounds().getY(), gn.getBounds()
                .getWidth(), gn.getBounds().getHeight());
        rect = t.shift(rect);
        g2d.setColor(Color.ORANGE);
        g2d.draw(rect);

        return editFrame;
    }

    /**
     * This method draws grid onto <code>BufferedImage</code>.
     * 
     * @return <code>BufferedImage</code> with grid.
     */
    private BufferedImage drawGrid() {
        // get Snap instance
        Snap s = Snap.getInstance();

        // create new grid
        BufferedImage grid = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D gridG2D = (Graphics2D) grid.getGraphics();

        // draw first rectangle
        gridG2D.setColor(new Color(210, 220, 255));
        Rectangle2D r = new Rectangle2D.Double(0, 0, s.getGridSize().doubleValue(), s.getGridSize().doubleValue());
        gridG2D.draw(r);

        // copy rectangle in row
        for (double d = s.getGridSize().doubleValue(); d <= grid.getWidth(); d += s.getGridSize().doubleValue()) {
            gridG2D.copyArea(0, 0, s.getGridSize().intValue() + 1, s.getGridSize().intValue() + 1, (int) d, 0);
        }

        // copy row of rectangles
        for (double d = 0; d <= grid.getHeight(); d += s.getGridSize().doubleValue()) {
            gridG2D.copyArea(0, 0, grid.getWidth(), s.getGridSize().intValue() + 1, 0, (int) d);
        }

        // return result
        return grid;
    }

    /**
     * Draws actual manipulated element onto <code>BufferedImage</code>.
     * 
     * @return <code>BufferedImage</code> with actual manipulated element.
     * @throws UnknownManipulationException In case of unknown manipulation.
     */
    private BufferedImage drawManipulatedElement() throws UnknownManipulationException {
        // create new manipulated element
        BufferedImage manipulatedElement = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        // prepare element
        SceneGraph sg = new SceneGraph();
        // this.manipulation.setManipulationCoordinates(getManipXCoord(), getManipYCoord());
        GroupNode gn = Structures.getActiveManipulation().getManipulatedGroup();

        sg.setTopNode(gn);

        // try to draw elements using DisplayExport
        DisplayExport de = DisplayExport.getExport();
        de.export(sg, manipulatedElement);

        return manipulatedElement;
    }

    /**
     * @return
     * @throws UnknownManipulationException In case of unknown manipulation.
     */
    private BufferedImage drawManipulatedGroup() throws UnknownManipulationException {
        // create new manipulated element
        BufferedImage manipulatedGroup = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        // create SceneGraph
        SceneGraph sg = new SceneGraph();

        // get manipulated group
        Manipulation manipulation = Structures.getActiveManipulation();
        GroupNode g = manipulation.getManipulatedGroup();
        sg.setTopNode(g);

        // try to draw elements using DisplayExport
        DisplayExport de = DisplayExport.getExport();
        de.export(sg, manipulatedGroup);

        return manipulatedGroup;
    }

    /**
     * This method draws scene and it's specific features.
     * 
     * @param g <code>Graphics</code> to draw on.
     * @throws UnknownManipulationException In case of unknown manipulation.
     */
    private void drawScene(Graphics g) throws UnknownManipulationException {
        Graphics2D g2d = (Graphics2D) g;

        // if grid is not valid and is visible, recreate it.
        if (this.gridVisible && !isGridValid()) {
            // restore validity
            setGridValid(true);

            // re-draw grid
            this.grid = drawGrid();
        }

        // draw grid if visible
        if (this.gridVisible) {
            g2d.drawImage(this.grid, 0, 0, null);
        }
        // if scheme is not valid, recreate it.
        if (!this.schemeValid) {
            // restore validity
            this.schemeValid = true;

            // TODO add schemeInvalidRect to algorithm
            // re-draw scheme
            this.scheme = drawScheme();
        }
        // draw scheme if visible
        logger.trace("draw scheme");
        g2d.drawImage(this.scheme, 0, 0, null);

        try {

            // TODO correct behavior: in case of manipulation progress, it should use active manipulation,
            // TODO else, it should use last processed manipulation
            Manipulation m = Structures.getActiveManipulation();

            // draw work element or group, if some is being manipulated.
            if (m.isActive() && (m.isManipulatingElements() || m.isManipulatingGroups())) {
                BufferedImage manipulatedElement = null;
                // manipulation manipulates with group
                if (m.isManipulatingGroups()) {
                    logger.trace("calling drawManipulatedGroup");
                    manipulatedElement = drawManipulatedGroup();
                }
                // manipulation manipulates with single element
                else if (m.isManipulatingElements()) {
                    logger.trace("calling drawManipulatedElement");
                    manipulatedElement = drawManipulatedElement();
                }
                // strange behavior, nevertheless, log it
                else {
                    logger.trace("no manipulated group, no manipulated element");
                }
                g2d.drawImage(manipulatedElement, 0, 0, null);
            }

            // highlight manipulated group
            if (m.isActive()) {
                // draw frame around selected group
                if (m.getManipulationType() == ManipulationType.SELECT) {
                    logger.trace("selected element frame drawing");
                    BufferedImage selectionFrame = drawSelectionFrame();
                    g2d.drawImage(selectionFrame, 0, 0, null);
                }
                // draw frame around selected group
                else if (m.getManipulationType() == ManipulationType.EDIT) {
                    logger.trace("create element frame drawing");
                    BufferedImage selectionFrame = drawEditFrame();
                    g2d.drawImage(selectionFrame, 0, 0, null);
                }
            }
        } catch (NullPointerException npe) {
            logger.trace("No manipultion in manipulation queue");
        }
    }

    /**
     * This method draws scheme onto <code>BufferedImage</code>.
     * 
     * @return <code>BufferedImage</code> with scheme.
     */
    private BufferedImage drawScheme() {
        logger.debug("full scheme redraw");

        // create new scheme
        BufferedImage scheme = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        // initialize DisplayExport
        DisplayExport de = DisplayExport.getExport();
        de.setAntialiased(this.schemeAntialiased);
        de.setDebugged(this.schemeDebugged);
        de.export(this.schemeSG, scheme);

        // TODO process sceneGraph elements in foreach loop and transform them using DisplayExport.

        // return result
        return scheme;
    }

    /**
     * This method draws selection frame onto {@link BufferedImage}.
     * 
     * @return {@link BufferedImage} with selection frame.
     * @throws UnknownManipulationException In case of unknown manipulation.
     */
    private BufferedImage drawSelectionFrame() throws UnknownManipulationException {
        // create new selection frame
        BufferedImage selectionFrame = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        Manipulation m = Structures.getActiveManipulation();
        GroupNode gn = m.getManipulatedGroup();
        Transformation t = m.getManipulatedGroup().getTransformation();

        Graphics2D g2d = (Graphics2D) selectionFrame.getGraphics();
        Rectangle2D.Double rect = new Rectangle2D.Double(gn.getBounds().getX(), gn.getBounds().getY(), gn.getBounds()
                .getWidth(), gn.getBounds().getHeight());
        rect = t.shift(rect);
        g2d.setColor(Color.GRAY);
        g2d.draw(rect);

        return selectionFrame;
    }

    /**
     * @return the schemeSG
     */
    public SceneGraph getSchemeSG() {
        return this.schemeSG;
    }

    /**
     * This method initializes this.
     */
    private void init() {
        this.setPreferredSize(new Dimension(Integer.parseInt(Structures.getProperties()
                .getProperty("sceneXDim", Constants.DEFAULT_SCENE_XDIM)), Integer.parseInt(Structures.getProperties()
                .getProperty("sceneYDim", "1024"))));
        this.setBackground(Color.WHITE);

        // initialize grid properties
        setGridVisible(true);
        setGridValid(false);

        // initialize scheme properties
        setSchemeAntialiased(true);
        setSchemeDebugged(false);

        // initialize images
        this.grid = null;
        this.scheme = null;
        this.schemeSG = new SceneGraph();
        this.schemeSG.manualCreateSceneGraph2();
    }

    /**
     * @return the gridValid
     */
    private boolean isGridValid() {
        return this.gridValid;
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
     * This method paints {@link ScenePanel}.
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            drawScene(g);
        } catch (UnknownManipulationException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * This method invalidates <code>scheme</code>.
     * 
     * @param bounds bounds of invalid region.
     */
    public void schemeInvalidate(UnitRectangle bounds) {
        this.schemeValid = false;
        this.schemeInvalidRect = bounds;
        this.repaint();
    }

    /**
     * @param gridValid the gridValid to set
     */
    public void setGridValid(boolean gridValid) {
        this.gridValid = gridValid;
    }

    /**
     * @param gridVisible the gridVisible to set
     */
    public void setGridVisible(boolean gridVisible) {
        this.gridVisible = gridVisible;
    }

    /**
     * @param schemeAntialiased the schemeAntialiased to set
     */
    public void setSchemeAntialiased(boolean schemeAntialiased) {
        this.schemeValid = false;
        this.schemeAntialiased = schemeAntialiased;
    }

    /**
     * @param schemeDebugged the schemeAntialiased to set
     */
    public void setSchemeDebugged(boolean schemeDebugged) {
        this.schemeValid = false;
        this.schemeDebugged = schemeDebugged;
    }
}
