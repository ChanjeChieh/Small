package com.small.service;

import com.small.common.ServerResponse;
import com.small.pojo.Category;

import java.util.List;

/**
 * Created by skdwj on 2020/3/4.
 */
public interface ICategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId,String categoryName);

    ServerResponse<List<Category>> getChildParallelCtegory(Integer categoryId);

    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
