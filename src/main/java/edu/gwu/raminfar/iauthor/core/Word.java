package edu.gwu.raminfar.iauthor.core;

/**
 * @author Amir Raminfar
 */
public class Word {
    private String word;

    public Word(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return word;
    }
}
