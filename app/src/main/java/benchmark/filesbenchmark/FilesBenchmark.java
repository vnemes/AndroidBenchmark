package benchmark.filesbenchmark;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import benchmark.Benchmarks;
import benchmark.IBenchmark;
import database.Database;
import database.Score;
import log.ConsoleLogger;
import stopwatch.Timer;

import static java.lang.Math.sqrt;

/**
 * Created by alex on 5/24/2017.
 */

public class FilesBenchmark implements IBenchmark {
    private static final int BUFFER_SIZE = 1024 * 4; // KB
    private static final int FILE_SIZE = 1024 * 1024 * 64; // MB
    private static String FILE_NAME = "benchmark.dat";
    private ConsoleLogger logger = new ConsoleLogger();
    private Timer timer = new Timer();
    private long writeResult, readResult;
    private String extra;
    private File dir;
    private File file;
    private ArrayList<File> fileArr;
    private volatile boolean shouldTestRun;

    @Override
    public void initialize() {
        writeResult = 0;
        readResult = 0;
        fileArr = new ArrayList<>();
        dir = Database.getContext().getFilesDir();
        for (int i=0; i<16; i++){
            fileArr.add(new File(dir, FILE_NAME+i));
        }
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
        for (File file: fileArr) {
            if (file != null) {
                file.delete();
                file = null;
            }
        }
    }

    @Override
    public String getInfo() {
        return "Measures the read and write speeds of the internal storage using a fixed buffer of 4 KiloBytes.\nThis might take a while..";
    }


    @Override
    public Score getScore() {
        double resRead = FILE_SIZE / (1024 * 1024.0) /(this.readResult / 1000000000.0);
        double resWrite = FILE_SIZE / (1024 * 1024.0) /(this.writeResult / 1000000000.0);
        double res = sqrt(resRead*resWrite);
        return new Score(
                Benchmarks.FilesBenchmark.toString(),
                Long.toString((long)res*100),
                "Read speed: "+String.format(java.util.Locale.US, "%.3f",resRead)+" MB/s\nWrite Speed: "+String.format(java.util.Locale.US, "%.3f",resWrite)+"MB/s");
    }

    @Override
    public void compute() {
        this.shouldTestRun = true;
        logger.write("Stream write benchmark");
        BufferedOutputStream outputStream = null;
        BufferedInputStream inputStream = null;
        Random rand = new Random();
        byte[] buffer = new byte[BUFFER_SIZE];
        //TODO clean up this spaghetti code
        for (int i = 0; i < 16 && shouldTestRun; i++) {
            try {
                outputStream = new BufferedOutputStream(new FileOutputStream(fileArr.get(i)), BUFFER_SIZE);
            } catch (Exception e) {
                logger.write(e.toString());
                return;
            }
            timer.start();
            for (int j = 0; j < FILE_SIZE && this.shouldTestRun; j += BUFFER_SIZE) {
                rand.nextBytes(buffer);
                //logger.write("ok");
                try {
                    outputStream.write(buffer);
                } catch (Exception e) {
                    logger.write(e.toString());
                    return;
                }
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                logger.write(e.toString());
            }
            this.writeResult += timer.stop();
        }
        try {
            inputStream = new BufferedInputStream(new FileInputStream(fileArr.get(0)), BUFFER_SIZE);
            timer.start();
            for (int j = 0; j < FILE_SIZE && this.shouldTestRun; j += BUFFER_SIZE) {
                rand.nextBytes(buffer);
                //logger.write("ok");
                try {
                    inputStream.read(buffer);
                } catch (Exception e) {
                    logger.write(e.toString());
                    return;
                }
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                logger.write(e.toString());
            }
            this.readResult = timer.stop();
        } catch (FileNotFoundException e) {
            logger.write(e.toString());
        }
        this.writeResult /= 16;
        this.shouldTestRun = false;
    }
}
