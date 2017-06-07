package com.example.prueba.CheckMobile.Vehiculo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.Cliente.ClienteActivity;
import com.example.prueba.CheckMobile.Combustible.AdapterCombustible;
import com.example.prueba.CheckMobile.Combustible.Combustible;
import com.example.prueba.CheckMobile.Combustible.CombustibleResponse;
import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.TipoVehiculo.AdaprterTipoVehiculo;
import com.example.prueba.CheckMobile.TipoVehiculo.TipoVehiculo;
import com.example.prueba.CheckMobile.TipoVehiculo.TipoVehiculoResponse;
import com.example.prueba.CheckMobile.VehiculoEstilo.AdapterEstilo;
import com.example.prueba.CheckMobile.VehiculoEstilo.Estilo;
import com.example.prueba.CheckMobile.VehiculoEstilo.EstiloResponse;
import com.example.prueba.CheckMobile.VehiculoEstilo.EstiloService;
import com.example.prueba.CheckMobile.VehiculoMarca.AdapterMarca;
import com.example.prueba.CheckMobile.VehiculoMarca.Marca;
import com.example.prueba.CheckMobile.VehiculoMarca.MarcaResponse;
import com.example.prueba.CheckMobile.VehiculoModelo.AdapterModelo;
import com.example.prueba.CheckMobile.VehiculoModelo.Modelo;
import com.example.prueba.CheckMobile.VehiculoModelo.ModeloResponse;
import com.example.prueba.CheckMobile.VehiculoTraccion.AdapterTraccion;
import com.example.prueba.CheckMobile.VehiculoTraccion.Traccion;
import com.example.prueba.CheckMobile.VehiculoTraccion.TraccionResponse;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Delayed;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.BufferedSink;
import retrofit.RetrofitError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.action;
import static android.R.attr.data;
import static android.R.id.list;
import static com.example.prueba.CheckMobile.R.string.id_modelo;
//import static com.example.prueba.CheckMobile.VehiculoEstilo.AdapterEstilo.Connect;

public class VehiculoActivity extends AppCompatActivity {
    Spinner spinnerEstilo;
    Spinner spinnerMarca;
    Spinner spinnerTipoVehiculo;
    Spinner spinnerEstado;
    Spinner spinnerModelo;
    RadioGroup radioCombustible, radioTraccion;
    EditText etxtMarca;
    ListView listaMarcas;
   EditText editTextReferencia;
    String parametro;

    private Timer timer = new Timer();
    private final long DELAY = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Inicializacion de los objetos
        spinnerEstilo = (Spinner) findViewById(R.id.spinnerEstilo);
        // spinnerMarca = (Spinner) findViewById(R.id.spinnerMarca);
        spinnerTipoVehiculo = (Spinner) findViewById(R.id.spinnerTipoVeh);
        spinnerEstado = (Spinner) findViewById(R.id.spinnerEstado);
        spinnerModelo = (Spinner) findViewById(R.id.spinnerModelo);
        etxtMarca = (EditText) findViewById(R.id.etxtMarca);

        //listMarca = (ListView) findViewById(R.id.listview_search);

        //Llamadas de los metodos
        poblarSpinnerEstadoVeh();
        obtenerDatosCombustilbe();
        obtenerDatosTraccion();
        obtenerDatosTipoVeh();
        obtenerDatosMarca();
        obtenerDatosModelo();
        obtenerDatosEstilo();
        buscarChasis();
//
//        this.runOnUiThread(new Runnable() {
//            public void run() {
//                new FeedTask().execute("2");
//            }
//        });


        Button btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VehiculoActivity.this.getApplicationContext(), ClienteActivity.class);
                startActivity(intent);
            }
        });

        etxtMarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogListView(view);

            }
        });


    }


    private void buscarChasis()
    {
        editTextReferencia = (EditText) findViewById(R.id.etxtChasis);
        editTextReferencia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {
                if(timer != null)
                    timer.cancel();
            }
            @Override
            public void afterTextChanged(final Editable s) {
                //avoid triggering event when text is too short
                if (s.length() >= 7) {

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                parametro = editTextReferencia.getText().toString();
                                    ObtenerVehiculo(parametro);

                                    }
                            });
                        }

                    }, DELAY );
                }
            }
        });














//         ((EditText) findViewById(R.id.etxtChasis)).setOnEditorActionListener(
//                new EditText.OnEditorActionListener() {
//                    @Override
//                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
//                                actionId == EditorInfo.IME_ACTION_DONE ||
//                                event.getAction() == KeyEvent.ACTION_DOWN &&
//                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//                            if (!event.isShiftPressed()) {
//                            Toast.makeText(getApplicationContext(), "Hola", Toast.LENGTH_LONG).show();
//                            return true;                            }
//                        }
//                        return false; // pass on to other listeners.
//                    }
//                });
    }

    private void ObtenerVehiculo(String parametro) {
        



    }

    private void obtenerDatosTraccion() {
        Call<Traccion> call = AdapterTraccion.getApiService().getTracciones();
        call.enqueue(new TraccionCallback());
    }

    private void obtenerDatosCombustilbe() {
        Call<Combustible> call = AdapterCombustible.getApiService().getCombustibles();
        call.enqueue(new CombustibleCallback());
    }

    private void obtenerDatosModelo() {

        Call<Modelo> call = AdapterModelo.getApiService().getModelos();
        call.enqueue(new ModeloCallbak());
    }

    private void obtenerDatosTipoVeh() {
        Call<TipoVehiculo> call = AdaprterTipoVehiculo.getApiService().getTiposVeh();
        call.enqueue(new TipoVehCallback());

    }

    private void obtenerDatosMarca() {

        Call<Marca> call = AdapterMarca.getApiService().getMarcas();
        call.enqueue(new MarcaCallback());
    }


    class TipoVehCallback implements Callback<TipoVehiculo> {
        @Override
        public void onResponse(@NonNull Call<TipoVehiculo> call, Response<TipoVehiculo> response) {
            if (response.isSuccessful()) {
                TipoVehiculoResponse tipoVehiculo = response.body();
                poblarSpinnerTipoVehiculo(tipoVehiculo.getTipoVehiculos());
                Log.v("hhhhhh", tipoVehiculo.getMessage());


            } else {
                Toast.makeText(getApplicationContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailure(Call<TipoVehiculo> call, Throwable t) {
            Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            Log.v("Tipo5 ===>", t.getMessage());
        }
    }


    class MarcaCallback implements Callback<Marca> {
        @Override
        public void onResponse(Call<Marca> call, Response<Marca> response) {
            if (response.isSuccessful()) {
                MarcaResponse marcaResponse = response.body();
                // poblarSpinnerMarca(marcaResponse.getMarcas());
                ObtenerDialogLista(marcaResponse.getMarcas());
            } else {
                Toast.makeText(getApplicationContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailure(Call<Marca> call, Throwable t) {
            Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            Log.v("Marcak ===>", t.getMessage());

        }
    }

    private void ObtenerDialogLista(ArrayList<Marca> marcas) {
        listaMarcas = new ListView(this);
        List<String> lista = new ArrayList<>();

        for (Marca varMarca : marcas) {
            lista.add(varMarca.getDescripcion());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.row_marca, R.id.txtRowMarca, lista);
        listaMarcas.setAdapter(adapter);
        listaMarcas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ViewGroup vg = (ViewGroup) view;
                TextView txt = (TextView) vg.findViewById(R.id.txtRowMarca);
                Toast.makeText(VehiculoActivity.this, txt.getText().toString(), Toast.LENGTH_LONG);


            }
        });

    }

    public void showDialogListView(View view) {
        AlertDialog.Builder builder = new
                AlertDialog.Builder(VehiculoActivity.this);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", null);
        builder.setView(listaMarcas);
        AlertDialog dialog = builder.create();

        dialog.show();

    }


    private void poblarSpinnerTipoVehiculo(ArrayList<TipoVehiculo> tipo) {

        List<String> lista = new ArrayList<>();
        for (TipoVehiculo varTipo : tipo) {
            lista.add(varTipo.getDescripcion());
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, lista);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoVehiculo.setAdapter(spinnerArrayAdapter);

    }

    private void poblarSpinnerMarca(ArrayList<Marca> marcas) {
        List<String> lista = new ArrayList<>();
        for (Marca varMarca : marcas) {
            lista.add(varMarca.getDescripcion());
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.select_dialog_item, lista);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        spinnerMarca.setAdapter(spinnerArrayAdapter);


    }

    private void poblarSpinnerEstadoVeh() {
        List<String> lista = new ArrayList<>();
        lista.add("ACTIVO");
        lista.add("INACTIVO");
        lista.add("PENDIENTE");


        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, lista);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(spinnerArrayAdapter);

    }

    private void poblarSpinnerEstilo(ArrayList<Estilo> estilos) {
        List<String> lista = new ArrayList<>();
        for (Estilo varEstilo : estilos) {
            lista.add(varEstilo.getDescripcion());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, lista);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstilo.setAdapter(spinnerArrayAdapter);
    }

    private void obtenerDatosEstilo() {

       Call<ArrayList<Estilo>> call = AdapterEstilo.getApiService().getEstilos();
      call.enqueue(new EstiloCallback());
    }

     class EstiloCallback implements Callback<ArrayList<Estilo>> {
        @Override
        public void onResponse(Call<ArrayList<Estilo>> call, Response<ArrayList<Estilo>> response) {
            if (response.isSuccessful()) {
                ArrayList<Estilo> estiloResponse = response.body();
                Log.v("AWiiii--->", response.body().toString());
                  poblarSpinnerEstilo(estiloResponse);
            } else {

                Toast.makeText(getApplicationContext(), call.toString(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<ArrayList<Estilo>> call, Throwable t) {

            Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            Log.v("Aqui ===>", t.getMessage());
        }
    }



private class ModeloCallbak implements Callback<Modelo> {

    @Override
    public void onResponse(Call<Modelo> call, Response<Modelo> response) {
        if (response.isSuccessful()) {
            ModeloResponse modeloResponse = response.body();
            poblarSpinnerModelo(modeloResponse.getModelos());

        } else {
            Toast.makeText(getApplicationContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<Modelo> call, Throwable t) {
        Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        Log.v("Aqui ===>", t.getMessage());
    }
}

    private void poblarSpinnerModelo(ArrayList<Modelo> modelos) {
        List<String> lista = new ArrayList<>();
        for (Modelo varModelo : modelos) {
            lista.add(varModelo.getDescripcion());
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, lista);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerModelo.setAdapter(spinnerArrayAdapter);


    }

private class CombustibleCallback implements Callback<Combustible> {
    @Override
    public void onResponse(Call<Combustible> call, Response<Combustible> response) {
        if (response.isSuccessful()) {
            CombustibleResponse combustiblerResponse = response.body();
            crearRadiosCombustible(combustiblerResponse.getCombustibles());

        } else {
            Toast.makeText(getApplicationContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<Combustible> call, Throwable t) {

        Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        Log.v("Comb*** ===>", t.getMessage());
    }
}

    private void crearRadiosCombustible(ArrayList<Combustible> combustibles) {
        String initCap;
        radioCombustible = (RadioGroup) findViewById(R.id.rGCombustible);
        for (Combustible varcombsutible : combustibles) {
            RadioButton radio = new RadioButton(radioCombustible.getContext());
            radio.setId(Integer.parseInt(varcombsutible.getIdCombustible()));
            initCap = varcombsutible.getDescripcion().toUpperCase().substring(0, 1);
            radio.setText(initCap + varcombsutible.getDescripcion().toLowerCase().substring(1));
            radioCombustible.addView(radio);
        }

    }

private class TraccionCallback implements Callback<Traccion> {
    @Override
    public void onResponse(Call<Traccion> call, Response<Traccion> response) {
        if (response.isSuccessful()) {
            TraccionResponse traccionResponse = response.body();
            CrearRadiosTraccion(traccionResponse.getTracciones());
        } else {
            Toast.makeText(getApplicationContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFailure(Call<Traccion> call, Throwable t) {

        Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        Log.v("tracc-*** ===>", t.getMessage());
    }

}


    private void CrearRadiosTraccion(ArrayList<Traccion> tracciones) {

        radioTraccion = (RadioGroup) findViewById(R.id.rGTraccion);
        for (Traccion varTraccion : tracciones) {
            RadioButton radio = new RadioButton(radioTraccion.getContext());
            radio.setText(varTraccion.getDescripcion().toUpperCase());
            radioTraccion.addView(radio);


        }


    }


}
