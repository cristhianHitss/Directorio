package com.bbva.hitss.directorio.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import com.bbva.R;
import com.bbva.hitss.directorio.Controllers.UI.ToastController;

public class PhoneUtils {

    private static final String TAG = PhoneUtils.class.getSimpleName();

    public static void sendSMS(Activity activity, String[] to, String msg) {

        String recipents = "";
        for (int i = 0; i < to.length; i++) {
            recipents += to[i];
            if (i < to.length - 1) {
                recipents += ",";
            }
        }

        Utils.log(TAG, "Enviar SMS: to: '" + recipents + "', msg: " + msg + "'");

        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + recipents));
        smsIntent.putExtra("sms_body", msg);
        activity.startActivity(smsIntent);
    }

    public static void sendEmail(Activity activity, String[] to, String subject, String body) {
        Utils.log(TAG, "Enviar Email: to: '" + to + "', Asunto : '" + subject + "', Cuerpo : '" + body + "'");
        Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        activity.startActivity(emailIntent);
    }


    public static void makePhoneCall(Activity activity, String number) {

        String tnum = number;

        if (number.contains(" Ext. ")) {         //, pause
            number = number.replace(" Ext. ", ",");
            // es una extensión
            try {
                number = URLEncoder.encode(number, "UTF-8");
                /*
                CustomToast.showSimple(activity, "Espera un momento, la extensión se marcará automáticamente.", Toast.LENGTH_SHORT);
*/
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, "Error, no se pudo marcar la extensión al numero: '" + number + "': " + e.getMessage());
                e.printStackTrace();
            }
        }

        String uri = "tel:" + number;

        Utils.log(TAG, "Realizar llamada a : '" + tnum + "', uri: '" + uri + "'");

        Intent callIntent = new Intent(Intent.ACTION_CALL); // ACTION_CALL marca, ACTION_DIAL abre la pantalla con el telefono para que el usuario marque
        callIntent.setData(Uri.parse(uri));
        activity.startActivity(callIntent);
    }

    public static String getStringPreference(Context ctx, String prefFileName, String key) {
        SharedPreferences prefs = ctx.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);

        return prefs.getString(key, null);
    }

    public static Map<String, ?> getAllPreferences(Context ctx, String prefFileName) {
        SharedPreferences prefs = ctx.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);

        return prefs.getAll();
    }

    public static Map<String, ?> putStringPreferences(Context ctx, String prefFileName, String[] keys, String[] values) {
        SharedPreferences prefs = ctx.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        for (int i = 0; i < keys.length; i++) {
            editor.putString(keys[i], values[i]);
        }
        editor.commit();

        return prefs.getAll();
    }

    public static Map<String, ?> removeStringPreferences(Context ctx, String prefFileName, String[] keys) {
        SharedPreferences prefs = ctx.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        for (int i = 0; i < keys.length; i++) {
            editor.remove(keys[i]);
        }
        editor.commit();

        return prefs.getAll();
    }

    public static void putBooleanPreferences(Context ctx, String prefFileName, String[] keys, boolean[] values) {
        SharedPreferences prefs = ctx.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        for (int i = 0; i < keys.length; i++) {
            editor.putBoolean(keys[i], values[i]);
        }
        editor.commit();
    }

    /**
     * <uses-permission android:name="android.permission.READ_CONTACTS" />
     *
     * @param context
     * @param phoneNumber
     * @return
     */
    public static boolean isNumberInContacts(Context context, String phoneNumber) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cur = context.getContentResolver().query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);

        boolean exists = (cur.getCount() > 0);

        if (!cur.isClosed()) {
            cur.close();
        }

        return exists;
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        boolean installed = true;

        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_ACTIVITIES);

        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }

        return installed;
    }

    public static void showInstalledAppDetailsWindow(Context context, String packageName) {
        final String SCHEME = "package";
        final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";
        final String APP_PKG_NAME_22 = "pkg";
        final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";
        final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";

        Intent intent = new Intent();
        final int apiLevel = Build.VERSION.SDK_INT;
        if (apiLevel >= 9) { // above 2.3
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts(SCHEME, packageName, null);
            intent.setData(uri);
        } else { // below 2.3
            final String appPkgName = (apiLevel == 8 ? APP_PKG_NAME_22 : APP_PKG_NAME_21);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName(APP_DETAILS_PACKAGE_NAME, APP_DETAILS_CLASS_NAME);
            intent.putExtra(appPkgName, packageName);
        }
        context.startActivity(intent);
    }

    public static boolean openApp(final Context context, final String packageName, final int wait_secs) {
        if (isAppInstalled(context, packageName)) {
            android.os.Handler handler = new android.os.Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
                    context.startActivity(launchIntent);
                }
            }, wait_secs * 1000);

            return true;
        }

        return false;
    }


    public static int sendWhatsapp(Context context, String number) {
        int found;
        String wp = context.getResources().getString(R.string.whatsapp_package);
        if (isAppInstalled(context, wp)) {
            if (PhoneUtils.isNumberInContacts(context, number)) {
                Uri uri = Uri.parse("smsto:" + number);
                Intent waIntent = new Intent(Intent.ACTION_SENDTO, uri);
                waIntent.setPackage(wp);
                context.startActivity(Intent.createChooser(waIntent, "Elige Whatsapp para continuar..."));
                found = 0; // OK
            } else {
                found = 1; // Contacto no existe
            }
        } else {
            found = -1; // Whatsapp no está instalado
        }

        Log.e(TAG, "Enviar Whatsapp: " + found);
        return found;
    }

    // Este codigo pide con quien compartir, pero pone el texto
    private void shareInWhatsapp(Context context, String text) {
        PackageManager pm = context.getPackageManager();
        try {
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");

            PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA); // verificar que este instalado

            waIntent.setPackage("com.whatsapp");
            waIntent.putExtra(Intent.EXTRA_TEXT, text);

            context.startActivity(Intent.createChooser(waIntent, "Share with"));
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    public static boolean insertContact(Context context, String strDisplayName, String strNumber) {
        boolean result = true;
        ArrayList<ContentProviderOperation> cntProOper = new ArrayList<ContentProviderOperation>();
        int contactIndex = cntProOper.size();//ContactSize

         cntProOper.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)//Step1
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());

         cntProOper.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)//Step2
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, contactIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, strDisplayName) // Name of the contact
                .build());

         cntProOper.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)//Step 3
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, contactIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, strNumber) // Number to be added
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build()); //Type like HOME, MOBILE etc

         cntProOper.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)//Step 3
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, contactIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, "56253434,6789") // Number to be added
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM)
                .withValue(ContactsContract.CommonDataKinds.Phone.LABEL, "Extension 1").build()); //Type like HOME, MOBILE etc
        try {
              ContentProviderResult[] contentProresult = null;
            contentProresult = context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, cntProOper); //apply above data insertion into contacts list

        } catch (RemoteException | OperationApplicationException exp) {
            result = false;
        }

        return result;
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static void copyTextToClipboard(String text, Activity activity, String toast) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < Build.VERSION_CODES.HONEYCOMB /*11 NONEYCOMB*/) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText(activity.getResources().getString(R.string.app_name), text);
            clipboard.setPrimaryClip(clip);
        }
        if(toast!=null) {
            ToastController.showSimple(activity, toast, Toast.LENGTH_LONG);}

        Utils.log(TAG, "Texto copiado al portapapeles: '" + text + "'");
    }
}
