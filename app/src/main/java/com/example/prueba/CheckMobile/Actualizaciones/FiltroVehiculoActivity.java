package com.example.prueba.CheckMobile.Actualizaciones;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.Cliente.ClienteActivity;
import com.example.prueba.CheckMobile.Inspeccion.InspeccionVehiculo;
import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.Vehiculo.AdapterVehiculo;
import com.example.prueba.CheckMobile.Vehiculo.Vehiculo;
import com.example.prueba.CheckMobile.Vehiculo.VehiculoResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.prueba.CheckMobile.Utils.Constantes.RESPONSE_CODE_OK;

public class FiltroVehiculoActivity extends AppCompatActivity {

    ArrayList<Vehiculo> vehiculos = new ArrayList<>();
    VehiculoAdapter madapter;
    ListView listViewVehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_vehiculo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCVehiculo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        listViewVehiculo = (ListView) findViewById(R.id.listaVehiculos);
        obtenerDatosVehiculos();

        listViewVehiculo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // InspeccionVehiculo itemInspeccion = (InspeccionVehiculo) listViewInspeccion.getAdapter().getItem(i);
                Vehiculo itemVehiculo = (Vehiculo) listViewVehiculo.getAdapter().getItem(i);
                Intent intent = new Intent(FiltroVehiculoActivity.this, ClienteActivity.class);
                intent.putExtra("IDVEHICULO",itemVehiculo.getId());
                intent.putExtra("ACTUALIZAR", true);
                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_buscar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_buscar) {
            SearchView search = (SearchView) MenuItemCompat.getActionView(item);

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    newText = newText.toUpperCase();
                    final ArrayList<Vehiculo> filterList = new ArrayList<Vehiculo>();
                    for (int i = 0; i < vehiculos.size(); i++) {
                        final String text = vehiculos.get(i).getDesc_marca().toUpperCase() + " "
                                + vehiculos.get(i).getDesc_modelo().toUpperCase() + " "
                                + vehiculos.get(i).getDesc_estilo() + " "
                                + vehiculos.get(i).getReferencia();
                        if (text.contains(newText)) {
                            filterList.add(vehiculos.get(i));
                        }
                    }
                    VehiculoAdapter mAdapter = new VehiculoAdapter(getApplicationContext(), filterList);
                    listViewVehiculo.setAdapter(mAdapter);

                    return true;
                }
            });

        }


        return super.onOptionsItemSelected(item);
    }


    private void obtenerDatosVehiculos() {

        Call<Vehiculo> call = AdapterVehiculo.getApiService().getVehiculos();
        call.enqueue(new VehiculoCallback());
    }


    class VehiculoCallback implements Callback<Vehiculo> {
        @Override
        public void onResponse(Call<Vehiculo> call, Response<Vehiculo> response) {
            if (response.isSuccessful()) {
                VehiculoResponse vehicle = response.body();
                if (vehicle.getResponseCode().equals(RESPONSE_CODE_OK)) {
                    vehiculos = vehicle.getVehiculos();
                    madapter = new VehiculoAdapter(getApplicationContext(), vehiculos);
                    listViewVehiculo.setAdapter(madapter);

                }
            } else {
                Toast.makeText(getApplicationContext(), "Error en el formato de respuesta de vehículo", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<Vehiculo> call, Throwable t) {
            Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            Log.v("Aqui ===>", t.getMessage());
        }
    }


    public class VehiculoAdapter extends ArrayAdapter<Vehiculo> {
        private List<Vehiculo> listaVehiculos;

        public VehiculoAdapter(Context context, List<Vehiculo> veh) {
            super(context, R.layout.row_cliente, veh);
            listaVehiculos = veh;
        }


        public View getView(int posicion, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.row_cliente, null);

            TextView textId = (TextView) item.findViewById(R.id.tv_item_number);
            textId.setText(listaVehiculos.get(posicion).getDesc_marca() + " " + vehiculos.get(posicion).getDesc_modelo()
                    + " " + listaVehiculos.get(posicion).getDesc_estilo() + " " + listaVehiculos.get(posicion).getAno() + " \n "
                    + "REF. " + listaVehiculos.get(posicion).getReferencia());
            item.setId(Integer.parseInt(listaVehiculos.get(posicion).getId()));


            return item;
        }


    }

}
