package com.example.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String Database_Name = "PetMates.db";
    private static final String Table1 = "user";
    private static final String Table2 = "user_info";
    private static final String Table3 = "question";
    private static final String Table4 = "suggestion";
    private static final String Table5 = "report";
    private static final String Table6 = "block";
    private static final String Table7 = "friendship";
    private static final String Table8 = "friendrequest";
    private static final String Table9 = "message";
    private static  final String Table10 = "pet_news";


    private static final int version = 1;

    public DatabaseHelper(Context context) {
        super(context, Database_Name, null, version);
    }

    @Override
    //create database
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Table1 + " (Email varchar(50) PRIMARY KEY, Password varchar(50), Phone varchar(20))");
        db.execSQL("CREATE TABLE " + Table2 + " (Email TEXT PRIMARY KEY, Name TEXT, Bio TEXT,Pet_type TEXT, Pet_Breed TEXT, Pet_gender TEXT, Zip TEXT," +
                "P_Pet_type TEXT, P_Pet_Breed TEXT, P_Pet_gender TEXT, P_Other TEXT, Image blob)");
        db.execSQL("CREATE TABLE " + Table3 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, questionTitle TEXT, questionContent TEXT, answer TEXT)");
        db.execSQL("CREATE TABLE " + Table4 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, suggestionTitle TEXT, suggestionContext TEXT)");
        db.execSQL("CREATE TABLE " + Table5 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, reportedEmail TEXT, reportedReason TEXT, who TEXT)");
        db.execSQL("CREATE TABLE " + Table6 + " (Email TEXT PRIMARY KEY, isBlock TEXT,reason TEXT)");
        db.execSQL("CREATE TABLE " + Table7 + " (ownerEmail TEXT, friendEmail TEXT, PRIMARY KEY(ownerEmail,friendEmail))");
        db.execSQL("CREATE TABLE " + Table8 + " (senderEmail TEXT, receiverEmail TEXT, PRIMARY KEY(senderEmail,receiverEmail))");
        db.execSQL("CREATE TABLE " + Table9 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,senderEmail TEXT, receiverEmail TEXT, message TEXT)");
        db.execSQL("CREATE TABLE " + Table10 + " (Title TEXT PRIMARY KEY, Description TEXT, Image blob)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table1);
        db.execSQL("DROP TABLE IF EXISTS " + Table2);
        db.execSQL("DROP TABLE IF EXISTS " + Table3);
        db.execSQL("DROP TABLE IF EXISTS " + Table4);
        db.execSQL("DROP TABLE IF EXISTS " + Table5);
        db.execSQL("DROP TABLE IF EXISTS " + Table6);
        db.execSQL("DROP TABLE IF EXISTS " + Table7);
        db.execSQL("DROP TABLE IF EXISTS " + Table8);
        db.execSQL("DROP TABLE IF EXISTS " + Table9);
        db.execSQL("DROP TABLE IF EXISTS " + Table10);
        onCreate(db);
    }
    ///////////////////////////////////////////////////////////////////////
    ///////////////////////////friends and message/////////////////////////
    ////////////////////////////////////////////////////////////////////////
    //send friend request
    public boolean send_Request(String sender, String receiver){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues request = new ContentValues();
        request.put("senderEmail",sender);
        request.put("receiverEmail",receiver);
        long ins = db.insert(Table8, null,request);
        if (ins == -1) return false;
        else return true;
    }
    //get friend request(get 1)
    public Cursor get_Friendrequest(String useremail) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT senderEmail FROM " + Table8+ " WHERE receiverEmail=?",
                new String[]{String.valueOf(useremail)});
        return cursor;
    }
    //add friend
    public boolean add_friend(String owner, String friend){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues request = new ContentValues();
        request.put("ownerEmail",owner);
        request.put("friendEmail",friend);
        long ins = db.insert(Table7, null,request);
        if (ins == -1) return false;
        else return true;
    }
    //get friend list
    public Cursor get_Friendlist(String useremail) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT friendEmail FROM " + Table7+ " WHERE ownerEmail=?",
                new String[]{useremail});
        return cursor;
    }
    //send message
    public boolean send_message(String owner, String friend, String message){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues request = new ContentValues();
        request.put("senderEmail",owner);
        request.put("receiverEmail",friend);
        request.put("message",message);
        long ins = db.insert(Table9, null,request);
        if (ins == -1) return false;
        else return true;
    }
    //get message
    public Cursor get_message(String senderEmail, String receiverEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT message FROM " + Table9+
                " WHERE senderEmail=? and receiverEmail=? ORDER BY ID ASC ",new String[]{senderEmail,receiverEmail});
        return cursor;
    }

    ///////////////////////////////////////////////////////////////////////
    /////////////////////registration and profile//////////////////////////
    //////////////////////////////////////////////////////////////////////

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
    public Boolean insertImage(String Email, String Name, String Bio, String Pet_type,
                               String Pet_breed, String Pet_gender, String Zip, String P_Pet_type,
                               String P_Pet_breed, String P_Pet_gender, String P_Other, String ImagePath ){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            FileInputStream fs = new FileInputStream(ImagePath);
            byte[] imgbyte = new byte[fs.available()];
            fs.read(imgbyte);
            ContentValues contentValues = new ContentValues();
            contentValues.put("Email",Email);
            contentValues.put("Name", Name);
            contentValues.put("Bio", Bio);
            contentValues.put("Pet_type", Pet_type);
            contentValues.put("Pet_Breed", Pet_breed);
            contentValues.put("Pet_gender", Pet_gender);
            contentValues.put("Zip", Zip);
            contentValues.put("P_Pet_type", P_Pet_type);
            contentValues.put("P_Pet_breed", P_Pet_breed);
            contentValues.put("P_Pet_gender", P_Pet_gender);
            contentValues.put("P_Other", P_Other);
            contentValues.put("Image", imgbyte);
            db.insert(Table2,null,contentValues);
            fs.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////Forum functions////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////

    //inserting data for the news
    public boolean insert_news(String Title, String Description, String Imagepath){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            FileInputStream fs = new FileInputStream(Imagepath);
            byte[] imgbyte = new byte[fs.available()];
            fs.read(imgbyte);
            ContentValues contentValues = new ContentValues();
            contentValues.put("Title", Title);
            contentValues.put("Description", Description);
            contentValues.put("Image", imgbyte);
            db.insert(Table10, null, contentValues);
            fs.close();
            return true;
        }catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cursor news_data(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table10, null);
        return cursor;
    }

    public Bitmap news_image(){
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap bt = null;
        Cursor cursor = db.rawQuery("SELECT * from "+Table10+"", null);
        if(cursor.moveToNext()){
            byte[] Image = cursor.getBlob(2);
            bt = BitmapFactory.decodeByteArray(Image,0,Image.length);
        }
        return bt;
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////check account or profile exist or not///////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    //checking if email exist;
    public Boolean email_Unique(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table1 + " WHERE Email = ?", new String[]{email});
        if (cursor.getCount() > 0) return false;
        else return true;
    }

    //checking the email and password match or not
    public Boolean emailpassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table1 + " WHERE Email=? AND Password=?", new String[]{email, password});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////update user database////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    //update all user account information
    public void update_user(String Email, String Password, String Phone, String OldEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("Email", Email);
        user.put("Password", Password);
        user.put("Phone", Phone);
        db.update(Table1, user, "Email=?", new String[]{String.valueOf(OldEmail)});
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////update user account individually///////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    public void update_user_Email(String Email, String OldEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("Email", Email);
        db.update(Table1, user, "Email=?", new String[]{String.valueOf(OldEmail)});
    }
    //update user's account password
    public void update_user_Password(String Password, String OldEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("Password", Password);
        db.update(Table1, user, "Email=?", new String[]{String.valueOf(OldEmail)});
    }
    //update user's account phone
    public void update_user_Phone(String Phone, String OldEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("Phone", Phone);
        db.update(Table1, user, "Email=?", new String[]{String.valueOf(OldEmail)});
    }

    //after update primary key email,we have to update other table's primary key at same time
    public void update_info_email(String Email, String OldEmail) {
        if (chkprofile_exist(Email)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues user = new ContentValues();
            user.put("Email", Email);
            db.update(Table2, user, "Email=?", new String[]{String.valueOf(OldEmail)});
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////update user profile individually///////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    public Boolean update_info_image(String Email,String ImagePath ){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            FileInputStream fs = new FileInputStream(ImagePath);
            byte[] imgbyte = new byte[fs.available()];
            fs.read(imgbyte);
            ContentValues contentValues = new ContentValues();
            contentValues.put("Email",Email);
            contentValues.put("Image", imgbyte);
            db.update(Table2, contentValues, "Email=?", new String[]{String.valueOf(Email)});
            fs.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void update_P_Other(String P_Other,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("P_Other", P_Other);
        db.update(Table2, user, "Email=?", new String[]{String.valueOf(email)});
    }

    public void update_P_Pet_gender(String P_Pet_gender,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("P_Pet_gender", P_Pet_gender);
        db.update(Table2, user, "Email=?", new String[]{String.valueOf(email)});
    }

    public void update_P_Pet_Breed(String P_Pet_Breed,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("P_Pet_Breed", P_Pet_Breed);
        db.update(Table2, user, "Email=?", new String[]{String.valueOf(email)});
    }

    public void update_P_Pet_type(String P_Pet_type,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("P_Pet_type", P_Pet_type);
        db.update(Table2, user, "Email=?", new String[]{String.valueOf(email)});
    }

    public void update_info_Zip(String Zip,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("Zip", Zip);
        db.update(Table2, user, "Email=?", new String[]{String.valueOf(email)});
    }

    public void update_info_Pet_gender(String Pet_gender,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("Pet_gender", Pet_gender);
        db.update(Table2, user, "Email=?", new String[]{String.valueOf(email)});
    }

    public void update_info_Pet_breed(String Pet_breed,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("Pet_Breed", Pet_breed);
        db.update(Table2, user, "Email=?", new String[]{String.valueOf(email)});
    }


    public void update_info_Pet_type(String Pet_type,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("Pet_type", Pet_type);
        db.update(Table2, user, "Email=?", new String[]{String.valueOf(email)});
    }

    public void update_info_Name(String name,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("Name", name);
        db.update(Table2, user, "Email=?", new String[]{String.valueOf(email)});
    }

    public void update_info_Bio(String Bio,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("Bio", Bio);
        db.update(Table2, user, "Email=?", new String[]{String.valueOf(email)});
    }
    //////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////application support//////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //insert data to question
    public boolean insert_question(String title, String content) {
        String a = "0";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues question = new ContentValues();
        question.put("questionTitle", title);
        question.put("questionContent", content);
        question.put("answer",a);
        long ins = db.insert(Table3, null, question);
        if (ins == -1) return false;
        else return true;
    }

    //insert data to question
    public boolean insert_suggestion(String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues suggest = new ContentValues();
        suggest.put("suggestionTitle", title);
        suggest.put("suggestionContext", content);
        long ins = db.insert(Table4, null, suggest);
        if (ins == -1) return false;
        else return true;
    }
    //search question
    //return array, [0]= id, [1]= title, [2] = content,[3] = answer
    public Cursor search_question(String key) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table3+" WHERE questionTitle LIKE ? OR questionTitle LIKE ?" +
                        " OR questionTitle LIKE ? OR questionContent LIKE ?"
                ,new String[]{key,"%"+key,key+"%","%"+key+"%"});
        return cursor;
    }

    //search key word by answered question title
    public Cursor search_answered_question(String key){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT questionTitle, questionContent FROM " + Table3+
                " WHERE answer != ? ORDER BY RANDOM()LIMIT THREE",new String[]{key});
        return cursor;
    }
    ///////////////////////////////////////////////////////////////////////////
    ////////////////////////for admin view/////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    //admin can view all suggest
    public Cursor getAll_suggest() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table4+
                " ORDER BY ID ASC",null);
        return cursor;
    }
    //get all unanswered question
    public Cursor getAll_question() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID, questionTitle, questionContent FROM " + Table3+
                " WHERE answer = ? ORDER BY ID ASC",new String[]{"0"});
        return cursor;
    }
    //admin answer question by id
    public void  answer_question(int ID, String answer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues user = new ContentValues();
        user.put("answer", answer);
        db.update(Table3, user, "ID=?", new String[]{String.valueOf(ID)});

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////report///////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////
    //report some one
    public boolean insert_report(String targetEmail, String reason, String who) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues question = new ContentValues();
        question.put("reportedEmail", targetEmail);
        question.put("reportedReason", reason);
        question.put("who",who);
        long ins = db.insert(Table5, null, question);
        if (ins == -1) return false;
        else return true;
    }
    //read reported information
    public Cursor getReported_information() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID, reportedEmail, reportedReason, who FROM " + Table5+
                " ORDER BY ID ASC",null);
        return cursor;
    }

    // block user
    public boolean insert_block(String Email, String reason) {
        String i = "YES";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues question = new ContentValues();
        question.put("Email", Email);
        question.put("isBlock", i);
        question.put("reason", reason);
        long ins = db.insert(Table6, null, question);
        if (ins == -1) return false;
        else return true;
    }
    //check the user been block or not
    public Boolean isBlock(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table6 + " WHERE Email = ?", new String[]{email});
        if (cursor.getCount() > 0) return false;
        else return true;
    }

    //tell the user the reason he been reported
    public Cursor getReason(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table6+
                " WHERE Email =?",new String[]{email});
        return cursor;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    //check the user profile exist or not
    public Boolean chkprofile_exist(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table2 + " WHERE Email = ?", new String[]{email});
        if (cursor.getCount() > 0) return false;
        else return true;
    }


   //get all data from a table
    public Cursor getAll_Data() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table1,null);
        return cursor;
    }
    //get entire row of account data for current user
    public Cursor getAll_User(String Email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table1+" WHERE Email=?",new String[]{Email});
        return cursor;
    }
    //get entire row of profile data for current user
    public Cursor getAll_User_info(String Email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table2+" WHERE Email=?",new String[]{Email});
        return cursor;
    }

    //get user name
    public Cursor getName(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table2 + " WHERE Email = ?", new String[]{email});
        return cursor;
    }


    ///////////////////////////////////////////////////////
    ///////////////////for pairing/////////////////////////
    ///////////////////////////////////////////////////////
    //randomly pick 1 mate by preferences
    public Cursor pair_pre(String email, String P_Pet_type, String P_Pet_breed, String P_Pet_gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT Email, Name, Bio, Pet_type, Pet_breed, Pet_gender FROM " + Table2 +
                " WHERE Pet_type =? AND Pet_breed=? AND Pet_gender=? AND Email !=? COLLATE NOCASE ORDER BY RANDOM() LIMIT 1", new String[]{P_Pet_type, P_Pet_breed, P_Pet_gender, email});
        return cursor;
    }

    //randomly pick 1 mate by location
    public Cursor pair_location(String email, String zip) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT Email, Name, Bio, Pet_type, Pet_breed, Pet_gender FROM " + Table2 +
                " WHERE zip = (SELECT Zip FROM " + Table2 + " WHERE Email =? AND Email !=? ORDER BY RANDOM() LIMIT 1)", new String[]{zip, email});
        return cursor;
    }

    //Retrieve all database from user_info
    public Cursor allData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table2 + ";", null);
        return cursor;
    }

    //Retrieve user preferences from user_info
    public Cursor getPreferences(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT P_Pet_type, P_Pet_breed, P_Pet_gender FROM " + Table2 + " WHERE Email =?;", new String[]{email});
        return cursor;
    }

    //Retrieve user zip from user_info
    public Cursor getZip(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT Zip FROM " + Table2 + " WHERE Email =?;", new String[]{email});
        return cursor;
    }

    //if couldn't find any pair mate, give a random mate
    public Cursor getRandom(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT Email, Name, Bio, Pet_type, Pet_breed, Pet_gender FROM " + Table2 +
                " WHERE Email !=? ORDER BY RANDOM() LIMIT 1", new String[]{email});
        return cursor;
    }

    //////////////////////////////////////////////////////////////
    //////////////////////get image////////////////////////////
    //////////////////////////////////////////////////////////////

    //retrieve image from database where email = given
    public Bitmap getimage(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap bt = null;
        Cursor cursor = db.rawQuery("SELECT * from "+Table2+" WHERE email=?",new String[]{email});
        if(cursor.moveToNext()){
            byte[] Image = cursor.getBlob(11);
            bt = BitmapFactory.decodeByteArray(Image,0,Image.length);
        }
        return bt;
    }
    //////////////////////////////////////////////////////////////////////////////////////////

}
