package com.hasmobi.rambo.supers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.hasmobi.lib.DDebug;
import com.hasmobi.rambo.utils.RamManager;

public class DFragment extends Fragment {

	public boolean fragmentVisible = false;

	public Context c;

	// private Tracker tracker;

	// Placeholder property ID.
	// private static final String GA_PROPERTY_ID = "UA-1704294-170";

	// Dispatch period in seconds.
	// private static final int GA_DISPATCH_PERIOD = 5;

	private BroadcastReceiver broadcast = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		c = getActivity().getBaseContext();

		super.onCreate(savedInstanceState);

		// this.tracker = EasyTracker.getInstance(this.getActivity());
		// this.tracker.getLogger().setLogLevel(LogLevel.VERBOSE);

		broadcast = new BroadcastReceiver() {

			@Override
			public void onReceive(Context c, Intent i) {
				handleBroadcast(c, i);
			}

		};

		c.registerReceiver(broadcast, new IntentFilter(
				RamManager.ACTION_RAM_MANAGER));
	}

	@Override
	public void onPause() {
		fragmentVisible = false;

		DDebug.log(getClass().toString(), "onPause()");
		if (broadcast != null)
			c.unregisterReceiver(broadcast);

		super.onPause();
	}

	@Override
	public void onResume() {
		fragmentVisible = true;

		if (broadcast == null) {
			broadcast = new BroadcastReceiver() {
				@Override
				public void onReceive(Context c, Intent i) {
					handleBroadcast(c, i);
				}
			};
		}

		c.registerReceiver(broadcast, new IntentFilter(
				RamManager.ACTION_RAM_MANAGER));

		super.onResume();

		DDebug.log(getClass().toString(), "onResume()");

		// Log the fragment view event to Google Analytics
		// this.tracker.set(Fields.SCREEN_NAME, getClass().getSimpleName());
		// this.tracker.send(MapBuilder.createAppView().build());
	}

	public void handleBroadcast(Context c, Intent i) {
		DDebug.log(getClass().toString(), "handleBroadcast()");
	}

	public void hideView(View v) {
		if (v != null)
			v.setVisibility(View.GONE);
	}

	public void showView(View v) {
		if (v != null)
			v.setVisibility(View.VISIBLE);
	}

	public void log(String s) {
		DDebug.log(getClass().toString(), s);
	}

}
