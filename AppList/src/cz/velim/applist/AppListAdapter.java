package cz.velim.applist;

import java.util.List;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AppListAdapter extends BaseAdapter {

	private Context mContext;
	final List<ResolveInfo> pkgAppsList;

	public AppListAdapter(Context c, List<ResolveInfo> reslveInfo) {
		mContext = c;
		pkgAppsList = reslveInfo;
	}

	@Override
	public int getCount() {
		return pkgAppsList.size();
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

		TextView textview = new TextView(mContext);
		// textview.setLayoutParams(new GridView.LayoutParams(85, 85));
		textview.setPadding(20, 20, 20, 20);
		ActivityInfo activityInfo = pkgAppsList.get(position).activityInfo;
		textview.setText(mContext.getPackageManager().getApplicationLabel(activityInfo.applicationInfo));

		return textview;
	}

}
