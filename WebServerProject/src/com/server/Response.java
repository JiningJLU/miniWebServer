package com.server;

import java.io.*;

public class Response {
    private StringBuilder headInfo;
    private StringBuilder content;
    private int length;
    private BufferedWriter bw;
    private static final String CRLF = "\r\n";
    private static final String BLANK = " ";
    public Response(){
        headInfo = new StringBuilder();
        content = new StringBuilder();
    }
    public Response(OutputStream os){
        this();
        try {
            bw = new BufferedWriter(new OutputStreamWriter(os,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            headInfo = null;
        }
    }

    //正文部分
    public Response print(String Info){
        content.append(Info);
        try {
            length += Info.getBytes("utf-8").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Response println(String Info){
        content.append(Info).append(CRLF);
        try {
            length += (CRLF+Info).getBytes("utf-8").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

    //构造响应头
    private void createHeadInfo(int code){
        headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
        switch (code){
            case 200:
                headInfo.append("OK");
                break;
            case 500:
                headInfo.append("SERVER ERROR");
                break;
            default:
                headInfo.append("NOT FOUND");
                break;
        }
        headInfo.append(CRLF);
        headInfo.append("Content-Type:text/html;charset=utf-8").append(CRLF);
        headInfo.append("Content-Length:"+length).append(CRLF);
        headInfo.append(CRLF);
    }

    public void pushToClient(int code){
        if(headInfo == null)
            code = 500;
        this.createHeadInfo(code);
        try {
            bw.write(headInfo.toString());
            bw.write(content.toString());
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.close();
    }
    private void close(){
        IOcloseUtils.closeAll(bw);
    }
}
