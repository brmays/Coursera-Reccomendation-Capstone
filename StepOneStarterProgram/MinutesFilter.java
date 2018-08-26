
/**
 * Write a description of MinutesFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MinutesFilter implements Filter {
	private int minTime;
	private int maxTime;
	
	public MinutesFilter(int min, int max) {
		minTime = min;
		maxTime = max;
	}
	
	@Override
	public boolean satisfies(String id) {
		return ((MovieDatabase.getMinutes(id) >= minTime) && (MovieDatabase.getMinutes(id) <= maxTime));
	}

}
