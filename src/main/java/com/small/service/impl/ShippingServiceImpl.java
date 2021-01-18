package com.small.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.small.common.ServerResponse;
import com.small.dao.ShippingMapper;
import com.small.pojo.Shipping;
import com.small.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by skdwj on 2020/3/14.
 */
@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService{

    @Autowired
    private ShippingMapper shippingMapper;

    public ServerResponse add(Integer userId, Shipping shipping){
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if(rowCount > 0){
            Map result = Maps.newHashMap();
            result.put("shippingId",shipping.getId());
            return ServerResponse.createBySuccess("新建地址成功",result);
        }
        return ServerResponse.createByErrorMessage("新建地址失败");
    }

    public ServerResponse del(Integer userId, Integer shippingId){
        int resultCount = shippingMapper.deleteByShippingIdUserId(userId,shippingId);//防止横向越权
        if(resultCount > 0){
            return ServerResponse.createBySuccess("删除地址成功");
        }
        return ServerResponse.createByErrorMessage("删除地址失败");
    }

    public ServerResponse update(Integer userId,Shipping shipping){
        shipping.setUserId(userId);//防止横向越权
        int rowCount = shippingMapper.updateByShipping(shipping);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("更新地址成功");
        }
        return ServerResponse.createByErrorMessage("更新地址失败");
    }

    public ServerResponse<Shipping> select(Integer userId,Integer shippingId){
        Shipping shipping = shippingMapper.selectByShippingIdUserId(shippingId,userId);
        if(shipping == null){
            return ServerResponse.createByErrorMessage("无法查询到该地址");
        }
        return ServerResponse.createBySuccess("更新地址成功", shipping);
    }

    public ServerResponse<PageInfo> list(Integer userId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectByuserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
