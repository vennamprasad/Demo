package com.example.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.WIFI_SERVICE;

public class DemoUtils {
    public static String Root_Path = "";
    public static String DB_PATH = "";
    public static String IMAGE_FOLDER_PATH = "";
    public static String IMAGE_FOLDER_NAME = "DemoImages";
    public static String DB_FOLDER_NAME = "DemoDatabase";
    public static String DB_Name = "Demo.db";
    public static String MobilePattern = "[6-9][0-9]{9}";

    static void setCustomTitle(AppCompatActivity activity) {
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayShowCustomEnabled(true);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(activity);
        View v = inflator.inflate(R.layout.titleview, null);
        ((TextView) v.findViewById(R.id.title)).setText(activity.getTitle());
        activity.getSupportActionBar().setCustomView(v);
    }

    @SuppressLint("DefaultLocale")
    public static String convertToSuffix(long count) {
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format("%.1f%c", count / Math.pow(1000, exp), "kmgtpe".charAt(exp - 1));
    }

    public void lodImage(Context context, String URL, ImageView view) {
        Glide.with(context).load(URL).into(view);
    }

    public static String creRowId() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    @SuppressLint("ResourceType")
    public static String getId(View view) {
        if (view.getId() == 0xffffffff) return "no-id";
        else return view.getResources().getResourceName(view.getId());
    }
    @SuppressLint("HardwareIds")
    public static String getMacAddress(Context context) {
        String result = "";
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = Objects.requireNonNull(wifiManager).getConnectionInfo();
            result = wifiInfo.getMacAddress();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (!"".equals(result)) {
            SharedPreferences settings = context.getSharedPreferences("DeviceInfo", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("MAC_ADDRESS", result);
            editor.apply();
        } else {
            SharedPreferences settings = context.getSharedPreferences("DeviceInfo", MODE_PRIVATE);
            result = settings.getString("MAC_ADDRESS", "");
        }
        return result;
    }

    public static String saveToInternalStorage(Bitmap bitmapImage, String fileName) {
        File directory = new File(Root_Path, "DemoImages");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File mypath = new File(directory.getPath(), fileName);
        FileOutputStream fos = null;
        try {
            bitmapImage.setDensity(72);
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
    }

    static void showMsg(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    static String getExceptionRootMessage(Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(throwable.getMessage()).append("\r\n");
        StackTraceElement[] stackTraceElementsList = throwable.getStackTrace();
        if (stackTraceElementsList != null) {
            for (StackTraceElement stackTraceElement : stackTraceElementsList) {
                if (stackTraceElement != null) {
                    stringBuilder.append(stackTraceElement.getClassName());
                    stringBuilder.append(".");
                    stringBuilder.append(stackTraceElement.getMethodName());
                    stringBuilder.append("()-");
                    stringBuilder.append(stackTraceElement.getLineNumber()).append("\r\n");
                }
            }
        }
        return stringBuilder.toString();
    }


}
