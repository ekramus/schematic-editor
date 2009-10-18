package cz.cvut.fel.piccoloDemo;

import java.awt.Color;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PDragEventHandler;
import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolox.PFrame;

/**
 * @author uk
 *
 */
public class InterfaceFrame extends PFrame {
    @Override
    public void initialize() {
        // Add Piccolo2D code here.

        // Remove the Default pan event handler and add a drag event handler
        // so that we can drag the nodes around individually.
        getCanvas().setPanEventHandler(null);
        getCanvas().addInputEventListener(new PDragEventHandler());

        // Create a node.
        PNode aNode = new PNode();

        // A node will not be visible until its bounds and brush are set.
        aNode.setBounds(0, 0, 100, 80);
        aNode.setPaint(Color.RED);

        // A node needs to be a descendent of the root to be displayed.
        PLayer layer = getCanvas().getLayer();
        layer.addChild(aNode);

        // A node can have child nodes added to it.
        PNode anotherNode = new PNode();
        anotherNode.setBounds(0, 0, 100, 80);
        anotherNode.setPaint(Color.YELLOW);
        aNode.addChild(anotherNode);

        // The base bounds of a node are easy to change. Changing the bounds
        // of a node will not affect its children.
        aNode.setBounds(-10, -10, 200, 110);

        // Each node has a transform that can be used to modify the position,
        // scale or rotation of a node. Changing a node's transform, will
        // transform all of its children as well.
        aNode.translate(100, 100);
        aNode.scale(1.5f);
        aNode.rotate(45);

        // Add a couple of PPath nodes and a PText node.
        layer.addChild(PPath.createEllipse(0, 0, 100, 100));
        layer.addChild(PPath.createRectangle(0, 100, 100, 100));
        layer.addChild(new PText("Hello World"));

        // Here we create a PImage node that displays a thumbnail image
        // of the root node. Then we add the new PImage to the main layer.
        PImage image = new PImage(layer.toImage(300, 300, null));
        layer.addChild(image);
    }

    public static void main(String[] args) {
        new InterfaceFrame();
    }
}
