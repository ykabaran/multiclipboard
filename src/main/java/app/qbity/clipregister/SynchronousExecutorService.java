package app.qbity.clipregister;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

public class SynchronousExecutorService extends AbstractExecutorService {

	private boolean running = false;

	public SynchronousExecutorService() {
		this.running = true;
	}

	@Override
	public void shutdown() {
		this.running = false;
	}

	@Override
	public List<Runnable> shutdownNow() {
		this.running = false;
		return new ArrayList<>(0);
	}

	@Override
	public boolean isShutdown() {
		return !this.running;
	}

	@Override
	public boolean isTerminated() {
		return !this.running;
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return true;
	}

	@Override
	public void execute(Runnable r) {
		r.run();
	}
}
