package com.truesculpt.onlinelibrary;

import java.io.IOException;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class MediaObject
{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private User owner;

	@Persistent
	private BlobKey imageBlob;
	
	@Persistent
	private BlobKey objectBlob;

	@Persistent
	private Date creation;

	@Persistent
	private Integer size;

	@Persistent
	private String title;

	@Persistent
	private String description;
	
	@Persistent
	private Integer downloadCount;
	
	@Persistent
	private Boolean hasBeenModerated;

	public MediaObject(User owner, BlobKey imageBlob, BlobKey objectBlob, Date creationTime,
					   Integer objectSize, String title, String description)
	{
		this.imageBlob = imageBlob;
		this.objectBlob=objectBlob;
		this.owner = owner;
		this.creation = creationTime;
		this.size = objectSize;
		this.title = title;
		this.description = description;
		this.downloadCount=0;
		this.hasBeenModerated=false;
	}

	public Key getKey()
	{
		return key;
	}

	public User getOwner()
	{
		return owner;
	}

	public Date getCreationTime()
	{
		return creation;
	}

	public String getDescription()
	{
		return description;
	}

	public String getTitle()
	{
		return title;
	}

	public Integer getObjectSize()
	{
		return size;
	}
	
	public Integer getDownloadCount()
	{
		return downloadCount;
	}
	
	public void incrementDownloadCount()
	{
		downloadCount++;
	}

	public Boolean getHasBeenModerated()
	{
		return hasBeenModerated;
	}
	
	public void setHasBeenModerated(Boolean moderated)
	{
		hasBeenModerated=moderated;
	}
	
	public void serveObject(HttpServletResponse resp) throws IOException
	{
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		blobstoreService.serve(objectBlob, resp);
	}
	
	 BlobKey getImageBlob()
	 {
		 return imageBlob;
	 }

	BlobKey getObjectBlob()
	{
		return objectBlob;
	}
	
	public String getImageURL()
	{
		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		return imagesService.getServingUrl(imageBlob);
	}

	public String getImageThumbnailURL()
	{
		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		return imagesService.getServingUrl(imageBlob, 180, false);
	}

	public String getDisplayURL()
	{
		String strKey = KeyFactory.keyToString(key);
		return "/display?key=" + strKey;
	}
	
	public String getObjectURL()
	{
		String strKey = KeyFactory.keyToString(key);
		return "/object?key=" + strKey;
	}
	
	public String getAcceptURL()
	{
		String strKey = KeyFactory.keyToString(key);
		return "/admin?key=" + strKey+"&accept";
	}
	
	public String getRejectURL()
	{
		String strKey = KeyFactory.keyToString(key);
		return "/admin?key=" + strKey+"&reject";
	}
}
