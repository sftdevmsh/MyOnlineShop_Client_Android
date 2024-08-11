package msh.myonlineshop.utlities;

import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import msh.myonlineshop.PaymentActivity;

public class MsgUtility {

//        Toast.makeText(PaymentActivity.this, "Error on payment method", Toast.LENGTH_SHORT).show();

    public static void showMsgShort(View view, String msg)
    {
//        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        Snackbar snackbar = Snackbar.make(view, msg, BaseTransientBottomBar.LENGTH_SHORT);
        snackbar.show();
        System.out.println(msg);
    }
    public static void showMsgShort(View view, String msg, int intTranslationY)
    {
//        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        Snackbar snackbar = Snackbar.make(view, msg, BaseTransientBottomBar.LENGTH_SHORT);
//        snackbar.getView().setTranslationY(-130);
        snackbar.getView().setTranslationY(intTranslationY);
        snackbar.show();
        System.out.println(msg);
    }


    public static void showMsgLong(View view, String msg)
    {
//        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        Snackbar snackbar = Snackbar.make(view, msg, BaseTransientBottomBar.LENGTH_LONG);
        snackbar.show();
        System.out.println(msg);
    }
    public static void showMsgLong(View view, String msg, int intTranslationY)
    {
//        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        Snackbar snackbar = Snackbar.make(view, msg, BaseTransientBottomBar.LENGTH_LONG);
//        snackbar.getView().setTranslationY(-130);
        snackbar.getView().setTranslationY(intTranslationY);
        snackbar.show();
        System.out.println(msg);
    }

}
