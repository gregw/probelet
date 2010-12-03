package com.intalio.jetty.probelet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogFilter implements Filter
{

    ServletContext _context;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        _context=filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest r = (HttpServletRequest)request;
        System.err.println("LOG: "+r.getContextPath()+" "+r.getServletPath()+" "+r.getPathInfo());
        chain.doFilter(request,response);
        
    }

    @Override
    public void destroy()
    {
    }

}
