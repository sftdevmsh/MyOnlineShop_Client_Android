package msh.myonlineshop.localDbHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import msh.myonlineshop.ProductDetailsActivity;
import msh.myonlineshop.localDbHandler.base.LocalDbHandler;
import msh.myonlineshop.models.CardItem;

public class CardDbHandler extends LocalDbHandler<ProductDetailsActivity> {

    public CardDbHandler(Context context) {
        super(context);
    }

    @Override
    protected String getTableName() {
        return "CARDS";
    }


    @Override
    protected String getCreateTableQuery() {
        return "CREATE TABLE IF NOT EXISTS " + getTableName() +
                "(" + CardItem.key_id + " INTEGER PRIMARY KEY,"
                + CardItem.key_product + " INTEGER,"
                + CardItem.key_size + " INTEGER,"
                + CardItem.key_color + " INTEGER,"
                + CardItem.key_quantity + " INTEGER,"
                + CardItem.key_color_title + " TEXT,"
                + CardItem.key_color_value + " TEXT,"
                + CardItem.key_product_image + " TEXT,"
                + CardItem.key_product_title + " TEXT,"
                + CardItem.key_product_price + " INTEGER,"
                + CardItem.key_size_title + " TEXT)";
    }

    @Override
    protected String getDropTableQuery() {
        return "DROP TABLE IF EXISTS " + getTableName();
    }

    public CardItem getDataByProductId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        //
        Cursor cursor = db.query(
                        getTableName()
                        , new String[]{
                                CardItem.key_id
                                , CardItem.key_product
                                , CardItem.key_size
                                , CardItem.key_color
                                , CardItem.key_quantity
                                , CardItem.key_color_title
                                , CardItem.key_color_value
                                , CardItem.key_product_image
                                , CardItem.key_product_title
                                , CardItem.key_product_price
                                , CardItem.key_size_title
                                }
                        , CardItem.key_product + "=?", new String[]{String.valueOf(id)}
                        , null
                        , null
                        , null);
        //
        if (cursor != null)
            cursor.moveToNext();
        //**etx
        db.close();
        //
        if (cursor.getCount() == 0)
            return null;
        //
        return new CardItem(cursor);
    }


    public CardItem getDataByDetail(long productId, long sizeId, long colorId) {
        SQLiteDatabase db = this.getReadableDatabase();
        //
        Cursor cursor = db.query(
                getTableName()
                , new String[]{
                        CardItem.key_id
                        , CardItem.key_product
                        , CardItem.key_size
                        , CardItem.key_color
                        , CardItem.key_quantity
                        , CardItem.key_color_title
                        , CardItem.key_color_value
                        , CardItem.key_product_image
                        , CardItem.key_product_title
                        , CardItem.key_product_price
                        , CardItem.key_size_title
                        }
                , CardItem.key_product + "=? AND " + CardItem.key_size + "=? AND " + CardItem.key_color + "=?"
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

        return new CardItem(cursor);
    }


    public CardItem addToBasket(CardItem data) {
        //getDataByDetail(long productId, long sizeId, long colorId)
        CardItem oldData = getDataByDetail(
                data.getProduct().getId()
                , data.getSize() != null ? data.getSize().getId() : 0
                , data.getColor() != null ? data.getColor().getId() : 0
                );

        if (oldData == null) {
            ContentValues values = new ContentValues();
            values.put(CardItem.key_product, data.getProduct().getId());
            values.put(CardItem.key_size, data.getSize() != null ? data.getSize().getId() : 0);
            values.put(CardItem.key_color, data.getColor() != null ? data.getColor().getId() : 0);
            values.put(CardItem.key_quantity, data.getQuantity());
            values.put(CardItem.key_color_title, data.getColor() != null ? data.getColor().getTitle() : "");
            values.put(CardItem.key_color_value, data.getColor() != null ? data.getColor().getValue() : "#fff");
            values.put(CardItem.key_size_title, data.getSize() != null ? data.getSize().getTitle() : "");
            values.put(CardItem.key_product_image, data.getProduct().getImage());
            values.put(CardItem.key_product_title, data.getProduct().getTitle());
            values.put(CardItem.key_product_price, data.getProduct().getPrice());
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

    public List<CardItem> getAllData() {
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
        List<CardItem> result = new ArrayList<>();
        do {
            result.add(new CardItem(cursor));
        } while (cursor.moveToNext());

        //**etx
        db.close();
        return result;
    }


    private int updateQuantity(CardItem data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CardItem.key_quantity, data.getQuantity());
        int i = db.update(
                getTableName()
                , values
                , CardItem.key_id + "=?"
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
