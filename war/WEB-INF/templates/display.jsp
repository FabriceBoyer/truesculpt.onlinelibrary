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
	<script type="text/javascript">
	    function openObjFileInAndroid(name, image, object) 
	    {
	        Android.openObjFileInAndroid(name, image, object);
	    }
	</script>

	<div align="center">
		
		Click on image to import the sculpture into your library <br><br>
		
	     <img src="<%= item.getImageURL()%>" width="100%" onClick="openObjFileInAndroid('<%= item.getTitle() %>', '<%= item.getImageURL()%>', '<%= item.getObjectURL()%>')"><br>
	          
	     <c:set var="title" value="<%= item.getTitle() %>"/>
	     ${fn:escapeXml(title)}<br>
	     
	     <!--
	     <c:set var="description" value="<%= item.getDescription() %>"/>
	     ${fn:escapeXml(description)}<br>
	     -->
	     
		 <%=item.getObjectSize()/1000%> ko<br>
		 
	     <%=item.getCreationTime()%><br>
	     
	     <a href="<%= item.getObjectURL()%>">download as zipped obj file</a>   <br> 
	     
	     <br>
	     <br>
	     licensed under the terms of the <a href="http://creativecommons.org/licenses/by-nc-sa/3.0/">creative commons share alike, non commercial</a>  
	     
	     <br>
	     <br>
   		<a href="javascript:javascript:history.go(-1)">Go back</a>
	</div>
  

</body>
</html>