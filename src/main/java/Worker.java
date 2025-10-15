import java.util.concurrent.BlockingQueue;

class Worker extends Thread {
    private volatile boolean isRunning = true;
    private BlockingQueue<Runnable> taskQueue;
    public Worker(BlockingQueue<Runnable> taskQueue,String name){
        super(name);
        this.taskQueue = taskQueue;
    }
    @Override
    public void run() {

            while (isRunning) {
                try{
                Runnable task = taskQueue.take();
                task.run();
                } catch (InterruptedException e) {
                    if (!isRunning) {
                        break;
                    }
                }
                catch(Exception e) {
                    System.err.println(getName() + " encountered error: " + e.getMessage());
                }
            }

    }

    public void shutdown(){
        isRunning = false;
        this.interrupt();
    }
}