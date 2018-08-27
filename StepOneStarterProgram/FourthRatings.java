
/**
 * Write a description of FourthRating here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class FourthRatings {
    
    public double getAverageByID(String id, int minimalRaters) {
        double scoreCount = 0.0;
        double rateCount = 0.0;
        RaterDatabase.intitialize(
        for (Rater r : myRaters.values()) {
            if (r.hasRating(id)) {
                scoreCount += r.getRating(id);
                rateCount++;
            }
        }
        
        if (rateCount >= minimalRaters) {
            return scoreCount/rateCount;
        } else {
            return 0.0;
        }
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        ArrayList<Rating> qualRatings = new ArrayList<Rating>();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        Iterator<String> itr = movies.iterator();
        while (itr.hasNext()) {
            String id = itr.next();
            double score = getAverageByID(id, minimalRaters);
            if (score != 0.0) {
                Rating r = new Rating(id, score);
                qualRatings.add(r);
            }
        }
        
        return qualRatings;
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        ArrayList<Rating> qualRatings = new ArrayList<Rating>();
        //create a string array list of movies using the filter, then calcuate the averages
        ArrayList<String> afterFilter = MovieDatabase.filterBy(filterCriteria);
        Iterator<String> itr = afterFilter.iterator();
        while (itr.hasNext()) {
            String id = itr.next();
            double score = getAverageByID(id, minimalRaters);
            if (score != 0.0) {
                Rating r = new Rating(id, score);
                qualRatings.add(r);
            }
        }
        return qualRatings;
    }
}
