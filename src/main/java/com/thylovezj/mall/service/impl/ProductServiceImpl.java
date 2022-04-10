package com.thylovezj.mall.service.impl;

import com.thylovezj.mall.exception.ThylovezjMallException;
import com.thylovezj.mall.exception.ThylovezjMallExceptionEnum;
import com.thylovezj.mall.model.dao.ProductMapper;
import com.thylovezj.mall.model.pojo.Product;
import com.thylovezj.mall.model.request.AddProductReq;
import com.thylovezj.mall.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * 商品服务实现类
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;

    @Override
    public void add(AddProductReq addProductReq) {
        Product product = new Product();
        BeanUtils.copyProperties(addProductReq, product);
        Product productOld = productMapper.selectByName(addProductReq.getName());
        if (productOld != null) {
            throw new ThylovezjMallException(ThylovezjMallExceptionEnum.NAME_EXIST);
        }
        int count = productMapper.insertSelective(product);
        if (count == 0) {
            throw new ThylovezjMallException(ThylovezjMallExceptionEnum.CREATE_FAILED);
        }
    }

    @Override
    public void update(Product updateProduct) {
        Product productOld = productMapper.selectByName(updateProduct.getName());
        //同名且不同id，不能继续修改
        if (productOld != null && productOld.getId() != updateProduct.getId()) {
            throw new ThylovezjMallException(ThylovezjMallExceptionEnum.NAME_EXIST);
        }
        int count = productMapper.updateByPrimaryKeySelective(updateProduct);
        if (count == 0) {
            throw new ThylovezjMallException(ThylovezjMallExceptionEnum.UPDATE_FAILED);
        }
    }


    @Override
    public void delete(Integer id) {
        Product productOld = productMapper.selectByPrimaryKey(id);
        //查不到该记录，无法删除
        if (productOld == null) {
            throw new ThylovezjMallException(ThylovezjMallExceptionEnum.DELETE_FAILED);
        }
        int count = productMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new ThylovezjMallException(ThylovezjMallExceptionEnum.DELETE_FAILED);
        }
    }
}
