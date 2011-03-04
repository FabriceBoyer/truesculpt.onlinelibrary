<%@ page import="com.google.appengine.api.users.User" %>
<%
  User user = (User) request.getAttribute("user");
  String authURL = (String) request.getAttribute("authURL");
  String uploadURL = (String) request.getAttribute("uploadURL");
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
  
<div align="left">
  <form action="<%= uploadURL %>" method="POST" enctype="multipart/form-data">
    Sharing: <select name="share">
      <option value="private">Private</option>
          <option value="public">Public</option>
        </select>
    Title: <input type="text" size="40" name="title" value="MySculpture"><br>
    Description:<br>
    <textarea cols="80" rows="20" name="description" ></textarea><br>
    Upload File: <input type="file" name="file"><br>
    <input type="submit" name="submit" value="Submit">
  </form>
</div>
  <hr>
  <a href="javascript:javascript:history.go(-1)">Cancel</a>
</body>
</html>