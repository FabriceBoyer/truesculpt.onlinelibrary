<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
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
<body onload="modelviewer(<%= item.getObjectURL()%>)">
	<script type="text/javascript">
	    function openObjFileInAndroid(name, image, object, size) 
	    {
	        Android.openObjFileInAndroid(name, image, object, size);
	    }
	</script>

	<div align="center">
		
		<%if (item.getHasBeenModerated()==false){%>
			<font color="#FF0000">
				Your sculpture have been correctly uploaded but has not been moderated yet<br>
			</font>
				It will be publicly available in the library once it has been manually approved by the manager<br>
				This sculpture can be rejected without notice if is considered unappropriate to public viewing<br>
			
		<%}%>
		
		<a href="" onClick="openObjFileInAndroid('<%= item.getTitle() %>', '<%= item.getImageURL()%>', '<%= item.getObjectURL()%>', '<%= item.getObjectSize()%>' )">Click here to import this sculpture into your library</a> 
		<br><br>
		<c:set var="title" value="<%= item.getTitle() %>"/>
		
	     <img src="<%= item.getImageURL()%>" alt="${fn:escapeXml(title)}" width=300><br>	          
	     
	     ${fn:escapeXml(title)}<br>
	     
	     <!--
	     <c:set var="description" value="<%= item.getDescription() %>"/>
	     ${fn:escapeXml(description)}<br>
	     -->
	     
		 <%=item.getObjectSize()/1000%> ko<br>
		 
	     <%=item.getCreationTime()%><br>
	     
	     Downloaded <%=item.getDownloadCount()%> times<br>
	     
	     <%if (item.getIsFeatured()==true){%>
	     This sculpture has been marked as "featured" by the application manager<br>
	     <%}else{%>
	      <!--TODO only for admin user -->
	     <a href="/feature?key="<%KeyFactory.keyToString(item.getKey());%>>Feature this sculpture</a>  
	     <%}%>
	     
	     <a href="<%= item.getObjectURL()%>">Download as zipped obj file</a>   
	     <br>
	     <canvas id="modelviewer" width="200" height="200"></canvas>	  
	     <p id="statustext"></p>   
	     <br>
   		 <a href="javascript:javascript:history.go(-1)">Go back</a>
	     
	     <br>
	     <br>
	     <br>
	     Licensed under the terms of the <br>
	     <a href="http://creativecommons.org/licenses/by-nc-sa/3.0/">creative commons share alike, non commercial</a>  
	     
	     <br>	    
	</div>
</body>
</html>