package no.uio.intermedia.xwiki.model;

import java.util.Vector;

import no.uio.intermedia.xwiki.model.interfaces.IAttachment;
import no.uio.intermedia.xwiki.model.interfaces.IComment;
import no.uio.intermedia.xwiki.model.interfaces.IPage;
import no.uio.intermedia.xwiki.model.interfaces.ITag;


/**
 * Represents a XWiki Page
 * 
 * @author Anthony Perritano
 * @author Jeremy Toussaint
 *
 */
public class XWikiPage implements IPage {
		

	private String language = null;
	private String version = null;
	private String majorVersion = null;
	private String minorVersion = null;
	private String created = null;
	private String creator = null;
	private String modified = null;
	private String modifier = null;
	private String content = null;
	private String pageName = null;
	private Vector<IComment> comments = null;
	private Vector<IAttachment> attachments = null;
	private Vector<ITag> tags = null;

	@Override
	public String getContent() {
		return content;
	}
	
	@Override
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String getLanguage() {
		return language;
	}
	
	@Override
	public void setLanguage(String language) {
		this.language = language;
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
	public String getMajorVersion() {
		return majorVersion;
	}
	
	@Override
	public void setMajorVersion(String majorVersion) {
		this.majorVersion = majorVersion;
	}
	
	@Override
	public String getMinorVersion() {
		return minorVersion;
	}
	
	@Override
	public void setMinorVersion(String minorVersion) {
		this.minorVersion = minorVersion;
	}
	
	@Override
	public String getCreated() {
		return created;
	}
	
	@Override
	public void setCreated(String created) {
		this.created = created;
	}
	
	@Override
	public String getCreator() {
		return creator;
	}
	
	@Override
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@Override
	public String getModified() {
		return modified;
	}
	
	@Override
	public void setModified(String modified) {
		this.modified = modified;
	}
	
	@Override
	public String getModifier() {
		return modifier;
	}
	
	@Override
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Override
	public void setPageName(String pageName) {
		this.pageName = pageName;		
	}

	@Override
	public String getPageName() {
		return pageName;
	}

	@Override
	public Vector<IComment> getComments() {
		return comments;
	}
	
	@Override
	public void setComments(Vector<IComment> comments) {
		this.comments = comments;
	}

	@Override
	public Vector<IAttachment> getAttachments() {
		return attachments;
	}

	@Override
	public void setAttachments(Vector<IAttachment> attachments) {
		this.attachments = attachments;
		
	}

	@Override
	public Vector<ITag> getTags() {
		return tags;
	}

	@Override
	public void setTags(Vector<ITag> tags) {
		this.tags = tags;
	}
}
