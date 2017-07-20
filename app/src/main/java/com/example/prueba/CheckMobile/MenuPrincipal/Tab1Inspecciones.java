package com.example.prueba.CheckMobile.MenuPrincipal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.prueba.CheckMobile.Inspeccion.AdapterInspeccion;
import com.example.prueba.CheckMobile.Inspeccion.ConsultaInspeccionActivity;
import com.example.prueba.CheckMobile.Inspeccion.InspeccionVehiculo;
import com.example.prueba.CheckMobile.Inspeccion.InspeccionVehiculoResponse;
import com.example.prueba.CheckMobile.OrdenTrabajo.OrdenTrabajoActivity;
import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.Vehiculo.VehiculoActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prueba on 26-may-17.
 */

public class Tab1Inspecciones extends Fragment implements GreenAdapterInspeccion.ListItemClickListener {

    private GreenAdapterInspeccion mAdapter;
    private RecyclerView mInspeccionList;
    private int NUM_LIST_ITEMS = 0;
    private int idInspeccion = 0;
    private View vista;
    private String nombreVehiculo;
    private String nombreCliente;
    private String motor;
    private String kilometraje;
    private String serieGomas;
    private String fechaInspeccion;
    private String observaciones;
    private String idCondicion;
    private String idCliente;
    private ArrayList<InspeccionVehiculo> inspecciones = new ArrayList<InspeccionVehiculo>();
    private sendData mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof sendData) {
            mListener = (sendData) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface sendData {
        void sendAdapter(RecyclerView recyclerView);

        void sendList(ArrayList<InspeccionVehiculo> listaInspeccion);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1_inspecciones, container, false);
        mInspeccionList = (RecyclerView) rootView.findViewById(R.id.rc_inspeccion);

        ObtenerDatosInspeccion();

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.flotanteInspeccion);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tab1Inspecciones.this.getContext(), VehiculoActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void ObtenerDatosInspeccion() {
        Call<InspeccionVehiculo> call = AdapterInspeccion.getApiService().getInspecciones();
        call.enqueue(new InspeccionCallback());

    }

    private void callAdapter(ArrayList<InspeccionVehiculo> inspeccionVeh) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mInspeccionList.setLayoutManager(layoutManager);
        mInspeccionList.setHasFixedSize(true);
        mAdapter = new GreenAdapterInspeccion(NUM_LIST_ITEMS, inspeccionVeh, this);
        mInspeccionList.setAdapter(mAdapter);
        mListener.sendAdapter(mInspeccionList);
        mListener.sendList(inspeccionVeh);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_context_inspeccion_action, menu);
    }

    private void anularInspeccion(String id) {
        Call<String> callAnular = AdapterInspeccion.updateInspeccion(id).setUpdate();
        callAnular.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.body().equals("200")) {
                        Toast.makeText(getContext(), "Inspección anulada con éxito!", Toast.LENGTH_SHORT).show();
                        ObtenerDatosInspeccion();
                    } else {
                        Toast.makeText(getContext(), "Error al anular inspección", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
                    Log.v("INSPECCION ==>", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
                Log.v("INSPECCION ==>", t.getMessage().toString());
            }
        });
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(Tab1Inspecciones.this.getContext(), ConsultaInspeccionActivity.class);
        vista = mInspeccionList.getLayoutManager().findViewByPosition(clickedItemIndex);
        TextView textView = (TextView) vista.findViewById(R.id.txtRowInspeccion0);
        int index1 = textView.getText().toString().indexOf("-") + 1;
        int index2 = textView.getText().toString().indexOf(")");
        idInspeccion = Integer.parseInt(textView.getText().toString().substring(index1, index2));
        llenarValoresInspeccion();
        intent.putExtra("IDINSPECCION", idInspeccion);
        intent.putExtra("VEHICULO", nombreVehiculo);
        intent.putExtra("CLIENTE", nombreCliente);
        intent.putExtra("MOTOR", motor);
        intent.putExtra("KILOMETRAJE", kilometraje);
        intent.putExtra("FECHA", fechaInspeccion);
        intent.putExtra("OBSERVACION", observaciones);
        intent.putExtra("SERIEGOMAS", serieGomas);
        intent.putExtra("CONSULTAR", true);
        startActivity(intent);
    }

    @Override
    public void onReciclyListItemClick(int idItem, final int clickedItemIndex) {
        final View view = mInspeccionList.getLayoutManager().findViewByPosition(clickedItemIndex);
        switch (idItem) {
            case R.id.action_convert:
            {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                alertBuilder.setMessage("¿Está seguro de generar orden de trabajo?")
                        .setCancelable(false)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TextView textView = (TextView) view.findViewById(R.id.txtRowInspeccion0);
                                int index1 = textView.getText().toString().indexOf("-") + 1;
                                int index2 = textView.getText().toString().indexOf(")");
                                idInspeccion = Integer.parseInt(textView.getText().toString().substring(index1, index2));
                                actualizarInspeccion(idInspeccion);

                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                AlertDialog alert = alertBuilder.create();
                alert.setTitle("Generar orden");
                alert.setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert));
                alert.show();

                break;
            }

            case R.id.action_delete:
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                alertBuilder.setMessage("¿Está seguro de anular la inspección?")
                        .setCancelable(false)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TextView textView = (TextView) view.findViewById(R.id.txtRowInspeccion0);
                                int index1 = textView.getText().toString().indexOf("-") + 1;
                                int index2 = textView.getText().toString().indexOf(")");
                                idInspeccion = Integer.parseInt(textView.getText().toString().substring(index1, index2));
                                anularInspeccion(String.valueOf(idInspeccion));
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
//Cambiar estado de inspeccion a Finalizada
    private void actualizarInspeccion(final int idInspeccion) {
        Call<String> callActualizar = AdapterInspeccion.updateInspeccion(String.valueOf(idInspeccion)).setConvert();
        callActualizar.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful())
                {
                    if (response.body().toString().equals("200"))
                    {
                        Intent intent = new Intent(getActivity(), OrdenTrabajoActivity.class);
                        llenarValoresInspeccion();
                        intent.putExtra("IDINSPECCION", String.valueOf(idInspeccion));
                        intent.putExtra("CLIENTE", nombreCliente);
                        intent.putExtra("VEHICULO", nombreVehiculo);
                        intent.putExtra("IDCONDICION", idCondicion);
                        intent.putExtra("IDCLIENTE", idCliente);
                        startActivity(intent);
                        Toast.makeText(getContext(), "Orden generada", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Error al generar orden", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Error: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.v("Inspeccion", t.getMessage());
            }
        });


    }

    private class InspeccionCallback implements retrofit2.Callback<InspeccionVehiculo> {
        @Override
        public void onResponse(Call<InspeccionVehiculo> call, Response<InspeccionVehiculo> response) {
            if (response.isSuccessful()) {
                InspeccionVehiculoResponse inspeccion = response.body();
                if (inspeccion.getResponseCode().equals("200")) {
                    inspecciones = inspeccion.getInspecciones();
                    NUM_LIST_ITEMS = inspeccion.getInspecciones().size();
                    callAdapter(inspeccion.getInspecciones());
                }
            } else {
                Toast.makeText(getContext(), "Error en el formato de respuesta de inspección", Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void onFailure(Call<InspeccionVehiculo> call, Throwable t) {
            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            Log.v("Aqui ===>", "error: " + t.getMessage());

        }
    }

    private void llenarValoresInspeccion() {
        if (idInspeccion != 0) {
            for (int i = 0; i < inspecciones.size(); i++) {
                if (inspecciones.get(i).getId().equals(String.valueOf(idInspeccion))) {
                    nombreVehiculo = inspecciones.get(i).getNombre_vehiculo();
                    nombreCliente = inspecciones.get(i).getNombre_cliente();
                    motor = inspecciones.get(i).getMotor();
                    kilometraje = inspecciones.get(i).getKilometraje();
                    serieGomas = inspecciones.get(i).getSerieGomas();
                    fechaInspeccion = inspecciones.get(i).getFechaInspeccion();
                    observaciones = inspecciones.get(i).getObservaciones();
                    idCondicion = inspecciones.get(i).getId_condicion();
                    idCliente = inspecciones.get(i).getIdCliente();
                }

            }
        }

    }

}
