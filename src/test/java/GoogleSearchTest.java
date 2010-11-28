import edu.gwu.raminfar.google.GoogleDocument;
import edu.gwu.raminfar.google.GoogleSearch;
import org.junit.Test;

import java.io.IOException;

/**
 * Author: Amir Raminfar
 * Date: Oct 21, 2010
 */
public class GoogleSearchTest {

    @Test
    public void test() throws IOException {
        GoogleSearch search = new GoogleSearch("test");
        for (GoogleDocument page : search.parseResults()) {
            System.out.println(page.getUrl() + ": " + page.fetchContent());
        }
    }

}
