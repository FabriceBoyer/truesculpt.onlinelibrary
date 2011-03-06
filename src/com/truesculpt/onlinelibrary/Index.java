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

		String strquery = "select from " + MediaObject.class.getName() + " order by creation desc range 0,100";
		Query query = pm.newQuery(strquery);

		List<MediaObject> results = (List<MediaObject>) query.execute();

		String[] errors = req.getParameterValues("error");
		if (errors == null) errors = new String[0];

		req.setAttribute("errors", errors);
		req.setAttribute("files", results);
		RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/templates/main.jsp");
		dispatcher.forward(req, resp);
	}
	
	public static int saturatePageNumber(int page, int nMaxPageCount)
	{
	  int nRes=page;
	  if (page>nMaxPageCount) { nRes=nMaxPageCount; }
	  if (page<0) { nRes=0; }
	  return nRes;
	}
}
