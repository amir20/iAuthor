import edu.gwu.raminfar.animation.Animator;
import edu.gwu.raminfar.animation.Easing;
import edu.gwu.raminfar.animation.ShapeWrapper;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.util.*;

/**
 * Author: Amir Raminfar
 * Date: Oct 12, 2010
 */
public class AnimationTest extends JPanel {

    ShapeWrapper wrapper = new ShapeWrapper(new RoundRectangle2D.Double(10, 200, 50, 50, 8, 8));
    Animator animator = new Animator(this);

    public AnimationTest() {
        setBackground(Color.white);
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Starting animation...");                
                animator.new Animation(wrapper).setEasing(Easing.OutElastic).move(wrapper.getShape().getBounds().getLocation(), new Point(400, 200)).animate();
            }
        }, 1000);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D) g.create();
        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gd.setColor(Color.blue);
        gd.fill(wrapper.getShape());
        gd.dispose();
    }

    public static void main(String... args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new AnimationTest());
        frame.setSize(new Dimension(1024, 900));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
