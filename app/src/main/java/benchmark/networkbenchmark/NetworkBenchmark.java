package benchmark.networkbenchmark;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import benchmark.Benchmarks;
import benchmark.IBenchmark;
import database.Score;
import log.ConsoleLogger;
import stopwatch.Timer;

/**
 * Created by alex on 5/14/2017.
 */

/**
 * Measures download speed by downloading a large file from http://www.engineerhammad.com.
 */
public class NetworkBenchmark implements IBenchmark {
    private static final String FILE_ADDRESS = "ftp://speedtest:speedtest@ftp.otenet.gr/test1Gb.db";
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 512;
    private static final int BUFFER_SIZE = 1024 * 1024 * 16; // Buffer size in bytes.

    private long size = 1024 * 1024 * 64; // How much to download.
    private double result = 0; // MB/SECOND
    private String extra;
    private ConsoleLogger logger = new ConsoleLogger();
    private boolean shouldTestRun;

    @Override
    public void initialize() {
        this.result = 0;
    }

    /**
     * @param size the total download in bytes..
     */
    @Override
    public void initialize(Long size) {
        this.size = size;
    }

    @Override
    public void warmup() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void run(Object... param) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void run() {
        this.compute();
    }

    @Override
    public void compute() {
        this.shouldTestRun = true;
        logger.write("Benchmark started");
        int bufferSize = Math.min(BUFFER_SIZE, (int)size);
        logger.write("" + bufferSize);
        try {
            URLConnection connection = new URL(FILE_ADDRESS).openConnection();
            connection.setUseCaches(false);
            DataInput stream = new DataInputStream(connection.getInputStream());
            byte[] buffer = new byte[bufferSize];
            Timer timer = new Timer();
            ArrayList<Double> measurements = new ArrayList<>((int) this.size / bufferSize);
            boolean eof = false;
            int totalDownload = 0;
            while (totalDownload < size && this.shouldTestRun) {
                timer.start();
                try {
                    logger.write("Downloading....");
                    stream.readFully(buffer);
                } catch (EOFException e) {
                    eof = true;
                }
                long measure = timer.stop();
                if (eof) {
                    break;
                }
                totalDownload += bufferSize;
                logger.write("Download " + bufferSize + " " + (bufferSize / (1024 * 1024)));
                measurements.add((double)bufferSize / (1024 * 1024) / (measure / 1000000000.0));
            }
            double sum = 0;
            for (double each : measurements) {
                logger.write("" + String.format(java.util.Locale.US,"%.6f", each));
                sum += each;
            }
            this.result = sum * 4 / measurements.size();
        } catch (IOException e) {
            logger.write(e.toString());
        } finally {
            this.shouldTestRun = false;
        }
    }

    @Override
    public void stop() {
        this.shouldTestRun = false;
    }

    @Override
    public void clean() {

    }

    @Override
    public Score getScore() {
        return new Score(
                Benchmarks.NetworkBenchmark.toString(),
                String.format(java.util.Locale.US,"%.3f", this.result),
                this.extra);
    }

    @Override
    public String getInfo(){
        return "Network Speed Benchmark:\nMeasures download speed by downloading a large file from http://www.engineerhammad.com.";
    }
}