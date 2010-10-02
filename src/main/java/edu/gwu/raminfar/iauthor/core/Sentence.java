package edu.gwu.raminfar.iauthor.core;

import edu.gwu.raminfar.iauthor.Utils;

import java.util.Collections;
import java.util.List;

/**
 * @author Amir Raminfar
 */
public class Sentence {
    private List<Word> words;

    public Sentence(List<Word> words) {
        this.words = Collections.unmodifiableList(words);
    }

    public List<Word> getWords() {
        return words;
    }

    @Override
    public String toString() {
        return Utils.join(words, " ");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sentence sentence = (Sentence) o;
        return !(words != null ? !words.equals(sentence.words) : sentence.words != null);
    }

    @Override
    public int hashCode() {
        return words != null ? words.hashCode() : 0;
    }
}
