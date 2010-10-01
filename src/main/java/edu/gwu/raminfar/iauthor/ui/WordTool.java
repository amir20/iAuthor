package edu.gwu.raminfar.iauthor.ui;

import edu.gwu.raminfar.iauthor.core.TextEditorEvent;

import java.awt.Color;

/**
 * @author Amir Raminfar
 */
public class WordTool extends AbstractTool {

    public WordTool() {
        setBackground(Color.red);
    }

    @Override
    public void onTextEvent(TextEditorEvent event) {
    }
}
