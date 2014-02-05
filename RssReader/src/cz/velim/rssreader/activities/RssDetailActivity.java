package cz.velim.rssreader.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import cz.velim.rssreader.R;
import cz.velim.rssreader.rss.RssItem;

public class RssDetailActivity extends Activity {
	RssItem rssItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rss_detail);
		ArrayList<RssItem> rssArray = getIntent().getParcelableArrayListExtra(
				MainActivity.KEY_RSS_ITEM);
		rssItem = rssArray.get(0);
		WebView wv = (WebView) findViewById(R.id.webViewRssDesc);
		final String mimeType = "text/html";
		final String encoding = "UTF-8";
		wv.loadDataWithBaseURL("", rssItem.getDesc(), mimeType, encoding, "");

		if (rssItem.getThumb144() != null) {
			ImageView iv = (ImageView) findViewById(R.id.imageView1);
			Bitmap bitmap = rssItem.getThumb144().getBitmap();
			iv.setImageBitmap(bitmap);
		}

	}

	public void showInBrowser(View v) {

		String url = rssItem.getLink();
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);

	}

}
