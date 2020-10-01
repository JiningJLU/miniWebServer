package com.server;
//<servlet>
//<servlet-name>login</servlet-name>
//<servlet-class>com.servlet.LoginServlet</servlet-class>
//</servlet>
public class Entity {//servlet name and servlet-name's entity class

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public Entity(String name, String clazz) {
        this.name = name;
        this.clazz = clazz;
    }
    public Entity(){
        super();
    }
    private String name;
    private String clazz;
}
