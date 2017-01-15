package com.extect.appbase;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.Builder;

import utils.Utils;

public class BaseApplication extends Application {

	private static Picasso picasso;
	public static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		// Initialize the SDK before executing any other operations,
		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);
		//Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
		/**
		 * initialize Intercom in Application space
		 */

		//context =

	}
	public static Picasso getPicasso(Context context) {
		if (picasso == null) {
			Builder picassoBuilder = new Builder(context);
			picassoBuilder.downloader(new Utils.UrlCustomDownloader(context));
			picasso = picassoBuilder.build();
			//picasso.setIndicatorsEnabled(true);
		}
		return picasso;
	}


}
