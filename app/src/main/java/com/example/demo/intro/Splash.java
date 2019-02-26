package com.example.demo.intro;

import androidx.appcompat.app.AppCompatActivity;

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
import static com.example.demo.Utils.DB_Name;
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
            Root_Path = getFilesDir().getAbsolutePath();
            createDirectories();
        } else {
            Root_Path = Environment.getExternalStorageDirectory().getAbsolutePath();
            createDirectories();
        }
    }

    private void createDirectories() {
        //DB
        File db = new File(Root_Path, Utils.DB_FOLDER_NAME + "/" + DB_Name);
        if (!db.exists()) {
            db.mkdirs();
        } else {
            Utils.DB_PATH = db.getPath();
        }
        //
        File image = new File(Root_Path, Utils.IMAGE_FOLDER_NAME);
        if (!image.exists()) {
            image.mkdirs();
        } else {
            Utils.IMAGE_FOLDER_PATH = image.getPath();
        }

    }

}
