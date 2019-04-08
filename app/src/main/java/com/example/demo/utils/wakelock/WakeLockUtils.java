package com.example.demo.utils.wakelock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;

public class WakeLockUtils {

    /**
     * The Wake lock.
     */
    private static PowerManager.WakeLock wakeLock;

    private WakeLockUtils() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }

    public static void holdWakeLock(Context context) {
        holdWakeLockTimed(context, 3600);
    }

    /**
     * Hold wake lock.
     *
     * @param context the context
     */
    @SuppressLint("InvalidWakeLockTag")
    public static void holdWakeLockTimed(Context context, long time) {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakeLock");
        wakeLock.acquire(time);
    }

    /**
     * Release wake lock.
     */
    public static void releaseWakeLock() {
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
    }
}
