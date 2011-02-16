package no.uio.intermedia.xwiki.model.interfaces;

import java.util.Date;


/**
 * XWiki attachment
 * 
 * @author Anthony Perritano
 * @author Jeremy Toussaint
 *
 */
public interface IAttachment {

	
	/**
	 * @return String id
	 */
	public String getId();

	/**
	 * @param id
	 */
	public void setId(String id);

	/**
	 * @return String name
	 */
	public String getName();

	/**
	 * @param name
	 */
	public void setName(String name);

	/**
	 * @return String size
	 */
	public String getSize();

	/**
	 * @param size
	 */
	public void setSize(String size);

	/**
	 * @return String version
	 */
	public String getVersion();

	/**
	 * @param version
	 */
	public void setVersion(String version);

	/**
	 * @return String pageId
	 */
	public String getPageId();

	/**
	 * @param pageId
	 */
	public void setPageId(String pageId);

	/**
	 * @return String pageVersion
	 */
	public String getPageVersion();

	/**
	 * @param pageVersion
	 */
	public void setPageVersion(String pageVersion);

	/**
	 * @return String mimeType
	 */
	public String getMimeType();

	/**
	 * @param mimeType
	 */
	public void setMimeType(String mimeType);

	/**
	 * @return String author
	 */
	public String getAuthor();

	/**
	 * @param author
	 */
	public void setAuthor(String author);

	/**
	 * @return String date
	 */
	public Date getDate();

	/**
	 * @param date
	 */
	public void setDate(Date date);

	/**
	 * @return String url
	 */
	public String getRelativeUrl();

	/**
	 * @param relativeUrl
	 */
	public void setRelativeUrl(String relativeUrl);

	/**
	 * @return String url
	 */
	public String getAbsoluteUrl();

	/**
	 * @param absoluteUrl
	 */
	public void setAbsoluteUrl(String absoluteUrl);
	
	/**
	 * @return Object
	 */
	public Object getResource();
	
	/**
	 * @param resource
	 */
	public void setResource(Object resource);
	
	/**
	 * @param resource
	 */
	public void setIsValid(boolean object);
	
	/**
	 * @return Boolean
	 * If the file size doesn't exceed the limit
	 */
	public boolean getIsValid();

	/**
	 * @return Boolean
	 * Returns true if extension is jpeg, jpg, png or gif
	 */
	public boolean isOfImageType();

}
