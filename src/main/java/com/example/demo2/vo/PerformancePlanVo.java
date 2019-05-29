package com.example.demo2.vo;

public class PerformancePlanVo {
    private int performancePlan_id;
    private String start_time;
    private String end_time;


    private int play_id;
    private String play_name;
    private String price;
    private String image;
    private String code;


    private int cinemaHall_id;
    private String cinemaHall_name;


    @Override
    public String toString() {
        return "PerformancePlanVo{" +
                "performancePlan_id=" + performancePlan_id +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", play_id=" + play_id +
                ", play_name='" + play_name + '\'' +
                ", price='" + price + '\'' +
                ", image='" + image + '\'' +
                ", code='" + code + '\'' +
                ", cinemaHall_id=" + cinemaHall_id +
                ", cinemaHall_name='" + cinemaHall_name + '\'' +
                '}';
    }

    public int getPerformancePlan_id() {
        return performancePlan_id;
    }

    public void setPerformancePlan_id(int performancePlan_id) {
        this.performancePlan_id = performancePlan_id;
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

    public int getPlay_id() {
        return play_id;
    }

    public void setPlay_id(int play_id) {
        this.play_id = play_id;
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
}
