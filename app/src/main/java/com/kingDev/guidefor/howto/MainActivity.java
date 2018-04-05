package com.kingDev.guidefor.howto;


import com.kingDev.guidefor.howto.config.admob;
import com.kingDev.guidefor.howto.database.DataBaseHelper;
import com.kingDev.guidefor.howto.R;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	InterstitialAd  mInterstitialAd;
	AdRequest adRequest;
	Button start;
	ProgressBar progressBar ;
	private final int SPLASH_DISPLAY_LENGTH = 7000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StartAppSDK.init(this, SettingsApp.startAppID, true);
		setContentView(R.layout.main_activity);

		if(SettingsApp.supportRTL) forceRTLIfSupported();
		
		start = (Button) findViewById(R.id.start);
		DataBaseHelper.setmDatabase(this);
        AppRater.setAPP_PNAME(this);



        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(SettingsApp.Interstitial);
        progressBar=(ProgressBar) findViewById(R.id.progressBar1);
        mInterstitialAd.setAdListener(new AdListener() {
        	@Override
        	public void onAdClosed() {
        		requestNewInterstitial();
        	}
		});

        requestNewInterstitial();
		
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
               progressBar.setVisibility(View.GONE);
               start.setVisibility(View.VISIBLE);
            }
        }, SPLASH_DISPLAY_LENGTH);
        
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,ListViewsItems.class);
				//intent.putExtra("id",""+itemClicked.getDirection());
				//startActivityForResult(intent, 1);
				startActivity(intent);
				if (mInterstitialAd.isLoaded())  mInterstitialAd.show();
				finish();
				//else Toast.makeText(Start.this, "nonLoaded", Toast.LENGTH_SHORT);
			}
		});
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	private void forceRTLIfSupported()
	{
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
			getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
		}
	}

	private void requestNewInterstitial() {
		 AdRequest adRequest = new AdRequest.Builder().build();
		 mInterstitialAd.loadAd(adRequest);
	}

	
}
