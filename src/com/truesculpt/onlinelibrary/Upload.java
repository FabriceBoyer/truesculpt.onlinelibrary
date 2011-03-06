package com.truesculpt.onlinelibrary;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
public class Upload extends HttpServlet
{
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException, ServletException
	{
		String uploadURL = blobstoreService.createUploadUrl("/post");

		req.setAttribute("uploadURL", uploadURL);

		RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/templates/upload.jsp");
		dispatcher.forward(req, resp);		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{	
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		String uploadUrl = blobstoreService.createUploadUrl("/post");
		resp.addHeader("uploadUrl", uploadUrl);
	}
}
