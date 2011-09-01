package com.truesculpt.onlinelibrary;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


public class MyHttpServletResponseWrapper extends HttpServletResponseWrapper {

	private StringWriter sw = new StringWriter(8192);
	private ByteArrayOutputStream os = new ByteArrayOutputStream();
	
	public MyHttpServletResponseWrapper(HttpServletResponse response) {
	  super(response);
	}
		
    public ServletOutputStream getOutputStream() throws IOException  
    {  
        return new TeeOutputStream(super.getOutputStream(), os);  
    }  
    
    public PrintWriter getWriter() throws IOException  
    {  
        return new TeeWriter(super.getWriter(), sw);  
    }  
	
	public String toString() {
	  return sw.toString();
	}
}