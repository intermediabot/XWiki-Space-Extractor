package no.uio.intermedia.xwiki;

import java.util.Vector;

import no.uio.intermedia.xwiki.model.interfaces.IAttachment;
import no.uio.intermedia.xwiki.model.interfaces.IComment;
import no.uio.intermedia.xwiki.model.interfaces.IPage;
import no.uio.intermedia.xwiki.model.interfaces.ISpace;
import no.uio.intermedia.xwiki.model.interfaces.ITag;

/**
 * XWikiSerivce interface
 * 
 * @author Anthony Perritano
 * @author Jeremy Toussaint
 *
 */
public interface WikiService {

	/**
	 * @param wikiName
	 * @param spaceName
	 * @param pageName
	 * @return IPage
	 */
	public IPage getWikiPage(String wikiName, String spaceName, String pageName, boolean withAttachmentResources);
	
	/**
	 * @param wikiName
	 * @return Vector<ISpace>
	 */
	public Vector<ISpace> getWikiSpaces(String wikiName);
	
	/**
	 * @param wikiName
	 * @param spaceName
	 * @param pageName
	 * @return Vector<IComment>
	 */
	public Vector<IComment> getComments(String wikiName, String spaceName, String pageName);
	
	/**
	 * @param wikiName
	 * @param spaceName
	 * @param pageName
	 * @return Vector<IAttachment>
	 */
	public Vector<IAttachment> getAttachments(String wikiName, String spaceName, String pageName);
	
	/**
	 * @param wikiName
	 * @param spaceName
	 * @param pageName
	 * @return Vector<ITag>
	 */
	public Vector<ITag> getTags(String wikiName, String spaceName, String pageName);

	/**
	 * @return IPage
	 * Use if instantiated with property file
	 */
	public IPage getWikiPage();

	/**
	 * @return Vector<ISpace>
	 * Use if instantiated with property file
	 */
	public Vector<ISpace> getWikiSpaces();

	/**
	 * @return Vector<IComment>
	 * Use if instantiated with property file
	 */
	public Vector<IComment> getComments();

	/**
	 * @return Vector<IAttachment>
	 * Use if instantiated with property file
	 */
	public Vector<IAttachment> getAttachments();

	/**
	 * @return Vector<ITag>
	 * Use if instantiated with property file
	 */
	public Vector<ITag> getTags();

	
	/**
	 * @return Vector<IAttachment> without resources
	 * Use if instantiated with property file
	 */
	public Vector<IAttachment> getAttachmentsWithoutResource();


	/**
	 * @param wikiName
	 * @param spaceName
	 * @param pageName
	 * @return Vector<IAttachment> without resources
	 */
	public Vector<IAttachment> getAttachmentsWithoutResource(String wikiName, String spaceName,String pageName);

	
	/**
	 * @return IPage without attachments resources
	 * Use if instantiated with property file
	 */
	public IPage getWikiPageWithoutAttachmentResources();

}
