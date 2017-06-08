package com.example.prueba.CheckMobile.Vehiculo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.Cliente.AdapterCliente;
import com.example.prueba.CheckMobile.Cliente.Cliente;
import com.example.prueba.CheckMobile.Cliente.ClienteActivity;
import com.example.prueba.CheckMobile.Cliente.ClienteResponse;
import com.example.prueba.CheckMobile.Combustible.AdapterCombustible;
import com.example.prueba.CheckMobile.Combustible.Combustible;
import com.example.prueba.CheckMobile.Combustible.CombustibleResponse;
import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.TipoVehiculo.AdaprterTipoVehiculo;
import com.example.prueba.CheckMobile.TipoVehiculo.TipoVehiculo;
import com.example.prueba.CheckMobile.TipoVehiculo.TipoVehiculoResponse;
import com.example.prueba.CheckMobile.VehiculoEstilo.AdapterEstilo;
import com.example.prueba.CheckMobile.VehiculoEstilo.Estilo;
import com.example.prueba.CheckMobile.VehiculoMarca.AdapterMarca;
import com.example.prueba.CheckMobile.VehiculoMarca.Marca;
import com.example.prueba.CheckMobile.VehiculoMarca.MarcaResponse;
import com.example.prueba.CheckMobile.VehiculoModelo.AdapterModelo;
import com.example.prueba.CheckMobile.VehiculoModelo.Modelo;
import com.example.prueba.CheckMobile.VehiculoModelo.ModeloResponse;
import com.example.prueba.CheckMobile.VehiculoTraccion.AdapterTraccion;
import com.example.prueba.CheckMobile.VehiculoTraccion.Traccion;
import com.example.prueba.CheckMobile.VehiculoTraccion.TraccionResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;


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
    String valor;


    private Timer timer = new Timer();
    private final long DELAY = 0;


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


    private void buscarChasis() {
        editTextReferencia = (EditText) findViewById(R.id.etxtReferencia);
        editTextReferencia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                //editTextReferencia.setText("");
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {
                if (timer != null)
                    timer.cancel();

            }

            @Override
            public void afterTextChanged(final Editable s) {
                //avoid triggering event when text is too short
                if (s.length() >= 6) {

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                public void run() {

                                    valor = editTextReferencia.getText().toString();
                                    // Toast.makeText(getApplicationContext(), valor, Toast.LENGTH_SHORT).show();

                                    ObtenerVehiculo(valor);

                                }
                            });
                        }

                    }, DELAY);
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

    private void ObtenerVehiculo(String valor) {
        Call<Vehiculo> call = AdapterVehiculo.getChasis("referencia", valor).getVehiculos();
        call.enqueue(new FiltroVehiculoCallback());

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
        public void onResponse(@NonNull Call<TipoVehiculo> call, @NonNull Response<TipoVehiculo> response) {
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

        Call<ArrayList<Estilo>> call = AdapterEstilo.getApiService("id_modelo", "2").getEstilos();
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
        int contador = 0;
        for (Traccion varTraccion : tracciones) {
            RadioButton radio = new RadioButton(radioTraccion.getContext());
            contador++;
            radio.setId(contador);
            radio.setText(varTraccion.getDescripcion().toUpperCase());
            radioTraccion.addView(radio);


        }

    }


    private class FiltroVehiculoCallback implements Callback<Vehiculo> {
        @Override
        public void onResponse(Call<Vehiculo> call, Response<Vehiculo> response) {
            if (response.isSuccessful()) {
                VehiculoResponse vehiculoResponse = response.body();
                llenarFormularioVehiculo(vehiculoResponse.getVehiculos());
            } else {
                Toast.makeText(getApplicationContext(), "Error en el formato de respuesta de vehiculo", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<Vehiculo> call, Throwable t) {
            Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            Log.v("veh-->*** ===>", t.getMessage());
        }
    }

    private void llenarFormularioVehiculo(ArrayList<Vehiculo> vehiculos) {

        EditText chasis = (EditText) findViewById(R.id.etxtChasis);
        EditText año = (EditText) findViewById(R.id.etxtAnio);
        EditText placa = (EditText) findViewById(R.id.etxtPlaca);
        Spinner tipo = (Spinner) findViewById(R.id.spinnerTipoVeh);
        EditText marca = (EditText) findViewById(R.id.etxtMarca);
        Spinner modelo = (Spinner) findViewById(R.id.spinnerModelo);
        Spinner estilo = (Spinner) findViewById(R.id.spinnerEstilo);
        RadioButton cilindro2 = (RadioButton) findViewById(R.id.rbNoClindro2);
        RadioButton cilindro3 = (RadioButton) findViewById(R.id.rbNoClindro3);
        RadioButton cilindro4 = (RadioButton) findViewById(R.id.rbNoClindro4);
        RadioButton cilindro5 = (RadioButton) findViewById(R.id.rbNoClindro5);
        RadioButton cilindro6 = (RadioButton) findViewById(R.id.rbNoClindro6);
        RadioButton cilindro7 = (RadioButton) findViewById(R.id.rbNoClindro7);
        RadioButton cilindro8 = (RadioButton) findViewById(R.id.rbNoClindro8);
        EditText color = (EditText) findViewById(R.id.etxtColor);
        EditText colorInterior = (EditText) findViewById(R.id.etxtColorInterior);
        RadioButton condicionNuevo = (RadioButton) findViewById(R.id.rbNuevo);
        RadioButton condicionUsado = (RadioButton) findViewById(R.id.rbUsado);
        EditText filaAsientos = (EditText) findViewById(R.id.etxtFilaAsiento);
        RadioButton cantPuerta2 = (RadioButton) findViewById(R.id.rbCant2);
        RadioButton cantPuerta3 = (RadioButton) findViewById(R.id.rbCant3);
        RadioButton cantPuerta4 = (RadioButton) findViewById(R.id.rbCant4);
        RadioButton cantPuerta5 = (RadioButton) findViewById(R.id.rbCant5);
        RadioButton transmisionMec = (RadioButton) findViewById(R.id.rbmecanico);
        RadioButton transmisionAut = (RadioButton) findViewById(R.id.rbAutomatica);
        RadioButton transmisionSin = (RadioButton) findViewById(R.id.rbSincronizada);
        EditText cilindraje = (EditText) findViewById(R.id.etxtCilindraje);
        Switch garantia = (Switch) findViewById(R.id.swGaranita);

        for (Vehiculo varVehiculo : vehiculos) {
            chasis.setText(varVehiculo.getChasis());
            año.setText(varVehiculo.getAno());
            placa.setText(varVehiculo.getPlaca());
            tipo.setSelection(Integer.parseInt(varVehiculo.getIdTipoVehiculo()));
            marca.setText(varVehiculo.getDesc_marca());
            modelo.setSelection(getIndex(modelo, varVehiculo.getDesc_modelo()));
            estilo.setSelection(getIndex(estilo, varVehiculo.getDesc_estilo()));

            View vista = radioCombustible.findViewById(Integer.parseInt(varVehiculo.getIdCombustible()));
            RadioButton radioCom = (RadioButton) vista;
            radioCom.setChecked(true);

            switch (varVehiculo.getCilindros()) {

                case 2: {
                    cilindro2.setChecked(true);
                    break;
                }
                case 3: {
                    cilindro3.setChecked(true);
                    break;
                }
                case 4: {
                    cilindro4.setChecked(true);
                    break;
                }
                case 5: {
                    cilindro5.setChecked(true);
                    break;
                }
                case 6: {
                    cilindro6.setChecked(true);
                    break;
                }
                case 8: {
                    cilindro7.setChecked(true);
                    break;
                }
                default: {
                    cilindro8.setChecked(true);
                    break;
                }
            }


                switch (varVehiculo.getIdTraccion().toString()) {
                case "2WD": {
                    View vistaTraccion = radioTraccion.findViewById(Integer.parseInt("1"));
                    RadioButton radioTrac = (RadioButton) vistaTraccion;
                    radioTrac.setChecked(true);
                    break;
                }
                case "4WD": {
                    View vistaTraccion = radioTraccion.findViewById(Integer.parseInt("2"));
                    RadioButton radioTrac = (RadioButton) vistaTraccion;
                    radioTrac.setChecked(true);
                    break;
                }
                case "4WD FULL": {
                    View vistaTraccion = radioTraccion.findViewById(Integer.parseInt("3"));
                    RadioButton radioTrac = (RadioButton) vistaTraccion;
                    radioTrac.setChecked(true);
                    break;
                }

            }


            color.setText(varVehiculo.getColor());
            colorInterior.setText(varVehiculo.getColorInterior());
            if (varVehiculo.getEstadoVeh().equals("1") || varVehiculo.getEstadoVeh().equals("2")) {
                condicionNuevo.setChecked(true);
            } else {
                condicionUsado.setChecked(true);
            }

            filaAsientos.setText(varVehiculo.getFilaAsiento().toString());

            switch (Integer.parseInt(varVehiculo.getCantPuerta())) {
                case 2: {
                    cantPuerta2.setChecked(true);
                    break;
                }
                case 3: {
                    cantPuerta3.setChecked(true);
                    break;
                }
                case 4: {
                    cantPuerta4.setChecked(true);
                    break;
                }
                case 5: {
                    cantPuerta5.setChecked(true);
                    break;

                }
            }

            switch (Integer.parseInt(varVehiculo.getIdTransmision())) {
                case 1: {
                    transmisionMec.setChecked(true);
                    break;
                }
                case 2: {
                    transmisionAut.setChecked(true);
                    break;
                }
                case 3: {
                    transmisionSin.setChecked(true);
                    break;
                }

            }
            cilindraje.setText(varVehiculo.getCilindraje());

            if (varVehiculo.getGarantia().toString().equals("S")) {
                garantia.setChecked(true);
            }

/*
            if (!varVehiculo.getIdCliente().toString().equals(""))
            {

                ObtenerDatosFiltradosCliente(varVehiculo.getIdCliente().toString());
            }*/



        }

        // Toast.makeText(getApplicationContext()," "+ varVehiculo.getCilindros(),Toast.LENGTH_SHORT).show();

        chasis.setEnabled(false);
        año.setEnabled(false);
        placa.setEnabled(false);
        tipo.setEnabled(false);
        marca.setEnabled(false);
        modelo.setEnabled(false);
        estilo.setEnabled(false);
        radioCombustible.setSelected(false);
        radioTraccion.setSelected(false);
        cilindro2.setEnabled(false);
        cilindro3.setEnabled(false);
        cilindro4.setEnabled(false);
        cilindro5.setEnabled(false);
        cilindro6.setEnabled(false);
        cilindro7.setEnabled(false);
        cilindro8.setEnabled(false);
        color.setEnabled(false);
        colorInterior.setEnabled(false);
        condicionNuevo.setEnabled(false);
        condicionUsado.setEnabled(false);
        filaAsientos.setEnabled(false);
        cantPuerta2.setEnabled(false);
        cantPuerta3.setEnabled(false);
        cantPuerta4.setEnabled(false);
        cantPuerta5.setEnabled(false);
        transmisionAut.setEnabled(false);
        transmisionMec.setEnabled(false);
        transmisionSin.setEnabled(false);
        cilindraje.setEnabled(false);

    }
    //Para obtener el indice donde se encuentra el string en el spinner deseado

    private int getIndex(Spinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
                index = i;
            }
        }
        return index;
    }


    //Obtener Cliente teniendo su id

    private void ObtenerDatosFiltradosCliente(String s) {


        Call<Cliente> call = AdapterCliente.getFiltroClliente("id_cliente", s).getClientes();
        call.enqueue(new FiltroClienteCallback());



    }


    private class FiltroClienteCallback implements Callback<Cliente> {
        @Override
        public void onResponse(Call<Cliente> call, Response<Cliente> response) {
            if (response.isSuccessful()){
                ClienteResponse clienteResponse = response.body();
                llenarFormularioCliente(clienteResponse.getClientes());
            }

            else {
                Toast.makeText(getApplicationContext(), "Error en el formato de respuesta de vehiculo", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<Cliente> call, Throwable t) {
            Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            Log.v("Clii-->*** ===>", t.getMessage());

        }
    }

    private void llenarFormularioCliente(ArrayList<Cliente> clientes) {

        EditText nombres = (EditText) findViewById(R.id.etxtNombre);

        for(Cliente varCte : clientes)
        {
            nombres.setText(varCte.getNombres());
        }

    }
}
