package com.example.demo2.pojo;

public class CinemaHall {
    private int cinemaHall_id;
    private String cinemaHall_name;
    private int cinemaHall_row;
    private int cinemaHall_column;
    private String image;

    @Override
    public String toString() {
        return "CinemaHall{" +
                "cinemaHall_id=" + cinemaHall_id +
                ", cinemaHall_name='" + cinemaHall_name + '\'' +
                ", cinemaHall_row=" + cinemaHall_row +
                ", cinemaHall_column=" + cinemaHall_column +
                ", image='" + image + '\'' +
                '}';
    }

    public int getCinemaHall_id() {
        return cinemaHall_id;
    }

    public void setCinemaHall_id(int cinemaHall_id) {
        this.cinemaHall_id = cinemaHall_id;
    }

    public String getCinemaHall_name() {
        return cinemaHall_name;
    }

    public void setCinemaHall_name(String cinemaHall_name) {
        this.cinemaHall_name = cinemaHall_name;
    }

    public int getCinemaHall_row() {
        return cinemaHall_row;
    }

    public void setCinemaHall_row(int cinemaHall_row) {
        this.cinemaHall_row = cinemaHall_row;
    }

    public int getCinemaHall_column() {
        return cinemaHall_column;
    }

    public void setCinemaHall_column(int cinemaHall_column) {
        this.cinemaHall_column = cinemaHall_column;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
