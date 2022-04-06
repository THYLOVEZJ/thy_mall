package com.thylovezj.mall.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.thylovezj.mall.exception.ThylovezjMallException;
import com.thylovezj.mall.exception.ThylovezjMallExceptionEnum;
import com.thylovezj.mall.model.dao.CategoryMapper;
import com.thylovezj.mall.model.pojo.Category;
import com.thylovezj.mall.model.request.AddCategoryReq;
import com.thylovezj.mall.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public void add(AddCategoryReq addCategoryReq) {
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq, category);
        Category categoryOld = categoryMapper.selectByName(addCategoryReq.getName());
        if (categoryOld != null) {
            throw new ThylovezjMallException(ThylovezjMallExceptionEnum.NAME_EXIST);
        }
        int count = categoryMapper.insertSelective(category);
        if (count == 0) {
            throw new ThylovezjMallException(ThylovezjMallExceptionEnum.CREATE_FAILED);
        }
    }

    @Override
    public void update(Category category) {
        if (category.getName() != null) {
            Category categoryOdd = categoryMapper.selectByName(category.getName());
            if (categoryOdd != null && !categoryOdd.getId().equals(category.getId())) {
                throw new ThylovezjMallException(ThylovezjMallExceptionEnum.NAME_EXIST);
            }
            int count = categoryMapper.updateByPrimaryKeySelective(category);
            if (count == 0) {
                throw new ThylovezjMallException(ThylovezjMallExceptionEnum.UPDATE_FAILED);
            }
        }
    }


    @Override
    public void delete(Integer id) {
        Category categoryOld = categoryMapper.selectByPrimaryKey(id);
        //查不到记录，无法删除
        if (categoryOld == null) {
            throw new ThylovezjMallException(ThylovezjMallExceptionEnum.DELETE_FAILED);
        }
        int count = categoryMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new ThylovezjMallException(ThylovezjMallExceptionEnum.DELETE_FAILED);
        }
    }


    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize,"type,order_num");
        List<Category> categoryList = categoryMapper.selectList();
        PageInfo pageInfo = new PageInfo(categoryList);
        return pageInfo;
    }



}