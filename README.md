# AndroidBenchmark
## Introduction

The *AndroidBenchmark* is an Android Application useful for assessing the performance of any device which runs on the Android Platform. It consists of a comprehensive suite of tests which evaluate the most important components of a device, such as the CPU, Internal Storage and the Wireless Adapter.

We have decided to start this project firstly because [Smartphones have become the most used mean to access the internet](https://www.theguardian.com/technology/2015/aug/06/smartphones-most-popular-way-to-browse-internet-ofcom), so there must exist a standardized way to evaluate and classify their performance and secondly, since [Android now the world's most popular operating system](http://www.networkworld.com/article/3187011/mobile-wireless/android-is-now-the-worlds-most-popular-operating-system.html) this platform seemed like the perfect fit to collect the biggest data set.

## Design and Implementation
### Benchmarks

The application consists of 7 benchmarks, each and every one of them testing a different functionality of the device under test.
The benchmarks implemented are as follows:
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
<br>Evaluates the read/write performance of the device using a fixed 4kb buffer, writing 16 files of 64mb each.

### Firebase Authentification and Database

When first logging into the application, the device will be assigned an unique id used for anonymously auhenticating it in the Firebase System. By using that id the Application can query the *Firebase Database* by taking SnapShots at certain locations where dataChangeListeners are triggered.
Using these SnapShots, the application retrieves the scores of an user for a particular benchmark, all its scores, or all the scores of all the users on a particular benchmark. 

Data is posted in two locations in the Firebase Database, one is the path which is only accessible by the user, and the other one where all users post their scores for a particular benchmark.

## State of the Art

Since the Android Operating System is so widely used, obviously there exist other Benchmark Applicatins which assess the performance of the device. Two of the most popular ones are:

+ [*AnTuTu Benchmark*](https://play.google.com/store/apps/details?id=com.antutu.ABenchMark&hl=en) - A full suite of benchmarks which evaluate the CPU, RAM, GPU and the IO of a device. <br>
The most interesting of its features is the *UX* metric, which assesses the *User experience* through multithreaded tasks and runtime application evaluation (on Dalvik or ART)

+ [*3DMARK*](https://play.google.com/store/apps/details?id=com.futuremark.dmandroid.application&hl=en) - One of the most popular Video benchmarks on PC is also availible on Android. This benchmark evaluates the performance of device in respect to 3D graphic rendering and CPU workload processing capabilities. 
<br>At the moment of this writing, 3DMark consists of 4 tests on Android, 2 for Flagship devices and 2 for older devices comparison. Each tests assesses the performance of the device by rendering a multiple scenes with shaders, particle effects, post processing and other GPU & CPU intensive effects added.

Both of these benchmarks have been tested on over 3000 devices and have over 100000 values in their datasets.

## Usage: Activities

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

## Results
| - | - |
|---|---|
![scores image](https://s7.postimg.org/5chb0ichn/capture.png "Preliminary benchmark results") | After running the benchmark on several devices, the performance assessment is very similar to the results found on more popular benchmarks (i.e. AnTuTu Benchmark) |

One interesting fact we have found our during our beta deployment is that the Google Pixel Smartphone has a hashing performance (evaluated using BCrypt) several orders of magnitude higher than any other device we have tested so far. We are still analysing the cause of this discrepancy (perhaps hardware accelerated BCrypt hash computation?)

## Conclusions

We feel that while developing this project we have learn a lot of particularities of the Android Application Development process, Android & Firebase specific best practices, and last but not least a better understanding in the inner workings of how the complex system we carry in our pocket works and interacts with the environment, be it through sensors or the internet.

