package com.example.prueba.CheckMobile.Inspeccion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.LucesParametros.ListaLuces;
import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.VehiculoDocumento.AdapterVehiculoDocumento;
import com.example.prueba.CheckMobile.VehiculoDocumento.VehiculoDocumento;
import com.example.prueba.CheckMobile.VehiculoDocumento.VehiculoDocumentoResponse;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static com.example.prueba.CheckMobile.R.id.listaLuces;
import static java.security.AccessController.getContext;


public class ConsultaInspeccionActivity extends AppCompatActivity {

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
    RadioButton llaveInteligente;
    RadioButton llaveNormal;
    RadioGroup alfombra1;
    RadioGroup alfombra2;
    RadioGroup alfombra3;
    LinearLayout layoutLuces;
    GridLayout grAccesorios;
    GridLayout grCantidades;
    ListView grLadoVehiculo;
    SeekBar seekAceite;


    private int idInspeccion;
    private String nombreVehiculo;
    private String nombreCliente;
    private String motor;
    private String kilometraje;
    private String serieGomas;
    private String fechaInspeccion;
    private String observaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_inspeccion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        if (extra != null) {
            idInspeccion = extra.getInt("IDINSPECCION");
            nombreVehiculo = extra.getString("VEHICULO");
            nombreCliente = extra.getString("CLIENTE");
            motor = extra.getString("MOTOR");
            kilometraje = extra.getString("KILOMETRAJE");
            serieGomas = extra.getString("SERIEGOMAS");
            fechaInspeccion = extra.getString("FECHA");
            observaciones = extra.getString("OBSERVACION");
        }
        inicializacionVariables();
        llenarFormulario();
        ObtenerDatosInspeccion(idInspeccion);
        ObtenerDatosVehiculoDocumento(idInspeccion);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabSalir);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void ObtenerDatosVehiculoDocumento(int valor) {

        Call<VehiculoDocumento> callDocumento = AdapterVehiculoDocumento.getDocumentos("id_documento", String.valueOf(valor)).getVehiculoDocumento();
        callDocumento.enqueue(new DocumentoCallback());


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
        layoutLuces = (LinearLayout) findViewById(R.id.layoutCLuces);
        grAccesorios = (GridLayout) findViewById(R.id.gridAccesorios);
        grCantidades = (GridLayout) findViewById(R.id.gridCantidades);
        seekAceite = (SeekBar) findViewById(R.id.seekBarCAceite);
        grLadoVehiculo = (ListView) findViewById(R.id.gridLadosVehiculo);
    }

    private void llenarFormulario() {
        Log.d("TAG==>", String.valueOf(idInspeccion));
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

    private class InspeccionCallBack implements retrofit2.Callback<InspeccionVehiculoDetalle> {
        @Override
        public void onResponse(Call<InspeccionVehiculoDetalle> call, Response<InspeccionVehiculoDetalle> response) {
            if (response.isSuccessful()) {
                InspeccionVehDetalleResponse inspeccionResponse = response.body();
                if (inspeccionResponse.getResponseCode().equals("200")) {
                    llenarFormularioDetalles(inspeccionResponse.getInspecciones());

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

        String tipoLlave;
        List<String> accesorios = new ArrayList<>();
        List<String> cantidades = new ArrayList<>();
        List<String> elementosCantidades = new ArrayList<>();
        String combustible, condicionAlfombra1 = null, condicionAlfombra2 = null, condicionAlfombra3 = null;
        int aceite;
        for (InspeccionVehiculoDetalle var : inspecciones) {
            if (var.getId_lista_parametro() != null) {
                switch (var.getId_lista_parametro()) {
                    case "58": {
                        //Luces de vehiculo
                        CheckBox checkLuces = new CheckBox(layoutLuces.getContext());
                        checkLuces.setText(var.getDesc__elemento());
                        checkLuces.setTextSize(20);
                        checkLuces.setChecked(true);
                        layoutLuces.addView(checkLuces);
                        checkLuces.setEnabled(false);
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
                        break;
                    }
                    case "62": {
                        //cantidades
                        cantidades.add(var.getPuntuacion());
                        elementosCantidades.add(var.getDesc__elemento());
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
                        } else {
                            aceite = Integer.parseInt(var.getPuntuacion());
                            seekAceite.setProgress(aceite);
                            seekAceite.setEnabled(false);
                            etxtNivelAceite.setText(String.valueOf(aceite) + "/" + seekAceite.getMax());
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
            checkAccesorios.setChecked(true);
            checkAccesorios.setTextSize(20);
            grAccesorios.addView(checkAccesorios);
            checkAccesorios.setEnabled(false);
        }

        grCantidades.setRowCount(cantidades.size());
        for (int i = 0; i < cantidades.size(); i++) {
            TextView textCantidades = new TextView(grCantidades.getContext());
            textCantidades.setTextSize(20);
            textCantidades.setText(elementosCantidades.get(i) + ": " + cantidades.get(i) + "    ");
            grCantidades.addView(textCantidades);
        }


        if (condicionAlfombra1.equals("9")) {
            RadioButton r = (RadioButton) alfombra1.findViewById(R.id.rbCBien);
            r.setChecked(true);

        } else {
            RadioButton r = (RadioButton) alfombra1.findViewById(R.id.rbCNoBien);
            r.setChecked(true);
        }

        if (condicionAlfombra2.equals("10")) {
            RadioButton r = (RadioButton) alfombra2.findViewById(R.id.rbCGenuina);
            r.setChecked(true);

        } else {
            RadioButton r = (RadioButton) alfombra2.findViewById(R.id.rbCNoGenuina);
            r.setChecked(true);

        }

        if (condicionAlfombra3.equals("11")) {
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

                    AdapterLados adapter = new AdapterLados(getApplicationContext(), documentoResponse.getVehiculoDocumento());
                    grLadoVehiculo.setAdapter(adapter);
                    Log.d("TAG==>", String.valueOf(grLadoVehiculo.getAdapter().getCount()));

                }

            } else {
                Toast.makeText(getApplicationContext(), "Error en de respuesta de inspeccion", Toast.LENGTH_SHORT).show();
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
            Picasso.with(getApplicationContext())
                    .load(f)
                    .error(R.mipmap.no_foto)
                    .into(imagen);

            return item;
        }


    }
}
