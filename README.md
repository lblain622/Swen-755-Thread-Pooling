# Swen-755-Thread-Pooling

**Domain:** Website Documentation Research  
**Task:** Crawling Web Content  
**Language:** Java 21  
**Build Tool:** Gradle  

---

## Overview  
This project demonstrates **thread pooling** through the implementation of a simple **multi-threaded web crawler**.  
The crawler starts with a user-provided URL, discovers links on that page, and continues crawling within the same domain using a fixed-size pool of worker threads.  

The project highlights concepts such as:
- Concurrency and synchronization using Java threads  
- Thread pool implementation with a custom queue  
- Web crawling and HTML parsing using **JSoup**

---

## Class Overview  

| Class | Description |
|-------|--------------|
| **Main** | Entry point of the program. Prompts the user for a website URL, initializes the thread pool, and starts the crawling process. |
| **CrawlerThreadPool** | Manages a fixed number of worker threads and a shared blocking task queue. Handles task submission and pool shutdown. |
| **Worker** | Represents a single worker thread. Continuously takes tasks from the queue and executes them until shut down. |
| **CrawlerTask** | Represents a single crawling task. Fetches HTML using JSoup, extracts same-domain links, and submits them back to the thread pool for further crawling. |

---

## Libraries Used  

The project uses **JSoup** for fetching and parsing HTML pages.

```gradle
dependencies {
    implementation 'org.jsoup:jsoup:1.17.1'
}
```

**JSoup documentation:** [https://jsoup.org/](https://jsoup.org/)

---

## How to Run  

### **Option 1: Using Gradle (Recommended)**  
1. Ensure you have **Java 21** and **Gradle** installed.  
2. Clone or download the project folder.  
3. Open a terminal in the project directory.  
4. Run the crawler using:  
   ```bash
   gradle runCrawler
   ```
5. Enter a website URL (e.g., `example.com`) when prompted.

---

### **Option 2: Manual Compilation and Run**
If Gradle is not available:
1. Compile all Java files:
   ```bash
   javac -cp "libs/jsoup-1.17.1.jar" *.java
   ```
2. Run the program:
   ```bash
   java -cp ".:libs/jsoup-1.17.1.jar" Main
   ```

---

## Example Output  

```
=== Thread Pooling: Web Crawler ===
Enter website URL: example.com
Crawling: https://example.com/
Using 10 threads...

Worker-0 Is Crawling: https://example.com/
Worker-3 Is Crawling: https://example.com/about
...

=== Results ===
Pages found: 42
Pages in queue: 5

Discovered pages:
  https://example.com/
  https://example.com/about
  https://example.com/contact
  ...
```

---

## How It Works  

1. **Main.java** initializes a 10-thread pool (`CrawlerThreadPool`) and a synchronized set of visited URLs.  
2. The first `CrawlerTask` starts crawling the provided URL.  
3. Each `CrawlerTask` uses **JSoup** to fetch and parse the HTML of a page.  
4. New same-domain links are discovered and submitted back to the pool.  
5. The crawler runs for 10 seconds, then prints results and gracefully shuts down all threads.

---

## Notes  
- The crawler is intentionally time-limited (10 seconds) to prevent infinite crawling loops.  
- Only same-domain links are followed to avoid leaving the target website.  
- Error handling ensures that invalid links or connection issues do not crash the program.  

---

## AI Usage
- We used ChatGPT and Grammarly to help phrase some parts of the readme.
- We used ChatGPT to help with formatting assistance for the readme markdown file.
- We used ChatGPT to help with Gradle Configuration for JSOUP

---
