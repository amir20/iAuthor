package edu.gwu.raminfar.iauthor.ui;

import com.apple.eawt.AppEvent;
import com.apple.eawt.Application;
import com.apple.eawt.QuitHandler;
import com.apple.eawt.QuitResponse;
import edu.gwu.raminfar.iauthor.Main;
import edu.gwu.raminfar.iauthor.core.AbstractTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Amir Raminfar
 */
public class ApplicationFrame extends JFrame {
    public static final Logger logger = Logger.getLogger(ApplicationFrame.class.getName());
    private final TextEditorPane editor = new TextEditorPane();
    private final List<AbstractTool> tools = new ArrayList<AbstractTool>();
    private final BufferedImage background;

    public ApplicationFrame() throws HeadlessException {
        super(Main.APP_NAME);
        try {
            background = ImageIO.read(getClass().getResource("/images/background.png"));
        } catch (IOException e) {
            ApplicationFrame.logger.log(Level.WARNING, "", e);
            throw new RuntimeException(e);
        }
        setContentPane(new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                double sx = (double) getWidth() / background.getWidth();
                double sy = (double) (getHeight() + 1) / background.getHeight();
                Graphics2D g2d = (Graphics2D) g.create();
                AffineTransform transform = new AffineTransform();
                transform.scale(sx, sy);
                g2d.setTransform(transform);
                g2d.drawImage(background, 0, -1, null);
                g2d.dispose();
                super.paintComponent(g);
            }
        });

        addListeners();
        setLayout(new BorderLayout());
        add(editor, BorderLayout.CENTER);
        addRightRail();
        setSize(new Dimension(1150, 700));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void addRightRail() {
        final JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        add(panel, BorderLayout.EAST);

        List<Class<? extends AbstractTool>> classes = getTools();
        for (Class<? extends AbstractTool> c : classes) {
            try {
                AbstractTool tool = c.newInstance();
                panel.add(new ToolWrapper(tool));
                tools.add(tool);
                tool.setTextPane(editor.getTextPane());
            } catch (InstantiationException e) {
                logger.log(Level.SEVERE, "Initialization Exception", e);
            } catch (IllegalAccessException e) {
                logger.log(Level.SEVERE, "Illegal Access Exception", e);
            }
        }
        editor.setTools(tools);
    }

    private static List<Class<? extends AbstractTool>> getTools() {
        try {
            Properties config = new Properties();
            config.load(ApplicationFrame.class.getResourceAsStream("/config.properties"));
            List<Class<? extends AbstractTool>> list = new ArrayList<Class<? extends AbstractTool>>();
            String[] classes = config.getProperty("iauthor.tools").split(" *, *");
            for (String c : classes) {
                try {
                    Class<?> clazz = Class.forName(c);
                    if (AbstractTool.class.isAssignableFrom(clazz)) {
                        Class<? extends AbstractTool> tool = clazz.asSubclass(AbstractTool.class);
                        list.add(tool);
                    } else {
                        // print error or throw run time exception
                        logger.log(Level.WARNING, "Class {0} does not extend AbstractTool", clazz.getName());
                    }

                } catch (ClassNotFoundException e) {
                    logger.log(Level.SEVERE, "Class not found", e);
                    // skipping to next class
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void addListeners() {
        if (Main.IS_MAC) {
            Application.getApplication().setQuitHandler(new QuitHandler() {
                @Override
                public void handleQuitRequestWith(AppEvent.QuitEvent quitEvent, QuitResponse quitResponse) {
                    closeTools();
                    quitResponse.performQuit();
                }
            });
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeTools();
            }
        });

    }

    private void closeTools() {
        logger.info("Closing all tools...");
        for (AbstractTool tool : tools) {
            tool.onClose();
        }
    }
}
