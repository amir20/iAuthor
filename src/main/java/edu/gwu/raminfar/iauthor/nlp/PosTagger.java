package edu.gwu.raminfar.iauthor.nlp;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.postag.POSTaggerME;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Amir Raminfar
 * Date: Oct 3, 2010
 */
public class PosTagger {
    private static final Logger logger = Logger.getLogger(PosTagger.class.getName());


    final private static POSModel posModel;static {
        try {
            posModel = new POSModel(PosTagger.class.getResourceAsStream("/opennlp/models/en-pos-maxent.bin"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not open model for pos tagger", e);
            throw new RuntimeException(e);
        }
    }

    final private static POSTagger tagger = new POSTaggerME(posModel);

}
