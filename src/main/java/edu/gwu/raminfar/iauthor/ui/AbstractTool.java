package edu.gwu.raminfar.iauthor.ui;

import edu.gwu.raminfar.iauthor.core.TextEditorEvent;

import javax.swing.JPanel;
import java.awt.Dimension;

/**
 * @author Amir Raminfar
 */
public abstract class AbstractTool extends JPanel {
    protected final static Dimension SIZE = new Dimension(250, 250);

    protected AbstractTool() {
        setPreferredSize(SIZE);
        setMaximumSize(SIZE);
    }

    public abstract void onTextEvent(TextEditorEvent event);
}
