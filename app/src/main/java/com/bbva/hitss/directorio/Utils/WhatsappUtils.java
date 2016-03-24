package com.bbva.hitss.directorio.Utils;

import android.app.Activity;
import android.widget.Toast;

import com.bbva.R;
import com.bbva.hitss.directorio.Controllers.UI.ToastController;
import com.bbva.hitss.directorio.Utils.UI.UIUtils;

/**
 * Created by Hitss on 17/03/2016.
 */
public class WhatsappUtils {
    public static void sendWhatsapp(final Activity activity, final String number, final String name) {

        switch (PhoneUtils.sendWhatsapp(activity, number)) {
            case 1: // Contacto no existe
                UIUtils.getConfirmDialog(activity, "Envío de Whatsapp", "Es necesario tener este número dentro de tus contactos. \n¿Deseas agregar esta persona a tus contactos?.",
                        new UIUtils.ConfirmDialogListener() {
                            @Override
                            public void onConfirmDialogAccept() {
                                if (!PhoneUtils.insertContact(activity, name, number.replaceAll("\\s+", ""))) {
                                    UIUtils.getInfoDialog(activity, "Importar contacto", "No fue posible agregar el contacto. Es necesario agregarlo manualmente.").show();
                                } else {
                                    //UIUtils.getInfoDialog(activity, "Importar contacto", "Contacto agregado correctamente.\nPuede ser necesario actualizar los contactos de Whatsapp antes de continuar.").show();
                                    ToastController.showSimple(activity, "Contacto agregado correctamente", Toast.LENGTH_SHORT);
                                    PhoneUtils.openApp(activity, activity.getResources().getString(R.string.whatsapp_package), 2);
                                    ToastController.showSimple(activity, "Actualiza tu lista de contactos de Whatsapp", Toast.LENGTH_SHORT);
                                }
                            }

                            @Override
                            public void onConfirmDialogCancel() {

                            }
                        }
                ).show();
                break;
            case -1: // Whatsapp no está instalado
                UIUtils.getInfoDialog(activity, "Envío de Whatsapp", "Whatsapp no está instalado.").show();
                break;
        }
    }
}
