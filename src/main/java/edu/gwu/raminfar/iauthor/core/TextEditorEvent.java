package edu.gwu.raminfar.iauthor.core;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextEditorEvent that = (TextEditorEvent) o;

        return !((currentWord != null ? !currentWord.equals(that.currentWord) : that.currentWord != null) ||
                (sentence != null ? !sentence.equals(that.sentence) : that.sentence != null));

    }

    @Override
    public int hashCode() {
        int result = sentence != null ? sentence.hashCode() : 0;
        result = 31 * result + (currentWord != null ? currentWord.hashCode() : 0);
        return result;
    }
}
