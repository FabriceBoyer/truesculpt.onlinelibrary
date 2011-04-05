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
import com.google.appengine.api.datastore.KeyFactory;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


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
		String installationID = req.getHeader("installationID");
		if (installationID==null)
		{
			installationID=req.getParameter("installationID");
		}

		try
		{
			MediaObject mediaObj = new MediaObject(null, imageBlobKey,objectBlobKey, creationDate, objectsize, title, description, installationID);
			PMF.get().getPersistenceManager().makePersistent(mediaObj);
			String strKey=KeyFactory.keyToString(mediaObj.getKey());
			String newURL="/display?key="+strKey;
			resp.addHeader("displayURL",newURL);	
			
			//send email
	        Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);
	
	        String msgBody = "A new sculpture named " + mediaObj.getTitle() +" have been uploaded and requires validation\n"
	        	+"The validation adress of the sculpture is : http://truesculpt.appspot.com/admin?key="+strKey+"\n"
	        	+"The administration adress is : http://truesculpt.appspot.com/admin";
	
	        try 
	        {
	            Message msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress("admin@truesculpt.appspotmail.com", "TrueSculpt administrator"));
	            msg.addRecipient(Message.RecipientType.TO, new InternetAddress("fabrice.boyer@gmail.com", "TrueSculpt administrator"));
	            msg.setSubject("[TrueSculpt] A new sculpture named "+mediaObj.getTitle()+" have been uploaded and requires validation");
	            msg.setText(msgBody);
	            Transport.send(msg);	    
	        } 
	        catch (Exception e)
	        {
	        	
	        }
		           
		}
		catch (Exception e)
		{
			blobstoreService.delete(objectBlobKey);
			blobstoreService.delete(imageBlobKey);
			resp.sendRedirect("/?error="+ URLEncoder.encode("Object save failed: " + e.getMessage(), "UTF-8"));
		}
	}
}
