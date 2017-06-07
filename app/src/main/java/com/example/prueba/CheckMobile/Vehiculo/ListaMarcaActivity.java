package com.example.prueba.CheckMobile.Vehiculo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.VehiculoMarca.AdapterMarca;
import com.example.prueba.CheckMobile.VehiculoMarca.Marca;
import com.example.prueba.CheckMobile.VehiculoMarca.MarcaResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prueba on 01-jun-17.
 */

public class ListaMarcaActivity extends AppCompatActivity {

    SearchView search;
    ListView searchResults;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_buscar_marca);

        search= (SearchView) findViewById(R.id.searchView1);
        search.setQueryHint("Escriba para buscar");
        searchResults = (ListView) findViewById(R.id.listview_search);

        obtenerDatosMarca();

        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Toast.makeText(getApplicationContext(), String.valueOf(b),Toast.LENGTH_SHORT).show();
            }
        });
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() > 3)
                {

                    searchResults.setVisibility(View.VISIBLE);
                    obtenerDatosMarca();
                }
                else
                {

                    searchResults.setVisibility(View.GONE);
                }



                return false;
            }

        });

    }

    private void obtenerDatosMarca() {

        Call<Marca> call = AdapterMarca.getApiService().getMarcas();
        call.enqueue(new MarcaCallback());
    }


    class MarcaCallback implements Callback<Marca> {
        @Override
        public void onResponse(Call<Marca> call, Response<Marca> response) {
            if (response.isSuccessful()) {
                MarcaResponse marcaResponse = response.body();
                AdapterListaMarcas AdapterMarca = new AdapterListaMarcas(getApplicationContext(), marcaResponse.getMarcas());
                searchResults.setAdapter(AdapterMarca);
                //   Log.v("Aki","==> " + clientes.toString());

            } else {
                Toast.makeText(getApplicationContext(), "Error en el formato de respuesta", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailure(Call<Marca> call, Throwable t) {
            Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            Log.v("Marcak ===>", t.getMessage());

        }
    }


     class AdapterListaMarcas extends ArrayAdapter<Marca> {

        private List<Marca> listaMarcas;

        public AdapterListaMarcas(Context contexto, List<Marca>  marcas){
            super(contexto,R.layout.lista_buscar_marca, marcas);
            listaMarcas = marcas;


        }

        public View getView (int posicion, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.row_marca, null);
            TextView txtdescripcion = (TextView) item.findViewById(R.id.txtRowMarca);
            txtdescripcion.setText(listaMarcas.get(posicion).getDescripcion());

            return item;
        }
    }

}
