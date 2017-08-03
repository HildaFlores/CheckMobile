package com.example.prueba.CheckMobile.MenuPrincipal;

/**
 * Created by Prueba on 26-may-17.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.Vehiculo.AdapterVehiculo;
import com.example.prueba.CheckMobile.Vehiculo.RegistrosAdapter;
import com.example.prueba.CheckMobile.Vehiculo.Vehiculo;
import com.example.prueba.CheckMobile.Vehiculo.VehiculoResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.prueba.CheckMobile.Utils.Constantes.RESPONSE_CODE_OK;


public class Tab3Vehiculos extends Fragment {


    VehiculoResponse vehiculoResponse = new VehiculoResponse();
    private RegistrosAdapter mAdapter;
    private RecyclerView mVehiculoList;
    private int NUM_LIST_ITEMS = 0 ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_vehiculos, container, false);
        mVehiculoList = (RecyclerView) rootView.findViewById(R.id.rv_vehiculos);
        obtenerDatosVehiculos();

        return rootView;
    }

    private void callAdapter( ArrayList<Vehiculo> veh)
    {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mVehiculoList.setLayoutManager(layoutManager);


        mVehiculoList.setHasFixedSize(true);

        mAdapter = new RegistrosAdapter(NUM_LIST_ITEMS, veh);

        mVehiculoList.setAdapter(mAdapter);

    }

    private void obtenerDatosVehiculos() {

        Call<Vehiculo> call = AdapterVehiculo.getApiService().getVehiculos();
        call.enqueue(new VehiculoCallback());
    }


    class VehiculoCallback implements Callback<Vehiculo> {
        @Override
        public void onResponse(Call<Vehiculo> call, Response<Vehiculo> response) {
            if (response.isSuccessful()) {
                VehiculoResponse vehicle = response.body();
                if (vehicle.getResponseCode().equals(RESPONSE_CODE_OK)) {
                    NUM_LIST_ITEMS = vehicle.getVehiculos().size();
                    callAdapter(vehicle.getVehiculos());
                    Log.v("Problema ===>", vehicle.getMessage());
                }
            } else {
                Toast.makeText(getContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<Vehiculo> call, Throwable t) {
            Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            Log.v("Aqui ===>", t.getMessage());
        }
    }

}


