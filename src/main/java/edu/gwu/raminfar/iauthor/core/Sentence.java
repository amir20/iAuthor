package edu.gwu.raminfar.iauthor.core;

import edu.gwu.raminfar.iauthor.Utils;
import edu.gwu.raminfar.iauthor.nlp.NlpService;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Amir Raminfar
 */
public class Sentence implements Iterable<Word> {
    private List<Word> words;
    private final String rawText;

    public Sentence(String text) {
        this.rawText = text;
    }

    public List<Word> getWords() {
        return words != null ? words : (words = Collections.unmodifiableList(NlpService.tagSentence(rawText)));
    }

    public String getRawText() {
        return rawText;
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
        return !(rawText != null ? !rawText.equals(sentence.rawText) : sentence.rawText != null);
    }

    @Override
    public int hashCode() {
        return rawText != null ? rawText.hashCode() : 0;
    }

    @Override
    public Iterator<Word> iterator() {
        return getWords().iterator();
    }
}
