package benchmark.CPUbenchmark;

import java.util.ArrayList;
import java.util.Random;

import benchmark.IBenchmark;
import stopwatch.Timer;

/**
 * Created by Vendetta on 16-Mar-17.
 */

public class OPSCPUBenchmark implements IBenchmark {
    private int numberOfLoops;
    private ArrayList<Integer> numbers;
    private final static int NUMBEROFOPERATIONS = 60;

    public int getNumberOfOperations() {
        return this.NUMBEROFOPERATIONS;
    }

    public int getNumberOfLoops(){
        return this.numberOfLoops;
    }

    public double computeMOPS(Long size){
        Timer t = new Timer();
        this.initialize(size);
        t.start();
        this.run();
        Long timeElapsed = t.stop();
        return ((double)this.NUMBEROFOPERATIONS * (double)this.numberOfLoops) / (double)(timeElapsed / 1000L);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void initialize(Long size) {
        Random rs = new Random();
        rs.setSeed(0xFFFFFFFFL);
        this.numberOfLoops = size.intValue();
        numbers = new ArrayList<>();
        for (int i = 0; i<this.numberOfLoops; i++){
            this.numbers.add(i,rs.nextInt(this.numberOfLoops));
        }
    }

    @Override
    public void warmup() {

    }

    @Override
    public void run(Object... param) {

    }

    @Override
    public void run() { //                                                                                  TOTAL NUMBER OF OPERATIONS/NUMBER OF ITERATIONS = 60 OPs
        int result = 0; // 2 (declaration and initialization)                                                                                               = 2 OPs
                                                                                                        /*arithmetic operations (+,-,/,&,>>,<<)*///   TOTAL =30 OPs
        for (int i = 0; i < this.numberOfLoops - 6; i++) { //2 (initial assignment, incrementation) + 1 (memory access) + 1 (subtraction) + 1 (comparison)  = 4 OPs
            result += this.numbers.get(i + 5); // 1 (memory access) + 1(memory access) + 1 (index addition) + 1 (result addition)                           = 4 OPs
            result -= this.numbers.get(i + 4); // 1 (memory access) + 1(memory access) + 1 (index addition) + 1 (result addition)                           = 4 OPs
            result *= this.numbers.get(i + 3); // 1 (memory access) + 1(memory access) + 1 (index addition) + 1 (result addition)                           = 4 OPs
            result &= this.numbers.get(i + 2); // 1 (memory access) + 1(memory access) + 1 (index addition) + 1 (result addition)                           = 4 OPs
            result >>= this.numbers.get(i + 1);// 1 (memory access) + 1(memory access) + 1 (index addition) + 1 (result addition)                           = 4 OPs
            result <<= this.numbers.get(i);    // 1 (memory access) + 1(memory access) + 1 (index addition) + 1 (result addition)                           = 4 OPs

                                                                                                          /*branching, ifs *///   WORST-CASE SCENARIO TOTAL =22 OPs
            if (this.numbers.get(i + 5) < this.numbers.get(i + 4)) { // 2 (index addition) + 2 (memory accesses) + 1 (comparison)                           = 5 OPs
                if (this.numbers.get(i + 4) < this.numbers.get(i + 3)) {// 2 (index addition) + 2 (memory accesses) + 1 (comparison)                        = 5 OPs
                    if (this.numbers.get(i + 2) > this.numbers.get(i)) {// 2 (index addition) + 2 (memory accesses) + 1 (comparison)                        = 5 OPs
                        result++;                                       // 1 (memory access) + 1 (incrementing)                                             = 2 OPs
                    } else if (this.numbers.get(i + 3) < this.numbers.get(i + 2)) { // 2 (index addition) + 2 (memory accesses) + 1 (comparison)            = 5 OPs
                        --result;                                       // 1 (memory access) + 1 (decrementing)                                             = 2 OPs
                    } else ++result;                                    // 1 (memory access) + 1 (incrementing)                                             = 2 OPs
                } else {
                    if (this.numbers.get(i + 3) < this.numbers.get(i + 2)) {        // 2 (index addition) + 2 (memory accesses) + 1 (comparison)            = 5 OPs
                        if (this.numbers.get(i + 1) < this.numbers.get(i + 2)) {    // 2 (index addition) + 2 (memory accesses) + 1 (comparison)            = 5 OPs
                            ++result;                                   // 1 (memory access) + 1 (incrementing)                                             = 2 OPs
                        } else {
                            if (this.numbers.get(i + 4) < this.numbers.get(i + 2)) {// 2 (index addition) + 2 (memory accesses) + 1 (comparison)            = 5 OPs
                                --result;                               // 1 (memory access) + 1 (decrementing)                                             = 2 OPs
                            }
                        }
                    }
                }
            } else {
                if (this.numbers.get(i + 4) < this.numbers.get(i + 2)) {            // 2 (index addition) + 2 (memory accesses) + 1 (comparison)            = 5 OPs
                    if (this.numbers.get(i) < this.numbers.get(i + 1)) {            // 2 (index addition) + 2 (memory accesses) + 1 (comparison)            = 5 OPs
                        ++result;                                          // 1 (memory access) + 1 (incrementing)                                          = 2 OPs
                    } else if (this.numbers.get(i + 3) < this.numbers.get(i + 2)) { // 2 (index addition) + 2 (memory accesses) + 1 (comparison)            = 5 OPs
                        --result;                                           // 1 (memory access) + 1 (decrementing)                                         = 2 OPs
                    }
                } else if (this.numbers.get(i) < this.numbers.get(i + 2)) {         // 2 (index addition) + 2 (memory accesses) + 1 (comparison)            = 5 OPs
                    if (this.numbers.get(i + 3) < this.numbers.get(i + 2)) {        // 2 (index addition) + 2 (memory accesses) + 1 (comparison)            = 5 OPs
                        ++result;                                       // 1 (memory access) + 1 (incrementing)                                             = 2 OPs
                    } else if (this.numbers.get(i + 3) < this.numbers.get(i + 2)) { // 2 (index addition) + 2 (memory accesses) + 1 (comparison)            = 5 OPs
                        --result;                                       // 1 (memory access) + 1 (decrementing)                                             = 2 OPs
                    }
                }
            }

                                                                                          /*array operations, acces at index, access a[b[c[i]]]*///  TOTAL  = 8 OPs
            result += numbers.get(numbers.get(numbers.get(numbers.get(numbers.get(i)))));// 1 (memory access) + 1 (addition) + 6 (memory accesses)          = 8 OPs

        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void clean() {

    }
}
