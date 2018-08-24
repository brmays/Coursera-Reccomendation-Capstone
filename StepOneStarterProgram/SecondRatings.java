
/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private HashMap<String, Rater> myRaters;
    
    public SecondRatings() throws Exception {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    
    public SecondRatings(String moviefile, String ratingsfile) throws Exception{
        FirstRatings fr = new FirstRatings();
        myMovies = fr.loadMovies(moviefile);
        myRaters = fr.loadRater(ratingsfile);
    }
    
    public int getMovieSize() {
        return myMovies.size();
    }
    
    public int getRatersSize() {
        return myRaters.size();
    }
    
    public String getID(String title) {
        String answer = "NO SUCH TITLE.";
        
        for (int i = 0; i < myMovies.size(); i++) {
            Movie m = myMovies.get(i);
            if (title.equals(m.getTitle())) {
                answer = m.getID();
                break;
            }
        }
        
        return answer;
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        ArrayList<Rating> qualRatings = new ArrayList<Rating>();
        Iterator<Movie> itr = myMovies.iterator();
        while (itr.hasNext()) {
            Movie m = itr.next();
            String id = m.getID();
            double score = getAverageByID(id, minimalRaters);
            if (score != 0.0) {
                Rating r = new Rating(id, score);
                qualRatings.add(r);
            }
        }
        
        return qualRatings;
    }
    
    public String getTitle(String id) {
        String title = "ID not found";
        Iterator<Movie> itr = myMovies.iterator();
        while (itr.hasNext()) {
            Movie m = itr.next();
            String curID = m.getID();
            if (curID.equals(id)) {
                title = m.getTitle();
            }
        }
        
        return title;
    }
    
    public double getAverageByID(String id, int minimalRaters) {
        double scoreCount = 0.0;
        double rateCount = 0.0;
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
    
}