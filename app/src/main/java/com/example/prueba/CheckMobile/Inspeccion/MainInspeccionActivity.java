package com.example.prueba.CheckMobile.Inspeccion;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.Vehiculo.*;
import com.example.prueba.CheckMobile.VehiculoDocumento.AdapterVehiculoDocumento;
import com.example.prueba.CheckMobile.VehiculoDocumento.VehiculoDocumento;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
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
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import static com.example.prueba.CheckMobile.Utils.Constantes.*;

public class MainInspeccionActivity extends AppCompatActivity implements InspeccionGeneralActivity.passingData, InspeccionLucesActivity.sendData, InspeccionAccesoriosActivity.sendAccesorios, OtrosActivity.sendOtros {

    private mSectionsPagerAdapterInspeccion mSectionsPagerAdapterInspeccion;
    private ViewPager mViewPager;
    String idVehiculo, chasis, referencia, idCliente, idInspeccion, nombreCliente, nombreVehiculo;
    String tipoLlave, descLlave, nivelCombustible, nivelAceite;
    private String motor;
    private String kilometraje;
    private String fecha;
    private String observaciones;
    private String serieGomas;
    private String placa;
    private String color;
    private String documentoIdentidad;
    private String celular;
    private String telefono;
    private String condicionAlfombra1;
    private String condicionAlfombra2;
    private String condicionAlfombra3;
    String cantAlfombra, cantLlaves, cantGato, cantAlicate, cantLlaveRueda, noBateria;
    boolean actualizar = false;

    //Views
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main_inspeccion);
        setSupportActionBar(toolbar);
        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();
        if (extra != null) {
            idVehiculo = extra.getString("IDVEHICULO");
            referencia = extra.getString("REFERENCIA");
            chasis = extra.getString("CHASIS");
            idCliente = extra.getString("IDCLIENTE");
            nombreCliente = extra.getString("NOMBRECLIENTE");
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainInspeccionActivity.this);
        alertBuilder.setMessage("¿Está seguro de salir?")
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        MainInspeccionActivity.this.finish();
                        Intent intent = new Intent(MainInspeccionActivity.this, VehiculoActivity.class);
                        startActivity(intent);

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
        } else if (id == R.id.action_ver) {
            generarPdfWithImage();
        }


        return super.onOptionsItemSelected(item);
    }

    private void generarPdfWithImage() {

        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream("ImageDemo.pdf"));
            doc.open();

            // Creating image by file name
            String filename = "other-sample/src/main/resources/java.gif";
            Image image = Image.getInstance(filename);
            doc.add(image);

            // The following line to prevent the "Server returned
            // HTTP response code: 403" error.
            System.setProperty("http.agent", "Chrome");

            // Creating image from a URL
            String url = "/storage/sdcard0/Pictures/JPEG_20170703_170334_778350350.jpg";
            image = Image.getInstance(url);
            doc.add(image);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            doc.close();
        }

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
    public void actualizar(boolean update) {
        this.actualizar = update;
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
    public void OnpassingNombreVeh(String nombre) {
        this.nombreVehiculo = nombre;

    }

    @Override
    public void OnpassingNomcreCte(String nombre) {
        this.nombreCliente = nombre;
    }

    @Override
    public void OnpassingPlaca(String placa) {
        this.placa = placa;
    }

    @Override
    public void OnpassingColor(String color) {
        this.color = color;
    }

    @Override
    public void OnpassingDocIdentidad(String docIdentidad) {
        this.documentoIdentidad = docIdentidad;
    }

    @Override
    public void OnpassingCelular(String celular) {
        this.celular = celular;
    }

    @Override
    public void OnpassingTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public void OnpassingCondicion1(String alfombra1) {
        this.condicionAlfombra1 = alfombra1;
    }

    @Override
    public void OnpassingCondicion2(String alfombra2) {
        this.condicionAlfombra2 = alfombra2;
    }

    @Override
    public void OnpassingCondicion3(String alfombra3) {
        this.condicionAlfombra3 = alfombra3;
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
                if (p[0].equals(RESPONSE_CODE_OK)) {
                    idInspeccion = p[1];
                    ArrayList<InspeccionVehiculoDetalle> inspeccionDetalle = new ArrayList<InspeccionVehiculoDetalle>();
                    for (int i = 0; i < idLuces.size(); i++) {
                        inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                                idInspeccion,
                                idVehiculo,
                                String.valueOf(idLuces.get(i)),
                                descripcionLuces.get(i),
                                "1",
                                KEY_LISTA_PARAM_LUZ
                        ));
                    }


                    for (int j = 0; j < idAccesorios.size(); j++) {
                        inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                                idInspeccion,
                                idVehiculo,
                                String.valueOf(idAccesorios.get(j)),
                                descAccesorios.get(j),
                                "1",
                                KEY_LISTA_PARAM_GENERALES
                        ));
                    }


                    for (int j = 0; j < idRespuesta.size(); j++) {
                        inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                                idInspeccion,
                                idVehiculo,
                                String.valueOf(j),
                                descAlfrombra.get(j),
                                idRespuesta.get(j),
                                KEY_LISTA_PARAM_CONDICION_ALFOMBRA
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
                                    KEY_LISTA_PARAM_LLAVE
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
                                    KEY_LISTA_PARAM_LLAVE
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
                                    KEY_LISTA_PARAM_CONDICION_ENTRADA
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
                                    KEY_LISTA_PARAM_CONDICION_ENTRADA
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
                                    KEY_LISTA_PARAM_CONDICION_ENTRADA
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
                                    KEY_LISTA_PARAM_CONDICION_ENTRADA
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
                                    KEY_LISTA_PARAM_CONDICION_ENTRADA
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
                            KEY_LISTA_PARAM_CONDICION_ENTRADA,
                            nivelAceite

                    ));
                    ///Cantidades

                    inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                            idInspeccion,
                            idVehiculo,
                            "1",
                            "Cantidad Alfombra",
                            "22",
                            KEY_LISTA_PARAM_CANTIDAD,
                            cantAlfombra

                    ));

                    inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                            idInspeccion,
                            idVehiculo,
                            "2",
                            "Cantidad Llaves",
                            "22",
                            KEY_LISTA_PARAM_CANTIDAD,
                            cantLlaves

                    ));

                    inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                            idInspeccion,
                            idVehiculo,
                            "3",
                            "Cantidad Gato",
                            "22",
                            KEY_LISTA_PARAM_CANTIDAD,
                            cantGato

                    ));

                    inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                            idInspeccion,
                            idVehiculo,
                            "4",
                            "Cantidad Alicate",
                            "22",
                            KEY_LISTA_PARAM_CANTIDAD,
                            cantAlicate

                    ));

                    inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                            idInspeccion,
                            idVehiculo,
                            "5",
                            "Cantidad Llave rueda",
                            "22",
                            KEY_LISTA_PARAM_CANTIDAD,
                            cantLlaveRueda

                    ));

                    inspeccionDetalle.add(new InspeccionVehiculoDetalle(
                            idInspeccion,
                            idVehiculo,
                            "6",
                            "Numero de Bateria",
                            "22",
                            KEY_LISTA_PARAM_CANTIDAD,
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

                if (response.body().toString().equals(RESPONSE_CODE_OK)) {
                    if (!imageRuta.isEmpty()) {
                        guardarVehiculoDocumento();
                    } else {
                        myDialogProgress dialogProgress = new myDialogProgress();
                        dialogProgress.show(getFragmentManager(), "Inspeccion");
                         generarPdfOnClick();
                    }
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
                case 2: {
                    documento.add(new VehiculoDocumento(
                            idVehiculo,
                            imageRuta.get(i),
                            idInspeccion,
                            "Derecha",
                            String.valueOf(ladoVehiculo.get(i))
                    ));
                    break;
                }
                case 3: {
                    documento.add(new VehiculoDocumento(
                            idVehiculo,
                            imageRuta.get(i),
                            idInspeccion,
                            "Delantero",
                            String.valueOf(ladoVehiculo.get(i))
                    ));
                    break;
                }
                case 4: {
                    documento.add(new VehiculoDocumento(
                            idVehiculo,
                            imageRuta.get(i),
                            idInspeccion,
                            "Detrás",
                            String.valueOf(ladoVehiculo.get(i))
                    ));
                    break;
                }
                case 5: {
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

        Call<String> callVehiculoDocumento = AdapterVehiculoDocumento.getService().setVehiculoDocumentos(documento);
        callVehiculoDocumento.enqueue(new VehDocumentoCallback());
    }

    private class VehDocumentoCallback implements retrofit2.Callback<String> {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            if (response.isSuccessful()) {
                if (response.body().toString().equals(RESPONSE_CODE_OK)) {
                    myDialogProgress dialogProgress = new myDialogProgress();
                    dialogProgress.show(getFragmentManager(), "Inspeccion");
                    generarPdfOnClick();
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

    public void generarPdfOnClick() {
        String NOMBRE_ARCHIVO = "MiArchivoPDF.pdf";
        String tarjetaSD = Environment.getExternalStorageDirectory().toString();
        Document document = new Document(PageSize.LETTER);
        int index = nombreVehiculo.indexOf("(");
        nombreVehiculo = nombreVehiculo.substring(0, index);

        if (!telefono.isEmpty()) {
            telefono = "(" + telefono.substring(0, 3) + ")" + telefono.substring(3);
        }
        if (!celular.isEmpty()) {
            celular = "(" + celular.substring(0, 3) + ")" + celular.substring(3);
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
            document.addSubject("Inspeccion");
            document.addCreationDate();
            document.addTitle("Inspeccion");

            String add = null;
            String nota;
            XMLWorkerHelper workerHelper = XMLWorkerHelper.getInstance();
            String htmToPDF = "<html><head></head><body>" +
                    "<table class=\"fixed\" style=\"width: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td style=\"text-align: left;\" colspan=\"2\" rowspan=\"1\">Av. Pedro A. Rivera Esq. Balilo G&oacute;mez</td>\n" +
                    "<td style=\"text-align: rigth;\" colspan=\"2\" rowspan=\"1\"><strong>Fecha:</strong>" + fecha + "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td style=\"text-align: left;\" colspan=\"2\" rowspan=\"1\">La Vega, Rep&uacute;blica Dominicana</td>\n" +
                    "<td style=\"text-align: rigth;\" colspan=\"2\" rowspan=\"1\"><strong>Inspecci&oacute;n:</strong> IV-" + idInspeccion + "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<p>&nbsp;</p>\n" +
                    "<p> <strong>Cliente:</strong>" + "(" + idCliente + ")" + nombreCliente +
                    "<br /><strong>Doc.Identidad:</strong> " + documentoIdentidad +
                    "<br /> <strong>Tel&eacute;fono/Celular:</strong> " + telefono + "/" + celular + "</p>\n" +
                    "<p>&nbsp;</p>\n" +
                    "<table class=\" fixed \" style=\"width: 60%;\" border=\"1\" cellspacing=\"2\" cellpadding=\"2\" align=\"left\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td style=\"background-color: #cccccc; text-align: center;\" colspan=\"1\" rowspan=\"1\"><strong>Vehiculo Inspeccionado</strong></td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<table class=\" fixed \" style=\"width: 75%;\" border=\"0\" cellspacing=\"1\" cellpadding=\"0\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td style=\"text-align: center;\" colspan=\"2\" rowspan=\"1\"><strong>" + nombreVehiculo + "</strong></td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td style=\"vertical-align: top;\"><strong>Chasis: </strong>" + chasis + "</td>\n" +
                    "<td style=\"vertical-align: top;\"><strong>Referencia: </strong>" + referencia + "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td style=\"vertical-align: top;\"><strong>Color: </strong>" + color + "</td>\n" +
                    "<td style=\"vertical-align: top;\"><strong>Placa: </strong>" + placa + "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td style=\"vertical-align: top;\"><strong>Motor: </strong>" + motor + "</td>\n" +
                    "<td style=\"vertical-align: top;\"><strong>Kilometraje: </strong>" + kilometraje + "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td style=\"vertical-align: top;\"><strong>Nivel combustible: </strong>" + nivelCombustible + "/4" + "</td>\n" +
                    "<td style=\"vertical-align: top;\"><strong>Nivel aceite: </strong>" + nivelAceite + "/10" + "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td style=\"vertical-align: top;\"><strong>Condici&oacute;n de llave: </strong>" + descLlave + "</td>\n" +
                    "<td style=\"vertical-align: top;\"><strong>Serie Gomas: </strong>" + serieGomas + "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<p>&nbsp;</p>\n" +


                    "<table class=\" fixed \" style=\"width: 100%;\" border=\"1\" cellspacing=\"2\" cellpadding=\"2\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td style=\"background-color: #cccccc; width: 50%; text-align: center;\"><strong>Luces encendidas</strong></td>\n" +
                    "<td style=\"background-color: #cccccc; width: 50%; text-align: center;\"><strong>Accesorios/Equipamientos</strong></td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +


                    "</table>\n" +
                    "<table class=\" fixed \" style=\"width: 100%;\" border=\"0\" cellspacing=\"1\" cellpadding=\"2\">\n" +
                    "<tbody>\n";


            if (descripcionLuces.size() == descAccesorios.size()) {

                for (int i = 1; i <= descripcionLuces.size(); i++) {

                    add = add + "<tr>\n" + "<td style=\"width: 5%;\">" + i + "</td>\n" +
                            "<td style=\"width: 45%;\">" + descripcionLuces.get(i - 1) + "</td>\n" +
                            "<td style=\"width: 5%;\">" + i + "</td>\n" +
                            "<td style=\"width: 45%;\">" + descAccesorios.get(i - 1) + "</td>\n" +
                            "</tr>\n";
                }

            } else if (descripcionLuces.size() > descAccesorios.size()) {

                for (int i = 1; i <= descripcionLuces.size(); i++) {

                    if (i > descAccesorios.size()) {
                        add = add + "<tr>\n" +
                                "<td style=\"width: 5%;\">" + i + "</td>\n" +
                                "<td style=\"width: 45%;\"> " + descripcionLuces.get(i - 1) + "</td>\n" +
                                "<td>&nbsp;</td>\n" +
                                "<td>&nbsp;</td>\n" +
                                "</tr>";
                    } else {
                        add = add + "<tr>\n" + "<td style=\"width: 5%;\">" + i + "</td>\n" +
                                "<td style=\"width: 45%;\">" + descripcionLuces.get(i - 1) + "</td>\n" +
                                "<td style=\"width: 5%;\">" + i + "</td>\n" +
                                "<td style=\"width: 45%;\">" + descAccesorios.get(i - 1) + "</td>\n" +
                                "</tr>\n";
                    }

                }
            } else if (descAccesorios.size() > descripcionLuces.size()) {
                for (int i = 1; i <= descAccesorios.size(); i++) {
                    if (i > descripcionLuces.size()) {
                        add = add + "<tr>\n" +
                                "<td>&nbsp;</td>\n" +
                                "<td>&nbsp;</td>\n" +
                                "<td style=\"width: 5%;\">" + i + "</td>\n" +
                                "<td style=\"width: 45%;\"> " + descAccesorios.get(i - 1) + "</td>\n" +
                                "</tr>";
                    } else {
                        add = add + "<tr>\n" + "<td style=\"width: 5%;\">" + i + "</td>\n" +
                                "<td style=\"width: 45%;\">" + descripcionLuces.get(i - 1) + "</td>\n" +
                                "<td style=\"width: 5%;\">" + i + "</td>\n" +
                                "<td style=\"width: 45%;\">" + descAccesorios.get(i - 1) + "</td>\n" +
                                "</tr>\n";
                    }
                }

            }


            htmToPDF = htmToPDF + add +
                    "</tbody>\n" +
                    "</table>\n" +

                    "<p>&nbsp;</p>\n" +


                    "<table style=\"width: 100%;\" border=\"1\" cellspacing=\"2\" cellpadding=\"2\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td style=\"background-color: #cccccc; width: 50%; text-align: center;\"><strong>Cantidades</strong></td>\n" +
                    "<td style=\"background-color: #cccccc; width: 50%; text-align: center;\"><strong>Condici&oacute;n Alfombra conductor</strong></td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<table style=\"width: 100%;\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\">\n" +
                    "<tbody>\n" +
                    "<tr>\n" +
                    "<td style=\"width: 15%; text-align: left;\"><strong>Alfombras:</strong></td>\n" +
                    "<td style=\"width: 10%;\">" + cantAlfombra + "</td>\n" +
                    "<td style=\"width: 15%; text-align: left;\"><strong>Llaves:</strong></td>\n" +
                    "<td style=\"width: 10%;\">" + cantLlaves + "</td>\n" +
                    "<td style=\"width: 50%;\">" + condicionAlfombra1 + "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td><strong>Gato:</strong></td>\n" +
                    "<td>" + cantGato + "</td>\n" +
                    "<td><strong>Alicate:</strong></td>\n" +
                    "<td>" + cantAlicate + "</td>\n" +
                    "<td>" + condicionAlfombra2 + "</td>\n" +
                    "</tr>\n" +
                    "<tr>\n" +
                    "<td><strong>Llave rueda:</strong></td>\n" +
                    "<td>" + cantLlaveRueda + "</td>\n" +
                    "<td><strong>No. Bateria:</strong></td>\n" +
                    "<td>" + noBateria + "</td>\n" +
                    "<td>" + condicionAlfombra3 + "</td>\n" +
                    "</tr>\n" +
                    "</tbody>\n" +
                    "</table>\n" +
                    "<p>&nbsp;</p>\n";
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
                        "<td style=\"width: 50%; text-align: center;\">Inspeccionador</td>\n" +
                        "<td style=\"width: 50%; text-align: center;\">Cliente</td>\n" +
                        "</tr>\n" +
                        "</tbody>\n" +
                        "</table>" +

                        "</body>\n" +
                        "</html>\n";
            }
            else{
                htmToPDF = htmToPDF  + "<p>&nbsp;</p>\n" +
                        "<table style=\"width: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                        "<tbody>\n" +
                        "<tr>\n" +
                        "<td style=\"width: 50%; text-align: center;\">__________________________________</td>\n" +
                        "<td style=\"width: 50%; text-align: center;\">__________________________________</td>\n" +
                        "</tr>\n" +
                        "<tr>\n" +
                        "<td style=\"width: 50%; text-align: center;\">Inspeccionador</td>\n" +
                        "<td style=\"width: 50%; text-align: center;\">Cliente</td>\n" +
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
            Phrase header = new Phrase("Inspección de Vehículo", ffont);
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
