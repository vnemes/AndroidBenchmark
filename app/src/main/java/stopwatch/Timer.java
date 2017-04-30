package stopwatch;

/**
 * @author vendetta
 *
 */
public class Timer implements ITimer {
	private long startTime;
	private long duration;

	@Override
	public void start() {
        this.duration = 0;
		this.startTime = System.nanoTime();
	}

	@Override
	public long stop() {
		duration += System.nanoTime()-this.startTime;
		return duration;
	}

	@Override
	public void resume() {
		this.startTime = System.nanoTime();
	}

	@Override
	public long pause() {
        duration += System.nanoTime()-this.startTime;
        return duration;
	}
}
