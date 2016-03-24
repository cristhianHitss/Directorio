package com.bbva.hitss.directorio.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bbva.R;
import com.bbva.hitss.directorio.init.Init;

public class LoadingController extends AppCompatActivity {
    SessionController manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_view);
        manager = new SessionController();


        // Metodo 1
        /****** Se crea un Thread que dormira(sleep) por 3 segundos se realiza la validacion de logueo *************/
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread que dormira (sleep) por 3 segundos
                    sleep(3 * 1000);

                    // Despues de 5 segundos se redirigira a otro intent
                    String status = manager.getPreferences(LoadingController.this, "status");
                    Log.d("status", status);
                    if (status.equals("1")) {
                        Intent i = new Intent(LoadingController.this, Init.class);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(LoadingController.this, LoginController.class);
                        startActivity(i);
                    }


                    //Eliminar activity
                    finish();

                } catch (Exception e) {
                    Log.d("Exepcion :", e.getMessage());
                }
            }
        };

        // Comienza thread
        background.start();

//Metodo 2

        /*
        new Handler().postDelayed(new Runnable() {

            // Se utiliza un Handler con postDelayed llamando a el metodo runnable

            @Override
            public void run() {
              if (status=="1"){
                        Intent i=new Intent(Splash.this,MainActivity.class);
                        startActivity(i);
                    }else{
                        Intent i=new Intent(Splash.this,Login.class);
                        startActivity(i);
                    }

                // Se cierra el activity
                finish();
            }
        }, 3*1000); // Se espera por 3 segundos
        */
    }

    /*
    Este metodo se utiliza para al momento de iniciar y el usuario presione el boton atras no lo envie a el activity anterior
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //***Aqui cambia***
        startActivity(intent);
        finish();
        System.exit(0);
    }
}
