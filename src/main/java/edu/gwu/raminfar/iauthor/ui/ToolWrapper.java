package edu.gwu.raminfar.iauthor.ui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Amir Raminfar
 */
public class ToolWrapper extends JPanel {
    private final AbstractTool tool;

    public ToolWrapper(AbstractTool tool) {
        this.tool = tool;
        add(tool);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setOpaque(false);
        Dimension size = new Dimension(tool.getPreferredSize());
        size.width += 30;
        size.height += 30;
        setPreferredSize(size);
        setMaximumSize(size);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(tool.getBackgroundImage(), 0, 0, null);
        super.paintComponent(g);
    }
}
