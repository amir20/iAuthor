package edu.gwu.raminfar.iauthor.wikitool;

import edu.gwu.raminfar.iauthor.core.TextEditorEvent;
import edu.gwu.raminfar.iauthor.ui.AbstractTool;
import edu.gwu.raminfar.iauthor.ui.ApplicationFrame;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;

import javax.swing.*;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

/**
 * @author Amir Raminfar
 */
public class WikiTool extends AbstractTool {
    private final static File index = new File("./lucene/wiki/index");
    private final IndexWriter writer;
    private final JLabel loader = new JLabel(new ImageIcon(this.getClass().getResource("/images/loader.gif")));
    private final Timer timer = new Timer(true);
    private TimerTask task = null;

    public WikiTool() {
        try {
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
            Directory directory = new NIOFSDirectory(index);
            writer = new IndexWriter(directory, analyzer, true, IndexWriter.MaxFieldLength.UNLIMITED);
        } catch (IOException e) {
            ApplicationFrame.logger.log(Level.WARNING, "Error reading SOLR data for wiki", e);
            throw new RuntimeException(e);
        }
        setBackground(Color.white);
    }

    @Override
    public void onTextEvent(TextEditorEvent event) {
        if (task != null) {
            task.cancel();
        } else {
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

                remove(loader);
                revalidate();
                repaint();
                task = null;
            }
        }), 450);
    }


    @Override
    public void setTextPane(JTextPane pane) {
    }
}
