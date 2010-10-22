import edu.gwu.raminfar.wikicrawler.WikiPage;
import edu.gwu.raminfar.wikicrawler.WikiSearch;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Author: Amir Raminfar
 * Date: Oct 21, 2010
 */
public class WikiSearchTest {

    @Test
    public void middleEast() throws IOException {
        WikiSearch search = new WikiSearch("middle east");
        for (WikiPage page : search.parseResults()) {
            System.out.println(page.getUrl() + ": " + page.content());
        }
    }
}
