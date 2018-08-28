
/**
 * Write a description of MovieRunnerWithFilters here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class MovieRunnerWithFilters {

    public void printAverageRatings() throws Exception {
        int min = 2;
        String ratings = "C:\\git\\Coursera-Reccomendation-Capstone\\StepOneStarterProgram\\data\\ratings_short.csv";
        String movies = "C:\\git\\Coursera-Reccomendation-Capstone\\StepOneStarterProgram\\data\\ratedmovies_short.csv";
        
        MovieDatabase.initialize(movies);
        ThirdRatings tr = new ThirdRatings(ratings);
        System.out.println("There are " + MovieDatabase.size() + " movies.");
        System.out.println("There are " + tr.getRatersSize() + " raters.");
        
        ArrayList<Rating> qualRatings = tr.getAverageRatings(min);
        Collections.sort(qualRatings);
        System.out.println("There are " + qualRatings.size() + " movies with this many ratings.");
        
        for (int i = 0; i < qualRatings.size(); i++) {
            Rating r = qualRatings.get(i);
            System.out.println(r.getValue() + " \t" + (MovieDatabase.getTitle(r.getItem())));
        }
    }
    
    public void printAverageRatingsByYear() throws Exception {
        int min = 20;
        String ratings = "ratings.csv";
        String movies = "ratedmoviesfull.csv";
        int year = 2000;
        
        MovieDatabase.initialize(movies);
        ThirdRatings tr = new ThirdRatings(ratings);
        System.out.println("There are " + MovieDatabase.size() + " movies.");
        System.out.println("There are " + tr.getRatersSize() + " raters.");
        Filter filter = new YearAfterFilter(year);
        
        ArrayList<Rating> qualRatings = tr.getAverageRatingsByFilter(min, filter);
        Collections.sort(qualRatings);
        System.out.println("There are " + qualRatings.size() + " movies with this many ratings.");
        
        for (int i = 0; i < qualRatings.size(); i++) {
            Rating r = qualRatings.get(i);
            System.out.println(r.getValue() + " \t" + (MovieDatabase.getYear(r.getItem())) + "\t" + (MovieDatabase.getTitle(r.getItem())));
        }
        
    }
    
    public void printAverageRatingsByGenre() throws Exception {
        int min = 20;
        String ratings = "ratings.csv";
        String movies = "ratedmoviesfull.csv";
        String genre = "Comedy";
        
        MovieDatabase.initialize(movies);
        ThirdRatings tr = new ThirdRatings(ratings);
        System.out.println("There are " + MovieDatabase.size() + " movies.");
        System.out.println("There are " + tr.getRatersSize() + " raters.");
        Filter filter = new GenreFilter(genre);
        
        ArrayList<Rating> qualRatings = tr.getAverageRatingsByFilter(min, filter);
        Collections.sort(qualRatings);
        System.out.println("There are " + qualRatings.size() + " movies with this many ratings.");
        
        for (int i = 0; i < qualRatings.size(); i++) {
            Rating r = qualRatings.get(i);
            System.out.println(r.getValue() + "\t" + (MovieDatabase.getTitle(r.getItem()) + "\t" + (MovieDatabase.getGenres(r.getItem()))));
        }
        
    }
    
    public void printAverageRatingsByMinutes() throws Exception {
        int min = 5;
        int minTime = 105;
        int maxTime = 135;
        String ratings = "ratings.csv";
        String movies = "ratedmoviesfull.csv";
              
        MovieDatabase.initialize(movies);
        ThirdRatings tr = new ThirdRatings(ratings);
        System.out.println("There are " + MovieDatabase.size() + " movies.");
        System.out.println("There are " + tr.getRatersSize() + " raters.");
        Filter filter = new MinutesFilter(minTime, maxTime);
        
        ArrayList<Rating> qualRatings = tr.getAverageRatingsByFilter(min, filter);
        Collections.sort(qualRatings);
        System.out.println("There are " + qualRatings.size() + " movies with this many ratings.");
        
        for (int i = 0; i < qualRatings.size(); i++) {
            Rating r = qualRatings.get(i);
            System.out.println(r.getValue() + "\t" + (MovieDatabase.getTitle(r.getItem()) + "\t" + "Time: " + (MovieDatabase.getMinutes(r.getItem()))));
        }
        
    }
    
    public void printAverageRatingsByDirector() throws Exception {
        int min = 4;
        String ratings = "ratings.csv";
        String movies = "ratedmoviesfull.csv";
        String director = "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack";
        
        MovieDatabase.initialize(movies);
        ThirdRatings tr = new ThirdRatings(ratings);
        System.out.println("There are " + MovieDatabase.size() + " movies.");
        System.out.println("There are " + tr.getRatersSize() + " raters.");
        Filter filter = new DirectorsFilter(director);
        
        ArrayList<Rating> qualRatings = tr.getAverageRatingsByFilter(min, filter);
        Collections.sort(qualRatings);
        System.out.println("There are " + qualRatings.size() + " movies with this many ratings.");
        
        for (int i = 0; i < qualRatings.size(); i++) {
            Rating r = qualRatings.get(i);
            System.out.println(r.getValue() + "\t" + (MovieDatabase.getTitle(r.getItem()) + "\t" + (MovieDatabase.getDirector(r.getItem()))));
        }
        
    }
    
    public void printAverageRatingsByMulitpleFilters() throws Exception {
        int min = 1;
        String ratings = "ratings.csv";
        String movies = "ratedmoviesfull.csv";
        
        MovieDatabase.initialize(movies);
        ThirdRatings tr = new ThirdRatings(ratings);
        System.out.println("There are " + MovieDatabase.size() + " movies.");
        System.out.println("There are " + tr.getRatersSize() + " raters.");
        
        Filter director = new DirectorsFilter("Spike Jonze");
        Filter minutes = new MinutesFilter(100, 170);
        Filter genre = new GenreFilter("Drama");
        Filter year = new YearAfterFilter(2000);
        Filter all = new AllFilters();
        AllFilters newall = (AllFilters) all;
        
        newall.addFilter(director);
        newall.addFilter(minutes);
        newall.addFilter(genre);
        newall.addFilter(year);
        
        
        ArrayList<Rating> qualRatings = tr.getAverageRatingsByFilter(min, newall);
        Collections.sort(qualRatings);
        System.out.println("There are " + qualRatings.size() + " movies with this many ratings.");
        
        for (int i = 0; i < qualRatings.size(); i++) {
            Rating r = qualRatings.get(i);
            System.out.println(r.getValue() + "\t" + (MovieDatabase.getTitle(r.getItem())) + "\t" + (MovieDatabase.getDirector(r.getItem())) + "\t" + (MovieDatabase.getYear(r.getItem()))+ "\t" + (MovieDatabase.getGenres(r.getItem()))+ "\t" + "Time: " + (MovieDatabase.getMinutes(r.getItem())));
        }
        
    }
    
    public void printAverageRatingsByYearAndGenre() throws Exception {
        int min = 8;
        String ratings = "C:\\git\\Coursera-Reccomendation-Capstone\\StepOneStarterProgram\\data\\ratings.csv";
        String movies = "C:\\git\\Coursera-Reccomendation-Capstone\\StepOneStarterProgram\\data\\ratedmoviesfull.csv";
        
        MovieDatabase.initialize(movies);
        ThirdRatings tr = new ThirdRatings(ratings);
        System.out.println("There are " + MovieDatabase.size() + " movies.");
        System.out.println("There are " + tr.getRatersSize() + " raters.");
        
        Filter genre = new GenreFilter("Drama");
        Filter year = new YearAfterFilter(1990);
        Filter all = new AllFilters();
        AllFilters newall = (AllFilters) all;
        
        newall.addFilter(genre);
        newall.addFilter(year);
        
        
        ArrayList<Rating> qualRatings = tr.getAverageRatingsByFilter(min, newall);
        Collections.sort(qualRatings);
        System.out.println("There are " + qualRatings.size() + " movies with this many ratings.");
        
        for (int i = 0; i < qualRatings.size(); i++) {
            Rating r = qualRatings.get(i);
            System.out.println(r.getValue() + "\t" + (MovieDatabase.getTitle(r.getItem())) + "\t" + (MovieDatabase.getYear(r.getItem()))+ "\t" + (MovieDatabase.getGenres(r.getItem())));
        }
        
    }
    
    public void printAverageRatingsByDirectorAndMinutes() throws Exception {
        int min = 1;
        String ratings = "ratings_short.csv";
        String movies = "ratedmoviesfull.csv";
        
        MovieDatabase.initialize(movies);
        ThirdRatings tr = new ThirdRatings(ratings);
        System.out.println("There are " + MovieDatabase.size() + " movies.");
        System.out.println("There are " + tr.getRatersSize() + " raters.");
        
        Filter director = new DirectorsFilter("Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack");
        Filter minutes = new MinutesFilter(90, 180);
        
        Filter all = new AllFilters();
        AllFilters newall = (AllFilters) all;
        
        newall.addFilter(director);
        newall.addFilter(minutes);
               
        
        ArrayList<Rating> qualRatings = tr.getAverageRatingsByFilter(min, newall);
        Collections.sort(qualRatings);
        System.out.println("There are " + qualRatings.size() + " movies with this many ratings.");
        
        for (int i = 0; i < qualRatings.size(); i++) {
            Rating r = qualRatings.get(i);
            System.out.println(r.getValue() + "\t" + (MovieDatabase.getTitle(r.getItem())) + "\t" + (MovieDatabase.getDirector(r.getItem())) + "\t" + (MovieDatabase.getYear(r.getItem()))+ "\t" + (MovieDatabase.getGenres(r.getItem()))+ "\t" + "Time: " + (MovieDatabase.getMinutes(r.getItem())));
        }
        
    }
}
