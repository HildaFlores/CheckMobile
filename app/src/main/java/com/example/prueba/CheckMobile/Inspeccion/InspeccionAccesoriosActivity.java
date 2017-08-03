package com.example.prueba.CheckMobile.Inspeccion;

import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import static com.example.prueba.CheckMobile.Utils.Constantes.JSON_KEY_LISTA;

public class InspeccionAccesoriosActivity extends Fragment {


    ListView lvAccesorios;
    List<String> listaDescripcion = new ArrayList<>();
    List<Integer> listaId = new ArrayList<>();
    private sendAccesorios mListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof sendAccesorios) {
            mListener = (sendAccesorios) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface sendAccesorios {
        void sendIdAccesorio(List<Integer> idAccesorio);

        void sendDescAccesorio(List<String> descAccesorio);
    }


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
        Call<ListaAccesorios> call = AdapterAccesorios.getServiceAccesorios(JSON_KEY_LISTA, s).getListaAccesorios();
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
            ckAccesorios.setText(lista.get(posicion).getDescripcion());
            ckAccesorios.setId(Integer.parseInt(lista.get(posicion).getValor()));


            if (!listaId.isEmpty() && !listaDescripcion.isEmpty())
            {
                for(int i = 0; i<listaId.size(); i++)
                {
                    if (ckAccesorios.getId() == listaId.get(i)) {
                        ckAccesorios.setChecked(true);
                    }
                }
            }

            ckAccesorios.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        listaDescripcion.add(compoundButton.getText().toString());
                        listaId.add(compoundButton.getId());
                        if (mListener != null) {
                           // Log.d("TAG", "Fragment ==> " + listaDescripcion);
                          //  Log.d("TAG", "Fragment ==> " + listaId);
                            mListener.sendDescAccesorio(listaDescripcion);
                            mListener.sendIdAccesorio(listaId);
                        }

                    } else {
                        for (int i = 0; i < listaDescripcion.size(); i++) {
                            if (listaDescripcion.get(i).equals(compoundButton.getText().toString())) {
                                listaDescripcion.remove(i);
                                listaId.remove(i);
                            }
                        }
                        if (mListener != null) {
                           // Log.d("TAG", "Fragment ==> " + listaDescripcion);
                            mListener.sendDescAccesorio(listaDescripcion);
                            mListener.sendIdAccesorio(listaId);
                        }
                    }

                }
            });




            return item;
        }
    }
}

