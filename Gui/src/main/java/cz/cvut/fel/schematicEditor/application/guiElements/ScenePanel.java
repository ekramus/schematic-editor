package cz.cvut.fel.schematicEditor.application.guiElements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.application.guiElements.listeners.scenePanel.ScenePanelKeyListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.scenePanel.ScenePanelMouseListener;
import cz.cvut.fel.schematicEditor.application.guiElements.listeners.scenePanel.ScenePanelMouseMotionListener;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.core.coreStructures.SceneGraph;
import cz.cvut.fel.schematicEditor.export.DisplayExport;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationQueue;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.properties.GuiConfiguration;
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
     * This field represents scheme.
     */
    private BufferedImage scheme;

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
        GuiConfiguration configuration = GuiConfiguration.getInstance();

        // create new grid
        BufferedImage grid = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D gridG2D = (Graphics2D) grid.getGraphics();

        // draw first rectangle
        gridG2D.setColor(new Color(210, 220, 255));
        Rectangle2D r = new Rectangle2D.Double(0, 0, configuration.getGridSize().doubleValue(), configuration
                .getGridSize().doubleValue());
        gridG2D.draw(r);

        // copy rectangle in row
        for (double d = configuration.getGridSize().doubleValue(); d <= grid.getWidth(); d += configuration
                .getGridSize().doubleValue()) {
            gridG2D.copyArea(0, 0, configuration.getGridSize().intValue() + 1,
                             configuration.getGridSize().intValue() + 1, (int) d, 0);
        }

        // copy row of rectangles
        for (double d = 0; d <= grid.getHeight(); d += configuration.getGridSize().doubleValue()) {
            gridG2D.copyArea(0, 0, grid.getWidth(), configuration.getGridSize().intValue() + 1, 0, (int) d);
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
            if (m.isActive()) {
                BufferedImage manipulatedElement = null;
                logger.trace("calling drawManipulatedGroup");
                manipulatedElement = drawManipulatedGroup();
                g2d.drawImage(manipulatedElement, 0, 0, null);

                // draw frame around selected group
                if (m.getManipulationType() == ManipulationType.SELECT) {
                    logger.trace("selected element frame drawing");
                    BufferedImage selectionFrame = drawSelectionFrame();
                    g2d.drawImage(selectionFrame, 0, 0, null);
                }
                // draw frame around edited group
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
        GuiConfiguration configuration = GuiConfiguration.getInstance();

        logger.debug("full scheme redraw");

        // create new scheme
        BufferedImage scheme = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        // initialize DisplayExport
        DisplayExport de = DisplayExport.getExport();
        de.setAntialiased(configuration.isSchemeAntialiased());
        de.setDebugged(configuration.isSchemeDebugged());
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
        GuiConfiguration config = GuiConfiguration.getInstance();

        this.setPreferredSize(new Dimension(config.getSceneDim()));
        this.setBackground(config.getSceneBackgroundColor());

        // initialize grid properties
        setGridValid(false);

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
        Manipulation m = Structures.getActiveManipulation()
                .manipulationStop(e, r2d, manipulationQueue, getSchemeSG().getTopNode(), isMouseClicked);
        if (m != null) {
            // execute manipulation
            manipulationQueue.execute(Structures.getActiveManipulation(), getSchemeSG().getTopNode());

            // redraw scheme
            ScenePanel.getInstance().schemeInvalidate(null);

            // create new manipulation based on previous
            Structures.setActiveManipulation(ManipulationFactory.createNext(Structures.getActiveManipulation()));
        }
        // manipulation is not finished yet
        else {
            logger.trace("Waiting for manipulation end");
        }
    }
}
