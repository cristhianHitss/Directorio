package com.bbva.hitss.directorio.Utils.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.bbva.R;

/**
 * Created by Hitss on 17/03/2016.
 */
public class UIUtils {


    public interface ListDialogListener {
        void onListDialogItemClicked(int dialogId, int selectedItem, View listitem);

        void onListDialogCancel(int dialogId, View listitem);
    }

    public interface ConfirmDialogListener {
        void onConfirmDialogAccept();

        void onConfirmDialogCancel();
    }

    public static AlertDialog.Builder getConfirmDialog(final Activity parent, final String title, final String message, final ConfirmDialogListener listener) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(parent);
        //LayoutInflater inflater = parent.getLayoutInflater();
        //View dialogView = inflater.bind(R.layout.dialog_list, null);
        //builderSingle.setView(dialogView);
        builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle(title);
        if (message != null) builderSingle.setMessage(message);

        builderSingle.setPositiveButton(parent.getResources().getString(R.string.op_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onConfirmDialogAccept();
                dialog.dismiss();
            }
        });

        builderSingle.setNegativeButton(parent.getResources().getString(R.string.op_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onConfirmDialogCancel();
                dialog.dismiss();
            }
        });

        builderSingle.setCancelable(false);
        return builderSingle;
    }

    public static AlertDialog.Builder getInfoDialog(Activity parent, final String title, final String message) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(parent);
        builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle(title);
        builderSingle.setMessage(message);

        builderSingle.setPositiveButton(parent.getResources().getString(R.string.op_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builderSingle;
    }


}
