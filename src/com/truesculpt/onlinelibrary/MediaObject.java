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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
	private BlobKey blob;
	
	@Persistent
	private BlobKey objectBlob;

	@Persistent
	private Date creation;

	@Persistent
	private long size;

	@Persistent
	private String title;

	@Persistent
	private String description;

	private static final List<String> IMAGE_TYPES = Arrays.asList("image/png",
			"image/jpeg", "image/tiff", "image/gif", "image/bmp");

	public MediaObject(User owner, BlobKey imageBlob, BlobKey objectBlob, Date creationTime,
					   long objectSize, String title, String description)
	{
		this.blob = imageBlob;
		this.objectBlob=objectBlob;
		this.owner = owner;
		this.creation = creationTime;
		this.size = objectSize;
		this.title = title;
		this.description = description;
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

	public long getObjectSize()
	{
		return size;
	}

	public void serveObject(HttpServletResponse resp) throws IOException
	{
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		blobstoreService.serve(objectBlob, resp);
	}
	
	public String getImageURL()
	{
		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		return imagesService.getServingUrl(blob);
	}

	public String getImageThumbnailURL()
	{
		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		return imagesService.getServingUrl(blob, 250, false);
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
}
