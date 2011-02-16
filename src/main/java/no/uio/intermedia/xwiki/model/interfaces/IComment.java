package no.uio.intermedia.xwiki.model.interfaces;

import java.util.Date;


/**
 * XWiki Comment
 * 
 * @author Anthony Perritano
 * @author Jeremy Toussaint
 *
 */
public interface IComment {

	/**
	 * @return String id
	 */
	public String getId();

	/**
	 * @param id
	 */
	public void setId(String id);

	/**
	 * @return String pageId
	 */
	public String getPageId();

	/**
	 * @param pageId
	 */
	public void setPageId(String pageId);

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
	 * @return String highlight
	 */
	public String getHighlight();

	/**
	 * @param highlight
	 */
	public void setHighlight(String highlight);

	/**
	 * @return String text
	 */
	public String getText();

	/**
	 * @param text
	 */
	public void setText(String text);

	/**
	 * @return String replyTo
	 */
	public String getReplyTo();

	/**
	 * @param replyTo
	 */
	public void setReplyTo(String replyTo);

}
