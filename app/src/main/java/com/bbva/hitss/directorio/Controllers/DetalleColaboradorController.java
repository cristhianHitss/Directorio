package com.bbva.hitss.directorio.Controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bbva.R;
import com.bbva.hitss.directorio.Controllers.UI.ToastController;
import com.bbva.hitss.directorio.Services.Google.AsyncResultService;
import com.bbva.hitss.directorio.Services.Google.GoogleSpreadsheetResponseService;
import com.bbva.hitss.directorio.Services.SpreadsheetDataService.Statements;
import com.bbva.hitss.directorio.Models.ColaboradorModel;
import com.bbva.hitss.directorio.Models.InternosModel;
import com.bbva.hitss.directorio.Utils.PhoneUtils;
import com.bbva.hitss.directorio.Utils.UI.CircleTransformation;
import com.bbva.hitss.directorio.Utils.UI.RevealBackgroundView;
import com.bbva.hitss.directorio.Utils.Utils;
import com.bbva.hitss.directorio.Utils.WhatsappUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hitss on 10/03/2016.
 */

public class DetalleColaboradorController extends AppCompatActivity implements RevealBackgroundView.OnStateChangeListener {

    Snackbar snackbar;
    View header_containerv;
    View contentphone;
    View r_hdr_title;
    TabLayout tlUserProfileTabs;
    ImageView ivUserProfilePhoto;
    ImageView vUserProfileRoot;
    private int avatarSize;
    private String profilePhoto;
    private TextView user_name;
    private TextView usuario_xm;
    private TextView no_empleado;
    private TextView ubicacion;
    private TextView area_laboral;
    private TextView name_interno2;
    private TextView list_titleb;
    private TextView list_titlebb;
    private TextView list_title;
    private TextView name_interno1;
    private TextView description1;
    private TextView celular_personal;
    private TextView list_titlea;
    private TextView description2;
    private TextView name_interno3;
    private TextView description3;
    private ImageView send_message;
    private ImageView send_whatsapp;
    private static final String EXTRA_NAME = "com.bbva.hitss.name";
    private static final String EXTRA_APELLIDO_PATERNO = "com.bbva.hitss.paterno";
    private static final String EXTRA_APELLIDO_MATERNO = "com.bbva.hitss.materno";
    private static final String EXTRA_EDIFICIO = "com.bbva.hitss.edificio";
    private static final String EXTRA_UBICACION = "com.bbva.hitss.ubicacion";
    private static final String EXTRA_USUARIO_XM = "com.bbva.hitss.xm";
    private static final String EXTRA_PERFIL = "com.bbva.hitss.perfil";
    private static final String EXTRA_NO_EMPLEADO = "com.bbva.hitss.no_empleado";
    private static final String EXTRA_AREA_LABORAL = "com.bbva.hitss.area_laboral";
    private static final String EXTRA_CELULAR_PERSONAL = "com.bbva.hitss.celular_personal";
    private static final String EXTRA_CORREO_BBVA = "com.bbva.hitss.correo_bbva";
    private static final String EXTRA_CORREO_PERSONAL = "com.bbva.hitss.correo_personal";
    private static final String EXTRA_PROYECTO_ASIGNADO = "com.bbva.hitss.proyecto_asignado";
    private static final String EXTRA_EXPERTISE = "com.bbva.hitss.expertise";
    private static final String EXTRA_PRIMER_NIVEL = "com.bbva.hitss.primer_nivel";
    private static final String EXTRA_SEGUNDO_NIVEL = "com.bbva.hitss.segundo_nivel";
    private static final String EXTRA_TERCER_NIVEL = "com.bbva.hitss.tercer_nivel";
    private static final String EXTRA_DRAWABLE = "com.bbva.hitss.drawable";
    private LinearLayout content_view;
    private RelativeLayout make_call;
    private RelativeLayout mail_work;
    private RelativeLayout mail_personal;
    private View contact;
    private View extrainfo_view;
    private View contentmail;
    private View matriz_view;
    private ProgressBar pgr_internos;
    private InternosModel internosModel;
    FloatingActionButton fab;

    public static void createInstance(Activity activity, ColaboradorModel colaborador) {
        Intent intent = getLaunchIntent(activity, colaborador);
        activity.startActivity(intent);
    }

    public static Intent getLaunchIntent(Context context, ColaboradorModel colaboradorModel) {
        Intent intent = new Intent(context, DetalleColaboradorController.class);
        intent.putExtra(EXTRA_NAME, colaboradorModel.getName());
        intent.putExtra(EXTRA_APELLIDO_PATERNO, colaboradorModel.getApellido_paterno());
        intent.putExtra(EXTRA_APELLIDO_MATERNO, colaboradorModel.getApelido_materno());
        intent.putExtra(EXTRA_USUARIO_XM, colaboradorModel.getUsuarioxm());
        intent.putExtra(EXTRA_PERFIL, colaboradorModel.getPerfil());
        intent.putExtra(EXTRA_NO_EMPLEADO, colaboradorModel.getNo_empleado());
        intent.putExtra(EXTRA_EDIFICIO, colaboradorModel.getEdificio());
        intent.putExtra(EXTRA_UBICACION, colaboradorModel.getUbicacion());
        intent.putExtra(EXTRA_AREA_LABORAL, colaboradorModel.getArea_laboral());
        intent.putExtra(EXTRA_CELULAR_PERSONAL, colaboradorModel.getCelular_personal());
        intent.putExtra(EXTRA_CORREO_BBVA, colaboradorModel.getCorreobbva());
        intent.putExtra(EXTRA_CORREO_PERSONAL, colaboradorModel.getCorreo_personal());
        intent.putExtra(EXTRA_PROYECTO_ASIGNADO, colaboradorModel.getProyecto_asignado());
        intent.putExtra(EXTRA_EXPERTISE, colaboradorModel.getExpertise());
        intent.putExtra(EXTRA_PRIMER_NIVEL, colaboradorModel.getPrimer_nivel());
        intent.putExtra(EXTRA_SEGUNDO_NIVEL, colaboradorModel.getSegundo_nivel());
        intent.putExtra(EXTRA_TERCER_NIVEL, colaboradorModel.getTercer_nivel());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colaborador_detalle_view);
        setToolbar();
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setfields();
        setdata(getIntent());
        setupTabs();
        tabfunction();
        getInternos();
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void getInternos() {
        new GoogleSpreadsheetResponseService(new AsyncResultService() {
            @Override
            public void onResult(JSONObject object) {
                if (object.toString().equalsIgnoreCase("{}")) {
                    showSnackBarAction("ERROR DE CONEXION");
                    Utils.log("ERROR DE CONEXION", "Ubo un error de conexion");
                    pgr_internos.setVisibility(View.INVISIBLE);
                } else {
                    processJson(object);
                    pgr_internos.setVisibility(View.INVISIBLE);
                }
            }
        }).execute(Statements.internosBBVA);
    }

    private void processJson(JSONObject object) {
        List<InternosModel> internosModels = new ArrayList<>();
        try {
            JSONArray rows = object.getJSONArray("rows");
            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");
                internosModel = new InternosModel();
                internosModel.setId(columns.getJSONObject(0).getInt("v"));
                internosModel.setM(columns.getJSONObject(1).getString("v"));
                internosModel.setNombre(columns.getJSONObject(2).getString("v"));
                internosModel.setApellido_paterno(columns.getJSONObject(3).getString("v"));
                internosModel.setApellido_materno(columns.getJSONObject(4).getString("v"));
                internosModel.setExtension(columns.getJSONObject(5).getInt("v"));
                internosModel.setMovil(columns.getJSONObject(6).getLong("v"));
                internosModel.setCorreo(columns.getJSONObject(7).getString("v"));
                internosModel.setUbicacion(columns.getJSONObject(8).getString("v"));
                internosModel.setEdificio(columns.getJSONObject(9).getString("v"));
                internosModel.setPuesto(columns.getJSONObject(10).getString("v"));
                internosModel.setRol(columns.getJSONObject(11).getString("v"));
                internosModel.setSubdireccion(columns.getJSONObject(12).getString("v"));
                internosModel.setDireccion(columns.getJSONObject(13).getString("v"));
                internosModel.setSupervisor_inmediato(columns.getJSONObject(14).getInt("v"));
                internosModels.add(internosModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setdatainternos(internosModels);

    }

    private void setupTabs() {
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_contacts_info));
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_dependency));
        tlUserProfileTabs.addTab(tlUserProfileTabs.newTab().setIcon(R.drawable.ic_extra_info));
        content_view.getChildAt(1).setVisibility(View.INVISIBLE);
        content_view.getChildAt(2).setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStateChange(int state) {
        if (RevealBackgroundView.STATE_FINISHED == state) {
            tlUserProfileTabs.setVisibility(View.VISIBLE);
            vUserProfileRoot.setVisibility(View.VISIBLE);
        } else {
            tlUserProfileTabs.setVisibility(View.INVISIBLE);
            vUserProfileRoot.setVisibility(View.INVISIBLE);
        }
    }


    private void setfields() {
        usuario_xm = (TextView) findViewById(R.id.usuario_xm);
        no_empleado = (TextView) findViewById(R.id.no_empleado);
        ubicacion = (TextView) findViewById(R.id.ubicacion);
        area_laboral = (TextView) findViewById(R.id.area_laboral);
        ivUserProfilePhoto = (ImageView) findViewById(R.id.ivUserProfilePhoto);
        tlUserProfileTabs = (TabLayout) findViewById(R.id.tlUserProfileTabs);
        user_name = (TextView) findViewById(R.id.user_name);
        content_view = (LinearLayout) findViewById(R.id.contentView);
        contact = findViewById(R.id.infocontacto);
        matriz_view = findViewById(R.id.matriz);
        extrainfo_view = findViewById(R.id.extrainfocontacto);
        celular_personal = (TextView) contact.findViewById(R.id.list_titlec);
        header_containerv = contact.findViewById(R.id.header_containerv);
        contentphone = contact.findViewById(R.id.contentphone);
        r_hdr_title = contact.findViewById(R.id.r_hdr_title);
        contentmail = contact.findViewById(R.id.contentmail);
        list_titlea = (TextView) contact.findViewById(R.id.list_titlea);
        list_titleb = (TextView) contact.findViewById(R.id.list_titleb);
        list_titlebb = (TextView) extrainfo_view.findViewById(R.id.list_titleb);
        list_title = (TextView) extrainfo_view.findViewById(R.id.list_title);
        make_call = (RelativeLayout) contact.findViewById(R.id.make_call);
        mail_work = (RelativeLayout) contact.findViewById(R.id.mail_work);
        send_message = (ImageView) contact.findViewById(R.id.send_message);
        send_whatsapp = (ImageView) contact.findViewById(R.id.send_whatsapp);
        this.avatarSize = getResources().getDimensionPixelSize(R.dimen.user_profile_avatar_size);
        pgr_internos = (ProgressBar) matriz_view.findViewById(R.id.pgr_internos);
        name_interno1 = (TextView) matriz_view.findViewById(R.id.list_titlec);
        description1 = (TextView) matriz_view.findViewById(R.id.list_descc);
        name_interno2 = (TextView) matriz_view.findViewById(R.id.name2);
        description2 = (TextView) matriz_view.findViewById(R.id.desc2);
        name_interno3 = (TextView) matriz_view.findViewById(R.id.name3);
        description3 = (TextView) matriz_view.findViewById(R.id.desc3);
        mail_personal = (RelativeLayout) contact.findViewById(R.id.mail_personal);
    }

    private void setdata(Intent i) {
        fab = (FloatingActionButton) findViewById(R.id.btnCreate);
        final Long celular = i.getLongExtra(EXTRA_CELULAR_PERSONAL, 0);
        boolean exist = PhoneUtils.isNumberInContacts(DetalleColaboradorController.this, celular + "");
        if (exist) {
            fab.setVisibility(View.GONE);
        }
        Picasso.with(this)
                .load(profilePhoto)
                .placeholder(R.drawable.ic_login_account)
                .resize(avatarSize, avatarSize)
                .centerCrop()
                .transform(new CircleTransformation())
                .into(ivUserProfilePhoto);
        final String name = i.getStringExtra(EXTRA_NAME) + " " + i.getStringExtra(EXTRA_APELLIDO_PATERNO) + " " + i.getStringExtra(EXTRA_APELLIDO_MATERNO);
        user_name.setText(name);
        usuario_xm.setText(i.getStringExtra(EXTRA_USUARIO_XM));
        no_empleado.setText(i.getIntExtra(EXTRA_NO_EMPLEADO, 0) + "");
        ubicacion.setText(i.getStringExtra(EXTRA_EDIFICIO) + "  -  " + i.getStringExtra(EXTRA_UBICACION));
        area_laboral.setText(i.getStringExtra(EXTRA_AREA_LABORAL));
        celular_personal.setText(i.getLongExtra(EXTRA_CELULAR_PERSONAL, 0) + "");
        list_titlea.setText(i.getStringExtra(EXTRA_CORREO_BBVA));
        final String corre_bbva = i.getStringExtra(EXTRA_CORREO_BBVA);
        final String corre_personal = i.getStringExtra(EXTRA_CORREO_PERSONAL);
        list_titleb.setText(i.getStringExtra(EXTRA_CORREO_PERSONAL));
        list_titlebb.setText(i.getStringExtra(EXTRA_PROYECTO_ASIGNADO));
        list_title.setText(i.getStringExtra(EXTRA_EXPERTISE));
        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean insert = PhoneUtils.insertContact(DetalleColaboradorController.this, name, celular + "");
                        if (insert) {
                            ToastController.showSimple(DetalleColaboradorController.this, " Contacto Agregado Exitosamente", Toast.LENGTH_LONG);
                            fab.setVisibility(View.GONE);
                        } else {
                            ToastController.showSimple(DetalleColaboradorController.this, " Error inesperado", Toast.LENGTH_LONG);
                        }

                    }
                }
        );
        make_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneUtils.makePhoneCall(DetalleColaboradorController.this, celular + "");
            }
        });
        make_call.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PhoneUtils.copyTextToClipboard(celular + "", DetalleColaboradorController.this, "  copiado exitosamente !");
                return false;
            }
        });
        final String[] send_to = {celular + ""};
        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneUtils.sendSMS(DetalleColaboradorController.this, send_to, "");
            }
        });
        send_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WhatsappUtils.sendWhatsapp(DetalleColaboradorController.this, celular + "", name);
            }
        });
        final String[] mail_to = {corre_bbva};
        mail_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneUtils.sendEmail(DetalleColaboradorController.this, mail_to, "", "");
            }
        });
        mail_work.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PhoneUtils.copyTextToClipboard(corre_bbva, DetalleColaboradorController.this, "  copiado exitosamente !");
                return false;
            }
        });
        final String[] mail_to_personal = {corre_personal};
        mail_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneUtils.sendEmail(DetalleColaboradorController.this, mail_to_personal, "", "");
            }
        });
        mail_personal.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PhoneUtils.copyTextToClipboard(corre_personal, DetalleColaboradorController.this, "  copiado exitosamente !");
                return false;
            }
        });
    }

    private void tabfunction() {
        tlUserProfileTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                if (pos == 1) {
                    content_view.getChildAt(1).setVisibility(View.VISIBLE);
                    content_view.getChildAt(0).setVisibility(View.GONE);
                    content_view.getChildAt(2).setVisibility(View.GONE);

                } else if (pos == 0) {
                    content_view.getChildAt(0).setVisibility(View.VISIBLE);
                    content_view.getChildAt(1).setVisibility(View.GONE);
                    content_view.getChildAt(2).setVisibility(View.GONE);
                } else {
                    content_view.getChildAt(2).setVisibility(View.VISIBLE);
                    content_view.getChildAt(0).setVisibility(View.GONE);
                    content_view.getChildAt(1).setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tlUserProfileTabs.setVisibility(View.VISIBLE);
        if (Utils.datascreendisplay(getWindowManager()) == 240) {
            contentphone.setVisibility(View.GONE);
            header_containerv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (contentphone.getVisibility() == View.GONE) {
                        contentphone.setVisibility(View.VISIBLE);
                        contentmail.setVisibility(View.GONE);
                    }
                }
            });
            r_hdr_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (contentmail.getVisibility() == View.GONE) {
                        contentmail.setVisibility(View.VISIBLE);
                        contentphone.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void showSnackBarAction(String msg) {
        snackbar = Snackbar.make(findViewById(R.id.detallecolaborador_view), msg, Snackbar.LENGTH_INDEFINITE)
                .setAction("REINTENTAR", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getInternos();
                        snackbar.dismiss();
                    }
                });
        snackbar.show();
    }

    private void setdatainternos(List<InternosModel> internosModels) {
        int er_niv = getIntent().getIntExtra(EXTRA_PRIMER_NIVEL, 0);
        int seg_niv = getIntent().getIntExtra(EXTRA_SEGUNDO_NIVEL, 0);
        int ter_niv = getIntent().getIntExtra(EXTRA_TERCER_NIVEL, 0);
        for (InternosModel model : internosModels) {
            if (model.getId() == er_niv) {
                name_interno1.setText(model.getNombre() + " " + model.getApellido_paterno() + " " + model.getApellido_materno());
                description1.setText("Puesto: " + model.getPuesto() + "        Ext." + model.getExtension() + "        Piso: " + model.getUbicacion());
            }
            if (model.getId() == seg_niv) {
                name_interno2.setText(model.getNombre() + " " + model.getApellido_paterno() + " " + model.getApellido_materno());
                description2.setText("Puesto: " + model.getPuesto() + "        Ext." + model.getExtension() + "        Piso: " + model.getUbicacion());
            }
            if (model.getId() == ter_niv) {
                name_interno3.setText(model.getNombre() + " " + model.getApellido_paterno() + " " + model.getApellido_materno());
                description3.setText("Puesto: " + model.getPuesto() + "        Ext." + model.getExtension() + "        Piso: " + model.getUbicacion());
            }
        }
    }
}

