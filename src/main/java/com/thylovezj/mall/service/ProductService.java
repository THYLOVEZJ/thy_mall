package com.thylovezj.mall.service;

import com.github.pagehelper.PageInfo;
import com.thylovezj.mall.model.pojo.Category;
import com.thylovezj.mall.model.pojo.Product;
import com.thylovezj.mall.model.request.AddCategoryReq;
import com.thylovezj.mall.model.request.AddProductReq;
import com.thylovezj.mall.model.vo.CategoryVO;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * 商品Service
 */
@Service
public interface ProductService {

    void add(AddProductReq addProductReq);

    void update(Product updateProduct);

    void delete(Integer id);
}
