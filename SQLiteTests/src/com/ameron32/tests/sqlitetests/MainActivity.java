package com.ameron32.tests.sqlitetests;

import java.util.List;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends Activity {
  public static final String TAG = "SQLiteTests: MainActivity";
  public static final boolean DEBUG = true;
  
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    if (savedInstanceState == null) {
      getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
    }
    
    init();
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) { return true; }
    return super.onOptionsItemSelected(item);
  }
  
  /**
   * A placeholder fragment containing a simple view.
   */
  public static class PlaceholderFragment extends Fragment {
    
    public PlaceholderFragment() {}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_main, container, false);
      return rootView;
    }
  }
  
  
  
  
  
  
  /*
   * DATABASE
   */
  void init() {
    createDatabase();
    insertContacts();
    List<Contact> contacts = readContacts();
    displayContacts(contacts);
  }
  
  DatabaseHandler db;
  
  void createDatabase() {
    debug("Creating Database");
    db  = new DatabaseHandler(this);
  }
  
  void insertContacts() {
    debug("Inserting...");
    db.addContact(new Contact("Kris", "8304211381"));
    db.addContact(new Contact("Vicky", "2107792332"));
    db.addContact(new Contact("Charles", "8302577083"));
    db.addContact(new Contact("Raquel", "8304314550"));
  }
  
  List<Contact> readContacts() {
    return db.getAllContacts();
  }
  
  void displayContacts(List<Contact> contacts) {
    for (Contact contact: contacts) {
      String log = "Id: " + contact.getId() + ", " 
          + "Name: " + contact.getName() + ", "
          + "Phone: " + contact.getPhoneNumber();
      debug(log);
    }
  }
  
  void debug(String debugText) {
    if (DEBUG) Log.d(TAG, debugText);
  }
  
}
