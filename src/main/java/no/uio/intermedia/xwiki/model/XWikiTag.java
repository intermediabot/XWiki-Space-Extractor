package no.uio.intermedia.xwiki.model;

import no.uio.intermedia.xwiki.model.interfaces.ITag;


/**
 * Represents a xwiki tag
 * 
 * @author Anthony Perritano
 * @author Jeremy Toussaint
 *
 */
public class XWikiTag implements ITag {

	private String name = null;
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

}
