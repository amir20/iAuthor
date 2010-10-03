import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.util.Span;
import org.junit.Test;

import java.io.IOException;

/**
 * Author: Amir Raminfar
 * Date: Oct 3, 2010
 */
public class OpenNlp {

    @Test
    public void nlp() throws IOException {
        SentenceModel sentenceModel = new SentenceModel(getClass().getResourceAsStream("/opennlp/models/en-sent.bin"));
        SentenceDetector sentenceDetector = new SentenceDetectorME(sentenceModel);

        String[] sentences = sentenceDetector.sentDetect("Use the links in the table below to download the pre-trained models for the OpenNLP 1.5 series. The models are language dependent and only perform well if the model language matches the language of the input text. Also make sure the input text is decoded correctly, depending on the input file encoding this can only be done by explicitly specifying the character encoding. See this Java Tutorial section for further details. ");

        for (String s : sentences) {
            System.out.println(s);
        }

        String first = sentences[0];

        POSModel posModel = new POSModel(getClass().getResourceAsStream("/opennlp/models/en-pos-maxent.bin"));
        POSTagger tagger = new POSTaggerME(posModel);

        System.out.println(tagger.tag(first));


    }
}
