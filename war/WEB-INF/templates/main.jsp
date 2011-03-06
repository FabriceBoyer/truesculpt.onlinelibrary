<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.truesculpt.onlinelibrary.MediaObject" %>
<%@ page import="com.truesculpt.onlinelibrary.Index" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.Integer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
  User user = (User) request.getAttribute("user");
  String authURL = (String) request.getAttribute("authURL");
  String strPage= (String) request.getParameter("page");
  Integer nPageNumber= 0;
  if (strPage!=null) { 
   nPageNumber=Integer.parseInt(strPage);
  }
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
       <br>
       
      <%
         List<MediaObject> files = (List<MediaObject>) request.getAttribute("files");
	     int nFileCount=files.size();
	     int nMaxColCount=3;
	     int nMaxRowCount=2;
	     int nMaxElemPerPage=nMaxRowCount*nMaxColCount;
	     int nMaxPageCount=nFileCount/nMaxElemPerPage;          
	     int nCurrPage=Index.saturatePageNumber(nPageNumber,nMaxPageCount);
		 int nPrevPage=Index.saturatePageNumber(nPageNumber-1,nMaxPageCount);
		 int nNextPage=Index.saturatePageNumber(nPageNumber+1,nMaxPageCount);
         if ( nFileCount > 0) {%>
         <TABLE>
         <%for (int i = 0; i < nMaxRowCount; i++) {%>
            <TR>
             <% for (int j = 0; j < nMaxColCount; j++) { %>
               <TD>
                  <% int index=nCurrPage*nMaxElemPerPage+i*nMaxColCount+j; 
                    if (index<nFileCount && index>=0) { %>
	                <%  MediaObject item = files.get(index); %>				    
	                <a href="<%=item.getDisplayURL()%>"> 
	                <img src="<%= item.getThumbnailURLPath() %>">
	                </a>       
	                <br>       
	                <c:set var="title" value="<%= item.getTitle() %>"/>
    				${fn:escapeXml(title)}<br>
	                </TD>
	             <% } %>
             <% } %>
             </TR>
         <% } %>
        </TABLE>
        <%} else { %>
            No media found.
      <% } %>
      <br>
      <a href="/?page=<%=0%>"><<</a>
      <a href="/?page=<%=nPrevPage%>"><</a>
      
        <%for (int iPage = nPrevPage; iPage <= nNextPage; iPage++) {%>
     		 <a href="/?page=<%=iPage%>"><%=iPage+1%></a>
        <% } %>
      
      <a href="/?page=<%=nNextPage%>">></a>
      <a href="/?page=<%=nMaxPageCount%>">>></a>
   </div> 
 
  </body>
</html>
