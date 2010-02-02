package test;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * @author uk
 *
 */
public class Gui extends JFrame {
    private static Gui instance = null;

    private Gui() {
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(400, 300));
    }

    /**
     * @return the instance
     */
    public static Gui getInstance() {
        if (instance == null) {
            instance = new Gui();
            instance.add(StringPanel.getInstance());
        }
        return instance;
    }
}
