package com.zwj.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri=request.getRequestURI();
        if(uri.equals("/oauth/token")||uri.equals("/oauth/login")){
            filterChain.doFilter(request,response);
        }else{
            boolean access_token=false;
            boolean authorization=false;
            if(request.getParameter("access_token")==null){
                access_token=true;
            }
            if(request.getHeader("Authorization")==null){
                authorization=true;
            }else{
                if(!request.getHeader("Authorization").startsWith("Bearer")){
                    authorization=true;
                }
            }
            if(access_token&&authorization){
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(JSONObject.toJSONString("未获得凭证！"));
            }else{
                filterChain.doFilter(request,response);
            }
        }
    }
}
