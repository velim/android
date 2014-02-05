package cz.velim.rssreader.rss;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public class RssParser {

	private static final String LOG_TAG = RssParser.class.getSimpleName();

	private static final String TAG_MEDIA_THUMBNAIL_NAME = "media:thumbnail";
	private static final String TAG_DESCRIPTION_NAME = "description";
	private static final String TAG_LINK_NAME = "link";
	private static final String TAG_PUB_DATE_NAME = "pubDate";
	private static final String TAG_TITLE_NAME = "title";
	private static final String TAG_CHANNEL_NAME = "channel";
	private static final String TAG_ITEM_NAME = "item";
	private static final String ATTR_URL_NAME = "url";	
	private static final String ATTR_HEIGHT_NAME = "height";
	private static final String ATTR_WIDTH_NAME = "width";

	InputStream inputRss;

	public RssParser(String rssSource) {

		RssFeed rssFeed = new RssFeed(rssSource);
		inputRss = rssFeed.getInputStream();
	}

	public ArrayList<RssItem> gesRssTopics() {
		if (inputRss == null)
			return null;
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(inputRss, null);
			parser.nextTag();
			return readRss(parser);
		} catch (XmlPullParserException e) {
			Log.e(LOG_TAG, "XmlPullParserException");
			Log.e(LOG_TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(LOG_TAG, "IOException");
			Log.e(LOG_TAG, e.getMessage());
		} finally {
			try {
				inputRss.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	private ArrayList<RssItem> readRss(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		ArrayList<RssItem> entries = new ArrayList<RssItem>();

		parser.require(XmlPullParser.START_TAG, null, "rss");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}

			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals(TAG_CHANNEL_NAME)) {
				entries = readChannel(parser);
			} else {
				skip(parser);
			}
		}
		return entries;
	}

	private ArrayList<RssItem> readChannel(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		ArrayList<RssItem> entries = new ArrayList<RssItem>();

		parser.require(XmlPullParser.START_TAG, null, TAG_CHANNEL_NAME);
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals(TAG_ITEM_NAME)) {
				entries.add(readItem(parser));
			} else {
				skip(parser);
			}
		}
		return entries;
	}

	private RssItem readItem(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		RssItem item = new RssItem();

		parser.require(XmlPullParser.START_TAG, null, TAG_ITEM_NAME);
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}

			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals(TAG_TITLE_NAME)) {

				if (parser.next() == XmlPullParser.TEXT) {
					item.setTitle(parser.getText());
					parser.nextTag();
				}

			} else if (name.equals(TAG_PUB_DATE_NAME)) {

				if (parser.next() == XmlPullParser.TEXT) {
					item.setPubDate(parser.getText());
					parser.nextTag();
				}

			} else if (name.equals(TAG_LINK_NAME)) {

				if (parser.next() == XmlPullParser.TEXT) {
					item.setLink(parser.getText());
					parser.nextTag();
				}

			} else if (name.equals(TAG_DESCRIPTION_NAME)) {

				if (parser.next() == XmlPullParser.TEXT) {
					item.setDesc(parser.getText());
					parser.nextTag();
				}

			} else if (name.equals(TAG_MEDIA_THUMBNAIL_NAME)
					&& parser.getAttributeValue(null, ATTR_WIDTH_NAME).equals(
							"66")) {

				item.setThumb66(parser.getAttributeValue(null, ATTR_URL_NAME),
						parser.getAttributeValue(null, ATTR_WIDTH_NAME),
						parser.getAttributeValue(null, ATTR_HEIGHT_NAME));
				parser.nextTag();

			} else if (name.equals(TAG_MEDIA_THUMBNAIL_NAME)
					&& parser.getAttributeValue(null, ATTR_WIDTH_NAME).equals(
							"144")) {

				item.setThumb144(parser.getAttributeValue(null, ATTR_URL_NAME),
						parser.getAttributeValue(null, ATTR_WIDTH_NAME),
						parser.getAttributeValue(null, ATTR_HEIGHT_NAME));
				parser.nextTag();

			} else {
				skip(parser);
			}
		}
		return item;
	}

	private void skip(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}

}
