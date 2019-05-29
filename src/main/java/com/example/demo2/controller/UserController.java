package com.example.demo2.controller;




import com.example.demo2.pojo.*;
import com.example.demo2.service.UserService;
import com.example.demo2.utils.ConstantValue;
import com.example.demo2.utils.PhoneUtil;
import com.example.demo2.vo.PerformancePlanVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;


@Controller
@RequestMapping(value="user/")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "login.action")
    public String login(String user_name, String password,
                        Model model, HttpSession session) {
        User user = userService.login(user_name, password);
        if (user == null) {
            model.addAttribute(ConstantValue.LOGIN_FAILED_KEY, ConstantValue.LOGIN_FAILED_VALUE);
            return "login";
        }

        user.setPassword(null);
        session.setAttribute(ConstantValue.CURRENT_USER, user);
        model.addAttribute("user", user);
        return "redirect:home";
    }

    @RequestMapping(value = "register.action")
    public String register(String user_name, String password, String rePassword,
                           String email, String phone, Model model) {
        if (userService.existUser(user_name)) {
            model.addAttribute(ConstantValue.REGISTER_FAILED_KEY, "用户名已存在");
            return "register";
        }
        if (!Objects.equals(password, rePassword)) {
            model.addAttribute(ConstantValue.REGISTER_FAILED_KEY, "密码不一致");
            return "register";
        }
        if (!PhoneUtil.isMobileNumber(phone)) {
            model.addAttribute(ConstantValue.REGISTER_FAILED_KEY, "电话号码不正确");
            return "register";
        }
        User user = new User();
        user.setUser_name(user_name);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setStatus(ConstantValue.STATUS_INCUMBENCY);
        user.setPosition(ConstantValue.POSITION_EMPLOYEE);
        int register = userService.register(user);
        if (register == ConstantValue.REGISTER_SUCCESS) {
            return "redirect:login";
        }
        return "error";
    }

    @RequestMapping(value = "update.action")
    public String update(String user_name, String email,
                         String phone, Model model, HttpSession session) {

        User current_user = (User) session.getAttribute(ConstantValue.CURRENT_USER);

        if (user_name == null || user_name == "") {
            model.addAttribute(ConstantValue.UPDATE_FAILED_KEY, "用户名不能为空");
            return "home";
        }
        if (Objects.equals(current_user.getUser_name(), user_name)) {
            if (!PhoneUtil.isMobileNumber(phone)) {
                model.addAttribute(ConstantValue.UPDATE_FAILED_KEY, "电话号码不正确");
                return "home";
            }
            current_user.setEmail(email);
            current_user.setPhone(phone);
        } else {
            if (userService.existUser(user_name)) {
                model.addAttribute(ConstantValue.UPDATE_FAILED_KEY, "用户名已存在");
                return "home";
            }
            if (!PhoneUtil.isMobileNumber(phone)) {
                model.addAttribute(ConstantValue.UPDATE_FAILED_KEY, "电话号码不正确");
                return "home";
            }
            current_user.setUser_name(user_name);
            current_user.setEmail(email);
            current_user.setPhone(phone);
        }
        int update = userService.updateUserById(current_user);
        if (update == ConstantValue.UPDATE_SUCCESS) {
            session.setAttribute(ConstantValue.CURRENT_USER, current_user);
            return "success";
        }
        return "error";
    }

    @RequestMapping(value = "showAllPlay.action")
    public String showAllPlay(Model model) {
        List<Play> playList = userService.selectAllPlay();
        model.addAttribute(ConstantValue.SELECT_RESULT, playList);
        return "play";
    }

    @RequestMapping(value = "showPlayDetails.action")
    public String showPlayDetails(int play_id,Model model) {
        Play play = userService.selectPlayById(play_id);
        model.addAttribute(ConstantValue.SELECT_RESULT, play);
        return "playDetails";

    }
    @RequestMapping(value = "showPerformancePlanInfo.action")
    public String showPerformancePlanInfo(int play_id,Model model) {
        List<PerformancePlanVo> performancePlanVoList = userService.showPerformancePlanInfo(play_id);
        model.addAttribute(ConstantValue.SELECT_RESULT, performancePlanVoList);
        return "performancePlanInfo";
    }
    @RequestMapping(value = "selectAllCinemaHall.action")
    public String selectAllCinemaHall(Model model) {

        List<CinemaHall> cinemaHallList = userService.selectAllCinemaHall();
        model.addAttribute(ConstantValue.SELECT_RESULT, cinemaHallList);
        return "selectCinemaHallResult";

    }


    @RequestMapping(value = "selectAllPerformancePlan.action")
    public String selectAllPerformancePlan(Model model) {


        List<PerformancePlanVo> performancePlanVoList = userService.selectAllPerformancePlan();
        model.addAttribute(ConstantValue.SELECT_RESULT, performancePlanVoList);
        return "selectPerformancePlanVoResult";
    }




    @RequestMapping(value = "buyTicket.action")
    public String buyTicket(PerformancePlanVo performancePlanVo,
                            int number,int row,int column, Model model){
        System.out.println(performancePlanVo);
        String seat=userService.selectSeat(performancePlanVo.getPerformancePlan_id());

        Ticket ticket=userService.buyTicket(performancePlanVo,number,row,column);
        System.out.println(ticket);
        System.out.println(seat);
        return "error";

    }









    @RequestMapping(value="login")
    public String loginPage(){
        return "login";
    }
    @RequestMapping(value="register")
    public String registerPage(){
        return "register";
    }
    @RequestMapping(value="home")
    public String homePage(){
        return "home";
    }


}
