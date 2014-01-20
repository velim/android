package cz.velim.applist;

import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static final String SCHEME = "package";
	private static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";
	private static final String APP_PKG_NAME_22 = "pkg";
	private static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";
	private static final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";

	private List<ResolveInfo> resolveInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView listView = (ListView) findViewById(R.id.listView1);

		final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		resolveInfo = (List<ResolveInfo>) getPackageManager()
				.queryIntentActivities(mainIntent, 0);
		listView.setAdapter(new AppListAdapter(this, resolveInfo));

		listView.setOnItemClickListener(new OnItemClickListener() {
			@SuppressLint("InlinedApi")
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				ActivityInfo activityInfo = resolveInfo.get(position).activityInfo;
				Intent intent = new Intent();
				final int apiLevel = Build.VERSION.SDK_INT;
				if (apiLevel >= 9) { // above 2.3
					intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
					Uri uri = Uri.fromParts(SCHEME, activityInfo.applicationInfo.packageName, null);
					intent.setData(uri);
				} else { // below 2.3
					final String appPkgName = (apiLevel == 8 ? APP_PKG_NAME_22
							: APP_PKG_NAME_21);
					intent.setAction(Intent.ACTION_VIEW);
					intent.setClassName(APP_DETAILS_PACKAGE_NAME,
							APP_DETAILS_CLASS_NAME);
					intent.putExtra(appPkgName, activityInfo.applicationInfo.packageName);
				}
				startActivity(intent);
				Toast.makeText(
						MainActivity.this,
						activityInfo.name + "\n"
								+ activityInfo.applicationInfo.dataDir,
						Toast.LENGTH_SHORT).show();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
