package com.example.demo2.pojo;

public class Ticket {
    private int ticket_id;
    private String start_time;
    private String end_time;



    private String play_name;
    private String price;
    private String image;
    private String code;


    private String cinemaHall_name;

    private int row;
    private int column;


    @Override
    public String toString() {
        return "Ticket{" +
                "ticket_id=" + ticket_id +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", play_name='" + play_name + '\'' +
                ", price='" + price + '\'' +
                ", image='" + image + '\'' +
                ", code='" + code + '\'' +
                ", cinemaHall_name='" + cinemaHall_name + '\'' +
                ", row=" + row +
                ", column=" + column +
                '}';
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getPlay_name() {
        return play_name;
    }

    public void setPlay_name(String play_name) {
        this.play_name = play_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCinemaHall_name() {
        return cinemaHall_name;
    }

    public void setCinemaHall_name(String cinemaHall_name) {
        this.cinemaHall_name = cinemaHall_name;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
