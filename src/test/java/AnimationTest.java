import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

/**
 * Author: Amir Raminfar
 * Date: Oct 12, 2010
 */
public class AnimationTest extends JPanel {


    Timer timer = new Timer(20, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            rectangle.setRoundRect(rectangle.getX() + 5, rectangle.getY() + 5, rectangle.getWidth(), rectangle.getHeight(), rectangle.getArcWidth(), rectangle.getArcHeight());
            repaint();
        }
    });

    RoundRectangle2D.Double rectangle = new RoundRectangle2D.Double(10, 10, 200, 180, 3, 3);
    public AnimationTest() {
        timer.setInitialDelay(1000);
        timer.start();
        setBackground(Color.white);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D) g;
        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gd.setColor(Color.blue);
        gd.fill(rectangle);
    }

    public static void main(String... args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new AnimationTest());
        frame.setSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
