
/**
 * Write a description of FirstRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
import java.io.*;

public class FirstRatings {
    public ArrayList<Movie> loadMovies(String filename) throws Exception{
        String [] FILE_HEADER_MAPPING = {"id","title","year","country","genre", "director", "minutes", "poster"};
        FileReader fileReader = null;
        CSVParser parser = null;
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);
        ArrayList<Movie> movies = new ArrayList<Movie>();
        fileReader = new FileReader(filename);
        parser = new CSVParser(fileReader, csvFileFormat);
        List<CSVRecord> csvRecords = parser.getRecords();
        
        for (int i = 1; i < csvRecords.size(); i++) {
            CSVRecord record = csvRecords.get(i);
            String ID = record.get("id");
            String title = record.get("title");
            String year = record.get("year");
            String genre = record.get("genre"); 
            String director = record.get("director");
            String country = record.get("country");
            String poster = record.get("poster");
            int minutes = Integer.parseInt(record.get("minutes"));
            Movie movie = new Movie(ID, title, year, genre, director, country, poster, minutes);
            movies.add(movie);
        }
        
        return movies;
    }
    
    public HashMap<String, EfficientRater> loadRater(String filename) throws Exception {
        //ArrayList<Rater> raters = new ArrayList<Rater>();
        String [] FILE_HEADER_MAPPING = {"rater_id","movie_id","rating","time"};
        FileReader fileReader = null;
        CSVParser parser = null;
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);
        fileReader = new FileReader(filename);
        parser = new CSVParser(fileReader, csvFileFormat);
        List<CSVRecord> csvRecords = parser.getRecords();
        HashMap<String, EfficientRater> raters = new HashMap<String, EfficientRater>();
        
        for (int i = 1; i < csvRecords.size(); i++) {
            CSVRecord record = csvRecords.get(i);
            String raterID = record.get("rater_id");
            String movieID = record.get("movie_id");
            Double rating = Double.parseDouble(record.get("rating"));
            EfficientRater rater = null;
            if (!raters.containsKey(raterID)) {
                rater = new EfficientRater(raterID);
                rater.addRating(movieID, rating);
                raters.put(raterID, rater);
            } else {
               rater = raters.get(raterID);
               rater.addRating(movieID, rating);
               raters.put(raterID, rater);
            }
        }
        
        return raters;
    }
    
    public void testLoadRater() throws Exception{
        String fileName = "ratings.csv";
        HashMap<String, EfficientRater> raters = loadRater(fileName);  
        System.out.println("There werw " + raters.size() + " raters.");
        String specRater = "193";
        ArrayList<String> maxRaters = maxRatings(raters);
        String specMovie = "1798709";
        int movRatings = 0;
        HashSet<String> uniqueMovs = new HashSet<String>();
        
        for (Rater r : raters.values()) {
            //Rater r = itr.next();
            //System.out.println(r.getID() + " had " + r.numRatings() + " ratings.");
            ArrayList<String> movies = r.getItemsRated();
            if (movies.contains(specMovie)) {
                movRatings++;
            }
            
            Iterator<String> movs = movies.iterator();
            while (movs.hasNext()) {
                String m = movs.next();
                uniqueMovs.add(m);
            }
        }
        
        Rater specRecord = raters.get(specRater);
        int numOfRatings = specRecord.numRatings();
        System.out.println("The rater you specified, " + specRater + ", provided " + numOfRatings + " ratings.");
        
        System.out.println("There were " + (maxRaters.size() - 1) + " raters with " + (maxRaters.get(0)) + " and they were: ");
        for (int i = 1; i < maxRaters.size(); i++) {
            System.out.println("\t" + maxRaters.get(i));
        }
        
        System.out.println("The movie " + specMovie + " was rated " + movRatings + " times.");
        System.out.println("There were " + uniqueMovs.size() + " movies rated.");
    }
    
    private ArrayList<String> maxRatings(HashMap<String, EfficientRater> map) {
        ArrayList<String> maxRaters = null;
        int max = 0;
        
        for (Rater r : map.values()) {
            int cur = r.numRatings();
            if (cur > max) {
                maxRaters = new ArrayList<String>();
                maxRaters.add(Integer.toString(cur));
                maxRaters.add(r.getID());
                max = cur;
            } else if (cur == max) {
                maxRaters.add(r.getID());
            }
        }
        
        
        return maxRaters;
    }
    
    
    public void testLoadMovies() throws Exception{
            String fileName = "ratedmoviesfull.csv";
            ArrayList<Movie> movies = loadMovies(fileName);  
            System.out.println(movies.size());
            int comedyCount = 0;
            int longMovie = 0;
            String popDirector = "All directors are even";
            int popDirCount = 1;
            HashMap<String, Integer> dirList = new HashMap<String, Integer>();
            
            for (int i = 0; i < movies.size(); i++) {
                Movie curMovie = movies.get(i);
                //System.out.println("question 1" + curMovie);
                if (curMovie.getGenres().contains("Comedy")) {
                    comedyCount++;
                }
                
                if (curMovie.getMinutes() > 150) {
                    longMovie++;
                }
                
                String[] directors = curMovie.getDirector().split(",\\s");
                for (int k = 0; k < directors.length; k++) {
                    if (!dirList.containsKey(directors[k])) {
                        dirList.put(directors[k], 1);
                    } else {
                        int count = dirList.get(directors[k]) + 1;
                        dirList.put(directors[k], count);
                        if (count > popDirCount) {
                            popDirCount = count;
                            popDirector = directors[k];
                        }
                    }
                }
            }
        
            System.out.println("There are " + comedyCount + " comedies in this list.");
            System.out.println("There are " + longMovie + " movies longer than 150 minutes.");
            System.out.println("The most movies any one director directed was " + popDirCount + " . Director: " + popDirector);
    }
}

