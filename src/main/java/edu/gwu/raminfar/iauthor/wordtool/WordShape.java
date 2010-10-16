package edu.gwu.raminfar.iauthor.wordtool;

import edu.gwu.raminfar.animation.ShapeWrapper;
import edu.gwu.raminfar.iauthor.core.Word;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

/**
 * @author Amir Raminfar
 */
public class WordShape {
    private ShapeWrapper wrapper;
    private Word word;

    public WordShape(Word word, FontMetrics metrics, int x, int y) {
        this.word = word;
        int width = metrics.stringWidth(word.getText());
        wrapper = new ShapeWrapper(new RoundRectangle2D.Double(x, y, width + 10, metrics.getHeight() + 5, 10, 10));
    }

    public ShapeWrapper getWrapper() {
        return wrapper;
    }

    public void paint(Graphics g) {
        Graphics2D gd = (Graphics2D) g.create();

        gd.setColor(Color.darkGray);
        gd.draw(wrapper.getShape());
        gd.setColor(Color.white);
        gd.fill(wrapper.getShape());
        int x = (int) wrapper.getShape().getBounds().getX();
        int y = (int) (wrapper.getShape().getBounds().getY() + wrapper.getShape().getBounds().getHeight());
        gd.setColor(Color.darkGray);
        gd.drawString(word.getText(), x + 5, y - 5);

        gd.dispose();
    }
}
