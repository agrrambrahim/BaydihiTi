package com.kingDev.guidefor.howto;

import java.util.List;

import com.codemybrainsout.ratingdialog.RatingDialog;
import com.kingDev.guidefor.howto.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;


public class AppRater {
    private final static String APP_TITLE = "APP";// App Name
    ListViewsItems LV= new ListViewsItems();
    private static String APP_PNAME ;
    		//AppRater.class.getClass().getPackage().getName();// Package Name

    private final static int DAYS_UNTIL_PROMPT = 1;// Min number of days
    private final static int LAUNCHES_UNTIL_PROMPT = 1;// Min number of launches
    
    public static void setAPP_PNAME(Context context){
    	APP_PNAME = context.getPackageName();
    }

    public static void app_launched(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
        if (prefs.getBoolean("dontshowagain", false)) { return ; }

        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }

        // Wait at least n days before opening
        showRateDialog(mContext, editor);
        
        /*
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch + 
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
              //  showRateDialog(mContext, editor);
            }
        }*/
        
        editor.commit();
    }   

    public String getPackageName(Context context) {
        return context.getPackageName();
    }
    public static void showSmartRateDialog(final Context mcontext){
        
        final RatingDialog ratingDialog = new RatingDialog.Builder(this)
                .threshold(4)
                .onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
                    @Override
                    public void onFormSubmitted(String feedback) {

                    }
                }).build();

        ratingDialog.show();
    }

    public static void rateLink(Context mContext){
    	try {
    		mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+APP_PNAME)));
        } catch (android.content.ActivityNotFoundException anfe) {
        	mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+APP_PNAME)));
        }
    }
    public static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor) {
    	
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setIcon(R.drawable.star);        
        builder.setTitle("Rate " + APP_TITLE);    
        builder.setMessage(mContext.getResources().getString(R.string.description_rate));

        //Button One : Yes
        builder.setPositiveButton(mContext.getResources().getString(R.string.exit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /*Toast.makeText(mContext, "Yes button Clicked!", Toast.LENGTH_LONG).show();*/
                System.exit(1);

            }
        });


        //Button Two : No
        builder.setNegativeButton(mContext.getResources().getString(R.string.improvement), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                      	
            	Intent emailIntent = new Intent(Intent.ACTION_SEND);
            	emailIntent.putExtra(Intent.EXTRA_EMAIL, SettingsApp.contactMail);
            	emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Improvement");
                emailIntent.setType("text/plain");
                //emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Messg content");
                final PackageManager pm = mContext.getPackageManager();
                final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
                ResolveInfo best = null;
                for(final ResolveInfo info : matches)
                    if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                        best = info;
                if (best != null)
                    emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                mContext.startActivity(emailIntent);
            	
            }
        });


        //Button Three : Neutral
        builder.setNeutralButton(mContext.getResources().getString(R.string.rate), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(mContext, "Neutral button Clicked!", Toast.LENGTH_LONG).show();
                //dialog.cancel();
            	try {
            		mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+APP_PNAME)));
                } catch (android.content.ActivityNotFoundException anfe) {
                	mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+APP_PNAME)));
                }
            	
            }
        });


        AlertDialog diag = builder.create();
        diag.show();
              
    }
    
}