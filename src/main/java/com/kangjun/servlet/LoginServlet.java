package com.kangjun.servlet;

import com.kangjun.web.HttpRequest;
import com.kangjun.web.HttpResponse;
import com.kangjun.web.HttpServlet;

import java.io.IOException;

public class LoginServlet implements HttpServlet {

    public void server(HttpRequest request, HttpResponse response) throws IOException {
        //获取参数
        String username = request.getParamter("username");
        String password = request.getParamter("password");
        if(username !=null && username.equals("kangjun") && password !=null && password.equals("123")){
            //有权限 放行
            response.writerFile("htmlfile/welcome.html");
        }else{
            response.writerFile("htmlfile/error.html");
        }

    }
}
