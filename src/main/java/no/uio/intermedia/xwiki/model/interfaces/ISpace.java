package no.uio.intermedia.xwiki.model.interfaces;


/**
 * XWiki space
 * 
 * @author Anthony Perritano
 * @author Jeremy Toussaint
 *
 */
public interface ISpace {

	/**
	 * @return String id
	 */
	public String getId();

	/**
	 * @param id
	 */
	public void setId(String id);

	/**
	 * @return String wiki
	 */
	public String getWiki();

	/**
	 * @param wiki
	 */
	public void setWiki(String wiki);

	/**
	 * @return String name
	 */
	public String getName();

	/**
	 * @param name
	 */
	public void setName(String name);

	/**
	 * @return String home
	 */
	public String getHome();

	/**
	 * @param home
	 */
	public void setHome(String home);

	/**
	 * @return String url
	 */
	public String getRelativeUrl();

	/**
	 * @param relativeUrl
	 */
	public void setRelativeUrl(String relativeUrl);

	/**
	 * @return String url
	 */
	public String getAbsoluteUrl();

	/**
	 * @param absoluteUrl
	 */
	public void setAbsoluteUrl(String absoluteUrl);

}
