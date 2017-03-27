package log;

public enum myTimeUnit {
	MicroSecond,
	MiliSecond,
	Second;

	public static long convertTime(long l,myTimeUnit t){ // takes as argument l in nanoSeconds
		switch (t){
		case MicroSecond: 	return l/1000;
		case MiliSecond: return l/1000000;
		case Second:  return l/1000000000;
		default:				 return l;
		}
	}
}
