package cz.velim.rssreader.rss;

import java.io.Serializable;

public class RssItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2547316623069795525L;
	private String mTitle;
	private String pubDate;
	private String link;
	private String desc;
	private String mUrlThumb66;
	private String thumb144;

	public void setTitle(String text) {
		mTitle = text;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setPubDate(String text) {
		pubDate = text;

	}

	public String getPubDate() {
		return pubDate;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLink() {
		return link;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setThumb66(String url) {
		mUrlThumb66 = url;
	}

	public String getThumb66() {
		return mUrlThumb66;
	}

	public String getThumb144() {
		return thumb144;
	}

	public void setThumb144(String url) {
		thumb144 = url;
	}

}
