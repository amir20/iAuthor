package edu.gwu.raminfar.iauthor.core;

import com.google.common.base.Objects;

import java.util.regex.Pattern;

/**
 * @author Amir Raminfar
 */
public class Word implements Comparable<Word> {
    private static Pattern NON_ALPHA = Pattern.compile("[^a-z0-9\\- ]");

    public enum Type {
        NOUN, PRONOUN, VERB, MODAL, ADJECTIVE, ADJECTIVE_SATELLITE, PARTICLE,
        ADVERB, CONJUNCTION, NUMBER, PREPOSITION, DETERMINER, TO, UNKNOWN
    }

    private String word;
    private Type type;

    public Word(String word, Type type) {
        this.word = NON_ALPHA.matcher(word.toLowerCase()).replaceAll("");
        this.type = type;
    }

    @Override
    public String toString() {
        return word;
    }

    public Type getType() {
        return type;
    }

    public String getText() {
        return word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Word) {
            Word other = (Word) o;
            return Objects.equal(word, other.word) && Objects.equal(type, other.type);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(word, type);
    }

    @Override
    public int compareTo(Word o) {
        return word.compareTo(o.word);
    }
}
