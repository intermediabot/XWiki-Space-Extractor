package no.uio.intermedia.xwiki.model;

import java.util.Date;

import no.uio.intermedia.xwiki.model.interfaces.IAttachment;


/**
 * Represents an attachment
 * 
 * @author Anthony Perritano
 * @author Jeremy Toussaint
 *
 */
public class XWikiAttachment implements IAttachment {

	public String id = null;
	public String name = null;
	public String size = null;
	public String version = null;
	public String pageId = null;
	public String pageVersion = null;
	public String mimeType = null;
	public String author = null;
	public Date date = null;
	public String xwikiRelativeUrl = null;
	public String xwikiAbsoluteUrl = null;
	public Object resourceObject = null;
	public boolean isFileValid = false;
	

	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getSize() {
		return size;
	}
	
	@Override
	public void setSize(String size) {
		this.size = size;
	}
	
	@Override
	public String getVersion() {
		return version;
	}
	
	@Override
	public void setVersion(String version) {
		this.version = version;
	}
	
	@Override
	public String getPageId() {
		return pageId;
	}
	
	@Override
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	
	@Override
	public String getPageVersion() {
		return pageVersion;
	}
	
	@Override
	public void setPageVersion(String pageVersion) {
		this.pageVersion = pageVersion;
	}
	
	@Override
	public String getMimeType() {
		return mimeType;
	}
	
	@Override
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	@Override
	public String getAuthor() {
		return author;
	}
	
	@Override
	public void setAuthor(String author) {
		this.author = author;
	}
	
	@Override
	public Date getDate() {
		return date;
	}
	
	@Override
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String getRelativeUrl() {
		return xwikiRelativeUrl;
	}
	
	@Override
	public void setRelativeUrl(String xwikiRelativeUrl) {
		this.xwikiRelativeUrl = xwikiRelativeUrl;
	}
	
	@Override
	public String getAbsoluteUrl() {
		return xwikiAbsoluteUrl;
	}
	
	@Override
	public void setAbsoluteUrl(String xwikiAbsoluteUrl) {
		this.xwikiAbsoluteUrl = xwikiAbsoluteUrl;
	}

	@Override
	public Object getResource() {
		return resourceObject;
	}

	@Override
	public void setResource(Object resource) {
		this.resourceObject = resource;
	}

	@Override
	public boolean getIsValid() {
		return isFileValid;
	}

	@Override
	public void setIsValid(boolean object) {
		this.isFileValid = object;
	}
	
	@Override
	public boolean isOfImageType() {
		if(this.getMimeType().equals("image/png") || this.getMimeType().equals("image/jpeg") || this.getMimeType().equals("image/jpg") || this.getMimeType().equals("image/gif")) {
			return true;
		}
		
		return false;
	}
}
