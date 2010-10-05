package edu.gwu.raminfar.iauthor.ui;

import edu.gwu.raminfar.iauthor.core.TextEditorEvent;

import javax.swing.*;
import java.awt.Dimension;

/**
 * @author Amir Raminfar
 */
public abstract class AbstractTool extends JPanel {
    protected final static Dimension SIZE = new Dimension(300, 250);

    protected AbstractTool() {
        setPreferredSize(SIZE);
        setMaximumSize(SIZE);
    }

    abstract void onTextEvent(TextEditorEvent event);

    abstract void setTextPane(JTextPane pane);
}
