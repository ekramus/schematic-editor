package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanel.listeners;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Vector;

import javax.swing.JPopupMenu;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.configuration.GuiConfiguration;
import cz.cvut.fel.schematicEditor.element.ElementPotential;
import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.element.element.part.Junction;
import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.element.element.part.Pin;
import cz.cvut.fel.schematicEditor.element.element.shape.Text;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.JunctionNode;
import cz.cvut.fel.schematicEditor.graphNode.Node;
import cz.cvut.fel.schematicEditor.graphNode.NodeFactory;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.graphNode.ShapeNode;
import cz.cvut.fel.schematicEditor.graphNode.xstreamConverter.GroupNodeLinkedListConditionalCoverter;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanel.ScenePanel;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanelDrawingPopup.ScenePanelDrawingPopup;
import cz.cvut.fel.schematicEditor.manipulation.Create;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationQueue;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.parts.PartType;
import cz.cvut.fel.schematicEditor.support.Support;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

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
     * Click buffer for PIN hits. Do not end on first PIN; 
     **/
    private static int hitPinOrder = 0;
    
    
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
                    
                    
  
                    
                    /*
                    if(!findHitPin(Gui.getActiveScenePanel().getSceneGraph().getTopNode(), r2d))
                    	{
                    		if(m.getManipulatedGroup().getElementType() == ElementType.T_WIRE)
                    		{
                    			m.setActive(false);
                    		}	
                    		
                    		m.clearManipulationCoordinates();	
                    		Gui.getActiveScenePanel().tryFinishManipulation(e, r2d, Gui.getActiveScenePanel().getManipulationQueue(), true);
                    		m.clearManipulationCoordinates();
                    			// Kajinek
                    			// ElementPotential.setHitObject(null);
                    		
                    	}*/
                    
                }
            } catch (NullPointerException npe) {
                logger.warn("No manipulation");
            }

        } catch (UnknownManipulationException ume) {
            logger.error(ume.getMessage());
        }
        logger.info("Zmacknul jsi prvek (mouse down)" + ElementPotential.getHitObject());
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
                            /*
                             *  this means LEFT CLICK
                             */
                            if (e.getButton() == MouseEvent.BUTTON1) {
                            	
                                {
                                                     
                                if (findHitPin(Gui.getActiveScenePanel().getSceneGraph().getTopNode(), r2d))
                                	{
                                	// this hits against PIN of the part, make JUNCTION and leave
                                	logger.info("Zmacknul jsi prvek (mouse Released)" + ElementPotential.getHitObject()); 
                                	/*
                                	 * Finish the wire if exists.
                                	 */
                                	  if(Gui.getDoAfter()!= null)
                                      {
                              		                           		  
                                    	logger.trace("Let's execute prepared Junction");
                          //              Gui.getActiveScenePanel().getManipulationQueue().execute(Gui.getDoAfter());
                                        // clear buffer to make new Junction next time
                                        Gui.clearDoAfter();
                                                								
                                      }                                	  
                                	
                                	String volt = "";
                                
                                	if(hitPinOrder==0)
                                		{
                                		volt = ElementPotential.getHitObject().getPinPotential(0);
                                		//hitPinOrder = 1;
                                		}
                                		else 
                                			{
                                	//		ElementPotential.getHitObject().setPinPotential(volt);
                                		//	hitPinOrder = 0;
                                			}
                                	
                                	// ElementPotential.setHitObject(null);
                             	   
                                	/**
                             	    * TODO hide original pin mark
                             	    **/
                                                                	                           	
                                	
                                   //if(hitPinOrder==1)
                                   {
                                	  logger.info("Rozdil potencialu!");
	                                  Pin pin = (Pin)  ElementPotential.getHitObject();
                                	  pin.setVisible(false);
	                                   pin.setPinPotential(volt);  // */
                                	  
	                                   Gui.getActiveScenePanel().tryFinishManipulation(e, r2d, mq, true);
	                                   Gui.getActiveScenePanel().getManipulationQueue().getActiveManipulation().setActive(false);
	                                   Gui.getActiveScenePanel().getManipulationQueue().execute(manipulation);
	                                   
	                                   Gui.getActiveScenePanel().getManipulationQueue().getActiveManipulation().clearManipulationCoordinates();
	                                  
	                                   //  return;
                                    } 
                         //        else {                                	 hitPinOrder = 1;   }
                                   
                                   
                                   
                       //        	el.setPinPotential(volt);
                               		                                   
                                	} else { // means the PIN is not touched -> air or wire //
                                		{
                                        	// element has infinite coordinates
                                            if (create.getPointsLeft() == Element.INFINITE_COORDINATES) {
                                            	                                            	
                                            	JPopupMenu popup = ScenePanelDrawingPopup.getScenePanelDrawingPopup(e, r2d);
                                                //popup.show(Gui.getActiveScenePanel(), e.getX(), e.getY());
                                                logger.trace("Show right-click popup2");
                                                
                                               if (el.getElementType() == ElementType.T_WIRE) {
                            						    // test, if distance between start of wire and current
                            						    // pointer position is greater, than 20
                            						    //	if (p1.distance(p2) > 20) {
                            							if (findHit(Gui.getActiveScenePanel().getSceneGraph().getTopNode(), r2d)) {
                            							  
                            								logger.info("Zmacknul jsi prvek (mouse down)" + ElementPotential.getHitObject());
                            							
                            								try {
                            									JunctionNode point = NodeFactory.createJunctionNode(new Junction());
                            									GroupNode mess = NodeFactory.createGroupNode();
                            									ParameterNode option = NodeFactory.createParameterNode();
                            									
                            									String volt = ElementPotential.getHitObject().getPinPotential(0);
                            									
                            									
                            									if(ElementPotential.getHitObject().getElementType() == ElementType.T_PART)
                                								{
                                									Part maybeGnd = (Part) ElementPotential.getHitObject();
                                									                         									
    																if(maybeGnd.getPartProperties().getPartType()==PartType.GROUND_SOURCE)
                                									volt = "0";	
                                									
                                								}
                                								
                            									if(volt == "0")
                            									{
                            									el.setPinPotential(volt);
                            									point.getElement().setPinPotential(volt);
                            									}
                            									               											
                            									mess.add(option);
                            									mess.add(point);
                            									
                            									option.setColor(Color.red);

                            									Manipulation newBorn = ManipulationFactory.create(ManipulationType.CREATE, Gui
                            											.getActiveScenePanel().getSceneGraph().getTopNode(), null, mess);
                            									       									
                            									double x;
                            									double y;
                            									
                            									//if the new line is vertical
                            									// TODO getX() and getY() from Manipulation classes should be private, try to find workaround
                            									if( manipulation.getX().get(manipulation.getX().size()-1).doubleValue() == manipulation.getX().get(manipulation.getX().size()-2).doubleValue() ) 
                            									{
                            										logger.error("vertical line");
                            										 x =  manipulation.getX().get(manipulation.getX().size()-1).doubleValue();
                            										 //y =  (r2d.getY() / Gui.getActiveScenePanel().getZoomFactor()) + (r2d.getCenterY() - r2d.getY());
                            										 y = ElementPotential.getHitObject().getY().lastElement().doubleValue();
                            										 //y = getMousePressedPoint().y;
                            									} else // new line is horizontal 
                            										{
                            										logger.error("horizontal line");
                            										y =  manipulation.getY().get(manipulation.getY().size()-1).doubleValue();
                            										// x =  (r2d.getX() / Gui.getActiveScenePanel().getZoomFactor()) + (r2d.getCenterX() - r2d.getX());
                            										x = ElementPotential.getHitObject().getX().lastElement().doubleValue();
                            										}
                            										
                            									
                            									newBorn.addManipulationCoordinates(
                            											new Pixel(x),
                            											new Pixel(y)  );

                            									if(Gui.isDoAfterActive()){
                            									
                            										Gui.setDoAfter(newBorn);
                            										newBorn = null;
                            										
                            										}   //** else                   
                            											if(!el.equals(ElementPotential.getHitObject())){
                            												
                            												Gui.getActiveScenePanel().getManipulationQueue().execute(manipulation);
                            												//Gui.getActiveScenePanel().getManipulationQueue().getActiveManipulation().setActive(false);
                            												Gui.getActiveScenePanel().tryFinishManipulation(e, r2d, Gui.getActiveScenePanel().getManipulationQueue(), true);
                            											}
                            											
                            										
                            								} catch (UnknownManipulationException h) {
                            									logger.error("Error making Junction " + h.toString());
                            								} catch (UnknownError f) {
                            									logger.error("Neznama chyba: " + f.toString());
                            								}

                            							}
                            							else {
                            								// if we click the wire (finish) in the air
                            								logger.info("hit the air");
                            								
                            								if(hitPinOrder==0)
                            									if(el.getElementType()==ElementType.T_WIRE){
                            							  
                            								//		Gui.getActiveScenePanel().getManipulationQueue().getActiveManipulation().setActive(false);
                            								//		Gui.getActiveScenePanel().getManipulationQueue().unexecute();
                            										// ElementPotential.setHitObject(null);
                            										
                            									}
                            								
                            							}
                            					
                            					}
                                                
                                                                                            
                                             /*/ the touched element is pin (or the part?)
                                            	if      (
                                            				el.getElementType() == ElementType.T_WIRE
                                            			)
                                            	{
                                            		logger.info("Kliknuto na objekt " + ElementPotential.getHitObject().getElementType().toString());
                                                    
                                                     try {
                                                     
                                                        Create create2 = (Create) Gui.getActiveScenePanel().getActiveManipulation();
                                                        create2.setFinished(true);
                                                        
                                                        Gui.getActiveScenePanel().tryFinishManipulation(
                                                                                                                e,
                                                                                                                r2d,
                                                                                                                Gui.getActiveScenePanel()
                                                                                                                        .getManipulationQueue(), false);
                                                    } catch (UnknownManipulationException ee) {
                                                        logger.error(ee.getMessage());
                                                    } 
                                                    
                                                     Gui.getActiveScenePanel().getManipulationQueue().getActiveManipulation().setActive(false);  //*/
                                                }
                                		//}                                
                                	   else // means FINITE COORDINATES 
                                            		if (create.getManipulatedGroup().getElementType() == ElementType.T_BEZIER) {
			                                                JPopupMenu popup = ScenePanelDrawingPopup.getScenePanelDrawingPopup(e, r2d);
			                                                popup.show(Gui.getActiveScenePanel(), e.getX(), e.getY());
			                                                logger.trace("Show right-click popup and make Junction");
			                                           }

                                        	}  //     if (e.getButton() == MouseEvent.BUTTON3) {
                                		
                                		                                		
                                		// if during (creation &&  left click) is not found and PIN hit
                                		//Gui.getActiveScenePanel().setActiveManipulation(null);
                           
                                	}
    							logger.info(ElementPotential.Tree(0,""));
                                }  //*/
                         	
                                
                         //* if((create.getSnapCoordinates().size()<2))
                //         Gui.getActiveScenePanel().tryFinishManipulation(e, r2d, mq, true); // */
                             
                          
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
    
   
    private boolean findHitPin(GroupNode stromek, Rectangle2D r2d) {
		LinkedList<GroupNode> mapleLeaves;

		// during the Creation do the check if other Element is hit
		mapleLeaves = stromek.getChildrenGroupList();
		boolean spojeni = false;
		int i = 0;

		for (i = 0; i < mapleLeaves.size(); i++) {
			//ElementPotential.Tree(mapleLeaves.get(i).getElementType().toString());
			// let me know that you're going through the list
			
			if(mapleLeaves.get(i).isDisabled())continue;
			spojeni = mapleLeaves.get(i).isHit(r2d, Gui.getActiveScenePanel().getZoomFactor(), null);

			// did I hit against the wire
			if (mapleLeaves.get(i).getElementType() == ElementType.T_PART)
				{
				spojeni = spojeni && true;
			    PartNode soucastka = (PartNode) mapleLeaves.get(i).getChildrenElementList().getFirst();
			    Pin m = new Pin();
			    
			    String netListEntry = "";
			    
			    Part sou = (Part) soucastka.getElement(); 

			    // if the name was not set we do it
			    if(sou.getPartProperties().getProperty("name") == "")
			    	sou.getPartProperties().setProperty("name", sou.getPartProperties().getPartType() .toString().substring(0,1) +  i);
			    	
			    netListEntry = netListEntry + " " + sou.getPartProperties().getProperty("name");
			    
			    for(int o = 0; o < soucastka.getPartPins().size(); o++){
			    	if(soucastka.isDisabled()==true)break;
			    	netListEntry = netListEntry + " " + soucastka.getPartPins().get(o).getElement().getPinPotential(0);
			    	if(soucastka.getPartPins().get(o).getElement().isHit(r2d, Gui.getActiveGraphics2D()))
			    	{
			    		if(ElementPotential.getHitObject()!=null)
			    			{
			    			soucastka.getPartPins().get(o).getElement().setPinPotential(ElementPotential.getHitObject().getPinPotential(0));
			    			// ElementPotential.setHitObject(null);
			    			
			    			// Set the GND potential to ZERO
			    			if(sou.getPartProperties().getPartType()==PartType.GROUND_SOURCE)
			    				soucastka.getPartPins().get(o).getElement().setPinPotential("0");
			    			}
			    			
			    		m.setPinPotential(soucastka.getPartPins().get(o).getElement().getPinPotential(0));
			    		ElementPotential.setHitObject(m);
			    	}
			    	
			    }
			    
			    			    			    
			   if (sou.getPartProperties().getNetlist() != "")
			   		{
				   if(!soucastka.isDisabled()) // if the part is enabled, insert a row into Netlist tree or empty space
					   ElementPotential.Tree(sou.hashCode(), netListEntry);
					   else 
						   	// or insert empty space
						   ElementPotential.Tree(sou.hashCode(), "");
			   		}
			    
				}
			else
				{
					spojeni = false;
				}
			
			
			
			// if both conditions works
			if (spojeni) {
				logger.info("You hit PIN");
				return true;
			}

			spojeni = findHitPin(mapleLeaves.get(i), r2d);
			if (spojeni)
			{
				//ElementPotential.posledniString = mapleLeaves.get(i).getChildrenElementList().getFirst().getElement().getPinPotential(0);
				logger.info("You hit PIN.");
				return true;
			}
				
		}
		return false;
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
				{
				spojeni = spojeni && true;
				
				mapleLeaves.get(i).getChildrenElementList().getLast().getElement().getPinPotential(0);
				ElementPotential.setHitObject(mapleLeaves.get(i).getChildrenElementList().getLast().getElement());
				}
			else
				{
						
				spojeni = false;
				
				}
			
			
			
			// if both conditions works
			if (spojeni) {
				return true;
			}

			spojeni = findHit(mapleLeaves.get(i), r2d);
			if (spojeni)
			{
				//ElementPotential.posledniString = mapleLeaves.get(i).getChildrenElementList().getFirst().getElement().getPinPotential(0);
				logger.info("You hit WIRE");
				return true;
			}
				
		}
		return false;
	}
    
    public static void runForNetlist(){
    	//ScenePanelMouseListener nic = new ScenePanelMouseListener();
    	//nic.findHit(stromek,);
    }
}
