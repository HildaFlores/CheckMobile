package com.example.prueba.CheckMobile.Inspeccion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.TipoTransaccion.AdapterTipoTransaccion;
import com.example.prueba.CheckMobile.TipoTransaccion.TipoTransaccion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Response;

import static com.example.prueba.CheckMobile.Utils.Constantes.KEY_TIPO_TRANS_INSPECCION;


public class InspeccionGeneralActivity extends Fragment {


    TextView cliente;
    TextView vehiculo;
    EditText fechaInspeccion;
    EditText motor;
    EditText kilometraje;
    EditText serieGomas;
    EditText observaciones;
    EditText idInspeccion;
    RadioGroup radio1;
    RadioGroup radio2;
    RadioGroup radio3;


    private Timer timer = new Timer();
    private final long DELAY = 0;
    String idCliente, idVehiculo, NombreVehiculo, NombreCliente, chasis, referencia;
    String fecha, kilo, moto, serie, notas, condicionAlfombra1, condicionAlfombra2, condicionAlfombra3;
    List<String> idRespuesta = new ArrayList<>();
    List<String> descAlfrombra = new ArrayList<>();
    private passingData mListener;
    boolean actualizar = false;
    List<String> listaDescripcion = new ArrayList<>();
    List<Integer> listaId = new ArrayList<>();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof passingData) {
            mListener = (passingData) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }


    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface passingData {
        void actualizar(boolean update);

        void OnPassingFecha(String fecha);

        void OnPassinKilo(String kilometraje);

        void OnPassinMotor(String motor);

        void OnPassinSerieGomas(String SerieGoma);

        void OnpassingObservaciones(String observaciones);

        void OnpassingIdRespuesta(List<String> idRespuesta);

        void OnpassingDescAlfrombra(List<String> descAlfrombra);

        void OnpassingNombreVeh(String nombre);

        void OnpassingNomcreCte(String nombre);

        void OnpassingPlaca(String placa);

        void OnpassingColor(String color);

        void OnpassingDocIdentidad(String docIdentidad);

        void OnpassingCelular(String celular);

        void OnpassingTelefono(String telefono);

        void OnpassingCondicion1(String alfombra1);

        void OnpassingCondicion2(String alfombra2);

        void OnpassingCondicion3(String alfombra3);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.activity_inspeccion_general, container, false);

        cliente = (TextView) rootView.findViewById(R.id.txtDatosCliente);
        vehiculo = (TextView) rootView.findViewById(R.id.txtDatosVehiculo);
        fechaInspeccion = (EditText) rootView.findViewById(R.id.EtxtFechaInspeccion);
        motor = (EditText) rootView.findViewById(R.id.etxtMotor);
        kilometraje = (EditText) rootView.findViewById(R.id.etxtKilometraje);
        serieGomas = (EditText) rootView.findViewById(R.id.etxtSerieGomas);
        observaciones = (EditText) rootView.findViewById(R.id.etxtNotasInspeccion);
        idInspeccion = (EditText) rootView.findViewById(R.id.etxtNoInspeccion);
        radio1 = (RadioGroup) rootView.findViewById(R.id.rgCondicion1);
        radio2 = (RadioGroup) rootView.findViewById(R.id.rgCondicion2);
        radio3 = (RadioGroup) rootView.findViewById(R.id.rgCondicion3);
        // mServicioList = (RecyclerView) rootView.findViewById(R.id.rc_servicios);
        //obtenerDatosServicios("4");


        Intent intent = getActivity().getIntent();
        Bundle extra = intent.getExtras();
        if (extra != null) {
            idCliente = extra.getString("IDCLIENTE");
            idVehiculo = extra.getString("IDVEHICULO");
            NombreCliente = extra.getString("CLIENTE");
            NombreVehiculo = extra.getString("VEHICULO");
            referencia = extra.getString("REFERENCIA");
            chasis = extra.getString("CHASIS");
            actualizar = extra.getBoolean("ACTUALIZAR");


            cliente.setText("CLIENTE  >> " + "(" + idCliente + ")" + NombreCliente);
            vehiculo.setText("VEHICULO >> " + "(" + idVehiculo + ")" + NombreVehiculo);

            mListener.OnpassingNombreVeh(NombreVehiculo);
            mListener.OnpassingNomcreCte(NombreCliente);
            mListener.OnpassingPlaca(extra.getString("PLACA"));
            mListener.OnpassingColor(extra.getString("COLOR"));
            mListener.OnpassingDocIdentidad(extra.getString("DOCIDENTIDAD"));
            mListener.OnpassingTelefono(extra.getString("CELULAR"));
            mListener.OnpassingCelular(extra.getString("TELEFONO"));

            obtenerProximaSecuencia(KEY_TIPO_TRANS_INSPECCION);

        }

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        fechaInspeccion.setText(df.format(c.getTime()));
        fechaInspeccion.setEnabled(false);
        EnvioDatosAGuardar();
        ObtenerValorFecha();
        ObtenerValorKilometraje();
        ObtenerValorMotor();
        ObtenerValorObservaciones();
        ObtenerValorSerie();
        ObtenerCondicionAlfrombra();

        return rootView;


    }

    private void ObtenerCondicionAlfrombra() {
        idRespuesta = new ArrayList<>();
        descAlfrombra = new ArrayList<>();

        radio1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rbBien: {
                        idRespuesta.add("9");
                        descAlfrombra.add("Condicion Alfrombra");

                        for (int j = 0; j < idRespuesta.size(); j++) {
                            if (idRespuesta.get(j).equals("12")) {
                                idRespuesta.remove(j);
                                descAlfrombra.remove(j);
                            }
                        }
                        RadioButton rb = (RadioButton) radio1.findViewById(i);
                        condicionAlfombra1 = rb.getText().toString();
                        if (mListener != null) {
                            Log.d("TAG", "Fragment ==> " + idRespuesta);
                            mListener.OnpassingIdRespuesta(idRespuesta);
                            mListener.OnpassingDescAlfrombra(descAlfrombra);
                            mListener.OnpassingCondicion1(condicionAlfombra1);
                        }

                        break;
                    }
                    case R.id.rbNoBien: {
                        idRespuesta.add("12");
                        descAlfrombra.add("Condicion Alfrombra");

                        for (int j = 0; j < idRespuesta.size(); j++) {
                            if (idRespuesta.get(j).equals("9")) {
                                idRespuesta.remove(j);
                                descAlfrombra.remove(j);
                            }
                        }

                        RadioButton rb = (RadioButton) radio1.findViewById(i);
                        condicionAlfombra1 = rb.getText().toString();
                        if (mListener != null) {
                            Log.d("TAG", "Fragment ==> " + idRespuesta);
                            mListener.OnpassingIdRespuesta(idRespuesta);
                            mListener.OnpassingDescAlfrombra(descAlfrombra);
                            mListener.OnpassingCondicion1(condicionAlfombra1);
                        }

                        break;
                    }
                }
            }
        });
        radio2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                switch (i) {
                    case R.id.rbGenuina: {
                        idRespuesta.add("10");
                        descAlfrombra.add("Condicion Alfrombra");
                        for (int j = 0; j < idRespuesta.size(); j++) {
                            if (idRespuesta.get(j).equals("13")) {
                                idRespuesta.remove(j);
                                descAlfrombra.remove(j);
                            }
                        }
                        RadioButton rb = (RadioButton) radio2.findViewById(i);
                        condicionAlfombra2 = rb.getText().toString();
                        if (mListener != null) {
                            Log.d("TAG", "Fragment ==> " + idRespuesta);
                            mListener.OnpassingIdRespuesta(idRespuesta);
                            mListener.OnpassingDescAlfrombra(descAlfrombra);
                            mListener.OnpassingCondicion2(condicionAlfombra2);
                        }

                        break;
                    }
                    case R.id.rbNoGenuina: {
                        idRespuesta.add("13");
                        descAlfrombra.add("Condicion Alfrombra");

                        for (int j = 0; j < idRespuesta.size(); j++) {
                            if (idRespuesta.get(j).equals("10")) {
                                idRespuesta.remove(j);
                                descAlfrombra.remove(j);
                            }
                        }
                        RadioButton rb = (RadioButton) radio2.findViewById(i);
                        condicionAlfombra2 = rb.getText().toString();
                        if (mListener != null) {
                            Log.d("TAG", "Fragment ==> " + idRespuesta);
                            Log.d("TAG", "Fragment ==> " + descAlfrombra);
                            mListener.OnpassingIdRespuesta(idRespuesta);
                            mListener.OnpassingDescAlfrombra(descAlfrombra);
                            mListener.OnpassingCondicion2(condicionAlfombra2);
                        }
                        break;
                    }
                }
            }
        });

        radio3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rbSoloUna: {
                        idRespuesta.add("11");
                        descAlfrombra.add("Condicion Alfrombra");

                        for (int j = 0; j < idRespuesta.size(); j++) {
                            if (idRespuesta.get(j).equals("14")) {
                                idRespuesta.remove(j);
                                descAlfrombra.remove(j);
                            }
                        }
                        RadioButton rb = (RadioButton) radio3.findViewById(i);
                        condicionAlfombra3 = rb.getText().toString();
                        if (mListener != null) {
                            Log.d("TAG", "Fragment ==> " + idRespuesta);
                            Log.d("TAG", "Fragment ==> " + descAlfrombra);
                            mListener.OnpassingIdRespuesta(idRespuesta);
                            mListener.OnpassingDescAlfrombra(descAlfrombra);
                            mListener.OnpassingCondicion3(condicionAlfombra3);
                        }
                        break;
                    }
                    case R.id.rbDosOMas: {
                        idRespuesta.add("14");
                        descAlfrombra.add("Condicion Alfrombra");

                        for (int j = 0; j < idRespuesta.size(); j++) {
                            if (idRespuesta.get(j).equals("11")) {
                                idRespuesta.remove(j);
                                descAlfrombra.remove(j);
                            }
                        }
                        RadioButton rb = (RadioButton) radio3.findViewById(i);
                        condicionAlfombra3 = rb.getText().toString();
                        if (mListener != null) {
                            Log.d("TAG", "Fragment ==> " + idRespuesta);
                            Log.d("TAG", "Fragment ==> " + descAlfrombra);
                            mListener.OnpassingIdRespuesta(idRespuesta);
                            mListener.OnpassingDescAlfrombra(descAlfrombra);
                            mListener.OnpassingCondicion3(condicionAlfombra3);

                        }
                        break;
                    }
                }
            }
        });
    }

    private void obtenerProximaSecuencia(String valor) {
        Call<ArrayList<TipoTransaccion>> callTransaccion = AdapterTipoTransaccion.getTransaccion("id_tipo_trans", valor).getTipoTransaccion();
        callTransaccion.enqueue(new TransaccionCallback());


    }


    private void ObtenerValorSerie() {

        serieGomas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 1) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    serie = serieGomas.getText().toString();
                                    if (mListener != null) {
                                        Log.d("TAG", "Fragment ==> " + serie);
                                        mListener.OnPassinSerieGomas(serie);
                                    }
                                }
                            });
                        }

                    }, DELAY);
                }
            }
        });
    }

    private void ObtenerValorObservaciones() {


        observaciones.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 1) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    notas = observaciones.getText().toString();
                                    if (mListener != null) {
                                        Log.d("TAG", "Fragment ==> " + notas);
                                        mListener.OnpassingObservaciones(notas);
                                    }
                                }
                            });
                        }

                    }, DELAY);
                }
            }
        });


    }

    private void ObtenerValorMotor() {
        motor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 2) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    moto = motor.getText().toString();
                                    if (mListener != null) {
                                        Log.d("TAG", "Fragment ==> " + moto);
                                        mListener.OnPassinMotor(moto);
                                    }
                                }
                            });
                        }

                    }, DELAY);
                }

            }
        });
    }

    private void ObtenerValorKilometraje() {
        kilometraje.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null)
                    timer.cancel();


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 1) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    kilo = kilometraje.getText().toString();
                                    if (mListener != null) {
                                        Log.d("TAG", "Fragment ==> " + kilo);
                                        mListener.OnPassinKilo(kilo);
                                    }
                                }
                            });
                        }

                    }, DELAY);
                }
            }
        });

    }

    private void ObtenerValorFecha() {

        fecha = fechaInspeccion.getText().toString();
        if (mListener != null) {
            Log.d("TAG", "Fragment ==> " + fecha);
            mListener.OnPassingFecha(fecha);
        }
/*
        fechaInspeccion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null)
                    timer.cancel();

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 10) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {



                                }

                            });
                        }

                    }, DELAY);
                }
            }
        });*/

    }


    private void EnvioDatosAGuardar() {

        Intent intent = new Intent(getActivity(), MainInspeccionActivity.class);
        intent.putExtra("REFERENCIA", referencia);
        intent.putExtra("CHASIS", chasis);
        intent.putExtra("IDCLIENTE", idCliente);
        intent.putExtra("IDVEHICULO", idVehiculo);

        //startActivity(intent);


    }

    private class TransaccionCallback implements retrofit2.Callback<ArrayList<TipoTransaccion>> {
        @Override
        public void onResponse(Call<ArrayList<TipoTransaccion>> call, Response<ArrayList<TipoTransaccion>> response) {
            if (response.isSuccessful()) {
                ArrayList<TipoTransaccion> tipo = response.body();
                if (!tipo.isEmpty()) {
                    idInspeccion.setText(tipo.get(0).getId().toUpperCase() + " - " + String.valueOf(tipo.get(0).getSecuencia()));
                }

            }else
            {
                Toast.makeText(getContext(), "Error el respuesta de transacción", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<ArrayList<TipoTransaccion>> call, Throwable t) {
            Toast.makeText(getContext(), "Error de respuesta", Toast.LENGTH_SHORT).show();
        }
    }


}
