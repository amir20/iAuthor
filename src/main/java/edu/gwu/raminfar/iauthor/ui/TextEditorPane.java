package edu.gwu.raminfar.iauthor.ui;

import edu.gwu.raminfar.iauthor.core.TextEditorEvent;
import edu.gwu.raminfar.iauthor.nlp.NlpService;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.BorderLayout;
import java.util.Collection;


/**
 * Author: Amir Raminfar
 * Date: Sep 25, 2010
 */
public class TextEditorPane extends JComponent implements CaretListener {
    private final JTextPane textPane = new JTextPane();
    private final Collection<AbstractTool> tools;
    private TextEditorEvent lastEvent;
    private static boolean ready = false;

    static {
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                System.out.println("Initializing NLP Services...");
                NlpService.detectedSentences("");
                return null;
            }

            @Override
            protected void done() {
                ready = true;
            }
        }.execute();
    }

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
        if (!ready) {
            return;
        }

        // find paragraph
        String text = textPane.getText();
        int len = text.length();
        if (len > 0) {
            int startOfP = Math.max(text.lastIndexOf("\n", e.getDot()), 0);
            int endOfP = text.indexOf("\n", e.getDot());
            if (endOfP == -1) {
                endOfP = len;
            }else {
                ++endOfP;
            }
            String paragraph = text.substring(startOfP, endOfP);

            // compute new index
            int fromStartOfParagraph = e.getDot() - startOfP;
            if (paragraph.startsWith("\n")) {
                paragraph = paragraph.substring(0);
                ++fromStartOfParagraph;
            }
            String[] sentences = NlpService.detectedSentences(paragraph);

            // find current sentence
            String sentence = null;
            if (sentences.length > 0) {
                int l = 0;
                // todo optimize?
                for (String s : sentences) {
                    sentence = s;
                    l += sentence.length();
                    if (fromStartOfParagraph <= l) {
                        break;
                    }
                }
            }

            int fromStartOfSentence = fromStartOfParagraph - paragraph.indexOf(sentence);
            System.out.println("fromStartOfSentence = " + fromStartOfSentence);
        }
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
