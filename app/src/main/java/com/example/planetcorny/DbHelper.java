package com.example.planetcorny;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.planetcorny.target.Account;
import com.example.planetcorny.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "db_planet";
    private static final int VERSION = 2;

    // private static final String TABLE_NAME = "Students";

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // query
        String sql_itemPlanet = "CREATE TABLE " + Constants.TABLE_ACCOUNT + "" +
                "(id integer primary key AUTOINCREMENT, " +
                Constants.COL_DISPLAY_NAME + " TEXT, " +
                Constants.COL_EMAIL + " TEXT, " +
                Constants.COL_PASSWORD + " TEXT)";

        String sql_planet = "CREATE TABLE " + Constants.TABLE_PLANET + "" +
                "(id integer primary key, " +
                Constants.COL_PLANET_NAME + " TEXT, " +
                Constants.COL_PLANET_IMAGE + " INTERGER, " +
                Constants.COL_PLANET_DESC + " TEXT, " +
                Constants.COL_PLANET_DESC_DETAIL + " TEXT)";

        // execute
        sqLiteDatabase.execSQL(sql_itemPlanet);
        sqLiteDatabase.execSQL(sql_planet);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // query
        String sql_itemPlanet = "DROP TABLE IF EXISTS " + Constants.TABLE_ACCOUNT;
        String sql_planet = "DROP TABLE IF EXISTS " + Constants.TABLE_PLANET;

        // execute
        sqLiteDatabase.execSQL(sql_itemPlanet);
        sqLiteDatabase.execSQL(sql_planet);

        onCreate(sqLiteDatabase);
    }

    // TABLE ACCOUNT
    public boolean checkExistedAccount(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + Constants.TABLE_ACCOUNT +
                " WHERE " + Constants.COL_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{email});
        if (cursor.moveToFirst()) {
            return true;
        }
        return false;
    }

    public Account getAccount(String email) {
        Account itemPlanet = new Account("", "", "");
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + Constants.TABLE_ACCOUNT +
                " WHERE " + Constants.COL_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{email});
        if (cursor.moveToFirst()) {
            itemPlanet.setId(cursor.getInt(0));
            itemPlanet.setDisplayName(cursor.getString(1));
            itemPlanet.setEmail(cursor.getString(2));
            itemPlanet.setPassword(cursor.getString(3));
        }
        return itemPlanet;
    }

    public long addAccount(Account itemPlanet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.COL_DISPLAY_NAME, itemPlanet.getDisplayName());
        contentValues.put(Constants.COL_EMAIL, itemPlanet.getEmail());
        contentValues.put(Constants.COL_PASSWORD, itemPlanet.getPassword());
        return db.insert(Constants.TABLE_ACCOUNT, null, contentValues);
    }


    // TABLE PLANET
    public boolean checkExistedPlanet(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + Constants.TABLE_PLANET +
                " WHERE " + Constants.COL_PLANET_ID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{id});
        if (cursor.moveToFirst()) {
            return true;
        }
        return false;
    }

    public long addPlanet(Items itemPlanet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.COL_PLANET_ID, itemPlanet.getId());
        contentValues.put(Constants.COL_PLANET_NAME, itemPlanet.getName());
        contentValues.put(Constants.COL_PLANET_IMAGE, itemPlanet.getImage());
        contentValues.put(Constants.COL_PLANET_DESC, itemPlanet.getDesc());
        contentValues.put(Constants.COL_PLANET_DESC_DETAIL, itemPlanet.getDescDetail());
        return db.insert(Constants.TABLE_PLANET, null, contentValues);
    }

    public int deletePlanet(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Constants.TABLE_PLANET, "id=?", new String[]{id});
    }

    public Items getPlanet(String id) {
        Items itemPlanet = new Items();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + Constants.TABLE_PLANET +
                " WHERE " + Constants.COL_PLANET_ID + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{id});
        if (cursor.moveToFirst()) {
            itemPlanet.setId(cursor.getInt(0));
            itemPlanet.setName(cursor.getString(1));
            itemPlanet.setImage(cursor.getInt(2));
            itemPlanet.setDesc(cursor.getString(3));
            itemPlanet.setDescDetail(cursor.getString(4));
        }
        return itemPlanet;
    }

    public List<Items> getAll() {
        List<Items> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + Constants.TABLE_PLANET;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Items itemPlanet = new Items();
            itemPlanet.setId(cursor.getInt(0));
            itemPlanet.setName(cursor.getString(1));
            itemPlanet.setImage(cursor.getInt(2));
            itemPlanet.setDesc(cursor.getString(3));
            itemPlanet.setDescDetail(cursor.getString(4));
            list.add(itemPlanet);
        }
        return list;
    }

}
