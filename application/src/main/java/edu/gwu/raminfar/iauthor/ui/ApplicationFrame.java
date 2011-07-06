package edu.gwu.raminfar.iauthor.ui;

import com.apple.eawt.AppEvent;
import com.apple.eawt.Application;
import com.apple.eawt.QuitHandler;
import com.apple.eawt.QuitResponse;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.gwu.raminfar.iauthor.Main;
import edu.gwu.raminfar.iauthor.core.AbstractTool;
import edu.gwu.raminfar.iauthor.core.EnabledModules;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Amir Raminfar
 */
@Singleton
public class ApplicationFrame extends JFrame {
    public static final Logger logger = Logger.getLogger(ApplicationFrame.class.getName());
    private final BufferedImage background;
    private List<AbstractTool> tools;
    private TextEditorPane editor;

    @Inject
    public ApplicationFrame(
            @EnabledModules List<AbstractTool> tools,
            TextEditorPane editor) throws HeadlessException {

        super(Main.APP_NAME);
        this.tools = tools;
        this.editor = editor;
        try {
            background = ImageIO.read(getClass().getResource("/images/background.png"));
        } catch (IOException e) {
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
        setupRightRail();
        setSize(new Dimension(1150, 700));
        setLocationRelativeTo(null);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void setupRightRail() {
        final JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        add(panel, BorderLayout.EAST);
        for (AbstractTool tool : tools) {
            panel.add(new ToolWrapper(tool));
            tool.setTextPane(editor.getTextPane());
        }
        editor.setTools(tools);
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
