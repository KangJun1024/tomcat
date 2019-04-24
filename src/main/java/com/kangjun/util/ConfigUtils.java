package com.kangjun.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  Function: 读取配置文件类 解析web.xml(dom4j) 注册容器
 *  key: url-pattern  唯一标识url
 *  value: servlet-class 类的全限定名 用于反射
 */
public class ConfigUtils {

    //类似Spring MVC handlerMap 根据url 找到对应后台处理器handler
    public static Map<String,String> getClassName(String path) throws Exception{
        //定义容器
        Map<String,String> handlerMap = new ConcurrentHashMap<String, String>();
        //定义dom4j对象
        SAXReader Reader = new SAXReader();
        //获取文件
        File file = new File(path);
        Document document = Reader.read(file);
        //获得根元素
        Element rootElement = document.getRootElement();
        List<Element> childElenments = rootElement.elements();
        for(Element childElenment:childElenments){
            String key = childElenment.element("url-pattern").getText();
            String value = childElenment.element("servlet-class").getText();

            handlerMap.put(key,value);
        }
        return handlerMap;
    }
}















