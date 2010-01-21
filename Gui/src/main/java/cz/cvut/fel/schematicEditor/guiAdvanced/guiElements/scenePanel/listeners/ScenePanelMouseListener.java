package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanel.listeners;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import javax.swing.JPopupMenu;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.configuration.GuiConfiguration;
import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.element.element.part.Junction;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanel.ScenePanel;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanelDrawingPopup.ScenePanelDrawingPopup;
import cz.cvut.fel.schematicEditor.manipulation.Create;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationQueue;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.original.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.original.graphNode.JunctionNode;
import cz.cvut.fel.schematicEditor.original.graphNode.NodeFactory;
import cz.cvut.fel.schematicEditor.original.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.support.Support;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;

/**
 * This class implements {@link MouseListener} for {@link ScenePanel}.
 *
 * @author Urban Kravjansky
 */
public class ScenePanelMouseListener implements MouseListener {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger  logger;

    /**
     * Point of <code>mousePressed</code> event.
     */
    private Point2D.Double mousePressedPoint  = null;
    /**
     * Point of <code>mouseReleased</code> event.
     */
    private Point2D.Double mouseReleasedPoint = null;

    /**
     * Default constructor. It initializes super instance and logger.
     */
    public ScenePanelMouseListener() {
        super();
        logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * Getter for <code>mousePressedPoint</code>.
     *
     * @return {@link Point2D.Double} value of <code>mousePressedPoint</code>.
     */
    private Point2D.Double getMousePressedPoint() {
        return this.mousePressedPoint;
    }

    /**
     * Getter for <code>mouseReleasedPoint</code>.
     *
     * @return {@link Point2D.Double} value of <code>mouseReleasedPoint</code>.
     */
    private Point2D.Double getMouseReleasedPoint() {
        return this.mouseReleasedPoint;
    }

    /**
     * Indicates, whether mouse was clicked or not.
     *
     * @return <code>true</code>, if mouse was clicked, <code>false</code> else.
     */
    private boolean isMouseClicked() {
        return getMousePressedPoint().equals(getMouseReleasedPoint());
    }

    /**
     * Method for mouse click events processing.
     *
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent e) {
        // nothing to do
    }

    /**
     * Method for mouse enter events processing.
     *
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent e) {
        logger.trace("mouse entered");

        // request focus for ScenePanel
        Gui.getActiveScenePanel().requestFocusInWindow();
    }

    /**
     * Method for mouse exit events processing.
     *
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent e) {
        logger.trace("mouse left");
    }

    /**
     * Method for mouse press events processing.
     *
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent e) {
        try {
            setMousePressedPoint(new Point2D.Double(e.getPoint().getX(), e.getPoint().getY()));

            // get pointer rectangle
            Rectangle2D.Double r2d = Support.createPointerRectangle(new Point2D.Double(e.getX(), e.getY()),
                                                                    GuiConfiguration.getInstance()
                                                                            .getPointerRectangle());

            Manipulation m = Gui.getActiveScenePanel().getActiveManipulation();
            try {
                if (!m.isActive() || (m.getManipulationType() == ManipulationType.SELECT)) {
                    // start manipulation and set result as active manipulation
                    Gui.getActiveScenePanel()
                            .setActiveManipulation(
                                                   m.manipulationStart(e, r2d, Gui.getActiveScenePanel()
                                                           .getManipulationQueue(), Gui.getActiveScenePanel()
                                                           .getZoomFactor(), isMouseClicked(), Gui
                                                           .getActiveGraphics2D()));
                }
            } catch (NullPointerException npe) {
                logger.warn("No manipulation");
            }

        } catch (UnknownManipulationException ume) {
            logger.error(ume.getMessage());
        }
    }

    /**
     * Method for mouse release events processing.
     *
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent e) {
        try {
            ManipulationQueue mq = Gui.getActiveScenePanel().getManipulationQueue();

            setMouseReleasedPoint(new Point2D.Double(e.getPoint().getX(), e.getPoint().getY()));

            // get pointer rectangle
            Rectangle2D.Double r2d = Support.createPointerRectangle(new Point2D.Double(e.getX(), e.getY()),
                                                                    GuiConfiguration.getInstance()
                                                                            .getPointerRectangle());

            // mouse was clicked
            if (getMouseReleasedPoint().equals(getMousePressedPoint())) {
                logger.debug("Mouse CLICKED");

                Manipulation manipulation = Gui.getActiveScenePanel().getActiveManipulation();
                try {
                    ManipulationType mt = manipulation.getManipulationType();

                    switch (mt) {
                        case CREATE:
                            Element el = manipulation.getManipulatedGroup().getChildrenElementList().getFirst().getElement();
                            Create create = (Create) manipulation;
                            // right mouse button is clicked
                            if (e.getButton() == MouseEvent.BUTTON3) {
                                // element has infinite coordinates
                                if (create.getPointsLeft() == Element.INFINITE_COORDINATES) {
                                    JPopupMenu popup = ScenePanelDrawingPopup.getScenePanelDrawingPopup(e, r2d);
                                    popup.show(Gui.getActiveScenePanel(), e.getX(), e.getY());
                                    logger.trace("Show right-click popup2");
                                    

                                    if (el.getElementType() == ElementType.T_WIRE) {
            							
                						// test, if distance between start of wire and current
                						// pointer position is greater, than 20
                						//	if (p1.distance(p2) > 20) {
                							if (findHit(Gui.getActiveScenePanel().getSceneGraph().getTopNode(), r2d)) {


                								try {

                								    JunctionNode point = NodeFactory.createJunctionNode(new Junction());
                									GroupNode mess = NodeFactory.createGroupNode();
                									ParameterNode option = NodeFactory.createParameterNode();

                									mess.add(option);
                									mess.add(point);
                									
                									option.setColor(Color.red);

                									Manipulation newBorn = ManipulationFactory.create(ManipulationType.CREATE, Gui
                											.getActiveScenePanel().getSceneGraph().getTopNode(), null, mess);
                									// newBorn.setManipulatedGroup(mess);
                									
                									double x;
                									double y;
                									
                											
                									//if the new line is vertical
                									// TODO getX() and getY() from Manipulation classes should be private, try to find workaround
                									if( manipulation.getX().get(manipulation.getX().size()-1).doubleValue() == manipulation.getX().get(manipulation.getX().size()-2).doubleValue() ) 
                									{
                										logger.error("vertical line");
                										 x =  manipulation.getX().get(manipulation.getX().size()-1).doubleValue();
                										 //x =  m.getSnapCoordinates().get(m.getSnapCoordinates().size()-1).getX();
                										 //y =  r2d.getCenterY(); 
                										 y =  (r2d.getY() / Gui.getActiveScenePanel().getZoomFactor()) + (r2d.getCenterY() - r2d.getY());
                									} else // new line is horizontal 
                										{
                										logger.error("horizontal line");
                										y =  manipulation.getY().get(manipulation.getY().size()-1).doubleValue();
                										//y =  m.getSnapCoordinates().get(m.getSnapCoordinates().size()-1).getY();
                										//x =  r2d.getCenterX();
                										x =  (r2d.getX() / Gui.getActiveScenePanel().getZoomFactor()) + (r2d.getCenterX() - r2d.getX()); 
                										}
                										
                									
                									newBorn.addManipulationCoordinates(
                											new Pixel(x),
                											new Pixel(y)  );

                									if(Gui.isDoAfterActive()){
                									
                										Gui.setDoAfter(newBorn);
                										newBorn = null;
                										
                									} else ;
                										


                									logger.info("XXXXXXXXXX  Vyrobeno krizeni XXXXXXXXXXXXX");

                								} catch (UnknownManipulationException h) {
                									logger.error("Error making Junction " + h.toString());
                								} catch (UnknownError f) {
                									logger.error("Neznámá chyba");
                								}

                							}
                						//}
                					}
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                    // check buffer doAfter if we have to create Junction
                                    if(Gui.getDoAfter()!= null)
                                      {
                                    	logger.trace("Let's execute prepared Junction");
	                                    Gui.getActiveScenePanel().getManipulationQueue().execute(Gui.getDoAfter());
	                                    // clear buffer to make new Junction next time
	                                    Gui.clearDoAfter();
										
                                      }
	                                    
                                } else if (create.getManipulatedGroup().getElementType() == ElementType.T_BEZIER) {
                                    JPopupMenu popup = ScenePanelDrawingPopup.getScenePanelDrawingPopup(e, r2d);
                                    popup.show(Gui.getActiveScenePanel(), e.getX(), e.getY());
                                    logger.trace("Show right-click popup and make Junction");
                                    
									
                                    
                                }
                                
                                
                                
                                
                            }
                            // left mouse button is clicked
                            else if (e.getButton() == MouseEvent.BUTTON1) {
                                Gui.getActiveScenePanel().tryFinishManipulation(e, r2d, mq, true);
                            }
                            break;
                        case SELECT:
                        case DELETE:
                        case EDIT:
                        case MOVE:
                            Gui.getActiveScenePanel().tryFinishManipulation(e, r2d, mq, true);
                            break;
                        default:
                            break;
                    }

                    // fire scene graph update
                    Gui.getActiveScenePanel().getSceneGraph().fireSceneGraphUpdateEvent();
                } catch (NullPointerException npe) {
                    logger.warn("No manipulation.");
                }
            }
            // mouse button was just released after drag
            else {
                Gui.getActiveScenePanel().tryFinishManipulation(e, r2d, mq, false);
            }
        } catch (UnknownManipulationException ume) {
            logger.error(ume.getMessage());
        }
    }

    private void setMousePressedPoint(Point2D.Double mousePressedPoint) {
        this.mousePressedPoint = mousePressedPoint;
    }

    private void setMouseReleasedPoint(Point2D.Double mouseReleasedPoint) {
        this.mouseReleasedPoint = mouseReleasedPoint;
    }
    
    private boolean findHit(GroupNode stromek, Rectangle2D r2d) {
		LinkedList<GroupNode> mapleLeaves;

		// during the Creation do the check if other Element is hit

		mapleLeaves = stromek.getChildrenGroupList();

		boolean spojeni = false;

		int i = 0;
		

		for (i = 0; i < mapleLeaves.size(); i++) {
			// let me know that you're going through the list
			
			if(mapleLeaves.get(i).isDisabled())continue;
			
			spojeni = mapleLeaves.get(i).isHit(r2d, Gui.getActiveScenePanel().getZoomFactor(), null);

			// did I hit against the wire
			if (mapleLeaves.get(i).getElementType() == ElementType.T_WIRE)
				spojeni = spojeni && true;
			else
				spojeni = false;

			// if both conditions works
			if (spojeni) {
				return true;
			}

			spojeni = findHit(mapleLeaves.get(i), r2d);
			if (spojeni)
				return true;
		}
		return false;
	}
    
}
