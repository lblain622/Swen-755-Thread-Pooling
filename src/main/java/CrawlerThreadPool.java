import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class CrawlerThreadPool{
    private final BlockingQueue<Runnable> taskQueue;
    private final Worker[] workingThreads;
    private volatile boolean isRunning;

    public CrawlerThreadPool(int poolSize){
        this.taskQueue = new LinkedBlockingQueue<>();
        this.workingThreads = new Worker[poolSize];
        this.isRunning = true;

        for (int i =0; i<poolSize; i++){
            workingThreads[i] = new Worker(taskQueue,"Worker-" + i);
            workingThreads[i].start();
        }
        System.out.println("Thread pool started with " + poolSize + " threads");
    }

    public void submit(Runnable task){
        if(!isRunning) return;
        taskQueue.offer(task);
    }

    public void shutdown(){
        isRunning = false;
        for (Worker worker : workingThreads) {
            worker.shutdown();
        }
        System.out.println("Shutting Down Thread Pool");
    }
    public int getQueueSize() {
        return taskQueue.size();
    }
}