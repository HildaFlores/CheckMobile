package com.example.prueba.CheckMobile.MenuPrincipal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.prueba.CheckMobile.Cliente.AdapterCliente;
import com.example.prueba.CheckMobile.Cliente.Cliente;
import com.example.prueba.CheckMobile.Cliente.ClienteResponse;
import com.example.prueba.CheckMobile.Cliente.GreenAdapter;
import com.example.prueba.CheckMobile.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Prueba on 26-may-17.
 */

public class Tab2OrdenesTrabajo extends Fragment {

    ClienteResponse clienteResponse = new ClienteResponse();
    private GreenAdapter mAdapter;
    private RecyclerView mClienteList;
    private int NUM_LIST_ITEMS = 0 ;

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2_ordenes_trabajo, container, false);
        mClienteList = (RecyclerView) rootView.findViewById(R.id.rc_numbers);

        obtenerDatosClientes();
        return rootView;
    }


    private void callAdapter( ArrayList<Cliente> cte)
    {
        /*
         * A LinearLayoutManager is responsible for measuring and positioning item views within a
         * RecyclerView into a linear list. This means that it can produce either a horizontal or
         * vertical list depending on which parameter you pass in to the LinearLayoutManager
         * constructor. By default, if you don't specify an orientation, you get a vertical list.
         * In our case, we want a vertical list, so we don't need to pass in an orientation flag to
         * the LinearLayoutManager constructor.
         *
         * There are other LayoutManagers available to display your data in uniform grids,
         * staggered grids, and more! See the developer documentation for more details.
         */
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mClienteList.setLayoutManager(layoutManager);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mClienteList.setHasFixedSize(true);

        /*
         * The GreenAdapter is responsible for displaying each item in the list.
         */
        mAdapter = new GreenAdapter(NUM_LIST_ITEMS, cte);

        mClienteList.setAdapter(mAdapter);

    }
    private void obtenerDatosClientes() {

        Call<Cliente> call = AdapterCliente.getApiService().getClientes();
        call.enqueue(new ClienteCallback());
    }
    class ClienteCallback implements Callback<Cliente> {


        @Override
        public void onResponse(Call<Cliente> call, Response<Cliente> response) {
            if (response.isSuccessful()) {
                ClienteResponse cliente = response.body();
                if (cliente.getResponseCode().equals("200")) {
                    NUM_LIST_ITEMS = cliente.getClientes().size();
                    callAdapter(cliente.getClientes());
                    Log.v("vehiculo===>", cliente.getMessage());
                }
            } else {
                Toast.makeText(getContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<Cliente> call, Throwable t) {
            Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            Log.v("Aqui ===>", t.getMessage());
        }
    }



}
