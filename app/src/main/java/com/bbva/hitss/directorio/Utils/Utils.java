package com.bbva.hitss.directorio.Utils;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

public class Utils {
    private static int screenWidth = 0;
    private static int screenHeight = 0;

    public static Boolean veryfyAccount(String account) {
        if (account.equalsIgnoreCase("ccastanedarivera@gmail.com")) {
            return true;
        } else if (account.equalsIgnoreCase("fraxeba@gmail.com")) {
            return true;
        } else if (account.equalsIgnoreCase("monitor.bbvamovilidadmx@gmail.com")) {
            return true;
        } else if (account.equalsIgnoreCase("l.enrique.cata@gmail.com")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static void log(final String TAG, final String msg) {
        Log.e(TAG, msg);
    }

    public static void getTypeFace(TextView view, AssetManager assetManager) {
        Typeface font = Typeface.createFromAsset(assetManager, "fontawesome-webfont.ttf");
        view.setTypeface(font);
    }

    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }
}
