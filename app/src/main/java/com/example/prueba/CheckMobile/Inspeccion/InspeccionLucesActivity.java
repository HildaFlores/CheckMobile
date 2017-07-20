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

import org.w3c.dom.ls.LSException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.example.prueba.CheckMobile.R.id.textView;

public class InspeccionLucesActivity extends Fragment {

    ListView listaLuces;
    List<String> listaDescripcion = new ArrayList<>();
    List<Integer> listaId = new ArrayList<>();
    private sendData mListener;
    private String idInspeccion;
    ArrayList<ListaLuces> listLuces;

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
        listLuces = new ArrayList<ListaLuces>();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            listaDescripcion.addAll(bundle.getStringArrayList("DESC_LUZ"));
            listaId.addAll(bundle.getIntegerArrayList("ID_LUZ"));
        }

            ObtenerDatosLuces("58");

//        for(int i=0; i<listaId.size();i++)
//        {
//            View item = listaLuces.getAdapter().getView(listaLuces.getSelectedItemPosition(), null, null);
//            //CheckBox check = (CheckBox) item.findViewById(listaId.get(i));
//          Toast.makeText(getContext(), String.valueOf(item.getId()), Toast.LENGTH_SHORT).show();
//        }

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
                listLuces = listaResponse.getListaParametros();
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

    private static class SelectViewHolder {
        private CheckBox checkBox;
        private ImageView imageView;

        public SelectViewHolder() {
        }

        public SelectViewHolder(CheckBox checkBox, ImageView imageView) {
            this.checkBox = checkBox;
            this.imageView = imageView;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public void setCheckBox(CheckBox checkBox) {
            this.checkBox = checkBox;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }
    }


    private class AdapterLuces extends ArrayAdapter<ListaLuces> {


        private List<ListaLuces> listaLuces;
        SelectViewHolder holder;

        public AdapterLuces(Context context, List<ListaLuces> luces) {
            super(context, R.layout.row_lista_luces, luces);
            listaLuces = luces;

        }

        public View getView(final int posicion, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            final View item = inflater.inflate(R.layout.row_lista_luces, null);
            holder = new SelectViewHolder();
            holder.setCheckBox((CheckBox) item.findViewById(R.id.checkLuz));
            holder.setImageView((ImageView) item.findViewById(R.id.ivLuces));
            holder.getCheckBox().setText(listaLuces.get(posicion).getDescripcion());
            holder.getCheckBox().setId(Integer.parseInt(listaLuces.get(posicion).getValor()));
            item.setId(Integer.parseInt(listaLuces.get(posicion).getValor()));
            String url = listaLuces.get(posicion).getRutaImagen();
            String[] p = url.split("/");
            String imageLink = "https://drive.google.com/uc?export=download&id=" + p[5];
            Picasso.with(getContext())
                    .load(imageLink)
                    .into(holder.getImageView());


//            item.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(getContext(), String.valueOf(view.getId()), Toast.LENGTH_SHORT).show();
//                    ckLuces.setChecked(true);
//                }
//            });

            if (!listaId.isEmpty() && !listaDescripcion.isEmpty()) {
                for (int i = 0; i < listaId.size(); i++) {
                    if (holder.getCheckBox().getId() == listaId.get(i)) {
                        holder.getCheckBox().setChecked(true);
                    }
                }
                listaId.clear();
                listaDescripcion.clear();
            }
            holder.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
            item.setTag(holder);


            return item;


        }


    }


}
