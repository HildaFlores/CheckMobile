package com.example.prueba.CheckMobile.Inspeccion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;
import com.example.prueba.CheckMobile.ProductoServicio.AdapterProductoServicio;
import com.example.prueba.CheckMobile.ProductoServicio.GreenAdapterServicio;
import com.example.prueba.CheckMobile.ProductoServicio.ProductoServicio;
import com.example.prueba.CheckMobile.ProductoServicio.ProductoServicioResponse;
import com.example.prueba.CheckMobile.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;


public class InspeccionGeneralActivity extends Fragment {
  /*  ProductoServicioResponse servicioResponse = new ProductoServicioResponse();
    private GreenAdapterServicio mAdapter;
    private RecyclerView mServicioList;
    private int NUM_LIST_ITEMS = 0 ;
    MenuItem menuItem;*/


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.activity_inspeccion_general, container, false);
       // mServicioList = (RecyclerView) rootView.findViewById(R.id.rc_servicios);
        //obtenerDatosServicios("4");
        return rootView;

    }



    /*

    private void obtenerDatosServicios(String s) {

        Call<ProductoServicio> call= AdapterProductoServicio.getApiService("id_clasificacion", s).getListaProductos();
        call.enqueue(new ServicioCallback());
    }


    private void callAdapter( ArrayList<ProductoServicio> productoServicios)
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mServicioList.setLayoutManager(layoutManager);
        mServicioList.setHasFixedSize(true);
       mAdapter = new GreenAdapterServicio(NUM_LIST_ITEMS, productoServicios);

        mServicioList.setAdapter(mAdapter);


    }

    private class ServicioCallback implements retrofit2.Callback<ProductoServicio> {
        @Override
        public void onResponse(Call<ProductoServicio> call, Response<ProductoServicio> response) {
            if (response.isSuccessful())
            {
              servicioResponse = response.body();
                NUM_LIST_ITEMS = servicioResponse.getProductoServicios().size();
                callAdapter(servicioResponse.getProductoServicios());
            }
            else {
                Toast.makeText(getContext(), "Error en el formato de respuesta de servicio", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<ProductoServicio> call, Throwable t) {
            Toast.makeText(getContext(), t.getLocalizedMessage() + t.getMessage(), Toast.LENGTH_LONG).show();
            Log.v("Serv *==>", t.getMessage());
        }
    }*/
}
