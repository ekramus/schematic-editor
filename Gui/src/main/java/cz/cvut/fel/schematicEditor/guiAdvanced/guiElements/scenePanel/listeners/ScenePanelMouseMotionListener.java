package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanel.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.configuration.GuiConfiguration;
import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.element.element.part.Junction;
import cz.cvut.fel.schematicEditor.guiAdvanced.StatusBar;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.scenePanel.ScenePanel;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationQueue;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.original.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.original.graphNode.JunctionNode;
import cz.cvut.fel.schematicEditor.original.graphNode.NodeFactory;
import cz.cvut.fel.schematicEditor.original.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.original.graphNode.xstreamConverter.GroupNodeLinkedListConditionalCoverter;
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

		okno = new JFrame("Krizeni");
		lejblik = new JTextArea("init");
		okno.add(lejblik);
		okno.setBounds(0, 0, 200, 200);
		okno.setVisible(true);

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
		sb
				.setX(sb.getX()
						- Gui.getActiveScenePanel().getRelativeStart().getX());
		sb
				.setY(sb.getY()
						- Gui.getActiveScenePanel().getRelativeStart().getY());
		// set label
		StatusBar.getInstance().setCoordinatesJLabel(
				"X: " + sb.getUnitX() + " Y: " + sb.getUnitY() + " "
						+ Gui.getActiveScenePanel().getRelativeStart());

		try {
			Manipulation m = Gui.getActiveScenePanel().getActiveManipulation();

			// manipulation is active
			if (m.isActive()) {
				UnitPoint up = m.getScaledUnitPoint(e.getX(), e.getY(), Gui
						.getActiveScenePanel().getZoomFactor());
				UnitPoint snap;
				if (m.getManipulationType() == ManipulationType.CREATE) {
					Element el = m.getManipulatedGroup()
							.getChildrenElementList().getFirst().getElement();
					snap = Snap.getSnap(up, m.getSnapCoordinates(), el.getX(),
							el.getY());
				} else {
					snap = Snap.getSnap(up, m.getSnapCoordinates());
				}
				m.replaceLastManipulationCoordinates(snap.getUnitX(), snap
						.getUnitY());

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
		sb
				.setX(sb.getX()
						- Gui.getActiveScenePanel().getRelativeStart().getX());
		sb
				.setY(sb.getY()
						- Gui.getActiveScenePanel().getRelativeStart().getY());
		// set label
		StatusBar.getInstance().setCoordinatesJLabel(
				"X: " + sb.getUnitX() + " Y: " + sb.getUnitY() + " "
						+ Gui.getActiveScenePanel().getRelativeStart());

		try {
			Manipulation m = Gui.getActiveScenePanel().getActiveManipulation();

			// manipulation is active
			if (m.isActive()) {
				UnitPoint up = m.getScaledUnitPoint(e.getX(), e.getY(), Gui
						.getActiveScenePanel().getZoomFactor());
				UnitPoint snap;
				if (m.getManipulationType() == ManipulationType.CREATE) {
					Element el = m.getManipulatedGroup()
							.getChildrenElementList().getFirst().getElement();
					snap = Snap.getSnap(up, m.getSnapCoordinates(), el.getX(),
							el.getY());

					// get pointer rectangle
					Rectangle2D.Double r2d = Support.createPointerRectangle(
							new Point2D.Double(e.getX(), e.getY()),
							GuiConfiguration.getInstance()
									.getPointerRectangle());

					System.err.println(Boolean.toString(el.getElementType() == ElementType.T_WIRE));
					if (el.getElementType() == ElementType.T_WIRE) {
						Point2D p1 = new Point2D.Double(el.getX().get(0).doubleValue(), el
								.getY().get(0).doubleValue());
						Point2D p2 = new Point2D.Double(el.getX().get(1).doubleValue(), el
								.getY().get(1).doubleValue());
						lejblik.setText(p1.distance(p2) + "");
						// test, if distance between start of wire and current pointer position is greater, than 20
						if(p1.distance(p2) > 20) {
							if (findHit(Gui.getActiveScenePanel().getSceneGraph()
									.getTopNode(), r2d)) {
								logger.info("YYYYYY  Vyrobeno krizeni XXXXXXXXXXXXX");
								lejblik.setText("Vyrabim");
								lejblik.setText("2 Vyrabim");

								try {

									lejblik.setText("Za try Vyrabim");

									JunctionNode point = NodeFactory
											.createJunctionNode(new Junction());
									GroupNode mess = NodeFactory.createGroupNode();
									ParameterNode option = NodeFactory
											.createParameterNode();

									mess.add(option);
									mess.add(point);

									Manipulation newBorn = ManipulationFactory.create(
											ManipulationType.CREATE, Gui
													.getActiveScenePanel()
													.getSceneGraph().getTopNode(),
											null, mess);
									// newBorn.setManipulatedGroup(mess);
									newBorn.addManipulationCoordinates(
											new Pixel(r2d.x), new Pixel(r2d.y));

									Gui.getActiveScenePanel().getManipulationQueue()
											.execute(newBorn);
									Gui.getActiveScenePanel().sceneInvalidate(null);

									lejblik.setText("Vyrabim: done");

									logger
											.info("XXXXXXXXXX  Vyrobeno krizeni XXXXXXXXXXXXX");

								} catch (UnknownManipulationException h) {
									logger.error("Error making Junction "
											+ h.toString());
								} catch (UnknownError f) {
									logger.error("Neznámá chyba");
								}

							}
						}
					}
				} else {
					snap = Snap.getSnap(up, m.getSnapCoordinates());
				}
				m.replaceLastManipulationCoordinates(snap.getUnitX(), snap
						.getUnitY());

				// repaint scene, it is much faster than full scene invalidate
				Gui.getActiveScenePanel().repaint();
			}
		} catch (NullPointerException npe) {
			logger.trace("No active manipulation");
		}
	}

	private boolean findHit(GroupNode stromek, Rectangle2D r2d) {

		Manipulation m = Gui.getActiveScenePanel().getActiveManipulation();
		LinkedList<GroupNode> mapleLeaves;

		// during the Creation do the check if other Element is hit

		mapleLeaves = stromek.getChildrenGroupList();

		boolean spojeni = false;

		int i = 0;
		this.lejblik.setText(lejblik.getText() + "\n prvku "
				+ mapleLeaves.size());

		for (i = 0; i < mapleLeaves.size(); i++) {
			// let me know that you're going through the list

			spojeni = mapleLeaves.get(i).isHit(r2d,
					Gui.getActiveScenePanel().getZoomFactor(), null);

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
