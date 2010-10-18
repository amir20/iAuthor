import edu.gwu.raminfar.iauthor.nlp.NlpService;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import org.junit.Test;

import java.util.regex.Pattern;


/**
 * @author Amir Raminfar
 */
public class MyCrawler extends WebCrawler {

    Pattern filters = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g|png|tiff?|mid|mp2|mp3|mp4"
            + "|wav|avi|mov|mpeg|ram|m4v|pdf|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    public MyCrawler() {
    }

    public boolean shouldVisit(WebURL url) {
        String href = url.getURL().toLowerCase();
        return !filters.matcher(href).matches() && href.startsWith("http://en.wikipedia.org");
    }

    public void visit(Page page) {
        String text = page.getText();
        String[] sentences = NlpService.detectedSentences(text);
        for (String sentence : sentences) {
            System.out.println(">>>>" + sentence);
        }

    }

    @Test
    public void test() throws Exception {
        CrawlController controller = new CrawlController("crawler/");
        controller.addSeed("http://en.wikipedia.org");
        controller.start(MyCrawler.class, 10);
    }
}