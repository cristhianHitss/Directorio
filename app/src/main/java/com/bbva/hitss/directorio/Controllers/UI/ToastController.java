package com.bbva.hitss.directorio.Controllers.UI;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bbva.R;

/**
 * Created by Hitss on 17/03/2016.
 */
public class ToastController {
    public final static int GRAVITY_CENTER = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
    public final static int GRAVITY_BOTTOM = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;


    public static void show(Context context, String[] from, int[] to, int toastLayout, int toastlength, int gravity) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customToastRoot = inflater.inflate(toastLayout, null);
        for (int i = 0; i < from.length; i++) {
            ((TextView) customToastRoot.findViewById(to[i])).setText(from[i]);
        }
        Toast customtoast = new Toast(context);
        customtoast.setView(customToastRoot);
        customtoast.setGravity(gravity, 0, 0);
        customtoast.setDuration(toastlength);
        customtoast.show();
    }

    public static void showSimple(Context context, String text, int toastlength) {
        show(context, new String[]{text}, new int[]{R.id.toast_simple_text}, R.layout.toast_view, toastlength, GRAVITY_CENTER);
    }

    public static void showSimpleAtBottom(Context context, String text, int toastlength) {
        show(context, new String[]{text}, new int[]{R.id.toast_simple_text}, R.layout.toast_view, toastlength, GRAVITY_BOTTOM);
    }

   /* public static void showLlamadaEntrante(Context context, HashMap<String, Object> contacto) {
        if (contacto != null) {
            show(context, new String[]{(String) contacto.get("NOMBRE"), (String) contacto.get("USUARIO"), (String) contacto.get("SUBBLOQUE"), (String) contacto.get("UBICACION")},
                    new int[]{R.id.contact_name, R.id.contact_user, R.id.contact_subbloque, R.id.contact_ubicacion},
                    R.layout.toast_llamada_entrante, Toast.LENGTH_LONG, GRAVITY_CENTER);
        }
    }*/
}
