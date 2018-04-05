package com.kingDev.guidefor.howto.database;

import java.util.ArrayList;
import java.util.List;

import com.kingDev.guidefor.howto.module.Categorie;
import com.kingDev.guidefor.howto.module.Item;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "db.sqlite";
    public static String DBLOCATION ;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DataBaseHelper(Context context) {
        super(context, DBNAME, null, 1);
        this.mContext = context;
    }
    
    public static void setmDatabase(Context context){
    	DBLOCATION = "/data/data/"+context.getPackageName()+"/databases/";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if(mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if(mDatabase!=null) {
            mDatabase.close();
        }
    }

    public List<Item> getListItem() {
    	Item item = null;
        List<Item> itemList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM items", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        	item = new Item(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        	itemList.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return itemList;
    }

    public List<Item> getListItemFilterByIdCat(int id_cat) {
        Item item = null;
        List<Item> itemList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM items where id_cat="+id_cat, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            item = new Item(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            itemList.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return itemList;
    }

    public List<Item> getListItemFilterByTitle(String title) {
        Item item = null;
        List<Item> itemList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM items where title like '%"+title+"%'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            item = new Item(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            itemList.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return itemList;
    }

    public List<Item> getListItemFilterByTitleAndCategorie(String title, int id_cat) {
        Item item = null;
        List<Item> itemList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM items where title like '%"+title+"%' and id_cat="+id_cat, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            item = new Item(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            itemList.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return itemList;
    }

    public List<Categorie> getListCtegories(){
        Categorie categorie = null;
        List<Categorie> categorieList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM categories", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            categorie = new Categorie(cursor.getInt(0), cursor.getString(1), cursor.getString(2) );
            categorieList.add(categorie);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return categorieList;
    }

    public List<Categorie> getListCtegoriesFilterByTitle(String name) {
        Categorie categorie = null;
        List<Categorie> categorieList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM categories where name like '%"+name+"%'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            categorie = new Categorie(cursor.getInt(0), cursor.getString(1), cursor.getString(2) );
            categorieList.add(categorie);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return categorieList;
    }

}