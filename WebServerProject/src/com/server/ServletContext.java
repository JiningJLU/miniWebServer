package com.server;

import java.util.HashMap;
import java.util.Map;

public class ServletContext {
    private Map<String,String> servlets;//name--->class
    private Map<String,String> mapping;

    public Map<String, String> getServlets() {
        return servlets;
    }

    public void setServlets(Map<String, String> servlets) {
        this.servlets = servlets;
    }

    public Map<String, String> getMapping() {
        return mapping;
    }

    public void setMapping(Map<String, String> mapping) {
        this.mapping = mapping;
    }

    public ServletContext(){
        this.mapping = new HashMap<>();
        this.servlets = new HashMap<>();
    }
}
