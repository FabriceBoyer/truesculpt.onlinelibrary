/* Copyright (c) 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.truesculpt.onlinelibrary;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class UploadPost extends HttpServlet
{
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
/*
	  try 
	  {
	    ServletFileUpload upload = new ServletFileUpload();
	    //resp.setContentType("text/plain");
	
		FileItemIterator iterator = upload.getItemIterator(req);
		while (iterator.hasNext()) 
		{
		  FileItemStream item = iterator.next();
		  InputStream stream = item.openStream();
		
		  if (item.isFormField()) 
		  {
		    //log.warning("Got a form field: " + item.getFieldName());
		  } 
		  else
		  {	        	  
		    //log.warning("Got an uploaded file: " + item.getFieldName() + ", name = " + item.getName());
		  }
		
		  // You now have the filename (item.getName() and the
		  // contents (which you can read from stream). Here we just
		  // print them back out to the servlet output stream, but you
		  // will probably want to do something more interesting (for
		  // example, wrap them in a Blob and commit them to the
		  // datastore).
		  int len;
		  byte[] buffer = new byte[8192];
		  while ((len = stream.read(buffer, 0, buffer.length)) != -1)
		  {
		   	 Blob blob= new Blob(buffer);	
		   	//resp.getOutputStream().write(buffer, 0, len);
	      }
	    }       
	  } 
	  catch (Exception ex)
	  {
	    throw new ServletException(ex);
	  }
*/
	      
		//UserService userService = UserServiceFactory.getUserService();
		//User user = userService.getCurrentUser();

		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
		if (blobs.keySet().isEmpty())
		{
			resp.sendRedirect("/?error="+ URLEncoder.encode("No file uploaded", "UTF-8"));
			return;
		}

		//Iterator<String> names = blobs.keySet().iterator();
		//String blobName = names.next();
		BlobKey blobKey = blobs.get("file");

		/*
		if (user == null)
		{
			blobstoreService.delete(blobKey);
			resp.sendRedirect("/?error="+ URLEncoder.encode("Must be logged in to upload","UTF-8"));
			return;
		}
		*/

		BlobInfoFactory blobInfoFactory = new BlobInfoFactory();
		BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKey);

		String contentType = blobInfo.getContentType();
		long size = blobInfo.getSize();
		Date creation = blobInfo.getCreation();
		String fileName = blobInfo.getFilename();

		String title = req.getHeader("title");// req.getParameter("title");
		String description = req.getParameter("description");

		try
		{
			MediaObject mediaObj = new MediaObject(null, blobKey, creation,
					contentType, fileName, size, title, description);
			PMF.get().getPersistenceManager().makePersistent(mediaObj);
			//resp.sendRedirect("/");
		}
		catch (Exception e)
		{
			blobstoreService.delete(blobKey);
			resp.sendRedirect("/?error="+ URLEncoder.encode("Object save failed: " + e.getMessage(), "UTF-8"));
		}
	}
}
