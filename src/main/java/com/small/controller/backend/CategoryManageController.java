package com.small.controller.backend;

import com.small.common.ResponseCode;
import com.small.common.ServerResponse;
import com.small.pojo.User;
import com.small.service.ICategoryService;
import com.small.service.IUserService;
import com.small.util.CookieUtil;
import com.small.util.JsonUtil;
import com.small.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by skdwj on 2020/3/4.
 */

@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpServletRequest httpServletRequest,String categoryName, @RequestParam(value = "parentId",defaultValue = "0") int parentId){
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)){
//            return ServerResponse.createByErrorMessage("用户未登录，请登录");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);
//        if(user == null){
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()){
//            return iCategoryService.addCategory(categoryName, parentId);
//        }else{
//            return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
//        }

        //拦截器验证中是否登陆、是否为管理员全部通过
        return iCategoryService.addCategory(categoryName, parentId);
    }

    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpServletRequest httpServletRequest,Integer categoryId,String categoryName){
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)){
//            return ServerResponse.createByErrorMessage("用户未登录，请登录");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);
//        if(user == null){
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()) {
//            return iCategoryService.updateCategoryName(categoryId, categoryName);
//        }else{
//            return ServerResponse.createByErrorMessage("无权限登录，需要管理员权限");
//        }

        //拦截器验证中是否登陆、是否为管理员全部通过
        return iCategoryService.updateCategoryName(categoryId, categoryName);
    }

    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpServletRequest httpServletRequest,@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId){
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)){
//            return ServerResponse.createByErrorMessage("用户未登录，请登录");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);
//        if(user == null){
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()) {
//            return iCategoryService.getChildParallelCtegory(categoryId);
//        }else{
//            return ServerResponse.createByErrorMessage("无权限登录，需要管理员权限");
//        }

        //拦截器验证中是否登陆、是否为管理员全部通过
        return iCategoryService.getChildParallelCtegory(categoryId);
    }

    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpServletRequest httpServletRequest,@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId){
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)){
//            return ServerResponse.createByErrorMessage("用户未登录，请登录");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr, User.class);
//        if(user == null){
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()) {
//            return iCategoryService.selectCategoryAndChildrenById(categoryId);
//        }else{
//            return ServerResponse.createByErrorMessage("无权限登录，需要管理员权限");
//        }

        //拦截器验证中是否登陆、是否为管理员全部通过
        return iCategoryService.selectCategoryAndChildrenById(categoryId);
    }

}
