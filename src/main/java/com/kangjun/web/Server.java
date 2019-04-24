package com.kangjun.web;

import com.kangjun.util.ConfigUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 *
 */
public class Server {
    //handlerMap映射处理组件
    static Map<String,String> handlerMap = null;
    public static void main(String[] args) {
        //发送信息的电话几家 字节流 相当于电话线
        ServerSocket serverSocket = null;
        Socket client = null;
        try {
            //服务发生信息的电报机：ServerSocket 初始化
            serverSocket = new ServerSocket(8888);
            //不断在接收客户端链接
            while (true){
                //服务端阻塞等待客户端的Socket链接过来
                client = serverSocket.accept();
                //===============请求业务类处理范围===============
                InputStream in =client.getInputStream();
                //初始化 请求uri 参数容器
                HttpRequest request = new HttpRequest(in);
                String requesUri = request.getUri();
                //===============响应业务类处理范围===============
                OutputStream os =  client.getOutputStream();
                HttpResponse response = new HttpResponse(os);
                //判断是否是静态资源
                if(isStatic(requesUri)){
                    response.writerFile(requesUri.substring(1));
                }else if(requesUri.endsWith(".action")){
                    //动态处理逻辑封装  约定大于配置
                    processActionRequest("WEB-INF/web.xml",requesUri,request,response);
                }
            }
        }catch(Exception e){

        }
    }

    private static void processActionRequest(String configLocation, String uri, HttpRequest request, HttpResponse response) {
        try {
            handlerMap = ConfigUtils.getClassName(configLocation);
            for(Map.Entry<String,String> entry:handlerMap.entrySet()){
                if(uri.endsWith(entry.getKey())){
                    try {
                        //非常灵活方法 后续扩展
                        HttpServlet processHandler =(HttpServlet)Class.forName(entry.getValue()).newInstance();
                        //子类的实现覆盖父类
                        processHandler.server(request, response);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //静态资源重复获取问题解决
    public static boolean isStatic(String uri){
        boolean isStatic = false;
        //定义一个静态资源的集合
        String [] suffixs={"html","css","jpg","js","jpeg","png"};
        for(String suffix:suffixs){
            if(uri.endsWith("."+suffix)){
                isStatic = true;
                break;
            }
        }
        return isStatic;
    }
}
