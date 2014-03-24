package com.ameron32.tests.sqlitetests;


public class Contact {
  private int mId;
  private String mName;
  private String mPhoneNumber;
  
  public Contact() { super(); }

  public Contact( int mId, String mName, String mPhoneNumber ) {
    super();
    this.mId = mId;
    this.mName = mName;
    this.mPhoneNumber = mPhoneNumber;
  }
  
  public Contact( String mName, String mPhoneNumber ) {
    super();
    this.mName = mName;
    this.mPhoneNumber = mPhoneNumber;
  }

  
  public int getId() {
    return mId;
  }

  
  public void setId(int mId) {
    this.mId = mId;
  }

  
  public String getName() {
    return mName;
  }

  
  public void setName(String mName) {
    this.mName = mName;
  }

  
  public String getPhoneNumber() {
    return mPhoneNumber;
  }

  
  public void setPhoneNumber(String mPhoneNumber) {
    this.mPhoneNumber = mPhoneNumber;
  }
  
  
}
