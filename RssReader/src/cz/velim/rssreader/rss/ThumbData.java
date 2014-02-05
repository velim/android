package cz.velim.rssreader.rss;

import android.graphics.Bitmap;

public class ThumbData {
	private String link;
	private int width;
	private int height;
	private Bitmap bitmap;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = Integer.parseInt(width);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = Integer.parseInt(height);
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	

}
