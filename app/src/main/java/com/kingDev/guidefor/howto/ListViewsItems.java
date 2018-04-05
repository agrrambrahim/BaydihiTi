package com.kingDev.guidefor.howto;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import com.kingDev.guidefor.howto.adapter.ListCategorieAdapter;
import com.kingDev.guidefor.howto.config.admob;
import com.kingDev.guidefor.howto.adapter.ListArticleAdapter;
import com.kingDev.guidefor.howto.database.DataBaseHelper;
import com.kingDev.guidefor.howto.module.Categorie;
import com.kingDev.guidefor.howto.module.Item;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.startapp.android.publish.ads.nativead.NativeAdDetails;
import com.startapp.android.publish.ads.nativead.NativeAdPreferences;
import com.startapp.android.publish.ads.nativead.StartAppNativeAd;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ListViewsItems extends Activity {
    
	InterstitialAd  mInterstitialAd;
	AdRequest adRequest;
    private ListView lvItem;
    private ListArticleAdapter adapter;
    private List<Item> mItemList;
    private DataBaseHelper mDBHelper;
	private ListCategorieAdapter adapterCategorie;
	private List<Categorie> mCategorieList;
	private  LinearLayout popup;
	private boolean currentListCategorie = false;
	private ImageView searchBtn;
	private EditText search;
	private TextView categories, allArticles, noArticles;
	private Button rateus, shareapp;
	private int id_cat;

	private StartAppAd startAppAd = new StartAppAd(this);
	/** StartApp Native Ad declaration */
	private StartAppNativeAd startAppNativeAd = new StartAppNativeAd(this);
	private NativeAdDetails nativeAd = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view);
		StartAppSDK.init(this, SettingsApp.startAppID, true);
		//View adContainer = findViewById(R.id.unitads);

		if(SettingsApp.supportRTL) forceRTLIfSupported();

		final String packageName = this.getPackageName();
		mDBHelper = new DataBaseHelper(this);
		lvItem = (ListView)findViewById(R.id.listViewtest);
		File database = getApplicationContext().getDatabasePath(DataBaseHelper.DBNAME);
		if(false == database.exists()) {
			mDBHelper.getReadableDatabase();
			if(copyDatabase(this)) {
				//Toast.makeText(this, "Copy database succes", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Copy data error"+DataBaseHelper.DBLOCATION, Toast.LENGTH_LONG).show();
				return;
			}
		}

        LinearLayout linearlayout = (LinearLayout)findViewById(R.id.unitads);
        admob.admobBannerCall(this, linearlayout);
        
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(SettingsApp.Interstitial);
        mInterstitialAd.setAdListener(new AdListener() {
        	@Override
        	public void onAdClosed() {
        		requestNewInterstitial();
        	}
		});
        requestNewInterstitial();

		search = (EditText) findViewById(R.id.text_search);
		searchBtn = (ImageView) findViewById(R.id.btn_search);
		categories = (TextView) findViewById(R.id.tab_cat);
		allArticles = (TextView) findViewById(R.id.tab_allArticles);
		noArticles = (TextView) findViewById(R.id.noArticles);
        rateus = (Button)findViewById(R.id.rateus2);
        shareapp= (Button) findViewById(R.id.play2);

        rateus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AppRater.rateLink(ListViewsItems.this);
			}
		});
        shareapp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ShareApp();
			}
		});
        searchBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if(currentListCategorie) adapterCategoriesFilterByName(String.valueOf(search.getText()));
				else if(id_cat!=0) adapterArticlesFilterByNameAndCategorie(String.valueOf(search.getText()), id_cat);
					 else adapterArticlesFilterByName(String.valueOf(search.getText()));
			}
		});

		categories.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				allArticles.setBackgroundResource(R.drawable.rounded_corner);
				categories.setBackgroundResource(R.drawable.rounded_corner_dark);
				currentListCategorie = true;
				adapterCategories();
			}
		});

		allArticles.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				categories.setBackgroundResource(R.drawable.rounded_corner);
				allArticles.setBackgroundResource(R.drawable.rounded_corner_dark);
				currentListCategorie = false;
				id_cat = 0;
				adapterAllArticles();
			}
		});
		currentListCategorie = true;
		adapterCategories();
		itemSelected();
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	private void forceRTLIfSupported()
	{
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
			getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
		}
	}

	public void adapterCategories(){
		noArticles.setVisibility(View.GONE);
		//Get product list in db when db exists
		mCategorieList = mDBHelper.getListCtegories();
		//Init adapter
		adapterCategorie = new ListCategorieAdapter(this, mCategorieList);
		//Set adapter for listview
		lvItem.setAdapter(adapterCategorie);
		itemSelected();
	}

	public void adapterAllArticles(){
		//Get product list in db when db exists
		mItemList = mDBHelper.getListItem();
		if(mItemList.size()!=0) {
			noArticles.setVisibility(View.GONE);
			//Init adapter
			adapter = new ListArticleAdapter(this, mItemList);
			//Set adapter for listview
			lvItem.setAdapter(adapter);
		} else {
			currentListCategorie= false;
			noArticles.setVisibility(View.VISIBLE);
		}
	}

	public void adapterArticlesFilterByIdCat(int id_cat){
		mItemList = mDBHelper.getListItemFilterByIdCat(id_cat);
		if(mItemList.size()!=0) {
			noArticles.setVisibility(View.GONE);
			//Init adapter
			adapter = new ListArticleAdapter(this, mItemList);
			//Set adapter for listview
			lvItem.setAdapter(adapter);
		} else {
			currentListCategorie= false;
			noArticles.setVisibility(View.VISIBLE);
		}
	}

	public void adapterArticlesFilterByName(String name){
		mItemList = mDBHelper.getListItemFilterByTitle(name);
		if(mItemList.size()!=0) {
			noArticles.setVisibility(View.GONE);
			//Init adapter
			adapter = new ListArticleAdapter(this, mItemList);
			lvItem.setAdapter(adapter);
		} else {
			currentListCategorie= false;
			noArticles.setVisibility(View.VISIBLE);
		}
	}

	public void adapterArticlesFilterByNameAndCategorie(String name, int id_cat){
		noArticles.setVisibility(View.GONE);
		mItemList = mDBHelper.getListItemFilterByTitleAndCategorie(name,id_cat);
		//Init adapter
		adapter = new ListArticleAdapter(this, mItemList);
		//Set adapter for listview
		lvItem.setAdapter(adapter);
	}

	public void adapterCategoriesFilterByName(String name){
		noArticles.setVisibility(View.GONE);
		mCategorieList = mDBHelper.getListCtegoriesFilterByTitle(name);
		adapterCategorie = new ListCategorieAdapter(this, mCategorieList);
		lvItem.setAdapter(adapterCategorie);
	}

	private void ShareApp(){
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		String shareBody = "Hey my friend check out this app\n https://play.google.com/store/apps/details?id="+ getPackageName() +" \n";
		sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
		sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
		startActivity(Intent.createChooser(sharingIntent, "Share via"));
	}
	
	private boolean copyDatabase(Context context) {
        try {

            InputStream inputStream = context.getAssets().open(DataBaseHelper.DBNAME);
            String outFileName = DataBaseHelper.DBLOCATION + DataBaseHelper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[]buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("MainActivity","DB copied");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	
	
	@Override
    public void onBackPressed() {
    	if(!currentListCategorie){
			noArticles.setVisibility(View.GONE);
			allArticles.setBackgroundResource(R.drawable.rounded_corner);
			categories.setBackgroundResource(R.drawable.rounded_corner_dark);
			currentListCategorie = true;
			adapterCategories();
		} else {
			StartAppAd.onBackPressed(this);
			AppRater.app_launched(this);
		}
    }
   
    private void requestNewInterstitial() {
		 AdRequest adRequest = new AdRequest.Builder().build();
		 mInterstitialAd.loadAd(adRequest);
	}

    private void itemSelected() {
    	
    	lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    		    if(currentListCategorie){
					Categorie itemClicked = mCategorieList.get(position);
					id_cat = itemClicked.getId();
					adapterArticlesFilterByIdCat(itemClicked.getId());
					currentListCategorie = false;
					if (admob.mCount == admob.nbShowInterstitial) {
						if (mInterstitialAd.isLoaded())
							mInterstitialAd.show();
						admob.mCount = 0;
					}
					++admob.mCount;

				}else if(!currentListCategorie){
				    Item itemClicked = mItemList.get(position)	;
					try {
						//Class classe= Class.forName(getPackageName()+".Details");
						Intent intent = new Intent(ListViewsItems.this, Details.class);
						intent.putExtra("detail", "" + itemClicked.getText());
						intent.putExtra("title", "" + itemClicked.getTitle());
						startActivityForResult(intent, 1);
						//startActivity(intent);
						//finish();
						if (admob.mCount == admob.nbShowInterstitial) {
							if (mInterstitialAd.isLoaded())
								mInterstitialAd.show();
							admob.mCount = 0;
						}
						++admob.mCount;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
    		};
    	});
    }
    
}