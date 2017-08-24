package com.example.prueba.CheckMobile.Utils;

import android.support.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.prueba.CheckMobile.Utils.Constantes.JSON_KEY_VEHICULO;
import static com.example.prueba.CheckMobile.Utils.Constantes.RESPONSE_CODE_OK;

public class tab2HistoricoVehiculo extends Fragment {

    ListView listaMantenimientos;
    TextView textMensaje;
    //String referenciaVehiculo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_tab2_historico_vehiculo, container, false);

        listaMantenimientos = (ListView) rootView.findViewById(R.id.listaVehiculoMantenimiento);
        textMensaje = (TextView) rootView.findViewById(R.id.textMensajeMantenimiento);


        return rootView;
    }

    public void obtenerDatosMantenimiento(String referenciaVehiculo) {
        //  Toast.makeText(getContext(), "Referencia => "+referenciaVehiculo, Toast.LENGTH_SHORT).show();
        Log.d("REFERENCIA! ", "==> " + referenciaVehiculo);

        Call<OrdenTrabajoEnc> call = AdapterOrdenTrabajo.updateOrdenTrabajo(JSON_KEY_VEHICULO, referenciaVehiculo).getMantenimientos();
        call.enqueue(new Callback<OrdenTrabajoEnc>() {
            @Override
            public void onResponse(Call<OrdenTrabajoEnc> call, Response<OrdenTrabajoEnc> response) {
                if (response.isSuccessful()) {
                    OrdenTrabajoEncResponse ordenes = response.body();
                    if (ordenes.getResponseCode().equals(RESPONSE_CODE_OK)) {
                        if (ordenes.getOrdenes().isEmpty()) {
                            textMensaje.setVisibility(View.VISIBLE);
                            listaMantenimientos.setVisibility(View.GONE);
                        } else {
                            textMensaje.setVisibility(View.GONE);
                            listaMantenimientos.setVisibility(View.VISIBLE);
                            Log.d("Mantenimiento ==>", ordenes.getOrdenes().toString());
                            llenarListaMantenimientos(ordenes.getOrdenes());
                        }

                    }
                } else {
                    Toast.makeText(getContext(), "Error en el formato de respuesta de Orden", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<OrdenTrabajoEnc> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.v("Mantenimiento ===>", "Error: " + t.getMessage());
            }
        });

    }

    private void llenarListaMantenimientos(ArrayList<OrdenTrabajoEnc> ordenes) {

        List<String> lista = new ArrayList<>();
        for (OrdenTrabajoEnc orden : ordenes) {
            if (orden.getKilometraje() >= 1000 && orden.getKilometraje() < 5000) {
                lista.add("Mantenimiento de 1,000 KMs");
            } else if (orden.getKilometraje() >= 5000 && orden.getKilometraje() < 10000) {
                lista.add("Mantenimiento de 5,000 KMs");
            } else if (orden.getKilometraje() >= 10000 && orden.getKilometraje() < 40000) {
                lista.add("Mantenimiento de 10,000 KMs");
            } else if (orden.getKilometraje() >= 40000) {
                lista.add("Mantenimiento de 40,000 KMs");
            }

        }

        Log.d("LISTA","==>" + lista);
        ArrayAdapter<String> madapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_style, lista);
        madapter.setDropDownViewResource(R.layout.spinner_style);
        listaMantenimientos.setAdapter(madapter);
    }

    //    ListView listaOrdenes;
//    TextView textMensaje;
//    String referenciaVehiculo;
//    AdapterOrden madapter;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        View rootView = inflater.inflate(R.layout.activity_tab1_historico_vehiculo, container, false);
//        listaOrdenes = (ListView) rootView.findViewById(R.id.listaVehiculoOrden);
//        textMensaje = (TextView) rootView.findViewById(R.id.textMensajeOrden);
////        View view = getActivity().getCurrentFocus();
////        if (view != null) {
////        tab3HistoricoVehiculo tab3 = new tab3HistoricoVehiculo();
//////        Bundle argumentos = new Bundle();
//////        argumentos.putString("REFERENCIA", referenciaVehiculo);
//////        Log.d("REFERENCIA2", " ==> " + referenciaVehiculo);
//////        tab3.setArguments(argumentos);
////        tab3.obtenerDatosMantenimiento(referenciaVehiculo);
//////        FragmentTransaction transaction1 = getActivity().getSupportFragmentManager().beginTransaction();
//////        transaction1.add(R.id.container3, tab3);
//////        transaction1.addToBackStack(null);
//////        transaction1.commit();
////        }
//        return rootView;
//    }
//
//    public void obtenerDatosOrdenes(String referenciaVehiculo) {
//
//        Call<OrdenTrabajoEnc> call = AdapterOrdenTrabajo.getApiService(JSON_KEY_VEHICULO, referenciaVehiculo).getPedidoVehiculo();
//        call.enqueue(new Callback<OrdenTrabajoEnc>() {
//            @Override
//            public void onResponse(Call<OrdenTrabajoEnc> call, Response<OrdenTrabajoEnc> response) {
//                if (response.isSuccessful()) {
//                    OrdenTrabajoEncResponse ordenes = response.body();
//                    if (ordenes.getResponseCode().equals("200")) {
//                       if (ordenes.getOrdenes().isEmpty()) {
//                            textMensaje.setVisibility(View.VISIBLE);
//                            listaOrdenes.setVisibility(View.GONE);
//                        } else {
//                            madapter = new AdapterOrden(getContext(), ordenes.getOrdenes());
//                            listaOrdenes.setAdapter(madapter);
//                            textMensaje.setVisibility(View.GONE);
//                            listaOrdenes.setVisibility(View.VISIBLE);
//                        }
//
//                    }
//                } else {
//                    Toast.makeText(getContext(), "Error en el formato de respuesta de Orden", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<OrdenTrabajoEnc> call, Throwable t) {
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//                Log.v("Aqui===>", "Error" + t.getMessage());
//            }
//        });
//    }
//
//    private class AdapterOrden extends ArrayAdapter<OrdenTrabajoEnc> {
//        private List<OrdenTrabajoEnc> listOrden;
//
//        public AdapterOrden(Context context, List<OrdenTrabajoEnc> ordenes) {
//            super(context, R.layout.row_historico_vehiculo, ordenes);
//            listOrden = ordenes;
//        }
//
//        public View getView(final int posicion, View convertView, ViewGroup parent) {
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            final View item = inflater.inflate(R.layout.row_historico_vehiculo, null);
//
//            TextView textId = (TextView) item.findViewById(R.id.txtid);
//            TextView textFecha = (TextView) item.findViewById(R.id.txtFecha);
//            TextView textCliente = (TextView) item.findViewById(R.id.txtCliente);
//            TextView textMecanico = (TextView) item.findViewById(R.id.txtMecánico);
//
//            textId.setText("Orden >> " + KEY_TIPO_TRANS_ORDEN + "-" + listOrden.get(posicion).getId() + ")");
//            textCliente.setText("Cliente >> " + listOrden.get(posicion).getNombreCliente() + " " + listOrden.get(posicion).getApellidosCte());
//            textFecha.setText(listOrden.get(posicion).getFechaPedido());
//            textMecanico.setText("Mecánico >> " + listOrden.get(posicion).getNombre_mecanico());
//
//            item.setId(Integer.parseInt(listOrden.get(posicion).getId()));
//            return item;
//
//        }
//
//
//    }


}
