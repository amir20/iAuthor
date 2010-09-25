package edu.gwu.raminfar;

import edu.gwu.raminfar.iauthor.Sentence;
import edu.gwu.raminfar.iauthor.TextEditorEvent;
import edu.gwu.raminfar.iauthor.Word;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;


/**
 * Author: Amir Raminfar
 * Date: Sep 25, 2010
 */
public class TextEditorPane extends JPanel implements CaretListener {
    private JTextPane textPane = new JTextPane();

    public TextEditorPane() {
        setLayout(new BorderLayout());
        createTextPane();
    }

    public void createTextPane() {
        JScrollPane editorScrollPane = new JScrollPane(textPane);
        add(editorScrollPane, BorderLayout.CENTER);
        textPane.addCaretListener(this);
    }


    public void caretUpdate(CaretEvent e) {
        String text = textPane.getText();

        int startOfWord = Math.max(text.lastIndexOf(" ", e.getDot()), 0);
        int endOfWord = Math.max(text.indexOf(" ", e.getDot()), text.length());
        Word word = new Word(text.substring(startOfWord, endOfWord));

        int startOfSentence = Math.max(text.lastIndexOf(".", e.getDot()), 0);
        int endOfSentence = Math.max(text.indexOf(".", e.getDot()), text.length());
        String[] words = text.substring(startOfSentence, endOfSentence).split(" +");

        List<Word> list = new ArrayList<Word>();
        for (String w : words) {
            list.add(new Word(w));
        }

        Sentence sentence = new Sentence(list);

        TextEditorEvent event = new TextEditorEvent(sentence, word, e);

        System.out.println(sentence);
        System.out.println(word);
    }
}
