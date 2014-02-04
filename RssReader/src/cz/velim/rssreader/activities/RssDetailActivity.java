package cz.velim.rssreader.activities;

import cz.velim.rssreader.R;
import cz.velim.rssreader.R.id;
import cz.velim.rssreader.R.layout;
import cz.velim.rssreader.rss.RssItem;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class RssDetailActivity extends Activity {
	RssItem rssItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rss_detail);
		rssItem = (RssItem) getIntent().getSerializableExtra("rssItem");
		WebView wv = (WebView) findViewById(R.id.webViewRssDesc);
		final String mimeType = "text/html";
		final String encoding = "UTF-8";

		wv.loadDataWithBaseURL("", rssItem.getDesc(), mimeType, encoding, "");
	}

	public void showInBrowser(View v) {

		String url = rssItem.getLink();
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);

	}

}
