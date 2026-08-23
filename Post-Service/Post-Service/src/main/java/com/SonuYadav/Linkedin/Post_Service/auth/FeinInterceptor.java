package com.SonuYadav.Linkedin.Post_Service.auth;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeinInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        String userId=UserContextHolder.getUserId();
        if (userId!=null){
            template.header("X-User-Id",userId);
        }
    }
}
