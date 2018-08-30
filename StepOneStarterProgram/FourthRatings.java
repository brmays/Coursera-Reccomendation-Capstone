
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
        ArrayList<Rater> myRaters = RaterDatabase.getRaters();
        for (Rater r : myRaters) {
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
    
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters) {
        ArrayList<Rating> recMovies = new ArrayList<Rating>();
        ArrayList<Rating> similarRaters = getSimilarities(id);
        System.out.println("Locations FourthRatings.getSimilarRatings, similarRaters size: " + similarRaters.size());
        /*for (int i = 0; i < numSimilarRaters +1; i++) {
            Rating r = similarRaters.get(i);
            System.out.println("SAA, RaterID: " + r.getItem() + "  score: " + r.getValue());
        }*/
        
        Rater me = RaterDatabase.getRater(id);
        ArrayList<String> myRatedMovies = me.getItemsRated();
        HashMap<String, ArrayList<Double>> movies = new HashMap<String, ArrayList<Double>>();
        ArrayList<Double> ratingsList = new ArrayList<Double>();
        int counter = 0;
        for (int i = 0; i < numSimilarRaters; i++) {
            Rater curRater = RaterDatabase.getRater(similarRaters.get(i).getItem());
            ArrayList<String> curRatedMovies = curRater.getItemsRated();
            //System.out.println("Printing out movie ids for rater: " + curRater.getID());
            //System.out.println(curRatedMovies.size());
            /*for (String s : curRatedMovies) {
                System.out.println(s);
            }*/
            for (String myMovie : myRatedMovies) {
                if (curRatedMovies.contains(myMovie)) {
                    if (myMovie=="2582846") {
                        counter++;
                    }
                    if (movies.containsKey(myMovie)) {
                        ArrayList<Double> curList = movies.get(myMovie);
                        curList.add(curRater.getRating(myMovie) * similarRaters.get(i).getValue());
                        movies.put(myMovie, curList);
                    } else {
                        ArrayList<Double> curList = new ArrayList<Double>();
                        curList.add(curRater.getRating(myMovie) * similarRaters.get(i).getValue());
                        movies.put(myMovie, curList);
                    }
                    
                }
            }
        }
        
        for (String movie : movies.keySet()) {
            ArrayList<Double> ratingList = movies.get(movie);
            int count = ratingList.size() + 1;
            if (count >= minimalRaters) {
                double total = 0.0;
                for (double cur : ratingList) {
                    total += cur;
                }
                Rating curRating = new Rating(movie, (total/count));
                recMovies.add(curRating);
            }
        
        }
        Collections.sort(recMovies);
        Collections.reverse(recMovies);
        System.out.println("Found that movie " + counter);
        return recMovies;
    }
    
    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters, Filter filterCriteria) {
        ArrayList<String> afterFilter = MovieDatabase.filterBy(filterCriteria);
        ArrayList<Rating> recMovies = new ArrayList<Rating>();
        ArrayList<Rating> similarRaters = getSimilarities(id);
        ArrayList<String> myRatedMovies = new EfficientRater(id).getItemsRated();
        HashMap<String, ArrayList<Double>> movies = new HashMap<String, ArrayList<Double>>();
        ArrayList<Double> ratingsList = new ArrayList<Double>();
        EfficientRater me = new EfficientRater(id);
        
        for (int i = 0; i < numSimilarRaters; i++) {
            EfficientRater curRater = new EfficientRater(similarRaters.get(i).getItem());
            ArrayList<String> curRatedMovies = curRater.getItemsRated();
            for (String myMovie : myRatedMovies) {
                if (curRatedMovies.contains(myMovie) && afterFilter.contains(myMovie)) {
                    if (movies.containsKey(myMovie)) {
                        ArrayList<Double> curList = movies.get(myMovie);
                        curList.add(curRater.getRating(myMovie) * similarRaters.get(i).getValue());
                        movies.put(myMovie, curList);
                    } else {
                        ArrayList<Double> curList = new ArrayList<Double>();
                        curList.add(curRater.getRating(myMovie) * similarRaters.get(i).getValue());
                        movies.put(myMovie, curList);
                    }
                    
                }
            }
        }
        
        for (String movie : movies.keySet()) {
            ArrayList<Double> ratingList = movies.get(movie);
            int count = ratingList.size() + 1;
            if (count >= minimalRaters) {
                double total = 0.0;
                for (double cur : ratingList) {
                    total += cur;
                }
                Rating curRating = new Rating(movie, (total/count));
                recMovies.add(curRating);
            }
        
        }
        Collections.sort(recMovies);
        
        return recMovies;
    }
    
    private ArrayList<Rating> getSimilarities(String id) {
        ArrayList<Rating> similarRaters = new ArrayList<Rating>();
        ArrayList<Rater> raters = RaterDatabase.getRaters();
        
        Rater me = RaterDatabase.getRater(id);
        
        for (Rater r : raters) {
            String curRater = r.getID();
            if (!curRater.equals(id)) {
                int sim = dotProduct(me, r);
                if (sim >= 0) {
                    Rating newRating = new Rating(curRater, (double) sim);
                    similarRaters.add(newRating);
                }
            }
        }
        
        
        Collections.sort(similarRaters);
        Collections.reverse(similarRaters);
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
