package com.example.demo2.service.impl;

import com.example.demo2.dao.ManagerMapper;
import com.example.demo2.pojo.CinemaHall;
import com.example.demo2.pojo.PerformancePlan;
import com.example.demo2.pojo.Play;
import com.example.demo2.pojo.User;
import com.example.demo2.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
