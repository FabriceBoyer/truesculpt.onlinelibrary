<?xml version="1.0" encoding="UTF-8"?>
<library>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.truesculpt.onlinelibrary.MediaObject"%>
<%@ page import="com.truesculpt.onlinelibrary.Index"%>
<%@ page import="java.util.List"%>
<%@ page import="java.lang.Integer"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%List<MediaObject> files = (List<MediaObject>) request.getAttribute("files");%>
<%int nFileCount=files.size();	for (int i = 0; i < nFileCount; i++) {  MediaObject item = files.get(i); %>
	<item>
		<title><%= item.getTitle()%></title>
		<description><%= item.getDescription()%></description>
		<downloadCount><%= item.getDownloadCount()%></downloadCount>
		<creationTime><%= item.getCreationTime()%></creationTime>
		<installationID><%= item.getInstallationID()%></installationID>
		<imageURL><%=item.getImageURL()%></imageURL>
		<imageThumbnailURL><%= item.getImageThumbnailURL() %></imageThumbnailURL>
	</item>
<%}%>	
</library>