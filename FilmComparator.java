
package reference.comparator;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import reference.domain.Film;
import reference.domain.Rating;

public class FilmComparator implements Comparator<Film> {

    private Map<Film, List<Rating>> ratings;
    
    public FilmComparator(Map<Film, List<Rating>> ratings) {
        this.ratings = ratings;
    }
    
    public int average(List<Rating> listOfRatings) {
        int sum = 0;
        
        for(Rating rating : listOfRatings) {
            sum += rating.getValue();
        }
        return sum / listOfRatings.size();
    }
    
    @Override
    public int compare(Film t, Film t1) {
        if(average(this.ratings.get(t)) > average(this.ratings.get(t1))) {
            return -1;
        }
        if(average(this.ratings.get(t)) < average(this.ratings.get(t1))) {
            return 1;
        }
        else return 0;
    }
    
}
