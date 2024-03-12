package msh.myonlineshop.utlities;

import android.content.Context;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class CommonUtility {
    public static void showMsg(View view, String msg)
    {
        Snackbar.make(view, msg , Snackbar.LENGTH_SHORT).show();
    }
}
