package benchmark.filesbenchmark;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import benchmark.Benchmarks;
import benchmark.IBenchmark;
import database.Database;
import database.Score;
import log.ConsoleLogger;
import log.myTimeUnit;
import stopwatch.Timer;

/**
 * Created by alex on 5/24/2017.
 */

public class FilesBenchmark implements IBenchmark {
    private static final int BUFFER_SIZE = 1024 * 4; // KB
    private static final int FILE_SIZE = 1024 * 1024 * 64; // MB
    private static String FILE_NAME = "benchmark.dat";
    private ConsoleLogger logger = new ConsoleLogger();
    private Timer timer = new Timer();
    private long result;
    private String extra;
    private File dir;
    private File file;
    private volatile boolean shouldTestRun;

    @Override
    public void initialize() {
        result = 0;
        dir = Database.getContext().getFilesDir();
        file = new File(dir, FILE_NAME);
    }

    @Override
    public void initialize(Long size) {

    }

    @Override
    public void warmup() {

    }

    @Override
    public void run(Object... param) {

    }

    @Override
    public void run() {
        this.initialize();
        this.compute();
        this.clean();
    }

    @Override
    public void stop() {
        this.shouldTestRun = false;
        this.compute();
    }

    @Override
    public void clean() {
        if (file != null) {
            file.delete();
            file = null;
        }
    }

    @Override
    public String getInfo() {
        return "File Write Speed Benchmark:\nMeasures storage write speed.";
    }


    @Override
    public Score getScore() {
        return new Score(
                Benchmarks.FilesBenchmark.toString(),
                String.format(java.util.Locale.US,"%.3f", FILE_SIZE / (1024 * 1024.0) / myTimeUnit.convertTime(this.result, myTimeUnit.Second)),
                this.extra);
    }

    @Override
    public void compute() {
        this.shouldTestRun = true;
        logger.write("Stream write benchmark");
        BufferedOutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file), BUFFER_SIZE);
        } catch (Exception e) {
            logger.write(e.toString());
            return;
        }

        byte[] buffer = new byte[BUFFER_SIZE];
        Random rand = new Random();
        timer.start();
        for (int i = 0; i < FILE_SIZE && this.shouldTestRun; i += BUFFER_SIZE) {
            rand.nextBytes(buffer);
            try {
                outputStream.write(buffer);
            } catch (Exception e) {
                logger.write(e.toString());
                return;
            }
        }
        this.result = timer.stop();
        this.shouldTestRun = false;
    }
}
