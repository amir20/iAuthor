package edu.gwu.raminfar.iauthor.ui;

import edu.gwu.raminfar.iauthor.core.Sentence;
import edu.gwu.raminfar.iauthor.core.TextEditorEvent;
import edu.gwu.raminfar.iauthor.core.Word;
import edu.gwu.raminfar.iauthor.nlp.NlpService;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;


/**
 * Author: Amir Raminfar
 * Date: Sep 25, 2010
 */
public class TextEditorPane extends JComponent implements CaretListener {
    private static final BufferedImage background;
    private static boolean ready = false;

    private final JTextPane textPane = new JTextPane();
    private Collection<AbstractTool> tools;
    private TextEditorEvent lastEvent;

    static {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                ApplicationFrame.logger.info("Initializing NLP Services...");
                NlpService.detectedSentences("");
                ApplicationFrame.logger.info("Completed setting up NLP Services...");
                return null;
            }

            @Override
            protected void done() {
                ready = true;
            }
        }.execute();
        try {
            background = ImageIO.read(TextEditorPane.class.getResource("/images/papers.png"));
        } catch (IOException e) {
            ApplicationFrame.logger.log(Level.WARNING, "", e);
            throw new RuntimeException(e);
        }
    }

    public TextEditorPane() {
        setLayout(new BorderLayout());
        JScrollPane editorScrollPane = new JScrollPane(textPane);
        editorScrollPane.setBorder(BorderFactory.createEmptyBorder(60, 50, 0, 0));
        setOpaque(false);
        editorScrollPane.setOpaque(false);
        editorScrollPane.getViewport().setOpaque(false);
        textPane.setOpaque(false);
        textPane.addCaretListener(this);
        editorScrollPane.setMinimumSize(new Dimension(710, 600));
        editorScrollPane.setPreferredSize(new Dimension(710, 600));
        editorScrollPane.setMaximumSize(new Dimension(710, 6000));
        Box hBox = Box.createHorizontalBox();
        hBox.add(editorScrollPane);
        add(hBox, BorderLayout.CENTER);
    }

    public void setTools(Collection<AbstractTool> tools) {
        this.tools = new ArrayList<AbstractTool>(tools);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(background, 0,0, null);
        super.paintComponent(g);
    }

    public JTextPane getTextPane() {
        return textPane;
    }

    public void caretUpdate(CaretEvent e) {
        if (!ready) {
            return;
        }

        String text = textPane.getText();
        int len = text.length();
        if (len > 0) {
            // find current paragraph
            int startOfP = Math.max(text.lastIndexOf("\n", e.getDot()), 0);
            int endOfP = text.indexOf("\n", e.getDot());
            if (endOfP == -1) {
                endOfP = len;
            } else {
                ++endOfP;
            }
            String paragraph = text.substring(startOfP, endOfP);

            if (paragraph.length() > 0) {
                // find the start of the current paragraph
                int fromStartOfParagraph = e.getDot() - startOfP;
                while (paragraph.startsWith("\n")) {
                    paragraph = paragraph.substring(1);
                    --fromStartOfParagraph;
                }
                String[] sentences = NlpService.detectedSentences(paragraph);

                TextEditorEvent event = null;
                if (sentences.length > 0) {
                    // find the current sentence
                    String detectedSentence = findCurrentSentence(fromStartOfParagraph, sentences);
                    int fromStartOfSentence = fromStartOfParagraph - paragraph.indexOf(detectedSentence);
                    Sentence sentence = new Sentence(detectedSentence);

                    // find the current word
                    Word currentWord = findCurrentWord(fromStartOfSentence, sentence);
                    event = new TextEditorEvent(sentence, currentWord);
                }


                // invoke a new event for each tool
                // only invoke if word and sentence has changed
                if (event != null && !event.equals(lastEvent)) {
                    final TextEditorEvent finalEvent = event;
                    for (final AbstractTool tool : tools) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                tool.onTextEvent(finalEvent);
                            }
                        });
                    }
                    lastEvent = finalEvent;
                }
            }
        }
    }

    private String findCurrentSentence(int fromStartOfParagraph, String[] sentences) {
        int l = 0;
        String detectedSentence = null;
        // todo optimize?
        for (String s : sentences) {
            detectedSentence = s;
            l += detectedSentence.length();
            if (fromStartOfParagraph <= l) {
                break;
            }
        }
        return detectedSentence;
    }

    private Word findCurrentWord(int fromStartOfSentence, Sentence sentence) {
        int l = 0;
        Word currentWord = null;
        for (Word word : sentence) {
            currentWord = word;
            l += word.getText().length() + 1 /* space */;
            if (fromStartOfSentence < l) {
                break;
            }
        }
        return currentWord;
    }


}
