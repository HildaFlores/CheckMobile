package com.example.prueba.CheckMobile.Inspeccion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.prueba.CheckMobile.Cliente.ClienteActivity;
import com.example.prueba.CheckMobile.ListaParametros.AdapterListaParametros;
import com.example.prueba.CheckMobile.ListaParametros.ListaParametroResponse;
import com.example.prueba.CheckMobile.ListaParametros.ListaParametros;
import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.Vehiculo.VehiculoActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static android.R.attr.path;
import static android.R.id.list;
import static com.example.prueba.CheckMobile.R.id.btnSiguiente;
import static com.example.prueba.CheckMobile.R.id.listaLuces;

public class InspeccionActivity extends AppCompatActivity {

    LinearLayout Luces;
    CheckBox checkBox;
    private Bitmap imagenLuz;
    ImageButton imgLuces;
    ListView listaLuces;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspeccion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listaLuces = (ListView) findViewById(R.id.listaLuces);
        ObtenerDatosLuces();


    }

    private void ObtenerDatosLuces() {

        Call<ListaParametros> call = AdapterListaParametros.getApiService().getListaLuces();
        call.enqueue(new LucesCallback());

    }

    private class LucesCallback implements retrofit2.Callback<ListaParametros> {
        @Override
        public void onResponse(Call<ListaParametros> call, Response<ListaParametros> response) {
            if (response.isSuccessful()) {
                ListaParametroResponse listaResponse = response.body();
                AdapterLuces adapterLuces = new AdapterLuces(getApplicationContext(), listaResponse.getListaParametros());
                listaLuces.setAdapter(adapterLuces);

            } else {
                Toast.makeText(getApplicationContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<ListaParametros> call, Throwable t) {

            Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            Log.v("Luces-*** ===>", t.getMessage());
        }
    }


    private class AdapterLuces extends ArrayAdapter<ListaParametros> {

        private List<ListaParametros> listaLuces;

        public AdapterLuces(Context context, List<ListaParametros> luces) {
            super(context, R.layout.row_lista_luces, luces);
            listaLuces = luces;

        }

        public View getView(int posicion, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());

            View item = inflater.inflate(R.layout.row_lista_luces, null);
            CheckBox ckLuces = (CheckBox) item.findViewById(R.id.checkLuz);
            ImageView imImagen = (ImageView) item.findViewById(R.id.ivLuces);
            ckLuces.setText(listaLuces.get(posicion).getDescripcion());
           String url = listaLuces.get(posicion).getRutaImagen();
            String[] p =url.split("/");
         //   Toast.makeText(getContext(), p[5].toString(), Toast.LENGTH_SHORT).show();
            String imageLink="https://drive.google.com/uc?export=download&id="+p[5];

            Picasso.with(getContext())
                    .load(imageLink)
                    .error(R.mipmap.ic_launcher)
                    .fit()
                    .centerInside()
                    .into(imImagen);
            //192.168.0.109:4567/
            return item;
        }

    }

//    private Bitmap descargarImagen(String rutaImagen) {
//
//        URL imageUrl = null;
//        Bitmap imagen = null;
//        try {
//            imageUrl = new URL(rutaImagen);
//            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
//            conn.connect();
//            imagen = BitmapFactory.decodeStream(conn.getInputStream());
//        } catch (IOException e) {
//
//            Toast.makeText(getApplicationContext(), "Error cargando la imagen: "+ e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//
//        }
//
//
//        return imagen;
//    }

}
