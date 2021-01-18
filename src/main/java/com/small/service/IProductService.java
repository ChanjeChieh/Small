package com.small.service;

import com.github.pagehelper.PageInfo;
import com.small.common.ServerResponse;
import com.small.pojo.Product;
import com.small.vo.ProductDetailVo;

/**
 * Created by skdwj on 2020/3/5.
 */
public interface IProductService {

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    ServerResponse getProductList(int pageNum, int pageSize);

    ServerResponse<PageInfo> searchProduct(String productName, Integer productId, Integer pageNum, Integer pageSize);

    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword,Integer categoryId,int pageNum,int pageSize,String orderBy);
}
