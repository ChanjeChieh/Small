package com.small.service;

import com.small.common.ServerResponse;
import com.small.vo.CartVo;

/**
 * Created by skdwj on 2020/3/11.
 */
public interface ICartService  {
    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);
    ServerResponse<CartVo> update(Integer userId,Integer productId,Integer count);
    ServerResponse<CartVo> deleteProduct(Integer userId,String productIds);
    ServerResponse<CartVo> list(Integer userId);
    ServerResponse<CartVo> selectOrUnSelect(Integer userId,Integer productId,Integer checked);
    ServerResponse<Integer> getCartProductCount(Integer userId);
}
