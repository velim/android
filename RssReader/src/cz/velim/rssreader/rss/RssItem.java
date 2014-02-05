package cz.velim.rssreader.rss;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class RssItem implements Parcelable, Serializable {
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

	public RssItem(Parcel p) {
		setTitle(p.readString());
		setPubDate(p.readString());
		setLink(p.readString());
		setDesc(p.readString());

		Bitmap bitmap1 = (Bitmap) p.readParcelable(getClass().getClassLoader());
		if (bitmap1 != null) {
			mThumb66 = new ThumbData();
			mThumb66.setBitmap(bitmap1);
		}

		Bitmap bitmap2 = (Bitmap) p.readParcelable(getClass().getClassLoader());
		if (bitmap2 != null) {
			mThumb144 = new ThumbData();
			mThumb144.setBitmap(bitmap2);
		}

	}

	public RssItem() {

	}

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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel des, int flags) {
		des.writeString(getTitle());
		des.writeString(getPubDate());
		des.writeString(getLink());
		des.writeString(getDesc());
		if (getThumb66() != null)
			des.writeParcelable(getThumb66().getBitmap(), flags);
		if (getThumb144() != null)
			des.writeParcelable(getThumb144().getBitmap(), flags);
	}

	public static final Parcelable.Creator<RssItem> CREATOR = new Creator<RssItem>() {

		public RssItem createFromParcel(Parcel source) {

			return new RssItem(source);
		}

		public RssItem[] newArray(int size) {

			return new RssItem[size];
		}

	};

}
