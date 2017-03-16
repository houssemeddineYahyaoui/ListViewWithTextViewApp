package com.booy.listviewwithtextviewapp.DataHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.booy.listviewwithtextviewapp.Entites.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by booy on 15/03/2017.
 */

public class DBPersonHelper {
 /******************** if debug is set true then it will show all Logcat message ************/
public static final boolean DEBUG = true;

    /******************** Logcat TAG ************/
    public static final String LOG_TAG = "DBPersonHelper";

    /******************** Table Fields ************/
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_AGE = "age";
    public static final String KEY_NBPART = "nbParticipations";


    /******************** Database Name ************/
    public static final String DATABASE_NAME = "DB_sqllite";

    /******************** Database Version (Increase one if want to also upgrade your database) ************/
    public static final int DATABASE_VERSION = 1;// started at 1

    /** Table names */
    public static final String PERSON_TABLE = "tbl_person";

    /******************** Set all table with comma seperated like USER_TABLE,ABC_TABLE ************/
    private static final String[] ALL_TABLES = { PERSON_TABLE };

    /** Create table syntax */
    private static final String USER_CREATE = "create table tbl_person(_id integer primary key autoincrement, name text not null, age text not null,nbParticipations text not null);";



    /******************** Used to open database in syncronized way ************/
    private static DBPersonHelper.DataBaseHelper DBHelper = null;

    public DBPersonHelper() {
    }



    /******************* Initialize database *************/
    public static void init(Context context) {
        if (DBHelper == null) {
            if (DEBUG)
                Log.i(LOG_TAG, context.toString());
            DBHelper = new DBPersonHelper.DataBaseHelper(context);
        }
    }

/********************** Main Database creation INNER class ********************/
private static class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (DEBUG)
            Log.i(LOG_TAG, "new create");
        try {
            db.execSQL(USER_CREATE);


        } catch (Exception exception) {
            if (DEBUG)
                Log.i(LOG_TAG, "Exception onCreate() exception");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (DEBUG)
            Log.w(LOG_TAG, "Upgrading database from version" + oldVersion
                    + "to" + newVersion + "...");

        for (String table : ALL_TABLES) {
            db.execSQL("DROP TABLE IF EXISTS " + table);
        }
        onCreate(db);
    }

} // Inner class closed


    /********************** Open database for insert,update,delete in syncronized manner ********************/
    private static synchronized SQLiteDatabase open() throws SQLException {
        return DBHelper.getWritableDatabase();
    }


    /************************ General functions**************************/


    /*********************** Escape string for single quotes (Insert,Update)************/
    private static String sqlEscapeString(String aString) {
        String aReturn = "";

        if (null != aString) {
            //aReturn = aString.replace("'", "''");
            aReturn = DatabaseUtils.sqlEscapeString(aString);
            // Remove the enclosing single quotes ...
            aReturn = aReturn.substring(1, aReturn.length() - 1);
        }

        return aReturn;
    }
    /*********************** UnEscape string for single quotes (show data)************/
    private static String sqlUnEscapeString(String aString) {

        String aReturn = "";

        if (null != aString) {
            aReturn = aString.replace("''", "'");
        }

        return aReturn;
    }




    public static void addPersonData(Person person) {
        final SQLiteDatabase db = open();

        String age = sqlEscapeString(person.getAge() + "");
        String name=sqlEscapeString(person.getName()+"");
        String nbPart=sqlEscapeString(person.getNbParticipations()+"");

        ContentValues cVal = new ContentValues();

        cVal.put(KEY_NAME, name);
        cVal.put(KEY_AGE, age);
        cVal.put(KEY_NBPART, nbPart);

        db.insert(PERSON_TABLE, null, cVal);
        db.close(); // Closing database connection
    }


    public static Person getPerspnData(int id) {
        final SQLiteDatabase db = open();

        Cursor cursor = db.query(PERSON_TABLE, new String[]{
                        KEY_ID, KEY_NAME, KEY_AGE},
                KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Person data = new Person(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),Integer.parseInt(cursor.getString(2)),Integer.parseInt(cursor.getString(3)));


        return data;
    }



    // Getting All Person
    public static List<Person> getAllPersonData() {
        List<Person> personList = new ArrayList<Person>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + PERSON_TABLE;

        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Person data = new Person();
                data.setId(Integer.parseInt(cursor.getString(0)));
                data.setName(cursor.getString(1));
                data.setAge(Integer.parseInt(cursor.getString(2)));
                data.setNbParticipations(Integer.parseInt(cursor.getString(3)));
                personList.add(data);
            } while (cursor.moveToNext());
        }

        // return list
        return personList;
    }



    public static void deleteAll()
    {
        SQLiteDatabase db = open();
        db.execSQL("delete from " + PERSON_TABLE);
        db.close();
    }

    public static void deletePersonData(Person data) {
        final SQLiteDatabase db = open();
        db.delete(PERSON_TABLE, KEY_ID + " = ?",
                new String[]{String.valueOf(data.getId())});
        db.close();
    }



    public static int getPersonDataCount() {
        int v=0;
        String countQuery = "SELECT  * FROM " + PERSON_TABLE;
        final SQLiteDatabase db = open();
        Cursor cursor = db.rawQuery(countQuery, null);
        v=cursor.getCount();
        cursor.close();
        return v;
    }


    public static int updatePersonData(Person data) {
        final SQLiteDatabase db = open();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, data.getName());
        values.put(KEY_AGE, data.getAge());
        values.put(KEY_NBPART, data.getNbParticipations());


        // updating row
        return db.update(PERSON_TABLE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(data.getId()) });
    }



}

