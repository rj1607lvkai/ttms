package com.example.demo2.service.impl;

import com.example.demo2.dao.UserMapper;
import com.example.demo2.pojo.*;
import com.example.demo2.service.UserService;
import com.example.demo2.utils.ConstantValue;
import com.example.demo2.utils.SeatUtil;
import com.example.demo2.vo.PerformancePlanDetailsVo;
import com.example.demo2.vo.PerformancePlanVo;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,readOnly = true)
    public User login(String user_name,String password){

        User user=userMapper.selectUserByNameAndPassword(user_name,password);
        return user;

    }

    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,readOnly = true)
    public boolean existUser(String user_name) {
        User user=userMapper.selectUserByName(user_name);
        if (user == null){
            return false;
        }
        return true;
    }

    @Override
    public int register(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public int updateUserById(User current_user) {
        return userMapper.updateUserById(current_user);
    }

    @Override
    public List<Play> selectAllPlay() {
        return userMapper.selectAllPlay();
    }

    @Override
    public List<CinemaHall> selectAllCinemaHall() {
        return userMapper.selectAllCinemaHall();
    }

    @Override
    public List<PerformancePlanVo> selectAllPerformancePlan() {
        ArrayList<PerformancePlanVo> performancePlanVoList = new ArrayList<>();
        List<PerformancePlan> performancePlanList=userMapper.selectAllPerformancePlan();
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

            Play play = userMapper.selectPlayById(performancePlan.getPlay_id());
            PerformancePlanVo performancePlanVo = new PerformancePlanVo();
            performancePlanVo.setPerformancePlan_id(performancePlan.getPerformancePlan_id());
            performancePlanVo.setImage(play.getImage());
            performancePlanVo.setPlay_name(play.getPlay_name());
            performancePlanVo.setCode(play.getCode());
            performancePlanVoList.add(performancePlanVo);
        }
        return performancePlanVoList;
    }


    @Override
    public Play selectPlayById(int play_id) {
        return userMapper.selectPlayById(play_id);
    }

    @Override
    public List<PerformancePlanVo> showPerformancePlanInfo(int play_id) {
        return setPerformancePlanVo(play_id);
    }

    @Override
    public Ticket buyTicket(PerformancePlanVo performancePlanVo, int number, int row, int column) {
   /*

        String seat=performancePlan.getSeat();
        if (seat == null || seat == ""){

        }else {
            String[] usedSeats = seat.split("\\.");
            for (String usedSeat:usedSeats){
                if (Objects.equals(currentSeat,usedSeat)){

                }
            }
        }*/
        int performancePlan_id = performancePlanVo.getPerformancePlan_id();
        PerformancePlan performancePlan=userMapper.selectPerformancePlanById(performancePlan_id);
        String seat=performancePlan.getSeat();
        String currentSeat=row+"&"+column+"/";
        if (seat == null || seat == ""){
            seat=currentSeat;
        }else {
            seat=seat+currentSeat;
        }
        int update=userMapper.updatePerformancePlanById(performancePlan_id,seat);
        if (update>= ConstantValue.UPDATE_SUCCESS){
            return setTicket(performancePlanVo,row,column);
        }
        return null;
    }

    @Override
    public String selectSeat(int performancePlan_id) {
        PerformancePlan performancePlan= userMapper.selectPerformancePlanById(performancePlan_id);
        return performancePlan.getSeat();
    }

    private Ticket setTicket(PerformancePlanVo performancePlanVo, int row, int column) {
        Ticket ticket=new Ticket();

        int performancePlan_id1=performancePlanVo.getPerformancePlan_id();
        int cinemaHall_id=performancePlanVo.getCinemaHall_id();
        int play_id=performancePlanVo.getPlay_id();
        String ticketStr=""+performancePlan_id1+
                cinemaHall_id+play_id+row+column;


        int ticket_id=Integer.parseInt(ticketStr);
        String start_time = performancePlanVo.getStart_time();
        String end_time = performancePlanVo.getEnd_time();



        String play_name = performancePlanVo.getPlay_name();
        String price = performancePlanVo.getPrice();
        String image = performancePlanVo.getImage();
        String code = performancePlanVo.getCode();



        String cinemaHall_name = performancePlanVo.getCinemaHall_name();



        ticket.setTicket_id(ticket_id);
        ticket.setStart_time(start_time);
        ticket.setEnd_time(end_time);
        ticket.setPlay_name(play_name);
        ticket.setPrice(price);
        ticket.setImage(image);
        ticket.setCode(code);
        ticket.setCinemaHall_name(cinemaHall_name);
        ticket.setRow(row);
        ticket.setColumn(column);
        return ticket;
    }

    private List<PerformancePlanVo> setPerformancePlanVo(int play_id) {
        List<PerformancePlanVo> performancePlanVoList=new ArrayList<>();
        List<PerformancePlan> performancePlanList = userMapper.selectPerformanceByPlayId(play_id);
        for (PerformancePlan performancePlan:performancePlanList){
            Play play = userMapper.selectPlayById(performancePlan.getPlay_id());
            CinemaHall cinemaHall=userMapper.selectCinemaHallById(performancePlan.getCinemaHall_id());

            PerformancePlanVo performancePlanVo = new PerformancePlanVo();


            performancePlanVo.setPerformancePlan_id(performancePlan.getPerformancePlan_id());
            performancePlanVo.setStart_time(performancePlan.getStart_time());
            performancePlanVo.setEnd_time(performancePlan.getEnd_time());


            performancePlanVo.setPlay_id(play.getPlay_id());
            performancePlanVo.setPlay_name(play.getPlay_name());
            performancePlanVo.setPrice(play.getPrice());
            performancePlanVo.setImage(play.getImage());
            performancePlanVo.setCode(play.getCode());


            performancePlanVo.setCinemaHall_id(cinemaHall.getCinemaHall_id());
            performancePlanVo.setCinemaHall_name(cinemaHall.getCinemaHall_name());

            performancePlanVoList.add(performancePlanVo);
        }
        return performancePlanVoList;
    }
}

