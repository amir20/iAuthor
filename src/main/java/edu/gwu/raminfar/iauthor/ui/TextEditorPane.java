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
    private JTextPane textPane = new JTextPane();
    private TextEditorEvent lastEvent;
    private Collection<AbstractTool> tools;

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
        int startOfWord = Math.max(text.lastIndexOf(" ", Math.max(e.getDot() - 1, 0)), 0);
        int endOfWord = text.indexOf(" ", e.getDot());
        if (endOfWord == -1) {
            endOfWord = len;
        }
        Word word = new Word(text.substring(startOfWord, endOfWord).trim().replaceFirst("^.+?\\s", ""));

        int startOfSentence = Math.max(text.lastIndexOf(".", e.getDot()), 0);
        int endOfSentence = text.indexOf(".", e.getDot());
        if (endOfSentence == -1) {
            endOfSentence = len;
        } else if (endOfSentence + 1 < len) {
            ++endOfSentence;
        }
        String[] words = text.substring(startOfSentence, endOfSentence).replaceFirst("^\\.", "").trim().split(" +");

        List<Word> list = new ArrayList<Word>();
        for (String w : words) {
            list.add(new Word(w));
        }

        Sentence sentence = new Sentence(list);
        TextEditorEvent event = new TextEditorEvent(sentence, word, e);
        if (!event.equals(lastEvent)) {
            for (AbstractTool tool : tools) {
                tool.onTextEvent(event);
            }
            lastEvent = event;
        }
    }
}
