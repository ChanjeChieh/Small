package com.small.controller.common.interceptor;

import com.google.common.collect.Maps;
import com.small.common.Const;
import com.small.common.ServerResponse;
import com.small.pojo.User;
import com.small.util.CookieUtil;
import com.small.util.JsonUtil;
import com.small.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by skdwj on 2020/4/4.
 */

@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle");
        //请求中Controller中的方法名
        HandlerMethod handlerMethod = (HandlerMethod)handler;

        //解析HandlerMethod
        String methodName = handlerMethod.getMethod().getName();
        String className = handlerMethod.getBean().getClass().getSimpleName();

        //解析参数，具体的参数key以及value是什么，并打印日志
        StringBuffer requestParamBuffer = new StringBuffer();
        Map paramMap = httpServletRequest.getParameterMap();
        Iterator it = paramMap.entrySet().iterator();
        while ( it.hasNext() ) {
            Map.Entry entry = (Map.Entry) it.next();
            String mapkey = (String) entry.getKey();
            String mapValue = StringUtils.EMPTY;
            Object obj = entry.getValue();
            if (obj instanceof String[]) {
                String[] strs = (String[]) obj;
                mapValue = Arrays.toString(strs);
            }
            requestParamBuffer.append(mapkey).append("=").append(mapValue);
        }

        if(StringUtils.equals(className, "UserManageController") && StringUtils.equals(methodName, "login")){
            log.info("权限拦截器拦截到请求,className:{},methodName:{}",className,methodName);
            return true;
        }

        log.info("权限拦截器拦截到请求,className:{},methodName:{},param:{}",className,methodName,requestParamBuffer.toString());

        User user = null;

        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isNotEmpty(loginToken)){
            String userJsonStr = RedisShardedPoolUtil.get(loginToken);
            user = JsonUtil.string2Obj(userJsonStr, User.class);
        }

        if(user == null || (user.getRole().intValue() != Const.Role.ROLE_ADMIN)){
            //返回false，即不会调用controller里的方法
            response.reset();   //否则会报异常：getWriter() has already been called for this response.
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");//设置返回值的类型

            PrintWriter out = response.getWriter();

            //因为富文本上传的控件要求的返回值比较特殊，所以这里进行特殊处理
           if(user == null){
                if(StringUtils.equals(className, "ProductManageController") && StringUtils.equals(methodName, "richtextImageupload")) {
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success", false);
                    resultMap.put("msg", "请登录管理员");
                    out.print(JsonUtil.obj2String(resultMap));
                }else{
                    out.print(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截，用户未登录")));
                }
            } else {
                log.info("权限拦截器拦截到请求,className:{},methodName:{}",className,methodName);
                if(StringUtils.equals(className, "ProductManageController") && StringUtils.equals(methodName, "richtextImageupload")) {
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success", false);
                    resultMap.put("msg", "无权限操作");
                    out.print(JsonUtil.obj2String(resultMap));
                }else{
                    out.print(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截，用户未登录")));
                }
            }
            out.flush();
            out.close();
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        log.info("afterCompletion");
    }
}
