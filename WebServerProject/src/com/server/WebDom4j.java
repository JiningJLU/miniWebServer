package com.server;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WebDom4j {
    private List<Entity> entityList;//用于村塾N多Entity 每一个Entity都是servlet-name和servlet-class
    private List<Mapping> mappingList;//存储N多mapping 每一个mapping都是一个servlet-name和url-pattern

    public WebDom4j(){
        entityList = new ArrayList<>();
        mappingList = new ArrayList<>();
    }

    public WebDom4j(List<Entity> entityList, List<Mapping> mappingList) {
        this.entityList = entityList;
        this.mappingList = mappingList;
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<Entity> entityList) {
        this.entityList = entityList;
    }

    public List<Mapping> getMappingList() {
        return mappingList;
    }

    public void setMappingList(List<Mapping> mappingList) {
        this.mappingList = mappingList;
    }

    public Document getDocument(){
        try{SAXReader reader = new SAXReader();
        return reader.read(
                new File("G:\\JavaCodes\\WebServerProject\\src\\WEB_INFO\\web.xml"));}
        catch (DocumentException e){
            e.printStackTrace();
        }
        return null;
    }

    public void parse(Document doc) {
        //1 获取根元素
        Element root = doc.getRootElement();
        //2 获取servlet子元素
        for (Iterator<Element> it = root.elementIterator("servlet"); it.hasNext(); ) {
            Element subElement = it.next();
            Entity ent = new Entity();//servlet-name or servlet class
            for (Iterator<Element> subit = subElement.elementIterator(); subit.hasNext(); ) {
                Element ele = subit.next();
                if ("servlet-name".equals(ele.getName())) {
                    ent.setName(ele.getText());
                } else if ("servlet-class".equals(ele.getName())) {
                    ent.setClazz(ele.getText());
                }
            }
            entityList.add(ent);
        }
//        for(Entity et:entityList){
//            System.out.println(et.getName() + '\t' + et.getClazz());
//        }
        for(Iterator<Element> ite = root.elementIterator("servlet-mapping");ite.hasNext();){
            Element subEle = ite.next();
            Mapping map = new Mapping();
            for(Iterator<Element> subite = subEle.elementIterator();subite.hasNext();){
                Element ele = subite.next();
                if ("servlet-name".equals(ele.getName())) {
                    map.setName(ele.getText());
                } else if ("url-pattern".equals(ele.getName())) {
                    map.getUrlPattern().add(ele.getText());
                }
            }
            mappingList.add(map);
        }
//        for(Mapping m :mappingList){
//            System.out.println(m.getName());
//            for(String s:m.getUrlPattern())
//                System.out.println(s);
//        }
    }

    public static void main(String[] args) {
        WebDom4j web = new WebDom4j();
        web.parse(web.getDocument());
    }
}
