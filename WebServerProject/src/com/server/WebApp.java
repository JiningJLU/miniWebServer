package com.server;

import com.servlet.Servlet;

import java.util.List;
import java.util.Map;

public class WebApp {
    private static ServletContext context;
    static {
        context = new ServletContext();
        //分别获取对应关系的map集合
        Map<String,String> servlet = context.getServlets();
        Map<String,String> mapping = context.getMapping();
        //创建解析XML文件对象
        WebDom4j w4 = new WebDom4j();
        w4.parse(w4.getDocument());
        //获取解析XML之后的list集合
        List<Entity> entityList = w4.getEntityList();
        List<Mapping> mappingList = w4.getMappingList();

        for(Entity entity:entityList){
            servlet.put(entity.getName(), entity.getClazz());
        }
        System.out.println(servlet);
        for(Mapping map:mappingList){
            List<String> urlPattern = map.getUrlPattern();
            for(String s:urlPattern){
                mapping.put(s,map.getName());
            }
        }
        //System.out.println(mapping);
    }

    public static Servlet getServlet(String url){
        if(url == null || url.trim().equals("")){
            return null;
        }
        try {
            String servletName = context.getMapping().get(url);
            String servletClass = context.getServlets().get(servletName);
            Class<?> clazz= Class.forName(servletClass);
            Servlet servlet = (Servlet)clazz.newInstance();
            return servlet;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getServlet("/log"));
    }
}
