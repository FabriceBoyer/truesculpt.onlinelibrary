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
public class Index extends HttpServlet
{
	//static boolean bFirstTime=true;
	
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
		
		//temp for test
//		if (bFirstTime)
//		{
//			cache.clear();
//			bFirstTime=false;
//		}

		String key = req.getQueryString();		

		if (cache.containsKey(key)) {
			String value = (String) cache.get(key);
			resp.setContentType("text/html");
            resp.getWriter().println(value);
		}
		else
		{
			PersistenceManager pm = PMF.get().getPersistenceManager();
	
			String strPage = req.getParameter("page");
			Integer nPageNumber = 0;
			if (strPage != null) {
				nPageNumber = Integer.parseInt(strPage);
			}
	
			int nElemsPerPage = 4;
			String strElemsPerPage = req.getParameter("ElemsPerPage");
			if (strElemsPerPage != null) {
				nElemsPerPage = Integer.parseInt(strElemsPerPage);
			}
	
			int nCurrPage = Index.saturatePageNumber(nPageNumber);
	
			int start = nCurrPage * nElemsPerPage;
			int stop = start + nElemsPerPage;
	
			// creation or downloadCount
			String strSortBy=req.getParameter("sortBy");
			if ((strSortBy==null)|| 
				(!(strSortBy.equals("creation")||strSortBy.equals("downloadCount"))))
			{
				strSortBy="creation";
			}
			
			//asc or desc
			String strOrderBy=req.getParameter("orderBy");
			if ((strOrderBy==null) ||
				(!(strOrderBy.equals("asc")||strOrderBy.equals("desc"))))
			{
				strOrderBy="desc";
			}
			String strquery = "select from " + MediaObject.class.getName() + " WHERE hasBeenModerated==true order by "+
							  strSortBy+" "+strOrderBy+" range "+ start+","+stop;
			
			Query query = pm.newQuery(strquery);
	
			List<MediaObject> results = (List<MediaObject>) query.execute();
	
			String[] errors = req.getParameterValues("error");
			if (errors == null) errors = new String[0];
	
			req.setAttribute("errors", errors);
			req.setAttribute("files", results);
			req.setAttribute("page", nCurrPage);
			req.setAttribute("shownext", results.size()==nElemsPerPage);
			req.setAttribute("showprev", nCurrPage!=0);
		    req.setAttribute("sortBy",strSortBy);
		    req.setAttribute("orderBy",strOrderBy);
		    
		    String strTemplate="WEB-INF/templates/main.jsp";
		    
		    String strRawPage=req.getParameter("rawpage");
		    if (strRawPage!=null && strRawPage.equals("true"))
		    {
		    	strTemplate="WEB-INF/templates/page.jsp";	    
		    }
			RequestDispatcher dispatcher = req.getRequestDispatcher(strTemplate);
			
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
