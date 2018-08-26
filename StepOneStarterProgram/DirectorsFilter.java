
/**
 * Write a description of DirectorsFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;

public class DirectorsFilter implements Filter{
    private String[] myDirector;
    
    public DirectorsFilter(String director) {
        myDirector = director.split(",");
    }
    
    @Override
    public boolean satisfies(String id) {
        for (int i = 0; i <myDirector.length; i++) {
            if (MovieDatabase.getDirector(id).contains(myDirector[i])) {
                return true;
            }
        }
        
        return false;
    }
}
