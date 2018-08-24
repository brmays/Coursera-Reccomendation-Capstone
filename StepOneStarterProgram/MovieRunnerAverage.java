
/**
 * Write a description of MovieRunnerAverage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class MovieRunnerAverage {

    public void printAverageRatings() throws Exception {
        int min = 12;
        String ratings = "ratings.csv";
        String movies = "ratedmoviesfull.csv";
        
        SecondRatings sr = new SecondRatings(movies, ratings);
        System.out.println("There are " + sr.getMovieSize() + " movies.");
        System.out.println("There are " + sr.getRatersSize() + " raters.");
        
        ArrayList<Rating> qualRatings = sr.getAverageRatings(min);
        Collections.sort(qualRatings);
        System.out.println("There are " + qualRatings.size() + " movies with this many ratings.");
        
        for (int i = 0; i < qualRatings.size(); i++) {
            Rating r = qualRatings.get(i);
            System.out.println(r.getValue() + " \t" + (sr.getTitle(r.getItem())));
        }
    }
    
    public void getAverageRatingOneMovie() throws Exception {
        String movie = "Vacation";
        String ratings = "ratings.csv";
        String movies = "ratedmoviesfull.csv";
        SecondRatings sr = new SecondRatings(movies, ratings);
        String id = sr.getID(movie);
        int min = 0;
        double rating = sr.getAverageByID(id, min);
        
        System.out.println(movie + " had an average rating of " + rating + ".");
        
    }
    
}
