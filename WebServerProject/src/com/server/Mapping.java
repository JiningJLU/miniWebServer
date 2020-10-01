package com.server;

/*<servlet-mapping>
<servlet-name>login</servlet-name>
<url-pattern>/login</url-pattern>
</servlet-mapping>
<servlet-mapping>
<servlet-name>login</servlet-name>
<url-pattern>/log</url-pattern>
</servlet-mapping>*/

import java.util.ArrayList;
import java.util.List;

public class Mapping {//映射关系，多个路径去访问共享资源

    public Mapping(){
        urlPattern = new ArrayList<>();
    }

    public Mapping(String name, List<String> urlPattern) {
        this.name = name;
        this.urlPattern = urlPattern;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(List<String> urlPattern) {
        this.urlPattern = urlPattern;
    }

    private String name;
    private List<String> urlPattern;
}
