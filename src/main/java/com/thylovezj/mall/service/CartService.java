package com.thylovezj.mall.service;

import com.thylovezj.mall.exception.ThylovezjMallException;
import com.thylovezj.mall.model.pojo.User;
import com.thylovezj.mall.model.vo.CartVo;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * UserService
 */
@Service
public interface CartService {

    List<CartVo> add(Integer userId, Integer productId, Integer count);
}
