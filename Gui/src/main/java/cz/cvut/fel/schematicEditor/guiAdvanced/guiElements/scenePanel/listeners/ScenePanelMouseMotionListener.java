package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanel.listeners;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.element.element.part.Junction;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.JunctionNode;
import cz.cvut.fel.schematicEditor.graphNode.NodeFactory;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.guiAdvanced.StatusBar;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanel.ScenePanel;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.support.Snap;
import cz.cvut.fel.schematicEditor.support.Support;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

/**
 * This class implements {@link MouseMotionListener} for {@link ScenePanel}.
 * 
 * @author Urban Kravjansky
 */
public class ScenePanelMouseMotionListener implements MouseMotionListener {
	/**
	 * {@link Logger} instance for logging purposes.
	 */
	private static Logger logger;

	private static JFrame okno;
	private static JTextArea lejblik;

	/**
	 * Default constructor. It initializes super instance and logger.
	 */
	public ScenePanelMouseMotionListener() {
		super();
		logger = Logger.getLogger(Gui.class.getName());
	}

	/**
	 * Method for mouse drag events processing.
	 * 
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	public void mouseDragged(MouseEvent e) {
		UnitPoint sb = Snap.getSnap(new UnitPoint(e.getX(), e.getY()), null);
		Gui.getActiveScenePanel().setActualPointerCoordinates(sb);
		// adjust coordinate according to relative start
		logger.trace("SB: " + sb);
		sb.setX(sb.getX() - Gui.getActiveScenePanel().getRelativeStart().getX());
		sb.setY(sb.getY() - Gui.getActiveScenePanel().getRelativeStart().getY());
		// set label
		StatusBar.getInstance().setCoordinatesJLabel(
				"X: " + sb.getUnitX() + " Y: " + sb.getUnitY() + " " + Gui.getActiveScenePanel().getRelativeStart());

		try {
			Manipulation m = Gui.getActiveScenePanel().getActiveManipulation();

			// manipulation is active
			if (m.isActive()) {
				UnitPoint up = m.getScaledUnitPoint(e.getX(), e.getY(), Gui.getActiveScenePanel().getZoomFactor());
				UnitPoint snap;
				if (m.getManipulationType() == ManipulationType.CREATE) {
					Element el = m.getManipulatedGroup().getChildrenElementList().getFirst().getElement();
					snap = Snap.getSnap(up, m.getSnapCoordinates(), el.getX(), el.getY());
				} else {
					snap = Snap.getSnap(up, m.getSnapCoordinates());
				}
				m.replaceLastManipulationCoordinates(snap.getUnitX(), snap.getUnitY());

				// repaint scene, it is much faster than full scene invalidate
				Gui.getActiveScenePanel().repaint();
			}
		} catch (NullPointerException npe) {
			logger.trace("No manipulation in manipulation queue");
		}
	}

	/**
	 * Method for mouse move events processing.
	 * 
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent e) {
		UnitPoint sb = Snap.getSnap(new UnitPoint(e.getX(), e.getY()), null);
		Gui.getActiveScenePanel().setActualPointerCoordinates(sb);
		// adjust coordinate according to relative start
		sb.setX(sb.getX() - Gui.getActiveScenePanel().getRelativeStart().getX());
		sb.setY(sb.getY() - Gui.getActiveScenePanel().getRelativeStart().getY());
		// set label
		StatusBar.getInstance().setCoordinatesJLabel(
				"X: " + sb.getUnitX() + " Y: " + sb.getUnitY() + " " + Gui.getActiveScenePanel().getRelativeStart());

		try {
			Manipulation m = Gui.getActiveScenePanel().getActiveManipulation();

			// manipulation is active
			if (m.isActive()) {
				UnitPoint up = m.getScaledUnitPoint(e.getX(), e.getY(), Gui.getActiveScenePanel().getZoomFactor());
				UnitPoint snap;
				if (m.getManipulationType() == ManipulationType.CREATE) {
					Element el = m.getManipulatedGroup().getChildrenElementList().getFirst().getElement();
					snap = Snap.getSnap(up, m.getSnapCoordinates(), el.getX(), el.getY());

					// get pointer rectangle
					Rectangle2D.Double r2d = Support.createPointerRectangle(new Point2D.Double(e.getX(), e.getY()),new Point2D.Double(1,1));
							
					
							
							//new Point2D.Double(e.getX(), e.getY()),
							//GuiConfiguration.getInstance().getPointerRectangle());

					System.err.println(Boolean.toString(el.getElementType() == ElementType.T_WIRE));
					
				} else {
					snap = Snap.getSnap(up, m.getSnapCoordinates());
				}
				m.replaceLastManipulationCoordinates(snap.getUnitX(), snap.getUnitY());

				// repaint scene, it is much faster than full scene invalidate
				Gui.getActiveScenePanel().repaint();
			}
		} catch (NullPointerException npe) {
			logger.trace("No active manipulation");
		}
	}

	
}
