package com.thylovezj.mall.service.impl;

import com.thylovezj.mall.exception.ThylovezjMallException;
import com.thylovezj.mall.exception.ThylovezjMallExceptionEnum;
import com.thylovezj.mall.model.dao.UserMapper;
import com.thylovezj.mall.model.pojo.User;
import com.thylovezj.mall.service.UserService;
import com.thylovezj.mall.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

/***
 * UserService实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User getUser() {
        return userMapper.selectByPrimaryKey(4);
    }

    @Override
    public void register(String userName, String password) throws ThylovezjMallException {
        //查询用户名是否存在
        User result = userMapper.selectByName(userName);
        if (result != null) {
            throw new ThylovezjMallException(ThylovezjMallExceptionEnum.NAME_EXIST);
        }
        //写到数据库
        User user = new User();
        user.setUsername(userName);
        try {
            user.setPassword(MD5Utils.getMD5Str(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        int count = userMapper.insertSelective(user);
        if (count == 0) {
            throw new ThylovezjMallException(ThylovezjMallExceptionEnum.INSERT_FAILED);
        }
    }

    @Override
    public User login(String username, String password) throws ThylovezjMallException {
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            throw new ThylovezjMallException(ThylovezjMallExceptionEnum.WRONG_PASSWORD);
        }
        return user;
    }

    @Override
    public void updateInformation(User user) throws ThylovezjMallException {
        //更新个性签名
        /***
         * updateByPrimaryKeySelective会对字段进行判断再更新(如果为Null就忽略更新)，如果你只想更新某一字段，可以用这个方法。
         *
         *
         *  updateByPrimaryKey对你注入的字段全部更新
         */
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 1) {
            throw new ThylovezjMallException(ThylovezjMallExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public boolean checkAdminRole(User user){
        /***
         * 校验其是否为管理员
         */
        return user.getRole().equals(2);
    }
}
