import edu.gwu.raminfar.iauthor.core.Sentence;
import edu.gwu.raminfar.iauthor.core.Word;
import edu.gwu.raminfar.iauthor.nlp.NlpService;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import org.junit.Test;

import java.io.IOException;

/**
 * Author: Amir Raminfar
 * Date: Oct 3, 2010
 */
public class OpenNlpTest {

    @Test
    public void nlp() throws IOException {
        String[] sentences = NlpService.detectedSentences("I found a great set of tools for natural language processing. The Java package includes a sentence detector, a tokenizer, a parts-of-speech (POS) tagger, and a treebank parser. It took me a little while to figure out where to start so I thought I'd post my findings here. I'm no linguist and I don't have previous experience with NLP, but hopefully this will help some one get setup with OpenNLP.");
        for (String s : sentences) {
            Sentence sentence = new Sentence(s);
            for (Word word : sentence) {
                System.out.printf("%s / %s \n", word.getText(), word.getType());
            }
        }
    }
}
