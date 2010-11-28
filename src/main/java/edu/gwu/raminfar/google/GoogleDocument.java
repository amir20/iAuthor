package edu.gwu.raminfar.google;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Amir Raminfar
 */
public class GoogleDocument {
    private static final Logger logger = Logger.getLogger(GoogleDocument.class.getName());

    private final String url;
    private Document doc;

    public GoogleDocument(String url) {
        this.url = url;
    }

    private void _connect() throws IOException {
        if (doc == null) {
            doc = Jsoup.connect(url).get();
        }
    }

    public String fetchContent() throws IOException {
        _connect();
        return doc.text();
    }

    public String findTitle() throws IOException {
        _connect();
        return doc.title();
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        String title = "";
        try {
            title = findTitle();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error parsing title", e);

        }
        return "GoogleDocument{" +
                "url='" + url + '\'' +
                ", title=" + title +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoogleDocument wikiPage = (GoogleDocument) o;
        return url.equals(wikiPage.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }
}
