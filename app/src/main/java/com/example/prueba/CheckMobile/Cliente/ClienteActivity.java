package com.example.prueba.CheckMobile.Cliente;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
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
import com.example.prueba.CheckMobile.Inspeccion.MainInspeccionActivity;
import com.example.prueba.CheckMobile.Pais.AdapterPais;
import com.example.prueba.CheckMobile.Pais.Pais;
import com.example.prueba.CheckMobile.Pais.PaisResponse;
import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.TablaDgii.AdapterDgii;
import com.example.prueba.CheckMobile.TablaDgii.TablaDgii;
import com.example.prueba.CheckMobile.TablaDgii.TablaDgiiResponse;
import com.example.prueba.CheckMobile.Vehiculo.AdapterVehiculo;
import com.example.prueba.CheckMobile.Vehiculo.VehiculoActivity;

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

import static android.R.attr.format;

public class ClienteActivity extends AppCompatActivity implements View.OnClickListener {
    //Vistas

    LinearLayout layoutEmpresa;
    LinearLayout layoutSegunDGII;
    RadioButton radioEmpresa;
    RadioButton radioPersona;
    TextView txtDocIdentidad;
    TextView txtNombre;
    TextView txtapellido;
    Spinner spinnerNacionalidad;
    Spinner spinnerPais;
    Spinner spinnerCondicion;
    EditText nombres;
    EditText apellidos;
    EditText apodo;
    EditText etxtDocIdentidad;
    EditText nombreSegunDGII;
    EditText nombreEmpresa;
    EditText fechaNac;
    EditText edad;
    RadioButton sexoF;
    RadioButton sexoM;
    EditText provincia;
    EditText direccion;
    EditText proximoA;
    EditText telefono;
    EditText celular;
    EditText email;
    EditText limiteCredito;
    EditText notas;

    // /Parametros

    String nombreVehiculo = null, nombreCliente = null, idCliente = null, idVehiculo = null, refVehiculo, chasisVehiculo;
    private Timer timer = new Timer();
    private final long DELAY = 0;
    String valor;
    int numCaracteres = 0;
    int contador = 0;
    boolean insertar = true;
    String valorSexo, idCondicion, desc_nacionalidad, desc_pais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCliente);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        inicializacionVistas();

        ObtenerValorSexo();
        CalcularEdad();
        ObtenerDatosNacionalidad();
        ObtenerDatosCondicion();

        radioPersona.setOnClickListener(this);
        radioEmpresa.setOnClickListener(this);
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();

        if (extra != null) {
            idCliente = extra.getString("CLIENTE");
            nombreVehiculo = extra.getString("VEHICULO");
            idVehiculo = extra.getString("IDVEHICULO");
            refVehiculo = extra.getString("REFERENCIA");
            chasisVehiculo = extra.getString("CHASIS");
        }
        if (idCliente != null) {
            insertar = false;
            ObtenerDatosFiltradosCliente(idCliente);
            radioEmpresa.setEnabled(false);
            radioPersona.setEnabled(false);
            etxtDocIdentidad.setEnabled(false);
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        } else {

            buscarCliente();
        }

        Button btnClienteSiguiente = (Button) findViewById(R.id.btnClienteSiguiente);
        btnClienteSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (insertar) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ClienteActivity.this);
                    alertBuilder.setMessage("¿Está seguro de guardar los datos?")
                            .setCancelable(false)
                            .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    InsertarCliente();


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

                    Intent intent = new Intent(ClienteActivity.this.getApplicationContext(), MainInspeccionActivity.class);
                    intent.putExtra("IDVEHICULO", idVehiculo);
                    intent.putExtra("VEHICULO", nombreVehiculo);
                    intent.putExtra("IDCLIENTE", idCliente);
                    intent.putExtra("CLIENTE", nombreCliente);
                    intent.putExtra("REFERENCIA", refVehiculo);
                    intent.putExtra("CHASIS", chasisVehiculo);
                    ActualizaVehiculoCliente(idCliente, idVehiculo);
                    etxtDocIdentidad.setEnabled(false);
                    radioEmpresa.setEnabled(false);
                    radioPersona.setEnabled(false);
                    InhabilitarVistas(false);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!insertar) {
            ClienteActivity.this.finish();
        }
        else {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ClienteActivity.this);
            alertBuilder.setMessage("¿Está seguro de salir?")
                    .setCancelable(false)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            ClienteActivity.this.finish();
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
    private void ActualizaVehiculoCliente(String idCte, String idVeh) {

        String parametro = idCte + "," + idVeh;
        Call<String> callUpdate = AdapterVehiculo.setUpdateService(parametro).updatesVehiculos();
        callUpdate.enqueue(new updateVehiculoCallback());

    }

    private void CalcularEdad() {


        fechaNac.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
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
                if (s.length() >= 10) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    try {
                                        Calendar cal = Calendar.getInstance();
                                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                        Date myDate = null;
                                        try {
                                            myDate = df.parse(fechaNac.getText().toString());
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        cal.setTime(myDate);
                                        if (insertar) {
                                            edad.setText(String.valueOf(calculaEdad(cal)));
                                        }

                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), "Error en formato de fecha!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }

                    }, DELAY);
                }
            }
        });
       /* fechaNac.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                try {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        // Perform action on key press
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                        Date myDate = null;
                        try {
                            myDate = df.parse(fechaNac.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        cal.setTime(myDate);

                        edad.setText(String.valueOf(calculaEdad(cal)));
                        fechaNac.setNextFocusDownId(R.id.rbFemenino);
                        return true;
                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error en formato de fecha!", Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });
*/

    }

    private int calculaEdad(Calendar fechaNac) {
        Calendar today = Calendar.getInstance();

        int diff_year = today.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
        int diff_month = today.get(Calendar.MONTH) - fechaNac.get(Calendar.MONTH);
        int diff_day = today.get(Calendar.DAY_OF_MONTH) - fechaNac.get(Calendar.DAY_OF_MONTH);

        //Si está en ese año pero todavía no los ha cumplido
        if (diff_month < 0 || (diff_month == 0 && diff_day < 0)) {
            diff_year = diff_year - 1; //no aparecían los dos guiones del postincremento :|
        }
        return diff_year;
    }

    private void ObtenerValorSexo() {

        sexoF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valorSexo = "F";
            }
        });

        sexoM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valorSexo = "M";
            }
        });

    }

    private void InsertarCliente() {

        String fechaNacimiento = fechaNac.getText().toString();
        String date = null;
        try {

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date newDate = null;
            try {
                newDate = format.parse(fechaNacimiento);
            } catch (Exception e) {
                Log.d("TAG", e.getMessage());
            }

            format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.format(newDate);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        if (radioPersona.isChecked()) {
            clientes.add(new Cliente(
                    "1",
                    nombres.getText().toString().toUpperCase(),
                    apellidos.getText().toString().toUpperCase(),
                    Integer.parseInt(edad.getText().toString()),
                    valorSexo,
                    etxtDocIdentidad.getText().toString(),
                    null,
                    null,
                    idCondicion,
                    desc_nacionalidad,
                    apodo.getText().toString().toUpperCase(),
                    notas.getText().toString().toUpperCase(),
                    desc_pais,
                    date,
                    limiteCredito.getText().toString(),
                    telefono.getText().toString(),
                    celular.getText().toString(),
                    email.getText().toString().toUpperCase(),
                    direccion.getText().toString().toUpperCase(),
                    provincia.getText().toString().toUpperCase(),
                    proximoA.getText().toString().toUpperCase()));

            if (etxtDocIdentidad.getText().toString() == null || nombres.getText().toString() == null
                    || apellidos.getText().toString() == null || idCondicion == null || fechaNac.getText().toString() == null
                    || edad.getText().toString() == null || direccion.getText().toString() == null) {

                Toast.makeText(getApplicationContext(), "Faltan datos por llenar", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("TAG", "cliente == >" + clientes);
                nombreCliente = nombres.getText().toString().toUpperCase() + " " + apellidos.getText().toString().toUpperCase();
                Call<String> clienteCall = AdapterCliente.setCliente().insertClientes(clientes);
                clienteCall.enqueue(new InsertClienteCallback());
            }

        } else if (radioEmpresa.isChecked()) {
            clientes.add(new Cliente(
                    "1",
                    nombres.getText().toString().toUpperCase(),
                    apellidos.getText().toString().toUpperCase(),
                    Integer.parseInt(edad.getText().toString()),
                    valorSexo,
                    null,
                    etxtDocIdentidad.getText().toString(),
                    nombreEmpresa.getText().toString().toUpperCase(),
                    idCondicion,
                    desc_nacionalidad,
                    apodo.getText().toString().toUpperCase(),
                    notas.getText().toString().toUpperCase(),
                    desc_pais,
                    date,
                    limiteCredito.getText().toString(),
                    telefono.getText().toString(),
                    celular.getText().toString(),
                    email.getText().toString().toUpperCase(),
                    direccion.getText().toString().toUpperCase(),
                    provincia.getText().toString().toUpperCase(),
                    proximoA.getText().toString().toUpperCase()));

            if (etxtDocIdentidad.getText().toString() == null || nombres.getText().toString() == null
                    || apellidos.getText().toString() == null || idCondicion == null || fechaNac.getText().toString() == null
                    || edad.getText().toString() == null || nombreEmpresa.getText().toString() == null || direccion.getText().toString() == null) {

                Toast.makeText(getApplicationContext(), "Faltan datos por llenar", Toast.LENGTH_SHORT).show();
            } else {
                nombreCliente = nombreEmpresa.getText().toString();
                Call<String> clienteCall = AdapterCliente.setCliente().insertClientes(clientes);
                clienteCall.enqueue(new InsertClienteCallback());
            }
        }
    }

    private void buscarCliente() {
        etxtDocIdentidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
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
                if (s.length() >= numCaracteres) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    valor = etxtDocIdentidad.getText().toString();
                                    //  Toast.makeText(getApplicationContext(), valor + " Con-> " + contador + " largo -> " + s.length(), Toast.LENGTH_SHORT).show();
                                    obtenerClientePorDocIdentidad(valor);
                                }
                            });
                        }

                    }, DELAY);
                }


            }
        });
    }

    //Obtener Cliente teniendo su Documento de identidad

    private void obtenerClientePorDocIdentidad(String valor) {
        contador++;
        if (contador == 1) {
            Call<Cliente> call = AdapterCliente.getFiltroClliente("documento_identidad", valor).getClientes();
            call.enqueue(new FiltroClienteCallback());
        }

    }

    private void obtenerClienteFiltrado(String valor) {
        Call<TablaDgii> call = AdapterDgii.getApiService("rnc_cedula", valor).getCliente();
        call.enqueue(new FiltroTablaDgiiCallback());

    }

    private void inicializacionVistas() {
        layoutEmpresa = (LinearLayout) findViewById(R.id.layoutNombreEmpresa);
        layoutSegunDGII = (LinearLayout) findViewById(R.id.layoutNombreSegunDgii);
        layoutSegunDGII.setVisibility(View.GONE);
        layoutEmpresa.setVisibility(View.GONE);
        radioEmpresa = (RadioButton) findViewById(R.id.rbEmpresa);
        radioPersona = (RadioButton) findViewById(R.id.rbPersona);
        radioPersona.setChecked(true);
        numCaracteres = 11;
        nombreSegunDGII = (EditText) findViewById(R.id.etxtNombreSegundDgii);
        nombreSegunDGII.setEnabled(false);
        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtapellido = (TextView) findViewById(R.id.txtApellido);
        txtDocIdentidad = (TextView) findViewById(R.id.txtDocIdentidad);
        etxtDocIdentidad = (EditText) findViewById(R.id.etxtDocIdentidad);
        spinnerNacionalidad = (Spinner) findViewById(R.id.spNacionalidad);
        spinnerPais = (Spinner) findViewById(R.id.spPais);
        spinnerCondicion = (Spinner) findViewById(R.id.spCondicion);
        nombres = (EditText) findViewById(R.id.etxtNombre);
        apellidos = (EditText) findViewById(R.id.etxtApellido);
        apodo = (EditText) findViewById(R.id.etxtApodo);
        nombreEmpresa = (EditText) findViewById(R.id.etxtNombreEmpresa);
        fechaNac = (EditText) findViewById(R.id.etxtFechaNacimiento);
        edad = (EditText) findViewById(R.id.etxtEdad);
        sexoM = (RadioButton) findViewById(R.id.rbMasculino);
        sexoF = (RadioButton) findViewById(R.id.rbFemenino);
        provincia = (EditText) findViewById(R.id.etxtProvincia);
        direccion = (EditText) findViewById(R.id.etxtDireccion);
        proximoA = (EditText) findViewById(R.id.etxtProximoA);
        telefono = (EditText) findViewById(R.id.etxtTelefono);
        celular = (EditText) findViewById(R.id.etxtCelular);
        email = (EditText) findViewById(R.id.etxtCorreo);
        limiteCredito = (EditText) findViewById(R.id.etxtLimite);
        notas = (EditText) findViewById(R.id.etxtNotasCliente);

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
                txtDocIdentidad.setText("RNC ");
                layoutEmpresa.setVisibility(View.VISIBLE);
                etxtDocIdentidad.setHint("RNC");
                etxtDocIdentidad.setFilters(new InputFilter[]{new InputFilter.LengthFilter(9)});
                numCaracteres = 9;
                etxtDocIdentidad.getText().clear();
                limpiarVistasCliente();
                InhabilitarVistas(true);
                contador = 0;
                buscarCliente();

                break;
            case R.id.rbPersona:
                radioEmpresa.setSelected(true);
                txtNombre.setText("Nombres ");
                txtapellido.setText("Apellidos ");
                txtDocIdentidad.setText("Documento Identidad ");
                layoutEmpresa.setVisibility(View.GONE);
                etxtDocIdentidad.setHint("Cédula/Pasaporte");
                etxtDocIdentidad.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
                numCaracteres = 11;
                etxtDocIdentidad.getText().clear();
                limpiarVistasCliente();
                InhabilitarVistas(true);
                contador = 0;
                buscarCliente();
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
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_style, listaNacionalidad);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_style);
        spinnerNacionalidad.setAdapter(spinnerArrayAdapter);


        final ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_style, listaPaises);
        spinnerArrayAdapter2.setDropDownViewResource(R.layout.spinner_style);
        spinnerPais.setAdapter(spinnerArrayAdapter2);

        spinnerNacionalidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                desc_nacionalidad = spinnerArrayAdapter.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerPais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                desc_pais = spinnerArrayAdapter2.getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private class CondicionPagoCallback implements retrofit2.Callback<CondicionPago> {
        @Override
        public void onResponse(Call<CondicionPago> call, Response<CondicionPago> response) {
            if (response.isSuccessful()) {
                CondicionResponse condicionResponse = response.body();
                poblarSpinnerCondicion(condicionResponse.getCondicionPagos());
            } else {
                Toast.makeText(getApplicationContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<CondicionPago> call, Throwable t) {
            Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            Log.v("Ahi ===>", t.getMessage());
        }
    }

    private void poblarSpinnerCondicion(final ArrayList<CondicionPago> condicionPagos) {
        List<String> listaCondicion = new ArrayList<>();

        for (CondicionPago varCondicion : condicionPagos) {
            listaCondicion.add(varCondicion.getDescripcion());
        }
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_style, listaCondicion);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_style);
        spinnerCondicion.setAdapter(spinnerArrayAdapter);

        spinnerCondicion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String descripcion = spinnerArrayAdapter.getItem(i);

                for (int j = 0; j < condicionPagos.size(); j++) {
                    if (condicionPagos.get(j).getDescripcion().equals(descripcion)) {
                        idCondicion = condicionPagos.get(j).getId();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    //Obtener Cliente teniendo su id

    private void ObtenerDatosFiltradosCliente(String s) {
        Call<Cliente> call = AdapterCliente.getFiltroClliente("id_cliente", s).getClientes();
        call.enqueue(new FiltroClienteCallback());

    }

    private class FiltroClienteCallback implements Callback<Cliente> {
        @Override
        public void onResponse(Call<Cliente> call, Response<Cliente> response) {
            if (response.isSuccessful()) {
                ClienteResponse clienteResponse = response.body();
                if (clienteResponse.getClientes().isEmpty()) {
                    insertar = true;
                    limpiarVistasCliente();
                    InhabilitarVistas(true);
                    obtenerClienteFiltrado(valor);

                } else {
                    insertar = false;
                    layoutSegunDGII.setVisibility(View.GONE);
                    etxtDocIdentidad.getText().clear();
                    llenarFormularioCliente(clienteResponse.getClientes());
                }


            } else {
                Toast.makeText(getApplicationContext(), "Error en el formato de respuesta de cliente", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<Cliente> call, Throwable t) {
            Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            Log.v("VEHICULO-->*** ===>", t.getMessage());

        }
    }
    private void limpiarVistasCliente() {
        nombreEmpresa.getText().clear();
        nombreSegunDGII.getText().clear();
        nombres.getText().clear();
        apellidos.getText().clear();
        edad.getText().clear();
        fechaNac.getText().clear();
        sexoF.setChecked(false);
        sexoM.setChecked(false);
        spinnerNacionalidad.setSelected(false);
        spinnerPais.setSelected(false);
        provincia.getText().clear();
        direccion.getText().clear();
        proximoA.getText().clear();
        telefono.getText().clear();
        celular.getText().clear();
        email.getText().clear();
        spinnerCondicion.setSelected(false);
        limiteCredito.getText().clear();

    }

    private void llenarFormularioCliente(ArrayList<Cliente> clientes) {
        for (Cliente varCte : clientes) {
            idCliente = varCte.getId().toString();
            if (varCte.getRnc() != null && varCte.getDocumentoIdentidad() == null) {
                radioEmpresa.setChecked(true);
                layoutEmpresa.setVisibility(View.VISIBLE);
                nombreEmpresa.setText(varCte.getNombreEmpresa());
                etxtDocIdentidad.setText(varCte.getRnc());
                nombreCliente = varCte.getNombreEmpresa();
            } else {
                nombreCliente = varCte.getNombres() + " " + varCte.getApellidos();
                etxtDocIdentidad.setText(varCte.getDocumentoIdentidad());
                layoutEmpresa.setVisibility(View.GONE);
            }
            nombres.setText(varCte.getNombres().toUpperCase());
            apellidos.setText(varCte.getApellidos().toUpperCase());
            Log.v("EDAD==>", Integer.toString(varCte.getEdad()));
            apodo.setText(varCte.getApodo());
            edad.setText(Integer.toString(varCte.getEdad()));
            if (varCte.getfechaNac() != null) {
                fechaNac.setText(varCte.getfechaNac().substring(0, 10));
            }
            if (varCte.getSexo().equals("F")) {
                sexoF.setChecked(true);
            } else {
                sexoM.setChecked(true);
            }
            spinnerNacionalidad.setSelection(getIndex(spinnerNacionalidad, varCte.getNacionalidad()));
            spinnerPais.setSelection(getIndex(spinnerPais, varCte.getPais()));
            provincia.setText(varCte.getCiudad_provincia());
            direccion.setText(varCte.getLinea1());
            proximoA.setText(varCte.getLinea2());
            telefono.setText(varCte.getTelefono());
            celular.setText(varCte.getTelefono_movil());
            email.setText(varCte.getDireccion_email());
            spinnerCondicion.setSelection(getIndex(spinnerCondicion, varCte.getDescripcion_condicion()));
            limiteCredito.setText(varCte.getLimiteCredito());
            InhabilitarVistas(false);
        }
    }

    private void InhabilitarVistas(boolean estado) {

        nombreEmpresa.setEnabled(estado);
        nombres.setEnabled(estado);
        apellidos.setEnabled(estado);
        apodo.setEnabled(estado);
        fechaNac.setEnabled(estado);
        edad.setEnabled(estado);
        sexoM.setEnabled(estado);
        sexoF.setEnabled(estado);
        spinnerNacionalidad.setEnabled(estado);
        spinnerPais.setEnabled(estado);
        provincia.setEnabled(estado);
        direccion.setEnabled(estado);
        proximoA.setEnabled(estado);
        telefono.setEnabled(estado);
        celular.setEnabled(estado);
        email.setEnabled(estado);
        spinnerCondicion.setEnabled(estado);
        limiteCredito.setEnabled(estado);

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


    private class FiltroTablaDgiiCallback implements Callback<TablaDgii> {
        @Override
        public void onResponse(Call<TablaDgii> call, Response<TablaDgii> response) {
            if (response.isSuccessful()) {
                TablaDgiiResponse tablaDgiiResponse = response.body();
                if (tablaDgiiResponse.getClienteDgii().isEmpty()) {
                    limpiarVistasCliente();
                    InhabilitarVistas(true);
                    layoutSegunDGII.setVisibility(View.GONE);
                } else {
                    layoutSegunDGII.setVisibility(View.VISIBLE);
                    nombreSegunDGII.setText(tablaDgiiResponse.getClienteDgii().get(0).getRazonSocial());
                }
            } else {
                Toast.makeText(getApplicationContext(), "Error en el formato de respuesta en Dgii", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<TablaDgii> call, Throwable t) {
            Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            Log.v("Clii-->*** ===>", t.getMessage());

        }
    }

    private class InsertClienteCallback implements Callback<String> {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            if (response.isSuccessful()) {
                String[] p = response.body().toString().split(",");
                if (p[0].equals("200")) {
                    idCliente = p[1];
                    Toast.makeText(getApplicationContext(), "Registros guardados con éxito", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ClienteActivity.this.getApplicationContext(), MainInspeccionActivity.class);
                    intent.putExtra("IDVEHICULO", idVehiculo);
                    intent.putExtra("VEHICULO", nombreVehiculo);
                    intent.putExtra("IDCLIENTE", idCliente);
                    intent.putExtra("CLIENTE", nombreCliente);
                    intent.putExtra("REFERENCIA", refVehiculo);
                    intent.putExtra("CHASIS", chasisVehiculo);
                    ActualizaVehiculoCliente(idCliente, idVehiculo);
                    etxtDocIdentidad.setEnabled(false);
                    radioEmpresa.setEnabled(false);
                    radioPersona.setEnabled(false);
                    InhabilitarVistas(false);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Error al guardar datos del cliente", Toast.LENGTH_SHORT).show();

                }
            } else {
                Toast.makeText(getApplicationContext(), "Error al guardar datos del cliente", Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            Log.v("Error insercion ** ", t.getMessage());
            Toast.makeText(getApplicationContext(), t.getMessage() + " Error de respuesta", Toast.LENGTH_SHORT).show();

        }
    }

    private class updateVehiculoCallback implements Callback<String> {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            if (response.isSuccessful()) {
                if (response.toString().equals("200")) {
                    Toast.makeText(getApplicationContext(), "Cliente Actualizado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Datos verificados", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Error al guardar datos", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            Toast.makeText(getApplicationContext(), "Error de respuesta", Toast.LENGTH_SHORT).show();
            Log.v("Act---> ", t.getMessage());

        }
    }
}