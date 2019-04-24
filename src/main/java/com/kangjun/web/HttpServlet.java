package com.kangjun.web;

import java.io.IOException;

/**
 * HttpServlet：规范网络请求处理的
 */
public interface HttpServlet {
    //处理HttpServlet
	public void server(HttpRequest request,HttpResponse response) throws IOException;
}