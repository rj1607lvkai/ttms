package com.example.demo2.service;

import com.example.demo2.pojo.*;
import com.example.demo2.vo.PerformancePlanDetailsVo;
import com.example.demo2.vo.PerformancePlanVo;

import java.util.List;

public interface UserService {
    User login(String user_name,String password);

    boolean existUser(String user_name);

    int register(User user);

    int updateUserById(User current_user);

    List<Play> selectAllPlay();

    List<CinemaHall> selectAllCinemaHall();

    List<PerformancePlanVo> selectAllPerformancePlan();


    Play selectPlayById(int play_id);

    List<PerformancePlanVo> showPerformancePlanInfo(int play_id);

    Ticket buyTicket(PerformancePlanVo performancePlanVo, int number, int row, int column);

    String selectSeat(int performancePlan_id);
}
