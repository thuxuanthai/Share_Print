package Server;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ThreadPrinter {
	BlockingQueue<Runnable> blockingQueue;
	public ThreadPrinter(BlockingQueue<Runnable> blockingQueue) {
		this.blockingQueue = blockingQueue;
		
		CustomThreadPoolExecutor executor = new CustomThreadPoolExecutor(10, 20, 5, TimeUnit.SECONDS,this.blockingQueue);
	 	executor.prestartAllCoreThreads();
	 	executor.shutdown();
	 	try {
			executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}