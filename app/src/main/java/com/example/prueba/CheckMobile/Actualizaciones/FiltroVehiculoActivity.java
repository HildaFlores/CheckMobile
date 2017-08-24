package com.example.prueba.CheckMobile.Actualizaciones;

import android.app.DialogFragment;
import android.app.ProgressDialog;
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
import com.example.prueba.CheckMobile.Usuario.AdapterUsuario;
import com.example.prueba.CheckMobile.Usuario.Usuario;
import com.example.prueba.CheckMobile.Usuario.UsuarioResponse;
import com.example.prueba.CheckMobile.Vehiculo.AdapterVehiculo;
import com.example.prueba.CheckMobile.Vehiculo.Vehiculo;
import com.example.prueba.CheckMobile.Vehiculo.VehiculoResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.prueba.CheckMobile.Utils.Constantes.JSON_CLAVE_USUARIO_ADMIN;
import static com.example.prueba.CheckMobile.Utils.Constantes.JSON_USARIO_ADMIN;
import static com.example.prueba.CheckMobile.Utils.Constantes.RESPONSE_CODE_OK;
import static com.example.prueba.CheckMobile.Utils.Constantes.USER_SUPERVISOR;
import static com.itextpdf.text.pdf.PdfName.ca;

public class FiltroVehiculoActivity extends AppCompatActivity implements myDialogClaveAutorizacion.OnDialogclickListener {

    ArrayList<Vehiculo> vehiculos = new ArrayList<>();
    VehiculoAdapter madapter;
    ListView listViewVehiculo;
    String claveAutorizacion;
    Intent intent;

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
                Vehiculo itemVehiculo = (Vehiculo) listViewVehiculo.getAdapter().getItem(i);
                intent = new Intent(FiltroVehiculoActivity.this, ClienteActivity.class);
                intent.putExtra("IDVEHICULO", itemVehiculo.getId());
                intent.putExtra("ACTUALIZAR", true);
                myDialogClaveAutorizacion myDialogClaveAutorizacion = new myDialogClaveAutorizacion();
                myDialogClaveAutorizacion.show(getFragmentManager(), "Clave");
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
        // Set up progress before call
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle(null);
        dialog.setMax(100);
        dialog.setMessage("Cargando...");
        // show it
        dialog.show();
        call.enqueue(new Callback<Vehiculo>() {
            @Override
            public void onResponse(Call<Vehiculo> call, Response<Vehiculo> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    VehiculoResponse vehicle = response.body();
                    if (vehicle.getResponseCode().equals(RESPONSE_CODE_OK)) {
                        vehiculos = vehicle.getVehiculos();
                        madapter = new VehiculoAdapter(getApplicationContext(), vehiculos);
                        listViewVehiculo.setAdapter(madapter);
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error en el formato de respuesta de vehículo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Vehiculo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.v("Vehiculo", " ===>" + t.getMessage());
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        buscarUsuario(claveAutorizacion);

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }

    @Override
    public void onTextViewClave(String clave) {

        this.claveAutorizacion = clave;

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

    private void buscarUsuario(String valor2) {

        Call<Usuario> callUsuario = AdapterUsuario.getUsuario(JSON_USARIO_ADMIN, USER_SUPERVISOR, JSON_CLAVE_USUARIO_ADMIN, valor2).getValidacion();
        callUsuario.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    UsuarioResponse usuarioResponse = null;
                    if (response.body() != null) {

                        usuarioResponse = response.body();
                    }
                    if (usuarioResponse.getResponseCode().equals("200") && usuarioResponse.getRows() > 0) {

                        startActivity(intent);
                    } else
                        Toast.makeText(getApplicationContext(), "Contraseña incorrecta ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error al validar usuario o contraseña ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("CLAVE==>", t.getMessage());
            }
        });

    }
}
