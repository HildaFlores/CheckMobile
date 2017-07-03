package com.example.prueba.CheckMobile.MenuPrincipal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.Inspeccion.AdapterInspeccion;
import com.example.prueba.CheckMobile.Inspeccion.ConsultaInspeccionActivity;
import com.example.prueba.CheckMobile.Inspeccion.InspeccionVehiculo;
import com.example.prueba.CheckMobile.Inspeccion.InspeccionVehiculoResponse;
import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.Vehiculo.VehiculoActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static android.R.attr.id;
import static android.R.string.no;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

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
    private String nombreCliente ;
    private String motor;
    private String kilometraje;
    private String serieGomas;
    private String fechaInspeccion;
    private String observaciones;
    private ArrayList<InspeccionVehiculo> inspecciones = new ArrayList<InspeccionVehiculo>();
    List<String> list = new ArrayList<>();
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
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(Tab1Inspecciones.this.getContext(), ConsultaInspeccionActivity.class);
        vista = mInspeccionList.getLayoutManager().findViewByPosition(clickedItemIndex);
       TextView textView = (TextView) vista.findViewById(R.id.txtRowInspeccion0);
        int index1 = textView.getText().toString().indexOf("-") + 1;
        int index2 = textView.getText().toString().indexOf(")");
        idInspeccion = Integer.parseInt(textView.getText().toString().substring(index1,index2));
        llenarValoresInspeccion();
        intent.putExtra("IDINSPECCION", idInspeccion);
        intent.putExtra("VEHICULO", nombreVehiculo);
        intent.putExtra("CLIENTE", nombreCliente);
        intent.putExtra("MOTOR", motor);
        intent.putExtra("KILOMETRAJE", kilometraje);
        intent.putExtra("FECHA", fechaInspeccion);
        intent.putExtra("OBSERVACION", observaciones);
        intent.putExtra("SERIEGOMAS", serieGomas);
        startActivity(intent);
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
                Toast.makeText(getContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
                Log.v("INSPECCION ==>", response.errorBody().toString());
            }
        }

        @Override
        public void onFailure(Call<InspeccionVehiculo> call, Throwable t) {
            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            Log.v("Aqui ===>", t.getMessage());

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
                }

            }
        }

    }

}
