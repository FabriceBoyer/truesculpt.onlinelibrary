/* Copyright (c) 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

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
