
/**
 * Write a description of MovieRunnerSimilarRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class MovieRunnerSimilarRatings {
       
    public void printAverageRatings() throws Exception {
        int min = 2;
        String ratings = "/home/ben/Desktop/git/Coursera-Reccomendation-Capstone/StepOneStarterProgram/data/ratings_short.csv";
        String movies = "/home/ben/Desktop/git/Coursera-Reccomendation-Capstone/StepOneStarterProgram/data/ratedmovies_short.csv";
        
        RaterDatabase.addRatings(ratings);
        MovieDatabase.initialize(movies);
        FourthRatings fr = new FourthRatings();
        System.out.println("There are " + MovieDatabase.size() + " movies.");
        System.out.println("There are " + RaterDatabase.size() + " raters.");
        
        ArrayList<Rating> qualRatings = fr.getAverageRatings(min);
        Collections.sort(qualRatings);
        System.out.println("There are " + qualRatings.size() + " movies with this many ratings.");
        
        for (int i = 0; i < qualRatings.size(); i++) {
            Rating r = qualRatings.get(i);
            System.out.println(r.getValue() + " \t" + (MovieDatabase.getTitle(r.getItem())));
        }
    }
    
    public void printAverageRatingsByYearAndGenre() throws Exception {
        int min = 8;
        String ratings = "/home/ben/Desktop/git/Coursera-Reccomendation-Capstone/StepOneStarterProgram/data/ratings.csv";
        String movies = "/home/ben/Desktop/git/Coursera-Reccomendation-Capstone/StepOneStarterProgram/data/ratedmoviesfull.csv";
        
        RaterDatabase.addRatings(ratings);
        MovieDatabase.initialize(movies);
        FourthRatings fr = new FourthRatings();
        System.out.println("There are " + MovieDatabase.size() + " movies.");
        System.out.println("There are " + RaterDatabase.size() + " raters.");
        
        Filter genre = new GenreFilter("Drama");
        Filter year = new YearAfterFilter(1990);
        Filter all = new AllFilters();
        AllFilters newall = (AllFilters) all;
        
        newall.addFilter(genre);
        newall.addFilter(year);
        
        
        ArrayList<Rating> qualRatings = fr.getAverageRatingsByFilter(min, newall);
        Collections.sort(qualRatings);
        System.out.println("There are " + qualRatings.size() + " movies with this many ratings.");
        
        for (int i = 0; i < qualRatings.size(); i++) {
            Rating r = qualRatings.get(i);
            System.out.println(r.getValue() + "\t" + (MovieDatabase.getTitle(r.getItem())) + "\t" + (MovieDatabase.getYear(r.getItem()))+ "\t" + (MovieDatabase.getGenres(r.getItem())));
        }
                
    }
    
    private ArrayList<Rating> getSimilarities(String id) {
        ArrayList<Rating> similarRaters = new ArrayList<Rating>();
        ArrayList<Rater> raters = RaterDatabase.getRaters();
        
        for (Rater r : raters) {
            String curRater = r.getID();
            if (!curRater.equals(id)) {
                int sim = dotProduct(RaterDatabase.getRater(id), RaterDatabase.getRater(curRater));
                if (sim >= 0) {
                    Rating newRating = new Rating(curRater, (double) sim);
                    similarRaters.add(newRating);
                }
            }
        }
        
        
        Collections.sort(similarRaters);
        return similarRaters;
    }
    
    private int dotProduct(Rater me, Rater r) {
            int answer = 0;
            ArrayList<String> myRatedMovies = me.getItemsRated();
            
            for (String item : myRatedMovies) {
                if (r.hasRating(item)) {
                    answer += ((me.getRating(item) - 5) * (r.getRating(item) - 5));
                }
            }
            
            return answer;
        }
}
