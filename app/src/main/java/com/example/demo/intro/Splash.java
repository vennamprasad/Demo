package com.example.demo.intro;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import com.example.demo.R;
import com.example.demo.Utils;
import com.example.demo.security.FingerprintsActivity;
import com.github.jksiezni.permissive.PermissionsGrantedListener;
import com.github.jksiezni.permissive.PermissionsRefusedListener;
import com.github.jksiezni.permissive.Permissive;

import java.io.File;
import java.io.FileOutputStream;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.demo.Utils.DB_FOLDER_NAME;
import static com.example.demo.Utils.DB_Name;
import static com.example.demo.Utils.DB_PATH;
import static com.example.demo.Utils.Root_Path;

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setDirectoryApproach();
        Thread splash = new Thread() {
            public void run() {
                try {
                    sleep(4 * 1000);
                    new Permissive.Request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                            .whenPermissionsGranted(new PermissionsGrantedListener() {
                                @Override
                                public void onPermissionsGranted(String[] permissions) throws SecurityException {
                                    Intent splash_intent = new Intent(Splash.this, FingerprintsActivity.class);
                                    startActivity(splash_intent);
                                    finish();
                                }
                            })
                            .whenPermissionsRefused(new PermissionsRefusedListener() {
                                @Override
                                public void onPermissionsRefused(String[] permissions) {

                                }
                            }).execute(Splash.this);
                } catch (Exception ignored) {
                }
            }
        };
        splash.start();
    }

    public void setDirectoryApproach() {
        String device = Build.DEVICE.toUpperCase();
        if (device.equals("GENERIC") || device.equals("GENERIC_X86") || device.equals("SDK")) {
            Root_Path = getFilesDir().getPath();
            createDirectories();
        } else {
            Root_Path = Environment.getExternalStorageDirectory().getPath();
            createDirectories();
        }
    }

    private void createDirectories() {
        //DB
        try {
            String rootPath = Root_Path + "/" + DB_FOLDER_NAME + "/";
            File root = new File(rootPath);
            if (!root.exists()) {
                root.mkdirs();
            }
            File f = new File(rootPath + DB_Name);
            if (!f.exists()) {
                f.createNewFile();
                FileOutputStream out = new FileOutputStream(f);
                out.flush();
                out.close();
            }
            DB_PATH = f.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        File image = new File(Root_Path, Utils.IMAGE_FOLDER_NAME);
        if (!image.exists()) {
            image.mkdirs();
        }
        Utils.IMAGE_FOLDER_PATH = image.getPath();

    }

}
