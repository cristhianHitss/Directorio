package com.bbva.hitss.directorio.Controllers;

/**
 * Created by Hitss on 10/03/2016.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import com.bbva.R;
import com.bbva.hitss.directorio.Adapters.ColaboradoresAdapter;
import com.bbva.hitss.directorio.Services.Google.AsyncResultService;
import com.bbva.hitss.directorio.Services.Google.GoogleSpreadsheetResponseService;
import com.bbva.hitss.directorio.Services.SpreadsheetDataService.Statements;
import com.bbva.hitss.directorio.Models.ColaboradorModel;
import com.bbva.hitss.directorio.Utils.Utils;
import com.bbva.hitss.directorio.init.MainApp;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class ColaboradoresController extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private ProgressBar bg_loader;
    Snackbar snackbar;
    private RecyclerView recycler;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayoutManager lManager;
    private CollapsingToolbarLayout collapser;
    List<ColaboradorModel> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colaborador_list_view);
        bg_loader = (ProgressBar) findViewById(R.id.bg_loader);
        setToolbar();// Añadir la Toolbar
        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        execute();
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        execute();
                        refreshLayout.setRefreshing(false);
                    }
                }
        );

    }

    public void execute() {
        bg_loader.setVisibility(View.VISIBLE);
        new GoogleSpreadsheetResponseService(new AsyncResultService() {
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
        }).execute(Statements.selectAll);
    }

    private void processJson(JSONObject object) {
        items = new ArrayList<>();
        try {
            JSONArray rows = object.getJSONArray("rows");
            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");
                ColaboradorModel colaboradorModel = new ColaboradorModel();
                colaboradorModel.setId(columns.getJSONObject(0).getInt("v"));
                colaboradorModel.setNo_empleado(columns.getJSONObject(1).getInt("v"));
                colaboradorModel.setName(columns.getJSONObject(2).getString("v"));
                colaboradorModel.setApellido_paterno(columns.getJSONObject(3).getString("v"));
                colaboradorModel.setApelido_materno(columns.getJSONObject(4).getString("v"));
                colaboradorModel.setUsuarioxm(columns.getJSONObject(5).getString("v"));
                colaboradorModel.setCorreobbva(columns.getJSONObject(6).getString("v"));
                colaboradorModel.setUbicacion(columns.getJSONObject(7).getString("v"));
                colaboradorModel.setEdificio(columns.getJSONObject(8).getString("v"));
                colaboradorModel.setCelular_personal(columns.getJSONObject(12).getLong("v"));
                colaboradorModel.setArea_laboral(columns.getJSONObject(13).getString("v"));
                colaboradorModel.setPerfil(columns.getJSONObject(16).getString("v"));
                colaboradorModel.setCorreo_personal(columns.getJSONObject(11).getString("v"));
                colaboradorModel.setProyecto_asignado(columns.getJSONObject(14).getString("v"));
                colaboradorModel.setExpertise(columns.getJSONObject(15).getString("v"));
                colaboradorModel.setPrimer_nivel(columns.getJSONObject(17).getInt("v"));
                colaboradorModel.setSegundo_nivel(columns.getJSONObject(18).getInt("v"));
                colaboradorModel.setTercer_nivel(columns.getJSONObject(19).getInt("v"));
                items.add(colaboradorModel);
                Utils.log("COLABORADOR INFO", colaboradorModel.toString());
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
        final MenuItem item = menu.findItem(R.id.sach);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }
                });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(ColaboradoresController.this, MainApp.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
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


    @Override
    public boolean onQueryTextChange(String newText) {
        final List<ColaboradorModel> filteredModelList = filter(items, newText);
        ColaboradoresAdapter adaptador = new ColaboradoresAdapter(this, new ArrayList<>(filteredModelList));
        recycler.setAdapter(adaptador);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<ColaboradorModel> filter(List<ColaboradorModel> models, String query) {
        query = query.toLowerCase();
        final List<ColaboradorModel> filteredModelList = new ArrayList<>();
        for (ColaboradorModel model : models) {
            final String text = model.getName().toLowerCase() + " " + model.getApellido_paterno().toLowerCase() + "" + model.getApelido_materno().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}