package com.server;

import com.servlet.Servlet;

import java.io.IOException;
import java.net.Socket;

public class Dispatcher implements Runnable {
    private Request req;
    private Response rep;
    private Socket client;
    private int code = 200;
    public Dispatcher(Socket client){
        this.client = client;
        try {
            req = new Request(client.getInputStream());
            rep = new Response(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            code = 500;
            return;
        }
    }
    @Override
    public void run() {
        //根据不同URL获取指定对象
        Servlet servlet = WebApp.getServlet(req.getUrl());
        if(servlet == null){
            this.code = 404;
        }else {
            try {
                servlet.service(req, rep);
            } catch (Exception e) {
                code = 500;
            }
        }
        rep.pushToClient(500);
        IOcloseUtils.closeAll(client);
    }
}
