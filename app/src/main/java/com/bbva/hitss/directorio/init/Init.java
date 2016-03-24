package com.bbva.hitss.directorio.init;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bbva.R;
import com.bbva.hitss.directorio.Controllers.ColaboradoresController;
import com.bbva.hitss.directorio.Controllers.SessionController;
import com.bbva.hitss.directorio.Utils.Utils;


public class Init extends AppCompatActivity {
    SessionController manager;
    Snackbar snackbar;
    Button button;
    Button button2;
    Button button3;
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        manager = new SessionController();
        button = (Button) findViewById(R.id.imageButton);
        button2 = (Button) findViewById(R.id.imageButton3);
        button3 = (Button) findViewById(R.id.imageButton4);
        button4 = (Button) findViewById(R.id.imageButton5);
        Utils.getTypeFace(button, getAssets());
        Utils.getTypeFace(button2, getAssets());
        Utils.getTypeFace(button3, getAssets());
        Utils.getTypeFace(button4, getAssets());
        /*
        Button button = (Button) findViewById(R.id.btn_logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.setPreferences(Init.this, "status", "0");
                finish();
            }
        });*/
    }

    public void some(View view) {
        if (Utils.isConnectedToInternet(getApplicationContext())) {
            Intent intent = new Intent(Init.this, ColaboradoresController.class);
            startActivity(intent);
        } else {
            snackbar = Snackbar.make(view, "SIN CONEXION", Snackbar.LENGTH_SHORT)
                    .setAction("CONFIGURACION", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                        }
                    });
            snackbar.show();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Aqui cambia***
        startActivity(intent);
        finish();
        System.exit(0);
    }
}
