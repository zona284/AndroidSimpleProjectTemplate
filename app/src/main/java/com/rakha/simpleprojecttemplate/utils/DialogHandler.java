package com.rakha.simpleprojecttemplate.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.rakha.simpleprojecttemplate.R;

/**
 * Created By rakha
 * 2019-12-20
 */
public class DialogHandler {
    public static void showDialog(Context context, String dialogTitle, String dialogMessage) {
        new AlertDialog.Builder(context)
                .setTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public static void showDialog(Context context, String dialogTitle, String dialogMessage, String buttonText, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setPositiveButton(buttonText, okListener)
                .show();
    }

    public static void showDialog(Context context,
                                  String title,
                                  String message,
                                  String confirmTitle,
                                  String cancelTitle,
                                  DialogInterface.OnClickListener confirmListener,
                                  DialogInterface.OnClickListener cancelListener) {

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(confirmTitle, confirmListener)
                .setNegativeButton(cancelTitle, cancelListener)
                .create()
                .show();
    }

    public static void showBasicConfirmationDialogWithSyle(Context context, String dialogTitle, String dialogMessage, String positiveWording, String negativeWording, DialogInterface.OnClickListener okListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setNegativeButton(negativeWording, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(positiveWording, okListener)
                .setCancelable(false)
                .setInverseBackgroundForced(false).show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.secondary_text_color));
    }

}