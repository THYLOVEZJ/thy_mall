package com.thylovezj.mall.service;

import com.github.pagehelper.PageInfo;
import com.thylovezj.mall.model.pojo.Category;
import com.thylovezj.mall.model.request.AddCategoryReq;
import com.thylovezj.mall.model.vo.CategoryVO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    void add(AddCategoryReq addCategoryReq);

    void update(Category category);


    void delete(Integer id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);


    @Cacheable(value = "listCategoryForCustomer")
    List<CategoryVO> listCategoryForCustomer(Integer parentId);

    void recursivelyFindCategories(List<CategoryVO> categoryVOArrayList, Integer parentId);
}
