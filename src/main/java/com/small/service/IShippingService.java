package com.small.service;

import com.github.pagehelper.PageInfo;
import com.small.common.ServerResponse;
import com.small.pojo.Shipping;

/**
 * Created by skdwj on 2020/3/14.
 */
public interface IShippingService {
    ServerResponse add(Integer userId, Shipping shipping);
    ServerResponse del(Integer userId, Integer shippingId);
    ServerResponse update(Integer userId,Shipping shipping);
    ServerResponse<Shipping> select(Integer userId,Integer shippingId);
    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);
}
