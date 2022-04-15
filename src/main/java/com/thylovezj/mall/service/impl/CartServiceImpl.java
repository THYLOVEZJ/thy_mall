package com.thylovezj.mall.service.impl;

import com.thylovezj.mall.common.Constant;
import com.thylovezj.mall.exception.ThylovezjMallException;
import com.thylovezj.mall.exception.ThylovezjMallExceptionEnum;
import com.thylovezj.mall.model.dao.CartMapper;
import com.thylovezj.mall.model.dao.ProductMapper;
import com.thylovezj.mall.model.pojo.Cart;
import com.thylovezj.mall.model.pojo.Product;
import com.thylovezj.mall.model.vo.CartVo;
import com.thylovezj.mall.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductMapper productMapper;


    @Autowired
    CartMapper cartMapper;

    @Override
    public List<CartVo> add(Integer userId, Integer productId, Integer count) {
        validProduct(productId, count);
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            //这个商品之前不在购物车里,需要新增一个记录
            cart = new Cart();
            cart.setProductId(productId);
            cart.setUserId(userId);
            cart.setQuantity(count);
            cart.setSelected(Constant.Cart.CHECKED);
            cartMapper.insertSelective(cart);
        } else {
            //这个商品已经存在了，那么数量相加
            count = cart.getQuantity() + count;
            Cart cartNew = new Cart();
            cartNew.setQuantity(count);
            cartNew.setSelected(Constant.Cart.CHECKED);
            cartNew.setUserId(cart.getUserId());
            cartNew.setProductId(cart.getProductId());
            cartNew.setId(cart.getId());
            cartMapper.updateByPrimaryKeySelective(cartNew);
        }
        return null;
    }

    public void validProduct(Integer productId, Integer count) {
        Product product = productMapper.selectByPrimaryKey(productId);
        //判断商品是否存在，商品是否下架
        if (product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALE)) {
            throw new ThylovezjMallException(ThylovezjMallExceptionEnum.NOT_SALE);
        }
        //判断商品库存
        if (count > product.getStock()) {
            throw new ThylovezjMallException(ThylovezjMallExceptionEnum.NOT_ENOUGH);
        }
    }
}
