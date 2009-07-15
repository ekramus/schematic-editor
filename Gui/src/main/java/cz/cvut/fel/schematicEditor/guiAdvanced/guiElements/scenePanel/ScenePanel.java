package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.configuration.GuiConfiguration;
import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.core.coreStructures.SceneProperties;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.export.DisplayExport;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.propertiesPanel.GeneralPropertiesPanel;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanel.listeners.ScenePanelKeyListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanel.listeners.ScenePanelMouseListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanel.listeners.ScenePanelMouseMotionListener;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationQueue;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.Select;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.support.Transformation;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;
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
     * This field represents grid.
     */
    private BufferedImage     grid;

    /**
     * This field represents grid validity.
     */
    private boolean           gridValid;
    /**
     * This field represents scheme.
     */
    private BufferedImage     scheme;
    /**
     * This field represents validity of scheme.
     */
    private boolean           sceneValid;
    /**
     * {@link SceneGraph} instance used for this instance of {@link ScenePanel}.
     */
    private SceneGraph        sceneGraph               = null;
    /**
     * Reference to <code>SceneProperties</code> instance.
     */
    private SceneProperties   sceneProperties          = null;
    /**
     * Reference to <code>ManipulationQueue</code> instance.
     */
    private ManipulationQueue manipulationQueue        = null;
    /**
     * Reference to active {@link Manipulation} instance.
     */
    private Manipulation      activeManipulation       = null;
    /**
     * PartNode, which is being edited.
     */
    private PartNode          editedPartNode           = null;
    /**
     * Zoom factor of this scene panel.
     */
    private double            zoomFactor               = 1.0;
    /**
     * Actual pointer coordinates.
     */
    private UnitPoint         actualPointerCoordinates = null;
    /**
     * Relative coordinate system start to aid users.
     */
    private UnitPoint         relativeStart            = new UnitPoint(0, 0);

    /**
     * @return the relativeStart
     */
    public UnitPoint getRelativeStart() {
        return this.relativeStart;
    }

    /**
     * Set the relativeStart based on current <code>actualPointerCoordinates</code> field.
     */
    public void setRelativeStart() {
        this.relativeStart = new UnitPoint(this.actualPointerCoordinates);
    }

    /**
     * @return the actualPointerCoordinates
     */
    private UnitPoint getActualPointerCoordinates() {
        return this.actualPointerCoordinates;
    }

    /**
     * @param actualPointerCoordinates the actualPointerCoordinates to set
     */
    public void setActualPointerCoordinates(UnitPoint actualPointerCoordinates) {
        this.actualPointerCoordinates = new UnitPoint(actualPointerCoordinates);
    }

    /**
     * @return the zoomFactor
     */
    public double getZoomFactor() {
        return this.zoomFactor;
    }

    /**
     * @param zoomFactor the zoomFactor to set
     */
    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    /**
     * Getter for <code>activeManipulation</code> instance.
     *
     * @return Instance of <code>activeManipulation</code>.
     */
    public Manipulation getActiveManipulation() {
        return this.activeManipulation;
    }

    /**
     * @return the editedPartNode
     */
    public PartNode getEditedPartNode() {
        return this.editedPartNode;
    }

    /**
     * @param editedPartNode the editedPartNode to set
     */
    public void setEditedPartNode(PartNode editedPartNode) {
        this.editedPartNode = editedPartNode;
    }

    /**
     * Setter for <code>activeManipulation</code> instance.
     *
     * @param activeManipulation instance of <code>activeManipulation</code>.
     */
    public void setActiveManipulation(Manipulation activeManipulation) {
        this.activeManipulation = activeManipulation;
    }

    /**
     * Getter for {@link ManipulationQueue}.
     *
     * @return the manipulationQueue
     */
    public ManipulationQueue getManipulationQueue() {
        if (this.manipulationQueue == null) {
            this.manipulationQueue = new ManipulationQueue();
        }
        return this.manipulationQueue;
    }

    /**
     * Getter for {@link SceneProperties}.
     *
     * @return the sceneProperties
     */
    public SceneProperties getSceneProperties() {
        if (this.sceneProperties == null) {
            this.sceneProperties = new SceneProperties();
        }
        return this.sceneProperties;
    }

    /**
     * Getter for <code>sceneGraph</code>.
     *
     * @return the sceneGraph
     */
    public SceneGraph getSceneGraph() {
        if (this.sceneGraph == null) {
            this.sceneGraph = new SceneGraph();
            this.sceneGraph.initSceneGraph();
        }
        return this.sceneGraph;
    }

    /**
     * This field represents invalid rectangle of scheme.
     */
    @SuppressWarnings("unused")
    private UnitRectangle sceneInvalidRect;

    /**
     * This is the default constructor
     */
    public ScenePanel() {
        super();

        logger = Logger.getLogger(this.getClass().getName());

        init();
    }

    /**
     * This method invalidates <code>scene</code>.
     *
     * @param bounds bounds of invalid region.
     */
    public void sceneInvalidate(UnitRectangle bounds) {
        GuiConfiguration config = GuiConfiguration.getInstance();

        // set invalid properties
        this.sceneValid = false;
        this.sceneInvalidRect = bounds;

        // set scene dim
        Dimension dim = config.getSceneDim();
        dim = new Dimension((int) (dim.getWidth() * getZoomFactor()), (int) (dim.getHeight() * getZoomFactor()));
        setMinimumSize(dim);
        setMaximumSize(dim);
        setPreferredSize(dim);

        this.revalidate();
        this.repaint();
    }

    /**
     * @param gridValid the gridValid to set
     */
    public void setGridValid(boolean gridValid) {
        this.gridValid = gridValid;
    }

    /**
     * Tries to finish currently active manipulation.
     *
     * @param e {@link MouseEvent} with coordinates.
     * @param r2d Pointer rectangle.
     * @param manipulationQueue Instance of {@link ManipulationQueue} containing all {@link Manipulation} instances.
     * @param isMouseClicked Indicates, whether mouse clicked or just released.
     * @throws UnknownManipulationException In case of unknown {@link Manipulation}.
     */
    public void tryFinishManipulation(MouseEvent e, Rectangle2D.Double r2d, ManipulationQueue manipulationQueue,
            boolean isMouseClicked) throws UnknownManipulationException {
        // try to finish manipulation
        Manipulation m = getActiveManipulation().manipulationStop(e, r2d, manipulationQueue, getZoomFactor(),
                                                                  isMouseClicked);
        if (m != null) {
            // execute manipulation
            manipulationQueue.execute(getActiveManipulation());

            // process manipulation dependent actions
            switch (getActiveManipulation().getManipulationType()) {
                case SELECT:
                    try {
                        Select select = (Select) getActiveManipulation();

                        // set selected element properties to selected element
                        GroupNode gn = select.getManipulatedGroup();
                        getSceneProperties()
                                .setSelectedElementProperties(gn.getChildrenParameterNode().getProperties());
                        // refresh general properties panel
                        GeneralPropertiesPanel.getInstance().refresh();

                        // partPropertiesPanel
                        Part part = (Part) select.getManipulatedGroup().getChildrenElementList().getFirst()
                                .getElement();
                        MenuBar.getInstance().getViewPartPropertiesMenuItem().setEnabled(true);

                        // set part properties panel
                        // PartPropertiesDialogPanel.getInstance().setPartProperties(part.getPartProperties());
                    } catch (ClassCastException cce) {
                        MenuBar.getInstance().getViewPartPropertiesMenuItem().setEnabled(false);
                    } catch (NullPointerException npe) {
                        logger.warn("No object selected.");
                    }
                    break;
                default:
                    break;
            }

            // redraw scheme
            sceneInvalidate(null);

            // create new manipulation based on previous
            setActiveManipulation(ManipulationFactory.createNext(getActiveManipulation()));
        }
        // manipulation is not finished yet
        else {
            logger.trace("Waiting for manipulation end");
        }
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

        Manipulation m = getActiveManipulation();
        GroupNode gn = m.getManipulatedGroup();
        Transformation t = m.getManipulatedGroup().getTransformation();

        Graphics2D g2d = (Graphics2D) editFrame.getGraphics();
        Rectangle2D.Double rect = new Rectangle2D.Double(gn.getBounds().getX() * getZoomFactor(),
                gn.getBounds().getY() * getZoomFactor(), gn.getBounds().getWidth() * getZoomFactor(), gn.getBounds()
                        .getHeight() * getZoomFactor());
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
        GuiConfiguration configuration = GuiConfiguration.getInstance();

        // create new grid
        BufferedImage grid = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        logger.trace("Scene Panel width: " + getWidth() + " | height: " + getHeight());
        Graphics2D gridG2D = (Graphics2D) grid.getGraphics();

        // draw first rectangle
        gridG2D.setColor(new Color(210, 220, 255));
        Rectangle2D r = new Rectangle2D.Double(0, 0, configuration.getGridSize().doubleValue() * getZoomFactor(),
                configuration.getGridSize().doubleValue() * getZoomFactor());
        gridG2D.draw(r);

        // copy rectangle in row
        for (double d = configuration.getGridSize().doubleValue() * getZoomFactor(); d <= grid.getWidth(); d += configuration
                .getGridSize().doubleValue() * getZoomFactor()) {
            gridG2D.copyArea(0, 0, (int) (configuration.getGridSize().doubleValue() * getZoomFactor()) + 1,
                             (int) (configuration.getGridSize().intValue() * getZoomFactor()) + 1, (int) d, 0);
        }

        // copy row of rectangles
        for (double d = 0; d <= grid.getHeight() * getZoomFactor(); d += configuration.getGridSize().doubleValue() * getZoomFactor()) {
            gridG2D.copyArea(0, 0, (int) (grid.getWidth() * getZoomFactor()), (int) (configuration.getGridSize()
                    .doubleValue() * getZoomFactor()) + 1, 0, (int) d);
        }

        // return result
        return grid;
    }

    /**
     * @return
     * @throws UnknownManipulationException In case of unknown manipulation.
     */
    private BufferedImage drawManipulatedGroup() throws UnknownManipulationException {
        // create new manipulated element
        BufferedImage manipulatedGroup = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        // retrieve SceneGraph instance, save original top node
        GroupNode originalTopNode = getSceneGraph().getTopNode();

        // get manipulated group
        Manipulation manipulation = getActiveManipulation();
        GroupNode g = manipulation.getManipulatedGroup();
        getSceneGraph().setTopNode(g);

        // try to draw elements using DisplayExport
        DisplayExport de = DisplayExport.getInstance();
        try {
            de.export(getSceneGraph(), getZoomFactor(), manipulatedGroup);
        } catch (NullPointerException npe) {
            logger.error("No active manipulation");
        } finally {
            // restore original top node
            getSceneGraph().setTopNode(originalTopNode);
        }
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
        GuiConfiguration configuration = GuiConfiguration.getInstance();

        // if grid is not valid and is visible, recreate it.
        if (configuration.isGridVisible() && !isGridValid()) {
            // restore validity
            setGridValid(true);

            // re-draw grid
            this.grid = drawGrid();
        }

        // draw grid if visible
        if (configuration.isGridVisible()) {
            g2d.drawImage(this.grid, 0, 0, null);
        }
        // if scheme is not valid, recreate it.
        if (!this.sceneValid) {
            // restore validity
            this.sceneValid = true;

            // TODO add sceneInvalidRect to algorithm
            // re-draw scheme
            this.scheme = drawScheme();
        }
        // draw scheme if visible
        logger.trace("draw scheme");
        g2d.drawImage(this.scheme, 0, 0, null);

        try {

            // TODO correct behavior: in case of manipulation progress, it should use active manipulation,
            // TODO else, it should use last processed manipulation
            Manipulation m = getActiveManipulation();

            // draw work element or group, if some is being manipulated.
            if (m.isActive()) {
                BufferedImage manipulatedElement = null;
                logger.trace("calling drawManipulatedGroup");
                manipulatedElement = drawManipulatedGroup();
                g2d.drawImage(manipulatedElement, 0, 0, null);

                // draw snap symbol
                if (m.getManipulationType() != ManipulationType.SELECT) {
                    BufferedImage snapSymbol = drawSnapSymbol();
                    g2d.drawImage(snapSymbol, 0, 0, null);
                }

                // draw frame around selected group
                if (m.getManipulationType() == ManipulationType.SELECT) {
                    logger.trace("selected element frame drawing");
                    g2d.drawImage(drawSelectionFrame(), 0, 0, null);
                    g2d.drawImage(drawEditMarks(), 0, 0, null);
                }
                // draw frame around edited group
                else if (m.getManipulationType() == ManipulationType.EDIT) {
                    logger.trace("edited element frame drawing");
                    g2d.drawImage(drawEditFrame(), 0, 0, null);
                    g2d.drawImage(drawEditMarks(), 0, 0, null);
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
        GuiConfiguration configuration = GuiConfiguration.getInstance();

        logger.debug("full scheme redraw");

        // create new scheme
        BufferedImage scheme = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        // initialize DisplayExport
        DisplayExport de = DisplayExport.getInstance();
        de.setAntialiased(configuration.isSchemeAntialiased());
        de.setDebugged(configuration.isSchemeDebugged());
        de.export(getSceneGraph(), getZoomFactor(), scheme);

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

        Manipulation m = getActiveManipulation();
        GroupNode gn = m.getManipulatedGroup();
        Transformation t = m.getManipulatedGroup().getTransformation();

        Graphics2D g2d = (Graphics2D) selectionFrame.getGraphics();
        Rectangle2D.Double rect = new Rectangle2D.Double(gn.getBounds().getX() * getZoomFactor(),
                gn.getBounds().getY() * getZoomFactor(), gn.getBounds().getWidth() * getZoomFactor(), gn.getBounds()
                        .getHeight() * getZoomFactor());
        rect = t.shift(rect);
        g2d.setColor(Color.GRAY);
        g2d.draw(rect);

        return selectionFrame;
    }

    /**
     * This method draws edit marks onto {@link BufferedImage}.
     *
     * @return {@link BufferedImage} with selection frame.
     */
    private BufferedImage drawEditMarks() {
        // create new BufferedImage
        BufferedImage editMarks = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        Manipulation m = getActiveManipulation();
        GroupNode gn = m.getManipulatedGroup();
        Transformation t = m.getManipulatedGroup().getTransformation();
        Element e = gn.getChildrenElementList().getFirst().getElement();

        Graphics2D g2d = (Graphics2D) editMarks.getGraphics();
        for (int i = 0; i < e.getX().size(); i++) {
            Rectangle2D.Double r2d = new Rectangle2D.Double(e.getX().get(i).doubleValue() * getZoomFactor() - 2, e
                    .getY().get(i).doubleValue() * getZoomFactor()
                    - 2, 5, 5);
            r2d = t.shift(r2d);
            g2d.setColor(Color.YELLOW);
            g2d.draw(r2d);
        }

        return editMarks;
    }

    /**
     * This method draws snap symbol onto {@link BufferedImage}.
     *
     * @return {@link BufferedImage} with snap symbol.
     * @throws UnknownManipulationException In case of unknown manipulation.
     */
    private BufferedImage drawSnapSymbol() throws UnknownManipulationException {
        // create new snap symbol
        BufferedImage snapSymbol = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        // get configuration
        GuiConfiguration configuration = GuiConfiguration.getInstance();

        Manipulation m = getActiveManipulation();
        Transformation t = m.getManipulatedGroup().getTransformation();
        UnitPoint up = m.getLastManipulationCoordinate();
        Vector<UnitPoint> sc = m.getSnapCoordinates();

        try {
            for (UnitPoint unitPoint : sc) {
                if (unitPoint.equals(up)) {
                    Graphics2D g2d = (Graphics2D) snapSymbol.getGraphics();
                    Rectangle2D.Double rect = new Rectangle2D.Double((up.getX() - configuration.getSnapSymbolSize()
                            .doubleValue()) * getZoomFactor(), (up.getY() - configuration.getSnapSymbolSize()
                            .doubleValue()) * getZoomFactor(), configuration.getSnapSymbolSize().doubleValue() * 2,
                            configuration.getSnapSymbolSize().doubleValue() * 2);
                    rect = t.shift(rect);
                    g2d.setColor(Color.YELLOW);
                    g2d.draw(rect);

                    return snapSymbol;
                }
            }
        }
        // e.g. no elements in sc
        catch (NullPointerException npe) {
            return snapSymbol;
        }

        return snapSymbol;
    }

    /**
     * This method initializes this.
     */
    private void init() {
        GuiConfiguration config = GuiConfiguration.getInstance();

        this.setPreferredSize(new Dimension(config.getSceneDim()));
        this.setBackground(config.getSceneBackgroundColor());

        // initialize grid properties
        setGridValid(false);

        // initialize images
        this.grid = null;
        this.scheme = null;

        // enable mouse actions
        addMouseListener(new ScenePanelMouseListener());
        addMouseMotionListener(new ScenePanelMouseMotionListener());
        // enable key actions
        addKeyListener(new ScenePanelKeyListener());
        setFocusable(true);
    }

    /**
     * @return the gridValid
     */
    private boolean isGridValid() {
        return this.gridValid;
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
}
