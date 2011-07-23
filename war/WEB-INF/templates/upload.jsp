<%@ page import="com.google.appengine.api.users.User" %>
<%
  String uploadURL = (String) request.getAttribute("uploadURL"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" 
  "http://www.w3.org/TR/html4/strict.dtd">

<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>Truesculpt Upload</title>
  <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />  
  <script type="text/javascript" src="/scripts/analytics.js" ></script>
</head>
<body>
    
<div align="left">
  <form action="<%= uploadURL %>" method="POST" enctype="multipart/form-data">
    Title: <input type="text" size="40" name="title" value="MySculpture"><br>
    Description:<br>
    <textarea cols="80" rows="10" name="description" ></textarea><br>
    Upload Image file: <input type="file" name="imagefile" ><br>
    Upload Object file: <input type="file" name="objectfile" ><br>
    <input type="submit" name="submit" value="Submit">
  </form>
</div>
  When clicking the submit button you accept to publish your sculpture under the terms of the <a href="http://creativecommons.org/licenses/by-nc-sa/3.0/"> creative commons share alike, non commercial license</a>	
<br>
  <a href="javascript:javascript:history.go(-1)">Cancel</a>
</body>
</html>