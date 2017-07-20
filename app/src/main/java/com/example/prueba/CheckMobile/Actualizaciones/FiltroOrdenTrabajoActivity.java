package com.example.prueba.CheckMobile.Actualizaciones;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.prueba.CheckMobile.OrdenTrabajo.AdapterOrdenTrabajo;
import com.example.prueba.CheckMobile.OrdenTrabajo.OrdenTrabajoActivity;
import com.example.prueba.CheckMobile.OrdenTrabajo.OrdenTrabajoEnc;
import com.example.prueba.CheckMobile.OrdenTrabajo.OrdenTrabajoEncResponse;
import com.example.prueba.CheckMobile.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.prueba.CheckMobile.R.id.listaLuces;
import static java.security.AccessController.getContext;

public class FiltroOrdenTrabajoActivity extends AppCompatActivity {

    ListView listaOrden;
    ArrayList<OrdenTrabajoEnc> ordenTrabajo = new ArrayList<OrdenTrabajoEnc>();
    AdapterOrden madapter;
    boolean permitePiezas = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_orden_trabajo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarOrden);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        listaOrden = (ListView) findViewById(R.id.listaOrdenTrabajo);
        obtenerDatosOrdenTrabajo();
        seleccionOrdenTrabajo();
    }

    private void seleccionOrdenTrabajo() {

        listaOrden.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OrdenTrabajoEnc ordenItem = (OrdenTrabajoEnc) listaOrden.getAdapter().getItem(i);
                Intent intent = new Intent(FiltroOrdenTrabajoActivity.this, OrdenTrabajoActivity.class);
                intent.putExtra("ORDEN", ordenItem.getId());
                intent.putExtra("CLIENTE", ordenItem.getNombreCliente() + " " + ordenItem.getApellidosCte());
                intent.putExtra("FECHA", ordenItem.getFechaPedido());
                intent.putExtra("OBSERVACION", ordenItem.getNotas());
                if (ordenItem.getPermite_pieza_reemplazo().toString().equals("1")) {
                    permitePiezas = true;
                }
                else
                {
                    permitePiezas = false;
                }

                intent.putExtra("PIEZAS", permitePiezas);
                intent.putExtra("MECANICO", ordenItem.getNombre_mecanico());
                intent.putExtra("IDMECANICO", ordenItem.getIdMecanico());
                intent.putExtra("IDCONDICION", ordenItem.getIdCondicion());
                intent.putExtra("CONDICION", ordenItem.getCondicion());
                intent.putExtra("IDINSPECCION", ordenItem.getId_inspeccion());
                intent.putExtra("ACTUALIZAR", true);
                startActivity(intent);
            }
        });
    }

    private void obtenerDatosOrdenTrabajo() {
        Call<OrdenTrabajoEnc> call = AdapterOrdenTrabajo.getApiService("id_tipo_trans", "OTT").getOrdenTrabajo();
        call.enqueue(new Callback<OrdenTrabajoEnc>() {
            @Override
            public void onResponse(Call<OrdenTrabajoEnc> call, Response<OrdenTrabajoEnc> response) {
                OrdenTrabajoEncResponse ordenes = response.body();
                if (ordenes.getResponseCode().equals("200")) {
                    ordenTrabajo = ordenes.getOrdenes();
                    madapter = new AdapterOrden(getApplicationContext(), ordenTrabajo);
                    listaOrden.setAdapter(madapter);
                } else {
                    Toast.makeText(getApplicationContext(), "Error en el formato de respuesta de Orden", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrdenTrabajoEnc> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.v("Aqui===>", "Error" + t.getMessage());
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
                    final ArrayList<OrdenTrabajoEnc> filterList = new ArrayList<OrdenTrabajoEnc>();
                    for (int i = 0; i < ordenTrabajo.size(); i++) {
                        final String text = ordenTrabajo.get(i).getNombreCliente().toUpperCase() + " " +  ordenTrabajo.get(i).getApellidosCte();
                        if (text.contains(newText)) {
                            filterList.add(ordenTrabajo.get(i));
                        }
                    }
                    AdapterOrden mAdapter = new AdapterOrden(getApplicationContext(), filterList);
                    listaOrden.setAdapter(mAdapter);

                    return true;
                }
            });

        }

        return super.onOptionsItemSelected(item);
    }


    private class AdapterOrden extends ArrayAdapter<OrdenTrabajoEnc> {


        private List<OrdenTrabajoEnc> listOrden;


        public AdapterOrden(Context context, List<OrdenTrabajoEnc> ordenes) {
            super(context, R.layout.row_inspeccion_update, ordenes);
            listOrden = ordenes;
        }

        public View getView(final int posicion, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            final View item = inflater.inflate(R.layout.row_inspeccion_update, null);
            TextView textId = (TextView) item.findViewById(R.id.txtRowUpdate1);
            TextView textVehiculo = (TextView) item.findViewById(R.id.txtRowUpdate2);
            TextView textCliente = (TextView) item.findViewById(R.id.txtRowUpdate3);
            textId.setText("(OTT-" + listOrden.get(posicion).getId() + ")");
            textCliente.setText("CLIENTE >> " + listOrden.get(posicion).getNombreCliente() + " " + listOrden.get(posicion).getApellidosCte());
            item.setId(Integer.parseInt(listOrden.get(posicion).getId()));
            textVehiculo.setVisibility(View.GONE);
            return item;


        }


    }

}
