package com.example.demo2.dao;

import com.example.demo2.pojo.CinemaHall;
import com.example.demo2.pojo.PerformancePlan;
import com.example.demo2.pojo.Play;
import com.example.demo2.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ManagerMapper {

    @Select("select * from user where user_name=#{user_name}")
    User selectUserByName(@Param("user_name") String user_name);

    @Delete("delete from user where user_id = #{user_id}")
    int deleteUserById(@Param("user_id") String user_id);







    @Insert("insert into play (duration,status,play_name,price,type,description,image,code) values (#{play.duration},#{play.status},#{play.play_name},#{play.price},#{play.type},#{play.description},#{play.image},#{play.code})")
    int addPlay(@Param("play") Play play);

    @Delete("delete from play where play_name = #{play_name}")
    int deletePlayByName(@Param("play_name") String play_name);

    @Update("update play set play_name=#{play.play_name},duration=#{play.duration},status=#{play.status},price=#{play.price},type=#{play.type},description=#{play.description},image=#{play.image},code=#{play.code} where play_id=#{play.play_id}")
    int updatePlayById(@Param("play") Play play);

    @Select("select * from play")
    List<Play> selectAllPlay();

    @Select("select * from play where play_id=#{play_id}")
    Play selectPlayById(@Param("play_id") int play_id);











    @Insert("insert into cinemaHall (cinemaHall_name,cinemaHall_row,cinemaHall_column,image) values(#{cinemaHall.cinemaHall_name},#{cinemaHall.cinemaHall_row},#{cinemaHall.cinemaHall_column},#{cinemaHall.image})")
    int insertCinemaHall(@Param("cinemaHall") CinemaHall cinemaHall);

    @Delete("delete from cinemaHall where cinemaHall_id=#{cinemaHall_id}")
    int deleteCinemaHallById(@Param("cinemaHall_id") int cinemaHall_id);

    @Update("update cinemaHall set cinemaHall_name=#{cinemaHall.cinemaHall_name},cinemaHall_row=#{cinemaHall.cinemaHall_row},cinemaHall_column=#{cinemaHall.cinemaHall_column},image=#{cinemaHall.image} where cinemaHall_id=#{cinemaHall.cinemaHall_id}")
    int updateCinemaHallById(@Param("cinemaHall") CinemaHall cinemaHall);

    @Select("select * from cinemaHall")
    List<CinemaHall> selectAllCinemaHall();







    @Select("select * from performancePlan where play_id=#{play_id}")
    List<PerformancePlan> selectPerformanceByPlayId(@Param("play_id") int play_id);









    @Select("select * from user")
    List<User> selectAllUser();

    @Update("update user set user_name=#{user.user_name},email=#{user.email},phone=#{user.phone},status=#{user.status},password=#{user.password},position=#{user.position} where user_id = #{user.user_id}")
    int updateUserById(@Param("user") User user);

    @Insert("insert into performancePlan(play_id,cinemaHall_id,start_time,end_time) values(#{performancePlan.play_id},#{performancePlan.cinemaHall_id},#{performancePlan.start_time},#{performancePlan.end_time})")
    int insertPerformancePlan(@Param("performancePlan") PerformancePlan performancePlan);


    @Update("update performancePlan set play_id=#{performancePlan.play_id},cinemaHall_id=#{performancePlan.cinemaHall_id},start_time=#{performancePlan.start_time},end_time=#{performancePlan.end_time} where performancePlan_id=#{performancePlan.performancePlan_id}")
    int updatePerformancePlanById(@Param("performancePlan") PerformancePlan performancePlan);

    @Delete("delete from performancePlan where performancePlan_id=#{performancePlan.performancePlan_id}")
    int deletePerformancePlanById(@Param("performancePlan") PerformancePlan performancePlan);

    @Select("select * from performancePlan")
    List<PerformancePlan> selectAllPerformancePlan();

    @Select({"<script>",
            "select * from performancePlan",
            "where 1=1",
            "<when test='play_name!=null'>",
            "and play_name like",
            "concat('%',#{play_name},'%')",
            "</when>",
            "<when test='cinemaHall_name!=null'>",
            "and cinemaHall_name like ",
            "concat('%',#{cinemaHall_name},'%')",
            "</when>",
            "</script>"})
    List<PerformancePlan> selectPerformancePlanByConditions(@Param("play_name") String play_name,@Param("cinemaHall_name") String cinemaHall_name);


    @Select("select * from cinemaHall where cinemaHall_id=#{cinemaHall_id}")
    CinemaHall selectCinemaHallById(@Param("cinemaHall_id") int cinemaHall_id);

    @Select({"<script>",
            "select * from user",
            "where 1=1",
            "<when test='user_name!=null'>",
            "and user_name like",
            "concat('%',#{user_name},'%')",
            "</when>",
            "<when test='position!=0'>",
            "and position =#{position} ",
            "</when>",
            "<when test='phone!=null'>",
            "and phone like",
            "concat('%',#{phone},'%')",
            "</when>",
            "</script>"})
    List<User> selectUserByConditions(@Param("user_name") String user_name,@Param("position") int position,@Param("phone") String phone);
}
