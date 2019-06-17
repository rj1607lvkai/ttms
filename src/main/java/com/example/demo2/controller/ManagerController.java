package com.example.demo2.controller;

import com.example.demo2.pojo.CinemaHall;
import com.example.demo2.pojo.PerformancePlan;
import com.example.demo2.pojo.Play;
import com.example.demo2.pojo.User;
import com.example.demo2.service.ManagerService;
import com.example.demo2.service.UserService;
import com.example.demo2.utils.ConstantValue;
import com.example.demo2.utils.FileUtil;
import com.example.demo2.utils.PhoneUtil;
import com.example.demo2.vo.SaleSituation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping(value="manager/")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private UserService userService;




    @RequestMapping(value = "manageUser.action")
    public String manageUser(Model model){
        List<User> userList = managerService.selectAllUser();
        model.addAttribute(ConstantValue.SELECT_RESULT,userList);
        return "manageUser";
    }

    @RequestMapping(value = "addUser.action")
    public String addUser(String user_name, String password,
                          String rePassword,
                          String sex,int position,
                          String email, String phone,
                          MultipartFile file,
                          Model model) {

        model.addAttribute("user_name",user_name);
        model.addAttribute("password",password);
        model.addAttribute("rePassword",rePassword);
        model.addAttribute("sex",sex);
        model.addAttribute("position",position);
        model.addAttribute("email",email);
        model.addAttribute("phone",phone);
        if (user_name == null || Objects.equals(user_name,"")){
            model.addAttribute(ConstantValue.REGISTER_FAILED_KEY,"用户名不能为空");
            return "addUser";
        }
        if (password == null || Objects.equals(password,"")){
            model.addAttribute(ConstantValue.REGISTER_FAILED_KEY,"密码不能为空");
            return "addUser";
        }
        if (rePassword == null || Objects.equals(rePassword,"")){
            model.addAttribute(ConstantValue.REGISTER_FAILED_KEY,"确认密码不能为空");
            return "addUser";
        }
        if (email == null || Objects.equals(email,"")){
            model.addAttribute(ConstantValue.REGISTER_FAILED_KEY,"邮箱不能为空");
            return "addUser";
        }
        if (phone == null || Objects.equals(phone,"")){
            model.addAttribute(ConstantValue.REGISTER_FAILED_KEY,"电话号码不能为空");
            return "addUser";
        }
        if (sex == null || Objects.equals(sex,"")){
            model.addAttribute(ConstantValue.REGISTER_FAILED_KEY,"性别不能为空");
            return "addUser";
        }
        if (userService.existUser(user_name)) {
            model.addAttribute(ConstantValue.REGISTER_FAILED_KEY, "用户名已存在");
            return "addUser";
        }
        if (!Objects.equals(password, rePassword)) {
            model.addAttribute(ConstantValue.REGISTER_FAILED_KEY, "密码不一致");
            return "addUser";
        }
        if (!PhoneUtil.isMobileNumber(phone)) {
            model.addAttribute(ConstantValue.REGISTER_FAILED_KEY, "电话号码不正确");
            return "addUser";
        }
        if (position == 0){
            model.addAttribute(ConstantValue.REGISTER_FAILED_KEY, "用户身份不能为空");
            return "addUser";
        }

        String fileName = FileUtil.upLoadFile(file);
        User user = new User();
        user.setUser_name(user_name);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setStatus(ConstantValue.STATUS_INCUMBENCY);
        user.setPosition(position);
        user.setSex(sex);
        user.setImage(ConstantValue.IMAGE_PREFIX+fileName);
        System.out.println(user);
        int insert = userService.register(user);
        if (insert >= ConstantValue.INSERT_SUCCESS) {
            return "redirect:manageUser.action";
        }
        return "managerHome";
    }

    @RequestMapping(value = "deleteUser.action")
    public String deleteUser(int user_id,
                             HttpServletRequest request, HttpServletResponse response,
                             Model model) {
        if (user_id == 0){
            return "redirect:manageUser.action";
        }
        int delete = managerService.deleteUserById(user_id);
        if (delete >= ConstantValue.DELETE_SUCCESS) {
            return "redirect:manageUser.action";
        }
        return "managerHome";
    }

    @RequestMapping(value = "updateUser.action")
    public String updateUser(User user, Model model,
                             MultipartFile file,
                             HttpSession session) {
        model.addAttribute("select_result",user);
        User selectUser = managerService.selectUserById(user.getUser_id());
        if (!PhoneUtil.isMobileNumber(user.getPhone())) {
            model.addAttribute(ConstantValue.UPDATE_FAILED_KEY, "电话号码不正确");
            return "updateUser";
        }
        String fileName = FileUtil.upLoadFile(file);
        if (Objects.equals(fileName, ConstantValue.DEFAULT_IMAGE)) {
            user.setImage(selectUser.getImage());
        }else {
            user.setImage(ConstantValue.IMAGE_PREFIX+fileName);
        }
        if (!Objects.equals(selectUser.getUser_name(), user.getUser_name())) {
            if (userService.existUser(user.getUser_name())) {
                model.addAttribute(ConstantValue.UPDATE_FAILED_KEY, "用户名已存在");
                return "updateUser";
            }
        }
        int update = managerService.updateUserById(user);
        if (update >= ConstantValue.UPDATE_SUCCESS) {
            return "redirect:manageUser.action";
        }
        return "managerHome";
    }

    @RequestMapping(value = "selectUserById.action")
    public String selectUserById(int user_id,Model model){
        if (user_id == 0){
            return "managerHome";
        }
        User user = managerService.selectUserById(user_id);
        model.addAttribute(ConstantValue.SELECT_RESULT,user);
        return "updateUser";
    }
    @RequestMapping(value = "showAllUser.action")
    public String showAllUser(Model model){
        List<User> userList = managerService.selectAllUser();
        model.addAttribute(ConstantValue.SELECT_RESULT,userList);
        return "manageUser";
    }

    @RequestMapping(value = "showUserByConditions.action")
    public String showUserByConditions(String user_name,
                                       @RequestParam(defaultValue = "0") int position,
                                       String phone,
                                       Model model){


        model.addAttribute("user_name",user_name);
        model.addAttribute("phone",phone);

        List<User> userList = managerService.showUserByConditions(user_name,position,phone);
        model.addAttribute(ConstantValue.SELECT_RESULT,userList);
        return "manageUser";
    }










    @RequestMapping(value = "addPlay.action")
    public String addPlay(Play play, MultipartFile file,
                          Model model) {
        if (play == null){
            model.addAttribute(ConstantValue.INSERT_FAILED_KEY,"请完善影片信息");
            return "addPlay";
        }
        model.addAttribute("play_name",play.getPlay_name());
        model.addAttribute("duration",play.getDuration());
        model.addAttribute("type",play.getType());
        model.addAttribute("price",play.getPrice());
        model.addAttribute("code",play.getCode());
        model.addAttribute("description",play.getDescription());
        if (play.getPlay_name() == null || Objects.equals(play.getPlay_name().trim(),"")) {
            model.addAttribute(ConstantValue.INSERT_FAILED_KEY,"影片名称不能为空");
            return "addPlay";
        }
        if (play.getDuration() <= 0){
            model.addAttribute(ConstantValue.INSERT_FAILED_KEY,"影片时长不正确");
            return "addPlay";
        }
        if (play.getType() == null || Objects.equals(play.getType().trim(),"")){
            model.addAttribute(ConstantValue.INSERT_FAILED_KEY,"影片类型不能为空");
            return "addPlay";
        }
        if (play.getPrice() <=0){
            model.addAttribute(ConstantValue.INSERT_FAILED_KEY,"影片价格不正确");
            return "addPlay";
        }
        if (play.getCode() == null || Objects.equals(play.getCode().trim(),"")){
            model.addAttribute(ConstantValue.INSERT_FAILED_KEY,"影片评分不能为空");
            return "addPlay";
        }
        if (play.getDescription() == null || Objects.equals(play.getDescription().trim(),"")){
            model.addAttribute(ConstantValue.INSERT_FAILED_KEY,"影片简介不能为空");
            return "addPlay";
        }
        play.setStatus(1);
        String fileName = FileUtil.upLoadFile(file);
        play.setImage(ConstantValue.IMAGE_PREFIX+fileName);


        int insert = managerService.addPlay(play);
        if (insert >= ConstantValue.INSERT_SUCCESS) {
            return "redirect:managePlay.action";
        }

        return "managerHome";

    }

    @RequestMapping(value = "selectPlayById.action")
    public String selectPlayById(int play_id,Model model){
        if (play_id == 0){
            return "redirect:managePlay.action";
        }
        Play play=managerService.selectPlayById(play_id);
        model.addAttribute(ConstantValue.SELECT_RESULT,play);
        return "updatePlay";
    }


    @RequestMapping(value = "deletePlay.action")
    public String deletePlay(int play_id,
                          Model model) {
        if (play_id == 0){
            return "redirect:managePlay.action";
        }
        int delete = managerService.deletePlayById(play_id);
        if (delete >= ConstantValue.DELETE_SUCCESS) {
            return "redirect:managePlay.action";
        }
        return "managerHome";

    }

    @RequestMapping(value = "updatePlay.action")
    public String updatePlay(Play play, MultipartFile file,
                          Model model) {
        int play_id = play.getPlay_id();
        if (play_id == 0) {
            model.addAttribute(ConstantValue.UPDATE_FAILED_KEY, "影片不能为空");
            return "updatePlay";
        }
        Play selectPlay = managerService.selectPlayById(play_id);
        String fileName = FileUtil.upLoadFile(file);
        if (Objects.equals(fileName,ConstantValue.DEFAULT_IMAGE)){
            play.setImage(selectPlay.getImage());
        }else {
            play.setImage(ConstantValue.IMAGE_PREFIX+fileName);
        }


        int update = managerService.updatePlayById(play);
        if (update >= ConstantValue.DELETE_SUCCESS) {
            return "redirect:managePlay.action";
        }
        return "managerHome";

    }


    @RequestMapping(value = "addCinemaHall.action")
    public String addCinemaHall(CinemaHall cinemaHall,
                                MultipartFile file,
                                Model model) {

        String fileName = FileUtil.upLoadFile(file);
        cinemaHall.setImage(ConstantValue.IMAGE_PREFIX+fileName);
        int insert = managerService.addCinemaHall(cinemaHall);
        if (insert >= ConstantValue.INSERT_SUCCESS) {
            return "redirect:manageCinemaHall.action";
        }
        return "managerHome";

    }

    @RequestMapping(value = "deleteCinemaHall.action")
    public String deleteCinemaHall(int cinemaHall_id,
                                   Model model) {

        if (cinemaHall_id == 0){
            return "managerHome";
        }
        int delete = managerService.deleteCinemaHall(cinemaHall_id);
        if (delete >= ConstantValue.DELETE_SUCCESS) {
            return "redirect:manageCinemaHall.action";
        }
        return "managerHome";

    }

    @RequestMapping(value = "updateCinemaHall.action")
    public String updateCinemaHall(CinemaHall cinemaHall,
                                   MultipartFile file,
                                   Model model) {

        CinemaHall selectCinemaHall1 = managerService.selectCinemaHallById(cinemaHall.getCinemaHall_id());
        String fileName = FileUtil.upLoadFile(file);
        if (Objects.equals(fileName,ConstantValue.DEFAULT_IMAGE)){
            cinemaHall.setImage(selectCinemaHall1.getImage());
        }else {
            cinemaHall.setImage(ConstantValue.IMAGE_PREFIX+fileName);
        }
        int update = managerService.updateCinemaHall(cinemaHall);
        if (update >= ConstantValue.UPDATE_SUCCESS) {
            return "redirect:manageCinemaHall.action";
        }
        return "managerHome";

    }


    @RequestMapping(value = "addPerformancePlan.action")
    public String addPerformancePlan(PerformancePlan performancePlan, Model model) {
        int insert = managerService.insertPerformancePlan(performancePlan);
        if (insert >= ConstantValue.INSERT_SUCCESS) {
            return "redirect:managePerformancePlan.action";
        }
        return "managerHome";
    }

    @RequestMapping(value = "updatePerformancePlan.action")
    public String updatePerformancePlan(PerformancePlan performancePlan, Model model) {
        int update = managerService.updatePerformancePlanById(performancePlan);
        if (update >= ConstantValue.UPDATE_SUCCESS) {
            return "redirect:managePerformancePlan.action";
        }
        return "managerHome";
    }

    @RequestMapping(value = "deletePerformancePlan.action")
    public String deletePerformancePlan(int performancePlan_id, Model model) {
        if (performancePlan_id == 0){
            return "managerHome";
        }
        int delete = managerService.deletePerformancePlanById(performancePlan_id);
        if (delete >= ConstantValue.DELETE_SUCCESS) {
            return "redirect:managePerformancePlan.action";
        }
        return "managerHome";
    }


    @RequestMapping(value = "showPlayByConditions.action")
    public String showPlayByConditions(String play_name,
                                       String type, Model model) {

        model.addAttribute("play_name",play_name);
        model.addAttribute("type",type);
        List<Play> playList = userService.selectPlayByConditions(play_name, type);
        model.addAttribute(ConstantValue.SELECT_RESULT, playList);
        return "managePlay";
    }
    @RequestMapping(value = "showPlayItemByConditions.action")
    public String showPlayItemByConditions(String play_name,
                                       String type, Model model) {
        List<Play> playList = userService.selectPlayByConditions(play_name, type);
        model.addAttribute(ConstantValue.SELECT_RESULT, playList);
        return "playItem";
    }

    @RequestMapping(value = "managePlay.action")
    public String managePlay(Model model) {
        List<Play> playList = userService.selectAllPlay();
        model.addAttribute(ConstantValue.SELECT_RESULT, playList);
        return "managePlay";
    }

    @RequestMapping(value = "showCinemaHallByConditions.action")
    public String showCinemaHallByConditions(String cinemaHall_name, Model model) {
        model.addAttribute("cinemaHall_name",cinemaHall_name);
        List<CinemaHall> cinemaHallList = userService.selectCinemaHallByConditions(cinemaHall_name);
        model.addAttribute(ConstantValue.SELECT_RESULT, cinemaHallList);
        return "manageCinemaHall";

    }
    @RequestMapping(value = "showCinemaHallItemByConditions.action")
    public String showCinemaHallItemByConditions(String cinemaHall_name, Model model) {
        List<CinemaHall> cinemaHallList = userService.selectCinemaHallByConditions(cinemaHall_name);
        model.addAttribute(ConstantValue.SELECT_RESULT, cinemaHallList);
        return "cinemaHallItem";

    }
    @RequestMapping(value = "selectCinemaHallById.action")
    public String selectCinemaHallById(int cinemaHall_id, Model model) {
        CinemaHall cinemaHall=managerService.selectCinemaHallById(cinemaHall_id);
        model.addAttribute(ConstantValue.SELECT_RESULT, cinemaHall);
        return "updateCinemaHall";

    }
    @RequestMapping(value = "selectPerformancePlanById.action")
    public String selectPerformancePlanById(int performancePlan_id, Model model) {
        PerformancePlan performancePlan=managerService.selectPerformancePlanById(performancePlan_id);
        model.addAttribute(ConstantValue.SELECT_RESULT, performancePlan);
        return "updatePerformancePlan";

    }

    @RequestMapping(value = "manageCinemaHall.action")
    public String manageCinemaHall(Model model) {
        List<CinemaHall> cinemaHallList = userService.selectAllCinemaHall();
        model.addAttribute(ConstantValue.SELECT_RESULT, cinemaHallList);
        return "manageCinemaHall";
    }




    @RequestMapping(value = "showPerformancePlanByConditions.action")
    public String showPerformancePlanByConditions(String cinemaHall_name,
                                                  String play_name,
                                                  Model model) {
        model.addAttribute("cinemaHall_name",cinemaHall_name);
        model.addAttribute("play_name",play_name);
        List<PerformancePlan> performancePlanList = managerService.showPerformancePlanByConditions(play_name, cinemaHall_name);
        model.addAttribute(ConstantValue.SELECT_RESULT, performancePlanList);
        return "managePerformancePlan";
    }

    @RequestMapping(value = "managePerformancePlan.action")
    public String managePerformancePlan(Model model) {
        List<PerformancePlan> performancePlanList = managerService.selectAllPerformancePlan();
        model.addAttribute(ConstantValue.SELECT_RESULT, performancePlanList);
        return "managePerformancePlan";
    }

    @RequestMapping(value = "showSaleSituation.action")
    public String showSaleSituation(Model model) {
        List<SaleSituation> saleSituationList=managerService.showSaleSituation();
        model.addAttribute(ConstantValue.SELECT_RESULT, saleSituationList);
        return "saleSituation";
    }
    @RequestMapping(value = "showSaleSituationByConditions.action")
    public String showSaleSituationByConditions(String play_name,
                                                String type,
                                                Model model) {
        model.addAttribute("play_name",play_name);
        model.addAttribute("type",type);

        List<SaleSituation> saleSituationList=managerService.showSaleSituation();
        List<SaleSituation> resultList=new ArrayList<>();
        if (play_name == null){
            if (type == null){
                model.addAttribute(ConstantValue.SELECT_RESULT, saleSituationList);
                return "saleSituation";
            }
            for (SaleSituation saleSituation:saleSituationList){
                if (saleSituation.getType().contains(type)){
                    resultList.add(saleSituation);
                }
            }
            model.addAttribute(ConstantValue.SELECT_RESULT, resultList);
            return "saleSituation";
        }
        if (type == null){
            for (SaleSituation saleSituation:saleSituationList){
                if (saleSituation.getPlay_name().contains(play_name)){
                    resultList.add(saleSituation);
                }
            }
            model.addAttribute(ConstantValue.SELECT_RESULT, resultList);
            return "saleSituation";
        }
        for (SaleSituation saleSituation:saleSituationList){
            if (saleSituation.getPlay_name().contains(play_name) && saleSituation.getType().contains(type)){
                resultList.add(saleSituation);
            }
        }
        model.addAttribute(ConstantValue.SELECT_RESULT, resultList);
        return "saleSituation";
    }

    @RequestMapping(value = "showAllPlayItem.action")
    public String showAllPlayItem(Model model) {
        List<Play> playList=managerService.selectAllPlay();
        model.addAttribute(ConstantValue.SELECT_RESULT, playList);
        return "playItem";
    }
    @RequestMapping(value = "showAllCinemaHallItem.action")
    public String showAllCinemaHallItem(Model model) {
        List<CinemaHall> cinemaHallList=managerService.selectAllCinemaHall();
        model.addAttribute(ConstantValue.SELECT_RESULT, cinemaHallList);
        return "cinemaHallItem";
    }









    @RequestMapping(value="home")
    public String homePage(){
        return "managerHome";
    }
    @RequestMapping(value="addCinemaHall")
    public String addCinemaHallPage(){
        return "addCinemaHall";
    }
    @RequestMapping(value="addUser")
    public String addUserPage(){
        return "addUser";
    }
    @RequestMapping(value="addPlay")
    public String addPlayPage(){
        return "addPlay";
    }
    @RequestMapping(value="addPerformancePlan")
    public String addPerformancePlanPage(){
        return "addPerformancePlan";
    }


}
