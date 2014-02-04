package cz.velim.rssreader.activities;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import cz.velim.rssreader.R;
import cz.velim.rssreader.rss.RssItem;
import cz.velim.rssreader.rss.RssParser;

public class MainActivity extends Activity {

	public static final int RSS_BBC_TECHNOLOGY_ID = 0;
	public static final String RSS_BBC_TECHNOLOGY = "http://feeds.bbci.co.uk/news/technology/rss.xml";

	public static final int RSS_BBC_TOP_STORIES_ID = 1;
	public static final String RSS_BBC_TOP_STORIES = "http://feeds.bbci.co.uk/news/rss.xml";

	public static final int RSS_IDNES_TECHNET_ID = 2;
	public static final String RSS_IDNES_TECHNET = "http://technet.idnes.feedsportal.com/c/33324/f/565937/index.rss";

	public static final int RSS_IDNES_MOBIL_ID = 3;
	public static final String RSS_IDNES_MOBIL = "http://mobil.idnes.cz.feedsportal.com/c/33698/f/597321/index.rss";

	ListView listView;
	List<RssItem> topics;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.i("rss", "onCreate");
		listView = (ListView) findViewById(R.id.listView1);
		loadRssItemsToList(MainActivity.RSS_BBC_TECHNOLOGY);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		menu.add(0, MainActivity.RSS_BBC_TOP_STORIES_ID, 0,
				R.string.bbc_top_stories);
		menu.add(0, MainActivity.RSS_BBC_TECHNOLOGY_ID, 0,
				R.string.bbc_technology);
		menu.add(1, MainActivity.RSS_IDNES_MOBIL_ID, 0, R.string.idnes_mobil);
		menu.add(1, MainActivity.RSS_IDNES_TECHNET_ID, 0,
				R.string.idnes_technet);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MainActivity.RSS_BBC_TECHNOLOGY_ID:
			loadRssItemsToList(MainActivity.RSS_BBC_TECHNOLOGY);
			return true;
		case MainActivity.RSS_BBC_TOP_STORIES_ID:
			loadRssItemsToList(MainActivity.RSS_BBC_TOP_STORIES);
			return true;
		case MainActivity.RSS_IDNES_TECHNET_ID:
			loadRssItemsToList(MainActivity.RSS_IDNES_TECHNET);
			return true;
		case MainActivity.RSS_IDNES_MOBIL_ID:
			loadRssItemsToList(MainActivity.RSS_IDNES_MOBIL);
			return true;
		default:
			return false;
		}

	}

	private void loadRssItemsToList(String rssSource) {

		new HttpAsyncTask().execute(rssSource);

	}

	private class HttpAsyncTask extends AsyncTask<String, Void, List<RssItem>> {
		@Override
		protected List<RssItem> doInBackground(String... urls) {

			RssParser rssParser = new RssParser(urls[0]);
			topics = rssParser.gesRssTopics();

			if (topics == null) {
				// Toast.makeText(getApplicationContext(), "rss download error",
				// Toast.LENGTH_LONG).show();
				return null;
			}
			return topics;
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(List<RssItem> result) {
			if (result == null) {
				Toast.makeText(getApplicationContext(), "rss download error",
						Toast.LENGTH_LONG).show();
				return;
			}
			Toast.makeText(getBaseContext(), "download complete",
					Toast.LENGTH_SHORT).show();
			listView.setAdapter(new RssListAdapter(getBaseContext(), result));

			listView.setOnItemClickListener(new OnItemClickListener() {
				@SuppressLint("InlinedApi")
				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					RssItem rssItem = topics.get(position);
					Intent intent = new Intent(getBaseContext(),
							RssDetailActivity.class);
					intent.putExtra("rssItem", rssItem);
					startActivity(intent);
				}
			});
		}
	}

}
