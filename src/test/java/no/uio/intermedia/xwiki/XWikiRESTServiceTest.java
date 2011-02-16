package no.uio.intermedia.xwiki;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Properties;
import java.util.Vector;

import no.uio.intermedia.xwiki.model.interfaces.IAttachment;
import no.uio.intermedia.xwiki.model.interfaces.IComment;
import no.uio.intermedia.xwiki.model.interfaces.IPage;
import no.uio.intermedia.xwiki.model.interfaces.ISpace;
import no.uio.intermedia.xwiki.model.interfaces.ITag;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;

/**
 * Tests extracting information from xwiki. Images, pages, etc...
 * 
 * @author Anthony Perritano
 *
 */
public class XWikiRESTServiceTest {
	
	private static final Logger logger = Logger.getLogger(XWikiRESTServiceTest.class.getName());
	private Properties prop = null;
	
	@Before
	public void setup() {
		prop = new Properties();
		 
    	try {
            prop.load(getClass().getResourceAsStream("/xwiki.properties"));
    	} 
    	catch (IOException e) {
    		logger.debug("setup:  IOException: "+e);
        }
	}
	
	@Ignore
	public void propertyFileNullExceptionTest() {
		IWikiService wikiService = null;
		try {
			wikiService = new XWikiRESTService(null);
		}
		catch (NullPointerException e) {
		    e.printStackTrace();
			assertNull(wikiService);
		}
	}
	
	
	@Ignore
	public void test_returnPage() {
		IWikiService wikiService = new XWikiRESTService(prop);
		IPage wikiPage = wikiService.getWikiPage();
		
		//test that page is not null
		assertNotNull(wikiPage);
		//test that the page content contains an expected string
		assertTrue((wikiPage.getContent()).contains("page"));
		
		logger.info("content: "+wikiPage.getContent());
		logger.info("creator: "+wikiPage.getCreator());
		logger.info("version: "+wikiPage.getVersion());
		logger.info("version: "+wikiPage.getComments());
		logger.info("version: "+wikiPage.getAttachments());
		logger.info("version: "+wikiPage.getTags());
	}
	
	@Ignore
	public void test_returnPageWithoutAttachmentResources() {
		IWikiService wikiService = new XWikiRESTService(prop);
		IPage wikiPage = wikiService.getWikiPageWithoutAttachmentResources();
		//test that page is not null
		assertNotNull(wikiPage);
		//test that the page content contains an expected string
		assertTrue((wikiPage.getContent()).contains("page"));
		
		logger.info("content: "+wikiPage.getContent());
		logger.info("creator: "+wikiPage.getCreator());
		logger.info("version: "+wikiPage.getVersion());
		logger.info("version: "+wikiPage.getComments());
		logger.info("version: "+wikiPage.getAttachments());
		logger.info("version: "+wikiPage.getTags());
	}
	
	@Ignore
	public void test_returnSpaces() {
		IWikiService wikiService = new XWikiRESTService(prop);
		Vector<ISpace> spaces = wikiService.getWikiSpaces();
		assertNotNull(spaces);
		if(spaces != null) {
			logger.info("1st space: "+spaces.elementAt(0).getName());			
		}
	}
	
	@Ignore
	public void test_returnCommentsForPage() {
		IWikiService wikiService = new XWikiRESTService(prop);
		Vector<IComment> comments = wikiService.getComments();
		assertNotNull(comments);
		if(comments != null) {
			logger.info("comment: "+comments.elementAt(0).getText());			
		}
	}
	
	@Ignore
	public void test_returnAttachmentsForPage() {
		IWikiService wikiService = new XWikiRESTService(prop);
		Vector<IAttachment> attachments = wikiService.getAttachments();
		assertNotNull(attachments);
		if(attachments != null) {
			int numberOfAttachments = 0;
			for(int i=0; i<attachments.size(); i++) {
				if(attachments.elementAt(i).getIsValid() && (attachments.elementAt(i).getMimeType().equals("image/png") || attachments.elementAt(i).getMimeType().equals("image/jpeg") || attachments.elementAt(i).getMimeType().equals("image/jpg") || attachments.elementAt(i).getMimeType().equals("image/gif"))) {
					assertNotNull(attachments.elementAt(i).getResource());
					logger.info("################################attachment: "+attachments.elementAt(i).getName()+" "+attachments.elementAt(i).getMimeType());		
					numberOfAttachments++;
				}
				else {
					assertNull((attachments.elementAt(i).getResource()));
				}				
			}
			logger.info("valid attachments: "+numberOfAttachments+ " with max attachment size: "+prop.getProperty("MAX_ATTCHMENT_SIZE"));
		}
	}
	
	@Ignore
	public void test_returnAttachmentsWithoutResourcesForPage() {
		IWikiService wikiService = new XWikiRESTService(prop);
		Vector<IAttachment> attachments = wikiService.getAttachmentsWithoutResource();
		assertNotNull(attachments);
		if(attachments != null) {
			for(int i=0; i<attachments.size(); i++) {
				if(attachments.elementAt(i).getMimeType().equals("image/png") || attachments.elementAt(i).getMimeType().equals("image/jpeg") || attachments.elementAt(i).getMimeType().equals("image/jpg") || attachments.elementAt(i).getMimeType().equals("image/gif")) {
					assertNotNull(attachments.elementAt(i).getAbsoluteUrl());
					logger.info("################################attachment: "+attachments.elementAt(i).getName()+" "+attachments.elementAt(i).getMimeType());		
				}			
			}
		}
	}
	

	@Ignore
	public void test_returnTagsForPage() {
		IWikiService wikiService = new XWikiRESTService(prop);
		Vector<ITag> tags = wikiService.getTags();
		assertNotNull(tags);
		if(tags != null) {
			logger.info("tag: "+tags.elementAt(0).getName());			
		}
	}
}