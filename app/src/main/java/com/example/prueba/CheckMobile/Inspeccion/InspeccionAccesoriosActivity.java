package com.example.prueba.CheckMobile.Inspeccion;

import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.AccesoriosParametros.AdapterAccesorios;
import com.example.prueba.CheckMobile.AccesoriosParametros.ListaAccesorios;
import com.example.prueba.CheckMobile.AccesoriosParametros.ListaAccesoriosResponse;
import com.example.prueba.CheckMobile.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class InspeccionAccesoriosActivity extends Fragment {


    ListView lvAccesorios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        View rootView = inflater.inflate(R.layout.activity_inspeccion_accesorios, container, false);
        lvAccesorios = (ListView) rootView.findViewById(R.id.listaAccesorios);
        OntenerDatosAccesorios("60");

        return rootView;
    }

    private void OntenerDatosAccesorios(String s) {

        Call<ListaAccesorios> call = AdapterAccesorios.getServiceAccesorios("id_lista", s).getListaAccesorios();
        call.enqueue(new AccesoriosCallback());
    }

    private class AccesoriosCallback implements retrofit2.Callback<ListaAccesorios> {
        @Override
        public void onResponse(Call<ListaAccesorios> call, Response<ListaAccesorios> response) {
            if (response.isSuccessful()) {
                ListaAccesoriosResponse accesoriosResponse = response.body();
               // Toast.makeText(getContext(), accesoriosResponse.getListaAccesorios().toString(), Toast.LENGTH_SHORT).show();
                AdapterListaAccesorios adapter = new AdapterListaAccesorios(getContext(), accesoriosResponse.getListaAccesorios());
                    lvAccesorios.setAdapter(adapter);

            } else {
                Toast.makeText(getContext(), "Error en el formato de respuesta de accesorios", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<ListaAccesorios> call, Throwable t) {
            Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            Log.v("accesorios-*** ===>", t.getMessage());
        }
    }

    private class AdapterListaAccesorios extends ArrayAdapter<ListaAccesorios> {

        private List<ListaAccesorios> lista;


        public AdapterListaAccesorios(Context context, List<ListaAccesorios> listaAccesorios) {
            super(context, R.layout.row_accesorios, listaAccesorios);
            lista = listaAccesorios;

        }

        public View getView(int posicion, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            View item = inflater.inflate(R.layout.row_accesorios, null);
            CheckBox ckAccesorios = (CheckBox) item.findViewById(R.id.checkAccesorios);
            ckAccesorios.setText(lista.get(posicion).getValor());
            ckAccesorios.setId(posicion);
            return item;
        }
    }
}

