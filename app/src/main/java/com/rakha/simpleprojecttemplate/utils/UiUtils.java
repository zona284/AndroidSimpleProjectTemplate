package com.rakha.simpleprojecttemplate.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.rakha.simpleprojecttemplate.services.SPData;
import com.rakha.simpleprojecttemplate.views.LoginActivity;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created By rakha
 * 2019-12-15
 */
public class UiUtils {

    public static String formatDateFromString(String oldFormat, String newFormat, String date){
        SimpleDateFormat oldSdf = new SimpleDateFormat(oldFormat);
        SimpleDateFormat dateFormat = new SimpleDateFormat(newFormat);
        try{
            Date temp = oldSdf.parse(date);
            date = dateFormat.format(temp);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();
        float pivotX = 0;
        float pivotY = 0;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, pivotX, pivotY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static void showKeyboard(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * for normalize phone number to national phone number
     *
     * @param number String
     * @return String
     */
    public static String normalizePhoneNumber(String number) {
        number = number.replace("+", "");
        number = number.replaceAll("[^+0-9]", ""); // All weird characters such as /, -, ...
        if (number.length() > 2) {
            if (number.substring(0, 2).equals("62")) {
                number = "0" + number.substring(2);
            }
        }
        return number;
    }

    public static String stripHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(html).toString();
        }
    }

    public static String rupiahFormatPrice(int number){
        if (number >= 0) {
            return "Rp"+numberFormatPrice(number);
        } else {
            return "-Rp"+numberFormatPrice(Math.abs(number));
        }
    }

    public static String numberFormatPrice(int number) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("ID"));
        return nf.format(number);
    }

    public static String hexFromColorResource(Context context, int colorResId){
        return "#" + Integer.toHexString(ContextCompat.getColor(context, colorResId) & 0x00ffffff);
    }

    //add clear text function to edit text
    public static void addClearTextOnEditTextChanged(final EditText editText, int startIconResId, int clearIconResId, CharSequence s){
        if (s.length() > 0 && editText.isEnabled()) {
            editText.setCompoundDrawablesRelativeWithIntrinsicBounds(startIconResId, 0, clearIconResId, 0);
            editText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    final int DRAWABLE_LEFT = 0;
                    final int DRAWABLE_TOP = 1;
                    final int DRAWABLE_RIGHT = 2;
                    final int DRAWABLE_BOTTOM = 3;

                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        if (motionEvent.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            editText.setText("");
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
        else {
            editText.setCompoundDrawablesWithIntrinsicBounds(startIconResId, 0, 0, 0);
            editText.setOnTouchListener(null);
        }
    }

    /**
     * Method for convert image to Base64Encoding
     * @param bmp
     * @param quality
     * @return
     */
    public static String bitmapToBase64String(Bitmap bmp, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * for open url link to web browser
     *
     * @param context Context
     * @param url     String
     */
    public static void openUrl(Context context, String url) {
        if (url != null && !url.equalsIgnoreCase("")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        }
    }

    public static void openWhatsApp(Context context, String number) {
//        String url = "https://api.whatsapp.com/send?phone=" + number;
        String url = "whatsapp://send?phone=" + number;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }

    public static void openMessage(Context context, String number) {
        String url = "sms:" + number;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }

    public static String distanceRoundFormat(double distance){
        double value = Math.round(distance * 100.0) / 100.0;
        if(value > 1) {
            return value + " Km";
        } else {
            value *= 100;
            return value + " m";
        }
    }

    public static void doLogout(Context context){
        SPData.clearAllData(context);
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * for check network
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        try {
            android.net.ConnectivityManager e = (android.net.ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = e.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } catch (Exception e) {
            Log.w("isConnected", e.toString());
        }

        return false;
    }

    public static int dpToPixel(Context context, float dpMeasure) {
        Resources r = context.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpMeasure,
                r.getDisplayMetrics()
        );
        return px;
    }

}