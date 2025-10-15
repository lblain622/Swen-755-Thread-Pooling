import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.net.URL;

public class CrawlerTask implements Runnable{
    private String url;
    private String baseDomain;
    private Set<String> visitedUrls;
    private CrawlerThreadPool pool;
    private Random random = new Random();
    public CrawlerTask(String url, String baseDomain, Set<String> visitedUrls, CrawlerThreadPool pool){
        this.url = url;
        this.baseDomain = baseDomain;
        this.visitedUrls = visitedUrls;
        this.pool = pool;
    }

    @Override
    public void run() {
        String ThreadName = Thread.currentThread().getName();
        System.out.println( ThreadName+ " Is Crawling:" + this.url);
            //Simulate a crawl request
//            Thread.sleep(100 + random.nextInt(300));
//
//            List<String> newLinks = findLinks();
//            for (String link : newLinks) {
//                if (isSameDomain(link) && visitedUrls.add(link)) {
//                    pool.submit(new CrawlerTask(link, baseDomain, visitedUrls, pool));
//                }
//            }
            //We Can Change this to crawl the actual site in here
        try {
            Document site = Jsoup.connect(url)
                    .timeout(10000)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .get();
            List<String> newLinks = findRealLinks(site);
            for (String link : newLinks) {
                if (visitedUrls.add(link)) {
                    pool.submit(new CrawlerTask(link, baseDomain, visitedUrls, pool));
                }
            }
        } catch (java.lang.Exception e) {
            throw new RuntimeException(e);
        }

    }

    //Healer To find real Links
    private List<String> findRealLinks(Document doc) {
        List<String> links = new ArrayList<>();
        Elements linkElements = doc.select("a[href]");

        for (Element link : linkElements) {
            String href = link.attr("href");
            String absoluteUrl = makeAbsoluteUrl(href);

            if (absoluteUrl != null && isSameDomain(absoluteUrl)) {
                links.add(absoluteUrl);
            }
        }

        return links;
    }

    private String makeAbsoluteUrl(String href) {
        try {
            if (href.startsWith("javascript:") || href.startsWith("mailto:") || href.startsWith("#")) {
                return null;
            }

            URL base = new URL(url);
            URL absolute = new URL(base, href);
            return absolute.toString();

        } catch (Exception e) {
            return null;
        }
    }

//    //Helper to simulation possible urls to crawl
//    private List<String> findLinks() {
//        // Simulate finding links on page
//        List<String> links = new ArrayList<>();
//        String[] paths = {"/about", "/contact", "/products", "/services", "/docs", "/api"};
//
//        int linkCount = 2 + random.nextInt(4); // 2-5 links
//        for (int i = 0; i < linkCount; i++) {
//            String path = paths[random.nextInt(paths.length)];
//            links.add(baseDomain + path + random.nextInt(10));
//        }
//
//        return links;
//    }

    //To check if the link is from the same domain
    private boolean isSameDomain(String url) {
        return url.startsWith(baseDomain);
    }
}