package com.intalio.jetty.probelet;

import java.applet.Applet;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;

public class Probelet extends Applet
{
    private final static String CONNECT_HOST="127.0.0.2";
    private final static String SPOOF_HOST="127.0.0.3";
    private final static int PORT=8080;
    
    private Image offScreenImage;
    
    private Image image;
    private int w=150;
    private int h=150;
    
    public String getAppletInfo()
    {
        return "MortBayLogo V1.0, (C)opyright 1996 Greg Wilkins";
    }

    public void init()
    {
        //image = getImage(getDocumentBase(), 
        //                 "images/mbLogoMosaic.gif");

        System.out.println ("Classloader = "+this.getClass().getClassLoader());
        
        URL imgURL = this.getClass().getClassLoader().getResource("probe.gif");

        System.out.println (imgURL.toString());
        
        image = getImage (imgURL);
        
        offScreenImage = createImage(w=getSize().width, h=getSize().height);
        offScreenImage.getGraphics().drawImage(image, 0, 0, this);
        resize(w,h);
    }

    public void start()
    {
        repaint();

        try
        {
            Socket socket = new Socket(CONNECT_HOST,PORT);
            socket.getOutputStream().write((
                    "GET /data/test1 HTTP/1.1\r\nHost:"+CONNECT_HOST+":"+PORT+"\r\n\r\n" +
                    "GET /data/test2 HTTP/1.1\r\nHost:"+CONNECT_HOST+":"+PORT+"\r\n\r\n" +
                    "GET /data/test3 HTTP/1.1\r\nHost:"+SPOOF_HOST+":"+PORT+"\r\n\r\n" +
                    "GET /data/test3 HTTP/1.1\r\nHost:"+CONNECT_HOST+":"+PORT+"\r\nConnection:close\r\n\r\n"
            ).getBytes());
            
            byte[] responseA = read(socket.getInputStream());

            socket = new Socket(CONNECT_HOST,PORT);
            socket.getOutputStream().write((
                    "POST /result/A HTTP/1.1\r\n"+
                    "Host:"+CONNECT_HOST+":"+PORT+"\r\n"+
                    "Content-Type: text/plain\r\n"+
                    "Content-Length:"+responseA.length+":"+PORT+"\r\n"+
                    "\r\n")
                    .getBytes());
            socket.getOutputStream().write(responseA);
            socket.getOutputStream().flush();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void stop()
    {
    }
    
    
    private byte[] read(InputStream in) throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        int len=0;
        while ((len=in.read(buf))>=0)
        {
            out.write(buf,0,len);
        }
        return out.toByteArray();
    }

    public void paintLogo(Image i)
    {
        Graphics g = i.getGraphics();
        g.clipRect(0,0,w,h);
        g.drawImage(image, 0, 0, this);
        
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    public void paint(Graphics g)
    {
        paintLogo(offScreenImage);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    public Probelet()
    {
    }
}


