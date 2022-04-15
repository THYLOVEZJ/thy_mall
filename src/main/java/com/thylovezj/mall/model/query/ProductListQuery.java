package com.thylovezj.mall.model.query;

import java.util.List;

/**
 * 查询商品列表的Query
 * <p>
 * 目录处理:如果要查某个目录下的商品，不仅是需要查询该目录的，还需要查出来子目录的各种商品
 * <p>
 * 所以要拿到某一个目录Id下的所有子目录id的List
 */
public class ProductListQuery {
    private String keyword;

    private List<Integer> categoryIds;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Integer> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }
}
