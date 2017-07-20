package com.example.prueba.CheckMobile.MenuPrincipal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.Inspeccion.ConsultaInspeccionActivity;
import com.example.prueba.CheckMobile.OrdenTrabajo.AdapterOrdenTrabajo;
import com.example.prueba.CheckMobile.OrdenTrabajo.ConsultaOrdenTrabajo;
import com.example.prueba.CheckMobile.OrdenTrabajo.OrdenTrabajoEncResponse;
import com.example.prueba.CheckMobile.OrdenTrabajo.OrdenTrabajoEnc;
import com.example.prueba.CheckMobile.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prueba on 26-may-17.
 */
public class Tab2OrdenesTrabajo extends Fragment implements GreenAdapterOrden.ListItemClickListenerOrden {

    private GreenAdapterOrden mAdapter;
    private RecyclerView mOrdenList;
    OrdenTrabajoEncResponse ordenes;
    private ArrayList<OrdenTrabajoEnc> listaOrdenes = new ArrayList<OrdenTrabajoEnc>();
    private String nombreCliente, idCondicion, idCliente, fechaOrden, observaciones, nombreMecanico, descripcionCondicion;
    private sendDataOrden mListener;
    private int NUM_LIST_ITEMS = 0;
    int idInspeccion, idOrden;
    Boolean buscar, permitePiezasReemplazo = false;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof sendDataOrden) {
            mListener = (sendDataOrden) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface sendDataOrden {
        void sendRecycleList(RecyclerView recyclerView);

        void sendOrdenList(ArrayList<OrdenTrabajoEnc> listaInspeccion);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2_ordenes_trabajo, container, false);
        mOrdenList = (RecyclerView) rootView.findViewById(R.id.rc_ordenes);
        obtenerDatosOrdenes();
        return rootView;
    }

    private void callAdapter(ArrayList<OrdenTrabajoEnc> orden) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mOrdenList.setLayoutManager(layoutManager);
        mOrdenList.setHasFixedSize(true);
        mAdapter = new GreenAdapterOrden(NUM_LIST_ITEMS, orden, this);
        mOrdenList.setAdapter(mAdapter);
        mListener.sendRecycleList(mOrdenList);
        mListener.sendOrdenList(orden);

    }

    private void obtenerDatosOrdenes() {

        Call<OrdenTrabajoEnc> call = AdapterOrdenTrabajo.getApiService("id_tipo_trans", "OTT").getOrdenTrabajo();
        call.enqueue(new OrdenCallback());
    }

    @Override
    public void onListItemClickOrden(int clickedItemIndex) {
        Intent intent = new Intent(Tab2OrdenesTrabajo.this.getContext(), ConsultaOrdenTrabajo.class);
        View vista = mOrdenList.getLayoutManager().findViewByPosition(clickedItemIndex);
        TextView textView = (TextView) vista.findViewById(R.id.txtRowOrden0);
        int index1 = textView.getText().toString().indexOf("-") + 1;
        int index2 = textView.getText().toString().indexOf(")");
        idOrden = Integer.parseInt(textView.getText().toString().substring(index1, index2));
        llenarDatosOrden();
        intent.putExtra("CLIENTE", nombreCliente);
        intent.putExtra("IDCLIENTE", idCliente);
        intent.putExtra("CONDICION", idCondicion);
        intent.putExtra("INSPECCION", idInspeccion);
        intent.putExtra("ORDEN", idOrden);
        intent.putExtra("FECHA", fechaOrden);
        intent.putExtra("OBSERVACIONES", observaciones);
        intent.putExtra("PIEZAS", permitePiezasReemplazo);
        intent.putExtra("MECANICO", nombreMecanico);
        intent.putExtra("CONDICION", descripcionCondicion);
       // Toast.makeText(getContext(), "orden==>" + idOrden, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    private void llenarDatosOrden() {
        if (idOrden != 0) {
            for (int i = 0; i < listaOrdenes.size(); i++) {
                if (listaOrdenes.get(i).getId().equals(String.valueOf(idOrden))) {
                    nombreCliente = listaOrdenes.get(i).getNombreCliente() + " " + listaOrdenes.get(i).getApellidosCte();
                    idCondicion = listaOrdenes.get(i).getIdCondicion();
                    idInspeccion = Integer.parseInt(listaOrdenes.get(i).getId_inspeccion());
                    idCliente = listaOrdenes.get(i).getCliente();
                    fechaOrden = listaOrdenes.get(i).getFechaPedido();
                    observaciones = listaOrdenes.get(i).getNotas();
                    nombreMecanico = listaOrdenes.get(i).getNombre_mecanico();
                    descripcionCondicion = listaOrdenes.get(i).getCondicion();
                    if (listaOrdenes.get(i).getPermite_pieza_reemplazo().equals("1"))
                    {
                        permitePiezasReemplazo = true;
                    }
                    Log.d("PIEZA ==> ", String.valueOf(permitePiezasReemplazo));
                }

            }
        }
    }

    @Override
    public void onReciclyListItemClickOrden(int Iditem, int clickedItemIndex) {
        final View view = mOrdenList.getLayoutManager().findViewByPosition(clickedItemIndex);
        switch (Iditem) {
            case R.id.action_ver_inspeccion: {
                Intent intent = new Intent(Tab2OrdenesTrabajo.this.getContext(), ConsultaInspeccionActivity.class);
                buscar = true;
                TextView textView = (TextView) view.findViewById(R.id.txtRowOrden0);
                int index1 = textView.getText().toString().indexOf("-") + 1;
                int index2 = textView.getText().toString().indexOf(")");
                String idOrden = textView.getText().toString().substring(index1, index2);

                for (int j = 0; j < ordenes.getOrdenes().size(); j++) {
                    if (ordenes.getOrdenes().get(j).getId().equals(idOrden)) {
                        idInspeccion = Integer.parseInt(ordenes.getOrdenes().get(j).getId_inspeccion());

                    }
                }

                intent.putExtra("IDINSPECCION", idInspeccion);
                intent.putExtra("BUSCAR", buscar);
                startActivity(intent);
                break;
            }
            case R.id.action_anular: {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                alertBuilder.setMessage("¿Está seguro de anular la orden?")
                        .setCancelable(false)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TextView textView = (TextView) view.findViewById(R.id.txtRowOrden0);
                                int index1 = textView.getText().toString().indexOf("-") + 1;
                                int index2 = textView.getText().toString().indexOf(")");
                                String idOrden = textView.getText().toString().substring(index1, index2);
                                anularOrdenTrabajo(idOrden);
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alert = alertBuilder.create();
                alert.setTitle("Anular");
                alert.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert));
                alert.show();

                break;
            }
        }
    }

    private void anularOrdenTrabajo(String idOrden) {

        Call<String> callAnular = AdapterOrdenTrabajo.updateOrdenTrabajo(idOrden).setAnulacion();
        callAnular.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body().toString().equals("200")) {
                        Toast.makeText(getContext(), "Orden anulada!", Toast.LENGTH_SHORT).show();
                        obtenerDatosOrdenes();
                    } else {
                        Toast.makeText(getContext(), "Orden no pudo ser anulada!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Error mientras se intentaba la anulación", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    class OrdenCallback implements Callback<OrdenTrabajoEnc> {
        @Override
        public void onResponse(Call<OrdenTrabajoEnc> call, Response<OrdenTrabajoEnc> response) {
            if (response.isSuccessful()) {
                ordenes = response.body();
                if (ordenes.getResponseCode().equals("200")) {
                    listaOrdenes = ordenes.getOrdenes();
                    NUM_LIST_ITEMS = ordenes.getOrdenes().size();
                    callAdapter(ordenes.getOrdenes());
                }
            } else {
                Toast.makeText(getContext(), "Error en el formato de respuesta de Orden", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onFailure(Call<OrdenTrabajoEnc> call, Throwable t) {
            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            Log.v("Aqui===>", "Error" +  t.getMessage());
        }
    }


}
