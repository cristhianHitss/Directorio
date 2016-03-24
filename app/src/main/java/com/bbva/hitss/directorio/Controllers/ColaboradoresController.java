package com.bbva.hitss.directorio.Controllers;

/**
 * Created by Hitss on 10/03/2016.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.bbva.R;
import com.bbva.hitss.directorio.Adapters.ColaboradoresAdapter;
import com.bbva.hitss.directorio.Http.Handlers.AsyncResult;
import com.bbva.hitss.directorio.Http.Handlers.GoogleSpreadsheetResponse;
import com.bbva.hitss.directorio.Http.SQL.StatementsSQL;
import com.bbva.hitss.directorio.Models.ColaboradorModel;
import com.bbva.hitss.directorio.Utils.Utils;
import com.bbva.hitss.directorio.init.Init;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ColaboradoresController extends AppCompatActivity {
    private ProgressBar bg_loader;
    Snackbar snackbar;
    private RecyclerView recycler;
    private LinearLayoutManager lManager;
    private CollapsingToolbarLayout collapser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colaboradores_);
        bg_loader = (ProgressBar) findViewById(R.id.bg_loader);
        setToolbar();// Añadir la Toolbar
        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        execute();
    }

    public void execute() {
        bg_loader.setVisibility(View.VISIBLE);
        new GoogleSpreadsheetResponse(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                if (object.toString().equalsIgnoreCase("{}")) {
                    showSnackBarAction("ERROR DE CONEXION");
                    bg_loader.setVisibility(View.INVISIBLE);
                } else {
                    processJson(object);
                    bg_loader.setVisibility(View.INVISIBLE);
                }
            }
        }).execute(StatementsSQL.selectAll);
    }

    private void processJson(JSONObject object) {
        List<ColaboradorModel> items = new ArrayList<>();
        try {
            JSONArray rows = object.getJSONArray("rows");
            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");
                int id = columns.getJSONObject(0).getInt("v");
                int no_empleado = columns.getJSONObject(1).getInt("v");
                String nombre = columns.getJSONObject(2).getString("v");
                String apellido_paterno = columns.getJSONObject(3).getString("v");
                String apellido_materno = columns.getJSONObject(4).getString("v");
                String usuarioxm = columns.getJSONObject(5).getString("v");
                String correobbva = columns.getJSONObject(6).getString("v");
                String ubicacion = columns.getJSONObject(7).getString("v");
                String edificio = columns.getJSONObject(8).getString("v");
                Long celular_personal = columns.getJSONObject(12).getLong("v");
                String area_laboral = columns.getJSONObject(13).getString("v");
                String perfil = columns.getJSONObject(16).getString("v");
                String corre_personal = columns.getJSONObject(11).getString("v");
                String proyecto_asignado = columns.getJSONObject(14).getString("v");
                String expertise = columns.getJSONObject(15).getString("v");
                int primer_nivel = columns.getJSONObject(17).getInt("v");
                int segundo_nivel = columns.getJSONObject(18).getInt("v");
                int tercer_nivel = columns.getJSONObject(19).getInt("v");
                ColaboradorModel m = new ColaboradorModel(id, no_empleado, nombre, apellido_paterno, apellido_materno, usuarioxm, correobbva, edificio, ubicacion, area_laboral, perfil, celular_personal, corre_personal, proyecto_asignado, expertise, primer_nivel, segundo_nivel, tercer_nivel);
                items.add(m);
                Utils.log("COLABORADOR INFO", id + " " + no_empleado + " " + nombre + " " + apellido_paterno + " " + apellido_materno + " " + usuarioxm + " " + correobbva + " " + ubicacion + " " + edificio + " " + perfil + " " + celular_personal);
            }
            ColaboradoresAdapter adaptador = new ColaboradoresAdapter(this, new ArrayList<>(items));
            recycler.setAdapter(adaptador);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                execute();
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(ColaboradoresController.this, Init.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Proyecta una {@link Snackbar} con el string usado
     *
     * @param msg Mensaje
     */
    private void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id.coordinator), msg, Snackbar.LENGTH_LONG)
                .show();
    }

    private void showSnackBarAction(String msg) {
        snackbar = Snackbar.make(findViewById(R.id.coordinator), msg, Snackbar.LENGTH_INDEFINITE)
                .setAction("REINTENTAR", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        execute();
                        snackbar.dismiss();
                    }
                });
        snackbar.show();
    }
}