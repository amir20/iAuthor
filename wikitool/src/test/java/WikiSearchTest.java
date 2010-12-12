import edu.gwu.raminfar.iauthor.wikitool.WikiPage;
import edu.gwu.raminfar.iauthor.wikitool.WikiSearch;
import org.junit.Test;

import java.io.IOException;

/**
 * Author: Amir Raminfar
 * Date: Oct 21, 2010
 */
public class WikiSearchTest {

    @Test
    public void middleEast() throws IOException {
        WikiSearch search = new WikiSearch("middle east");
        for (WikiPage page : search.parseResults()) {
            System.out.println(page.getUrl() + ": " + page.fetchContent());
        }
    }
}
