package log;
/**
 * @author Vendetta
 */


import android.util.Log;

/**
 * @author user
 *
 */
public class ConsoleLogger implements ILogger {

	/**
	 * @param s String s string to be written
	 * 
	 */
	@Override
	public void write(String s) {
		Log.d("BenchmarkResult:",s);
	}

	/** 
	 * @param l long value to be written
	 * 
	 */
	@Override
	public void write(long l) {
        Log.d("BenchmarkResult:",String.valueOf(l));
	}

	/* 
	 * 
	 */
	@Override
	public void writeTime(long l, myTimeUnit t) {
        Log.d("BenchmarkResult:",String.valueOf(t.convertTime(l,t)));
	}

}
