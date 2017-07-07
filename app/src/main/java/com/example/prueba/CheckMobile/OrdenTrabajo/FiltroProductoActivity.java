package com.example.prueba.CheckMobile.OrdenTrabajo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.Inspeccion.InspeccionVehiculo;
import com.example.prueba.CheckMobile.MenuPrincipal.GreenAdapterInspeccion;
import com.example.prueba.CheckMobile.ProductoServicio.AdapterProductoServicio;
import com.example.prueba.CheckMobile.ProductoServicio.ProductoServicio;
import com.example.prueba.CheckMobile.ProductoServicio.ProductoServicioResponse;
import com.example.prueba.CheckMobile.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class FiltroProductoActivity extends AppCompatActivity {
    /*  ProductoServicioResponse servicioResponse = new ProductoServicioResponse();
        private GreenAdapterServicio mAdapter;
        private RecyclerView mServicioList;
        private int NUM_LIST_ITEMS = 0 ;
        MenuItem menuItem;*/
    //equestCode = 1
    android.widget.SearchView buscar;
    ListView listaServicios;
    ArrayList<ProductoServicio> productoServicios = new ArrayList<ProductoServicio>();
    AdapterServicios mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_producto);
        listaServicios = (ListView) findViewById(R.id.listaServicios);
        buscar = (android.widget.SearchView) findViewById(R.id.searchViewServicios);

        obtenerDatosServicios("4");
        filtroServicios();

        listaServicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProductoServicio producto = (ProductoServicio) listaServicios.getAdapter().getItem(i);
                String idProducto =producto.getId_producto();
                String descProducto = producto.getDesc_servicio();
                Intent intent = new Intent(FiltroProductoActivity.this, OrdenTrabajoActivity.class);
                intent.putExtra("PRODUCTO", idProducto);
                intent.putExtra("DESCRIPCION", descProducto);
                setResult(RESULT_OK, intent);
                finish();
               // startActivity(intent);
              //  Toast.makeText(getApplicationContext(), producto.getId_producto().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filtroServicios() {

        buscar.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                s = s.toUpperCase();
                final ArrayList<ProductoServicio> filterList = new ArrayList<ProductoServicio>();
                for (int i = 0; i < productoServicios.size(); i++) {
                   final String text = productoServicios.get(i).getDesc_servicio().toUpperCase();
                    if (text.contains(s)) {
                       filterList.add(productoServicios.get(i));
                   }
               }
               AdapterServicios mAdapter = new AdapterServicios(FiltroProductoActivity.this, filterList);
                listaServicios.setAdapter(mAdapter);


                return true;
            }
        });
    }


    private void obtenerDatosServicios(String s) {

        Call<ProductoServicio> call = AdapterProductoServicio.getApiService("id_clasificacion", s).getListaProductos();
        call.enqueue(new ServicioCallback());
    }

    /*
        private void callAdapter( ArrayList<ProductoServicio> productoServicios)
        {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            mServicioList.setLayoutManager(layoutManager);
            mServicioList.setHasFixedSize(true);
           mAdapter = new GreenAdapterServicio(NUM_LIST_ITEMS, productoServicios);

            mServicioList.setAdapter(mAdapter);

        }
    */
    private class ServicioCallback implements retrofit2.Callback<ProductoServicio> {
        @Override
        public void onResponse(Call<ProductoServicio> call, Response<ProductoServicio> response) {
            if (response.isSuccessful()) {
                   ProductoServicioResponse servicioResponse = response.body();
                productoServicios = servicioResponse.getProductoServicios();
                 mAdapter = new AdapterServicios(getApplicationContext(),productoServicios);
                listaServicios.setAdapter(mAdapter);
//                NUM_LIST_ITEMS = servicioResponse.getProductoServicios().size();
//                callAdapter(servicioResponse.getProductoServicios());
            } else {
                Toast.makeText(getApplicationContext(), "Error en el formato de respuesta de servicio", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<ProductoServicio> call, Throwable t) {
            Toast.makeText(getApplicationContext(), t.getLocalizedMessage() + t.getMessage(), Toast.LENGTH_LONG).show();
            Log.v("Serv *==>", t.getMessage());
        }
    }



    public class AdapterServicios extends ArrayAdapter<ProductoServicio>{
        private List<ProductoServicio> listaServicios;

        public AdapterServicios(Context context, List<ProductoServicio> servicios) {
            super(context, R.layout.row_producto_servicio, servicios);
            listaServicios = servicios;
        }
//
//        public void setFilter(List<ProductoServicio> servicios) {
//            listaServicios = new ArrayList<>();
//            listaServicios.addAll(servicios);
//            notifyDataSetChanged();
//
//        }

        public View getView(int posicion, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.row_producto_servicio, null);

            TextView textServicio = (TextView) item.findViewById(R.id.item_servicios);
            textServicio.setText(listaServicios.get(posicion).getDesc_servicio());
            item.setId(Integer.parseInt(listaServicios.get(posicion).getId_producto()));

            return item;
        }


    }

}
