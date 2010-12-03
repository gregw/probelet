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

public class AppletFilter implements Filter
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
        try
        {
            String servletPath=((HttpServletRequest)request).getServletPath();
            _context.getRequestDispatcher("/WEB-INF/classes"+servletPath).forward(request,response);  
        }
        catch(Exception e)
        {
            throw new ServletException(e);
        }
        
    }

    @Override
    public void destroy()
    {
        // TODO Auto-generated method stub
        
    }

}
