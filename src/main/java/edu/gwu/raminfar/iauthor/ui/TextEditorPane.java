package edu.gwu.raminfar.iauthor.ui;

import edu.gwu.raminfar.iauthor.core.Sentence;
import edu.gwu.raminfar.iauthor.core.TextEditorEvent;
import edu.gwu.raminfar.iauthor.core.Word;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Author: Amir Raminfar
 * Date: Sep 25, 2010
 */
public class TextEditorPane extends JPanel implements CaretListener {
    private final JTextPane textPane = new JTextPane();
    private final Collection<AbstractTool> tools;
    private TextEditorEvent lastEvent;

    public TextEditorPane(Collection<AbstractTool> tools) {
        setLayout(new BorderLayout());
        addTextPane();
        this.tools = tools;
    }

    public void addTextPane() {
        JScrollPane editorScrollPane = new JScrollPane(textPane);
        add(editorScrollPane, BorderLayout.CENTER);
        textPane.addCaretListener(this);
    }

    public void caretUpdate(CaretEvent e) {
        String text = textPane.getText();
        int len = text.length();
        int startOfP = Math.max(text.lastIndexOf("\n", e.getDot()), 0);
        int endOfP = text.indexOf("\n", e.getDot());
        if (endOfP == -1) {
            endOfP = len;
        }
        String paragraph = text.substring(startOfP, endOfP);

        int fromStartOfParagraph = e.getDot() - startOfP;
        

        System.out.println(paragraph);

//        Sentence sentence = new Sentence(list);
//        TextEditorEvent event = new TextEditorEvent(sentence, word, e);
//        if (!event.equals(lastEvent)) {
//            for (AbstractTool tool : tools) {
//                tool.onTextEvent(event);
//            }
//            lastEvent = event;
//        }
    }
}
