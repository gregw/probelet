package com.intalio.jetty.probelet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DataServlet extends HttpServlet
{
    private static final long serialVersionUID = -749633029152078773L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String result = req.getRemoteAddr()+"-->"+req.getServerName()+":"+req.getRequestURL();
        resp.setHeader("cacheControl","max-age=60,public");
        resp.setContentType("text/plain");
        resp.getOutputStream().println(result);
        System.err.println(result);
    }

}
