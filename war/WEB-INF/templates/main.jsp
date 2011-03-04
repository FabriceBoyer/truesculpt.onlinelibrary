<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.truesculpt.onlinelibrary.MediaObject" %>
<%@ page import="java.util.List" %>
<%
  User user = (User) request.getAttribute("user");
  String authURL = (String) request.getAttribute("authURL");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
  "http://www.w3.org/TR/html4/strict.dtd">

<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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

    <ul>
        <% 
           String[] errors = (String[]) request.getAttribute("errors");
           for (int i = 0; i < errors.length; i++) { %>
          <li style="color: red"><%= errors[i]%></li>
        <% } %>
      </ul>

<div align="center">
       TrueSculpt online library   
       <br>
       
      <%
         List<MediaObject> files = (List<MediaObject>) request.getAttribute("files");
           int n=files.size();
           int nColCount=3;
           int nRowCount=n/nColCount;
         if ( n > 0) {%>
         <TABLE>
         <%for (int i = 0; i <= nRowCount; i++) {%>
            <TR>
             <% for (int j = 0; j < nColCount; j++) { %>
               <TD>
                  <% int index =i*nColCount+j; 
                    if (index<n) { %>
	                <%  MediaObject item = files.get(index); %>
				    
	                <a href="<%=item.getDisplayURL()%>"> 
	                <img src="<%= item.getThumbnailURLPath() %>">
	                </a>       
	                <br>       
	                <%=item.getTitle()%>
		            </TD>
	             <% } %>
             <% } %>
             </TR>
         <% } %>
        </TABLE>
        <%} else { %>

          <% if (user != null) { %>
            No media found.
          <% } else { %>
            Log in or look for media
          <% } %>

      <% } %>
   </div>
    
      <% if (user != null) { %>
        <a href="/upload">Upload new media</a>
      <% } %>
      
 
  </body>
</html>
