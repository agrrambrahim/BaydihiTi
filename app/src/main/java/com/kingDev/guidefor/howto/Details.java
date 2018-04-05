package com.kingDev.guidefor.howto;


import com.kingDev.guidefor.howto.config.admob;
import com.kingDev.guidefor.howto.module.ourWebView;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Details extends AppCompatActivity {

	WebView browser;
	ActionBar actionBar;
	int rtl , trl, arrow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StartAppSDK.init(this, SettingsApp.startAppID, true);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);

		trl= R.drawable.ic_arrow_back;
		rtl= R.drawable.ic_arrow_back_rtl;
		arrow=trl;
		if(SettingsApp.supportRTL) {forceRTLIfSupported(); arrow=rtl;}
		setContentView(R.layout.activity_details);

		//this.setTitleColor(getResources().getColor(R.color.menuTextcolor));
		actionBar= this.getActionBar();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		getSupportActionBar().setHomeAsUpIndicator(arrow);

		LinearLayout linearlayout = (LinearLayout)findViewById(R.id.Unit_Ads2);
        admob.admobBannerCall(this, linearlayout);

        this.setTitle(getIntent().getStringExtra("title"));
        
		browser = (WebView) findViewById(R.id.webView1);
		
		//Toast.makeText(Details.this, "id:"+getIntent().getStringExtra("id"), Toast.LENGTH_SHORT).show();
		
		browser.getSettings().setJavaScriptEnabled(true);
		browser.getSettings().setLoadWithOverviewMode(true);
		browser.getSettings().setUseWideViewPort(true);
		browser.setWebViewClient(new ourWebView());
		
		final StringBuilder s = new StringBuilder();    
 	//"<style>body {line-height: 170%;text-align:justify;}</style>" +
		s.append("<html>");  
		s.append("<head><meta name='viewport' content='width=device-width, user-scalable=no' >" +
				"<style>body {line-height: 170%;}</style>" +
				"</head>");
		s.append("<body style='padding-bottom:60px'>");
		s.append(getIntent().getStringExtra("detail"));
		s.append("</body>");                            
		s.append("</html>");
		browser.loadDataWithBaseURL(null, s.toString(), "text/html", "UTF-8", null);


	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	private void forceRTLIfSupported()
	{
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
			getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
		}
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		/*int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);*/
		switch (item.getItemId()) {
        case android.R.id.home:
            //Toast.makeText(this, "This is my Toast message!",
                    //Toast.LENGTH_LONG).show();
            /*Intent intent = new Intent(Details.this, ListViews.class);
            startActivity(intent);*/
            this.finish();
        	//finishActivity(0);
            return true;
        default:
            Toast.makeText(this, "Sorry some Error block app",
                    Toast.LENGTH_LONG).show();
            return super.onOptionsItemSelected(item);
    }
	}
}
