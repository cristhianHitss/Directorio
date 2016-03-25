package com.bbva.hitss.directorio.Controllers;

import android.accounts.AccountManager;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.bbva.R;
import com.bbva.hitss.directorio.Utils.Utils;
import com.bbva.hitss.directorio.init.MainApp;
import com.google.android.gms.common.AccountPicker;

public class LoginController extends AppCompatActivity {
    ProgressBar progressBar;
    SessionController manager;
    int SOME_REQUEST_CODE;
    public View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);
        manager = new SessionController();
        progressBar = (ProgressBar) findViewById(R.id.loginprogress);
    }

    public void initSession(View view) {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"},
                false, null, null, null, null);
        startActivityForResult(intent, SOME_REQUEST_CODE);
        putView(view);
    }

    protected void onActivityResult(final int requestCode, final int resultCode,
                                    final Intent data) {
        if (requestCode == SOME_REQUEST_CODE && resultCode == RESULT_OK) {
            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            if (Utils.veryfyAccount(accountName)) {
                manager.setPreferences(LoginController.this, "status", "1");
                String status = manager.getPreferences(LoginController.this, "status");
                Log.d("status", status);
                Snackbar snackbar= Snackbar.make(getView(),"INICIANDO SESION",Snackbar.LENGTH_SHORT);
                snackbar.show();
                progressBar.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(LoginController.this, MainApp.class);
                startActivity(intent);

            } else {
                Snackbar snackbar= Snackbar.make(getView(),"PERMISOS INSUFICIENTES",Snackbar.LENGTH_SHORT);
                snackbar.show();
                progressBar.setVisibility(View.INVISIBLE);
            }

        } else {
            Snackbar snackbar= Snackbar.make(getView(),"SELECCIONA UNA CUENTA",Snackbar.LENGTH_SHORT);
            snackbar.show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//*** Aqui cambia ***
        startActivity(intent);
        finish();
        System.exit(0);
    }

    //Administracion del  View
    public View getView() {
        return view;
    }
    public void setView(View view) {
        this.view = view;
    }
    public void putView(View view){
        setView(view);
    }

}
