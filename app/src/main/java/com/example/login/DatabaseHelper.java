package com.example.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String Database_Name = "PetMates.db";
    private static final String Table1 = "user";
    private static final String Table2 = "user_info";

    private static final int version = 1;
    private String id_value;


    public DatabaseHelper(Context context) {
        super(context, Database_Name, null, version);
    }

    @Override
    //create database
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Table1 + " (Email varchar(50) PRIMARY KEY, Password varchar(50), Phone varchar(20))");
        db.execSQL("CREATE TABLE " + Table2 + " (Email TEXT PRIMARY KEY, Name TEXT, Bio TEXT,Pet_type TEXT, Pet_Breed TEXT, Pet_gender TEXT, Zip TEXT," +
                "P_Pet_type TEXT, P_Pet_Breed TEXT, P_Pet_gender TEXT, P_Other TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table1);
        db.execSQL("DROP TABLE IF EXISTS " + Table2);
        onCreate(db);
    }

    //insert data to user
    public boolean insert_user(String Email, String Password, String Phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();

        user.put("email", Email);
        user.put("password", Password);
        user.put("phone", Phone);
        long ins = db.insert(Table1, null, user);
        if (ins == -1) return false;
        else return true;
    }

    //insert data to user_info
    public boolean insert_user_info(String Email, String Name, String Bio, String Pet_type,
                                    String Pet_breed, String Pet_gender, String Zip, String P_Pet_type,
                                    String P_Pet_breed, String P_Pet_gender, String P_Other) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user_info = new ContentValues();

        user_info.put("Email", Email);
        user_info.put("Name", Name);
        user_info.put("Bio", Bio);
        user_info.put("Pet_type", Pet_type);
        user_info.put("Pet_Breed", Pet_breed);
        user_info.put("Pet_gender", Pet_gender);
        user_info.put("Zip", Zip);
        user_info.put("P_Pet_type", P_Pet_type);
        user_info.put("P_Pet_breed", P_Pet_breed);
        user_info.put("P_Pet_gender", P_Pet_gender);
        user_info.put("P_Other", P_Other);

        long ins = db.insert(Table2, null, user_info);
        if (ins == -1) return false;
        else return true;
    }


    /*public boolean insert_user_preference(String Email,String Pet_type, String Pet_breed, String Pet_gender, String Other) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues ser_preferences = new ContentValues();

        ser_preferences.put("Email",Email);
        ser_preferences.put("Pet_type",Pet_type);
        ser_preferences.put("Pet_Breed",Pet_breed);
        ser_preferences.put("Pet_gender",Pet_gender);
        ser_preferences.put("Other",Other);

        long ins = db.insert(Table3,null, ser_preferences);
        if (ins==-1) return false;
        else return true;
        */
    //checking if email exist;
    public  Boolean email_Unique(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Table1+ " WHERE Email = ?",new String[]{email});
        if(cursor.getCount()>0) return false;
        else return true;
    }
    //checking the email and password match or not
    public  Boolean emailpassword(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Table1+" WHERE Email=? AND Password=?",new String[]{email,password});
        if(cursor.getCount()>0)return true;
        else return false;
    }
//////////////////////////////////////////////////////////////////////////////////////////////
    //for edit account information
    public void update_user(String Email,String Password, String Phone, String OldEmail ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("Email",Email);
        user.put("Password",Password);
        user.put("Phone",Phone);
        db.update(Table1,user,"Email=?",new String[]{String.valueOf(OldEmail)});
    }
    //after update primary key email,we have to update other table's data
    public void update_info_email(String Email,String OldEmail ) {
        if (chkprofile_exist(Email) == true) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues user = new ContentValues();
            user.put("Email", Email);
            db.update(Table2, user, "Email=?", new String[]{String.valueOf(OldEmail)});
        }
    }
    /*public void update_preferences_email(String Email,String OldEmail ){
        if(chkPreferences_exist(Email) == true) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues user = new ContentValues();
            user.put("Email", Email);
            db.update(Table3, user, "Email=?", new String[]{String.valueOf(OldEmail)});
        }
    }*/
    ////////////////////////////////////////////////////////////////////////////////////////

    //check the user profile exist or not
    public  Boolean chkprofile_exist(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Table2+ " WHERE Email = ?",new String[]{email});
        if(cursor.getCount()>0) return false;
        else return true;
    }
    /*public  Boolean chkPreferences_exist(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Table3+ " WHERE Email = ?",new String[]{email});
        if(cursor.getCount()>0) return false;
        else return true;
    }*/

    public Cursor getAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Table1,null);
        return cursor;
    }
    //get user name
    public Cursor getName(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Table2+ " WHERE Email = ?",new String[]{email});
        return cursor;
    }


///////////////////////////////////////////////////////
    //for pairing
    //randomly pick 1 mate by preferences
    public Cursor pair_pre(String email, String P_Pet_type, String P_Pet_breed, String P_Pet_gender){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT Email, Name, Bio, Pet_type, Pet_breed, Pet_gender FROM "+Table2+
                " WHERE Pet_type =? AND Pet_breed=? AND Pet_gender=? AND Email !=? ORDER BY RANDOM() LIMIT 1",new String[]{P_Pet_type,P_Pet_breed,P_Pet_gender,email});
        return cursor;
    }

    //randomly pick 1 mate by location
    public Cursor pair_location(String email, String zip){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT Email, Name, Bio, Pet_type, Pet_breed, Pet_gender FROM "+Table2+
                " WHERE zip = (SELECT Zip FROM "+Table2+" WHERE Email =? AND Email !=? ORDER BY RANDOM() LIMIT 1)",new String[]{zip,email});
        return cursor;
    }

    //Retrieve all database from user_info
    public Cursor allData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+Table2+";",null);
        return cursor;
    }

    //Retrieve user preferences from user_info
    public Cursor getPreferences(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT P_Pet_type, P_Pet_breed, P_Pet_gender FROM "+Table2+" WHERE Email =?;",new String[]{email});
        return cursor;
    }

    //Retrieve user zip from user_info
    public Cursor getZip(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT Zip FROM "+Table2+" WHERE Email =?;",new String[]{email});
        return cursor;
    }
    //if couldn't find any pair mate, give a random mate
    public Cursor getRandom(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT Email, Name, Bio, Pet_type, Pet_breed, Pet_gender FROM " + Table2 +
                " WHERE Email !=? ORDER BY RANDOM() LIMIT 1", new String[]{email});
        return cursor;
    }
}
