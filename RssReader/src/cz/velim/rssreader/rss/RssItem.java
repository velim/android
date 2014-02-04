package cz.velim.rssreader.rss;

import java.io.Serializable;

public class RssItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2547316623069795525L;
	private String title;
	private String pubDate;
	private String link;
	private String desc;

	public void setTitle(String text) {
		title = text;
	}

	public String getTitle() {
		return title;
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

}
