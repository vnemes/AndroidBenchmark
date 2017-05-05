package log;

public enum myTimeUnit {
    MicroSecond,
    MiliSecond,
    Second;

    /**
     * Converts nanoseconds to the given time unit.
     *
     * @param ns Time in nanoseconds.
     * @param unit Time unit to which nanoseconds are converted.
     * @return The converted value.
     */
    public static double convertTime(long ns, myTimeUnit unit) {
        switch (unit) {
            case MicroSecond: return ns / 1000.0;
            case MiliSecond:  return ns / 1000000.0;
            case Second:      return ns / 1000000000.0;
            default:          return ns;
        }
	}
}