package cz.velim.rssreader.rss;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class RssFeed {

	private InputStream resultStream;

	public RssFeed(String rssSource) {
		resultStream = getRssInputOverHttp(rssSource);
	}

	public InputStream getInputStream() {
		return resultStream;

	}

	private InputStream getRssInputOverHttp(String rssSource) {
		InputStream input = getHttpContentEasily(rssSource);
		if (input == null) {
			Log.e("RssFeed", "input == null");
			return null;
		}
		return input;
	}

	public InputStream getHttpContentEasily(String url) {
		HttpURLConnection conn;

		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(2000);
			conn.setReadTimeout(2000);
			conn.setInstanceFollowRedirects(true);
			return (conn.getInputStream());
		} catch (MalformedURLException e) {
			Log.e("RssFeed", "MalformedURLException");
		} catch (IOException e) {
			Log.e("RssFeed", "IOException");
		}
		return null;
	}

}
