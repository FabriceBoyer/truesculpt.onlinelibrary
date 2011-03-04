<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.truesculpt.onlinelibrary.MediaObject" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" 
  "http://www.w3.org/TR/html4/strict.dtd">
<%
  MediaObject item = (MediaObject) request.getAttribute("item");
  User user = (User) request.getAttribute("user");
  String authURL = (String) request.getAttribute("authURL");
  String displayURL = (String) request.getAttribute("displayURL");
  String blobkey = (String) request.getAttribute("blobkey");
%>
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
<div align="right">
    <%
      if (user != null) {
    %>
     <%= user.getNickname() %>
    <% } %>

    <a href="<%= authURL %>">
      <% if (user != null) { %>Log out<% } else  { %>Log in<% } %>
    </a>
</div>
<br>
<div align="center">
     <a href="javascript:javascript:history.go(-1)"><img src="<%= displayURL %>"></a><br>
     
     <c:set var="filename" value="<%= item.getFilename() %>"/>
     ${fn:escapeXml(filename)}<br>
     
     <c:set var="description" value="<%= item.getDescription() %>"/>
     ${fn:escapeXml(description)}<br>
     
	 <%=item.getSize()/1000%> ko<br>
	 
     <%=item.getCreationTime()%><br>
     
     <!--<%=item.getContentType()%><br>-->
</div>
  
   <br>
  <a href="javascript:javascript:history.go(-1)">Back</a>
</body>
</html>