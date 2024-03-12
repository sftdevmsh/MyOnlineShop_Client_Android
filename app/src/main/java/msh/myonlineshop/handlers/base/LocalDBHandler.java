package msh.myonlineshop.handlers.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class LocalDBHandler<T> extends SQLiteOpenHelper {

    public final static int DatabaseVersion = 1;
    public final static String DatabaseName = "MyOnlineShop";

    public LocalDBHandler(Context context)
    {
        super(context, DatabaseName, null, DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getCreateQuery());
//        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(getDropQuery());
        onCreate(db);
        db.close();
    }

    public void addData(ContentValues values)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(getTableName(), null , values);
        db.close();
    }

    public void checkAndCreateTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
        db.close();
    }


    public abstract String getTableName();
    public abstract String getCreateQuery();
    public abstract String getDropQuery();
}
