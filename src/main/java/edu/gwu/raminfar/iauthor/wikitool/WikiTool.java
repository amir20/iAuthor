package edu.gwu.raminfar.iauthor.wikitool;

import edu.gwu.raminfar.iauthor.Utils;
import edu.gwu.raminfar.iauthor.core.Sentence;
import edu.gwu.raminfar.iauthor.core.TextEditorEvent;
import edu.gwu.raminfar.iauthor.core.Word;
import edu.gwu.raminfar.iauthor.nlp.NlpService;
import edu.gwu.raminfar.iauthor.ui.AbstractTool;
import edu.gwu.raminfar.iauthor.ui.ApplicationFrame;
import edu.gwu.raminfar.wiki.WikiPage;
import edu.gwu.raminfar.wiki.WikiSearch;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;

import javax.swing.*;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

/**
 * @author Amir Raminfar
 */
public class WikiTool extends AbstractTool {
    // lucene
    private final static File index = new File("./lucene/wiki/index");
    Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
    QueryParser parser = new QueryParser(Version.LUCENE_30, null, analyzer);
    private final IndexWriter writer;
    private final IndexSearcher searcher;

    // loader gif
    private final JLabel loader = new JLabel(new ImageIcon(this.getClass().getResource("/images/loader.gif")));

    // delay thread
    private final Timer timer = new Timer(true);
    private TimerTask task = null;

    public WikiTool() {
        try {

            Directory directory = new NIOFSDirectory(index);
            writer = new IndexWriter(directory, analyzer, true, IndexWriter.MaxFieldLength.UNLIMITED);
            searcher = new IndexSearcher(directory, true);
        } catch (IOException e) {
            ApplicationFrame.logger.log(Level.SEVERE, "Error reading SOLR data for wiki", e);
            throw new RuntimeException(e);
        }
        setBackground(Color.white);
    }

    @Override
    public void onTextEvent(final TextEditorEvent event) {
        if (task != null) {
            task.cancel();
        }
        final List<Word> nouns = new ArrayList<Word>();
        for (Word w : event.getSentence().getWords()) {
            if (w.getType() == Word.Type.NOUN) {
                nouns.add(w);
            }
        }

        // only query wiki if there is more than 3 nouns
        if (nouns.size() > 2) {
            if (task != null) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        add(loader);
                        revalidate();
                        repaint();
                    }
                });
            }
            timer.schedule((task = new TimerTask() {
                @Override
                public void run() {
                    try {
                        String keywords = Utils.join(nouns, " ");
                        ApplicationFrame.logger.log(Level.INFO, "Querying wiki with {0}", keywords);
                        Collection<WikiPage> pages = new WikiSearch(keywords).parseResults(3 /* max docs */);
                        for (WikiPage page : pages) {
                            try {
                                Query query = parser.parse("url:" + QueryParser.escape(page.getUrl()));
                                // make sure it hasn't already been parsed
                                if (searcher.search(query, 1).totalHits == 0) {
                                    ApplicationFrame.logger.log(Level.INFO, "Indexing {0}", page.getUrl());
                                    for (String s : NlpService.detectedSentences(page.fetchContent())) {
                                        Sentence sentence = new Sentence(s);
                                        Document doc = new Document();
                                        doc.add(new Field("originalSentence", sentence.getRawText(), Field.Store.YES, Field.Index.ANALYZED));
                                        doc.add(new Field("url", page.getUrl(), Field.Store.YES, Field.Index.ANALYZED));
                                        for (Word w : sentence.getWords()) {
                                            if (w.getType() == Word.Type.NOUN) {
                                                doc.add(new Field("noun", w.getText(), Field.Store.NO, Field.Index.ANALYZED));
                                            }
                                        }
                                        writer.addDocument(doc);
                                    }
                                    writer.commit();
                                    writer.optimize(true);
                                } else {
                                    ApplicationFrame.logger.log(Level.INFO, "Already cached {0}", page.getUrl());
                                }
                            } catch (ParseException e) {
                                ApplicationFrame.logger.log(Level.WARNING, "Parsing exception", e);
                            }
                        }
                    } catch (IOException e) {
                        ApplicationFrame.logger.log(Level.WARNING, "Error parsing sentences", e);
                    } finally {
                        remove(loader);
                        revalidate();
                        repaint();
                        task = null;
                    }
                }
            }), 250);
        }

    }


    @Override
    public void setTextPane(JTextPane pane) {
    }
}
