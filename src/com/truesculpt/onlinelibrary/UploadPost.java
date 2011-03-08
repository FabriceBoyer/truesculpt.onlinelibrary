package com.truesculpt.onlinelibrary;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
public class UploadPost extends HttpServlet
{
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
		if (blobs.keySet().isEmpty())
		{
			resp.sendRedirect("/?error="+ URLEncoder.encode("No file uploaded", "UTF-8"));
			return;
		}

		BlobKey objectBlobKey = blobs.get("objectfile");		
		BlobKey imageBlobKey = blobs.get("imagefile");

		BlobInfoFactory blobInfoFactory = new BlobInfoFactory();
		BlobInfo objectBlobInfo = blobInfoFactory.loadBlobInfo(objectBlobKey);
		BlobInfo imageBlobInfo = blobInfoFactory.loadBlobInfo(imageBlobKey);

		Integer objectsize = (Integer)(int)(objectBlobInfo.getSize()+imageBlobInfo.getSize());
		Date creationDate = objectBlobInfo.getCreation();

		String title = req.getHeader("title");
		if (title==null)
		{
			title=req.getParameter("title");
		}
		String description = req.getHeader("description");
		if (description==null)
		{
			description=req.getParameter("description");
		}

		try
		{
			MediaObject mediaObj = new MediaObject(null, imageBlobKey,objectBlobKey, creationDate, objectsize, title, description);
			PMF.get().getPersistenceManager().makePersistent(mediaObj);
			resp.sendRedirect("/main");
		}
		catch (Exception e)
		{
			blobstoreService.delete(objectBlobKey);
			blobstoreService.delete(imageBlobKey);
			resp.sendRedirect("/?error="+ URLEncoder.encode("Object save failed: " + e.getMessage(), "UTF-8"));
		}
	}
}
