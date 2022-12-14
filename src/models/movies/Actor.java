package models.movies;

import java.util.Comparator;

/**
 *  The Actor class representing the actor record with its attributes, methods and construction.
 *
 * @author Kristiyan Cholakov (KrisCholakov02)
 * @version 10/11/22
 */
public class Actor extends Director {

    /**
     * An attribute to hold the number of oscars the actor has.
     */
    private int numberOfOscars;

    /**
     * The Actor constructor to create a new actor record.
     *
     * @param fName The first name of the actor.
     * @param lName The last name of the actor.
     * @param numberOfMovies The number of movies from the storage the actor has acted in.
     * @param numberOfOscars The number of oscars the actor has.
     */
    public Actor(String fName, String lName, int numberOfMovies, int numberOfOscars) {
        super(fName, lName, numberOfMovies);
        this.numberOfOscars = numberOfOscars;
    }

    /**
     * The getNumberOfOscars method gets the number of oscars the actor has.
     *
     * @return The number of oscars the actor has.
     */
    public int getNumberOfOscars() {
        return numberOfOscars;
    }

    /**
     * The ByNumberOfOscars class is used for sorting the actors by the number of oscars in descending order.
     */
    public static class ByNumberOfOscars implements Comparator<Actor> {

        /**
         * Custom compare method to compare 2 actors based on their number of oscars.
         *
         * @param o1 the first director to be compared.
         * @param o2 the second director to be compared.
         * @return Negative integer if second actor has less oscars than the first one. Positive if the more. 0 when equal.
         */
        @Override
        public int compare(Actor o1, Actor o2) {
            return o2.getNumberOfOscars() - o1.getNumberOfOscars();
        }
    }

    /**
     * Custom toString method to get the text representation of the actor.
     *
     * @return String representing the actor record with its attributes.
     */
    @Override
    public String toString() {
        return "Actor:\n" +
                "F_Name=" + super.getFName() + "\n" +
                "L_Name=" + super.getLName() + "\n" +
                "NumberOfOscars=" + getNumberOfOscars() + "\n"+
                "NumberOfMovies=" + super.getNumberOfMovies();
    }
}
