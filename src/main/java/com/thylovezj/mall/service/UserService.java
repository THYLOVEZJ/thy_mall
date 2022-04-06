package com.thylovezj.mall.service;

import com.thylovezj.mall.exception.ThylovezjMallException;
import com.thylovezj.mall.model.pojo.User;
import org.springframework.stereotype.Service;

/***
 * UserService
 */
@Service
public interface UserService {
    User getUser();

    void register(String userName, String password) throws ThylovezjMallException;

    User login(String username, String password) throws ThylovezjMallException;

    void updateInformation(User user) throws ThylovezjMallException;

    boolean checkAdminRole(User user);
}
