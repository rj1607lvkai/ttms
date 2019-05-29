package com.example.demo2.dao;


import com.example.demo2.pojo.CinemaHall;
import com.example.demo2.pojo.PerformancePlan;
import com.example.demo2.pojo.Play;
import com.example.demo2.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface UserMapper {
	

//  @Results({@Result(property = "userId",column = "user_id"),@Result(property = "productId",column = "product_id")})
	@Select("select * from user where user_name=#{user_name} and password = #{password}")
	User selectUserByNameAndPassword(@Param("user_name")String user_name,@Param("password")String password);

	@Select("select * from user where user_name=#{user_name}")
    User selectUserByName(@Param("user_name") String user_name);

	@Insert("insert into user (user_name,password,email,phone,position,status) values (#{user.user_name},#{user.password},#{user.email},#{user.phone},#{user.position},#{user.status})")
	int insertUser(@Param("user") User user);

	@Update("update user set user_name=#{user.user_name},email=#{user.email},phone=#{user.phone} where user_id = #{user.user_id}")
    int updateUserById(@Param("user") User current_user);

	@Select("select * from play")
	List<Play> selectAllPlay();

	@Select("select * from play where play_id=#{play_id}")
	Play selectPlayById(@Param("play_id") int play_id);

	@Select("select * from cinemaHall")
    List<CinemaHall> selectAllCinemaHall();

	@Select("select * from performancePlan")
    List<PerformancePlan> selectAllPerformancePlan();

	@Select("select * from performancePlan where play_id=#{play_id}")
    List<PerformancePlan> selectPerformanceByPlayId(@Param("play_id") int play_id);

	@Select("select * from cinemaHall where cinemaHall_id=#{cinemaHall_id}")
	CinemaHall selectCinemaHallById(@Param("cinemaHall_id") int cinemaHall_id);

	@Update("update performancePlan set seat=#{seat} where performancePlan_id=#{performancePlan_id}")
    int updatePerformancePlanById(@Param("performancePlan_id") int performancePlan_id,@Param("seat") String seat);

	@Select("select * from performancePlan where PerformancePlan_id=#{PerformancePlan_id}")
	PerformancePlan selectPerformancePlanById(@Param("PerformancePlan_id") int performancePlan_id);

	@Select({"<script>","select * from play",
			"where 1=1",
			"<when test='play_name!=null'>",
			"and play_name like ",
			"concat('%',#{play_name},'%')",
			"</when>",
			"<when test='type!=null'>",
			"and type like ",
			"concat('%',#{type},'%')",
			"</when>",
			"</script>"
			})
    List<Play> selectPlayByConditions(@Param("play_name") String play_name,@Param("type") String type);

	@Select({"<script>",
			"select * from cinemaHall",
			"where 1=1",
			"<when test='cinemaHall_name!=null'>",
			"and cinemaHall_name like",
			"concat('%',#{cinemaHall_name},'%')",
			"</when>",
			"</script>"})
	List<CinemaHall> selectCinemaHallByConditions(@Param("cinemaHall_name") String cinemaHall_name);
}
