package com.example.prueba.CheckMobile.Inspeccion;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.example.prueba.CheckMobile.Actualizaciones.*;
import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.VehiculoDocumento.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.prueba.CheckMobile.Utils.Constantes.*;
import static java.lang.Integer.parseInt;


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

    List<String> idluces = new ArrayList<>();
    List<String> descLuces = new ArrayList<>();
    List<String> accesorios = new ArrayList<>();
    List<String> idAccesorios = new ArrayList<>();
    List<String> cantidades = new ArrayList<>();
    List<String> elementosCantidades = new ArrayList<>();
    List<String> idElementoCantidades = new ArrayList<>();
    ArrayList<VehiculoDocumento> listaDocumentos;

    private int idInspeccion;
    private String nombreVehiculo;
    private String nombreCliente;
    private String motor;
    private String kilometraje;
    private String serieGomas;
    private String fechaInspeccion;
    private String observaciones;
    private String idVehiculo;
    private String idCliente;
    private String chasis;
    private String referencia;
    private String tipoLlave;
    private String combustible;
    private String condicionAlfombra1 = null;
    private String condicionAlfombra2 = null;
    private String condicionAlfombra3 = null;
    private String placa;
    private String color;
    private String docIdentidad;
    private String telefono;
    private String celular;
    private int aceite;
    private boolean buscar = false;
    private boolean actualizar = false;
    private boolean consultar = false;
    private String descripcionLado;
    private String descLlave;
    private final int CAMERA_REQUEST = 13323;
    private CameraPhoto cameraPhoto;
    private int idPictureLados;
    private int requestCode = 1, requestCode2 = 2;
    private Timer timer = new Timer();
    private final long DELAY = 0;
    private static final String NOMBRE_CARPETA_APP = "proyecto.com.demoPdf";
    private static final String GENERADOS = "MisArchivos";

    String descAlfombra1, descAlfombra2, descAlfombra3;

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
            idCliente = extra.getString("IDCLIENTE");
            chasis = extra.getString("CHASIS");
            referencia = extra.getString("REFERENCIA");
            color = extra.getString("COLOR");
            placa = extra.getString("PLACA");
            docIdentidad = extra.getString("DOCIDENTIDAD");
            telefono = extra.getString("TELEFONO");
            celular = extra.getString("CELULAR");
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
                        RadioButton rb = (RadioButton) alfombra1.findViewById(i);
                        descAlfombra1 = rb.getText().toString();
                        break;
                    }
                    case R.id.rbCNoBien: {
                        condicionAlfombra1 = "12";
                        RadioButton rb = (RadioButton) alfombra1.findViewById(i);
                        descAlfombra1 = rb.getText().toString();
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
                        condicionAlfombra2 = "10";
                        RadioButton rb = (RadioButton) alfombra2.findViewById(i);
                        descAlfombra2 = rb.getText().toString();
                        break;
                    }
                    case R.id.rbCNoGenuina: {
                        condicionAlfombra2 = "13";
                        RadioButton rb = (RadioButton) alfombra2.findViewById(i);
                        descAlfombra2 = rb.getText().toString();
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
                        condicionAlfombra3 = "11";
                        RadioButton rb = (RadioButton) alfombra3.findViewById(i);
                        descAlfombra3 = rb.getText().toString();
                        break;
                    }
                    case R.id.rbCSDosOMas: {
                        condicionAlfombra3 = "14";
                        RadioButton rb = (RadioButton) alfombra3.findViewById(i);
                        descAlfombra3 = rb.getText().toString();
                        break;
                    }
                }
            }
        });

        llaveInteligente.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                tipoLlave = "1";
                descLlave = "Inteligente";

            }
        });

        llaveNormal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                tipoLlave = "2";
                descLlave = "Normal";
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
                    if (response.body().equals(RESPONSE_CODE_OK)) {
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
                Toast.makeText(getApplicationContext(), "Error update: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                    KEY_LISTA_PARAM_LUZ
            ));
        }

        for (int j = 0; j < idAccesorios.size(); j++) {
            inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                    String.valueOf(idInspeccion),
                    idVehiculo,
                    String.valueOf(idAccesorios.get(j)),
                    accesorios.get(j),
                    "1",
                    KEY_LISTA_PARAM_GENERALES
            ));
        }
        inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                String.valueOf(idInspeccion),
                idVehiculo,
                String.valueOf(0),
                "Condicion Alfrombra",
                condicionAlfombra1,
                KEY_LISTA_PARAM_CONDICION_ALFOMBRA
        ));

        inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                String.valueOf(idInspeccion),
                idVehiculo,
                String.valueOf(1),
                "Condicion Alfrombra",
                condicionAlfombra2,
                KEY_LISTA_PARAM_CONDICION_ALFOMBRA
        ));
        inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                String.valueOf(idInspeccion),
                idVehiculo,
                String.valueOf(2),
                "Condicion Alfrombra",
                condicionAlfombra3,
                KEY_LISTA_PARAM_CONDICION_ALFOMBRA
        ));


        switch (tipoLlave) {

            case "1": {
                inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                        String.valueOf(idInspeccion),
                        idVehiculo,
                        tipoLlave,
                        "Tipo llave Inteligente",
                        "15",
                        KEY_LISTA_PARAM_LLAVE
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
                        KEY_LISTA_PARAM_LLAVE
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
                        KEY_LISTA_PARAM_CONDICION_ENTRADA
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
                        KEY_LISTA_PARAM_CONDICION_ENTRADA
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
                        KEY_LISTA_PARAM_CONDICION_ENTRADA
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
                        KEY_LISTA_PARAM_CONDICION_ENTRADA
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
                        KEY_LISTA_PARAM_CONDICION_ENTRADA
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
                KEY_LISTA_PARAM_CONDICION_ENTRADA,
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
                            KEY_LISTA_PARAM_CANTIDAD,
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
                            KEY_LISTA_PARAM_CANTIDAD,
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
                            KEY_LISTA_PARAM_CANTIDAD,
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
                            KEY_LISTA_PARAM_CANTIDAD,
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
                            KEY_LISTA_PARAM_CANTIDAD,
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
                            KEY_LISTA_PARAM_CANTIDAD,
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

                    if (response.body().toString().equals(RESPONSE_CODE_OK)) {

                        if (listaDocumentos.isEmpty()) {
                            myDialogProgress dialogProgress = new myDialogProgress();
                            dialogProgress.show(getFragmentManager(), "Inspeccion");
                            generarPdfOnClick();
                        } else {
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
                Toast.makeText(getApplicationContext(), "Error update detalle" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void guardarVehiculoDocumento() {

        Call<String> callVehiculoDocumento = AdapterVehiculoDocumento.getService().setVehiculoDocumentos(listaDocumentos);
        callVehiculoDocumento.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body().toString().equals(RESPONSE_CODE_OK)) {
                        myDialogProgress dialogProgress = new myDialogProgress();
                        dialogProgress.show(getFragmentManager(), "Inspeccion");
                        generarPdfOnClick();
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
        Call<InspeccionVehiculo> call = AdapterInspeccion.getApiService(JSON_KEY_INSPECCION, valor).getInspecciones();
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
                } else {
                    Toast.makeText(getApplicationContext(), "Error " + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InspeccionVehiculo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error Encabezado: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.v("ENCABEZADO", t.getMessage());
            }
        });

    }


    private void ObtenerDatosVehiculoDocumento(int valor) {

        Call<VehiculoDocumento> callDocumento = AdapterVehiculoDocumento.getDocumentos(JSON_KEY_PEDIDO_ID, String.valueOf(valor)).getVehiculoDocumento();
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

        etxtInspeccion.setText(KEY_TIPO_TRANS_INSPECCION + "-" + idInspeccion);
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
        Call<InspeccionVehiculoDetalle> callInspeccion = AdapterInspeccion.getInspeccionDetalle(JSON_KEY_INSPECCION, String.valueOf(valor)).getInspeccionDetalle();
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
                if (inspeccionResponse.getResponseCode().equals(RESPONSE_CODE_OK)) {
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
                    case KEY_LISTA_PARAM_LUZ: {
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
                    case KEY_LISTA_PARAM_LLAVE: {
                        //llaves
                        tipoLlave = var.getIdRespuesta();
                        if (tipoLlave.equals("15")) {
                            llaveInteligente.setChecked(true);
                            descLlave = "Inteligente";
                        } else {
                            llaveNormal.setChecked(true);
                            descLlave = "Normal";
                        }
                        break;
                    }
                    case KEY_LISTA_PARAM_GENERALES: {
                        //accesorios
                        accesorios.add(var.getDesc__elemento());
                        idAccesorios.add(var.getIdElementoInspeccion());
                        break;
                    }
                    case KEY_LISTA_PARAM_CANTIDAD: {
                        //cantidades
                        cantidades.add(var.getPuntuacion());
                        elementosCantidades.add(var.getDesc__elemento());
                        idElementoCantidades.add(var.getIdElementoInspeccion());
                        break;
                    }
                    case KEY_LISTA_PARAM_CONDICION_ALFOMBRA: {
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
                    case KEY_LISTA_PARAM_CONDICION_ENTRADA: {
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
                if (documentoResponse.getResponseCode().equals(RESPONSE_CODE_OK)) {
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

    public void generarPdfOnClick() {
        String NOMBRE_ARCHIVO = "MiArchivoPDF.pdf";
        String tarjetaSD = Environment.getExternalStorageDirectory().toString();
        Document document = new Document(PageSize.LETTER);


        File pdfDir = new File(tarjetaSD + File.separator + NOMBRE_CARPETA_APP);
        if (!pdfDir.exists()) {
            pdfDir.mkdir();
        }
        File pdfSubDir = new File(pdfDir.getPath() + File.separator + GENERADOS);
        if (!pdfSubDir.exists()) {
            pdfSubDir.mkdir();
        }

        String nombre_completo = tarjetaSD + File.separator + NOMBRE_CARPETA_APP + File.separator +
                GENERADOS + File.separator + NOMBRE_ARCHIVO;


        File outputfile = new File(nombre_completo);
        if (outputfile.exists()) {
            outputfile.delete();
        }
        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(nombre_completo));
            //Crear documento para escribirlo
            MyFooter footerEvent = new MyFooter();
            pdfWriter.setPageEvent(footerEvent);
            document.open();
            document.addAuthor("Hilda Flores Santana");
            document.addCreator("CREADOR");
            document.addSubject("Inspeccion");
            document.addCreationDate();
            document.addTitle("Inspeccion");
            if (placa == null) {
                placa = "En trámite";
            }

            if (telefono != null) {
                telefono = "(" + telefono.substring(0, 3) + ")" + telefono.substring(3);
            }
            if (celular != null) {
                celular = "(" + celular.substring(0, 3) + ")" + celular.substring(3);
            }

            String add = null;
            String nota;
            XMLWorkerHelper workerHelper = XMLWorkerHelper.getInstance();
            String htmToPDF = "<html><head></head><body>" +
                    "<table class=\"fixed\" style=\"width: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td style=\"text-align: left;\" colspan=\"2\" rowspan=\"1\">Av. Pedro A. Rivera Esq. Balilo G&oacute;mez</td>\n" +
                    "<td style=\"text-align: rigth;\" colspan=\"2\" rowspan=\"1\"><strong>Fecha:</strong>" + fechaInspeccion + "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td style=\"text-align: left;\" colspan=\"2\" rowspan=\"1\">La Vega, Rep&uacute;blica Dominicana</td>\n" +
                    "<td style=\"text-align: rigth;\" colspan=\"2\" rowspan=\"1\"><strong>Inspecci&oacute;n:</strong> IV-" + idInspeccion + "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<p>&nbsp;</p>\n" +
                    "<p> <strong>Cliente:</strong>" + "(" + idCliente + ")" + nombreCliente +
                    "<br /><strong>Doc.Identidad:</strong> " + docIdentidad +
                    "<br /> <strong>Tel&eacute;fono/Celular:</strong> " + telefono + " / " + celular + "</p>\n" +
                    "<p>&nbsp;</p>\n" +
                    "<table class=\" fixed \" style=\"width: 60%;\" border=\"1\" cellspacing=\"2\" cellpadding=\"2\" align=\"left\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td style=\"background-color: #cccccc; text-align: center;\" colspan=\"1\" rowspan=\"1\"><strong>Vehiculo Inspeccionado</strong></td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<table class=\" fixed \" style=\"width: 75%;\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td style=\"text-align: center;\" colspan=\"2\" rowspan=\"1\"><strong>" + nombreVehiculo + "</strong></td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td style=\"vertical-align: top;\"><strong>Chasis: </strong>" + chasis + "</td>\n" +
                    "<td style=\"vertical-align: top;\"><strong>Referencia: </strong>" + referencia + "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td style=\"vertical-align: top;\"><strong>Color: </strong>" + color + "</td>\n" +
                    "<td style=\"vertical-align: top;\"><strong>Placa: </strong>" + placa + "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td style=\"vertical-align: top;\"><strong>Motor: </strong>" + motor + "</td>\n" +
                    "<td style=\"vertical-align: top;\"><strong>Kilometraje: </strong>" + kilometraje + "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td style=\"vertical-align: top;\"><strong>Nivel combustible: </strong>" + etxtCombustible.getText().toString() + "</td>\n" +
                    "<td style=\"vertical-align: top;\"><strong>Nivel aceite: </strong>" + etxtNivelAceite.getText().toString() + "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td style=\"vertical-align: top;\"><strong>Condici&oacute;n de llave: </strong>" + descLlave + "</td>\n" +
                    "<td style=\"vertical-align: top;\"><strong>Serie Gomas: </strong>" + serieGomas + "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<p>&nbsp;</p>\n" +


                    "<table class=\" fixed \" style=\"width: 100%;\" border=\"1\" cellspacing=\"2\" cellpadding=\"2\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td style=\"background-color: #cccccc; width: 50%; text-align: center;\"><strong>Luces encendidas</strong></td>\n" +
                    "<td style=\"background-color: #cccccc; width: 50%; text-align: center;\"><strong>Accesorios/Equipamientos</strong></td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +


                    "</table>\n" +
                    "<table class=\" fixed \" style=\"width: 100%;\" border=\"0\" cellspacing=\"1\" cellpadding=\"2\">\n" +
                    "<tbody>\n";


            if (descLuces.size() == accesorios.size()) {

                for (int i = 1; i <= descLuces.size(); i++) {

                    add = add + "<tr>\n" + "<td style=\"width: 5%;\">" + i + "</td>\n" +
                            "<td style=\"width: 45%;\">" + descLuces.get(i - 1) + "</td>\n" +
                            "<td style=\"width: 5%;\">" + i + "</td>\n" +
                            "<td style=\"width: 45%;\">" + accesorios.get(i - 1) + "</td>\n" +
                            "</tr>\n";
                }

            } else if (descLuces.size() > accesorios.size()) {

                for (int i = 1; i <= descLuces.size(); i++) {

                    if (i > accesorios.size()) {
                        add = add + "<tr>\n" +
                                "<td style=\"width: 5%;\">" + i + "</td>\n" +
                                "<td style=\"width: 45%;\"> " + descLuces.get(i - 1) + "</td>\n" +
                                "<td>&nbsp;</td>\n" +
                                "<td>&nbsp;</td>\n" +
                                "</tr>";
                    } else {
                        add = add + "<tr>\n" + "<td style=\"width: 5%;\">" + i + "</td>\n" +
                                "<td style=\"width: 45%;\">" + descLuces.get(i - 1) + "</td>\n" +
                                "<td style=\"width: 5%;\">" + i + "</td>\n" +
                                "<td style=\"width: 45%;\">" + accesorios.get(i - 1) + "</td>\n" +
                                "</tr>\n";
                    }

                }
            } else if (accesorios.size() > descLuces.size()) {
                for (int i = 1; i <= accesorios.size(); i++) {
                    if (i > descLuces.size()) {
                        add = add + "<tr>\n" +
                                "<td>&nbsp;</td>\n" +
                                "<td>&nbsp;</td>\n" +
                                "<td style=\"width: 5%;\">" + i + "</td>\n" +
                                "<td style=\"width: 45%;\"> " + accesorios.get(i - 1) + "</td>\n" +
                                "</tr>";
                    } else {
                        add = add + "<tr>\n" + "<td style=\"width: 5%;\">" + i + "</td>\n" +
                                "<td style=\"width: 45%;\">" + descLuces.get(i - 1) + "</td>\n" +
                                "<td style=\"width: 5%;\">" + i + "</td>\n" +
                                "<td style=\"width: 45%;\">" + accesorios.get(i - 1) + "</td>\n" +
                                "</tr>\n";
                    }
                }

            }


            htmToPDF = htmToPDF + add +
                    "</tbody>\n" +
                    "</table>\n" +

                    "<p>&nbsp;</p>\n" +

                    "<table style=\"width: 100%;\" border=\"1\" cellspacing=\"2\" cellpadding=\"2\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td style=\"background-color: #cccccc; width: 50%; text-align: center;\"><strong>Cantidades</strong></td>\n" +
                    "<td style=\"background-color: #cccccc; width: 50%; text-align: center;\"><strong>Condici&oacute;n Alfombra conductor</strong></td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<table style=\"width: 100%;\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td style=\"width: 15%; text-align: left;\"><strong>Alfombras:</strong></td>\n" +
                    "<td style=\"width: 10%;\">" + cantidades.get(0) + "</td>\n" +
                    "<td style=\"width: 15%; text-align: left;\"><strong>Llaves:</strong></td>\n" +
                    "<td style=\"width: 10%;\">" + cantidades.get(1) + "</td>\n" +
                    "<td style=\"width: 50%;\">" + descAlfombra1 + "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td><strong>Gato:</strong></td>\n" +
                    "<td>" + cantidades.get(2) + "</td>\n" +
                    "<td><strong>Alicate:</strong></td>\n" +
                    "<td>" + cantidades.get(3) + "</td>\n" +
                    "<td>" + descAlfombra2 + "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td><strong>Llave rueda:</strong></td>\n" +
                    "<td>" + cantidades.get(4) + "</td>\n" +
                    "<td><strong>No. Bateria:</strong></td>\n" +
                    "<td>" + cantidades.get(5) + "</td>\n" +
                    "<td>" + descAlfombra3 + "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<p>&nbsp;</p>\n";
            if (observaciones != null) {
                nota = "<p><strong>Observaciones: </strong>" + observaciones.toUpperCase() + "</p>\n";
                htmToPDF = htmToPDF + nota + "<p>&nbsp;</p>\n" +
                        "<table style=\"width: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                        "<tbody>\n" +
                        "<tr>\n" +
                        "<td style=\"width: 50%; text-align: center;\">__________________________________</td>\n" +
                        "<td style=\"width: 50%; text-align: center;\">__________________________________</td>\n" +
                        "</tr>\n" +
                        "<tr>\n" +
                        "<td style=\"width: 50%; text-align: center;\">Inspeccionador</td>\n" +
                        "<td style=\"width: 50%; text-align: center;\">Cliente</td>\n" +
                        "</tr>\n" +
                        "</tbody>\n" +
                        "</table>" +

                        "</body>\n" +
                        "</html>\n";
            } else {
                htmToPDF = htmToPDF + "<p>&nbsp;</p>\n" +
                        "<table style=\"width: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                        "<tbody>\n" +
                        "<tr>\n" +
                        "<td style=\"width: 50%; text-align: center;\">__________________________________</td>\n" +
                        "<td style=\"width: 50%; text-align: center;\">__________________________________</td>\n" +
                        "</tr>\n" +
                        "<tr>\n" +
                        "<td style=\"width: 50%; text-align: center;\">Inspeccionador</td>\n" +
                        "<td style=\"width: 50%; text-align: center;\">Cliente</td>\n" +
                        "</tr>\n" +
                        "</tbody>\n" +
                        "</table>" +

                        "</body>\n" +
                        "</html>\n";
            }
            Log.d("HTML==>", htmToPDF);
            workerHelper.parseXHtml(pdfWriter, document, new StringReader(htmToPDF));
            document.close();
            // Toast.makeText(this, "PDF generado", Toast.LENGTH_SHORT).show();
            muestraPDF(nombre_completo, this);
        } catch (DocumentException d) {
            d.printStackTrace();
            Log.d("Error==>", d.getLocalizedMessage());
        } catch (FileNotFoundException f) {
            f.printStackTrace();
            Log.d("Error==>", f.getLocalizedMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Error==>", e.getLocalizedMessage());
        }

    }

    public void muestraPDF(String archivo, Context context) {
        // Toast.makeText(context, "Leyendo archivo", Toast.LENGTH_SHORT).show();
        File file = new File(archivo);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No tiene una aplicacion para abrir este archivo", Toast.LENGTH_SHORT).show();

        }

    }

    class MyFooter extends PdfPageEventHelper {
        Font ffont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
        Font ffont2 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);

        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            Phrase header = new Phrase("Inspección de Vehículo", ffont);
            Phrase footer = new Phrase("No nos hacemos responsables de todos los objetos dejados en la empresa" +
                    " por mas de 30 dias", ffont2);
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    header,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.top() + 10, 0);
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    footer,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.bottom() - 10, 0);
        }
    }


}
