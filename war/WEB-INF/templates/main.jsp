<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.truesculpt.onlinelibrary.MediaObject"%>
<%@ page import="com.truesculpt.onlinelibrary.Index"%>
<%@ page import="java.util.List"%>
<%@ page import="java.lang.Integer"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
       Integer nCurrPage=(Integer) request.getAttribute("page");
       Boolean bShowNext=(Boolean) request.getAttribute("shownext");
       Boolean bShowPrev=(Boolean) request.getAttribute("showprev");
       String sortBy=(String) request.getAttribute("sortBy");
       String orderBy=(String) request.getAttribute("orderBy");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"  "http://www.w3.org/TR/html4/strict.dtd">

<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
	<title>TrueSculpt</title>	
	<link type="text/css" rel="stylesheet" href="/stylesheets/main.css">
    <script type="text/javascript" src="/scripts/analytics.js"></script>
    <script type="text/javascript" src="/scripts/scroll.js"></script>
</head>

<body onload="setInterval('scroll();', 250);">

	<ul>
		<% 
       String[] errors = (String[]) request.getAttribute("errors");
       for (int i = 0; i < errors.length; i++) { %>
		<li style="color: red"><%= errors[i]%></li>
		<% } %>
	</ul>

	<div align="center" id="filters">
	
		<form action="" method="get" accept-charset="utf-8">
			<select name="sortBy" onchange="javascript:form.submit()" size="1">
				<option <% if ("creation".equals(sortBy)) { %> selected <% } %>
					value="creation">Date</option>
				<option <% if ("downloadCount".equals(sortBy)) { %> selected <% } %>
					value="downloadCount">Download</option>
			</select>			 
			<select name="orderBy" onchange="javascript:form.submit()"
				size="1">
				<option <% if ("asc".equals(orderBy)) { %> selected <% } %>
					value="asc">Increasing</option>
				<option <% if ("desc".equals(orderBy)) { %> selected <% } %>
					value="desc">Decreasing</option>
			</select> 
			<input type="hidden" name="page" value="0">
		</form>
		<br>		
	</div>
	
	<div align="center" id="container">
 
	</div>
	
	<div align="center" id="waiter">
	 <img src="/images/loading.gif" alt="Loading">
	 <h1>Loading ...</h1>
	</div>
	
	<% boolean bShowNavigation =false;
	   if (bShowNavigation) { %> 
	<div align="center" id="navigation">
		
		<% if (bShowPrev) { %>
		<a href="/main?page=<%=nCurrPage-1%>&sortBy=<%=sortBy%>&orderBy=<%=orderBy%>" style="text-decoration: none">
			<img src="/images/prev.png" alt="Previous"> 
		</a>
		<% } %>

		&nbsp 

		<% if (bShowNext) { %>
		<a href="/main?page=<%=nCurrPage+1%>&sortBy=<%=sortBy%>&orderBy=<%=orderBy%>" style="text-decoration: none">
			<img src="/images/next.png" alt="Next">
		 </a>
		<% } %>

		<br>
		Page <%=nCurrPage+1%>&nbsp
		<br>
	</div>
	<%} %>
	
	<div align="center" id="footer">
		 <br>All the sculptures are licensed under the terms of the<br>
		 <a	href="http://creativecommons.org/licenses/by-nc-sa/3.0/">
		 creative commons share alike, non commercial
		 </a>
		 <br>		 
	</div>

</body>
</html>
