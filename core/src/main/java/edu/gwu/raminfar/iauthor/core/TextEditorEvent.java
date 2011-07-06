package edu.gwu.raminfar.iauthor.core;

import com.google.common.base.Objects;

/**
 * @author Amir Raminfar
 */
public class TextEditorEvent {
    private Sentence sentence;
    private Word currentWord;

    public TextEditorEvent(Sentence sentence, Word currentWord) {
        this.sentence = sentence;
        this.currentWord = currentWord;
    }

    public Sentence getSentence() {
        return sentence;
    }

    public Word getCurrentWord() {
        return currentWord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof TextEditorEvent) {
            TextEditorEvent other = (TextEditorEvent) o;
            return Objects.equal(sentence, other.sentence) && Objects.equal(currentWord, other.currentWord);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sentence, currentWord);
    }
}
