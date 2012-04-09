package com.truesculpt.onlinelibrary;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Collections;
import net.sf.jsr107cache.*;


@SuppressWarnings("serial")
public class XMLData extends HttpServlet
{
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException, ServletException
	{
		Cache cache = null;

		try {
			cache = CacheManager.getInstance().getCacheFactory()
					.createCache(Collections.emptyMap());
		} catch (CacheException e) {
			resp.sendRedirect("/?error="+ URLEncoder.encode("cache not initialized", "UTF-8"));
		}

		String key = req.getQueryString();		

		/*
		if (cache.containsKey(key)) {
			String value = (String) cache.get(key);
			resp.setContentType("text/xml");
            resp.getWriter().println(value);
		}
		else*/
		{
			PersistenceManager pm = PMF.get().getPersistenceManager();
				
			String strquery = "SELECT FROM " + MediaObject.class.getName() + " WHERE hasBeenModerated==true ";
			
			Query query = pm.newQuery(strquery);
	
			List<MediaObject> results = (List<MediaObject>) query.execute();
	
			String[] errors = req.getParameterValues("error");
			if (errors == null) errors = new String[0];
	
			req.setAttribute("errors", errors);
			req.setAttribute("files", results);
		    		 
			RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/templates/xmlData.jsp");
			
			MyHttpServletResponseWrapper wrapper = new MyHttpServletResponseWrapper(resp);
			dispatcher.forward(req, wrapper);
			
			//update cache
			String value = wrapper.toString();
			cache.put(key,value);
		}
	}
	
	public static int saturatePageNumber(int page)
	{
	  int nRes=page;
	  if (page<0) { nRes=0; }
	  return nRes;
	}
}
