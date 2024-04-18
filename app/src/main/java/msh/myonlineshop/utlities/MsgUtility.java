package msh.myonlineshop.utlities;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class MsgUtility {

    public static void showMsgShort(View view, String msg)
    {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
        System.out.println(msg);
    }
    public static void showMsgShort(View view, String msg, int intTranslationY)
    {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
//        snackbar.getView().setTranslationY(-130);
        snackbar.getView().setTranslationY(intTranslationY);
        snackbar.show();
        System.out.println(msg);
    }


    public static void showMsgLong(View view, String msg)
    {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        System.out.println(msg);
    }
    public static void showMsgLong(View view, String msg, int intTranslationY)
    {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
//        snackbar.getView().setTranslationY(-130);
        snackbar.getView().setTranslationY(intTranslationY);
        snackbar.show();
        System.out.println(msg);
    }

}
