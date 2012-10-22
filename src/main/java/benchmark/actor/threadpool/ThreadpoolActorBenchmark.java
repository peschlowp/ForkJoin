package benchmark.actor.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import actor.ActorContext;
import benchmark.actor.ActorBenchmarkDef;
import benchmark.actor.ActorGenerator;

/**
 * Benchmark definition for the parallel actor scenario using a {@link ThreadPoolExecutor}. When the
 * benchmark is started, the initial messages are scheduled. The benchmark ends when the context
 * indicates that it is finished.
 * 
 * When all benchmark runs have completed, the threadpool is shut down. This is required because the
 * internal threads are not started as daemon threads.
 * 
 * @author patrick.peschlow
 */
public class ThreadpoolActorBenchmark extends ActorBenchmarkDef {

    private final ExecutorService threadPool;

    private ThreadpoolDispatcher dispatcher;

    public ThreadpoolActorBenchmark(ActorGenerator generator, int numThreads) {
	super(generator);
	this.threadPool = Executors.newFixedThreadPool(numThreads);
    }

    @Override
    public Object execute() {
	dispatcher.scheduleInitially();
	context.waitForFinished();
	return null;
    }

    @Override
    public void afterAll() {
	threadPool.shutdown();
    }

    @Override
    protected void initContext() {
	dispatcher = new ThreadpoolDispatcher(actors, threadPool);
	context = new ActorContext(dispatcher, actors, initialMessages);
    }
}
