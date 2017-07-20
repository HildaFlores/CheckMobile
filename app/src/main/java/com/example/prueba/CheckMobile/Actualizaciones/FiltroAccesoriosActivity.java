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
import android.widget.ListView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.AccesoriosParametros.AdapterAccesorios;
import com.example.prueba.CheckMobile.AccesoriosParametros.ListaAccesorios;
import com.example.prueba.CheckMobile.AccesoriosParametros.ListaAccesoriosResponse;
import com.example.prueba.CheckMobile.Inspeccion.ConsultaInspeccionActivity;
import com.example.prueba.CheckMobile.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class FiltroAccesoriosActivity extends AppCompatActivity {
    ListView lvAccesorios;
    List<String> listaDescripcion = new ArrayList<>();
    List<String> listaId = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_accesorios);
        lvAccesorios = (ListView) findViewById(R.id.listaFiltroAccesorio);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAccesorio);
        setSupportActionBar(toolbar);

        OntenerDatosAccesorios("60");

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
            Intent intent = new Intent(FiltroAccesoriosActivity.this, ConsultaInspeccionActivity.class);
            intent.putStringArrayListExtra("IDACCESORIOS", (ArrayList<String>) listaId);
            intent.putStringArrayListExtra("DESCACCESORIOS", (ArrayList<String>) listaDescripcion);
            setResult(2, intent);
            finish();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    private void OntenerDatosAccesorios(String s) {
        Call<ListaAccesorios> call = AdapterAccesorios.getServiceAccesorios("id_lista", s).getListaAccesorios();
        call.enqueue(new Callback<ListaAccesorios>() {
            @Override
            public void onResponse(Call<ListaAccesorios> call, Response<ListaAccesorios> response) {
                if (response.isSuccessful()) {
                    ListaAccesoriosResponse accesoriosResponse = response.body();
                    // Toast.makeText(getContext(), accesoriosResponse.getListaAccesorios().toString(), Toast.LENGTH_SHORT).show();
                    AdapterListaAccesorios adapter = new AdapterListaAccesorios(getApplicationContext(), accesoriosResponse.getListaAccesorios());
                    lvAccesorios.setAdapter(adapter);

                } else {
                    Toast.makeText(getApplicationContext(), "Error en el formato de respuesta de accesorios", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListaAccesorios> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.v("accesorios-*** ===>", t.getMessage());
            }
        });
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

            ckAccesorios.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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


            return item;
        }
    }

}
