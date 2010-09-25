package edu.gwu.raminfar;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Amir Raminfar
 * Date: Sep 25, 2010
 */
public class TextEditorPane extends JPanel {

    public TextEditorPane() {        
        setLayout(new BorderLayout());
        JScrollPane editorScrollPane = new JScrollPane(createEditorPane());
        //editorScrollPane.setPreferredSize(new Dimension(800, 600));
        add(editorScrollPane, BorderLayout.CENTER);
    }

    private JTextPane createEditorPane() {
        JTextPane textPane = new JTextPane();
        return textPane;
    }


}
