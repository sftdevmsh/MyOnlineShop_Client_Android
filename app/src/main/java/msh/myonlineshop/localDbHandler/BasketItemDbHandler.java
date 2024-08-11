package msh.myonlineshop.localDbHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import msh.myonlineshop.ProductDetailsActivity;
import msh.myonlineshop.localDbHandler.base.LocalDbHandler;
import msh.myonlineshop.models.BasketItem;

public class BasketItemDbHandler extends LocalDbHandler<ProductDetailsActivity> {

    public BasketItemDbHandler(Context context) {
        super(context);
    }


    @Override
    protected String getTableName() {
        return "CARDS";
    }


    @Override
    protected String getCreateTableQuery() {
        return "CREATE TABLE IF NOT EXISTS " + getTableName() +
                "(" + BasketItem.key_id + " INTEGER PRIMARY KEY,"
                + BasketItem.key_product + " INTEGER,"
                + BasketItem.key_size + " INTEGER,"
                + BasketItem.key_color + " INTEGER,"
                + BasketItem.key_quantity + " INTEGER,"
                + BasketItem.key_color_title + " TEXT,"
                + BasketItem.key_color_value + " TEXT,"
                + BasketItem.key_product_image + " TEXT,"
                + BasketItem.key_product_title + " TEXT,"
                + BasketItem.key_product_price + " INTEGER,"
                + BasketItem.key_size_title + " TEXT)";
    }

    @Override
    protected String getDropTableQuery() {
        return "DROP TABLE IF EXISTS " + getTableName();
    }

    public BasketItem getDataByProductId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        //
        Cursor cursor = db.query(
                        getTableName()
                        , new String[]{
                                BasketItem.key_id
                                , BasketItem.key_product
                                , BasketItem.key_size
                                , BasketItem.key_color
                                , BasketItem.key_quantity
                                , BasketItem.key_color_title
                                , BasketItem.key_color_value
                                , BasketItem.key_product_image
                                , BasketItem.key_product_title
                                , BasketItem.key_product_price
                                , BasketItem.key_size_title
                                }
                        , BasketItem.key_product + "=?", new String[]{String.valueOf(id)}
                        , null
                        , null
                        , null);
        //
        if (cursor != null)
            cursor.moveToNext();

        //**etx
        db.close();
        //
        if (cursor.getCount() == 0) {
            //**etx
            cursor.close();
            return null;
        }
        //**etx
        //cursor.close();
        return new BasketItem(cursor);
    }


    public BasketItem getDataByDetail(long productId, long sizeId, long colorId) {
        SQLiteDatabase db = this.getReadableDatabase();
        //
        Cursor cursor = db.query(
                getTableName()
                , new String[]{
                        BasketItem.key_id
                        , BasketItem.key_product
                        , BasketItem.key_size
                        , BasketItem.key_color
                        , BasketItem.key_quantity
                        , BasketItem.key_color_title
                        , BasketItem.key_color_value
                        , BasketItem.key_product_image
                        , BasketItem.key_product_title
                        , BasketItem.key_product_price
                        , BasketItem.key_size_title
                        }
                , BasketItem.key_product + "=? AND " + BasketItem.key_size + "=? AND " + BasketItem.key_color + "=?"
                , new String[]{
                        String.valueOf(productId)
                        , String.valueOf(sizeId)
                        , String.valueOf(colorId)
                        }
                , null
                , null
                , null);

        if (cursor != null)
            cursor.moveToNext();

        //**etx
        db.close();

        if (cursor.getCount() == 0)
            return null;

        return new BasketItem(cursor);
    }


    public BasketItem addToBasket(BasketItem data) {
        //getDataByDetail(long productId, long sizeId, long colorId)
        BasketItem oldData = getDataByDetail(
                data.getProduct().getId()
                , data.getSize() != null ? data.getSize().getId() : 0
                , data.getColor() != null ? data.getColor().getId() : 0
                );

        if (oldData == null) {
            ContentValues values = new ContentValues();
            values.put(BasketItem.key_product, data.getProduct().getId());
            values.put(BasketItem.key_size, data.getSize() != null ? data.getSize().getId() : 0);
            values.put(BasketItem.key_color, data.getColor() != null ? data.getColor().getId() : 0);
            values.put(BasketItem.key_quantity, data.getQuantity());
            values.put(BasketItem.key_color_title, data.getColor() != null ? data.getColor().getTitle() : "");
            values.put(BasketItem.key_color_value, data.getColor() != null ? data.getColor().getValue() : "#fff");
            values.put(BasketItem.key_size_title, data.getSize() != null ? data.getSize().getTitle() : "");
            values.put(BasketItem.key_product_image, data.getProduct().getImage());
            values.put(BasketItem.key_product_title, data.getProduct().getTitle());
            values.put(BasketItem.key_product_price, data.getProduct().getPrice());
            addData(values);
            oldData = data;
        }
        else {
            oldData.setQuantity(oldData.getQuantity() + 1);
            updateQuantity(oldData);
        }
        return oldData;
    }


    public int getAllBasketDataCount() {
        String countQuery = "SELECT * FROM " + getTableName();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        //**etx
        db.close();
        return count;
    }

    public List<BasketItem> getAllData() {
        String allQuery = "SELECT * FROM " + getTableName();
        SQLiteDatabase db = this.getReadableDatabase();
        //
        Cursor cursor = db.rawQuery(allQuery, null);
        if (cursor != null)
            cursor.moveToNext();
        if (cursor.getCount() == 0) {
            //**etx
            db.close();
            return new ArrayList<>();
        }
        //
        List<BasketItem> result = new ArrayList<>();
        do {
            result.add(new BasketItem(cursor));
        } while (cursor.moveToNext());

        //**etx
        db.close();
        return result;
    }


    private int updateQuantity(BasketItem data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BasketItem.key_quantity, data.getQuantity());
        int i = db.update(
                getTableName()
                , values
                , BasketItem.key_id + "=?"
                , new String[]{String.valueOf(data.getId())}
        );
        //**etx
        db.close();
        return i;
    }

    public void deleteAllBasket() {
        String query = "delete from " + getTableName();
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }
}
