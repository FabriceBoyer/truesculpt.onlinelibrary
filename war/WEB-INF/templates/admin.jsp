<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.truesculpt.onlinelibrary.MediaObject" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" 
  "http://www.w3.org/TR/html4/strict.dtd">
<%
  MediaObject item = (MediaObject) request.getAttribute("item");
%>
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
  <title>TrueSculpt viewer</title>
  <link type="text/css" rel="stylesheet" href="/stylesheets/main.css">  
  <script type="text/javascript" src="/scripts/analytics.js" ></script>
</head>
<body>

	<div align="center">		
		
	     <img src="<%= item.getImageURL()%>" onClick="openObjFileInAndroid('<%= item.getTitle() %>', '<%= item.getImageURL()%>', '<%= item.getObjectURL()%>')"><br>
	          
	     <c:set var="title" value="<%= item.getTitle() %>"/>
	     ${fn:escapeXml(title)}<br>
	     
	     <!--
	     <c:set var="description" value="<%= item.getDescription() %>"/>
	     ${fn:escapeXml(description)}<br>
	     -->
	     
		 <%=item.getObjectSize()/1000%> ko<br>
		 
	     <%=item.getCreationTime()%><br>
	     
	     Downloaded <%=item.getDownloadCount()%> times<br>
	     
	     <a href="<%= item.getAcceptURL()%>">Accept</a>   
	     &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp	
	     <a href="<%= item.getRejectURL()%>">Reject</a>      
	    
	</div>
  
</body>
</html>