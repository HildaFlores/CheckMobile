package com.example.prueba.CheckMobile.Utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.Inspeccion.AdapterInspeccion;
import com.example.prueba.CheckMobile.Inspeccion.InspeccionVehiculo;
import com.example.prueba.CheckMobile.Inspeccion.InspeccionVehiculoResponse;
import com.example.prueba.CheckMobile.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.prueba.CheckMobile.Utils.Constantes.JSON_KEY_VEHICULO;

public class tab1HistoricoVehiculo extends Fragment {
    String referenciaVehiculo;
    ListView listaInspecciones;
    InspeccionAdapter madapter;
    TextView mensaje;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_tab1_historico_vehiculo, container, false);
        listaInspecciones = (ListView) rootView.findViewById(R.id.listaVehiculoInspeccion);
        mensaje = (TextView) rootView.findViewById(R.id.txtMensajeInspeccion);
        Bundle arguments = this.getArguments();
        if (arguments != null) {
            referenciaVehiculo = arguments.getString("REFERENCIA");
            obtenerDatosVehiculo();
        }

        return rootView;
    }

    private void obtenerDatosVehiculo() {
        Call<InspeccionVehiculo> call = AdapterInspeccion.getApiService(JSON_KEY_VEHICULO, referenciaVehiculo).getInspeccionesPorReferencia();
        call.enqueue(new Callback<InspeccionVehiculo>() {
            @Override
            public void onResponse(Call<InspeccionVehiculo> call, Response<InspeccionVehiculo> response) {
                if (response.isSuccessful()) {
                    InspeccionVehiculoResponse inspeccion = response.body();
                    if (inspeccion.getResponseCode().equals("200")) {
                        madapter = new InspeccionAdapter(getContext(), inspeccion.getInspecciones());
                        listaInspecciones.setAdapter(madapter);
                        if(inspeccion.getInspecciones().isEmpty())
                        {
                            mensaje.setVisibility(View.VISIBLE);
                            listaInspecciones.setVisibility(View.GONE);
                        }
                        else
                        {
                            mensaje.setVisibility(View.GONE);
                            listaInspecciones.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Error en el formato de respuesta de inspeccion", Toast.LENGTH_SHORT).show();
                    Log.v("INSPECCION ==>", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<InspeccionVehiculo> call, Throwable t) {
                Toast.makeText(getContext(),  "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.v("Aqui ===>", "Error: " + t.getMessage());
            }
        });
    }

    public class InspeccionAdapter extends ArrayAdapter<InspeccionVehiculo> {
        private List<InspeccionVehiculo> listaInspecciones;

        public InspeccionAdapter(Context context, List<InspeccionVehiculo> ins) {
            super(context, R.layout.row_historico_vehiculo, ins);
            listaInspecciones = ins;
        }

        public View getView(int posicion, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View item = inflater.inflate(R.layout.row_historico_vehiculo, null);

            TextView textId = (TextView) item.findViewById(R.id.txtInspeccion);
            TextView textFecha = (TextView) item.findViewById(R.id.txtFecha);
            TextView textCliente = (TextView) item.findViewById(R.id.txtCliente);

            textId.setText("Inspección >> IV-" + listaInspecciones.get(posicion).getId());
            textFecha.setText(listaInspecciones.get(posicion).getFechaInspeccion());
            textCliente.setText("Cliente >> " + listaInspecciones.get(posicion).getNombre_cliente());
            item.setId(Integer.parseInt(listaInspecciones.get(posicion).getId()));
            return item;
        }


    }
}
