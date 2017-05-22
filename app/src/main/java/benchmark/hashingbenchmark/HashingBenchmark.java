package benchmark.hashingbenchmark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import benchmark.IBenchmark;
import log.ConsoleLogger;

/**
 * Created by alex on 5/15/2017.
 */

/**
 * Hashes passwords using the BCrypt algorithm.
 */
public class HashingBenchmark implements IBenchmark {
    private static final int THREAD_POOL_SIZE = 4;
    private static final long RND_SEED = 25214903917L; // Used for shuffling.

    private ConsoleLogger logger = new ConsoleLogger();

    private final List<Character> alphabet = new ArrayList<>();
    private long size = 100L; // How many passwords to be hashed.
    private boolean shouldTestRun;

    @Override
    public void initialize() {
        for (int i = 0x20; i <= 0x7E; ++i) {
            alphabet.add((char) i);
        }
    }

    @Override
    public void initialize(Long size) {
        this.size = size;
        initialize();
    }

    @Override
    public void warmup() {
        Long prevSize = this.size;
        initialize(10L);
        run();
        this.size = prevSize;
    }

    @Override
    public void run(Object... param) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void run() {
        this.shouldTestRun = true;
        Random rnd = new Random(RND_SEED);
        final ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        final List<Future<String>> results = new ArrayList<>((int)this.size);
        for (int i = 0; i < this.size && this.shouldTestRun; ++i) {
            Collections.shuffle(alphabet, rnd);
            StringBuilder passwordBuilder = new StringBuilder(alphabet.size());
            for(Character chr: alphabet) {
                passwordBuilder.append(chr);
            }
            logger.write(passwordBuilder.toString());
            results.add(threadPool.submit(new HashCalculator(passwordBuilder.toString())));
        }
        for (Future<String> r : results) {
            try {
                logger.write("hash " + r.get());
            } catch (Exception e) {
                logger.write("Exception: " + e.toString());
            }
        }
        threadPool.shutdownNow();
        this.shouldTestRun = false;
    }

    @Override
    public void stop() {
        this.shouldTestRun = false;
    }

    @Override
    public void clean() {}

    private class HashCalculator implements Callable<String> {
        private final String password;

        HashCalculator(String password) {
            this.password = password;
        }

        @Override
        public String call() throws Exception {
            return BCrypt.hashpw(password, BCrypt.gensalt(13));
        }
    }
}
