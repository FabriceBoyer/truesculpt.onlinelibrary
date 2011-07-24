<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.truesculpt.onlinelibrary.MediaObject"%>
<%@ page import="com.truesculpt.onlinelibrary.Index"%>
<%@ page import="java.util.List"%>
<%@ page import="java.lang.Integer"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
       List<MediaObject> files = (List<MediaObject>) request.getAttribute("files");
%>

<% int nFileCount=files.size();		 
       if ( nFileCount > 0) {%>
<%for (int i = 0; i < nFileCount; i++) {%>
<%  MediaObject item = files.get(i); %>

<c:set var="title" value="<%= item.getTitle() %>"/>
	     
<a href="<%=item.getDisplayURL()%>" style="text-decoration: none"> 
<img src="<%= item.getImageThumbnailURL() %>" alt="${fn:escapeXml(title)}"> 
</a>
<br>
<% } %>
<%} else { %>
No extra media found<br>
<% } %>
<br>
	
