package com.example.prueba.CheckMobile.Utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.OrdenTrabajo.AdapterOrdenTrabajo;
import com.example.prueba.CheckMobile.OrdenTrabajo.OrdenTrabajoEnc;
import com.example.prueba.CheckMobile.OrdenTrabajo.OrdenTrabajoEncResponse;
import com.example.prueba.CheckMobile.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.prueba.CheckMobile.Utils.Constantes.JSON_KEY_VEHICULO;
import static com.example.prueba.CheckMobile.Utils.Constantes.KEY_TIPO_TRANS_INSPECCION;
import static com.example.prueba.CheckMobile.Utils.Constantes.KEY_TIPO_TRANS_ORDEN;
import static com.example.prueba.CheckMobile.Utils.Constantes.RESPONSE_CODE_OK;

public class tab1HistoricoVehiculo extends Fragment {
//   // String referenciaVehiculo;
//    ListView listaInspecciones;
//    InspeccionAdapter madapter;
//    TextView mensaje;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        View rootView = inflater.inflate(R.layout.activity_tab1_historico_vehiculo, container, false);
//        listaInspecciones = (ListView) rootView.findViewById(R.id.listaVehiculoInspeccion);
//        mensaje = (TextView) rootView.findViewById(R.id.txtMensajeInspeccion);
////        Bundle arguments = this.getArguments();
////        if (arguments != null) {
////            Log.d("ARGUMENT", "==> " + arguments.getString("REFERENCIA"));
////            referenciaVehiculo = arguments.getString("REFERENCIA");
////            obtenerDatosVehiculo();
////        }
//
//        return rootView;
//    }
//
//    public void obtenerDatosVehiculo(String referenciaVehiculo) {
//        Call<InspeccionVehiculo> call = AdapterInspeccion.getApiService(JSON_KEY_VEHICULO, referenciaVehiculo).getInspeccionesPorReferencia();
//        call.enqueue(new Callback<InspeccionVehiculo>() {
//            @Override
//            public void onResponse(Call<InspeccionVehiculo> call, Response<InspeccionVehiculo> response) {
//                if (response.isSuccessful()) {
//                    InspeccionVehiculoResponse inspeccion = response.body();
//                    if (inspeccion.getResponseCode().equals(RESPONSE_CODE_OK)) {
//                        if (inspeccion.getInspecciones().isEmpty()) {
//                            mensaje.setVisibility(View.VISIBLE);
//                            listaInspecciones.setVisibility(View.GONE);
//                        } else {
//                            madapter = new InspeccionAdapter(getContext(), inspeccion.getInspecciones());
//                            listaInspecciones.setAdapter(madapter);
//                             mensaje.setVisibility(View.GONE);
//                            listaInspecciones.setVisibility(View.VISIBLE);
//                        }
//                    }
//                } else {
//                    Toast.makeText(getContext(), "Error en el formato de respuesta de inspeccion", Toast.LENGTH_SHORT).show();
//                    Log.v("INSPECCION ==>", response.errorBody().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<InspeccionVehiculo> call, Throwable t) {
//                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
//                Log.v("Inspecciones ===>", "Error: " + t.getMessage());
//            }
//        });
//    }
//
//    public class InspeccionAdapter extends ArrayAdapter<InspeccionVehiculo> {
//        private List<InspeccionVehiculo> listaInspecciones;
//
//        public InspeccionAdapter(Context context, List<InspeccionVehiculo> ins) {
//            super(context, R.layout.row_historico_vehiculo, ins);
//            listaInspecciones = ins;
//        }
//
//        public View getView(int posicion, View convertView, ViewGroup parent) {
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            View item = inflater.inflate(R.layout.row_historico_vehiculo, null);
//
//            TextView textId = (TextView) item.findViewById(R.id.txtid);
//            TextView textFecha = (TextView) item.findViewById(R.id.txtFecha);
//            TextView textCliente = (TextView) item.findViewById(R.id.txtCliente);
//            TextView textMecanico = (TextView) item.findViewById(R.id.txtMecánico);
//
//            textId.setText("Inspección >> IV-" + listaInspecciones.get(posicion).getId());
//            textFecha.setText(listaInspecciones.get(posicion).getFechaInspeccion());
//            textCliente.setText("Cliente >> " + listaInspecciones.get(posicion).getNombre_cliente());
//            textMecanico.setVisibility(View.GONE);
//            item.setId(Integer.parseInt(listaInspecciones.get(posicion).getId()));
//
//            return item;
//        }
//    }


    ListView listaOrdenes;
    TextView textMensaje;
    String referenciaVehiculo;
    AdapterOrden madapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_tab1_historico_vehiculo, container, false);
        listaOrdenes = (ListView) rootView.findViewById(R.id.listaVehiculoOrden);
        textMensaje = (TextView) rootView.findViewById(R.id.textMensajeOrden);
//        View view = getActivity().getCurrentFocus();
//        if (view != null) {
//        tab3HistoricoVehiculo tab3 = new tab3HistoricoVehiculo();
////        Bundle argumentos = new Bundle();
////        argumentos.putString("REFERENCIA", referenciaVehiculo);
////        Log.d("REFERENCIA2", " ==> " + referenciaVehiculo);
////        tab3.setArguments(argumentos);
//        tab3.obtenerDatosMantenimiento(referenciaVehiculo);
////        FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
////        transaction1.add(R.id.container3, tab3);
////        transaction1.addToBackStack(null);
////        transaction1.commit();
//        }
        return rootView;
    }

    public void obtenerDatosOrdenes(String referenciaVehiculo) {

        Call<OrdenTrabajoEnc> call = AdapterOrdenTrabajo.getApiService(JSON_KEY_VEHICULO, referenciaVehiculo).getPedidoVehiculo();
        call.enqueue(new Callback<OrdenTrabajoEnc>() {
            @Override
            public void onResponse(Call<OrdenTrabajoEnc> call, Response<OrdenTrabajoEnc> response) {
                if (response.isSuccessful()) {
                    OrdenTrabajoEncResponse ordenes = response.body();
                    if (ordenes.getResponseCode().equals(RESPONSE_CODE_OK)) {
                        if (ordenes.getOrdenes().isEmpty()) {
                            textMensaje.setVisibility(View.VISIBLE);
                            listaOrdenes.setVisibility(View.GONE);
                        } else {
                            madapter = new AdapterOrden(getContext(), ordenes.getOrdenes());
                            listaOrdenes.setAdapter(madapter);
                            textMensaje.setVisibility(View.GONE);
                            listaOrdenes.setVisibility(View.VISIBLE);
                        }

                    }
                } else {
                    Toast.makeText(getContext(), "Error en el formato de respuesta de Orden", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrdenTrabajoEnc> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.v("Aqui===>", "Error" + t.getMessage());
            }
        });
    }

    private class AdapterOrden extends ArrayAdapter<OrdenTrabajoEnc> {
        private List<OrdenTrabajoEnc> listOrden;

        public AdapterOrden(Context context, List<OrdenTrabajoEnc> ordenes) {
            super(context, R.layout.row_historico_vehiculo, ordenes);
            listOrden = ordenes;
        }

        public View getView(final int posicion, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            final View item = inflater.inflate(R.layout.row_historico_vehiculo, null);

            TextView textId = (TextView) item.findViewById(R.id.txtid);
            TextView textIdIns = (TextView) item.findViewById(R.id.txtidInspeccion);
            TextView textFecha = (TextView) item.findViewById(R.id.txtFecha);
            TextView textCliente = (TextView) item.findViewById(R.id.txtCliente);
            TextView textMecanico = (TextView) item.findViewById(R.id.txtMecánico);

            textId.setText(KEY_TIPO_TRANS_ORDEN + "-" + listOrden.get(posicion).getId());
            textIdIns.setText(KEY_TIPO_TRANS_INSPECCION + "-" + listOrden.get(posicion).getId_inspeccion());
            textCliente.setText(listOrden.get(posicion).getNombreCliente() + " " + listOrden.get(posicion).getApellidosCte());


            if (listOrden.get(posicion).getFechaPedido() != null) {
//                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//                Date myDate = null;
//                try {
//                    myDate = df.parse(listOrden.get(posicion).getFechaPedido().substring(0, 10));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
                textFecha.setText(listOrden.get(posicion).getFechaPedido());
            }
            textMecanico.setText(listOrden.get(posicion).getNombre_mecanico());
            item.setId(Integer.parseInt(listOrden.get(posicion).getId()));
            return item;

        }


    }
}