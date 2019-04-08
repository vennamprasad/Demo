package com.example.demo.utils.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * The type Activity utils.
 */
public class ActivityUtils {

    private ActivityUtils() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    /**
     * Hides the activity's action bar
     *
     * @param activity the activity
     */
    public static void hideActionBar(Activity activity) {
        // Call before calling setContentView();
        if (activity != null) {
            activity.getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
            if (activity.getActionBar() != null) {
                activity.getActionBar().hide();
            }
        }
    }

    /**
     * Sets the activity in Fullscreen mode
     *
     * @param activity the activity
     */
    public static void setFullScreen(Activity activity) {
        // Call before calling setContentView();
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private static void setIntent(Activity from, Class<Activity> to) {
        Intent intent = new Intent(from.getApplicationContext(), to);
        from.startActivity(intent);
    }

    private static void setIntentWithExtras(Activity from, Class<Activity> to, Bundle bundle) {
        Intent intent = new Intent(from.getApplicationContext(), to);
        intent.putExtras(bundle);
        from.startActivity(intent);
    }
}