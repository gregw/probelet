package com.intalio.jetty.probelet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ResultServlet extends HttpServlet
{
    private static final long serialVersionUID = -749633029152078773L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        InputStream in =req.getInputStream();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        int len=0;
        while ((len=in.read(buf))>=0)
            out.write(buf,0,len);
        
        String result = new String(out.toByteArray());
        
        System.err.println("=============\nRESULT: "+req.getPathInfo()+"\n---\n"+result+"\n=============");
        
    }

}
