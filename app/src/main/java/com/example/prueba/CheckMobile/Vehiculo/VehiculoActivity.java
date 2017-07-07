package com.example.prueba.CheckMobile.Vehiculo;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
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
import com.example.prueba.CheckMobile.VehiculoEstilo.myDialogEstilo;
import com.example.prueba.CheckMobile.VehiculoMarca.AdapterMarca;
import com.example.prueba.CheckMobile.VehiculoMarca.Marca;
import com.example.prueba.CheckMobile.VehiculoMarca.MarcaResponse;
import com.example.prueba.CheckMobile.VehiculoMarca.myDialogMarca;
import com.example.prueba.CheckMobile.VehiculoModelo.AdapterModelo;
import com.example.prueba.CheckMobile.VehiculoModelo.Modelo;
import com.example.prueba.CheckMobile.VehiculoModelo.ModeloResponse;
import com.example.prueba.CheckMobile.VehiculoModelo.myDialogModelo;
import com.example.prueba.CheckMobile.VehiculoTraccion.AdapterTraccion;
import com.example.prueba.CheckMobile.VehiculoTraccion.Traccion;
import com.example.prueba.CheckMobile.VehiculoTraccion.TraccionResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.id;
import static com.example.prueba.CheckMobile.VehiculoMarca.AdapterMarca.insertarMarca;


public class VehiculoActivity extends AppCompatActivity implements myDialogMarca.OnCompleteListener, myDialogModelo.OnCompleteListenerModelo, myDialogEstilo.OnCompleteListenerEstilo {
    Spinner spinnerEstilo;
    Spinner spinnerTipoVehiculo;
    Spinner spinnerModelo;
    RadioGroup radioCombustible, radioTraccion;
    ListView listaMarcas;
    EditText editTextReferencia;
    String valor;
    String nombreVehiculo;
    EditText chasis;
    EditText año;
    EditText placa;
    AutoCompleteTextView marca;
    RadioGroup rgCilindros;
    RadioGroup rGCantPuertas;
    RadioButton cilindro2;
    RadioButton cilindro3;
    RadioButton cilindro4;
    RadioButton cilindro5;
    RadioButton cilindro6;
    RadioButton cilindro7;
    RadioButton cilindro8;
    EditText color;
    EditText colorInterior;
    RadioButton condicionNuevo;
    RadioButton condicionUsado;
    EditText filaAsientos;
    RadioButton cantPuerta2;
    RadioButton cantPuerta3;
    RadioButton cantPuerta4;
    RadioButton cantPuerta5;
    RadioButton transmisionMec;
    RadioButton transmisionAut;
    RadioButton transmisionSin;
    EditText cilindraje;
    EditText nota;
    Switch swgarantia;
    private String idCliente, idTipoVeh, idMarca, idModelo, idEstilo, cantidadPuertas,
            idCombustible, idTraccion, estadoVeh, garantia, idTransmision, idVehiculo, refVehiculo,
            chasisVehiculo, descMarca, descModelo, descEstilo;
    private boolean insertar = true;
    int NumCilindro;
    private Timer timer = new Timer();
    private final long DELAY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        InicializacionVistasVehiculo();

        //Llamadas de los metodos
        obtenerDatosCombustilbe();
        obtenerDatosTraccion();
        obtenerDatosTipoVeh();
        obtenerDatosMarca();
        buscarChasis();
        obtenerCantidadPuertas();
        obtenerEstadoVeh();
        obtenerCilindros();
        obtenerTransmision();
        obtenerGarantia();

        //Para pasar de una actividad a otra
        Button btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (insertar) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(VehiculoActivity.this);
                    alertBuilder.setMessage("¿Está seguro de guardar los datos?")
                            .setCancelable(false)
                            .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    InsertarVehiculo();

                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });

                    AlertDialog alert = alertBuilder.create();
                    alert.setTitle("Guardar");
                    alert.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert));
                    alert.show();


                } else {
                    Intent intent = new Intent(VehiculoActivity.this.getApplicationContext(), ClienteActivity.class);
                    if (idCliente != null) {
                        intent.putExtra("CLIENTE", idCliente);
                    }
                    intent.putExtra("IDVEHICULO", idVehiculo);
                    intent.putExtra("VEHICULO", nombreVehiculo);
                    intent.putExtra("REFERENCIA", refVehiculo);
                    intent.putExtra("CHASIS", chasisVehiculo);
                    startActivity(intent);

                }
            }
        });


        FloatingActionButton fabcancel = (FloatingActionButton) findViewById(R.id.fabCancel);
        fabcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (!insertar) {
            VehiculoActivity.this.finish();
        } else {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(VehiculoActivity.this);
            alertBuilder.setMessage("¿Está seguro de salir?")
                    .setCancelable(false)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            VehiculoActivity.this.finish();
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

            AlertDialog alert = alertBuilder.create();
            alert.setTitle("Advertencia");
            alert.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert));
            alert.show();
        }
    }

    private void obtenerGarantia() {
        swgarantia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    garantia = "S";
                } else {
                    garantia = "N";
                }
            }
        });
    }

    private void obtenerCilindros() {

        rgCilindros.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case (R.id.rbNoClindro2): {
                        NumCilindro = Integer.parseInt(cilindro2.getText().toString());
                        break;
                    }
                    case (R.id.rbNoClindro3): {
                        NumCilindro = Integer.parseInt(cilindro3.getText().toString());
                        break;
                    }
                    case (R.id.rbNoClindro4): {
                        NumCilindro = Integer.parseInt(cilindro4.getText().toString());
                        break;
                    }
                    case (R.id.rbNoClindro5): {
                        NumCilindro = Integer.parseInt(cilindro5.getText().toString());
                        break;
                    }
                    case (R.id.rbNoClindro6): {
                        NumCilindro = Integer.parseInt(cilindro6.getText().toString());
                        break;
                    }
                    case (R.id.rbNoClindro7): {
                        NumCilindro = Integer.parseInt(cilindro7.getText().toString());
                        break;
                    }
                    case (R.id.rbNoClindro8): {
                        NumCilindro = Integer.parseInt(cilindro8.getText().toString());
                        break;
                    }
                }
            }
        });

    }

    private void obtenerTransmision() {
        transmisionMec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idTransmision = "1";
            }
        });

        transmisionAut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idTransmision = "2";
            }
        });
        transmisionSin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idTransmision = "3";
            }
        });


    }

    private void obtenerEstadoVeh() {
        condicionNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                estadoVeh = "1";
            }
        });
        condicionUsado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                estadoVeh = "3";
            }
        });
    }

    private void obtenerCantidadPuertas() {
        rGCantPuertas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rbCant2: {
                        cantidadPuertas = cantPuerta2.getText().toString();
                        break;
                    }
                    case R.id.rbCant3: {
                        cantidadPuertas = cantPuerta3.getText().toString();
                        break;
                    }
                    case R.id.rbCant4: {
                        cantidadPuertas = cantPuerta4.getText().toString();
                        break;
                    }
                    case R.id.rbCant5: {
                        cantidadPuertas = cantPuerta5.getText().toString();
                        break;
                    }
                }
            }
        });

    }


    private void InicializacionVistasVehiculo() {

        //Inicializacion de los objetos
        spinnerEstilo = (Spinner) findViewById(R.id.spinnerEstilo);
        registerForContextMenu(spinnerEstilo);
        spinnerTipoVehiculo = (Spinner) findViewById(R.id.spinnerTipoVeh);
        spinnerModelo = (Spinner) findViewById(R.id.spinnerModelo);
        registerForContextMenu(spinnerModelo);
        editTextReferencia = (EditText) findViewById(R.id.etxtReferencia);
        chasis = (EditText) findViewById(R.id.etxtChasis);
        año = (EditText) findViewById(R.id.etxtAnio);
        placa = (EditText) findViewById(R.id.etxtPlaca);
        marca = (AutoCompleteTextView) findViewById(R.id.etxtMarca);
        registerForContextMenu(marca);
        rgCilindros = (RadioGroup) findViewById(R.id.rGNumCilindros);
        cilindro2 = (RadioButton) findViewById(R.id.rbNoClindro2);
        cilindro3 = (RadioButton) findViewById(R.id.rbNoClindro3);
        cilindro4 = (RadioButton) findViewById(R.id.rbNoClindro4);
        cilindro5 = (RadioButton) findViewById(R.id.rbNoClindro5);
        cilindro6 = (RadioButton) findViewById(R.id.rbNoClindro6);
        cilindro7 = (RadioButton) findViewById(R.id.rbNoClindro7);
        cilindro8 = (RadioButton) findViewById(R.id.rbNoClindro8);
        color = (EditText) findViewById(R.id.etxtColor);
        colorInterior = (EditText) findViewById(R.id.etxtColorInterior);
        condicionNuevo = (RadioButton) findViewById(R.id.rbNuevo);
        condicionUsado = (RadioButton) findViewById(R.id.rbUsado);
        filaAsientos = (EditText) findViewById(R.id.etxtFilaAsiento);
        rGCantPuertas = (RadioGroup) findViewById(R.id.rGCantPuertas);
        cantPuerta2 = (RadioButton) findViewById(R.id.rbCant2);
        cantPuerta3 = (RadioButton) findViewById(R.id.rbCant3);
        cantPuerta4 = (RadioButton) findViewById(R.id.rbCant4);
        cantPuerta5 = (RadioButton) findViewById(R.id.rbCant5);
        transmisionMec = (RadioButton) findViewById(R.id.rbmecanico);
        transmisionAut = (RadioButton) findViewById(R.id.rbAutomatica);
        transmisionSin = (RadioButton) findViewById(R.id.rbSincronizada);
        cilindraje = (EditText) findViewById(R.id.etxtCilindraje);
        nota = (EditText) findViewById(R.id.etxtNota);
        swgarantia = (Switch) findViewById(R.id.swGaranita);
    }

    private void InsertarVehiculo() {
        ArrayList<Vehiculo> vehiculo = new ArrayList<Vehiculo>();
        vehiculo.add(new Vehiculo(
                "1",
                chasis.getText().toString(),
                idTipoVeh,
                idMarca,
                idModelo,
                idEstilo,
                nota.getText().toString().toUpperCase(),
                color.getText().toString().toUpperCase(),
                colorInterior.getText().toString().toUpperCase(),
                año.getText().toString(),
                filaAsientos.getText().toString(),
                cantidadPuertas,
                idCombustible,
                idTraccion,
                cilindraje.getText().toString(),
                editTextReferencia.getText().toString(),
                placa.getText().toString(),
                estadoVeh,
                NumCilindro,
                garantia,
                idTransmision));

        if (chasis.getText().toString() == null || editTextReferencia.getText().toString() == null
                || idMarca == null || idModelo == null || idEstilo == null || idTipoVeh == null ||
                color.getText().toString() == null || estadoVeh == null) {
            Toast.makeText(getApplicationContext(), "Faltan datos por llenar!!", Toast.LENGTH_LONG).show();
        } else {

            Call<String> vehiculoCall = AdapterVehiculo.setVehiculo().insertVehiculos(vehiculo);
            vehiculoCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        String[] p = response.body().toString().split(",");
                        if (p[0].equals("200")) {
                            idVehiculo = p[1];
                            Toast.makeText(getApplicationContext(), "Registros guardados con éxito", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(VehiculoActivity.this.getApplicationContext(), ClienteActivity.class);
                            if (idCliente != null) {
                                intent.putExtra("CLIENTE", idCliente);
                            }
                            refVehiculo = editTextReferencia.getText().toString();
                            chasisVehiculo = chasis.getText().toString();
                            intent.putExtra("VEHICULO", nombreVehiculo);
                            intent.putExtra("IDVEHICULO", idVehiculo);
                            intent.putExtra("REFERENCIA", refVehiculo);
                            intent.putExtra("CHASIS", chasisVehiculo);
                            InabilitarVistasVehiculo(false);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Error al guardar datos del vehiculo", Toast.LENGTH_SHORT).show();

                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.v("Error insercion ** ", t.getMessage());
                    Toast.makeText(getApplicationContext(), t.getMessage() + " Error al insertar vehiculo", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void buscarChasis() {

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

    private void obtenerDatosModelo(String valor) {

        Call<Modelo> call = AdapterModelo.getApiService("id_marca", valor).getModelos();
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

    @Override
    public void onComplete(String id) {

        this.idMarca = id;
        Log.d("ACTIVITY==>", idMarca);
    }

    @Override
    public void onCompleteDescripcion(String descripcion) {
        this.descMarca = descripcion;
        Log.d("ACTIVITY==>", descMarca);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        ArrayList<Marca> marcas = new ArrayList<Marca>();
        marcas.add(new Marca(
                descMarca
        ));

        Call<String> callMarca = insertarMarca().setMarca(marcas);
        callMarca.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String[] p = response.body().toString().split(",");
                    if (p[0].equals("200")) {
                        Toast.makeText(getApplicationContext(), "Marca guardada con éxito!", Toast.LENGTH_SHORT).show();
                        obtenerDatosMarca();
                        String id = p[1];

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error al guardar registro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error de respuesta", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });


    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }


    @Override
    public void onCompleteModelo(String idModelo) {
        this.idModelo = idModelo;
    }

    @Override
    public void onCompleteDescripcionModelo(String descripcion) {
        this.descModelo = descripcion;
    }

    @Override
    public void onDialogModeloPositiveClick(DialogFragment dialog) {

        final ArrayList<Modelo> modelos = new ArrayList<Modelo>();
        modelos.add(new Modelo(
                idMarca,
                descModelo
        ));

        Call<String> callModelo = AdapterModelo.insertarModelo().setModelo(modelos);
        callModelo.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String[] p = response.body().toString().split(",");

                    if (p[0].equals("200")) {
                        Toast.makeText(getApplicationContext(), "Modelo guardado con éxito!", Toast.LENGTH_SHORT).show();
                        obtenerDatosModelo(idMarca);
                        spinnerModelo.setSelection(getIndex(spinnerModelo, descModelo));

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error al guardar registro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error de respuesta", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onDialogModeloNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    @Override
    public void onCompleteEstilo(String idModelo) {

    }

    @Override
    public void onCompleteDescripcionEstilo(String descripcion) {
        this.descEstilo = descripcion;
    }

    @Override
    public void onDialogEstiloPositiveClick(DialogFragment dialog) {

        ArrayList<Estilo> estilos = new ArrayList<Estilo>();
        estilos.add(new Estilo(
                idModelo,
                descEstilo


        ));

        Call<String> callEstilo = AdapterEstilo.insertarEstilo().setEstilo(estilos);
        callEstilo.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String[] p = response.body().toString().split(",");
                    if (p[0].equals("200")) {
                        Toast.makeText(getApplicationContext(), "Estilo guardado con éxito!", Toast.LENGTH_SHORT).show();
                        obtenerDatosEstilo(idModelo);
                        spinnerEstilo.setSelection(getIndex(spinnerEstilo, descEstilo));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error al guardar registro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error de respuesta", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });


    }

    @Override
    public void onDialogEstiloNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
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
                MarcaResponse marcaRes = response.body();
                Log.d("PASE POR AQUI", "==>" + idMarca);
                poblarSpinnerMarca(marcaRes.getMarcas());

                //marcaResponse = marcaRes.getMarcas();
                // ObtenerDialogLista(marcaResponse.getMarcas());
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

    private void poblarSpinnerTipoVehiculo(final ArrayList<TipoVehiculo> tipo) {

        List<String> lista = new ArrayList<>();
        for (TipoVehiculo varTipo : tipo) {
            lista.add(varTipo.getDescripcion());
        }
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_style, lista);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_style);
        spinnerTipoVehiculo.setAdapter(spinnerArrayAdapter);

        spinnerTipoVehiculo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String descripcion = spinnerArrayAdapter.getItem(i);

                for (int j = 0; j < tipo.size(); j++) {
                    if (tipo.get(j).getDescripcion().equals(descripcion)) {
                        idTipoVeh = tipo.get(j).getIdTipoVehiculo();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void poblarSpinnerMarca(final ArrayList<Marca> marcas) {
        List<String> lista = new ArrayList<>();
        for (Marca varMarca : marcas) {
            lista.add(varMarca.getDescripcion());
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_style, lista);
        marca.setAdapter(arrayAdapter);
        marca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String descripcion = arrayAdapter.getItem(i);
                String id = null;
                for (int j = 0; j < marcas.size(); j++) {
                    if (marcas.get(j).getDescripcion().equals(descripcion)) {
                        id = marcas.get(j).getId();
                        idMarca = id;
                    }

                }
                obtenerDatosModelo(id);
            }
        });


    }


    private void poblarSpinnerEstilo(final ArrayList<Estilo> estilos) {
        List<String> lista = new ArrayList<>();
        for (Estilo varEstilo : estilos) {
            lista.add(varEstilo.getDescripcion());
        }

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_style, lista);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_style);
        spinnerEstilo.setAdapter(spinnerArrayAdapter);

        spinnerEstilo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String descripcion = spinnerArrayAdapter.getItem(i);

                for (int j = 0; j < estilos.size(); j++) {
                    if (estilos.get(j).getDescripcion().equals(descripcion)) {
                        idEstilo = estilos.get(j).getId();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void obtenerDatosEstilo(String valor) {

        Call<ArrayList<Estilo>> call = AdapterEstilo.getApiService("id_modelo", valor).getEstilos();
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

    private void poblarSpinnerModelo(final ArrayList<Modelo> modelos) {
        List<String> lista = new ArrayList<>();
        lista.add("");
        for (Modelo varModelo : modelos) {
            lista.add(varModelo.getDescripcion());
        }

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_style, lista);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_style);
        spinnerModelo.setAdapter(spinnerArrayAdapter);

        spinnerModelo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    String descripcion = spinnerArrayAdapter.getItem(i);
                    String id = null;

                    for (int j = 0; j < modelos.size(); j++) {
                        if (modelos.get(j).getDescripcion().equals(descripcion)) {
                            id = modelos.get(j).getId();
                            idModelo = id;
                        }
                    }

                    obtenerDatosEstilo(id);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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

        radioCombustible.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case 1: {
                        idCombustible = "1";
                        break;
                    }
                    case 2: {
                        idCombustible = "2";
                        break;
                    }
                    case 3: {
                        idCombustible = "3";
                        break;
                    }
                    case 4: {
                        idCombustible = "4";
                        break;
                    }
                    case 5: {
                        idCombustible = "5";
                        break;
                    }
                    case 6: {
                        idCombustible = "6";
                        break;
                    }
                    case 7: {
                        idCombustible = "7";
                        break;
                    }


                }

                //  Toast.makeText(getApplicationContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();
            }
        });

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
        radioTraccion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case 1: {
                        RadioButton vista = (RadioButton) radioGroup.findViewById(Integer.parseInt("1"));
                        idTraccion = vista.getText().toString();
                        break;
                    }

                    case 2: {
                        RadioButton vista = (RadioButton) radioGroup.findViewById(Integer.parseInt("2"));
                        idTraccion = vista.getText().toString();
                        break;
                    }
                    case 3: {
                        RadioButton vista = (RadioButton) radioGroup.findViewById(Integer.parseInt("3"));
                        idTraccion = vista.getText().toString();
                        break;
                    }

                }
            }
        });

    }


    private class FiltroVehiculoCallback implements Callback<Vehiculo> {
        @Override
        public void onResponse(Call<Vehiculo> call, Response<Vehiculo> response) {
            if (response.isSuccessful()) {
                VehiculoResponse vehiculoResponse = response.body();
                if (!vehiculoResponse.getVehiculos().isEmpty()) {
                    idCliente = null;
                    llenarFormularioVehiculo(vehiculoResponse.getVehiculos());
                    insertar = false;
                } else {
                    InabilitarVistasVehiculo(true);
                    limpiarVistas();
                    insertar = true;
                }
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

    private void limpiarVistas() {
        chasis.getText().clear();
        año.getText().clear();
        placa.getText().clear();
        spinnerTipoVehiculo.setSelected(false);
        marca.getText().clear();
        spinnerModelo.setAdapter(null);
        spinnerEstilo.setAdapter(null);

        for (int i = 0; i < radioCombustible.getChildCount(); i++) {
            View vista = radioCombustible.getChildAt(i);
            RadioButton radioCom = (RadioButton) vista;
            radioCom.setChecked(false);
        }

        for (int i = 0; i < rgCilindros.getChildCount(); i++) {
            View vista = rgCilindros.getChildAt(i);
            RadioButton radioCil = (RadioButton) vista;
            radioCil.setChecked(false);
        }

        for (int i = 0; i < radioTraccion.getChildCount(); i++) {
            View vista = radioTraccion.getChildAt(i);
            RadioButton radioCil = (RadioButton) vista;
            radioCil.setChecked(false);

        }

        color.getText().clear();
        colorInterior.getText().clear();
        condicionNuevo.setChecked(false);
        condicionUsado.setChecked(false);
        filaAsientos.getText().clear();
        cantPuerta2.setChecked(false);
        cantPuerta3.setChecked(false);
        cantPuerta4.setChecked(false);
        cantPuerta5.setChecked(false);
        transmisionMec.setChecked(false);
        transmisionAut.setChecked(false);
        transmisionSin.setChecked(false);
        cilindraje.getText().clear();
        nota.getText().clear();
        swgarantia.setChecked(false);
        idCliente = null;

    }


    private void llenarFormularioVehiculo(ArrayList<Vehiculo> vehiculos) {

        for (Vehiculo varVehiculo : vehiculos) {
            idVehiculo = varVehiculo.getId();
            chasisVehiculo = varVehiculo.getChasis().toString();
            refVehiculo = varVehiculo.getReferencia().toString();
            chasis.setText(varVehiculo.getChasis());
            año.setText(varVehiculo.getAno());
            placa.setText(varVehiculo.getPlaca());
            spinnerTipoVehiculo.setSelection(Integer.parseInt(varVehiculo.getIdTipoVehiculo()));
            marca.setText(varVehiculo.getDesc_marca());
            /////Spinner Modelo/////////
            List<String> lista = new ArrayList<>();
            lista.add(varVehiculo.getDesc_modelo());
            ArrayAdapter<String> spinnerAdapterModelo = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_style, lista);
            spinnerModelo.setAdapter(spinnerAdapterModelo);

            /////Spinner Estilo////
            List<String> listaEstilo = new ArrayList<>();
            listaEstilo.add(varVehiculo.getDesc_estilo());
            ArrayAdapter<String> spinnerAdapterEstilo = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_style, listaEstilo);
            spinnerEstilo.setAdapter(spinnerAdapterEstilo);
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
            nota.setText(varVehiculo.getNota());

            if (varVehiculo.getGarantia().toString().equals("S")) {
                swgarantia.setChecked(true);
            }

            nombreVehiculo = varVehiculo.getDesc_marca() + " " + varVehiculo.getDesc_modelo() + " " + varVehiculo.getDesc_estilo()
                    + " " + varVehiculo.getAno() + " (Ref." + varVehiculo.getReferencia() + ")";

            if (varVehiculo.getIdCliente() != null) {
                idCliente = varVehiculo.getIdCliente().toString();
            } else {
                idCliente = null;
            }
        }
        InabilitarVistasVehiculo(false);


    }

    private void InabilitarVistasVehiculo(boolean estado) {
        chasis.setEnabled(estado);
        año.setEnabled(estado);
        placa.setEnabled(estado);
        spinnerTipoVehiculo.setEnabled(estado);
        marca.setEnabled(estado);
        spinnerModelo.setEnabled(estado);
        spinnerEstilo.setEnabled(estado);

        for (int i = 0; i < radioCombustible.getChildCount(); i++) {
            radioCombustible.getChildAt(i).setEnabled(estado);
        }


        for (int i = 0; i < radioTraccion.getChildCount(); i++) {
            radioTraccion.getChildAt(i).setEnabled(estado);
        }
        cilindro2.setEnabled(estado);
        cilindro3.setEnabled(estado);
        cilindro4.setEnabled(estado);
        cilindro5.setEnabled(estado);
        cilindro6.setEnabled(estado);
        cilindro7.setEnabled(estado);
        cilindro8.setEnabled(estado);
        color.setEnabled(estado);
        colorInterior.setEnabled(estado);
        condicionNuevo.setEnabled(estado);
        condicionUsado.setEnabled(estado);
        filaAsientos.setEnabled(estado);
        cantPuerta2.setEnabled(estado);
        cantPuerta3.setEnabled(estado);
        cantPuerta4.setEnabled(estado);
        cantPuerta5.setEnabled(estado);
        transmisionAut.setEnabled(estado);
        transmisionMec.setEnabled(estado);
        transmisionSin.setEnabled(estado);
        cilindraje.setEnabled(estado);
        nota.setEnabled(estado);
        swgarantia.setEnabled(estado);


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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        switch (v.getId()) {
            case R.id.etxtMarca: {
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.menu_contextual_marca, menu);
                break;
            }
            case R.id.spinnerModelo: {
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.menu_contextual_modelo, menu);
                break;
            }
            case R.id.spinnerEstilo: {
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.menu_contextual_estilo, menu);
                break;
            }

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.action_add_marca: {
                myDialogMarca myDialogMarca = new myDialogMarca();
                myDialogMarca.show(getFragmentManager(), "Marca");
                return true;

            }
            case R.id.action_add_modelo: {
                if (idMarca == null) {
                    Toast.makeText(getApplicationContext(), "Debe seleccionar la marca", Toast.LENGTH_SHORT).show();
                } else {
                    myDialogModelo myDialogModelo = new myDialogModelo();
                    myDialogModelo.show(getFragmentManager(), "Modelo");
                }
                return true;
            }
            case R.id.action_add_estilo: {
                if (idModelo == null) {
                    Toast.makeText(getApplicationContext(), "Debe seleccionar el modelo", Toast.LENGTH_SHORT).show();
                } else {
                    myDialogEstilo myDialogEstilo = new myDialogEstilo();
                    myDialogEstilo.show(getFragmentManager(), "Estilo");
                }
                return true;
            }
        }
        return super.onContextItemSelected(item);
    }
}
