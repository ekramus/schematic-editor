package cz.cvut.fel.piccoloDemo;

import javax.swing.JFrame;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.nodes.PText;

/**
 * @author uk
 *
 */
public class HelloWorldExample extends JFrame {

    private HelloWorldExample() {
        final PCanvas canvas = new PCanvas();
        final PText text = new PText("Hello World");
        canvas.getLayer().addChild(text);
        add(canvas);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setVisible(true);
    }

    public static void main(String[] args) {
        new HelloWorldExample();
    }

}
