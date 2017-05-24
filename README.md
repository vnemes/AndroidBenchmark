# AndroidBenchmark

## Android Activities

*BaseActivity* is the superclass that all other activities extend.
The class handles basic UI tasks such as inflating the drawer and header Views and setting the respectve onItemSelected and *onClickListeners* listeners.

The Drawer on the left contains the benchmarks that can be ran by the user, and by tapping one of them, an Intent is generated towards the *BenchmarkActivity*, with the payload as a value from the enum Benchmarks.

*MainActivty* is the Activity the user is presented first. It contains a list of available benchmarks which, when tapped, launch the respective benchmark. The list is synced with the values in the Database.

The button on the lower screen launches a "Benchmark Suite", a benchmark that will iterate though all availible benchmarks and run them.

*BenchmarkActivity* is the Activity that runs any benchmark.
When first created, it will extract the payload from the intent, and by using java reflection it will dynamically instantiate an Object of type IBenchmark, depending on the payload value.

General information about the benchmark are presented in the field above the *Run button*, which, when pressed starts the benchmark and shows a spinner for its entire duration.

The *benchmark.run()* method is ran inside an asynchronous task, which on completition launches the *ScoreActivity*.
*ScoreActivity* is the Activity that displays the user the score achieved after running the benchmark, metrics about the benchmark and a leaderboard of devices and their respective scores from the Database.

## Firebase Authentification and Database

When first logging into the application, the device will be assigned an unique id used for anonymously auhenticating it in the Firebase System. By using that id the Application can query the *Firebase Database* by taking SnapShots at certain locations where dataChangeListeners are triggered.
Using these SnapShots, the application retrieves the scores of an user for a particular benchmark, all its scores, or all the scores of all the users on a particular benchmark. 
Data is posted in two locations in the Firebase Database, one is the path which is only accessible by the user, and the other one where all users post their scores for a particular benchmark.
