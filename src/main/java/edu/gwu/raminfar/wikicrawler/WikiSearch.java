package edu.gwu.raminfar.wikicrawler;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: Amir Raminfar
 * Date: Oct 21, 2010
 */
public class WikiSearch {
    private final static String SEARCH_URL = "http://en.wikipedia.org/w/index.php?search=%s&fulltext=Search";
    private final String query;


    public WikiSearch(String query) {
        this.query = query.replaceAll(" ", "+").replaceAll("&", "%26");
    }

    public Collection<WikiPage> parseResults() throws IOException {
        Set<WikiPage> results = new HashSet<WikiPage>();

        String url = String.format(SEARCH_URL, query);
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select("ul.mw-search-results a");
        for (Element e : elements) {
            WikiPage page = new WikiPage(e.absUrl("href"));
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
