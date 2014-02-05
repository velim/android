package cz.velim.rssreader.rss;

import java.io.Serializable;

public class RssItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2547316623069795525L;
	protected static final String TAG = RssItem.class.getSimpleName();
	private String mTitle;
	private String pubDate;
	private String link;
	private String desc;
	private ThumbData mThumb66;
	private ThumbData mThumb144;

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

	public void setThumb66(String url, String width, String height) {
		mThumb66 = new ThumbData();
		mThumb66.setLink(url);
		mThumb66.setWidth(width);
		mThumb66.setHeight(height);
	}

	public ThumbData getThumb66() {
		return mThumb66;
	}

	public void setThumb144(String url, String width, String height) {
		mThumb144 = new ThumbData();
		mThumb144.setLink(url);
		mThumb144.setWidth(width);
		mThumb144.setHeight(height);
	}

	public ThumbData getThumb144() {
		return mThumb144;
	}

}
