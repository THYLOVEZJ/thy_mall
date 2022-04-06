package com.thylovezj.mall.service;

import com.github.pagehelper.PageInfo;
import com.thylovezj.mall.model.pojo.Category;
import com.thylovezj.mall.model.request.AddCategoryReq;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    void add(AddCategoryReq addCategoryReq);

    void update(Category category);


    void delete(Integer id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);
}
