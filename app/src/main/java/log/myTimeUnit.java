package log;

public enum myTimeUnit {
    MicroSecond,
    MilliSecond,
    Second;

    /**
     * Converts nanoseconds to the given time unit.
     *
     * @param ns Time in nanoseconds.
     * @param unit Time unit to which nanoseconds are converted.
     * @return The converted value.
     */
    public static long convertTime(long ns, myTimeUnit unit) {
        switch (unit) {
            case MicroSecond: return ns / 1000;
            case MilliSecond:  return ns / 1000000;
            case Second:      return ns / 1000000000;
            default:          return ns;
        }
	}
}