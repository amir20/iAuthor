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
}
