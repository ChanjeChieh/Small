package com.small.controller.backend;

import com.small.common.Const;
import com.small.common.ServerResponse;
import com.small.pojo.User;
import com.small.service.IUserService;
import com.small.util.CookieUtil;
import com.small.util.JsonUtil;
import com.small.util.RedisShardedPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.monitor.CounterMonitor;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by skdwj on 2020/3/3.
 */

@Controller
@RequestMapping("/manage/user")
public class UserManageController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse httpServletResponse) {
        ServerResponse<User> response = iUserService.login(username, password);
        if(response.isSuccess()){
            User user = response.getData();
            if(user.getRole() == Const.Role.ROLE_ADMIN){
//                session.setAttribute(Const.CURRENT_USER,user);
                //新增redis共享cookie，session的方式
                CookieUtil.writeLoginToken(httpServletResponse, session.getId());
                RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2String(response.getData()),Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
                return response;
            }else{
                return ServerResponse.createByErrorMessage("不是管理员无法登录");
            }
        }
        return response;
    }
}
