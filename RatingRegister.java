
package reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import reference.domain.Film;
import reference.domain.Person;
import reference.domain.Rating;

public class RatingRegister {
    private Map<Film, List<Rating>> filmRatings;
    private Map<Person, Map<Film, Rating>> personRatings;
    
    public RatingRegister() {
        this.filmRatings = new HashMap<Film, List<Rating>>();
        this.personRatings = new HashMap<Person, Map<Film, Rating>>();
    }
    
    public void addRating(Film film, Rating rating) {
        if(this.filmRatings.containsKey(film)) {
            this.filmRatings.get(film).add(rating);
        }
        else {
            this.filmRatings.put(film, new ArrayList<Rating>());
            this.filmRatings.get(film).add(rating);
        }
    }
    
    public List<Rating> getRatings(Film film) {
        return this.filmRatings.get(film);
    }
    
    public Map<Film, List<Rating>> filmRatings() {
        return this.filmRatings;
    }
    
    public void addRating(Person person, Film film, Rating rating) {
        if(this.personRatings.containsKey(person)) {
            this.personRatings.get(person).put(film, rating);
            addRating(film, rating);
        }
        else {
            this.personRatings.put(person, new HashMap<Film, Rating>());
            this.personRatings.get(person).put(film, rating);
            addRating(film, rating);
        }
    }
    
    public Rating getRating(Person person, Film film) {
        if(this.personRatings.containsKey(person)) {
            if(this.personRatings.get(person).containsKey(film)) {
                return this.personRatings.get(person).get(film);
            }
        }
        return Rating.NOT_WATCHED;
    }
    
    public Map<Film, Rating> getPersonalRatings(Person person) {
        if(!this.personRatings.containsKey(person)) {
            return new HashMap<Film, Rating>();
        }
        return this.personRatings.get(person);
    }
    
    public List<Person> reviewers() {
        ArrayList<Person> reviewed = new ArrayList<Person>();
        for(Person person : this.personRatings.keySet()) {
            reviewed.add(person);
        }
        
        return reviewed;
    }
}
