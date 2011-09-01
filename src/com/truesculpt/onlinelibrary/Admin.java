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
public class Admin extends HttpServlet
{
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException, ServletException
	{
		String keyString = req.getParameter("key");
		if (keyString == null || keyString.equals(""))
		{
			String strquery = "select from " + MediaObject.class.getName() + " WHERE hasBeenModerated==false range 0,1";

			PersistenceManager pm = PMF.get().getPersistenceManager();
			
			Query query = pm.newQuery(strquery);
			
			List<MediaObject> results = (List<MediaObject>) query.execute();
			
			if (results.size()>0)
			{
				MediaObject obj=results.get(0);
				String strKey = KeyFactory.keyToString(obj.getKey());
				resp.sendRedirect("/admin?key="+strKey);
			}
			else
			{
		        resp.setContentType("text/plain");
	            resp.getWriter().println("No unmoderated sculptures left");
			}
		}
		else
		{
			PersistenceManager pm = PMF.get().getPersistenceManager();
			Key key=KeyFactory.stringToKey(keyString);
			MediaObject result = pm.getObjectById(MediaObject.class,key);
			if (result==null)
			{
				resp.sendRedirect("/?error="+ URLEncoder.encode("key does not exist", "UTF-8"));
				return;
			}
		
			Boolean bAccept=req.getParameter("accept")!=null;
			Boolean bReject=req.getParameter("reject")!=null;
			if( bAccept)
			{
				result.setHasBeenModerated(true);
				resp.sendRedirect("/admin");
				
				//clears invalid cache
				try {
					Cache  cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
					cache.clear();
				} catch (CacheException e) {
					resp.sendRedirect("/?error="+ URLEncoder.encode("cache not initialized", "UTF-8"));
				}				
			}
			else if (bReject)
			{
				BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
				
				blobstoreService.delete(result.getImageBlob());
				blobstoreService.delete(result.getObjectBlob());
				pm.deletePersistent(result);
				resp.sendRedirect("/admin");
			}
			else//show
			{
				req.setAttribute("item", result);
				RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/templates/admin.jsp");
				dispatcher.forward(req, resp);
			}
			pm.close();
		}


	}
}
