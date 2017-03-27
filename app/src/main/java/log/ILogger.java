package log;
/**
 * 
 * 
 * @author user
 *
 */
interface ILogger {

	public void write(String s);

	public void write(long l);

	/**
	 * 
	 * @param l time in nanoseconds
	 * @param t timeunit
	 */
	public void writeTime(long l, myTimeUnit t);

}
