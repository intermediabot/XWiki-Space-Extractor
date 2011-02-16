package no.uio.intermedia.xwiki.model.interfaces;

import java.util.Vector;


/**
 * XWiki Page
 * 
 * @author Anthony Perritano
 * @author Jeremy Toussaint
 *
 */
public interface IPage {

	/**
	 * @return String content
	 */
	public String getContent();

	/**
	 * @param content
	 */
	public void setContent(String content);

	/**
	 * @return String creator
	 */
	public String getCreator();

	/**
	 * @param creator
	 */
	public void setCreator(String creator);

	/**
	 * @return String version
	 */
	public String getVersion();

	/**
	 * @param version
	 */
	public void setVersion(String version);

	/**
	 * @param modifier
	 */
	public void setModifier(String modifier);

	/**
	 * @return String modifier
	 */
	public String getModifier();

	/**
	 * @param modified
	 */
	public void setModified(String modified);

	/**
	 * @return String modified
	 */
	public String getModified();

	/**
	 * @param created
	 */
	public void setCreated(String created);

	/**
	 * @return String created
	 */
	public String getCreated();

	/**
	 * @param minorVersion
	 */
	public void setMinorVersion(String minorVersion);

	/**
	 * @return String minorVersion
	 */
	public String getMinorVersion();

	/**
	 * @param majorVersion
	 */
	public void setMajorVersion(String majorVersion);

	/**
	 * @return String majorVersion
	 */
	public String getMajorVersion();

	/**
	 * @param language
	 */
	public void setLanguage(String language);

	/**
	 * @return String language
	 */
	public String getLanguage();

	/**
	 * @param pageName
	 */
	public void setPageName(String pageName);

	/**
	 * @return String pageName
	 */
	public String getPageName();
	
	/**
	 * @return Vector<IComment>
	 */
	public Vector<IComment> getComments();

	/**
	 * @param comments
	 */
	public void setComments(Vector<IComment> comments);

	/**
	 * @param attachments
	 */
	public void setAttachments(Vector<IAttachment> attachments);
	
	/**
	 * @return Vector<IAttachment>
	 */
	public Vector<IAttachment> getAttachments();

	/**
	 * @param tags
	 */
	public void setTags(Vector<ITag> tags);
	
	/**
	 * @return Vector<ITag>
	 */
	public Vector<ITag> getTags();

}
