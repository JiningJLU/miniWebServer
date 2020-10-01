package com.server;

import com.servlet.Servlet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket server;
    private boolean isShutDown = false;
    public void start(int port){
        try {
            server = new ServerSocket(port);
            this.receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receive() {
        try {
            while (!isShutDown) {
                Socket client = server.accept();
                Dispatcher dispatcher = new Dispatcher(client);
                new Thread(dispatcher).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.stop();
        }
    }
    public void stop(){
        isShutDown = true;
        IOcloseUtils.closeAll(server);
    }

    private static void start(){
        new Server().start(8888);
    }

    public static void main(String[] args) {
        start();
    }
}
