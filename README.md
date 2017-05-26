# AndroidBenchmark

## About the benchmarks

+ CPU Benchmark
This benchmark evaluates the performance of the CPU. It consists of 3 smaller benchmarks:
<br>*Integer arithmetic*: Stresses the ALU by doing lots of integer computations.
<br>*Floating point*: Stresses the FPU by doing lots of floating point computations.
<br>*Digits of PI*: Puts caching and multithreading to the test by computing digits of PI, using the
[Bailey–Borwein–Plouffe formula](https://en.wikipedia.org/wiki/Bailey%E2%80%93Borwein%E2%80%93Plouffe_formula) in a parallel manner.
+ Hashing Benchmark
<br>This benchmark computes hashes using the BCrypt Algorithm. [The implementation](http://www.mindrot.org/projects/jBCrypt/) of the BCrypt class was provided by Damien Miller.
+ Network Benchmark
<br>Measures download speed by downloading part of a large file from <http://www.engineerhammad.com>.
+ Files Benchmark
<br>Evaluates the read/write performance of the device using a fixed 4kb buffer, reading/writing 16 files of 64mb each.

## Comparison with AnTuTu

| Features        | AndroidBenchmark  | Antutu |
| :-------------: |:-----------------:| :-----:|
| UX   | No | Yes |
| CPU  | Yes | Yes |
| RAM | No |  Yes |
| Storage IO | Yes | Yes |
| GPU | No | Yes |
| Network | Yes | No |


## Activities

+ *BaseActivity* is the superclass that all other activities extend.
The class handles basic UI tasks such as inflating the drawer and header Views and setting the respectve onItemSelected and *onClickListeners* listeners.

+ *MainActivty* is the Activity the user is presented first. It contains a list of available benchmarks which, when tapped, launch the respective benchmark. The list is synced with the values in the Database.

+ *BenchmarkActivity* is the Activity that runs any benchmark.
When first created, it will extract the payload from the intent, and by using java reflection it will dynamically instantiate an Object of type *IBenchmark*, depending on the payload value.
<br>The *Button* on the lower screen launches a *Benchmark Suite*, a benchmark that will iterate though all availible benchmarks and run them.
<br>The *Drawer* on the left contains the benchmarks that can be ran by the user, and by tapping one of them, an Intent is generated towards the *BenchmarkActivity*, with the payload as a value from the *enum Benchmarks*.
General information about the benchmark are presented in the field above the *Run button*, which, when pressed starts the benchmark and shows a spinner for its entire duration.
<br>The *benchmark.run()* method is ran inside an asynchronous task, which on completition launches the *ScoreActivity*.

+ *ScoreActivity* is the Activity that displays the user the score achieved after running the benchmark, metrics about the benchmark and a leaderboard of devices and their respective scores from the Database.

## Firebase Authentification and Database

When first logging into the application, the device will be assigned an unique id used for anonymously auhenticating it in the Firebase System. By using that id the Application can query the *Firebase Database* by taking SnapShots at certain locations where dataChangeListeners are triggered.
Using these SnapShots, the application retrieves the scores of an user for a particular benchmark, all its scores, or all the scores of all the users on a particular benchmark. 

Data is posted in two locations in the Firebase Database, one is the path which is only accessible by the user, and the other one where all users post their scores for a particular benchmark.
