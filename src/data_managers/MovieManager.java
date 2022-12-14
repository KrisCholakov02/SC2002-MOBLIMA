package data_managers;

import models.cinemas.Cineplex;
import models.cinemas.ShowTime;
import models.movies.Movie;
import models.movies.Review;
import pages.MainPage;
import pages.PageElements;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The MovieManager class takes care of interacting with the file where the movies are stored.
 * It performs writing, updating, reading and searching in the movies' storage.
 *
 * @author Kristiyan Cholakov (KrisCholakov02)
 * @version 10/11/22
 */
public class MovieManager {

    /**
     * The MOVIES_PATH constant points to the file where movies are stored.
     */
    private static final String MOVIES_PATH = "src/data/movies.txt";

    /**
     * The readMovies function reads all movies records from the movies' storage.
     *
     * @return An array list of the movies records from the storage. Empty array list if not any.
     */
    public static ArrayList<Movie> readMovies () {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(MOVIES_PATH));
            ArrayList<Movie> movies = (ArrayList<Movie>) ois.readObject();
            ois.close();
            return movies;
        } catch (FileNotFoundException e) {
            PageElements.printConsoleMessage("Error: Invalid Path! Movies can't be read!");
        } catch (EOFException e) {
        } catch (IOException | ClassNotFoundException e) {
            PageElements.printConsoleMessage("Error!");
        }
        return new ArrayList<Movie>();
    }

    /**
     * The writeMovie function adds a movie record to the movies' storage.
     *
     * @param movie The movie record to be written in the storage.
     * @return true if the movie record is added successfully. false if writing failed.
     */
    public static boolean writeMovie (Movie movie) {
        File file = new File(MOVIES_PATH);
        ArrayList<Movie> allMovies = readMovies();
        allMovies.add(movie);
        if(file.exists()) file.delete();
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(MOVIES_PATH));
            out.writeObject(allMovies);
            out.flush();
            out.close();
            PageElements.printConsoleMessage("Movie Added!");
            return true;
        } catch (IOException e) {
            PageElements.printConsoleMessage("Error: Invalid Path! Movie is not saved to the database.");
            return false;
        }
    }

    /**
     * The getMovie function searches for a movie with the same title in the storage and provides their record.
     *
     * @param title The title of the movie that we search
     * @return The record of the movie with the same title. null if a movie with this title does not exist.
     */
    public static Movie getMovie(String title) {
        ArrayList<Movie> allMovies = readMovies();
        for (int i = 0; i < allMovies.size(); i++) {
            Movie currentMovie = allMovies.get(i);
            if (currentMovie.getTitle().equals(title)) {
                return currentMovie;
            }
        }
        return null;
    }

    /**
     * The updateMovie function searches if the provided movie record exists and if so updates their entry.
     *
     * @param movie The movie record to be updated in the storage.
     * @return true if the movie record is updated successfully. false if updating failed.
     */
    public static boolean updateMovie (Movie movie) {
        ArrayList<Movie> allMovies = readMovies();
        Movie movieToUpdated = getMovie(movie.getTitle());
        if (movieToUpdated == null) {
            PageElements.printConsoleMessage("No such movie!");
            return false;
        } else {
            ArrayList<Cineplex> cineplexes = CineplexManager.readCineplexes();
            for (int i=0; i < cineplexes.size(); i++) {
                Cineplex cineplex = cineplexes.get(i);
                ArrayList<LocalDate> scheduleDates = new ArrayList<>();
                scheduleDates.addAll(cineplex.getSchedules().keySet());
                for (int j = 0; j < scheduleDates.size(); j++) {
                    ArrayList<ShowTime> showTimes = cineplex.getSchedules().get(scheduleDates.get(j));
                    for (int z = 0; z < showTimes.size(); z++) {
                        ShowTime showTime = showTimes.get(z);
                        if (showTime.getMovie().equals(movieToUpdated)) {
                            showTimes.get(z).setMovieTitle(movie.getTitle());
                            HashMap<LocalDate, ArrayList<ShowTime>> schedules = cineplex.getSchedules();
                            schedules.put(scheduleDates.get(j), showTimes);
                            cineplex.setSchedules(schedules);
                            CineplexManager.updateCineplex(cineplex);
                        }
                    }
                }
            }
            for (int i = 0; i < movie.getReviews().size(); i++) {
                movie.getReviews().get(i).setMovie(movie);
            }
            allMovies.remove(movieToUpdated);
            allMovies.add(movie);
            File file = new File(MOVIES_PATH);
            if(file.exists()) file.delete();
        }
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(MOVIES_PATH));
            out.writeObject(allMovies);
            out.flush();
            out.close();
            if (MainPage.getCurrentUser() == null) PageElements.printConsoleMessage("Movie Updated!");
            return true;
        } catch (IOException e) {
            PageElements.printConsoleMessage("Error: Invalid Path! Movie is not updated to the database.");
            return false;
        }
    }

    /**
     * The deleteMovie function searches if the provided movie record exists and if so deletes their entry.
     *
     * @param movie The movie record to be deleted in the storage.
     * @return true if the movie record is deleted successfully. false if deletion failed.
     */
    public static boolean deleteMovie(Movie movie) {
        ArrayList<Movie> allMovies = readMovies();
        Movie movieToUpdated = getMovie(movie.getTitle());
        if (movieToUpdated == null) {
            PageElements.printConsoleMessage("No such movie!");
            return false;
        } else {
            ArrayList<Cineplex> cineplexes = CineplexManager.readCineplexes();
            for (int i=0; i < cineplexes.size(); i++) {
                Cineplex cineplex = cineplexes.get(i);
                ArrayList<LocalDate> scheduleDates = new ArrayList<>();
                scheduleDates.addAll(cineplex.getSchedules().keySet());
                for (int j = 0; j < scheduleDates.size(); j++) {
                    ArrayList<ShowTime> showTimes = cineplex.getSchedules().get(scheduleDates.get(j));
                    for (int z = 0; z < showTimes.size(); z++) {
                        ShowTime showTime =  showTimes.get(z);
                        if (showTime.getMovie().equals(movieToUpdated)) {
                            showTimes.remove(z);
                            HashMap<LocalDate, ArrayList<ShowTime>> schedules = cineplex.getSchedules();
                            schedules.put(scheduleDates.get(j), showTimes);
                            cineplex.setSchedules(schedules);
                            CineplexManager.updateCineplex(cineplex);
                        }
                    }
                }
            }
            allMovies.remove(movieToUpdated);
            File file = new File(MOVIES_PATH);
            if(file.exists()) file.delete();
        }
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(MOVIES_PATH));
            out.writeObject(allMovies);
            out.flush();
            out.close();
            PageElements.printConsoleMessage("Movie Deleted!");
            return true;
        } catch (IOException e) {
            PageElements.printConsoleMessage("Error: Invalid Path! Movie is not deleted from the database.");
            return false;
        }
    }
}
