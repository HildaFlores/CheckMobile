package com.example.prueba.CheckMobile.Cliente;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.CondicionPago.AdapterCondicion;
import com.example.prueba.CheckMobile.CondicionPago.CondicionPago;
import com.example.prueba.CheckMobile.CondicionPago.CondicionResponse;
import com.example.prueba.CheckMobile.Inspeccion.InspeccionLucesActivity;
import com.example.prueba.CheckMobile.Pais.AdapterPais;
import com.example.prueba.CheckMobile.Pais.Pais;
import com.example.prueba.CheckMobile.Pais.PaisResponse;
import com.example.prueba.CheckMobile.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ClienteActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layoutEmpresa;
    RadioButton radioEmpresa;
    RadioButton radioPersona;
    TextView txtDocIdentidad;
    TextView txtNombre;
    TextView txtapellido;
    EditText etxtDocIdentidad;
    Spinner spinnerNacionalidad;
    Spinner spinnerPais;
    Spinner spinnerCondicion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        layoutEmpresa = (LinearLayout) findViewById(R.id.layoutNombreEmpresa);
        layoutEmpresa.setVisibility(View.GONE);
        radioEmpresa = (RadioButton) findViewById(R.id.rbEmpresa);
        radioPersona = (RadioButton) findViewById(R.id.rbPersona);
        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtapellido = (TextView) findViewById(R.id.txtApellido);
        txtDocIdentidad = (TextView) findViewById(R.id.txtDocIdentidad);
        etxtDocIdentidad = (EditText) findViewById(R.id.etxtDocIdentidad);
        spinnerNacionalidad = (Spinner) findViewById(R.id.spNacionalidad);
        spinnerPais = (Spinner) findViewById(R.id.spPais);
        spinnerCondicion = (Spinner) findViewById(R.id.spCondicion);
        ObtenerDatosNacionalidad();
        ObtenerDatosCondicion();


        radioPersona.setOnClickListener(this);
        radioEmpresa.setOnClickListener(this);

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCancel);
        fab.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        Button btnClienteSiguiente = (Button) findViewById(R.id.btnClienteSiguiente);
        btnClienteSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClienteActivity.this.getApplicationContext(), InspeccionLucesActivity.class);
                startActivity(intent);
            }
        });


    }

    private void ObtenerDatosCondicion() {

        Call<CondicionPago> call = AdapterCondicion.getApiService().getCondicionPagos();
        call.enqueue(new CondicionPagoCallback());
    }

    private void ObtenerDatosNacionalidad() {
        Call<Pais> call = AdapterPais.getApiService().getPaises();
        call.enqueue(new NacionalidadCallback());
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.rbEmpresa:
                txtNombre.setText("Nombres Rep.");
                txtapellido.setText("Apellidos Rep.");
                txtDocIdentidad.setText("RNC");
                layoutEmpresa.setVisibility(View.VISIBLE);
                etxtDocIdentidad.setHint("RNC");

                break;
            case R.id.rbPersona:
                radioEmpresa.setSelected(true);
                txtNombre.setText("Nombres ");
                txtapellido.setText("Apellidos ");
                txtapellido.setText("Documento Identidad ");
                layoutEmpresa.setVisibility(View.GONE);
                etxtDocIdentidad.setHint("Cédula/Pasaporte");
                break;

        }

    }

    private class NacionalidadCallback implements retrofit2.Callback<Pais> {
        @Override
        public void onResponse(Call<Pais> call, Response<Pais> response) {
            if (response.isSuccessful()) {
                PaisResponse nacionalidadresponse = response.body();
                poblarSpinnerNacionalidad(nacionalidadresponse.getPaises());

            } else {
                Toast.makeText(getApplicationContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<Pais> call, Throwable t) {
            Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            Log.v("Aqui ===>", t.getMessage());
        }
    }

    private void poblarSpinnerNacionalidad(ArrayList<Pais> paises) {
        List<String> listaNacionalidad = new ArrayList<>();
        List<String> listaPaises = new ArrayList<>();
        for (Pais varpais : paises) {
            listaNacionalidad.add(varpais.getNacionalidad());
            listaPaises.add(varpais.getDesc_pais());

        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listaNacionalidad);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNacionalidad.setAdapter(spinnerArrayAdapter);


        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listaPaises);
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPais.setAdapter(spinnerArrayAdapter2);

    }

    private class CondicionPagoCallback implements retrofit2.Callback<CondicionPago> {
        @Override
        public void onResponse(Call<CondicionPago> call, Response<CondicionPago> response) {
            if (response.isSuccessful())
            {
                CondicionResponse condicionResponse = response.body();
                poblarSpinnerCondicion(condicionResponse.getCondicionPagos());
            }
            else {
                Toast.makeText(getApplicationContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<CondicionPago> call, Throwable t) {
            Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            Log.v("Ahi ===>", t.getMessage());
        }
    }

    private void poblarSpinnerCondicion(ArrayList<CondicionPago> condicionPagos) {
        List<String> listaCondicion = new ArrayList<>();

        for (CondicionPago varCondicion : condicionPagos) {
            listaCondicion.add(varCondicion.getDescripcion());
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, listaCondicion);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCondicion.setAdapter(spinnerArrayAdapter);


    }
}