package cz.velim.rssreader.rss;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public class RssParser {
	InputStream inputRss;

	public RssParser( String rssSource)  {

		
		RssFeed rssFeed = new RssFeed(rssSource);
		inputRss = rssFeed.getInputStream();
	}

	public List<RssItem> gesRssTopics() {
		if (inputRss == null)
			return null;
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(inputRss, null);
			parser.nextTag();
			return readRss(parser);
		} catch (XmlPullParserException e) {
			Log.e("RssParser", "XmlPullParserException");
			Log.e("RssParser", e.getMessage());
		} catch (IOException e) {
			Log.e("RssParser", "IOException");
			Log.e("RssParser", e.getMessage());
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

	private List<RssItem> readRss(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		List<RssItem> entries = new ArrayList<RssItem>();

		parser.require(XmlPullParser.START_TAG, null, "rss");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}

			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals("channel")) {
				entries = readChannel(parser);
			} else {
				skip(parser);
			}
		}
		return entries;
	}

	private List<RssItem> readChannel(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		List<RssItem> entries = new ArrayList<RssItem>();

		parser.require(XmlPullParser.START_TAG, null, "channel");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals("item")) {
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

		parser.require(XmlPullParser.START_TAG, null, "item");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}

			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals("title")) {

				if (parser.next() == XmlPullParser.TEXT) {
					item.setTitle(parser.getText());
					parser.nextTag();
				}

			} else if (name.equals("pubDate")) {

				if (parser.next() == XmlPullParser.TEXT) {
					item.setPubDate(parser.getText());
					parser.nextTag();
				}

			} else if (name.equals("link")) {

				if (parser.next() == XmlPullParser.TEXT) {
					item.setLink(parser.getText());
					parser.nextTag();
				}

			} else if (name.equals("description")) {

				if (parser.next() == XmlPullParser.TEXT) {
					item.setDesc(parser.getText());
					parser.nextTag();
				}

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
