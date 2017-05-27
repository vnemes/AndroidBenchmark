package stopwatch;

/**
 * Measures time.
 */
interface ITimer {
	/**
	 * Starts the timer.
	 */
	public void start();

	/**
	 * Stops the timer.
	 *
	 * @return Time passed since last start.
	 */
	public long stop();

	/**
	 * Resumes timer.
	 */
	public void resume();

	/**
	 * Pauses the timer.
	 *
	 * @return Time passed since last resume.
	 */
	public long pause();
}
