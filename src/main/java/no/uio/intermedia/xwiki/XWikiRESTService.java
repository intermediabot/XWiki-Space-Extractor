package no.uio.intermedia.xwiki;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import no.uio.intermedia.xwiki.model.XWikiAttachment;
import no.uio.intermedia.xwiki.model.XWikiComment;
import no.uio.intermedia.xwiki.model.XWikiPage;
import no.uio.intermedia.xwiki.model.XWikiSpace;
import no.uio.intermedia.xwiki.model.XWikiTag;
import no.uio.intermedia.xwiki.model.interfaces.IAttachment;
import no.uio.intermedia.xwiki.model.interfaces.IComment;
import no.uio.intermedia.xwiki.model.interfaces.IPage;
import no.uio.intermedia.xwiki.model.interfaces.ISpace;
import no.uio.intermedia.xwiki.model.interfaces.ITag;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;
import org.xwiki.rest.model.jaxb.Attachment;
import org.xwiki.rest.model.jaxb.Attachments;
import org.xwiki.rest.model.jaxb.Comment;
import org.xwiki.rest.model.jaxb.Comments;
import org.xwiki.rest.model.jaxb.Page;
import org.xwiki.rest.model.jaxb.Space;
import org.xwiki.rest.model.jaxb.Spaces;
import org.xwiki.rest.model.jaxb.Tag;
import org.xwiki.rest.model.jaxb.Tags;

/**
 * Service implementation
 * 
 * @author Anthony Perritano
 * @author Jeremy Toussaint
 * 
 */
public class XWikiRESTService implements IWikiService {

	static final Logger logger = Logger.getLogger(XWikiRESTService.class.getName());
	
	private static final int PAGE_TYPE = 0;
	private static final int COMMENT_TYPE = 1;
	private static final int SPACE_TYPE = 2;
	static final int ATTACHMENT_TYPE = 3;
	private static final int TAG_TYPE = 4;

	public static final boolean NO_ATTACHMENT_RESOURCES = false;
	public static final boolean WITH_ATTACHMENT_RESOURCES = true;
	
	String wikiUser = null;
	String wikiPass = null;
	String wikiHost = null;
	String wikiPort = null;
	String wikiName = null;
	String wikiSpace = null;
	String wikiPageName = null;
	int maxFileSize = 0;
	private IPage wikiPage = null;
	
	private Vector<IComment> comments = null;
	private Vector<ISpace> spaces = null;
	Vector<IAttachment> attachments = null;
	private Vector<ITag> tags = null;
	
	private GetMethod getPageMethod = null;
	private GetMethod getCommentsMethod = null;
	GetMethod getAttachmentsMethod = null;
	private GetMethod getSpacesMethod = null;
	private GetMethod getTagsMethod = null;
	
	HttpClient httpClient = null;
	Credentials defaultcreds = null;
	
	private GetThread threadPage = null;
	private GetThread threadComments = null;
	private GetThread threadSpaces = null;
	GetThread threadAttachments = null;
	private GetThread threadTags = null;
	
	/**
	 * Constructor
	 * 
	 * @param url
	 * @param port
	 * @param user
	 * @param pass
	 */
	public XWikiRESTService(String url, String port, String user, String pass) {
		this.wikiUser = user;
		this.wikiPass = pass;
		this.wikiHost = url;
		this.wikiPort = port;
		
		Properties prop = new Properties();
		 
    	try {
            prop.load(getClass().getResourceAsStream("/xwiki.properties"));
            this.wikiName = prop.getProperty("DEFAULT_WIKI_NAME");
            this.wikiSpace = prop.getProperty("DEFAULT_WIKI_SPACE");
            this.wikiPageName = prop.getProperty("DEFAULT_TEST_PAGE_NAME");
            this.maxFileSize = Integer.valueOf(prop.getProperty("MAX_ATTCHMENT_SIZE"));
    	} 
    	catch (IOException e) {
    		logger.debug("setup:  IOException: "+e);
        }
    	
		defaultcreds = new UsernamePasswordCredentials(wikiUser, wikiPass);
	}

	/**
	 * @param prop
	 * @throws NullPointerException
	 */
	public XWikiRESTService(Properties prop) throws NullPointerException {
		if (prop != null) {
			this.wikiUser = prop.getProperty("DEFAULT_USER");
			this.wikiPass = prop.getProperty("DEFAULT_PASS");
			this.wikiHost = prop.getProperty("DEFAULT_HOST");
			this.wikiPort = prop.getProperty("DEFAULT_PORT");
			this.wikiName = prop.getProperty("DEFAULT_WIKI_NAME");
			this.wikiSpace = prop.getProperty("REPOSITORY_WIKI_SPACE");
			this.wikiPageName = prop.getProperty("REPOSITORY_WIKI_SPACE_STENCILS");
			this.maxFileSize = Integer.valueOf(prop.getProperty("MAX_ATTCHMENT_SIZE"));
			defaultcreds = new UsernamePasswordCredentials(wikiUser, wikiPass);
		} else {
			throw new NullPointerException("The property file was probably null, check it again");
		}
	}

	@Override
	public IPage getWikiPage() {
		return getWikiPage(wikiName, wikiSpace, wikiPageName, WITH_ATTACHMENT_RESOURCES);
	}
	
	@Override
	public IPage getWikiPageWithoutAttachmentResources() {
		return getWikiPage(wikiName, wikiSpace, wikiPageName, NO_ATTACHMENT_RESOURCES);
	}
	
	/**
	 * @param methodName
	 * @return the method string to be passed to the httpClient
	 */
	String getParameteredMethod(int methodName) {
		switch (methodName) {
			case PAGE_TYPE:
				return "http://"+wikiHost+":"+wikiPort+"/xwiki/rest/wikis/"+wikiName+"/spaces/"+wikiSpace+"/pages/"+wikiPageName;
			case COMMENT_TYPE:
				return "http://"+wikiHost+":"+wikiPort+"/xwiki/rest/wikis/"+wikiName+"/spaces/"+wikiSpace+"/pages/"+wikiPageName+"/comments";
			case SPACE_TYPE:
				return "http://"+wikiHost+":"+wikiPort+"/xwiki/rest/wikis/"+wikiName+"/spaces";
			case ATTACHMENT_TYPE:
				return "http://"+wikiHost+":"+wikiPort+"/xwiki/rest/wikis/"+wikiName+"/spaces/"+wikiSpace+"/pages/"+wikiPageName+"/attachments";
			case TAG_TYPE:
				return "http://"+wikiHost+":"+wikiPort+"/xwiki/rest/wikis/"+wikiName+"/spaces/"+wikiSpace+"/pages/"+wikiPageName+"/tags";
			default:
				return null;
		}
	}

	@Override
	public IPage getWikiPage(String wikiName, String spaceName, String pageName, boolean withAttachmentResources) {
		this.wikiName = wikiName;
		this.wikiSpace = spaceName;
		this.wikiPageName = pageName;
		Vector<GetThread> threads = null;
		
		if(wikiName != null && spaceName != null && pageName != null) {
			threads = new Vector<GetThread>();
			httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());		
			
			getPageMethod = new GetMethod(getParameteredMethod(PAGE_TYPE));
			getPageMethod.addRequestHeader("Accept", "application/xml");
			getCommentsMethod = new GetMethod(getParameteredMethod(COMMENT_TYPE));
			getCommentsMethod.addRequestHeader("Accept", "application/xml");
			getTagsMethod = new GetMethod(getParameteredMethod(TAG_TYPE));
			getTagsMethod.addRequestHeader("Accept", "application/xml");
			
			httpClient.getParams().setAuthenticationPreemptive(true);
			httpClient.getState().setCredentials(new AuthScope(wikiHost, Integer.valueOf(wikiPort), AuthScope.ANY_REALM), defaultcreds);

			threadPage = new GetThread(httpClient, getPageMethod, PAGE_TYPE, wikiUser, wikiPass); 
			threads.addElement(threadPage);
			threadComments = new GetThread(httpClient, getCommentsMethod, COMMENT_TYPE, wikiUser, wikiPass); 
			threads.addElement(threadComments);
			threadTags = new GetThread(httpClient, getTagsMethod, TAG_TYPE, wikiUser, wikiPass); 
			threads.addElement(threadTags);
			
			//start all the threads at first so they run in parallel
			for(int i = 0; i<threads.size(); i++) {
				threads.elementAt(i).start();
			}
			
			//call the join() method on all threads, so that the getWikiPage() waits until they are finished before returning the wikiPage
			try {
				for(int i = 0; i<threads.size(); i++) {
					threads.elementAt(i).join();
				}
			} catch (InterruptedException e) {
				logger.debug("getComments:  InterruptedException: " + e);
			}
			
			wikiPage = (IPage) threadPage.getItem();
			wikiPage.setPageName(pageName);
			wikiPage.setComments((Vector<IComment>) threadComments.getItem());
			if(withAttachmentResources) {
				wikiPage.setAttachments(getAttachments());				
			}
			else {
				wikiPage.setAttachments(getAttachmentsWithoutResource());
			}
			wikiPage.setTags((Vector<ITag>) threadTags.getItem());
		}
		
		return wikiPage;		
	}

	@Override
	public Vector<ISpace> getWikiSpaces() {
		return getWikiSpaces(wikiName);
	}

	@Override
	public Vector<ISpace> getWikiSpaces(String wikiName) {
		this.wikiName = wikiName;
		
		if(wikiName != null) {
			httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());		
			
			getSpacesMethod = new GetMethod(getParameteredMethod(SPACE_TYPE));
			getSpacesMethod.addRequestHeader("Accept", "application/xml");
						
			httpClient.getParams().setAuthenticationPreemptive(true);
			httpClient.getState().setCredentials(new AuthScope(wikiHost, Integer.valueOf(wikiPort), AuthScope.ANY_REALM), defaultcreds);

			threadSpaces = new GetThread(httpClient, getSpacesMethod, SPACE_TYPE, wikiUser, wikiPass); 
			try {
				threadSpaces.start();
				threadSpaces.join();
			} catch (InterruptedException e) {
				logger.debug("getComments:  InterruptedException: " + e);
			}
			
			spaces = (Vector<ISpace>) threadSpaces.getItem();
		}

		return spaces;
	}

	@Override
	public Vector<IComment> getComments() {
		return getComments(wikiName, wikiSpace, wikiPageName);
	}

	@Override
	public Vector<IComment> getComments(String wikiName, String spaceName, String pageName) {
		this.wikiName = wikiName;
		this.wikiSpace = spaceName;
		this.wikiPageName = pageName;
		
		if(wikiName != null && spaceName != null && pageName != null) {
			httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());		
			
			getCommentsMethod = new GetMethod(getParameteredMethod(COMMENT_TYPE));
			getCommentsMethod.addRequestHeader("Accept", "application/xml");
						
			httpClient.getParams().setAuthenticationPreemptive(true);
			httpClient.getState().setCredentials(new AuthScope(wikiHost, Integer.valueOf(wikiPort), AuthScope.ANY_REALM), defaultcreds);

			threadComments = new GetThread(httpClient, getCommentsMethod, COMMENT_TYPE, wikiUser, wikiPass); 
			try {
				threadComments.start();
				threadComments.join();
			} catch (InterruptedException e) {
				logger.debug("getComments:  InterruptedException: " + e);
			}
			
			comments = (Vector<IComment>) threadComments.getItem();
		}

		return comments;
	}

	@Override
	public Vector<IAttachment> getAttachments() {
		return getAttachments(wikiName, wikiSpace, wikiPageName);
	}

	@Override
	public Vector<IAttachment> getAttachments(String wikiName, String spaceName, String pageName) {
		this.wikiName = wikiName;
		this.wikiSpace = spaceName;
		this.wikiPageName = pageName;
		
		if(wikiName != null && spaceName != null && pageName != null) {
			httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());		
			
			getAttachmentsMethod = new GetMethod(getParameteredMethod(ATTACHMENT_TYPE));
			getAttachmentsMethod.addRequestHeader("Accept", "application/xml");
						
			httpClient.getParams().setAuthenticationPreemptive(true);
			httpClient.getState().setCredentials(new AuthScope(wikiHost, Integer.valueOf(wikiPort), AuthScope.ANY_REALM), defaultcreds);

			threadAttachments = new GetThread(httpClient, getAttachmentsMethod, ATTACHMENT_TYPE, wikiUser, wikiPass); 
			try {
				threadAttachments.start();
				threadAttachments.join();
			} catch (InterruptedException e) {
				logger.debug("getAttachments:  InterruptedException: " + e);
			}
			
			attachments = (Vector<IAttachment>) threadAttachments.getItem();
		}

		return attachments;
	}
	
	
	@Override
	public Vector<IAttachment> getAttachmentsWithoutResource() {
		return getAttachmentsWithoutResource(wikiName, wikiSpace, wikiPageName);
	}
	
	@Override
	public Vector<IAttachment> getAttachmentsWithoutResource(String wikiName, String spaceName, String pageName) {
		this.wikiName = wikiName;
		this.wikiSpace = spaceName;
		this.wikiPageName = pageName;
		
		if(wikiName != null && spaceName != null && pageName != null) {
			httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());		
			
			getAttachmentsMethod = new GetMethod(getParameteredMethod(ATTACHMENT_TYPE));
			getAttachmentsMethod.addRequestHeader("Accept", "application/xml");
						
			httpClient.getParams().setAuthenticationPreemptive(true);
			httpClient.getState().setCredentials(new AuthScope(wikiHost, Integer.valueOf(wikiPort), AuthScope.ANY_REALM), defaultcreds);

			threadAttachments = new GetThread(httpClient, getAttachmentsMethod, ATTACHMENT_TYPE, wikiUser, wikiPass, NO_ATTACHMENT_RESOURCES); 
			try {
				threadAttachments.start();
				threadAttachments.join();
			} catch (InterruptedException e) {
				logger.debug("getAttachments:  InterruptedException: " + e);
			}
			
			attachments = (Vector<IAttachment>) threadAttachments.getItem();
		}

		return attachments;
	}

	

	@Override
	public Vector<ITag> getTags() {
		return getTags(wikiName, wikiSpace, wikiPageName);
	}

	@Override
	public Vector<ITag> getTags(String wikiName, String spaceName, String pageName) {
		this.wikiName = wikiName;
		this.wikiSpace = spaceName;
		this.wikiPageName = pageName;
		
		if(wikiName != null && spaceName != null && pageName != null) {
			httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());		
			
			getTagsMethod = new GetMethod(getParameteredMethod(TAG_TYPE));
			getTagsMethod.addRequestHeader("Accept", "application/xml");
			
			httpClient.getParams().setAuthenticationPreemptive(true);
			httpClient.getState().setCredentials(new AuthScope(wikiHost, Integer.valueOf(wikiPort), AuthScope.ANY_REALM), defaultcreds);

			threadTags = new GetThread(httpClient, getTagsMethod, TAG_TYPE, wikiUser, wikiPass); 
			try {
				threadTags.start();
				threadTags.join();
			} catch (InterruptedException e) {
				logger.debug("getTags:  InterruptedException: " + e);
			}
			
			tags = (Vector<ITag>) threadTags.getItem();
		}

		return tags;
	}

	static class GetThread extends Thread {

		private HttpClient httpClient;
		private GetMethod method;
		private int id;
		private String wikiUser;
		private String wikiPass;
		private Object item = null;
		private boolean withResources = XWikiRESTService.WITH_ATTACHMENT_RESOURCES;
		
		public GetThread(HttpClient httpClient, GetMethod method, int id, String wikiUser, String wikiPass) {
			this.httpClient = httpClient;
			this.method = method;
			this.id = id;
			this.wikiUser = wikiUser;
			this.wikiPass = wikiPass;
		}

		public GetThread(HttpClient httpClient, GetMethod method, int id, String wikiUser, String wikiPass, boolean withResources) {
			this.httpClient = httpClient;
			this.method = method;
			this.id = id;
			this.wikiUser = wikiUser;
			this.wikiPass = wikiPass;
			this.withResources = withResources;
		}

		public Object getItem() {
			return item;
		}
		

		/**
		 * Executes the GetMethod and prints some satus information.
		 */
		public void run() {
			JAXBContext context;
			try {
				context = JAXBContext.newInstance("org.xwiki.rest.model.jaxb");
				Unmarshaller unmarshaller = context.createUnmarshaller();
				IPage wikiPage = null;
				Vector<IComment> comments = null;
				Vector<IAttachment> attachments = null;
				Vector<ITag> tags = null;
				Vector<ISpace> spaces = null;
				int max_number_of_attachments = 20;
				System.out.println(id + " - about to get something from " + method.getURI());
				
				httpClient.executeMethod(method);
				switch (id) {
					case XWikiRESTService.PAGE_TYPE:
						Page page = (Page) unmarshaller.unmarshal(method.getResponseBodyAsStream());			
						
						if(page != null) {
							wikiPage = new XWikiPage();
							wikiPage.setContent(page.getContent());
							wikiPage.setCreator(page.getCreator());
							wikiPage.setVersion(page.getVersion());
							
							item = wikiPage;
						}
						break;
					case XWikiRESTService.COMMENT_TYPE:
						Comments wikiComments = (Comments) unmarshaller.unmarshal(method.getResponseBodyAsStream());
						List<Comment> wikiCommentsList = wikiComments.getComments();

						if (wikiCommentsList.size() > 0) {
							comments = new Vector<IComment>();
							IComment recoveredComment = null;
							for (Comment comment : wikiCommentsList) {
								recoveredComment = new XWikiComment();
								recoveredComment.setAuthor(comment.getAuthor());
								recoveredComment.setDate(comment.getDate().getTime());
								recoveredComment.setHighlight(comment.getHighlight());
								recoveredComment.setId(String.valueOf(comment.getId()));
								recoveredComment.setPageId(comment.getPageId());
								recoveredComment.setReplyTo(String.valueOf(comment
										.getReplyTo()));
								recoveredComment.setText(comment.getText());
								comments.addElement(recoveredComment);
							}
							
							item = comments;
						}
						break;
					case XWikiRESTService.ATTACHMENT_TYPE:
						Attachments wikiAttachments = (Attachments) unmarshaller.unmarshal(method.getResponseBodyAsStream());
						List<Attachment> wikiAttachment = wikiAttachments.getAttachments();

						if (wikiAttachment.size() > 0) {
							attachments = new Vector<IAttachment>();
							IAttachment recoveredAttachment = null;
							Attachment attachment = null;
							Object resource = null;
							//make sure to reduce max size to max_number_of_attachments
//							if(wikiAttachment.size() < max_number_of_attachments) {
//								max_number_of_attachments = wikiAttachment.size();
//							}
							
							for (int i = 0; i < wikiAttachment.size(); i++) {
								attachment = wikiAttachment.get(i);
								recoveredAttachment = new XWikiAttachment();
								recoveredAttachment.setAbsoluteUrl(attachment.getXwikiAbsoluteUrl());
								recoveredAttachment.setAuthor(attachment.getAuthor());
								recoveredAttachment.setDate(attachment.getDate().getTime());
								recoveredAttachment.setId(attachment.getId());
								recoveredAttachment.setMimeType(attachment.getMimeType());
								recoveredAttachment.setName(attachment.getName());
								recoveredAttachment.setPageId(attachment.getPageId());
								recoveredAttachment.setPageVersion(attachment.getPageVersion());
								recoveredAttachment.setRelativeUrl(attachment.getXwikiRelativeUrl());
								recoveredAttachment.setSize(String.valueOf(attachment.getSize()));
								recoveredAttachment.setVersion(attachment.getVersion());
								if(withResources) {
									WikiUtility wikiUtility = new WikiUtility(wikiUser, wikiPass, max_number_of_attachments, recoveredAttachment.getMimeType(),recoveredAttachment.getAbsoluteUrl());
									try {
										wikiUtility.start();
										wikiUtility.join();
									} catch (InterruptedException e) {
										logger.debug("getComments:  InterruptedException: " + e);
									}
									resource = wikiUtility.getResource();
								}
								else {
									attachments.addElement(recoveredAttachment);
								}

								if(resource != null && withResources) {
									recoveredAttachment.setIsValid(true);
									recoveredAttachment.setResource(resource);
									attachments.addElement(recoveredAttachment);
								}									
							}
							
							item = attachments;
						}
						break;
					case XWikiRESTService.TAG_TYPE:
						Tags wikiTags = (Tags) unmarshaller.unmarshal(method.getResponseBodyAsStream());
						List<Tag> wikiTag = wikiTags.getTags();

						if (wikiTag.size() > 0) {
							tags = new Vector<ITag>();
							ITag recoveredTag = null;
							for (Tag tag : wikiTag) {
								recoveredTag = new XWikiTag();
								recoveredTag.setName(tag.getName());
								tags.addElement(recoveredTag);
							}
							
							item = tags;
						}
						break;
					case XWikiRESTService.SPACE_TYPE:
						Spaces wikiSpaces = (Spaces) unmarshaller.unmarshal(method.getResponseBodyAsStream());
						List<Space> wikiSpacesList = wikiSpaces.getSpaces();

						if (wikiSpacesList.size() > 0) {
							spaces = new Vector<ISpace>();
							ISpace recoveredSpace = null;
							for (Space space : wikiSpacesList) {
								recoveredSpace = new XWikiSpace();
								recoveredSpace.setId(space.getId());
								recoveredSpace.setHome(space.getHome());
								recoveredSpace.setName(space.getName());
								recoveredSpace.setWiki(space.getWiki());
								recoveredSpace.setAbsoluteUrl(space.getXwikiAbsoluteUrl());
								recoveredSpace.setRelativeUrl(space.getXwikiRelativeUrl());
								spaces.addElement(recoveredSpace);
							}
							
							item = spaces;
						}
						break;
				}

			} catch (JAXBException e) {
				logger.debug("run :  JAXBException: " + e);
			} catch (IOException e) {
				logger.debug("run :  IOException: " + e);
			} 
			finally {
				// always release the connection after we're done
				method.releaseConnection();
			}
		}

	}
}
