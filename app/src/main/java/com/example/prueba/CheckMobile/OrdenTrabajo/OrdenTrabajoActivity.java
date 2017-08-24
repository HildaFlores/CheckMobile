package com.example.prueba.CheckMobile.OrdenTrabajo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.Cliente.ClienteActivity;
import com.example.prueba.CheckMobile.CondicionPago.AdapterCondicion;
import com.example.prueba.CheckMobile.CondicionPago.CondicionPago;
import com.example.prueba.CheckMobile.CondicionPago.CondicionResponse;
import com.example.prueba.CheckMobile.Inspeccion.AdapterInspeccion;
import com.example.prueba.CheckMobile.Inspeccion.myDialogProgress;
import com.example.prueba.CheckMobile.MainActivity;
import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.RepresentanteVenta.AdapterRepresentante;
import com.example.prueba.CheckMobile.RepresentanteVenta.RepresentanteVenta;
import com.example.prueba.CheckMobile.RepresentanteVenta.RepresentanteVentaResponse;
import com.example.prueba.CheckMobile.TipoTransaccion.AdapterTipoTransaccion;
import com.example.prueba.CheckMobile.TipoTransaccion.TipoTransaccion;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.prueba.CheckMobile.Utils.Constantes.GENERADOS;
import static com.example.prueba.CheckMobile.Utils.Constantes.ID_SUPERVISOR;
import static com.example.prueba.CheckMobile.Utils.Constantes.JSON_KEY_PEDIDO_ID;
import static com.example.prueba.CheckMobile.Utils.Constantes.JSON_KEY_TIPO_TRANS;
import static com.example.prueba.CheckMobile.Utils.Constantes.KEY_TIPO_TRANS_INSPECCION;
import static com.example.prueba.CheckMobile.Utils.Constantes.KEY_TIPO_TRANS_ORDEN;
import static com.example.prueba.CheckMobile.Utils.Constantes.NOMBRE_CARPETA_APP;
import static com.example.prueba.CheckMobile.Utils.Constantes.NOMBRE_USUARIO;
import static com.example.prueba.CheckMobile.Utils.Constantes.RESPONSE_CODE_OK;
import static com.example.prueba.CheckMobile.Utils.Constantes.SUPERVISOR;

public class OrdenTrabajoActivity extends AppCompatActivity {

    //mis Views
    ImageButton buscarServicio;
    ListView listaServicios;
    ArrayAdapter<String> arrayAdapter;
    TextView txtOrden;
    TextView txtNombreCliente;
    TextView txtNombreVehiculo;
    TextView txtSupervisor;
    EditText editTextObservaciones;
    EditText txtfechaOrden;
    Spinner spinnerMecanico;
    Spinner spinnerCondicion;
    Switch swReemplazo;
    ProgressDialog dialog;

    //mis variables
    int requestCode = 1;
    private String idProducto;
    private String descProducto;
    private String idInspeccion;
    private String nombreCliente;
    private String nombreVehiculo;
    private String idCondicion;
    private String idCliente;
    private String idMecanico;
    private String nombreSupervisor;
    String idOrden, permiteReemplazo;
    List<String> listaIdProductos = new ArrayList<>();
    List<String> listaDescProductos = new ArrayList<>();
    private boolean permitePiezasReemplazo = false;
    private boolean actualizar = false;
    private String fechaOrden;
    private String observaciones;
    private String descripcionCondicion;
    private String nombreMecanico;
    ImageButton ibFecha;


    private DatePickerDialog.OnDateSetListener mDateSetListenerFecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orden_trabajo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarOrden);
        setSupportActionBar(toolbar);

        inicializacionVistas();
        ObtenerIdMecanico();
        ObtenerDatosMecanicos("1");
        ObtenerDatosCondicion();


        //obtentiendo datos de intent
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        if (extra != null) {
            idInspeccion = extra.getString("IDINSPECCION");
            nombreCliente = extra.getString("CLIENTE");
            nombreVehiculo = extra.getString("VEHICULO");
            idCondicion = extra.getString("IDCONDICION");
            idCliente = extra.getString("IDCLIENTE");
            actualizar = extra.getBoolean("ACTUALIZAR");
            if (actualizar) {
                idOrden = extra.getString("ORDEN");
                nombreCliente = extra.getString("CLIENTE");
                idCliente = extra.getString("IDCLIENTE");
                idCondicion = extra.getString("IDCONDICION");
                fechaOrden = extra.getString("FECHA");
                observaciones = extra.getString("OBSERVACION");
                permitePiezasReemplazo = extra.getBoolean("PIEZAS");
                nombreMecanico = extra.getString("MECANICO");
                idMecanico = extra.getString("IDMECANICO");
                descripcionCondicion = extra.getString("CONDICION");
                nombreSupervisor = extra.getString("SUPERVISOR");
                llenarOrdenEnc();
                obtenerOrdenDetalle(idOrden);
//                spinnerMecanico.setSelection(getIndex(spinnerMecanico, nombreMecanico));
//                spinnerCondicion.setSelection(getIndex(spinnerCondicion, descripcionCondicion));

                if (permitePiezasReemplazo) {
                    swReemplazo.setChecked(true);
                    permiteReemplazo = "1";
                } else {
                    permiteReemplazo = "0";
                    swReemplazo.setChecked(false);
                }

                txtfechaOrden.setEnabled(false);
            } else {
                ObtenerSecuencia(KEY_TIPO_TRANS_ORDEN);
            }

        }

        //Evento click buscar
        buscarServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrdenTrabajoActivity.this, FiltroProductoActivity.class);
                startActivityForResult(intent, requestCode);
            }
        });

        //Llenando datos de encabezado
        if (actualizar) {
            txtNombreVehiculo.setVisibility(View.GONE);
            ibFecha.setEnabled(false);
        } else {
            txtNombreVehiculo.setText("VEHICULO >> " + nombreVehiculo);
            txtNombreCliente.setText("CLIENTE >> " + nombreCliente);
            txtSupervisor.setText(SUPERVISOR);
        }

        obtenerResultadoPieza();
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


        ibFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        OrdenTrabajoActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListenerFecha,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


                mDateSetListenerFecha = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        txtfechaOrden.setText(date);
                        try {
                            Calendar cal = Calendar.getInstance();
                            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                            Date myDate = null;
                            try {
                                myDate = df.parse(txtfechaOrden.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            cal.setTime(myDate);

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error en formato de fecha!", Toast.LENGTH_SHORT).show();

                        }
                    }
                };

            }
        });


    }

    private void ObtenerIdMecanico() {
        Call<String> callMecanico = AdapterRepresentante.getIdService().getIdMecanico();
        callMecanico.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                {
                    if(response.body().toString() != null)
                    {
                        idMecanico = response.body().toString();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Error en respuesta de mecánico", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Mecanico", "Error ==> " +  t.getMessage());
            }
        });

    }

    private void inicializacionVistas() {
        //Inicializacion vistas
        buscarServicio = (ImageButton) findViewById(R.id.ibBuscarServicio);
        listaServicios = (ListView) findViewById(R.id.listaAddServicios);
        txtOrden = (TextView) findViewById(R.id.textViewOrden);
        txtNombreCliente = (TextView) findViewById(R.id.textViewDatosCliente);
        txtNombreVehiculo = (TextView) findViewById(R.id.textViewDatosVehiculo);
        editTextObservaciones = (EditText) findViewById(R.id.etxtObervaciones);
        spinnerMecanico = (Spinner) findViewById(R.id.spinnerMec);
        spinnerCondicion = (Spinner) findViewById(R.id.spCondicion2);
        txtfechaOrden = (EditText) findViewById(R.id.etxtFechaOrden);
        swReemplazo = (Switch) findViewById(R.id.switchPiezas);
        txtSupervisor = (TextView) findViewById(R.id.textViewSupervisor);
        ibFecha = (ImageButton) findViewById(R.id.ibFechaOrden);
    }

    private void obtenerOrdenDetalle(String valor) {


        Call<OrdenTrabajoDet> callOrden = AdapterOrdenTrabajo.getOrdenDetalle(JSON_KEY_TIPO_TRANS, KEY_TIPO_TRANS_ORDEN, JSON_KEY_PEDIDO_ID, valor).getPedidoDetalle();
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
            listaDescProductos.add(var.getDescripcion_producto());
            listaIdProductos.add(var.getId_producto());
        }
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_style, listaDescProductos);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_style);
        listaServicios.setAdapter(arrayAdapter);
    }

    private void llenarOrdenEnc() {

        txtOrden.setText(KEY_TIPO_TRANS_ORDEN + "-" + idOrden);
        txtfechaOrden.setText(fechaOrden);
        txtNombreCliente.setText("Cliente >> " + nombreCliente.toUpperCase());
        txtSupervisor.setText(nombreSupervisor.toUpperCase());
        editTextObservaciones.setText(observaciones);
        if (!permitePiezasReemplazo) {
            swReemplazo.setChecked(true);
        }

    }

    private void obtenerResultadoPieza() {

        swReemplazo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    permiteReemplazo = "1";
                    permitePiezasReemplazo = true;
                } else {
                    permiteReemplazo = "0";
                    permitePiezasReemplazo = false;
                }
            }
        });

    }

    private void ObtenerSecuencia(String valor) {

        Call<ArrayList<TipoTransaccion>> callTransaccion = AdapterTipoTransaccion.getTransaccion(JSON_KEY_TIPO_TRANS, valor).getTipoTransaccion();
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
                    if (response.body().getResponseCode().equals(RESPONSE_CODE_OK)) {
                        RepresentanteVentaResponse representante = response.body();
                        poblarSpinnerMecanico(representante.getRepresentanteVentas());
                    } else {
                        Toast.makeText(getApplicationContext(), "Error en datos de mecánicos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error en respuesta de mecánicos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RepresentanteVenta> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error de respuesta de mecánicos", Toast.LENGTH_SHORT).show();
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


        for (int j = 0; j < representanteVentas.size(); j++) {
            if (representanteVentas.get(j).getId().equals(idMecanico)) {
                spinnerMecanico.setSelection(j);
                break;

            }
        }

        spinnerMecanico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String mecanico = adapterMecanico.getItem(i);
                nombreMecanico = mecanico;
                for (int j = 0; j < representanteVentas.size(); j++) {
                    if ((representanteVentas.get(j).getNombres().toUpperCase() + " " + representanteVentas.get(j).getApellidos().toUpperCase()).equals(mecanico.toUpperCase())) {
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


            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (actualizar) {
            getMenuInflater().inflate(R.menu.menu_main_actualizar, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_main_guardar, menu);
        }
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
        } else if (id == R.id.action_update) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(OrdenTrabajoActivity.this);
            alertBuilder.setMessage("¿Está seguro de actualizar los datos?")
                    .setCancelable(false)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActualizarOrdenTrabajo();
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

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void ActualizarOrdenTrabajo() {

        if (nombreCliente != null && idCondicion != null && idMecanico != null && idInspeccion != null &&
                !listaIdProductos.isEmpty()) {
            ArrayList<OrdenTrabajoEnc> ordenes = new ArrayList<OrdenTrabajoEnc>();
            ordenes.add(new OrdenTrabajoEnc(
                    idOrden,
                    editTextObservaciones.getText().toString().toUpperCase(),
                    idCondicion,
                    idMecanico,
                    txtfechaOrden.getText().toString(),
                    permiteReemplazo
            ));

            Call<String> callUpdate = AdapterOrdenTrabajo.updateOrdenTrabajo().setupdateOrden(ordenes);
            dialog = new ProgressDialog(this);
            dialog.setTitle(null);
            dialog.setMax(100);
            dialog.setMessage("Actualizando Orden...");
            // show it
            dialog.show();
            callUpdate.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        if (response.body().toString().equals(RESPONSE_CODE_OK)) {
                            guardarOrdenDetalle();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Error al guardar los datos", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error de respuesta al guardar", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error " + " " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("ORDEN==>", " " + t.getMessage());
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "Faltan datos por llenar!", Toast.LENGTH_SHORT).show();
        }
    }

    private void GuardarOrdenTrabajo() {
        if (idCliente != null && nombreCliente != null && idCondicion != null && idMecanico != null
                && idInspeccion != null && txtfechaOrden.getText().toString() != null && !listaIdProductos.isEmpty()) {
            final ArrayList<OrdenTrabajoEnc> ordenes = new ArrayList<OrdenTrabajoEnc>();
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
                    txtfechaOrden.getText().toString(),
                    ID_SUPERVISOR,
                    idInspeccion,
                    permiteReemplazo
            ));

            //Cambiar estado de inspeccion a Finalizada
            Call<String> callActualizar = AdapterInspeccion.updateInspeccion(String.valueOf(idInspeccion)).setConvert();
            dialog = new ProgressDialog(this);
            dialog.setTitle(null);
            dialog.setMax(100);
            dialog.setMessage("Guardando Orden...");
            // show it
            dialog.show();
            callActualizar.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        if (response.body().toString().equals(RESPONSE_CODE_OK)) {
                            guardarOrdenEnc(ordenes);
                        } else {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Error al generar orden", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.v("Inspeccion", t.getMessage());
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Faltan datos por llenar", Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarOrdenEnc(ArrayList<OrdenTrabajoEnc> ordenes) {

        Call<String> callOrden = AdapterOrdenTrabajo.setApiService().setOrdenTrabajo(ordenes);
        callOrden.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String[] p = response.body().split(",");
                    idOrden = p[1];
                    if (p[0].equals(RESPONSE_CODE_OK)) {
                        guardarOrdenDetalle();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error al guardar los datos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error de respuesta al guardar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void guardarOrdenDetalle() {
        ArrayList<OrdenTrabajoDet> ordenes = new ArrayList<OrdenTrabajoDet>();

        for (int i = 0; i < listaIdProductos.size(); i++) {
            ordenes.add(new OrdenTrabajoDet(
                    idOrden,
                    listaIdProductos.get(i),
                    "1",
                    "ITBIS",
                    listaDescProductos.get(i),
                    "1",
                    "0",
                    "1",
                    "0",
                    "1",
                    "0.5",
                    "1",
                    editTextObservaciones.getText().toString().toUpperCase(),
                    "1",
                    "1",
                    "1",
                    "1"
            ));
        }

        Call<String> callOrdenDet = AdapterOrdenTrabajo.insertOrdendDetalle().setPedidoDetalle(ordenes);
        callOrdenDet.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String[] p = response.body().split(",");
                    if (p[0].equals(RESPONSE_CODE_OK)) {
//                            myDialogProgress dialogProgress = new myDialogProgress();
//                            dialogProgress.show(getFragmentManager(), "Orden Trabajo");
                        dialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        generarPdfOnClick();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error al guardar los datos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error de respuesta al guardar", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ORDEN==>", " " + t.getMessage());
            }
        });

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
            if (response.isSuccessful()) {
                ArrayList<TipoTransaccion> tipo = response.body();
                txtOrden.setText("(" + tipo.get(0).getId().toUpperCase() + " - " + String.valueOf(tipo.get(0).getSecuencia()) + ")");
            }
        }

        @Override
        public void onFailure(Call<ArrayList<TipoTransaccion>> call, Throwable t) {
            Toast.makeText(getApplicationContext(), "Error de respuesta", Toast.LENGTH_SHORT).show();
        }

    }


    public void generarPdfOnClick() {
        String NOMBRE_ARCHIVO = "OrdenTrabajo.pdf";
        String tarjetaSD = Environment.getExternalStorageDirectory().toString();
        Document document = new Document(PageSize.LETTER);

        String valorPiezaReemplazo;
        String condicion;
        if (permitePiezasReemplazo) {
            valorPiezaReemplazo = "Sí";
        } else {
            valorPiezaReemplazo = "No";
        }

        if (idCondicion.equals("0")) {
            condicion = "ORDEN AL CONTADO";
        } else {
            condicion = "ORDEN A CRÉDITO";
        }


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
            document.addSubject("Orden Trabajo");
            document.addCreationDate();
            document.addTitle("Orden Trabajo");
            String add = null;
            String nota;
            XMLWorkerHelper workerHelper = XMLWorkerHelper.getInstance();
            String htmToPDF = "<html><head></head><body>" +
                    "<table style=\"width: 100%;\" border=\"0\" cellpadding=\"0\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td style=\"width: 50%;\">Empresa X</td>\n" +
                    "<td style=\"text-align: left;\"><strong>Fecha:</strong>" + txtfechaOrden.getText().toString() + "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td style=\"width: 50%;\">Av. Pedro A. Rivera Esq. Balilo G&oacute;mez</td>\n" +
                    "<td style=\"text-align: left;\"><strong>Orden de Trabajo: </strong>" + KEY_TIPO_TRANS_ORDEN + "-" + idOrden + "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td style=\"width: 50%;\">La Vega, Rep&uacute;blica Dominicana</td>\n" +
                    "<td style=\"text-align: left;\"><strong>Inspecci&oacute;n</strong>: " + KEY_TIPO_TRANS_INSPECCION + "-" + idInspeccion + "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td style=\"width: 50%;\">&nbsp;</td>\n" +
                    "<td style=\"text-align: left;\"><strong>Supervisor</strong>:" + txtSupervisor.getText() + "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td style=\"width: 65%;\">&nbsp;</td>\n" +
                    "<td style=\"text-align: left;\"><strong>Permite pieza reemplazo:&nbsp;</strong>" + valorPiezaReemplazo + "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td style=\"width: 65%;\">&nbsp;</td>\n" +
                    "<td style=\"text-align: left;\"><strong>Mec&aacute;nico: </strong>" + nombreMecanico + "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>" +
                    "<p>&nbsp;</p>\n" +
                    "<p style=\"text-align: center;\">" + condicion + "</p>\n" +

                    "<table style=\"width: 100%;\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" align=\"\\left\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td style=\"text-align: left;\" colspan=\"1\" rowspan=\"1\"><strong>Cliente: </strong>" + nombreCliente + "</td>\n" +
                    "</tr>\n";

            if (nombreVehiculo != null) {
                htmToPDF = htmToPDF + "<tr>\n" +
                        "<td style=\"text-align: left;\"><strong>Vehiculo:</strong>" + nombreVehiculo + "</td>\n" +
                        "</tr>\n" +
                        "</tbody> \n" +
                        "</table>\n" +
                       "<p>&nbsp;</p>\n" +
                        "<table style=\"width: 75%;\" cellspacing=\"2\" cellpadding=\"1\">\n" +
                        "<tbody>\\n\" +\n" +
                        "<tr>\\n\" +\n" +
                        "<td style=\"background-color: #cccccc; text-align: center;\" colspan=\"4\" rowspan=\"1\"><strong>Servicios solicitados</strong></td>\n" +
                        "</tr>\n" +
                        "<tr>\n" +
                        "<td style=\"width: 10%;\"><strong>Sec.</strong></td>\n" +
                        "<td style=\"width: 15%;\"><strong>C&oacute;digo</strong></td>\n" +
                        "<td style=\"width: 85%;\"><strong>Descripci&oacute;n</strong></td>\n" +
                         "</tr>\n";
            } else {

                htmToPDF = htmToPDF +
                        "</tbody>\n" +
                        "</table>\n" +
                        "<p>&nbsp;</p>\n" +
                        "<table style=\"width: 75%;\" cellspacing=\"2\" cellpadding=\"1\">\n" +
                        "<tbody>\n" +
                        "<tr>\n" +
                        "<td style=\"background-color: #cccccc; text-align: center;\" colspan=\"4\" rowspan=\"1\"><strong>Servicios solicitados</strong></td>\n" +
                        "</tr>\n" +
                        "<tr>\n" +
                        "<td style=\"width: 10%;\"><strong>Sec.</strong></td>\n" +
                        "<td style=\"width: 15%;\"><strong>C&oacute;digo</strong></td>\n" +
                        "<td style=\"width: 85%;\"><strong>Descripci&oacute;n</strong></td>\n" +
                        "</tr>\n";
            }
            for (int i = 1; i <= listaIdProductos.size(); i++) {
                add = add + "<tr>\n" +
                        "<td style=\"width: 10%;\">" + i + "</td>\n" +
                        "<td style=\"width: 10%;\">" + listaIdProductos.get(i - 1) + "</td>\n" +
                        "<td style=\"width: 90%;\">" + listaDescProductos.get(i - 1) + "</td>\n" +
                        "</tr>\n";
            }
            htmToPDF = htmToPDF + add + "</tbody>\n" +
                    "</table>";


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
                        "<td style=\"width: 50%; text-align: center;\">"+NOMBRE_USUARIO+"</td>\n" +
                        "<td style=\"width: 50%; text-align: center;\">"+nombreCliente+"</td>\n" +
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
                        "<td style=\"width: 50%; text-align: center;\">"+NOMBRE_USUARIO+"</td>\n" +
                        "<td style=\"width: 50%; text-align: center;\">"+nombreCliente+"</td>\n" +
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
            Phrase header = new Phrase("Orden de Trabajo", ffont);
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
