package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

/**
 * @author uk
 *
 */
public class StringPanel extends JPanel {
    private static StringPanel instance = null;

    private StringPanel() {
        this.setBackground(Color.WHITE);
    }

    public static StringPanel getInstance() {
        if (instance == null) {
            instance = new StringPanel();
        }
        return instance;
    }

    private void drawString(Graphics2D g2d) {
        Font font = new Font("Monospaced", Font.PLAIN, 13);
        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics();

        g2d.setColor(Color.BLACK);
        String text = "pokusný text";
        g2d.drawString(text, 100, 100);
        Rectangle2D r2d = metrics.getStringBounds(text, g2d);
        g2d.setColor(Color.RED);
        r2d = new Rectangle2D.Double(100 + r2d.getX(), 100 + r2d.getY(), r2d.getWidth(), r2d.getHeight());
        g2d.draw(r2d);

        g2d.setColor(Color.BLACK);
        g2d.drawLine(100, 100, 50, 50);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.Container#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        drawString((Graphics2D) g);
    }
}
