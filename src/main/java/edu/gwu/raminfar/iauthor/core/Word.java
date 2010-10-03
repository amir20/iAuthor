package edu.gwu.raminfar.iauthor.core;

/**
 * @author Amir Raminfar
 */
public class Word {
    public enum Type{NOUN, VERB, ADJECTIVE, ADJECTIVE_SATELLITE, ADVERB}
    private String word;
    private Type type;

    public Word(String word, Type type) {
        this.word = word;
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
        if (o == null || getClass() != o.getClass()) return false;
        Word word1 = (Word) o;
        return !(type != word1.type || (word != null ? !word.equals(word1.word) : word1.word != null));
    }

    @Override
    public int hashCode() {
        int result = word != null ? word.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
