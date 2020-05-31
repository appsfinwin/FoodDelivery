package com.finwin.brahmagiri.fooddelivery.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.finwin.brahmagiri.fooddelivery.Responses.ProductEntryModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Manager";
    private static final String TABLE_CONTACTS = "products";
    private static final String KEY_ID = "id";
    private static final String KEY_PID = "product_id";
    private static final String KEY_PNAME = "product_name";

    private static final String KEY_PRICE = "price";
    private static final String KEY_Q = "quantity";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PID + " TEXT,"
                + KEY_PNAME + " TEXT,"
                + KEY_PRICE + " TEXT,"
                + KEY_Q + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    public void addContact(ProductEntryModel contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PID, contact.getId());
        values.put(KEY_PNAME, contact.getProductname());
        values.put(KEY_PRICE, contact.getPrice()); // Contact Name
        values.put(KEY_Q, contact.getQuantity()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public List<ProductEntryModel> getAllContacts() {
        List<ProductEntryModel> contactList = new ArrayList<ProductEntryModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ProductEntryModel contact = new ProductEntryModel();
                contact.setDbid(Integer.parseInt(cursor.getString(0)));
                contact.setId(Integer.parseInt(cursor.getString(1)));
                contact.setProductname(cursor.getString(2));
                contact.setPrice(Double.valueOf(cursor.getString(3)));
                contact.setQuantity(Integer.parseInt(cursor.getString(4)));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public void deleteEntry(int Dbid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_PID + " = ?",
                new String[]{String.valueOf(Dbid)});
        db.close();
    }

    public void removeAll() {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(TABLE_CONTACTS, null, null);
        // db.delete(DatabaseHelper.TAB_USERS_GROUP, null, null);
    }

    public boolean rowIdExists(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select 1 from " + TABLE_CONTACTS
                + " where product_id=?", new String[]{"" + id});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }

    public int updateContact(int quantity, int productid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_Q, quantity);
        /*  values.put(KEY_PH_NO, contact.getPhoneNumber());*/

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_PID + " = ?",
                new String[]{String.valueOf(productid)});
    }
  public   String  getquantity(int id) {
      String quant = null;
      SQLiteDatabase db = this.getWritableDatabase();
      db.execSQL("SELECT quantity FROM products WHERE product_id='11'");
        // return contact
        return quant;
    }
    public String getFromDb(String pid){
        String selection = "0";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query("products", new String[] {"quantity"}, pid, null, null, null, null);
        if(c != null && c.moveToFirst()){

            selection = c.getString(c.getColumnIndex("quantity"));

            c.close();
            db.close();
            Log.d("FootballApp", "" + "=" + selection);
        }
        return selection;
    }
}
