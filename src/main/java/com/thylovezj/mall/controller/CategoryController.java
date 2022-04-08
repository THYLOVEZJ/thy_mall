package com.thylovezj.mall.controller;

import com.github.pagehelper.PageInfo;
import com.thylovezj.mall.common.ApiRestResponse;
import com.thylovezj.mall.common.Constant;
import com.thylovezj.mall.exception.ThylovezjMallException;
import com.thylovezj.mall.exception.ThylovezjMallExceptionEnum;
import com.thylovezj.mall.model.pojo.Category;
import com.thylovezj.mall.model.pojo.User;
import com.thylovezj.mall.model.request.AddCategoryReq;
import com.thylovezj.mall.model.request.UpdateCategoryReq;
import com.thylovezj.mall.model.vo.CategoryVO;
import com.thylovezj.mall.service.CategoryService;
import com.thylovezj.mall.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/***
 * 目录Controller
 */

@Controller
public class CategoryController {
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;

    @PostMapping("/admin/category/add")
    @ResponseBody
    public ApiRestResponse addCategory(HttpSession session, @Valid @RequestBody AddCategoryReq addCategoryReq) {
        User user = (User) session.getAttribute(Constant.THYLOVEZJ_MALL_USER);
        if (user == null) {
            return ApiRestResponse.error(ThylovezjMallExceptionEnum.NEED_LOGIN);
        }
        //校验是否是管理员
        if (userService.checkAdminRole(user)) {
            categoryService.add(addCategoryReq);
            return ApiRestResponse.success();
        } else {
            return ApiRestResponse.error(ThylovezjMallExceptionEnum.NEED_ADMIN);
        }
    }

    @PostMapping("/admin/category/update")
    @ResponseBody
    public ApiRestResponse updateCategory(@Valid @RequestBody UpdateCategoryReq updateCategoryReq, HttpSession session) {
        User user = (User) session.getAttribute(Constant.THYLOVEZJ_MALL_USER);
        if (user == null) {
            return ApiRestResponse.error(ThylovezjMallExceptionEnum.NEED_LOGIN);
        }
        //校验是否是管理员
        if (userService.checkAdminRole(user)) {
            Category category = new Category();
            BeanUtils.copyProperties(updateCategoryReq, category);
            categoryService.update(category);
            return ApiRestResponse.success();
        } else {
            return ApiRestResponse.error(ThylovezjMallExceptionEnum.NEED_ADMIN);
        }
    }

    @ApiOperation("后台删除目录")
    @PostMapping("/admin/category/delete")
    @ResponseBody
    public ApiRestResponse deleteCategory(@RequestParam Integer id) {
        categoryService.delete(id);
        return ApiRestResponse.success();
    }


    @ApiOperation("后台目录列表")
    @PostMapping("/admin/category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForAdmin(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo pageInfo = categoryService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }


    @ApiOperation("前台目录列表")
    @PostMapping("/category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForAdmin() {
        List<CategoryVO> categoryVOS = categoryService.listCategoryForCustomer();
        return ApiRestResponse.success(categoryVOS);
    }
}
