package edu.gwu.raminfar.iauthor.core;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Amir Raminfar
 */
public abstract class AbstractTool extends JPanel {
    protected final static Dimension SIZE = new Dimension(300, 250);
    protected final static BufferedImage BACKGROUND;

    static {
        try {
            BACKGROUND = ImageIO.read(AbstractTool.class.getResource("/images/tool.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected AbstractTool() {
        setPreferredSize(SIZE);
        setMaximumSize(SIZE);
        setOpaque(false);
    }

    /**
     * Tool implementation that includes current sentence and word
     *
     * @param event current event
     */
    public abstract void onTextEvent(TextEditorEvent event);

    /**
     * Passes an instance of the textpane so that each tool can control
     *
     * @param pane shared textpane instance
     */
    public void setTextPane(JTextPane pane) {
    }

    /**
     * Gets called on application close.
     * Use this tool close resources
     */
    public void onClose() {
    }

    /**
     * Get the background image tool use for drawing. Subclass should cache this instead of reading it every time
     *
     * @return PNG
     */
    public BufferedImage getBackgroundImage() {
        return BACKGROUND;
    }
}
