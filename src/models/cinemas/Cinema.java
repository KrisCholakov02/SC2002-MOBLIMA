package models.cinemas;

public class Cinema {
    int number;
    CinemaEnums.CinemaType type;

    public Cinema(int number, CinemaEnums.CinemaType type) {
        this.number = number;
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public CinemaEnums.CinemaType getType() {
        return type;
    }
}
