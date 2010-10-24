package edu.gwu.raminfar.wiki;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Author: Amir Raminfar
 * Date: Oct 21, 2010
 */
public class WikiPage {
    private static final Logger logger = Logger.getLogger(WikiPage.class.getName());
    private static final Pattern REMOVE_FILTER = Pattern.compile("\\[\\d+?\\]");
    private static final Pattern ANCHOR = Pattern.compile("#.*");

    private final String url;
    private Document doc;

    public WikiPage(String url) {
        this.url = ANCHOR.matcher(url).replaceAll("");
    }

    private void _connect() throws IOException {
        if (doc == null) {
            doc = Jsoup.connect(url).get();
        }
    }

    public String fetchContent() throws IOException {
        _connect();
        StringBuilder sb = new StringBuilder();
        Elements elements = doc.select("div#bodyContent p, div#bodyContent blockquote");
        for (Element element : elements) {
            sb.append(element.text()).append(" ");
        }
        return REMOVE_FILTER.matcher(sb.toString().trim()).replaceAll("");
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
        return "WikiPage{" +
                "url='" + url + '\'' +
                ", title=" + title +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WikiPage wikiPage = (WikiPage) o;
        return url.equals(wikiPage.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }
}
