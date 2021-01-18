package com.small.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by skdwj on 2020/4/4.
 */
@Slf4j
@Component
public class ExceptionResolver implements HandlerExceptionResolver{

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        log.error("{} Exception",httpServletRequest.getRequestURL(), e);
        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());

        //当使用Jackson2.x的时候使用MappingJackson2JsonView

        modelAndView.addObject("data", e.toString());
        modelAndView.addObject("msg", "接口异常，详情请查看服务端日志的异常信息");
        modelAndView.addObject("status:", ResponseCode.ERROR.getCode());

        return modelAndView;
    }
}
