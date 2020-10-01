package com.servlet;

import com.server.Request;
import com.server.Response;

public class LoginServlet extends Servlet {

    @Override
    public void doGet(Request req, Response rep) throws Exception {
        String name = req.getParameterValue("username");
        String pwd = req.getParameterValue("pwd");
        if(this.login(name, pwd)){
            rep.println(name+"登陆成功");
        }
        else {
            rep.println(name+"登录失败，账号密码不正确");
        }
    }

    private boolean login(String name,String pwd){
        if("jining".equals(name) && "980909".equals(pwd))
            return true;
        return false;
    }

    @Override
    public void doPost(Request req, Response rep) throws Exception {

    }
}
