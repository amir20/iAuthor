package edu.gwu.raminfar.iauthor.core;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;

import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.ImmutableList.copyOf;
import static com.google.common.collect.Iterables.filter;

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
        return words != null ? words : (words = copyOf(NlpService.tagSentence(rawText)));
    }

    public String getRawText() {
        return rawText;
    }

    public Iterable<Word> find(final Word.Type type) {
        return filter(getWords(), new Predicate<Word>() {
            @Override
            public boolean apply(Word word) {
                return word.getType() == type;
            }
        });
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("Sentence", rawText)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Sentence) {
            Sentence other = (Sentence) o;
            return Objects.equal(rawText, other.rawText);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(rawText);
    }

    @Override
    public Iterator<Word> iterator() {
        return getWords().iterator();
    }
}
