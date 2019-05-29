package com.example.demo2.service;

import com.example.demo2.pojo.CinemaHall;
import com.example.demo2.pojo.PerformancePlan;
import com.example.demo2.pojo.Play;
import com.example.demo2.pojo.User;
import com.example.demo2.vo.SaleSituation;

import java.util.List;

public interface ManagerService {
    int deleteUserById(String user_id);

    int addPlay(Play play);

    int deletePlayByName(String play_name);

    int updatePlayById(Play play);

    int addCinemaHall(CinemaHall cinemaHall);

    int deleteCinemaHall(int cinemaHall_id);


    int updateCinemaHall(CinemaHall cinemaHall);

    List<User> selectAllUser();

    int updateUserById(User user);

    int insertPerformancePlan(PerformancePlan performancePlan);

    int updatePerformancePlanById(PerformancePlan performancePlan);

    int deletePerformancePlanById(PerformancePlan performancePlan);

    List<PerformancePlan> selectAllPerformancePlan();

    List<PerformancePlan> showPerformancePlanByConditions(String play_name, String cinemaHall_name);

    List<SaleSituation> showSaleSituation();

    List<User> showUserByConditions(String user_name, int position, String phone);
}
