package com.example.prueba.CheckMobile.Actualizaciones;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.Inspeccion.ConsultaInspeccionActivity;
import com.example.prueba.CheckMobile.LucesParametros.AdapterLucesParametros;
import com.example.prueba.CheckMobile.LucesParametros.ListaLuces;
import com.example.prueba.CheckMobile.LucesParametros.ListaLucesResponse;
import com.example.prueba.CheckMobile.OrdenTrabajo.FiltroProductoActivity;
import com.example.prueba.CheckMobile.OrdenTrabajo.OrdenTrabajoActivity;
import com.example.prueba.CheckMobile.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.prueba.CheckMobile.R.id.listaLuces;
import static java.security.AccessController.getContext;

public class FiltroLucesActivity extends AppCompatActivity {

    ArrayList<ListaLuces> listLuces = new ArrayList<ListaLuces>();
    List<String> listaDescripcion = new ArrayList<>();
    List<String> listaId = new ArrayList<>();

    ListView listaLuces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_luces);
        listaLuces = (ListView) findViewById(R.id.listaFiltroLuces);
        ObtenerDatosLuces("58");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarLuces);
        setSupportActionBar(toolbar);
    }

    private void ObtenerDatosLuces(String valor) {
        Call<ListaLuces> call = AdapterLucesParametros.getApiService("id_lista", valor).getListaLuces();
        call.enqueue(new Callback<ListaLuces>() {
            @Override
            public void onResponse(Call<ListaLuces> call, Response<ListaLuces> response) {
                if (response.isSuccessful()) {
                    ListaLucesResponse listaResponse = response.body();
                    listLuces = listaResponse.getListaParametros();
                    AdapterLuces adapterLuces = new AdapterLuces(getApplicationContext(), listaResponse.getListaParametros());
                    listaLuces.setAdapter(adapterLuces);

                } else {
                    Toast.makeText(getApplicationContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListaLuces> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage() + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.v("Luces *==>", t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_filtro_luces, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_done)
        {
            Intent intent = new Intent(FiltroLucesActivity.this, ConsultaInspeccionActivity.class);
            intent.putStringArrayListExtra("IDLUCES", (ArrayList<String>) listaId);
            intent.putStringArrayListExtra("DESCLUCES", (ArrayList<String>) listaDescripcion);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
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


            holder.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {

                        listaDescripcion.add(compoundButton.getText().toString());
                        listaId.add(String.valueOf(compoundButton.getId()));

                    } else {
                        for (int i = 0; i < listaDescripcion.size(); i++) {
                            if (listaDescripcion.get(i).equals(compoundButton.getText().toString())) {
                                listaDescripcion.remove(i);
                                listaId.remove(i);
                            }
                        }

                    }
                }
            });
            item.setTag(holder);
            return item;


        }


    }

}
