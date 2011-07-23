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

<a href="<%=item.getDisplayURL()%>"> 
<img src="<%= item.getImageThumbnailURL() %>"> 
</a>

<br>

<% } %>
<%} else { %>
No extra media found
<% } %>
<br>
	
