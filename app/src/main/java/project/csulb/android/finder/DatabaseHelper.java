package project.csulb.android.finder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Shishir on 4/25/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String ID_COLUMN = "_id";
    public static final String NAME_COLUMN = "name";
    public static final String Address_Column = "address";
    public static final String Type_column = "type";
    public static final String Table_Name = "Recent";
    public static final String DB_NAME = "Searches";
    public static final int DATABASE_VERSION = 1;
    public static final String Database_Create = "Create table "+Table_Name+"( "+ ID_COLUMN + " integer primary key autoincrement, "
            +NAME_COLUMN+" text NOT NULL, "+Address_Column+" text NOT NULL, "+Type_column+" text NOT NULL)";

    private static DatabaseHelper helper;

    private DatabaseHelper(Context context){
        super(context,DB_NAME,null,DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context){
        if(helper == null){
            helper =  new DatabaseHelper(context.getApplicationContext());
        }
        return helper;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Database_Create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
        onCreate(db);
    }
}
