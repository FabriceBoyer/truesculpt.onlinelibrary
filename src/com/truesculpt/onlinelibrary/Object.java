package com.truesculpt.onlinelibrary;

import java.io.IOException;
import java.net.URLEncoder;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public class Object extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException, ServletException
	{
		String keyString = req.getParameter("key");
		if (keyString == null || keyString.equals(""))
		{
			resp.sendRedirect("/?error="+ URLEncoder.encode("key not provided", "UTF-8"));
			return;
		}

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Key key=KeyFactory.stringToKey(keyString);
		MediaObject result = pm.getObjectById(MediaObject.class,key);
		if (result==null)
		{
			resp.sendRedirect("/?error="+ URLEncoder.encode("key does not exist", "UTF-8"));
			return;
		}
	
		result.incrementDownloadCount();
		pm.close();
		
		result.serveObject(resp);		
	}
}
