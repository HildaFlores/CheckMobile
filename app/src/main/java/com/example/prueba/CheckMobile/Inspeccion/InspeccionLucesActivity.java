package com.example.prueba.CheckMobile.Inspeccion;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.LucesParametros.AdapterLucesParametros;
import com.example.prueba.CheckMobile.LucesParametros.ListaLuces;
import com.example.prueba.CheckMobile.LucesParametros.ListaLucesResponse;
import com.example.prueba.CheckMobile.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class InspeccionLucesActivity extends Fragment {

    ListView listaLuces;
    List<String> listaDescripcion = new ArrayList<>();
    List<Integer> listaId = new ArrayList<>();
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
        void sendIdLuces(List<Integer> idLuces);

        void sendDescLuces(List<String> descLuces);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_inspeccion_luces, container, false);

        listaLuces = (ListView) rootView.findViewById(R.id.listaLuces);
        ObtenerDatosLuces("58");

        return rootView;
    }

    private void ObtenerDatosLuces(String valor) {

        Call<ListaLuces> call = AdapterLucesParametros.getApiService("id_lista", valor).getListaLuces();
        call.enqueue(new LucesCallback());

    }

    private class LucesCallback implements retrofit2.Callback<ListaLuces> {
        @Override
        public void onResponse(Call<ListaLuces> call, Response<ListaLuces> response) {
            if (response.isSuccessful()) {
                ListaLucesResponse listaResponse = response.body();
                AdapterLuces adapterLuces = new AdapterLuces(getContext(), listaResponse.getListaParametros());
                listaLuces.setAdapter(adapterLuces);

            } else {
                Toast.makeText(getContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<ListaLuces> call, Throwable t) {

            Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
          //  Log.v("Luces-*** ===>", t.getMessage());
        }
    }


    private class AdapterLuces extends ArrayAdapter<ListaLuces> {

        private List<ListaLuces> listaLuces;

        public AdapterLuces(Context context, List<ListaLuces> luces) {
            super(context, R.layout.row_lista_luces, luces);
            listaLuces = luces;

        }

        public View getView(int posicion, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.row_lista_luces, null);

            final CheckBox ckLuces = (CheckBox) item.findViewById(R.id.checkLuz);
            ImageView imImagen = (ImageView) item.findViewById(R.id.ivLuces);
            ckLuces.setText(listaLuces.get(posicion).getDescripcion());
            ckLuces.setId(Integer.parseInt(listaLuces.get(posicion).getValor()));
            String url = listaLuces.get(posicion).getRutaImagen();
            String[] p = url.split("/");
            String imageLink = "https://drive.google.com/uc?export=download&id=" + p[5];
            Picasso.with(getContext())
                    .load(imageLink)
                    .into(imImagen);
            ckLuces.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        listaDescripcion.add(compoundButton.getText().toString());
                        listaId.add(compoundButton.getId());
                        if (mListener != null) {
                          //  Log.d("TAG", "Fragment ==> " + listaDescripcion);
                           // Log.d("TAG", "Fragment ==> " + listaId);
                            mListener.sendDescLuces(listaDescripcion);
                            mListener.sendIdLuces(listaId);
                        }

                        //   Toast.makeText(getContext(), "Seleccionado  >> " + listaId.toString() + " - " +listaDescripcion, Toast.LENGTH_SHORT).show();


                    } else {
                        for (int i = 0; i < listaDescripcion.size(); i++) {
                            if (listaDescripcion.get(i).equals(compoundButton.getText().toString())) {
                                listaDescripcion.remove(i);
                                listaId.remove(i);
                            }
                        }
                        if (mListener != null) {
                          //  Log.d("TAG", "Fragment ==> " + listaDescripcion);
                            mListener.sendDescLuces(listaDescripcion);
                            mListener.sendIdLuces(listaId);
                        }
                     }

                }
            });


            if (listaId != null && listaDescripcion!= null)
            {
                for(int i = 0; i<listaId.size(); i++)
                {
                    if (ckLuces.getId() == listaId.get(i)) {
                        ckLuces.setChecked(true);
                    }
                }
            }

            return item;


        }

    }


}
