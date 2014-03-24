package com.ameron32.tests.sqlitetests;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
  
  // All Static variables
  // Database Version
  private static final int    DATABASE_VERSION = 1;
  
  // Database Name
  private static final String DATABASE_NAME    = "contactsManager";
  
  // Contacts table name
  private static final String TABLE_CONTACTS   = "contacts";
  
  // Contacts Table Columns names
  private static final String KEY_ID           = "id";
  private static final String KEY_NAME         = "name";
  private static final String KEY_PH_NO        = "phone_number";
  
  public DatabaseHandler(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }
  
  @Override
  public void onCreate(SQLiteDatabase db) {
    // SQL Command Code
    // ---------------------------------------------------
    /*
     * CREATE TABLE contacts (id INTEGER PRIMARY KEY, name TEXT, phone_number
     * TEXT)
     */
    String createContactsTable = "CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
        + " TEXT," + KEY_PH_NO + " TEXT" + ")";
    db.execSQL(createContactsTable);
  }
  
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // Drop older table if existed
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
    
    // Create tables again
    onCreate(db);
  }
  
  public void addContact(Contact contact) {

    SQLiteDatabase wDB = this.getWritableDatabase();
    
    // Information wrapper
    ContentValues values = new ContentValues();
    values.put(KEY_NAME, contact.getName());
    values.put(KEY_PH_NO, contact.getPhoneNumber());
    
    // Inserting row
    String nullColumnHack = null;
    wDB.insert(TABLE_CONTACTS, nullColumnHack, values);
    wDB.close(); // Close database connection
  }
  
  public Contact getContact(int id) {
    
    SQLiteDatabase rDB = this.getReadableDatabase();
    
    // attempt to pull data from database within Cursor
    // ---------------------------------------------------
    /* 
     * query("contacts", { "id", "name", "phone_number" }, 
     * "id=?", { "0" }, 
     * null, null, null, null) 
     */ 
    Cursor cursor = rDB.query( TABLE_CONTACTS, 
        new String[] { KEY_ID, KEY_NAME, KEY_PH_NO },
        KEY_ID + "=?",
        new String[] { String.valueOf(id) },
        null, null, null, null);
    if (cursor != null) {
      cursor.moveToFirst();
    }
    
    // build a new Contact from database
    Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
        cursor.getString(1), cursor.getString(2));
    return contact;
  }
  
  public List<Contact> getAllContacts() {
    List<Contact> contactList = new ArrayList<Contact>();
    
    // Select All Query
    // ---------------------------------------------------
    // SELECT * FROM contacts
    String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;
    
    SQLiteDatabase wDB = this.getWritableDatabase();
    Cursor cursor = wDB.rawQuery(selectQuery, null);
    
    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
      do {
        Contact contact = new Contact();
        contact.setId(Integer.parseInt(cursor.getString(0)));
        contact.setName(cursor.getString(1));
        contact.setPhoneNumber(cursor.getString(2));
        contactList.add(contact);
      }
      while (cursor.moveToNext());
    }
    
    return contactList;
  }
  
  public int getContactsCount() {
    
    // Count All Query
    // ---------------------------------------------------
    // SELECT * FROM contacts
    String countQuery = "SELECT * FROM " + TABLE_CONTACTS;
    SQLiteDatabase rDB = this.getReadableDatabase();
    Cursor cursor = rDB.rawQuery(countQuery, null);
    cursor.close();
    
    return cursor.getCount();
  }
  
  public int updateContact(Contact contact) {
    SQLiteDatabase wDB = this.getWritableDatabase();
    
    ContentValues values = new ContentValues();
    values.put(KEY_NAME, contact.getName());
    values.put(KEY_PH_NO, contact.getPhoneNumber());
    
    int update = wDB.update(TABLE_CONTACTS, values, 
        KEY_ID + " = ?", new String[] { String.valueOf(contact.getId()) });
    
    return update;
  }
  
  public void deleteContact(Contact contact) {
    
    SQLiteDatabase wDB = this.getWritableDatabase();
    wDB.delete(TABLE_CONTACTS, KEY_ID + " = ?", 
        new String[] { String.valueOf(contact.getId())} );
    wDB.close();
  }
  
}
