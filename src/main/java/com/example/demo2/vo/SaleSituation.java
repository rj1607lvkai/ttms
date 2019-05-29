package com.example.demo2.vo;

public class SaleSituation {
    private int play_id;
    private String play_name;
    private String type;
    private int saleCount;
    private double saleVolume;


    @Override
    public String toString() {
        return "SaleSituation{" +
                "play_id=" + play_id +
                ", play_name='" + play_name + '\'' +
                ", type='" + type + '\'' +
                ", saleCount=" + saleCount +
                ", saleVolume=" + saleVolume +
                '}';
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public double getSaleVolume() {
        return saleVolume;
    }

    public void setSaleVolume(double saleVolume) {
        this.saleVolume = saleVolume;
    }
}
