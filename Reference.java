package reference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import reference.comparator.FilmComparator;
import reference.domain.Film;
import reference.domain.Person;
import reference.domain.Rating;

public class Reference {

    private RatingRegister ratingregister;
    //private Map<Film, List<Rating>> filmsAndTheirRatings;
    //private List<Film> listOfFilms;
    //private List<Person> peopleReviewedFilms;

    public Reference(RatingRegister ratingRegister) {
        this.ratingregister = ratingRegister;
        //this.filmsAndTheirRatings = this.ratingregister.filmRatings();
        //this.listOfFilms = listOfFilms();
        //this.peopleReviewedFilms = this.ratingregister.reviewers();
    }

    public Film recommendFilm(Person person) {
        List<Film> listOfFilms = listOfFilms();
        List<Person> peopleReviewedFilms = this.ratingregister.reviewers();
        Map<Film, List<Rating>> filmsAndTheirRatings = this.ratingregister.filmRatings();
        if (!peopleReviewedFilms.contains(person)) {
            Collections.sort(listOfFilms, new FilmComparator(filmsAndTheirRatings));
            return listOfFilms.get(0);
        }
        Person personWithMostSimilarity = PersonWithMostSimilarity(person);
        System.out.println(person + " most similar:" + personWithMostSimilarity);
        Map<Film, Rating> personToReccomendRatings = this.ratingregister.getPersonalRatings(person);
        Map<Film, Rating> personWithMostSimilarityRatings = this.ratingregister.getPersonalRatings(personWithMostSimilarity);
        List<Film> personToReccomendFilmsWatched = new ArrayList<Film>();
        List<Film> personWithMostSimilarityFilmsWatched = new ArrayList<Film>();

        for (Film film : personToReccomendRatings.keySet()) {
            personToReccomendFilmsWatched.add(film);
        }

        for (Film film : personWithMostSimilarityRatings.keySet()) {
            personWithMostSimilarityFilmsWatched.add(film);
        }

        List<Film> filmsToReccomend = new ArrayList<Film>();

        for (Film film : personWithMostSimilarityFilmsWatched) {
            if (!personToReccomendFilmsWatched.contains(film)) {
                filmsToReccomend.add(film);
            }

        }

        Film highestRatedFilm;
        if (filmsToReccomend.isEmpty()) {
            highestRatedFilm = null;
        } else {
            highestRatedFilm = filmsToReccomend.get(0);
        }
        for (int i = 0; i < filmsToReccomend.size(); i++) {
            if(highestRatedFilm == null) {
                break;
            }
            Film film = filmsToReccomend.get(i);
            if (personWithMostSimilarityRatings.get(film).getValue() > personWithMostSimilarityRatings.get(highestRatedFilm).getValue()) {
                highestRatedFilm = film;
            }

        }

        return highestRatedFilm;
    }

    public List<Film> listOfFilms() {
        List<Person> peopleReviewedFilms = this.ratingregister.reviewers();
        Map<Film, List<Rating>> filmsAndTheirRatings = this.ratingregister.filmRatings();
        List<Film> tempList = new ArrayList<Film>();
        Set<Film> setOfFilms = filmsAndTheirRatings.keySet();

        Iterator<Film> iterator = setOfFilms.iterator();

        while (iterator.hasNext()) {
            tempList.add(iterator.next());
        }
        return tempList;
    }

    public Person PersonWithMostSimilarity(Person person) {
        List<Film> listOfFilms = listOfFilms();
        List<Person> peopleReviewedFilms = this.ratingregister.reviewers();
        Map<Film, List<Rating>> filmsAndTheirRatings = this.ratingregister.filmRatings();
        Person personMostSimilarity = null;
        int similarity = 0;
        int greatestSimilarity = 0;

        for (Person eachPerson : peopleReviewedFilms) {
            if (person.equals(eachPerson)) {
                continue;
            }
            similarity = calculateSimilarity(person, eachPerson);
            if (similarity > greatestSimilarity) {
                personMostSimilarity = eachPerson;
            }
        }

        return personMostSimilarity;

    }

    public int calculateSimilarity(Person person1, Person person2) {
        List<Film> listOfFilms = listOfFilms();
        List<Person> peopleReviewedFilms = this.ratingregister.reviewers();
        Map<Film, List<Rating>> filmsAndTheirRatings = this.ratingregister.filmRatings();
        int similarity = 0;
        Map<Film, Rating> person1Ratings = this.ratingregister.getPersonalRatings(person1);
        Map<Film, Rating> person2Ratings = this.ratingregister.getPersonalRatings(person2);

        for (Film film : person1Ratings.keySet()) {
            if (person2Ratings.containsKey(film)) {
                similarity += this.ratingregister.getRating(person1, film).getValue()
                        * this.ratingregister.getRating(person2, film).getValue();
            }
        }

        return similarity;
    }
}
