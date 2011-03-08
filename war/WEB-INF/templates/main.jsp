<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.truesculpt.onlinelibrary.MediaObject" %>
<%@ page import="com.truesculpt.onlinelibrary.Index" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.Integer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
       List<MediaObject> files = (List<MediaObject>) request.getAttribute("files");
       Integer nCurrPage=(Integer) request.getAttribute("page");
       Boolean bShowNext=(Boolean) request.getAttribute("shownext");
       Boolean bShowPrev=(Boolean) request.getAttribute("showprev");
       String sortBy=(String) request.getAttribute("sortBy");
       String orderBy=(String) request.getAttribute("orderBy");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
  "http://www.w3.org/TR/html4/strict.dtd">

<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
    <title>TrueSculpt</title>
    
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />  
    <script type="text/javascript">
	  var _gaq = _gaq || [];
	  _gaq.push(['_setAccount', 'UA-18915484-4']);
	  _gaq.push(['_trackPageview']);
	
	  (function() {
	    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
	    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
	    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
	  })();	
	</script>
  </head>
  
  <body>
  
  <ul>
    <% 
       String[] errors = (String[]) request.getAttribute("errors");
       for (int i = 0; i < errors.length; i++) { %>
      <li style="color: red"><%= errors[i]%></li>
    <% } %>
  </ul>

	<div align="center">       
	
	
   <form action="" method="get" accept-charset="utf-8">
    Order
    <select name="orderBy" onchange="javascript:form.submit()" size="1">
      <option <% if ("asc".equals(orderBy)) { %>selected<% } %> value="asc">Increasing</option>
      <option <% if ("desc".equals(orderBy)) { %>selected<% } %> value="desc">Decreasing</option>
    </select>
    <input type="hidden" name="page" value="<%= nCurrPage %>">
    <input type="hidden" name="sortBy" value="<%= sortBy %>">
  </form>
	<br> 
  <form action="" method="get" accept-charset="utf-8">
    Sort by
    <select name="sortBy" onchange="javascript:form.submit()" size="1">
      <option <% if ("creation".equals(sortBy)) { %>selected<% } %> value="creation">Date</option>
      <option <% if ("downloadCount".equals(sortBy)) { %>selected<% } %> value="downloadCount">Download</option>
    </select>
    <input type="hidden" name="page" value="<%= nCurrPage %>">
    <input type="hidden" name="orderBy" value="<%= orderBy %>">
  </form>
  <br> 
	

	    <% int nFileCount=files.size();		 
         if ( nFileCount > 0) {%>
         <%for (int i = 0; i < nFileCount; i++) {%>         
            
            <%  MediaObject item = files.get(i); %>		
            		    
            <a href="<%=item.getDisplayURL()%>"> 
            <img src="<%= item.getImageThumbnailURL() %>">
            </a>       
            <br>       
            <c:set var="title" value="<%= item.getTitle() %>"/>
			${fn:escapeXml(title)}
			<br>
			<br>      

         <% } %>
         
       <%} else { %>
            No media found.
      <% } %>
      <br>
  
  		<% if (bShowPrev) { %>
	    <a href="/main?page=<%=nCurrPage-1%>&sortBy=<%=sortBy%>&orderBy=<%=orderBy%>"> 
        <img src="/images/prev.png">
        </a>  
        <% } %>
        
        &nbsp Page <%=nCurrPage+1%>&nbsp
         
        <% if (bShowNext) { %>
	    <a href="/main?page=<%=nCurrPage+1%>&sortBy=<%=sortBy%>&orderBy=<%=orderBy%>"> 
        <img src="/images/next.png">
        </a>  
	    <% } %>

		<br>   
		<br> 
		<br> 
		<br>   		
		
		 All the sculptures are licensed under the terms of the<br>
		 <a href="http://creativecommons.org/licenses/by-nc-sa/3.0/">creative commons share alike, non commercial</a><br>
		 
   </div> 
 
  </body>
</html>
