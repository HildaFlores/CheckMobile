package com.example.prueba.CheckMobile.Inspeccion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.prueba.CheckMobile.Cliente.ClienteActivity;
import com.example.prueba.CheckMobile.MainActivity;
import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.Vehiculo.AdapterVehiculo;
import com.example.prueba.CheckMobile.VehiculoDocumento.AdapterVehiculoDocumento;
import com.example.prueba.CheckMobile.VehiculoDocumento.VehiculoDocumento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static android.R.attr.data;
import static android.R.attr.id;

public class MainInspeccionActivity extends AppCompatActivity implements InspeccionGeneralActivity.passingData, InspeccionLucesActivity.sendData, InspeccionAccesoriosActivity.sendAccesorios, OtrosActivity.sendOtros {

    private mSectionsPagerAdapterInspeccion mSectionsPagerAdapterInspeccion;
    private ViewPager mViewPager;
    String idVehiculo, chasis, referencia, idCliente, idInspeccion;
    String tipoLlave, descLlave, nivelCombustible, nivelAceite;
    private String motor;
    private String kilometraje;
    private String fecha;
    private String observaciones;
    private String serieGomas;
    String cantAlfombra, cantLlaves, cantGato, cantAlicate, cantLlaveRueda, noBateria;
    private List<String> descripcionLuces = new ArrayList<>();
    private List<Integer> idLuces = new ArrayList<>();
    private List<String> descAccesorios = new ArrayList<>();
    private List<Integer> idAccesorios = new ArrayList<>();
    private List<String> idRespuesta = new ArrayList<>();
    private List<String> descAlfrombra = new ArrayList<>();
    private List<String> imageRuta = new ArrayList<>();
    private List<Integer> ladoVehiculo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inspeccion);

        mSectionsPagerAdapterInspeccion = new mSectionsPagerAdapterInspeccion(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container2);
        mViewPager.setAdapter(mSectionsPagerAdapterInspeccion);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs2);
        tabLayout.setupWithViewPager(mViewPager);

        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();
        if (extra != null) {
            idVehiculo = extra.getString("IDVEHICULO");
            referencia = extra.getString("REFERENCIA");
            chasis = extra.getString("CHASIS");
            idCliente = extra.getString("IDCLIENTE");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main_inspeccion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainInspeccionActivity.this);
            alertBuilder.setMessage("¿Está seguro de guardar los datos?")
                    .setCancelable(false)
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            guardarInspeccionVehiculo();

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

    private void guardarInspeccionVehiculo() {

        ArrayList<InspeccionVehiculo> inspeccionEnc = new ArrayList<InspeccionVehiculo>();
        inspeccionEnc.add(new InspeccionVehiculo(
                idVehiculo,
                chasis,
                referencia,
                fecha,
                serieGomas,
                null,
                null,
                observaciones,
                null,
                idCliente,
                kilometraje,
                motor
        ));

        if (idVehiculo == null || chasis == null || referencia == null || idCliente == null || kilometraje == null
                || motor == null || serieGomas == null || fecha == null || (descripcionLuces.isEmpty() && idLuces.isEmpty())
                || (descAccesorios.isEmpty() && idAccesorios.isEmpty()) || tipoLlave == null || nivelAceite == null
                || nivelCombustible == null || cantAlfombra == null || cantAlicate == null || cantLlaves == null
                || cantLlaveRueda == null || cantGato == null || noBateria == null) {
            Toast.makeText(getApplicationContext(), "Faltan datos por llenar!!", Toast.LENGTH_SHORT).show();
        } else {
            Call<String> callInspeccion = AdapterInspeccion.setInspeccionVehiculo().insertInspeccion(inspeccionEnc);
            callInspeccion.enqueue(new InspeccionCallback());
        }
    }


    @Override
    public void OnPassingFecha(String fecha) {
        //  Log.d("TAG", "Activity ==> " + fecha);
        this.fecha = fecha;

    }

    @Override
    public void OnPassinKilo(String kilometraje) {
        //  Log.d("TAG", "Activity ==> " + kilometraje);
        this.kilometraje = kilometraje;
    }

    @Override
    public void OnPassinMotor(String motor) {
        //  Log.d("TAG", "Activity ==> " + motor);
        this.motor = motor;
    }

    @Override
    public void OnPassinSerieGomas(String SerieGoma) {
        //   Log.d("TAG", "Activity ==> " + SerieGoma);
        this.serieGomas = SerieGoma;
    }

    @Override
    public void OnpassingObservaciones(String observaciones) {

        //  Log.d("TAG", "Activity ==> " + observaciones);
        this.observaciones = observaciones;
    }

    @Override
    public void OnpassingIdRespuesta(List<String> idRespuesta) {
        this.idRespuesta = idRespuesta;
        // Log.d("TAG", "Activity ==> " + idRespuesta);

    }

    @Override
    public void OnpassingDescAlfrombra(List<String> descAlfrombra) {
        this.descAlfrombra = descAlfrombra;
//        Log.d("TAG", "Activity ==> " + descAlfrombra);

    }

    @Override
    public void sendIdLuces(List<Integer> id) {
        this.idLuces = id;
        //  Log.d("TAG", "Activity ==> " + id);
    }

    @Override
    public void sendDescLuces(List<String> DescLuces) {
        this.descripcionLuces = DescLuces;
        //   Log.d("TAG", "Activity ==> " + DescLuces);

    }

    @Override
    public void sendIdAccesorio(List<Integer> idAccesorio) {
        this.idAccesorios = idAccesorio;
        //  Log.d("TAG", "Activity ==> " + idAccesorio);
    }

    @Override
    public void sendDescAccesorio(List<String> descAccesorio) {
        this.descAccesorios = descAccesorio;
        //   Log.d("TAG", "Activity ==> " + descAccesorio);

    }

    @Override
    public void sendTipoLlave(String llave) {
        this.tipoLlave = llave;
    }

    @Override
    public void sendDescLlave(String descLlave) {
        this.descLlave = descLlave;

    }

    @Override
    public void sendNivelCombustible(String nivelComb) {
        this.nivelCombustible = nivelComb;

    }

    @Override
    public void sendNivelAceite(String nivelAceite) {
        this.nivelAceite = nivelAceite;

    }

    @Override
    public void sendCantAlfombra(String cantAlfombra) {
        this.cantAlfombra = cantAlfombra;
    }

    @Override
    public void sendCantLlave(String cantLlave) {
        this.cantLlaves = cantLlave;
    }

    @Override
    public void sendCantGato(String cantGato) {
        this.cantGato = cantGato;
    }

    @Override
    public void sendCantAlicate(String cantAlicate) {
        this.cantAlicate = cantAlicate;
    }

    @Override
    public void sendLlaveRueda(String cantLlaveRueda) {
        this.cantLlaveRueda = cantLlaveRueda;
    }

    @Override
    public void sendNoBateria(String noBateria) {
        this.noBateria = noBateria;
    }

    @Override
    public void sendImageRuta(List<String> imageRuta) {
        this.imageRuta = imageRuta;
    }

    @Override
    public void sendLadoId(List<Integer> lado) {
        this.ladoVehiculo = lado;

    }

    public class mSectionsPagerAdapterInspeccion extends FragmentPagerAdapter {

        public mSectionsPagerAdapterInspeccion(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    InspeccionGeneralActivity servicio = new InspeccionGeneralActivity();
                    return servicio;

                case 1:
                    InspeccionLucesActivity Luces = new InspeccionLucesActivity();
                    return Luces;

                case 2:

                    InspeccionAccesoriosActivity accesorios = new InspeccionAccesoriosActivity();
                    return accesorios;
                case 3:
                    OtrosActivity otros = new OtrosActivity();
                    return otros;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String titulo1 = getString(R.string.section1Inspeccion);
            String titulo2 = getString(R.string.section2Inspeccion);
            String titulo3 = getString(R.string.section3Inspeccion);
            String titulo4 = getString(R.string.section4Inspeccion);

            switch (position) {
                case 0:
                    return titulo1;
                case 1:
                    return titulo2;
                case 2:
                    return titulo3;
                case 3:
                    return titulo4;
            }
            return null;
        }
    }

    private class InspeccionCallback implements retrofit2.Callback<String> {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            if (response.isSuccessful()) {
                String[] p = response.body().toString().split(",");
                if (p[0].equals("200")) {
                    idInspeccion = p[1];
                    ArrayList<InspeccionVehiculoDetalle> inspeccionDetalle = new ArrayList<InspeccionVehiculoDetalle>();
                    for (int i = 0; i < idLuces.size(); i++) {
                        inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                                idInspeccion,
                                idVehiculo,
                                String.valueOf(idLuces.get(i)),
                                descripcionLuces.get(i),
                                "1",
                                "58"
                        ));
                    }


                    for (int j = 0; j < idAccesorios.size(); j++) {
                        inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                                idInspeccion,
                                idVehiculo,
                                String.valueOf(idAccesorios.get(j)),
                                descAccesorios.get(j),
                                "1",
                                "60"
                        ));
                    }


                    for (int j = 0; j < idRespuesta.size(); j++) {
                        inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                                idInspeccion,
                                idVehiculo,
                                String.valueOf(j),
                                descAlfrombra.get(j),
                                idRespuesta.get(j),
                                "63"
                        ));
                    }

                    switch (tipoLlave) {

                        case "1": {
                            inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                                    idInspeccion,
                                    idVehiculo,
                                    tipoLlave,
                                    descLlave,
                                    "15",
                                    "59"
                            ));
                            break;
                        }
                        case "2": {
                            inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                                    idInspeccion,
                                    idVehiculo,
                                    tipoLlave,
                                    descLlave,
                                    "16",
                                    "59"
                            ));
                        }
                    }

                    switch (nivelCombustible) {

                        case "0": {
                            inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                                    idInspeccion,
                                    idVehiculo,
                                    "1",
                                    "Nivel Combustible",
                                    "6",
                                    "64"
                            ));
                            break;
                        }
                        case "1": {
                            inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                                    idInspeccion,
                                    idVehiculo,
                                    "1",
                                    "Nivel Combustible",
                                    "3",
                                    "64"
                            ));
                            break;
                        }
                        case "2": {
                            inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                                    idInspeccion,
                                    idVehiculo,
                                    "1",
                                    "Nivel Combustible",
                                    "4",
                                    "64"
                            ));
                            break;
                        }
                        case "3": {
                            inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                                    idInspeccion,
                                    idVehiculo,
                                    "1",
                                    "Nivel Combustible",
                                    "5",
                                    "64"
                            ));
                            break;
                        }
                        default: {

                            inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                                    idInspeccion,
                                    idVehiculo,
                                    "1",
                                    "Nivel Combustible",
                                    "8",
                                    "64"
                            ));
                            break;
                        }

                    }

                    inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                            idInspeccion,
                            idVehiculo,
                            "2",
                            "Nivel Aceite",
                            "22",
                            "64",
                            nivelAceite

                    ));
                    ///Cantidades

                    inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                            idInspeccion,
                            idVehiculo,
                            "1",
                            "Cantidad Alfombra",
                            "22",
                            "62",
                            cantAlfombra

                    ));

                    inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                            idInspeccion,
                            idVehiculo,
                            "2",
                            "Cantidad Llaves",
                            "22",
                            "62",
                            cantLlaves

                    ));

                    inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                            idInspeccion,
                            idVehiculo,
                            "3",
                            "Cantidad Gato",
                            "22",
                            "62",
                            cantGato

                    ));

                    inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                            idInspeccion,
                            idVehiculo,
                            "4",
                            "Cantidad Alicate",
                            "22",
                            "62",
                            cantAlicate

                    ));

                    inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                            idInspeccion,
                            idVehiculo,
                            "5",
                            "Cantidad Llave rueda",
                            "22",
                            "62",
                            cantLlaveRueda

                    ));

                    inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                            idInspeccion,
                            idVehiculo,
                            "6",
                            "Numero de Bateria",
                            "22",
                            "62",
                            noBateria

                    ));


                    insertarInspeccionDetalle(inspeccionDetalle);
                } else {
                    Toast.makeText(getApplicationContext(), "Error al guardar datos", Toast.LENGTH_SHORT).show();

                }
            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            Log.v("Error insercion ** ", t.getMessage());
            Toast.makeText(getApplicationContext(), t.getMessage() + " Error al insertar inspeccion", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertarInspeccionDetalle(ArrayList<InspeccionVehiculoDetalle> inspeccionDetalle) {
        Call<String> callInspeccionDet = AdapterInspeccion.setInspeccionVehiculoDet().insertInspeccionDetalle(inspeccionDetalle);
        callInspeccionDet.enqueue(new InspeccionDetalleCallback());

    }

    private class InspeccionDetalleCallback implements retrofit2.Callback<String> {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            if (response.isSuccessful()) {
                Log.v("TAG ==> ", response.body());
                if (response.body().toString().equals("200")) {
                    guardarVehiculoDocumento();
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
    }

    private void guardarVehiculoDocumento() {

        ArrayList<VehiculoDocumento> documento = new ArrayList<VehiculoDocumento>();
        for (int i = 0; i < imageRuta.size(); i++) {
            switch (ladoVehiculo.get(i)) {
                case 1: {
                    documento.add(new VehiculoDocumento(
                            idVehiculo,
                            imageRuta.get(i),
                            idInspeccion,
                            "Izquierda",
                            String.valueOf(ladoVehiculo.get(i))
                    ));
                    break;
                }
                case 2:
                {
                    documento.add(new VehiculoDocumento(
                            idVehiculo,
                            imageRuta.get(i),
                            idInspeccion,
                            "Derecha",
                            String.valueOf(ladoVehiculo.get(i))
                    ));
                    break;
                }
                case 3:
                {
                    documento.add(new VehiculoDocumento(
                            idVehiculo,
                            imageRuta.get(i),
                            idInspeccion,
                            "Delantero",
                            String.valueOf(ladoVehiculo.get(i))
                    ));
                    break;
                }
                case 4:
                {
                    documento.add(new VehiculoDocumento(
                            idVehiculo,
                            imageRuta.get(i),
                            idInspeccion,
                            "Detrás",
                            String.valueOf(ladoVehiculo.get(i))
                    ));
                    break;
                }
                case 5:
                {
                    documento.add(new VehiculoDocumento(
                            idVehiculo,
                            imageRuta.get(i),
                            idInspeccion,
                            "Encima",
                            String.valueOf(ladoVehiculo.get(i))
                    ));
                    break;
                }
            }

        }

        System.out.print("==>" + documento);
        Call<String> callVehiculoDocumento = AdapterVehiculoDocumento.getService().setVehiculoDocumentos(documento);
        callVehiculoDocumento.enqueue(new VehDocumentoCallback());
    }

    private class VehDocumentoCallback implements retrofit2.Callback<String> {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            if (response.isSuccessful()) {
                if (response.body().toString().equals("200")) {
                    Toast.makeText(getApplicationContext(), "Registros guardados con éxito >> IV-" + idInspeccion, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainInspeccionActivity.this.getApplicationContext(), MainActivity.class);
                    startActivity(intent);
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
    }
}
