package edu.gwu.raminfar.iauthor;

import javax.swing.event.CaretEvent;

/**
 * @author Amir Raminfar
 */
public class TextEditorEvent {
    private Sentence sentence;
    private Word currentWord;
    private CaretEvent event;

    public TextEditorEvent(Sentence sentence, Word currentWord, CaretEvent event) {
        this.sentence = sentence;
        this.currentWord = currentWord;
        this.event = event;
    }

    public Sentence getSentence() {
        return sentence;
    }

    public Word getCurrentWord() {
        return currentWord;
    }

    public CaretEvent getEvent() {
        return event;
    }
}
