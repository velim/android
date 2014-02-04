package cz.velim.rssreader.activities;

import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cz.velim.rssreader.R;
import cz.velim.rssreader.rss.RssFeed;
import cz.velim.rssreader.rss.RssItem;

public class RssListAdapter extends BaseAdapter {

	private Context mContext;
	final List<RssItem> rssList;

	public RssListAdapter(Context c, List<RssItem> rssItems) {
		mContext = c;
		rssList = rssItems;
	}

	@Override
	public int getCount() {
		return rssList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ListItemHolder holder = new ListItemHolder();

		// creating LayoutInflator for inflating the row layout.
		LayoutInflater inflator = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// inflating the row layout we defined earlier.
		convertView = inflator.inflate(R.layout.list_item, null);

		// setting the views into the ViewHolder.
		holder.title = (TextView) convertView.findViewById(R.id.title);
		holder.title.setText(rssList.get(position).getTitle());
		holder.date = (TextView) convertView.findViewById(R.id.date);
		holder.date.setText(rssList.get(position).getPubDate());
		holder.image = (ImageView) convertView.findViewById(R.id.image);

		new AsyncTask<String, Void, Bitmap>() {

			@Override
			protected Bitmap doInBackground(String... urls) {
				String url = urls[0];
				RssFeed rssFeed = new RssFeed(url);
				InputStream inputStream = rssFeed.getInputStream();
				return BitmapFactory.decodeStream(inputStream);
			}
			
			@Override
			protected void onPostExecute(Bitmap bitmap) {
				if (bitmap != null)
					holder.image.setImageBitmap(bitmap);
			}

		}.execute(rssList.get(position).getThumb66());

		// return the row view.
		return convertView;
	}

}
