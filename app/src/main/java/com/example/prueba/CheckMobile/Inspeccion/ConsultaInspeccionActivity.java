package com.example.prueba.CheckMobile.Inspeccion;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.Actualizaciones.FiltroAccesoriosActivity;
import com.example.prueba.CheckMobile.Actualizaciones.FiltroLucesActivity;
import com.example.prueba.CheckMobile.Actualizaciones.myDialogLadosVehiculo;
import com.example.prueba.CheckMobile.LucesParametros.ListaLuces;
import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.VehiculoDocumento.AdapterVehiculoDocumento;
import com.example.prueba.CheckMobile.VehiculoDocumento.VehiculoDocumento;
import com.example.prueba.CheckMobile.VehiculoDocumento.VehiculoDocumentoResponse;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.data;
import static android.R.attr.id;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.example.prueba.CheckMobile.R.id.imageView;
import static com.example.prueba.CheckMobile.R.id.listaLuces;
import static com.example.prueba.CheckMobile.R.id.visible;
import static java.lang.Integer.parseInt;
import static java.security.AccessController.getContext;
import static java.util.Collections.addAll;


public class ConsultaInspeccionActivity extends AppCompatActivity implements myDialogLadosVehiculo.OnCompleteListener {

    EditText etxtInspeccion;
    TextView etxtVehiculo;
    TextView etxtCliente;
    EditText etxtMotor;
    EditText etxtKilometraje;
    EditText etxtFecha;
    EditText etxtSerieGoma;
    EditText etxtObservacion;
    TextView etxtCombustible;
    TextView etxtNivelAceite;
    TextView mensajeLadosVehiculo;
    RadioButton llaveInteligente;
    RadioButton llaveNormal;
    RadioGroup alfombra1;
    RadioGroup alfombra2;
    RadioGroup alfombra3;
    LinearLayout layoutLuces;
    GridLayout grAccesorios;
    GridLayout grCantidades;
    ListView grLadoVehiculo;
    SeekBar seekCombustible;
    SeekBar seekAceite;
    ImageButton btnAgregarLuces;
    TextView txtinfoLuces;
    ImageButton btnAgregarAccesorios;
    TextView txtInfoAccesorios;
    ImageButton btnTomarFoto;
    TextView txtInfoCamara;

    private int idInspeccion;
    private String nombreVehiculo;
    private String nombreCliente;
    private String motor;
    private String kilometraje;
    private String serieGomas;
    private String fechaInspeccion;
    private String observaciones;
    private String idVehiculo;
    String tipoLlave;
    String combustible, condicionAlfombra1 = null, condicionAlfombra2 = null, condicionAlfombra3 = null;
    int aceite;
    private boolean buscar = false, actualizar = false, consultar = false;
    List<String> idluces = new ArrayList<>();
    List<String> descLuces = new ArrayList<>();
    List<String> accesorios = new ArrayList<>();
    List<String> idAccesorios = new ArrayList<>();
    List<String> cantidades = new ArrayList<>();
    List<String> elementosCantidades = new ArrayList<>();
    List<String> idElementoCantidades = new ArrayList<>();
    ArrayList<VehiculoDocumento> listaDocumentos;
    String descripcionLado;
    final int CAMERA_REQUEST = 13323;
    private Uri imageUri;
    CameraPhoto cameraPhoto;
    int idPictureLados;
    int requestCode = 1, requestCode2 = 2;
    private Timer timer = new Timer();
    private final long DELAY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_inspeccion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCInspeccion);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        inicializacionVariables();
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        if (extra != null) {
            idInspeccion = extra.getInt("IDINSPECCION");
            nombreVehiculo = extra.getString("VEHICULO");
            idVehiculo = extra.getString("IDVEHICULO");
            nombreCliente = extra.getString("CLIENTE");
            motor = extra.getString("MOTOR");
            kilometraje = extra.getString("KILOMETRAJE");
            serieGomas = extra.getString("SERIEGOMAS");
            fechaInspeccion = extra.getString("FECHA");
            observaciones = extra.getString("OBSERVACION");
            buscar = extra.getBoolean("BUSCAR");
            actualizar = extra.getBoolean("ACTUALIZAR");
            consultar = extra.getBoolean("CONSULTAR");
            if (actualizar) {
                btnAgregarLuces.setVisibility(View.VISIBLE);
                btnAgregarAccesorios.setVisibility(View.VISIBLE);
                txtinfoLuces.setVisibility(View.VISIBLE);
                txtInfoAccesorios.setVisibility(View.VISIBLE);
                seekCombustible.setVisibility(View.VISIBLE);
                seekAceite.setVisibility(View.VISIBLE);
                btnTomarFoto.setVisibility(View.VISIBLE);
                txtInfoCamara.setVisibility(View.VISIBLE);
                habilitarVistas(true);
                buscarBotonEvents();
                etxtMotor.setFocusable(true);
                this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                removerDocumentoVehiculo();
                btnTomarFoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialogLadosVehiculo myDialogLadosVehiculo = new myDialogLadosVehiculo();
                        myDialogLadosVehiculo.show(getFragmentManager(), "Lados");
                    }
                });
            }
        }

        if (!buscar && (actualizar || consultar)) {
            llenarFormulario();
        } else if (buscar && !actualizar && !consultar) {
            ObtenerDatosInspeccionEnc(String.valueOf(idInspeccion));
        }

        ObtenerDatosInspeccion(idInspeccion);
        ObtenerDatosVehiculoDocumento(idInspeccion);
        cameraPhoto = new CameraPhoto(getApplicationContext());
    }

    private void removerDocumentoVehiculo() {
        grLadoVehiculo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VehiculoDocumento idItem = (VehiculoDocumento) grLadoVehiculo.getAdapter().getItem(i);

                for (int j = 0; j < listaDocumentos.size(); j++) {
                    if (idItem.getId_lado() == listaDocumentos.get(j).getId_lado()) {
                        listaDocumentos.remove(i);
                    }
                }

                AdapterLados adapter = new AdapterLados(getApplicationContext(), listaDocumentos);
                grLadoVehiculo.setAdapter(adapter);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == this.requestCode) {
            if (data != null) {
                List<String> dataidLuces = new ArrayList<>();
                List<String> dataDescripcionLuces = new ArrayList<>();

                dataidLuces.addAll(data.getStringArrayListExtra("IDLUCES"));
                dataDescripcionLuces.addAll(data.getStringArrayListExtra("DESCLUCES"));

                if (!dataidLuces.isEmpty() && !dataDescripcionLuces.isEmpty()) {
                    for (int i = 0; i < dataidLuces.size(); i++) {
                        if (idluces.contains(dataidLuces.get(i))) {
                            Toast.makeText(getApplicationContext(), "Este elemento ya fue agregado antes", Toast.LENGTH_SHORT).show();
                        } else {
                            idluces.add(dataidLuces.get(i));
                            descLuces.add(dataDescripcionLuces.get(i));


                        }
                    }

                    layoutLuces.removeAllViews();
                    for (int j = 0; j < idluces.size(); j++) {
                        CheckBox checkLuces = new CheckBox(layoutLuces.getContext());
                        checkLuces.setText(descLuces.get(j));
                        checkLuces.setTextSize(20);
                        checkLuces.setChecked(true);
                        checkLuces.setId(parseInt(idluces.get(j)));
                        layoutLuces.addView(checkLuces);

                        checkLuces.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (!b) {
                                    layoutLuces.removeView(compoundButton);
                                    idluces.remove(String.valueOf(compoundButton.getId()));
                                    descLuces.remove(compoundButton.getText().toString());

                                }
                            }

                        });

                    }

                }

            }
        } else if (resultCode == 2) {
            List<String> dataidAccesorios = new ArrayList<>();
            List<String> dataDescAccesorios = new ArrayList<>();

            dataidAccesorios.addAll(data.getStringArrayListExtra("IDACCESORIOS"));
            dataDescAccesorios.addAll(data.getStringArrayListExtra("DESCACCESORIOS"));

            if (!dataidAccesorios.isEmpty() && !dataDescAccesorios.isEmpty()) {
                for (int i = 0; i < dataidAccesorios.size(); i++) {
                    if (idAccesorios.contains(dataidAccesorios.get(i))) {
                        Toast.makeText(getApplicationContext(), "Este elemento ya ha sido agregado antes", Toast.LENGTH_SHORT).show();
                    } else {
                        idAccesorios.add(dataidAccesorios.get(i));
                        accesorios.add(dataDescAccesorios.get(i));
                    }
                }

                grAccesorios.removeAllViews();
                for (int j = 0; j < idAccesorios.size(); j++) {
                    CheckBox checkAccesorios = new CheckBox(grAccesorios.getContext());
                    checkAccesorios.setText(accesorios.get(j));
                    checkAccesorios.setId(parseInt(idAccesorios.get(j)));
                    checkAccesorios.setChecked(true);
                    checkAccesorios.setTextSize(20);
                    grAccesorios.addView(checkAccesorios);

                    checkAccesorios.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (!b) {
                                grAccesorios.removeView(compoundButton);
                                accesorios.remove(compoundButton.getText().toString());
                                idAccesorios.remove(String.valueOf(compoundButton.getId()));
                            }
                        }
                    });

                }
            }
        }


        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            String PhotoPath = cameraPhoto.getPhotoPath();

            for (int i = 0; i < listaDocumentos.size(); i++) {
                if (listaDocumentos.get(i).getId_lado().equals(String.valueOf(idPictureLados))) {
                    listaDocumentos.get(i).setRutaDocumento(PhotoPath);
                    break;
                } else {
                    if (i == listaDocumentos.size() - 1) {
                        listaDocumentos.add(new VehiculoDocumento(
                                idVehiculo,
                                PhotoPath,
                                String.valueOf(idInspeccion),
                                descripcionLado,
                                String.valueOf(idPictureLados)));
                    }
                }
            }

            AdapterLados adapter = new AdapterLados(getApplicationContext(), listaDocumentos);
            grLadoVehiculo.setAdapter(adapter);
        }
    }

    private void buscarBotonEvents() {

        btnAgregarLuces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConsultaInspeccionActivity.this, FiltroLucesActivity.class);
                startActivityForResult(intent, requestCode);
            }
        });


        btnAgregarAccesorios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConsultaInspeccionActivity.this, FiltroAccesoriosActivity.class);
                startActivityForResult(intent, requestCode2);
            }
        });

        seekCombustible.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                etxtCombustible.setText(seekBar.getProgress() + "/" + seekBar.getMax());
                combustible = String.valueOf(seekBar.getProgress());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekAceite.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                etxtNivelAceite.setText(seekBar.getProgress() + "/" + seekBar.getMax());
                aceite = seekBar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        alfombra1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rbCBien: {
                        condicionAlfombra1 = "9";
                        break;
                    }
                    case R.id.rbCNoBien: {
                        condicionAlfombra1 = "12";
                        break;
                    }
                }
            }
        });
        alfombra2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rbCGenuina: {
                        condicionAlfombra1 = "10";
                        break;
                    }
                    case R.id.rbCNoGenuina: {
                        condicionAlfombra1 = "13";
                        break;
                    }
                }
            }
        });

        alfombra3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rbCSoloUna: {
                        condicionAlfombra1 = "11";
                        break;
                    }
                    case R.id.rbCSDosOMas: {
                        condicionAlfombra1 = "14";
                        break;
                    }
                }
            }
        });

        llaveInteligente.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                tipoLlave = "1";
            }
        });

        llaveNormal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                tipoLlave = "2";
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (actualizar) {
            getMenuInflater().inflate(R.menu.menu_main_actualizar, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_update) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ConsultaInspeccionActivity.this);
            alertBuilder.setMessage("¿Está seguro de actualizar los datos?")
                    .setCancelable(false)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            actualizarInspeccion();
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

            AlertDialog alert = alertBuilder.create();
            alert.setTitle("Actualizar");
            alert.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert));
            alert.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void actualizarInspeccion() {

        ArrayList<InspeccionVehiculo> inspeccion = new ArrayList<InspeccionVehiculo>();

        inspeccion.add(new InspeccionVehiculo(
                String.valueOf(idInspeccion),
                etxtSerieGoma.getText().toString(),
                combustible,
                etxtObservacion.getText().toString(),
                etxtKilometraje.getText().toString(),
                etxtMotor.getText().toString()
        ));


        Call<String> callUpdate = AdapterInspeccion.setUpdateInspeccionVeh().setupdateInspeccion(inspeccion);
        callUpdate.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body().equals("200")) {
                        actualizarInspeccionDetalle();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error al actualizar inspección", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error de respuesta " + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ACTUALIZAR==>", t.getMessage());
            }
        });


    }

    private void actualizarInspeccionDetalle() {

        ArrayList<InspeccionVehiculoDetalle> inspeccionDetalle = new ArrayList<InspeccionVehiculoDetalle>();
        for (int i = 0; i < idluces.size(); i++) {
            inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                    String.valueOf(idInspeccion),
                    idVehiculo,
                    String.valueOf(idluces.get(i)),
                    descLuces.get(i),
                    "1",
                    "58"
            ));
        }

        for (int j = 0; j < idAccesorios.size(); j++) {
            inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                    String.valueOf(idInspeccion),
                    idVehiculo,
                    String.valueOf(idAccesorios.get(j)),
                    accesorios.get(j),
                    "1",
                    "60"
            ));
        }
        inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                String.valueOf(idInspeccion),
                idVehiculo,
                String.valueOf(0),
                "Condicion Alfrombra",
                condicionAlfombra1,
                "63"
        ));

        inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                String.valueOf(idInspeccion),
                idVehiculo,
                String.valueOf(1),
                "Condicion Alfrombra",
                condicionAlfombra2,
                "63"
        ));
        inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                String.valueOf(idInspeccion),
                idVehiculo,
                String.valueOf(2),
                "Condicion Alfrombra",
                condicionAlfombra3,
                "63"
        ));


        switch (tipoLlave) {

            case "1": {
                inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                        String.valueOf(idInspeccion),
                        idVehiculo,
                        tipoLlave,
                        "Tipo llave Inteligente",
                        "15",
                        "59"
                ));
                break;
            }
            case "2": {
                inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                        String.valueOf(idInspeccion),
                        idVehiculo,
                        tipoLlave,
                        "Tipo llave Normal",
                        "16",
                        "59"
                ));
            }
        }

        switch (combustible) {

            case "0": {
                inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                        String.valueOf(idInspeccion),
                        idVehiculo,
                        "1",
                        "Nivel Combustible",
                        "6",
                        "64"
                ));
                break;
            }
            case "1": {
                inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                        String.valueOf(idInspeccion),
                        idVehiculo,
                        "1",
                        "Nivel Combustible",
                        "3",
                        "64"
                ));
                break;
            }
            case "2": {
                inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                        String.valueOf(idInspeccion),
                        idVehiculo,
                        "1",
                        "Nivel Combustible",
                        "4",
                        "64"
                ));
                break;
            }
            case "3": {
                inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                        String.valueOf(idInspeccion),
                        idVehiculo,
                        "1",
                        "Nivel Combustible",
                        "5",
                        "64"
                ));
                break;
            }
            default: {

                inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                        String.valueOf(idInspeccion),
                        idVehiculo,
                        "1",
                        "Nivel Combustible",
                        "8",
                        "64"
                ));
                break;
            }

        }

        inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                String.valueOf(idInspeccion),
                idVehiculo,
                "2",
                "Nivel Aceite",
                "22",
                "64",
                String.valueOf(aceite)

        ));


        for (int i = 0; i < idElementoCantidades.size(); i++) {
            switch (idElementoCantidades.get(i)) {
                case "1": {
                    inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                            String.valueOf(idInspeccion),
                            idVehiculo,
                            "1",
                            elementosCantidades.get(i),
                            "22",
                            "62",
                            cantidades.get(i)

                    ));
                    break;
                }
                case "2": {
                    inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                            String.valueOf(idInspeccion),
                            idVehiculo,
                            "2",
                            elementosCantidades.get(i),
                            "22",
                            "62",
                            cantidades.get(i)

                    ));
                    break;

                }
                case "3": {
                    inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                            String.valueOf(idInspeccion),
                            idVehiculo,
                            "3",
                            elementosCantidades.get(i),
                            "22",
                            "62",
                            cantidades.get(i)

                    ));
                    break;
                }
                case "4": {
                    inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                            String.valueOf(idInspeccion),
                            idVehiculo,
                            "4",
                            elementosCantidades.get(i),
                            "22",
                            "62",
                            cantidades.get(i)

                    ));
                    break;
                }
                case "5": {
                    inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                            String.valueOf(idInspeccion),
                            idVehiculo,
                            "5",
                            elementosCantidades.get(i),
                            "22",
                            "62",
                            cantidades.get(i)

                    ));
                    break;
                }
                case "6": {
                    inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                            String.valueOf(idInspeccion),
                            idVehiculo,
                            "6",
                            elementosCantidades.get(i),
                            "22",
                            "62",
                            cantidades.get(i)

                    ));

                }
            }
        }
        Call<String> callInspeccionDet = AdapterInspeccion.setInspeccionVehiculoDet().insertInspeccionDetalle(inspeccionDetalle);
        callInspeccionDet.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {

                    if (response.body().toString().equals("200")) {

                        if (listaDocumentos.isEmpty())
                        {
                        myDialogProgress dialogProgress = new myDialogProgress();
                        dialogProgress.show(getFragmentManager(), "Inspeccion");}
                        else
                        {
                            guardarVehiculoDocumento();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error al actualizar detalle de inspección", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v("Actualizacion==> ", t.getMessage());
                Toast.makeText(getApplicationContext(), "Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void guardarVehiculoDocumento() {

        Call<String> callVehiculoDocumento = AdapterVehiculoDocumento.getService().setVehiculoDocumentos(listaDocumentos);
        callVehiculoDocumento.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body().toString().equals("200")) {
                        myDialogProgress dialogProgress = new myDialogProgress();
                        dialogProgress.show(getFragmentManager(), "Inspeccion");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error al guardar datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v("Error insercion ** ", t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage() + " Error al insertar Inspeccion", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void ObtenerDatosInspeccionEnc(String valor) {
        Call<InspeccionVehiculo> call = AdapterInspeccion.getApiService("id_inspeccion", valor).getInspecciones();
        call.enqueue(new Callback<InspeccionVehiculo>() {
            @Override
            public void onResponse(Call<InspeccionVehiculo> call, Response<InspeccionVehiculo> response) {
                if (response.isSuccessful()) {
                    InspeccionVehiculoResponse inspeccion = response.body();
                    nombreCliente = inspeccion.getInspecciones().get(0).getNombre_cliente();
                    nombreVehiculo = inspeccion.getInspecciones().get(0).getNombre_vehiculo();
                    motor = inspeccion.getInspecciones().get(0).getMotor();
                    kilometraje = inspeccion.getInspecciones().get(0).getKilometraje();
                    observaciones = inspeccion.getInspecciones().get(0).getObservaciones();
                    fechaInspeccion = inspeccion.getInspecciones().get(0).getFechaInspeccion();
                    serieGomas = inspeccion.getInspecciones().get(0).getSerieGomas();
                    llenarFormulario();
                }
                Toast.makeText(getApplicationContext(), "Error " + response.errorBody(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<InspeccionVehiculo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.v("ENCABEZADO", t.getMessage());
            }
        });

    }


    private void ObtenerDatosVehiculoDocumento(int valor) {

        Call<VehiculoDocumento> callDocumento = AdapterVehiculoDocumento.getDocumentos("id_documento", String.valueOf(valor)).getVehiculoDocumento();
        callDocumento.enqueue(new DocumentoCallback());
    }

    private void habilitarVistas(boolean b) {
        etxtMotor.setEnabled(b);
        etxtKilometraje.setEnabled(b);
        etxtSerieGoma.setEnabled(b);
        etxtObservacion.setEnabled(b);
        llaveNormal.setEnabled(b);
        llaveInteligente.setEnabled(b);
        RadioButton r1 = (RadioButton) alfombra1.findViewById(R.id.rbCBien);
        r1.setEnabled(b);
        RadioButton r2 = (RadioButton) alfombra1.findViewById(R.id.rbCNoBien);
        r2.setEnabled(b);
        RadioButton r3 = (RadioButton) alfombra2.findViewById(R.id.rbCGenuina);
        r3.setEnabled(b);
        RadioButton r4 = (RadioButton) alfombra2.findViewById(R.id.rbCNoGenuina);
        r4.setEnabled(b);
        RadioButton r5 = (RadioButton) alfombra3.findViewById(R.id.rbCSoloUna);
        r5.setEnabled(b);
        RadioButton r6 = (RadioButton) alfombra3.findViewById(R.id.rbCSDosOMas);
        r6.setEnabled(b);

        //  seekAceite.setEnabled(true);
    }

    private void inicializacionVariables() {
        etxtInspeccion = (EditText) findViewById(R.id.etxtCNoInspeccion);
        etxtCliente = (TextView) findViewById(R.id.txtCDatosCliente);
        etxtVehiculo = (TextView) findViewById(R.id.txtCDatosVehiculo);
        etxtMotor = (EditText) findViewById(R.id.etxtCMotor);
        etxtKilometraje = (EditText) findViewById(R.id.etxtCKilometraje);
        etxtSerieGoma = (EditText) findViewById(R.id.etxtCSerieGomas);
        etxtFecha = (EditText) findViewById(R.id.etxtCFechaInspeccion);
        etxtObservacion = (EditText) findViewById(R.id.etxtCNotasInspeccion);
        etxtCombustible = (TextView) findViewById(R.id.textCCombustible);
        etxtNivelAceite = (TextView) findViewById(R.id.txtCProgressAceite);
        llaveInteligente = (RadioButton) findViewById(R.id.rbCLlaveSmart);
        llaveNormal = (RadioButton) findViewById(R.id.rbCLlaveNormal);
        alfombra1 = (RadioGroup) findViewById(R.id.rgCCondicion1);
        alfombra2 = (RadioGroup) findViewById(R.id.rgCCondicion2);
        alfombra3 = (RadioGroup) findViewById(R.id.rgCCondicion3);
        btnAgregarLuces = (ImageButton) findViewById(R.id.ibBuscarLuces);
        txtinfoLuces = (TextView) findViewById(R.id.txtInfoLuces);
        layoutLuces = (LinearLayout) findViewById(R.id.layoutCLuces);
        btnAgregarAccesorios = (ImageButton) findViewById(R.id.ibBuscarAccesorios);
        txtInfoAccesorios = (TextView) findViewById(R.id.txtInfoAccesorios);
        grAccesorios = (GridLayout) findViewById(R.id.gridAccesorios);
        grCantidades = (GridLayout) findViewById(R.id.gridCantidades);
        seekCombustible = (SeekBar) findViewById(R.id.seekBarCCombustible);
        seekAceite = (SeekBar) findViewById(R.id.seekBarCAceite);
        grLadoVehiculo = (ListView) findViewById(R.id.gridLadosVehiculo);
        mensajeLadosVehiculo = (TextView) findViewById(R.id.txtMensajeLados);
        btnTomarFoto = (ImageButton) findViewById(R.id.ibCamera);
        txtInfoCamara = (TextView) findViewById(R.id.txtMensajeCam);

    }

    private void llenarFormulario() {

        etxtInspeccion.setText("IV- " + idInspeccion);
        etxtCliente.setText(nombreCliente);
        etxtVehiculo.setText(nombreVehiculo);
        etxtMotor.setText(motor);
        etxtKilometraje.setText(kilometraje);
        etxtSerieGoma.setText(serieGomas);
        fechaInspeccion = fechaInspeccion.substring(0, 10);
        etxtFecha.setText(fechaInspeccion);
        etxtObservacion.setText(observaciones);

    }

    private void ObtenerDatosInspeccion(int valor) {
        Call<InspeccionVehiculoDetalle> callInspeccion = AdapterInspeccion.getInspeccionDetalle("id_inspeccion", String.valueOf(valor)).getInspeccionDetalle();
        callInspeccion.enqueue(new InspeccionCallBack());

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        try {
            dialog.dismiss();
            startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA_REQUEST);
            cameraPhoto.addToGallery();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Algo ha fallado al tomar Foto", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    @Override
    public void onSendIdLado(int id) {
        this.idPictureLados = id;
    }

    @Override
    public void onSendDescLado(String descripcion) {
        this.descripcionLado = descripcion;
    }

    private class InspeccionCallBack implements retrofit2.Callback<InspeccionVehiculoDetalle> {
        @Override
        public void onResponse(Call<InspeccionVehiculoDetalle> call, Response<InspeccionVehiculoDetalle> response) {
            if (response.isSuccessful()) {
                InspeccionVehDetalleResponse inspeccionResponse = response.body();
                if (inspeccionResponse.getResponseCode().equals("200")) {
                    if (!inspeccionResponse.getInspecciones().isEmpty()) {
                        llenarFormularioDetalles(inspeccionResponse.getInspecciones());
                    } else {
                        Toast.makeText(getApplicationContext(), "Inspeccion no tiene detalles", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "Error en el formato de respuesta de inspeccion", Toast.LENGTH_SHORT).show();
                Log.v("INSPECCION ==>", response.errorBody().toString());
            }
        }

        @Override
        public void onFailure(Call<InspeccionVehiculoDetalle> call, Throwable t) {
            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            Log.v("Aqui ===>", t.getMessage());

        }
    }

    private void llenarFormularioDetalles(ArrayList<InspeccionVehiculoDetalle> inspecciones) {

        for (InspeccionVehiculoDetalle var : inspecciones) {
            if (var.getId_lista_parametro() != null) {
                switch (var.getId_lista_parametro()) {
                    case "58": {
                        //Luces de vehiculo
                        idluces.add(var.getIdElementoInspeccion());
                        descLuces.add(var.getDesc__elemento());
                        CheckBox checkLuces = new CheckBox(layoutLuces.getContext());
                        checkLuces.setText(var.getDesc__elemento());
                        checkLuces.setTextSize(20);
                        checkLuces.setChecked(true);
                        checkLuces.setId(parseInt(var.getIdElementoInspeccion()));
                        layoutLuces.addView(checkLuces);
                        checkLuces.setEnabled(false);

                        if (actualizar) {
                            checkLuces.setEnabled(true);
                            checkLuces.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                    if (!b) {
                                        layoutLuces.removeView(compoundButton);
                                        idluces.remove(String.valueOf(compoundButton.getId()));
                                        descLuces.remove(compoundButton.getText().toString());

                                    }
                                }

                            });
                        }
                        break;
                    }
                    case "59": {
                        //llaves
                        tipoLlave = var.getIdRespuesta();
                        if (tipoLlave.equals("15")) {
                            llaveInteligente.setChecked(true);
                        } else {
                            llaveNormal.setChecked(true);
                        }
                        break;
                    }
                    case "60": {
                        //accesorios
                        accesorios.add(var.getDesc__elemento());
                        idAccesorios.add(var.getIdElementoInspeccion());
                        break;
                    }
                    case "62": {
                        //cantidades
                        cantidades.add(var.getPuntuacion());
                        elementosCantidades.add(var.getDesc__elemento());
                        idElementoCantidades.add(var.getIdElementoInspeccion());
                        break;
                    }
                    case "63": {
                        //condicion alfombra
                        if (var.getIdElementoInspeccion().equals("0")) {
                            condicionAlfombra1 = var.getIdRespuesta();
                        } else if (var.getIdElementoInspeccion().equals("1")) {
                            condicionAlfombra2 = var.getIdRespuesta();
                        } else {
                            condicionAlfombra3 = var.getIdRespuesta();
                        }
                        break;
                    }
                    case "64": {
                        //condicion combustible y nivel aceite
                        if (var.getIdElementoInspeccion().equals("1")) {
                            combustible = var.getDesc_respuesta();
                            etxtCombustible.setText(combustible);
                            if (actualizar) {
                                switch (var.getIdRespuesta()) {
                                    case "3": {
                                        seekCombustible.setProgress(1);
                                        break;
                                    }
                                    case "4": {
                                        seekCombustible.setProgress(2);
                                        break;
                                    }
                                    case "5": {
                                        seekCombustible.setProgress(3);
                                        break;
                                    }
                                    case "6": {
                                        seekCombustible.setProgress(4);
                                        break;
                                    }
                                }
                            }
                        } else {
                            aceite = parseInt(var.getPuntuacion());
                            etxtNivelAceite.setText(String.valueOf(aceite) + "/" + seekAceite.getMax());

                            if (actualizar) {
                                seekAceite.setProgress(aceite);
                            }
                        }
                        break;
                    }
                }
            }
        }

        grAccesorios.setRowCount(accesorios.size());
        for (int i = 0; i < accesorios.size(); i++) {
            CheckBox checkAccesorios = new CheckBox(grAccesorios.getContext());
            checkAccesorios.setText(accesorios.get(i));
            checkAccesorios.setId(parseInt(idAccesorios.get(i)));
            checkAccesorios.setChecked(true);
            checkAccesorios.setTextSize(20);
            grAccesorios.addView(checkAccesorios);
            checkAccesorios.setEnabled(false);

            if (actualizar) {
                checkAccesorios.setEnabled(true);
                checkAccesorios.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (!b) {
                            grAccesorios.removeView(compoundButton);
                            accesorios.remove(compoundButton.getText().toString());
                            idAccesorios.remove(String.valueOf(compoundButton.getId()));
                        }
                    }
                });

            }
        }

        grCantidades.setRowCount(cantidades.size());
        for (int i = 0; i < cantidades.size(); i++) {
            TextView textCantidades = new TextView(grCantidades.getContext());
            final EditText eCantidades = new EditText(grCantidades.getContext());
            textCantidades.setTextSize(18);
            textCantidades.setText(elementosCantidades.get(i) + ": ");
            eCantidades.setText(cantidades.get(i));
            eCantidades.setEnabled(false);
            eCantidades.setEms(4);
            eCantidades.setId(Integer.parseInt(idElementoCantidades.get(i)));
            eCantidades.setPadding(2, 0, 5, 0);
            grCantidades.addView(textCantidades);
            grCantidades.addView(eCantidades);

            if (actualizar) {
                eCantidades.setEnabled(true);
                eCantidades.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (timer != null)
                            timer.cancel();
                    }

                    @Override
                    public void afterTextChanged(final Editable editable) {
                        if (editable.length() >= 1) {
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            for (int i = 0; i < cantidades.size(); i++) {
                                                if (eCantidades.getId() == Integer.parseInt(idElementoCantidades.get(i))) {
                                                    cantidades.remove(i);
                                                    cantidades.add(i, editable.toString());
                                                }
                                            }

                                        }
                                    });
                                }

                            }, DELAY);
                        } else if (editable.length() == 0) {
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            for (int i = 0; i < cantidades.size(); i++) {
                                                if (eCantidades.getId() == Integer.parseInt(idElementoCantidades.get(i))) {
                                                    cantidades.remove(i);
                                                    cantidades.add(i, String.valueOf(0));
                                                }
                                            }

                                        }

                                    });


                                }
                            }, DELAY);
                        }
                    }
                });
            }
        }
        if (condicionAlfombra1.equals("9") && condicionAlfombra1 != null) {
            RadioButton r = (RadioButton) alfombra1.findViewById(R.id.rbCBien);
            r.setChecked(true);

        } else {
            RadioButton r = (RadioButton) alfombra1.findViewById(R.id.rbCNoBien);
            r.setChecked(true);
        }

        if (condicionAlfombra2.equals("10") && condicionAlfombra2 != null) {
            RadioButton r = (RadioButton) alfombra2.findViewById(R.id.rbCGenuina);
            r.setChecked(true);

        } else {
            RadioButton r = (RadioButton) alfombra2.findViewById(R.id.rbCNoGenuina);
            r.setChecked(true);

        }

        if (condicionAlfombra3.equals("11") && condicionAlfombra3 != null) {
            RadioButton r = (RadioButton) alfombra3.findViewById(R.id.rbCSoloUna);
            r.setChecked(true);

        } else {
            RadioButton r = (RadioButton) alfombra3.findViewById(R.id.rbCSDosOMas);
            r.setChecked(true);

        }

    }

    private class DocumentoCallback implements retrofit2.Callback<VehiculoDocumento> {
        @Override
        public void onResponse(Call<VehiculoDocumento> call, Response<VehiculoDocumento> response) {
            if (response.isSuccessful()) {
                VehiculoDocumentoResponse documentoResponse = response.body();
                if (documentoResponse.getResponseCode().equals("200")) {
                    //  Toast.makeText(getApplicationContext(), documentoResponse.getVehiculoDocumento().toString(), Toast.LENGTH_LONG).show();
                    if (documentoResponse.getVehiculoDocumento().isEmpty()) {
                        mensajeLadosVehiculo.setVisibility(View.VISIBLE);
                        grLadoVehiculo.setVisibility(View.GONE);

                    } else {
                        mensajeLadosVehiculo.setVisibility(View.GONE);
                        listaDocumentos = documentoResponse.getVehiculoDocumento();
                        AdapterLados adapter = new AdapterLados(getApplicationContext(), documentoResponse.getVehiculoDocumento());
                        grLadoVehiculo.setAdapter(adapter);
                    }
                }

            } else {
                Toast.makeText(getApplicationContext(), "Error en respuesta de documentos adjuntos", Toast.LENGTH_SHORT).show();
                Log.v("INSPECCION ==>", response.errorBody().toString());
            }
        }

        @Override
        public void onFailure(Call<VehiculoDocumento> call, Throwable t) {
            Toast.makeText(getApplicationContext(), "Error en el formato de respuesta de inspeccion", Toast.LENGTH_SHORT).show();
            Log.v("INSPECCION ==>", t.getMessage());
        }
    }
    private class AdapterLados extends ArrayAdapter<VehiculoDocumento> {

        private List<VehiculoDocumento> lista;

        public AdapterLados(Context context, List<VehiculoDocumento> documentos) {
            super(context, R.layout.row_lado_vehiculo, documentos);
            lista = documentos;
        }

        public View getView(int posicion, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.row_lado_vehiculo, null);
            File f = new File(lista.get(posicion).getRutaDocumento());

            ImageView imagen = (ImageView) item.findViewById(R.id.imageViewLado);
            TextView text = (TextView) item.findViewById(R.id.textViewLado);
            text.setText(lista.get(posicion).getNota());
            item.setId(Integer.parseInt(lista.get(posicion).getId_lado()));

            Picasso.with(getApplicationContext())
                    .load(f)
                    .error(R.mipmap.no_foto)
                    .into(imagen);

            return item;
        }
    }
}
