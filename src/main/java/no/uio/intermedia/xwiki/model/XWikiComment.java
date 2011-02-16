package no.uio.intermedia.xwiki.model;

import java.util.Date;

import no.uio.intermedia.xwiki.model.interfaces.IComment;


/**
 * Represents Comment
 * 
 * @author Anthony Perritano
 * @author Jeremy Toussaint
 *
 */
public class XWikiComment implements IComment{

	private String id = null;
	private String pageId = null;
	private String author = null;
	private Date date = null;
	private String highlight = null;
	private String text = null;
	private String replyTo = null;
	
	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public void setId(String id) {
		this.id = id;
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
	public String getHighlight() {
		return highlight;
	}
	
	@Override
	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}
	
	@Override
	public String getText() {
		return text;
	}
	
	@Override
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String getReplyTo() {
		return replyTo;
	}
	
	@Override
	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}
}
