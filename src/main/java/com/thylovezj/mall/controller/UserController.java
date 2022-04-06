package com.thylovezj.mall.controller;

import com.thylovezj.mall.common.ApiRestResponse;
import com.thylovezj.mall.common.Constant;
import com.thylovezj.mall.exception.ThylovezjMallException;
import com.thylovezj.mall.exception.ThylovezjMallExceptionEnum;
import com.thylovezj.mall.model.pojo.User;
import com.thylovezj.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/***
 * 描述:  用户控制器
 */
@Controller
public class UserController {
    @Autowired
    UserService userService;

    @ResponseBody
    @GetMapping("/test")
    public User personalPage() {
        return userService.getUser();
    }


    @PostMapping("/register")
    @ResponseBody
    public ApiRestResponse register(@RequestParam("username") String username, @RequestParam("password") String password) throws ThylovezjMallException {
        if (StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(ThylovezjMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(ThylovezjMallExceptionEnum.NEED_PASSWORD);
        }
        //密码长度不能少于8位
        if (password.length() < 8) {
            return ApiRestResponse.error(ThylovezjMallExceptionEnum.PASSWORD_TOO_SHORT);
        }
        userService.register(username, password);
        return ApiRestResponse.success();
    }


    @PostMapping("/login")
    @ResponseBody
    public ApiRestResponse login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession httpSession) throws ThylovezjMallException {
        if (StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(ThylovezjMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(ThylovezjMallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(username, password);
        //保存用户信息时，不保存密码
        user.setPassword(null);
        httpSession.setAttribute(Constant.THYLOVEZJ_MALL_USER,user);
        return ApiRestResponse.success(user);
    }

    @PostMapping("/user/update")
    @ResponseBody
    public ApiRestResponse updateUserInfo(HttpSession httpSession,@RequestParam String signature) throws ThylovezjMallException {
        User currentUser = (User) httpSession.getAttribute(Constant.THYLOVEZJ_MALL_USER);
        if (currentUser==null){
            return ApiRestResponse.error(ThylovezjMallExceptionEnum.NEED_LOGIN);
        }
        User user = new User();
        user.setId(currentUser.getId());
        user.setPersonalizedSignature(signature);
        userService.updateInformation(user);
        return ApiRestResponse.success();
    }

    @PostMapping("/user/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session){
        session.removeAttribute(Constant.THYLOVEZJ_MALL_USER);
        return ApiRestResponse.success();
    }


    /***
     * 管理员登录
     * @param username
     * @param password
     * @param httpSession
     * @return
     * @throws ThylovezjMallException
     */
    @PostMapping("/adminLogin")
    @ResponseBody
    public ApiRestResponse adminLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession httpSession) throws ThylovezjMallException {
        if (StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(ThylovezjMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(ThylovezjMallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(username, password);
        if (userService.checkAdminRole(user)) {
            //是管理员
            //保存用户信息时，不保存密码
            user.setPassword(null);
            httpSession.setAttribute(Constant.THYLOVEZJ_MALL_USER,user);
            return ApiRestResponse.success(user);
        }else{
            throw new ThylovezjMallException(ThylovezjMallExceptionEnum.NEED_ADMIN);
        }
    }
}
