package com.hslzk.share.shiro;

import com.hslzk.share.jwt.JwtToken;
import com.hslzk.share.util.ApiResultUtil;
import com.hslzk.share.util.GsonUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.hslzk.share.constants.Constants.APPLICATION_JSON;

public class ShiroFilter extends BasicHttpAuthenticationFilter {

    /**
     * 表示是否允许访问，如果允许访问返回true，否则false；
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        System.out.println("isAccessAllowed");
        if (isLoginAttempt(request, response)) {
            return executeLogin(request, response);
        }
        try {
            response.setContentType(APPLICATION_JSON);
            response.getOutputStream().write(GsonUtil.beanToJson(ApiResultUtil.error(10003, "Token必须携带！")).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        System.out.println("isLoginAttempt");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        return authorization != null;
    }

    /**
     * 执行登陆方法
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        System.out.println("executeLogin");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        JwtToken jwtToken = new JwtToken(authorization);
        // 提交给realm进行登入，如果错误它会抛出异常并被捕获
        try {
            getSubject(request, response).login(jwtToken);
        } catch (AuthenticationException a) {
            System.out.println("AuthenticationException");
            try {
                response.setContentType(APPLICATION_JSON);
                response.getOutputStream().write(GsonUtil.beanToJson(ApiResultUtil.error(10004, "Token认证失败！")).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        System.out.println("onAccessDenied");
        return false;
    }

}
