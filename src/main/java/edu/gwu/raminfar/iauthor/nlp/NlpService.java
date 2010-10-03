package edu.gwu.raminfar.iauthor.nlp;

import edu.gwu.raminfar.iauthor.core.Sentence;
import edu.gwu.raminfar.iauthor.core.Word;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Amir Raminfar
 * Date: Oct 3, 2010
 */
public final class NlpService {
    private static final Logger logger = Logger.getLogger(NlpService.class.getName());

    private NlpService() {
    }

    final private static POSModel posModel;static {
        try {
            posModel = new POSModel(NlpService.class.getResourceAsStream("/opennlp/models/en-pos-maxent.bin"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not open model for pos tagger", e);
            throw new RuntimeException(e);
        }
    }

    final private static POSTagger tagger = new POSTaggerME(posModel);

    final private static SentenceModel sentenceModel;static {
        try {
            sentenceModel = new SentenceModel(NlpService.class.getResourceAsStream("/opennlp/models/en-sent.bin"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not open model for sentence detector", e);
            throw new RuntimeException(e);
        }
    }

    private static final SentenceDetector sentenceDetector = new SentenceDetectorME(sentenceModel);

    public static Sentence tagSentence(String text) {
        String tagged = tagger.tag(text);
        String[] tags = tagged.split(" ");
        List<Word> words = new ArrayList<Word>();
        for (String tag : tags) {
            String[] split = tag.split("/");
            Word.Type type = getType(split[1]);
            Word word = new Word(split[0], type);
            words.add(word);
        }
        return new Sentence(words);
    }

    public static String[] detectedSentences(String paragraph) {
        return sentenceDetector.sentDetect(paragraph);
    }

    private static Word.Type getType(String s) {
        Word.Type type = Word.Type.UNKNOWN;

        if (s != null) {
            // todo add more types here
            if (s.equals("CC")) {
                type = Word.Type.CONJUNCTION;
            } else if (s.equals("IN")) {
                type = Word.Type.PREPOSITION;
            } else if (s.equals("CD")) {
                type = Word.Type.NUMBER;
            } else if (s.startsWith("VB")) {
                type = Word.Type.VERB;
            } else if (s.startsWith("MD")) {
                type = Word.Type.MODAL;
            } else if (s.startsWith("PRP")) {
                type = Word.Type.PRONOUN;
            } else if (s.startsWith("NN")) {
                type = Word.Type.NOUN;
            } else if (s.startsWith("JJ")) {
                type = Word.Type.ADJECTIVE;
            } else if (s.startsWith("RB") || s.equals("WRB")) {
                type = Word.Type.ADVERB;
            } else if (s.equals("TO")) {
                type = Word.Type.TO;
            } else if (s.equals("DT")) {
                type = Word.Type.DETERMINER;
            } else if (s.equals("RP")) {
                type = Word.Type.PARTICLE;
            }
        }

        return type;
    }
}
