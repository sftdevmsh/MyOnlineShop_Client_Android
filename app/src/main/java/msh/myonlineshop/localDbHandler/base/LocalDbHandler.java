package msh.myonlineshop.localDbHandler.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class LocalDbHandler<T> extends SQLiteOpenHelper {

    public final static int DatabaseVersion = 1;
    public final static String DatabaseName = "MyOnlineShop";

    public LocalDbHandler(Context context)
    {
        super(context, DatabaseName, null, DatabaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getCreateTableQuery());
        //**etx
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(getDropTableQuery());
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

    protected abstract String getTableName();
    protected abstract String getCreateTableQuery();
    protected abstract String getDropTableQuery();
}
