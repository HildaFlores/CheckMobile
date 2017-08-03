package com.example.prueba.CheckMobile.Actualizaciones;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
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

import com.example.prueba.CheckMobile.Inspeccion.AdapterInspeccion;
import com.example.prueba.CheckMobile.Inspeccion.ConsultaInspeccionActivity;
import com.example.prueba.CheckMobile.Inspeccion.InspeccionVehiculo;
import com.example.prueba.CheckMobile.Inspeccion.InspeccionVehiculoResponse;
import com.example.prueba.CheckMobile.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FiltroInspeccionActivity extends AppCompatActivity {

    ListView listViewInspeccion;
    private ArrayList<InspeccionVehiculo> inspecciones = new ArrayList<InspeccionVehiculo>();
    InspeccionAdapter madapter;
    boolean consultar = false;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_inspeccion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarInspeccion);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listViewInspeccion = (ListView) findViewById(R.id.listaInspeccion);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            consultar = extra.getBoolean("CONSULTAR");
            inspecciones = extra.getParcelableArrayList("INSPECCIONES");
        }

        if (consultar) {
            madapter = new InspeccionAdapter(getApplicationContext(), inspecciones);
            listViewInspeccion.setAdapter(madapter);

        } else {

            ObtenerDatosInspeccion();
        }
        SeleccionInspeccion();

    }

    private void SeleccionInspeccion() {

        this.listViewInspeccion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InspeccionVehiculo itemInspeccion = (InspeccionVehiculo) listViewInspeccion.getAdapter().getItem(i);
                ObtenerDetalleInspeccion(itemInspeccion);

            }
        });
    }

    private void ObtenerDetalleInspeccion(final InspeccionVehiculo item) {

        Intent intent = new Intent(FiltroInspeccionActivity.this, ConsultaInspeccionActivity.class);
        intent.putExtra("IDINSPECCION", Integer.parseInt(item.getId()));
        intent.putExtra("VEHICULO", item.getNombre_vehiculo());
        intent.putExtra("IDVEHICULO", item.getIdVehiculo());
        intent.putExtra("REFERENCIA", item.getReferencia());
        intent.putExtra("CHASIS", item.getChasis());
        intent.putExtra("IDCLIENTE", item.getIdCliente());
        intent.putExtra("CLIENTE", item.getNombre_cliente());
        intent.putExtra("KILOMETRAJE", item.getKilometraje());
        intent.putExtra("MOTOR", item.getMotor());
        intent.putExtra("FECHA", item.getFechaInspeccion());
        intent.putExtra("SERIEGOMAS", item.getSerieGomas());
        intent.putExtra("OBSERVACION", item.getObservaciones());
        intent.putExtra("PLACA", item.getPlaca());
        intent.putExtra("COLOR", item.getColor());
        intent.putExtra("TELEFONO", item.getTelefono());
        intent.putExtra("CELULAR", item.getCelular());
        intent.putExtra("DOCIDENTIDAD", item.getDocIdentidad());

        if (consultar) {
            intent.putExtra("CONSULTAR", true);
        } else {
            intent.putExtra("ACTUALIZAR", true);
        }
        startActivity(intent);
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
                    final ArrayList<InspeccionVehiculo> filterList = new ArrayList<InspeccionVehiculo>();
                    for (int i = 0; i < inspecciones.size(); i++) {
                        final String text = inspecciones.get(i).getNombre_vehiculo().toUpperCase();
                        if (text.contains(newText)) {
                            filterList.add(inspecciones.get(i));
                        }
                    }
                    InspeccionAdapter mAdapter = new InspeccionAdapter(getApplicationContext(), filterList);
                    listViewInspeccion.setAdapter(mAdapter);

                    return true;
                }
            });

        }


        return super.onOptionsItemSelected(item);
    }

    private void ObtenerDatosInspeccion() {

        Call<InspeccionVehiculo> call = AdapterInspeccion.getApiService().getInspecciones();
        call.enqueue(new Callback<InspeccionVehiculo>() {
            @Override
            public void onResponse(Call<InspeccionVehiculo> call, Response<InspeccionVehiculo> response) {
                if (response.isSuccessful()) {
                    InspeccionVehiculoResponse inspeccion = response.body();
                    if (inspeccion.getResponseCode().equals("200")) {
                        inspecciones = inspeccion.getInspecciones();
                        madapter = new InspeccionAdapter(getApplicationContext(), inspecciones);
                        listViewInspeccion.setAdapter(madapter);

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error en el formato de respuesta de inspeccion", Toast.LENGTH_SHORT).show();
                    Log.v("INSPECCION ==>", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<InspeccionVehiculo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.v("Aqui ===>", t.getMessage());
            }
        });

    }


    public class InspeccionAdapter extends ArrayAdapter<InspeccionVehiculo> {
        private List<InspeccionVehiculo> listaInspecciones;

        public InspeccionAdapter(Context context, List<InspeccionVehiculo> ins) {
            super(context, R.layout.row_inspeccion_update, ins);
            listaInspecciones = ins;
        }


        public View getView(int posicion, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.row_inspeccion_update, null);

            TextView textId = (TextView) item.findViewById(R.id.txtRowUpdate1);
            TextView textVehiculo = (TextView) item.findViewById(R.id.txtRowUpdate2);
            TextView textCliente = (TextView) item.findViewById(R.id.txtRowUpdate3);
            textId.setText("(IV-" + listaInspecciones.get(posicion).getId() + ")");
            textVehiculo.setText("Vehiculo >> " + listaInspecciones.get(posicion).getNombre_vehiculo());
            textCliente.setText("Cliente >> " + listaInspecciones.get(posicion).getNombre_cliente());
            item.setId(Integer.parseInt(listaInspecciones.get(posicion).getId()));

            return item;
        }


    }

}
