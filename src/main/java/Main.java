import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Thread Pooling: Web Crawler ===");
        System.out.print("Enter website URL: ");
        String url = scanner.nextLine().trim();


        if (!url.startsWith("http")) {
            url = "https://" + url;
        }

        // Ensure URL ends with /
        if (!url.endsWith("/")) {
            url = url + "/";
        }

        System.out.println("Crawling: " + url);
        System.out.println("Using 10 threads...\n");

        // Setup
        Set<String> visitedUrls = Collections.synchronizedSet(new HashSet<>());
        CrawlerThreadPool pool = new CrawlerThreadPool(10);

        // Start with initial URL
        visitedUrls.add(url);
        pool.submit(new CrawlerTask(url, url, visitedUrls, pool));

        // Let it run for a while
        try {
            Thread.sleep(10000); // Run for 10 seconds

            System.out.println("\n=== Results ===");
            System.out.println("Pages found: " + visitedUrls.size());
            System.out.println("Pages in queue: " + pool.getQueueSize());

            // Show some discovered pages
            System.out.println("\nDiscovered pages:");
            int count = 0;
            for (String page : visitedUrls) {
                if (count++ < 10) { // Show first 10 pages
                    System.out.println("  " + page);
                }
            }
            if (visitedUrls.size() > 10) {
                System.out.println("  ... and " + (visitedUrls.size() - 10) + " more");
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Shutdown
        pool.shutdown();
        scanner.close();
    }
}