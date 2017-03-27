package stopwatch;

interface ITimer {
	/**
	 * 
	 */
	public void start();
	/**
	 * 
	 * 
	 * @return long Re
	 */
	public long stop();

	public void resume();

	public long pause();
	
}
