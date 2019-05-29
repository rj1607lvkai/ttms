package com.example.demo2.pojo;

import java.io.Serializable;

public class PerformancePlan implements Serializable {
    private int performancePlan_id;
    private int play_id;
    private int cinemaHall_id;
    private String start_time;
    private String end_time;


    private int start_year;
    private int start_month;
    private int start_day;
    private int start_hour;
    private int start_minute;

    private int end_year;
    private int end_month;
    private int end_day;
    private int end_hour;
    private int end_minute;

    private String seat;


    @Override
    public String toString() {
        return "PerformancePlan{" +
                "performancePlan_id=" + performancePlan_id +
                ", play_id=" + play_id +
                ", cinemaHall_id=" + cinemaHall_id +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", start_year=" + start_year +
                ", start_month=" + start_month +
                ", start_day=" + start_day +
                ", start_hour=" + start_hour +
                ", start_minute=" + start_minute +
                ", end_year=" + end_year +
                ", end_month=" + end_month +
                ", end_day=" + end_day +
                ", end_hour=" + end_hour +
                ", end_minute=" + end_minute +
                ", seat='" + seat + '\'' +
                '}';
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }



    public int getPerformancePlan_id() {
        return performancePlan_id;
    }

    public void setPerformancePlan_id(int performancePlan_id) {
        this.performancePlan_id = performancePlan_id;
    }

    public int getPlay_id() {
        return play_id;
    }

    public void setPlay_id(int play_id) {
        this.play_id = play_id;
    }

    public int getCinemaHall_id() {
        return cinemaHall_id;
    }

    public void setCinemaHall_id(int cinemaHall_id) {
        this.cinemaHall_id = cinemaHall_id;
    }

    public int getStart_year() {
        return start_year;
    }

    public void setStart_year(int start_year) {
        this.start_year = start_year;
    }

    public int getStart_month() {
        return start_month;
    }

    public void setStart_month(int start_month) {
        this.start_month = start_month;
    }

    public int getStart_day() {
        return start_day;
    }

    public void setStart_day(int start_day) {
        this.start_day = start_day;
    }

    public int getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(int start_hour) {
        this.start_hour = start_hour;
    }

    public int getStart_minute() {
        return start_minute;
    }

    public void setStart_minute(int start_minute) {
        this.start_minute = start_minute;
    }

    public int getEnd_year() {
        return end_year;
    }

    public void setEnd_year(int end_year) {
        this.end_year = end_year;
    }

    public int getEnd_month() {
        return end_month;
    }

    public void setEnd_month(int end_month) {
        this.end_month = end_month;
    }

    public int getEnd_day() {
        return end_day;
    }

    public void setEnd_day(int end_day) {
        this.end_day = end_day;
    }

    public int getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(int end_hour) {
        this.end_hour = end_hour;
    }

    public int getEnd_minute() {
        return end_minute;
    }

    public void setEnd_minute(int end_minute) {
        this.end_minute = end_minute;
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
}