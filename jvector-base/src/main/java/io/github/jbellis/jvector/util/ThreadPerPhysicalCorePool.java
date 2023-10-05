package io.github.jbellis.jvector.util;

import java.util.concurrent.ForkJoinPool;
import java.util.function.Supplier;

public class ThreadPerPhysicalCorePool {
    private static final int physicalCoreCount = Integer.getInteger("jvector.physical_core_count", Math.max(4, Runtime.getRuntime().availableProcessors()/2));

    public static final ThreadPerPhysicalCorePool instance = new ThreadPerPhysicalCorePool(physicalCoreCount);

    private final ForkJoinPool pool;

    private ThreadPerPhysicalCorePool(int cores) {
        assert cores > 0 && cores <= Runtime.getRuntime().availableProcessors();
        this.pool = new ForkJoinPool(cores);
    }

    public void execute(Runnable run) {
        pool.submit(run).join();
    }

    public <T> T submit(Supplier<T> run) {
        return pool.submit(run::get).join();
    }
}
