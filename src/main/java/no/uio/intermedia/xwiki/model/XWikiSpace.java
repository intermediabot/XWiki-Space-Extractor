package no.uio.intermedia.xwiki.model;

import no.uio.intermedia.xwiki.model.interfaces.ISpace;


/**
 * Represents a Space
 * 
 * @author Anthony Perritano
 * @author Jeremy Toussaint
 *
 */
public class XWikiSpace implements ISpace {
	
	private String id = null;
	private String wiki = null;
	private String name = null;
	private String home = null;
	private String xwikiRelativeUrl = null;
	private String xwikiAbsoluteUrl = null;
	
	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String getWiki() {
		return wiki;
	}
	
	@Override
	public void setWiki(String wiki) {
		this.wiki = wiki;
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
	public String getHome() {
		return home;
	}
	
	@Override
	public void setHome(String home) {
		this.home = home;
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
}
