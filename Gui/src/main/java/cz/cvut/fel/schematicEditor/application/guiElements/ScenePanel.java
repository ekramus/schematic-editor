package cz.cvut.fel.schematicEditor.application.guiElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.application.Gui;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.scenePanel.ScenePanelKeyListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.scenePanel.ScenePanelMouseListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.scenePanel.ScenePanelMouseMotionListener;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.element.element.shape.Line;
import cz.cvut.fel.schematicEditor.element.element.shape.Shape;
import cz.cvut.fel.schematicEditor.export.DisplayExport;
import cz.cvut.fel.schematicEditor.graphNode.ElementNode;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.ShapeNode;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.manipulation.manipulation.Create;
import cz.cvut.fel.schematicEditor.manipulation.manipulation.Delete;
import cz.cvut.fel.schematicEditor.manipulation.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.manipulation.Select;
import cz.cvut.fel.schematicEditor.support.Transformation;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;
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
     * @param sceneJPanel
     *            the sceneJPanel to set
     */
    private static final void setSceneJPanel(ScenePanel sceneJPanel) {
        ScenePanel.sceneJPanel = sceneJPanel;
    }

    /**
     * This field represents snap to grid status.
     */
    private boolean       snapToGrid;
    /**
     * This field represents grid.
     */
    private BufferedImage grid;
    /**
     * This field represents grid interval.
     */
    private Unit          gridSize;
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
     * 
     * @throws UnknownManipulationException
     */
    private ScenePanel() {
        super();

        logger = Logger.getLogger(Gui.class.getName());

        init();
    }

    /**
     * @return the gridSize
     */
    public Unit getGridSize() {
        return this.gridSize;
    }

    /**
     * @return the schemeSG
     */
    public SceneGraph getSchemeSG() {
        return this.schemeSG;
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
     * Method used for correct finalization method selection.
     * 
     * @throws UnknownManipulationException
     *             In case of unknown manipulation.
     */
    @Deprecated
    public void processFinalManipulationStep() throws UnknownManipulationException {
        Manipulation m = Structures.getManipulation();
        ManipulationType mt = m.getManipulationType();

        switch (mt) {
            case CREATE:
                processFinalCreateStep();
                break;
            case DELETE:
                processFinalDeleteStep();
                break;
            default:
                processFinalSelectStep();
                break;
        }

        // create new manipulation of the same type as previous
        Structures.setManipulation(ManipulationFactory.duplicate(m));
    }

    /**
     * This method processes final step for {@link Create} manipulation. It is responsible for {@link Element}
     * finalization, {@link ScenePanel} redraw, etc.
     * 
     * @throws UnknownManipulationException
     *             In case of unknown manipulation.
     */
    @Deprecated
    private void processFinalCreateStep() throws UnknownManipulationException {
        Element child = null;
        ShapeNode sn;
        GroupNode gn;
        ParameterNode pn;

        logger.debug("processing final manipulation step");

        child = Structures.getManipulation().getManipulatedElement();
        Structures.getManipulation().setActive(false);

        sn = new ShapeNode((Shape) child);
        pn = new ParameterNode();

        pn.setProperties(Structures.getSceneProperties().getSceneElementProperties());

        logger.debug("Nodes created");

        gn = new GroupNode();
        gn.add(pn);
        gn.add(sn);

        this.schemeSG.getTopNode().add(gn);
        schemeInvalidate(child.getBounds());
    }

    /**
     * This method processes final step for {@link Delete} manipulation. It is responsible for {@link Element}
     * finalization, {@link ScenePanel} redraw, etc.
     * 
     * @throws UnknownManipulationException
     *             In case of unknown manipulation.
     */
    @Deprecated
    private void processFinalDeleteStep() throws UnknownManipulationException {
        logger.debug("processing final DELETE step");

        // this is really not necessary, it is here only for possible future uses
        Delete delete = (Delete) Structures.getManipulation();

        schemeInvalidate(null);
    }

    /**
     * This method invalidates <code>scheme</code>.
     * 
     * @param bounds
     *            bounds of invalid region.
     */
    public void schemeInvalidate(UnitRectangle bounds) {
        this.schemeValid = false;
        this.schemeInvalidRect = bounds;
        this.repaint();
    }

    /**
     * @param gridSize
     *            the gridSize to set
     */
    public void setGridSize(Unit gridSize) {
        this.gridSize = gridSize;
        this.gridValid = false;
    }

    /**
     * @param gridVisible
     *            the gridVisible to set
     */
    public void setGridVisible(boolean gridVisible) {
        this.gridVisible = gridVisible;
    }

    /**
     * @param schemeAntialiased
     *            the schemeAntialiased to set
     */
    public void setSchemeAntialiased(boolean schemeAntialiased) {
        this.schemeValid = false;
        this.schemeAntialiased = schemeAntialiased;
    }

    /**
     * @param schemeDebugged
     *            the schemeAntialiased to set
     */
    public void setSchemeDebugged(boolean schemeDebugged) {
        this.schemeValid = false;
        this.schemeDebugged = schemeDebugged;
    }

    /**
     * @param snapToGrid
     *            the snapToGrid to set
     */
    public void setSnapToGrid(boolean snapToGrid) {
        this.snapToGrid = snapToGrid;
    }

    /**
     * This method draws grid onto <code>BufferedImage</code>.
     * 
     * @return <code>BufferedImage</code> with grid.
     */
    private BufferedImage drawGrid() {
        // create new grid
        BufferedImage grid = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D gridG2D = (Graphics2D) grid.getGraphics();

        // draw first rectangle
        gridG2D.setColor(new Color(210, 220, 255));
        Rectangle2D r = new Rectangle2D.Double(0, 0, this.gridSize.doubleValue(), this.gridSize.doubleValue());
        gridG2D.draw(r);

        // copy rectangle in row
        for (double d = this.gridSize.doubleValue(); d <= grid.getWidth(); d += this.gridSize.doubleValue()) {
            gridG2D.copyArea(0, 0, this.gridSize.intValue() + 1, this.gridSize.intValue() + 1, (int) d, 0);
        }

        // copy row of rectangles
        for (double d = 0; d <= grid.getHeight(); d += this.gridSize.doubleValue()) {
            gridG2D.copyArea(0, 0, grid.getWidth(), this.gridSize.intValue() + 1, 0, (int) d);
        }

        // return result
        return grid;
    }

    /**
     * This method draws actual manipulated element onto <code>BufferedImage</code>.
     * 
     * @return <code>BufferedImage</code> with actual manipulated element.
     * @throws UnknownManipulationException
     *             In case of unknown manipulation.
     */
    private BufferedImage drawManipulatedElement() throws UnknownManipulationException {
        // create new manipulated elelement
        BufferedImage manipulatedElement = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        // prepare element
        SceneGraph sg = new SceneGraph();
        // this.manipulation.setManipulationCoordinates(getManipXCoord(), getManipYCoord());
        Shape s = (Shape) Structures.getManipulation().getManipulatedElement();

        // create SceneGraph
        ParameterNode p = new ParameterNode();
        ElementNode e = new ElementNode(s);
        GroupNode g = new GroupNode();
        g.add(e);
        g.add(p);
        sg.setTopNode(g);

        // try to draw elements using DisplayExport
        DisplayExport de = DisplayExport.getExport();
        de.export(sg, manipulatedElement);

        return manipulatedElement;
    }

    /**
     * @return
     * @throws UnknownManipulationException
     *             In case of unknown manipulation.
     */
    private BufferedImage drawManipulatedGroup() throws UnknownManipulationException {
        // create new manipulated element
        BufferedImage manipulatedGroup = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        // create SceneGraph
        SceneGraph sg = new SceneGraph();

        // get manipulated group
        Manipulation manipulation = Structures.getManipulation();
        GroupNode g = manipulation.getManipulatedGroup();
        sg.setTopNode(g);

        // try to draw elements using DisplayExport
        DisplayExport de = DisplayExport.getExport();
        de.export(sg, manipulatedGroup);

        return manipulatedGroup;
    }

    /**
     * This method draws selection frame onto {@link BufferedImage}.
     * 
     * @return {@link BufferedImage} with selection frame.
     * @throws UnknownManipulationException
     *             In case of unknown manipulation.
     */
    private BufferedImage drawSelectionFrame() throws UnknownManipulationException {
        // create new selection frame
        BufferedImage selectionFrame = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        GroupNode gn = Structures.getManipulation().getManipulatedGroup();
        Transformation t = Structures.getManipulation().getManipulatedGroup().getTransformation();

        Graphics2D g2d = (Graphics2D) selectionFrame.getGraphics();
        Rectangle2D.Double rect = new Rectangle2D.Double(gn.getBounds().getX(), gn.getBounds().getY(), gn.getBounds()
                .getWidth(), gn.getBounds().getHeight());
        rect = t.shift(rect);
        g2d.setColor(Color.GRAY);
        g2d.draw(rect);

        return selectionFrame;
    }

    /**
     * Draws edit frame onto {@link BufferedImage}.
     * 
     * @return {@link BufferedImage} with edit frame.
     * @throws UnknownManipulationException
     *             In case of unknown manipulation.
     */
    private BufferedImage drawEditFrame() throws UnknownManipulationException {
        // create new edit frame
        BufferedImage editFrame = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        GroupNode gn = Structures.getManipulation().getManipulatedGroup();
        Transformation t = Structures.getManipulation().getManipulatedGroup().getTransformation();

        Graphics2D g2d = (Graphics2D) editFrame.getGraphics();
        Rectangle2D.Double rect = new Rectangle2D.Double(gn.getBounds().getX(), gn.getBounds().getY(), gn.getBounds()
                .getWidth(), gn.getBounds().getHeight());
        rect = t.shift(rect);
        g2d.setColor(Color.ORANGE);
        g2d.draw(rect);

        return editFrame;
    }

    /**
     * This method draws scene and it's specific features.
     * 
     * @param g
     *            <code>Graphics</code> to draw on.
     * @throws UnknownManipulationException
     *             In case of unknown manipulation.
     */
    private void drawScene(Graphics g) throws UnknownManipulationException {
        Graphics2D g2d = (Graphics2D) g;

        // if grid is not valid and is visible, recreate it.
        if (this.gridVisible && !this.gridValid) {
            // restore validity
            this.gridValid = true;

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
        logger.debug("draw scheme");
        g2d.drawImage(this.scheme, 0, 0, null);

        // draw work element or group, if some is being manipulated.
        if (Structures.getManipulation().isActive() && (Structures.getManipulation().isManipulatingElements() || Structures
                .getManipulation().isManipulatingGroups())) {
            logger.debug("manipulated element/group redrawn");
            BufferedImage manipulatedElement = null;
            // manipulation manipulates with group
            if (Structures.getManipulation().isManipulatingGroups()) {
                logger.trace("calling drawManipulatedGroup");
                manipulatedElement = drawManipulatedGroup();
            }
            // manipulation manipulates with single element
            else if (Structures.getManipulation().isManipulatingElements()) {
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
        if (Structures.getManipulation().isActive()) {
            // draw frame around selected group
            if (Structures.getManipulation().getManipulationType() == ManipulationType.SELECT) {
                logger.trace("selected element frame drawing");
                BufferedImage selectionFrame = drawSelectionFrame();
                g2d.drawImage(selectionFrame, 0, 0, null);
            }
            // draw frame around selected group
            else if (Structures.getManipulation().getManipulationType() == ManipulationType.EDIT) {
                logger.trace("create element frame drawing");
                BufferedImage selectionFrame = drawEditFrame();
                g2d.drawImage(selectionFrame, 0, 0, null);
            }
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
     * This method initializes this.
     */
    private void init() {
        this.setPreferredSize(new Dimension(Integer.parseInt(Structures.getProperties()
                .getProperty("sceneXDim", Constants.DEFAULT_SCENE_XDIM)), Integer.parseInt(Structures.getProperties()
                .getProperty("sceneYDim", "1024"))));
        this.setBackground(Color.WHITE);

        // initialize grid properties
        this.gridVisible = true;
        this.gridSize = new Pixel(25);
        this.gridValid = false;

        // initialize scheme properties
        this.schemeAntialiased = true;
        this.schemeDebugged = false;
        try {
            Structures.setManipulation(ManipulationFactory.create(ManipulationType.CREATE, new Line()));
        } catch (UnknownManipulationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // initialize images
        this.grid = null;
        this.scheme = null;
        this.schemeSG = new SceneGraph();
        this.schemeSG.manualCreateSceneGraph2();
    }

    @Deprecated
    public void processActualManipulationStep() throws UnknownManipulationException {
        ManipulationType mt = Structures.getManipulation().getManipulationType();

        switch (mt) {
            case CREATE:
                processActualCreateStep();
                break;
            case MOVE:
            case SELECT:
                processActualSelectStep();
                break;
            case EDIT:
                break;
            case DELETE:
                break;
        }
    }

    /**
     * This method repaints correctly scene after change of point coordinates in {@link Create} manipulation.
     */
    @Deprecated
    private void processActualCreateStep() {
        logger.debug("pocessing actual manipulation step");

        this.repaint();
    }

    /**
     * This method repaints correctly scene after change of point coordinates.
     */
    @Deprecated
    private void processActualSelectStep() {
        logger.debug("pocessing actual manipulation step");

        this.repaint();
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
     * This method processes final step for {@link Select} manipulation. It is responsible for {@link Element}
     * finalization, {@link ScenePanel} redraw, etc.
     * 
     * @throws UnknownManipulationException
     *             In case of unknown manipulation.
     */
    @Deprecated
    private void processFinalSelectStep() throws UnknownManipulationException {
        logger.trace("processing final SELECT step");

        Select select = (Select) Structures.getManipulation();
        select.setActive(true);

        GroupNode gn = select.getManipulatedGroup();

        schemeInvalidate(gn.getBounds());
    }
}
