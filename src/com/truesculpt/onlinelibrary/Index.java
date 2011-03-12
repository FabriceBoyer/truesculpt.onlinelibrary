package com.truesculpt.onlinelibrary;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Index extends HttpServlet
{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException, ServletException
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		  String strPage= req.getParameter("page");
		  Integer nPageNumber= 0;
		  if (strPage!=null) 
		  { 
		   nPageNumber=Integer.parseInt(strPage);
		  }
		  
		  int nElemsPerPage=10;
		  String strElemsPerPage=req.getParameter("ElemsPerPage");	     
	     if (strElemsPerPage!=null)
	     {
	    	 nElemsPerPage=Integer.parseInt(strElemsPerPage);
	     }
	     
	     int nCurrPage=Index.saturatePageNumber(nPageNumber);
		 
		 int start=nCurrPage*nElemsPerPage;
		 int stop=start+nElemsPerPage;

		 //creation or downloadCount
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
		String strquery = "select from " + MediaObject.class.getName() + " WHERE hasBeenModerated=true order by "+
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
		RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/templates/main.jsp");
		dispatcher.forward(req, resp);
	}
	
	public static int saturatePageNumber(int page)
	{
	  int nRes=page;
	  if (page<0) { nRes=0; }
	  return nRes;
	}
}
