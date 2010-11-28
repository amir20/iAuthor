package edu.gwu.raminfar.google;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Amir Raminfar
 */
public class GoogleSearch {
    private final static String SEARCH_URL = "http://www.google.com/search?q=%s";
    private final String query;

    public GoogleSearch(String query) {
        this.query = query.replaceAll(" ", "+").replaceAll("&", "%26");
    }

    public Set<GoogleDocument> parseResults() throws IOException {
        Set<GoogleDocument> results = new LinkedHashSet<GoogleDocument>(10);

        String url = String.format(SEARCH_URL, query);
        Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (X11; U; Linux x86_64; en-GB; rv:1.8.1.6) Gecko/20070723 Iceweasel/2.0.0.6 (Debian-2.0.0.6-0etch1)").get();
        Elements elements = doc.select("a.l");
        for (int i = 0, elementsSize = elements.size(); i < elementsSize; i++) {
            Element e = elements.get(i);
            GoogleDocument page = new GoogleDocument(e.absUrl("href"));
            if (!results.contains(page)) {
                results.add(page);
            }
        }

        return results;
    }

    public String getQuery() {
        return query;
    }
}
