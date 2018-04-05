package com.kingDev.guidefor.howto.config;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.kingDev.guidefor.howto.SettingsApp;

import android.app.Activity;
import android.media.audiofx.BassBoost.Settings;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class admob {
	

	public static int nbShowInterstitial = 2;
	public static int mCount = 0;
	
	public static void admobBannerCall(Activity acitivty , LinearLayout linerlayout){
		
        AdView adView = new AdView(acitivty);
        adView.setAdUnitId(SettingsApp.admBanner);
        adView.setAdSize(AdSize.SMART_BANNER);
        AdRequest.Builder builder = new AdRequest.Builder();
        adView.loadAd(builder.build());
        linerlayout.addView(adView);

	}
	
	public static void admobBannerCall(Activity acitivty , RelativeLayout relativeLayout){
		
        AdView adView = new AdView(acitivty);
        adView.setAdUnitId(SettingsApp.admBanner);
        adView.setAdSize(AdSize.SMART_BANNER);
        AdRequest.Builder builder = new AdRequest.Builder();
        adView.loadAd(builder.build());
        relativeLayout.addView(adView);

	}
	
	public static String getBannerId(){
		return SettingsApp.admBanner;
	}
	
}