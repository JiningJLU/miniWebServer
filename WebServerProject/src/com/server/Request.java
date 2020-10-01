package com.server;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class Request {
    private InputStream is;
    private String requestInfo;
    private String method;
    private String url;

    public String getUrl() {
        return url;
    }

    private Map<String, List<String>> parametermapValues;
    private static final String CRLF = "\r\n";
    private static final String BLANK = " ";

    public Request(){
        requestInfo = "";
        method = "";
        url = "";
        parametermapValues = new HashMap<>();
    }
    public Request(InputStream is){
        this();
        try {
            this.is = is;
            byte[] buffer = new byte[20480];
            int len = is.read(buffer);
            requestInfo = new String(buffer,0,len);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        this.parseRequestInfo();
    }

    private void parseRequestInfo(){
        String requestParameter = "";
        String firstLine = requestInfo.substring(0,requestInfo.indexOf(CRLF)).trim();
        int index = firstLine.indexOf("/");
        this.method = firstLine.substring(0,index).trim();
        String urlString = firstLine.substring(index,firstLine.indexOf("HTTP/")).trim();
        if("get".equalsIgnoreCase(this.method)){
            if(urlString.contains("?")){
                String[] urlArray = urlString.split("\\?");
                this.url = urlArray[0];
                requestParameter = urlArray[1];
            }
        }
        else {
            this.url = urlString;
            requestParameter = requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
        }
        if(requestParameter.equals(""))
            return;
        //System.out.println(requestParameter);
        //调用请求参数方法
        this.parsePara(requestParameter);
    }

//    public void testShow(){
//        System.out.println(url);
//        System.out.println(method);
//    }
    private void parsePara(String paraString){
        String[] token = paraString.split("&");
        for(int i=0;i<token.length;i++){
            String keyValues = token[i];
            String[] keyValue = keyValues.split("=");
            if(keyValue.length == 1){
                keyValue = Arrays.copyOf(keyValue, 2);
                keyValue[1] = null;
            }
            String key = keyValue[0].trim();
            String value;
            if(keyValue[1] == null)
                value = null;
            else
                value = deCode(keyValue[1].trim(),"utf-8");
            if(!parametermapValues.containsKey(key)){
                parametermapValues.put(key, new ArrayList<>());
            }
            List<String> values = parametermapValues.get(key);
            values.add(value);
        }
    }

    public String[] getParameterValues(String name){
        List<String> values = parametermapValues.get(name);
        if(values.get(0) == null)
            return null;
        else {
            return values.toArray(new String[0]);
        }
    }

    public String getParameterValue(String name){
        String[] result = this.getParameterValues(name);
        if(result == null)
            return null;
        return result[0];
    }

    //浏览器encode中文，需要解码
    private String deCode(String value,String code){
        try {
            return URLDecoder.decode(value, code);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) {
        Request req = new Request();
        req.parsePara("username=jining&pwd=980909&hobby=read");
        System.out.println(req.parametermapValues);

        String str  = req.getParameterValue("hobby");
        System.out.println(str);
    }
}
