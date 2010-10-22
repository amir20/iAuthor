import edu.gwu.raminfar.iauthor.core.Word;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Author: Amir Raminfar
 * Date: Oct 2, 2010
 */
public class WordNetIngestorTest {

    @Test
    public void ingestToLucene() throws IOException {
        Map<Word, List<Long>> wordToId = new HashMap<Word, List<Long>>();
        Map<Long, List<Word>> idToWord = new HashMap<Long, List<Word>>();
        BufferedReader reader = new BufferedReader(new FileReader(getClass().getResource("/wordnet/wn_s.pl").getFile()));
        try {
            Scanner scanner = new Scanner(reader);
            scanner.useDelimiter("(s\\(|\\)|,'|',|,)+");

            while (scanner.hasNextLine()) {
                long id = scanner.nextLong();
                scanner.nextInt(); // skip
                String text = scanner.next();
                String t = scanner.next();
                Word.Type type = parseType(t);
                Word word = new Word(text, type);

                // add to word map
                if (!wordToId.containsKey(word)) {
                    wordToId.put(word, new LinkedList<Long>());
                }
                wordToId.get(word).add(id);

                // add to id map
                if (!idToWord.containsKey(id)) {
                    idToWord.put(id, new LinkedList<Word>());
                }
                idToWord.get(id).add(word);

                scanner.nextLine();
            }
        } finally {
            reader.close();
        }


        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
        File index = new File("./index");
        Directory directory = new NIOFSDirectory(index);
        IndexWriter writer = new IndexWriter(directory, analyzer, true, IndexWriter.MaxFieldLength.UNLIMITED);

        for (Map.Entry<Word, List<Long>> entry : wordToId.entrySet()) {
            Word word = entry.getKey();
            List<Long> ids = entry.getValue();
            Set<Word> synonyms = new TreeSet<Word>();
            for (Long id : ids) {
                synonyms.addAll(idToWord.get(id));
            }
            synonyms.remove(word); // remove self
            Document document = new Document();
            document.add(new Field("word", word.toString(), Field.Store.YES, Field.Index.ANALYZED));
            document.add(new Field("type", word.getType().toString(), Field.Store.YES, Field.Index.ANALYZED));
            for (Word syn : synonyms) {
                document.add(new Field("synonym", syn.toString(), Field.Store.YES, Field.Index.NO));
            }
            writer.addDocument(document);
        }

        System.out.println("Committing...");
        writer.commit();
        System.out.println("Optimizing...");
        writer.optimize();
        writer.close();
        System.out.println("Saved index to " + index);
    }

    @Test
    public void testLucene() throws IOException, ParseException {
        IndexSearcher searcher = new IndexSearcher(new NIOFSDirectory(new File(getClass().getResource("/lucene/wordnet/index").getFile())));
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
        QueryParser parser = new QueryParser(Version.LUCENE_30, "", analyzer);
        Query query = parser.parse("word:'wager' AND type:NOUN");

        TopDocs docs = searcher.search(query, 10);
        for (ScoreDoc doc : docs.scoreDocs) {
            Document document = searcher.doc(doc.doc);
            String[] syns = document.getValues("synonym");
            for (String s : syns) {
                System.out.println(s);
            }
        }
    }

    private static Word.Type parseType(String t) {
        char c = t.charAt(0);
        Word.Type type;
        switch (c) {
            case 'v':
                type = Word.Type.VERB;
                break;
            case 'n':
                type = Word.Type.NOUN;
                break;
            case 'a':
                type = Word.Type.ADJECTIVE;
                break;
            case 's':
                type = Word.Type.ADJECTIVE_SATELLITE;
                break;
            case 'r':
                type = Word.Type.ADVERB;
                break;
            default:
                throw new IllegalArgumentException("Cannot recognize type: " + t);
        }

        return type;
    }
}
