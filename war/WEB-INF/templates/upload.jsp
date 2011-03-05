<%@ page import="com.google.appengine.api.users.User" %>
<%
  User user = (User) request.getAttribute("user");
  String authURL = (String) request.getAttribute("authURL");
  String uploadURL = (String) request.getAttribute("uploadURL");
  String title = (String) request.getAttribute("title");
  String file = (String) request.getAttribute("file");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" 
  "http://www.w3.org/TR/html4/strict.dtd">

<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>Truesculpt Upload</title>
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
    <%= user.getNickname() %> | <a href="<%= authURL %>">Log out</a>
  </div>
  
  <% if (file!="") { %>
  	<img src="<%=file%>">
  <% } %>
  
<div align="left">
  <form action="<%= uploadURL %>" method="POST" enctype="multipart/form-data">
    Title: <input type="text" size="40" name="title" value="<%=title%>"><br>
    Description:<br>
    <textarea cols="80" rows="10" name="description" ></textarea><br>
    Upload File: <input type="file" name="file" value="<%=file%>"><br>
    <input type="submit" name="submit" value="Submit">
  </form>
</div>
  When clicking the submit button you accept to publish your sculpture under the terms of the <a href="http://creativecommons.org/licenses/by-nc-sa/3.0/"> creative commons share alike, non commercial license</a>	
<br>
  <a href="javascript:javascript:history.go(-1)">Cancel</a>
</body>
</html>