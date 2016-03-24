package com.bbva.hitss.directorio.init;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.bbva.R;
import com.bbva.hitss.directorio.Controllers.ColaboradoresController;
import com.bbva.hitss.directorio.Controllers.SessionController;
import com.bbva.hitss.directorio.Utils.Utils;


public class Init extends AppCompatActivity {
    SessionController manager;
    Snackbar snackbar;
    DrawerLayout drawerLayout;
    ListView listView;
    String[] opciones = {"Configuracion", "Desarrolladores","Contacto", "Acerca de","Salir"};
    Button button;
    Button button2;
    Button button3;
    Button button4;
    Button mn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        manager = new SessionController();
        button = (Button) findViewById(R.id.imageButton);
        button2 = (Button) findViewById(R.id.imageButton3);
        button3 = (Button) findViewById(R.id.imageButton4);
        button4 = (Button) findViewById(R.id.imageButton5);
        mn = (Button) findViewById(R.id.mn);
        Utils.getTypeFace(button, getAssets());
        Utils.getTypeFace(button2, getAssets());
        Utils.getTypeFace(button3, getAssets());
        Utils.getTypeFace(button4, getAssets());
        Utils.getTypeFace(mn, getAssets());
        listView = (ListView) findViewById(R.id.list_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        listView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.item_view_menu, R.id.txt,
                opciones));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                Toast.makeText(Init.this, "Item: " + opciones[arg2],
                        Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
            }
        });
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

    public void mn(View view) {
        if (drawerLayout.isDrawerOpen(listView)) {
            drawerLayout.closeDrawers();
        } else {
            drawerLayout.openDrawer(listView);
        }
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
