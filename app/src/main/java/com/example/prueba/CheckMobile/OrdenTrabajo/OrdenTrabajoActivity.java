package com.example.prueba.CheckMobile.OrdenTrabajo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.CondicionPago.AdapterCondicion;
import com.example.prueba.CheckMobile.CondicionPago.CondicionPago;
import com.example.prueba.CheckMobile.CondicionPago.CondicionResponse;
import com.example.prueba.CheckMobile.Inspeccion.AdapterInspeccion;
import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.RepresentanteVenta.AdapterRepresentante;
import com.example.prueba.CheckMobile.RepresentanteVenta.RepresentanteService;
import com.example.prueba.CheckMobile.RepresentanteVenta.RepresentanteVenta;
import com.example.prueba.CheckMobile.RepresentanteVenta.RepresentanteVentaResponse;
import com.example.prueba.CheckMobile.TipoTransaccion.AdapterTipoTransaccion;
import com.example.prueba.CheckMobile.TipoTransaccion.TipoTransaccion;
import com.example.prueba.CheckMobile.Vehiculo.VehiculoService;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.data;
import static android.text.TextUtils.concat;
import static java.security.AccessController.getContext;

public class OrdenTrabajoActivity extends AppCompatActivity {

    //mis Views
    ImageButton buscarServicio;
    ListView listaServicios;
    ArrayAdapter<String> arrayAdapter;
    TextView txtOrden;
    TextView txtNombreCliente;
    TextView txtNombreVehiculo;
    EditText editTextObservaciones;
    EditText fechaOrden;
    Spinner spinnerMecanico;
    Spinner spinnerCondicion;

    //mis variables
    int requestCode = 1;
    String idProducto, descProducto, idInspeccion, nombreCliente, nombreVehiculo, idCondicion, idCliente, idMecanico;
    String idOrden;
    List<String> listaIdProductos = new ArrayList<>();
    List<String> listaDescProductos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orden_trabajo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarOrden);
        setSupportActionBar(toolbar);
        //Inicializacion vistas
        buscarServicio = (ImageButton) findViewById(R.id.ibBuscarServicio);
        listaServicios = (ListView) findViewById(R.id.listaAddServicios);
        txtOrden = (TextView) findViewById(R.id.textViewOrden);
        txtNombreCliente = (TextView) findViewById(R.id.textViewDatosCliente);
        txtNombreVehiculo = (TextView) findViewById(R.id.textViewDatosVehiculo);
        editTextObservaciones = (EditText) findViewById(R.id.editTextObervaciones);
        spinnerMecanico = (Spinner) findViewById(R.id.spinnerMec);
        spinnerCondicion = (Spinner) findViewById(R.id.spCondicion2);
        fechaOrden = (EditText) findViewById(R.id.etxtFechaOrden);


        //obtentiendo datos de intent
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        if (extra != null) {
            idInspeccion = extra.getString("IDINSPECCION");
            nombreCliente = extra.getString("CLIENTE");
            nombreVehiculo = extra.getString("VEHICULO");
            idCondicion = extra.getString("IDCONDICION");
            idCliente = extra.getString("IDCLIENTE");
        }

        //Evento click buscar
        buscarServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrdenTrabajoActivity.this, FiltroProductoActivity.class);
                startActivityForResult(intent, requestCode);
            }
        });

//Llenando datos de la inspeccion


        txtNombreVehiculo.setText("VEHICULO >> " + nombreVehiculo);
        txtNombreCliente.setText("CLIENTE >> " + nombreCliente);
        ObtenerDatosMecanicos("1");
        ObtenerDatosCondicion();
        ObtenerSecuencia("OTT");
        //Evento click de la lista para remover los elementos
        listaServicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String producto = (String) adapterView.getItemAtPosition(i);
                for (int j = 0; j < listaDescProductos.size(); j++) {
                    if (listaDescProductos.get(i).equals(producto)) {
                        listaDescProductos.remove(i);
                        listaIdProductos.remove(i);
                        break;
                    }
                }
                arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_style, listaDescProductos);
                listaServicios.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();

            }
        });

    }

    private void ObtenerSecuencia(String valor) {

        Call<ArrayList<TipoTransaccion>> callTransaccion = AdapterTipoTransaccion.getTransaccion("id_tipo_trans", valor).getTipoTransaccion();
        callTransaccion.enqueue(new TransaccionCallback());
    }

    private void ObtenerDatosCondicion() {

        Call<CondicionPago> call = AdapterCondicion.getApiService().getCondicionPagos();
        call.enqueue(new Callback<CondicionPago>() {
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
                Log.v("Condicion ===>", t.getMessage());
            }
        });
    }

    private void poblarSpinnerCondicion(final ArrayList<CondicionPago> condicionPagos) {
        List<String> listaCondicion = new ArrayList<>();

        for (CondicionPago varCondicion : condicionPagos) {
            listaCondicion.add(varCondicion.getDescripcion());
        }
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_style, listaCondicion);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_style);
        spinnerCondicion.setAdapter(spinnerArrayAdapter);

        for (int j = 0; j < condicionPagos.size(); j++) {
            if (condicionPagos.get(j).getId().equals(idCondicion)) {
                spinnerCondicion.setSelection(j);

            }
        }

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


    private void ObtenerDatosMecanicos(String valor) {
        Call<RepresentanteVenta> callMecancio = AdapterRepresentante.getApiService("mecanico", valor).getMecanico();
        callMecancio.enqueue(new Callback<RepresentanteVenta>() {
            @Override
            public void onResponse(Call<RepresentanteVenta> call, Response<RepresentanteVenta> response) {
                if (response.isSuccessful()) {
                    RepresentanteVentaResponse representante = response.body();
                    poblarSpinnerMecanico(representante.getRepresentanteVentas());
                }
            }

            @Override
            public void onFailure(Call<RepresentanteVenta> call, Throwable t) {

            }
        });

    }

    private void poblarSpinnerMecanico(final ArrayList<RepresentanteVenta> representanteVentas) {
        List<String> listaMecanico = new ArrayList<>();
        for (RepresentanteVenta var : representanteVentas) {
            listaMecanico.add(var.getNombres() + " " + var.getApellidos());
        }
        final ArrayAdapter<String> adapterMecanico = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_style, listaMecanico);
        adapterMecanico.setDropDownViewResource(R.layout.spinner_style);
        this.spinnerMecanico.setAdapter(adapterMecanico);

        spinnerMecanico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String mecanico = adapterMecanico.getItem(i);
                for (int j = 0; j < representanteVentas.size(); j++) {
                    if ((representanteVentas.get(j).getNombres().toUpperCase() + " " + representanteVentas.get(j).getApellidos().toUpperCase()).equals(mecanico)) {
                        idMecanico = representanteVentas.get(j).getId();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            if (data != null) {
                idProducto = data.getStringExtra("PRODUCTO");
                descProducto = data.getStringExtra("DESCRIPCION");

                if (idProducto != null && descProducto != null) {
                    if (listaIdProductos.contains(idProducto) && listaDescProductos.contains(descProducto)) {
                        Toast.makeText(getApplicationContext(), "Este servicio ya fue agregado", Toast.LENGTH_SHORT).show();
                    } else {

                        listaIdProductos.add(idProducto);
                        listaDescProductos.add(descProducto);
                        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_style, listaDescProductos);
                        arrayAdapter.setDropDownViewResource(R.layout.spinner_style);
                        listaServicios.setAdapter(arrayAdapter);
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
                Log.d("ORDEN==>", listaDescProductos.toString());

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main_guardar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(OrdenTrabajoActivity.this);
            alertBuilder.setMessage("¿Está seguro de guardar los datos?")
                    .setCancelable(false)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            GuardarOrdenTrabajo();
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

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void GuardarOrdenTrabajo() {
        ArrayList<OrdenTrabajoEnc> ordenes = new ArrayList<OrdenTrabajoEnc>();
        ordenes.add(new OrdenTrabajoEnc(
                idCliente,
                nombreCliente,
                null,
                "1",
                "1",
                "0",
                "0",
                "0",
                "0",
                "0",
                editTextObservaciones.getText().toString().toUpperCase(),
                idCondicion,
                "RD",
                idMecanico,
                fechaOrden.getText().toString(),
                "1",
                idInspeccion
        ));

        Call<String> callOrden = AdapterOrdenTrabajo.setApiService().setOrdenTrabajo(ordenes);
        callOrden.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String[] p = response.body().split(",");
                    idOrden = p[1];
                    if (p[0].equals("200")) {
                        Toast.makeText(getApplicationContext(), "Orden guardada con éxito!", Toast.LENGTH_SHORT).show();
                       // guardarOrdenDetalle();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Error al guardar los datos", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Error de respuesta al guardar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error ", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void guardarOrdenDetalle() {



    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(OrdenTrabajoActivity.this);
        alertBuilder.setMessage("¿Está seguro de salir?")
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        OrdenTrabajoActivity.this.finish();
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


    private class TransaccionCallback implements retrofit2.Callback<ArrayList<TipoTransaccion>> {
        @Override
        public void onResponse(Call<ArrayList<TipoTransaccion>> call, Response<ArrayList<TipoTransaccion>> response) {
            if (response.isSuccessful())
            {
                ArrayList<TipoTransaccion> tipo = response.body();
                txtOrden.setText( "(" + tipo.get(0).getId().toUpperCase() + " - " + String.valueOf(tipo.get(0).getSecuencia()) + ")");
            }
        }

        @Override
        public void onFailure(Call<ArrayList<TipoTransaccion>> call, Throwable t) {
            Toast.makeText(getApplicationContext(), "Error de respuesta", Toast.LENGTH_SHORT).show();
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