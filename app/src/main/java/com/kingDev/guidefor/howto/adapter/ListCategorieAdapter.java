package com.kingDev.guidefor.howto.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingDev.guidefor.howto.R;
import com.kingDev.guidefor.howto.database.DataBaseHelper;
import com.kingDev.guidefor.howto.module.Categorie;
import com.kingDev.guidefor.howto.module.Item;

import java.util.List;

public class ListCategorieAdapter extends BaseAdapter{
	private Context mContext;
    private List<Categorie> mItemList;
    Typeface type1 ,type2 ;

    public ListCategorieAdapter(Context mContext, List<Categorie> mItemList) {
        this.mContext = mContext;
        this.mItemList = mItemList;
        type1 = Typeface.createFromAsset(mContext.getAssets(),"fonts/HelveticaNeueLTStd-Lt.otf"); 
        Typeface type2 = Typeface.createFromAsset(mContext.getAssets(),"fonts/Roboto-Bold.ttf"); 
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mItemList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

    	View v  = convertView;

        if(v==null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_view2, parent, false);
        }

        //View v = View.inflate(mContext, R.layout.item_view, null);
        
    // find the item to work with
	//Categorie currentCategorie = mItemList.get(position);
	// fill the view
	ImageView img = (ImageView) v.findViewById(R.id.imageView1);
	String uri = "@drawable/"+mItemList.get(position).getIcon();  
	int imageResource = mContext.getResources().getIdentifier(uri, null, mContext.getPackageName());
	Drawable res = mContext.getResources().getDrawable(imageResource);
	img.setImageDrawable(res);
	// fill  title
	TextView txv = (TextView) v.findViewById(R.id.textView1) ;
	txv.setTypeface(type2);
	txv.setText(mItemList.get(position).getName().trim());
	// fill distance
    TextView nbArticles =  (TextView) v.findViewById(R.id.nbArticles) ;
    nbArticles.setText(new DataBaseHelper(mContext).getListItemFilterByIdCat(mItemList.get(position).getId()).size()+" Articles");

	((TextView) v.findViewById(R.id.textView2)).setTypeface(type1);
	v.setPadding(0, 5, 0, 5);
	
	return v;
    }
    
}
