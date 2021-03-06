package com.example.demo2.service.impl;

import com.example.demo2.dao.ManagerMapper;
import com.example.demo2.pojo.CinemaHall;
import com.example.demo2.pojo.PerformancePlan;
import com.example.demo2.pojo.Play;
import com.example.demo2.pojo.User;
import com.example.demo2.service.ManagerService;
import com.example.demo2.vo.SaleSituation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerMapper managerMapper;
    @Override
    public int deleteUserById(String user_id) {
        return managerMapper.deleteUserById(user_id);
    }

    @Override
    public int addPlay(Play play) {
        return managerMapper.addPlay(play);
    }

    @Override
    public int deletePlayByName(String play_name) {
        return managerMapper.deletePlayByName(play_name);
    }

    @Override
    public int updatePlayById(Play play) {
        return managerMapper.updatePlayById(play);
    }

    @Override
    public int addCinemaHall(CinemaHall cinemaHall) {
        return managerMapper.insertCinemaHall(cinemaHall);
    }

    @Override
    public int deleteCinemaHall(int cinemaHall_id) {
        return managerMapper.deleteCinemaHallById(cinemaHall_id);
    }

    @Override
    public int updateCinemaHall(CinemaHall cinemaHall) {
        return managerMapper.updateCinemaHallById(cinemaHall);
    }

    @Override
    public List<User> selectAllUser() {
        List<User> userList=managerMapper.selectAllUser();
        for (User user:userList){
            user.setPassword(null);
        }
        return userList;
    }

    @Override
    public int updateUserById(User user) {
        return managerMapper.updateUserById(user);
    }

    @Override
    public int insertPerformancePlan(PerformancePlan performancePlan) {
        PerformancePlan p = setPerformancePlanTime(performancePlan);
        return managerMapper.insertPerformancePlan(p);
    }

    @Override
    public int updatePerformancePlanById(PerformancePlan performancePlan) {
        PerformancePlan p = setPerformancePlanTime(performancePlan);
        return managerMapper.updatePerformancePlanById(p);
    }

    @Override
    public int deletePerformancePlanById(PerformancePlan performancePlan) {
        return managerMapper.deletePerformancePlanById(performancePlan);
    }

    @Override
    public List<PerformancePlan> selectAllPerformancePlan() {
        List<PerformancePlan> performancePlanList=managerMapper.selectAllPerformancePlan();
        for (PerformancePlan performancePlan:performancePlanList){
            String start_time = performancePlan.getStart_time();
            String end_time = performancePlan.getEnd_time();

            String[] startTimes = start_time.split("\\.");
            String[] endTimes = end_time.split("\\.");


            performancePlan.setStart_year(Integer.parseInt(startTimes[0]));
            performancePlan.setStart_month(Integer.parseInt(startTimes[1]));
            performancePlan.setStart_day(Integer.parseInt(startTimes[2]));
            performancePlan.setStart_hour(Integer.parseInt(startTimes[3]));
            performancePlan.setStart_minute(Integer.parseInt(startTimes[4]));

            performancePlan.setEnd_year(Integer.parseInt(endTimes[0]));
            performancePlan.setEnd_month(Integer.parseInt(endTimes[1]));
            performancePlan.setEnd_day(Integer.parseInt(endTimes[2]));
            performancePlan.setEnd_hour(Integer.parseInt(endTimes[3]));
            performancePlan.setEnd_minute(Integer.parseInt(endTimes[4]));
        }
        return performancePlanList;
    }

    @Override
    public List<PerformancePlan> showPerformancePlanByConditions(String play_name, String cinemaHall_name) {
        List<PerformancePlan> resultList=new ArrayList<>();
        List<PerformancePlan> performancePlanList = managerMapper.selectAllPerformancePlan();
        for (PerformancePlan performancePlan:performancePlanList){
            int play_id = performancePlan.getPlay_id();
            int cinemaHall_id = performancePlan.getCinemaHall_id();
            CinemaHall cinemaHall = managerMapper.selectCinemaHallById(cinemaHall_id);
            Play play = managerMapper.selectPlayById(play_id);
            if (play_name == null || play_name == ""){
                if (cinemaHall_name == null || cinemaHall_name ==""){
                    return performancePlanList;
                }
                if (cinemaHall.getCinemaHall_name().contains(cinemaHall_name)){
                    resultList.add(performancePlan);
                }
            }else {
                if (cinemaHall_name == null || cinemaHall_name == ""){
                    if (play.getPlay_name().contains(play_name)){
                        resultList.add(performancePlan);
                    }
                }else {
                    if (play.getPlay_name().contains(play_name) && cinemaHall.getCinemaHall_name().contains(cinemaHall_name)){
                        resultList.add(performancePlan);
                    }
                }
            }
        }
        return resultList;
    }

    @Override
    public List<SaleSituation> showSaleSituation() {
        List<SaleSituation> saleSituationList=new ArrayList<>();

        List<PerformancePlan> performancePlanList = managerMapper.selectAllPerformancePlan();
        for (PerformancePlan performancePlan:performancePlanList){
            SaleSituation saleSituation=new SaleSituation();
            int play_id = performancePlan.getPlay_id();
            Play play = managerMapper.selectPlayById(play_id);
            saleSituation.setPlay_id(play_id);
            saleSituation.setPlay_name(play.getPlay_name());
            saleSituation.setType(play.getType());

            String seat = performancePlan.getSeat();
            if (seat == null || seat == ""){
                saleSituation.setSaleCount(0);
            }else {
                String[] seatNum = seat.split("/");
                saleSituation.setSaleCount(seatNum.length);
            }
            double price=Integer.parseInt(play.getPrice());
            saleSituation.setSaleVolume(saleSituation.getSaleCount()*price);
            saleSituationList.add(saleSituation);
        }
        return saleSituationList;
    }

    @Override
    public List<User> showUserByConditions(String user_name, int position, String phone) {
        return managerMapper.selectUserByConditions(user_name,position,phone);
    }

    private PerformancePlan setPerformancePlanTime(PerformancePlan performancePlan) {
        int start_year=performancePlan.getStart_year();
        int start_month = performancePlan.getStart_month();
        int start_day = performancePlan.getStart_day();
        int start_hour = performancePlan.getStart_hour();
        int start_minute = performancePlan.getStart_minute();

        int end_year = performancePlan.getEnd_year();
        int end_month = performancePlan.getEnd_month();
        int end_day = performancePlan.getEnd_day();
        int end_hour = performancePlan.getEnd_hour();
        int end_minute = performancePlan.getEnd_minute();


        String start_time = start_year+"."+
                start_month+"."+
                start_day+"."+
                start_hour+"."+
                start_minute;
        String end_time = end_year+"."+
                end_month+"."+
                end_day+"."+
                end_hour+"."+
                end_minute;
        performancePlan.setStart_time(start_time);
        performancePlan.setEnd_time(end_time);
        return performancePlan;
    }

}
