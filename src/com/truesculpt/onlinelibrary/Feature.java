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

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import java.util.Collections;
import net.sf.jsr107cache.*;

@SuppressWarnings("serial")
public class Feature extends HttpServlet
{
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException, ServletException
	{
		String keyString = req.getParameter("key");
		if (keyString != null && !keyString.equals(""))
		{
			PersistenceManager pm = PMF.get().getPersistenceManager();
			
			Key key=KeyFactory.stringToKey(keyString);
			MediaObject result = pm.getObjectById(MediaObject.class,key);
			if (result==null)
			{
				resp.sendRedirect("/?error="+ URLEncoder.encode("key does not exist", "UTF-8"));
				return;
			}
			
			result.setIsFeatured(true);
			
			//clears invalid cache
			try {
				Cache cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
				cache.clear();
			} catch (CacheException e) {
				resp.sendRedirect("/?error="+ URLEncoder.encode("cache not initialized", "UTF-8"));
			}	
			
			resp.sendRedirect("/main");

			pm.close();
		}

	}
}
