package com.example.prueba.CheckMobile.OrdenTrabajo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultaOrdenTrabajo extends AppCompatActivity {
    private String nombreCliente;
    private String idCondicion;
    private String idCliente;
    private String descripcionCondicion;
    private String nombreMecanico;
    private String fechaOrden;
    private String observaciones;
    private int idInspeccion;
    private int idOrden;
    private Boolean permitePiezasReemplazo;

    TextView consultaNombreCliente;
    EditText consultafechaDocumento;
    TextView consultaIdOrden;
    TextView consultaCondicion;
    TextView consultaMecanico;
    EditText consultaObservaciones;
    LinearLayout layoutServicios;
    Switch swReemplazo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_orden_trabajo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarOrden);
        setSupportActionBar(toolbar);
        inicializacionVariables();
// Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            idOrden = extras.getInt("ORDEN");
            nombreCliente = extras.getString("CLIENTE");
            idCliente = extras.getString("IDCLIENTE");
            idCondicion = extras.getString("CONDICION");
            idInspeccion = extras.getInt("INSPECCION");
            fechaOrden = extras.getString("FECHA");
            observaciones = extras.getString("OBSERVACIONES");
            permitePiezasReemplazo = extras.getBoolean("PIEZA");
            nombreMecanico = extras.getString("MECANICO");
            descripcionCondicion = extras.getString("CONDICION");
        }

        llenarOrdenEnc();
        obtenerOrdenDetalle(idOrden);
    }

    private void obtenerOrdenDetalle(int valor) {
        Call<OrdenTrabajoDet> callOrden = AdapterOrdenTrabajo.getOrdenDetalle("id_tipo_trans", "OTT", "id_documento", String.valueOf(valor)).getPedidoDetalle();
        callOrden.enqueue(new Callback<OrdenTrabajoDet>() {
            @Override
            public void onResponse(Call<OrdenTrabajoDet> call, Response<OrdenTrabajoDet> response) {
                if (response.isSuccessful()) {
                    OrdenTrabajoDetResponse ordenResponse = response.body();
                    if (!ordenResponse.getOrdenes().isEmpty()) {
                        llenarFormularioOrdenDetalle(ordenResponse.getOrdenes());
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Error en el formato de respuesta de orden", Toast.LENGTH_SHORT).show();
                    Log.v("INSPECCION ==>", response.errorBody().toString());

                }
            }

            @Override
            public void onFailure(Call<OrdenTrabajoDet> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.v("Aqui ===>", t.getMessage());
            }
        });
    }

    private void llenarFormularioOrdenDetalle(ArrayList<OrdenTrabajoDet> ordenes) {
        for (OrdenTrabajoDet var : ordenes) {
            CheckBox checkServicios = new CheckBox(layoutServicios.getContext());
            checkServicios.setText(var.getDescripcion_producto());
            checkServicios.setTextSize(15);
            checkServicios.setChecked(true);
            checkServicios.setEnabled(false);
            layoutServicios.addView(checkServicios);
        }
    }


    private void inicializacionVariables() {
        consultaNombreCliente = (TextView) findViewById(R.id.textViewCDatosCliente);
        consultafechaDocumento = (EditText) findViewById(R.id.etxtCFechaOrden);
        consultaIdOrden = (TextView) findViewById(R.id.textViewCOrden);
        consultaCondicion = (TextView) findViewById(R.id.textCCondicion);
        consultaMecanico = (TextView) findViewById(R.id.textViewCMec);
        consultaObservaciones = (EditText) findViewById(R.id.etxtCObervaciones);
        layoutServicios = (LinearLayout) findViewById(R.id.layoutServicios);
        swReemplazo = (Switch) findViewById(R.id.switchCPiezas);

    }

    private void llenarOrdenEnc() {
        //  Log.d("ORDEN==>", String.valueOf(idOrden));
        consultaIdOrden.setText("OTT-" + idOrden);
        consultafechaDocumento.setText(fechaOrden);
        consultaNombreCliente.setText("Cliente >> " + nombreCliente.toUpperCase());
        consultaObservaciones.setText(observaciones);
        consultaCondicion.setText(descripcionCondicion);
        consultaMecanico.setText(nombreMecanico.toUpperCase());

        if (!permitePiezasReemplazo)
        {
            swReemplazo.setChecked(true);
        }

    }

    private int getIndex(Spinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                index = i;
            }
        }
        return index;
    }
}