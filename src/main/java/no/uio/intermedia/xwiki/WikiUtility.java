package no.uio.intermedia.xwiki;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

/**
 * XWiki util class, does download multithreaded.
 * 
 * @author Anthony Perritano
 * @author Jeremy Toussaint
 *
 */
public class WikiUtility extends Thread {
	
	private static final Logger logger = Logger.getLogger(WikiUtility.class.getName());
	private String wikiUser = null;
	private String wikiPass = null;
	private String mimeType = null;
	private String absoluteUrl = null;
	private int maxFileSize = 0;
	private Object resource = null;

	/**
	 * @param wikiUser
	 * @param wikiPass
	 * @param absoluteUrl 
	 * @param mimeType 
	 */
	public WikiUtility(String wikiUser, String wikiPass, int maxFileSize, String mimeType, String absoluteUrl) {
		this.wikiUser = wikiUser;
		this.wikiPass = wikiPass;
		this.maxFileSize = maxFileSize;
		this.mimeType = mimeType;
		this.absoluteUrl = absoluteUrl;
	}
	
	/**
	 * @param mimeType
	 * @param absoluteUrl
	 * @return Object(BufferedImage, pdf, video, etc)
	 */
	public Object getResource() {
		return resource;
	}
	
	public void run() {

		String passwdstring = wikiUser + ":" + wikiPass;
		String encoding = new sun.misc.BASE64Encoder().encode(passwdstring.getBytes());
		InputStream content = null;

		if (mimeType.equals("image/png") || mimeType.equals("image/jpeg")
				|| mimeType.equals("image/jpg") || mimeType.equals("image/gif")) {
			// we have a picture
			URL url;
			Image image = null;
			try {
				url = new URL(absoluteUrl);

				URLConnection uc = url.openConnection();
				uc.setRequestProperty("Authorization", "Basic " + encoding);
				logger.info("Object size from headers: "+uc.getHeaderField("Content-Length")+" -- "+uc.getHeaderFields());
				
				if(uc.getHeaderField("Content-Length") != null) {
					int fileSize = Integer.valueOf(uc.getHeaderField("Content-Length"));
					
					if(fileSize < maxFileSize) {
						content = uc.getInputStream();
						logger.info("Object size: "+content.available()+" - "+absoluteUrl);
						image = ImageIO.read(content);
						resource = image;
						content.close();						
					}
					else {
						logger.info("Sorry luv', your file's too big ..");
					}
				}
				else {
					logger.info("The header returns a null file size ..");
				}
			} catch (MalformedURLException e) {
				logger.debug("getResource image/*:  MalformedURLException: "
						+ e);
			} catch (IOException e) {
				logger.debug("getResource image/*:  IOException: " + e);
			}
		} 
		else {
			logger.info("Object is not of type image: "+absoluteUrl);
		}
//		else {
//			// we have a file
//			URL url;
//			byte[] byteFile = null;
//			try {
//				url = new URL(absoluteUrl);
//
//				URLConnection uc = url.openConnection();
//				uc.setRequestProperty("Authorization", "Basic " + encoding);
//				content = (InputStream) uc.getInputStream();
//				byteFile = IOUtils.toByteArray(content);
//				resource = byteFile;
//			} catch (MalformedURLException e) {
//				logger.debug("getResource image/*:  MalformedURLException: "
//						+ e);
//			} catch (IOException e) {
//				logger.debug("getResource image/*:  IOException: " + e);
//			}
//		}

	}

}
