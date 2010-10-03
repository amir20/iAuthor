package edu.gwu.raminfar.iauthor.ui;

import edu.gwu.raminfar.iauthor.core.TextEditorEvent;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * @author Amir Raminfar
 */
public class WordTool extends AbstractTool {
    private IndexSearcher searcher = null;
    private final Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
    private final QueryParser parser = new QueryParser(Version.LUCENE_30, "", analyzer);

    public WordTool() {
        try {
            searcher = new IndexSearcher(new NIOFSDirectory(new File(getClass().getResource("/lucene/wordnet/index").getFile())));
        } catch (IOException e) {
            ApplicationFrame.logger.log(Level.SEVERE, "Error while reading indexer data for wordnet", e);
        }
        setBackground(Color.white);
    }

    @Override
    public void onTextEvent(TextEditorEvent e) {
        System.out.println("sentence = " + e.getSentence());
        System.out.println("word = " + e.getCurrentWord());
    }
}
