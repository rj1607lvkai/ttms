package com.example.demo2.controller;

import com.example.demo2.pojo.CinemaHall;
import com.example.demo2.pojo.PerformancePlan;
import com.example.demo2.pojo.Play;
import com.example.demo2.pojo.User;
import com.example.demo2.service.ManagerService;
import com.example.demo2.service.UserService;
import com.example.demo2.utils.ConstantValue;
import com.example.demo2.utils.PhoneUtil;
import com.example.demo2.vo.PerformancePlanVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value="manager/")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private UserService userService;

    @RequestMapping(value="addUser.action")
    public String addUser(String user_name, String password, String rePassword,
                           String email, String phone, Model model) {

        if (!Objects.equals(password, rePassword)) {
            model.addAttribute(ConstantValue.REGISTER_FAILED_KEY, "密码不一致");
            return "failed";
        }
        if (!PhoneUtil.isMobileNumber(phone)) {
            model.addAttribute(ConstantValue.REGISTER_FAILED_KEY, "电话号码不正确");
            return "failed";
        }
        User user = new User();
        user.setUser_name(user_name);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setStatus(ConstantValue.STATUS_INCUMBENCY);
        user.setPosition(ConstantValue.POSITION_EMPLOYEE);
        int insert = userService.register(user);
        if (insert >= ConstantValue.INSERT_SUCCESS) {
            return "success";
        }
        return "error";
    }
    @RequestMapping(value="updateUser.action")
    public String updateUser(User user, Model model,
                             HttpServletRequest request,HttpServletResponse response){
        if (!PhoneUtil.isMobileNumber(user.getPhone())) {
            model.addAttribute(ConstantValue.UPDATE_FAILED_KEY, "电话号码不正确");
            try {
                request.getRequestDispatcher("/manager/selectAllUser.action").forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "error";
        } else {
            int update = managerService.updateUserById(user);
            if (update >= ConstantValue.UPDATE_SUCCESS){
                return "success";
            }
            model.addAttribute(ConstantValue.UPDATE_FAILED_KEY,"用户不存在");
            try {
                request.getRequestDispatcher("/manager/selectAllUser.action").forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "error";
        }
    }
    @RequestMapping(value="deleteUser.action")
    public String deleteUser(String user_id,
                             HttpServletRequest request,HttpServletResponse response,
                             Model model) {
        int delete = managerService.deleteUserById(user_id);
        if (delete == ConstantValue.DELETE_SUCCESS) {
            return "success";
        }
        model.addAttribute(ConstantValue.DELETE_FAILED_KEY, "删除失败,用户不存在");
        try {
            request.getRequestDispatcher("/manager/selectAllUser.action").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }







    @RequestMapping(value="addPlay.action")
    public String addPlay(Play play, Model model) {
        int add = managerService.addPlay(play);
        if (add == ConstantValue.INSERT_SUCCESS) {
            return "success";
        }
        model.addAttribute(ConstantValue.INSERT_FAILED_KEY, "添加失败");
        return "home";

    }
    @RequestMapping(value="deletePlay.action")
    public String addPlay(String play_name, HttpSession session,
                          Model model) {
        if (play_name == null || play_name == "") {
            model.addAttribute(ConstantValue.DELETE_FAILED_KEY, "影片名不能为空");
            return "home";
        }
        int delete = managerService.deletePlayByName(play_name);
        if (delete >= ConstantValue.DELETE_SUCCESS) {
            return "success";
        }
        model.addAttribute(ConstantValue.DELETE_FAILED_KEY, "影片不存在");
        return "home";

    }
    @RequestMapping(value="updatePlay.action")
    public String addPlay(Play play, HttpSession session,
                          Model model) {
        int play_id=play.getPlay_id();
        if (play_id == 0) {
            model.addAttribute(ConstantValue.UPDATE_FAILED_KEY, "影片不能为空");
            return "home";
        }
        int update = managerService.updatePlayById(play);
        if (update >= ConstantValue.DELETE_SUCCESS) {
            return "success";
        }
        model.addAttribute(ConstantValue.UPDATE_FAILED_KEY, "更新失败,影片不存在");
        return "home";

    }



    @RequestMapping(value="addCinemaHall.action")
    public String addCinemaHall(CinemaHall cinemaHall, Model model) {

        int insert = managerService.addCinemaHall(cinemaHall);
        if (insert >= ConstantValue.INSERT_SUCCESS) {
            return "success";
        }
        model.addAttribute(ConstantValue.INSERT_FAILED_KEY, "影厅添加失败");
        return "home";

    }
    @RequestMapping(value="deleteCinemaHall.action")
    public String deleteCinemaHall(int cinemaHall_id,
                                   HttpServletRequest request, HttpServletResponse response,
                                   Model model) {

        int delete = managerService.deleteCinemaHall(cinemaHall_id);
        if (delete >= ConstantValue.DELETE_SUCCESS) {
            return "success";
        }
        model.addAttribute(ConstantValue.DELETE_FAILED_KEY, "影厅不存在");
        try {
            request.getRequestDispatcher("/user/selectAllCinemaHall.action").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";

    }
    @RequestMapping(value="updateCinemaHall.action")
    public String updateCinemaHall(CinemaHall cinemaHall,
                                   HttpServletRequest request, HttpServletResponse response,
                                   Model model) {

        int update = managerService.updateCinemaHall(cinemaHall);
        if (update >= ConstantValue.UPDATE_SUCCESS) {
            return "success";
        }
        model.addAttribute(ConstantValue.UPDATE_FAILED_KEY, "影厅不存在");
        try {
            request.getRequestDispatcher("/user/selectAllCinemaHall.action").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";

    }






    @RequestMapping(value = "addPerformancePlan.action")
    public String addPerformancePlan(PerformancePlan performancePlan,Model model){
        int insert=managerService.insertPerformancePlan(performancePlan);
        if (insert>=ConstantValue.INSERT_SUCCESS){
            return "success";
        }
        model.addAttribute(ConstantValue.INSERT_FAILED_KEY,"添加演出计划失败");
        return "failed";
    }
    @RequestMapping(value = "updatePerformancePlan.action")
    public String updatePerformancePlan(PerformancePlan performancePlan,Model model){
        int update=managerService.updatePerformancePlanById(performancePlan);
        if (update>=ConstantValue.UPDATE_SUCCESS){
            return "success";
        }
        model.addAttribute(ConstantValue.UPDATE_FAILED_KEY,"修改演出计划失败");
        return "failed";
    }
    @RequestMapping(value = "deletePerformancePlan.action")
    public String deletePerformancePlan(PerformancePlan performancePlan,Model model){
        int delete=managerService.deletePerformancePlanById(performancePlan);
        if (delete>=ConstantValue.DELETE_SUCCESS){
            return "success";
        }
        model.addAttribute(ConstantValue.DELETE_FAILED_KEY,"删除演出计划失败");
        return "failed";
    }

    @RequestMapping(value = "managePlay.action")
    public String managePlay(Model model){
        List<Play> playList = userService.selectAllPlay();
        model.addAttribute(ConstantValue.SELECT_RESULT,playList);
        return "managePlay";
    }
    @RequestMapping(value = "manageCinemaHall.action")
    public String manageCinemaHall(Model model){
        List<CinemaHall> cinemaHallList = userService.selectAllCinemaHall();
        model.addAttribute(ConstantValue.SELECT_RESULT,cinemaHallList);
        return "manageCinemaHall";
    }
    @RequestMapping(value = "managePerformancePlan.action")
    public String managePerformancePlan(Model model){
        List<PerformancePlan> performancePlanList = managerService.selectAllPerformancePlan();
        model.addAttribute(ConstantValue.SELECT_RESULT,performancePlanList);
        return "managePerformancePlan";
    }
    @RequestMapping(value = "manageUser.action")
    public String manageUser(Model model){
        List<User> userList = managerService.selectAllUser();
        model.addAttribute(ConstantValue.SELECT_RESULT,userList);
        return "manageUser";
    }

}
