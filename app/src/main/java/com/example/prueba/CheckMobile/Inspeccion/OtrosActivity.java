package com.example.prueba.CheckMobile.Inspeccion;

import android.content.Intent;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.OtrosParametros.AdapterOtrosParametros;
import com.example.prueba.CheckMobile.OtrosParametros.OtrosParametros;
import com.example.prueba.CheckMobile.OtrosParametros.OtrosParametrosResponse;
import com.example.prueba.CheckMobile.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static android.R.attr.id;

public class OtrosActivity extends Fragment {
    GridLayout layoutCantidades;
    GridLayout layoutLados;
    SeekBar sbCombustible;
    TextView txtSeekBar;
    SeekBar sbAceite;
    TextView txtAceite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.activity_otros, container, false);
        layoutCantidades = (GridLayout) rootView.findViewById(R.id.layoutCantidades);

        txtSeekBar = (TextView) rootView.findViewById(R.id.txtProgress);
        sbCombustible = (SeekBar) rootView.findViewById(R.id.seekBarCombustible);
        sbAceite = (SeekBar) rootView.findViewById(R.id.seekBarAceite);
        txtAceite = (TextView) rootView.findViewById(R.id.txtProgressAceite);
        layoutLados = (GridLayout) rootView.findViewById(R.id.layoutLadosVehiculo);


        txtSeekBar.setText("Nivel --> " + sbCombustible.getProgress() + "/" + sbCombustible.getMax());
        txtAceite.setText("Nivel -->" + sbAceite.getProgress());

        ObtenerDatosOtrosParametros("59,61,62,63,64");

        sbCombustible.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txtSeekBar.setText("Nivel --> " + sbCombustible.getProgress() + "/" + sbCombustible.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbAceite.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txtAceite.setText("Nivel --> " + sbAceite.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return rootView;

    }

    private void ObtenerDatosOtrosParametros(String s) {


        Call<OtrosParametros> call = AdapterOtrosParametros.getServiceOtros("id_lista", s).getOtrosParametros();
        call.enqueue(new OtrosCallback());


    }

    private class OtrosCallback implements retrofit2.Callback<OtrosParametros> {
        @Override
        public void onResponse(Call<OtrosParametros> call, Response<OtrosParametros> response) {
            if (response.isSuccessful()) {

                OtrosParametrosResponse otrosParametros = response.body();
                DividirListas(otrosParametros.getOtrosParametros());

            } else {
                Toast.makeText(getContext(), "Error en el formato de respuesta de parametros", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<OtrosParametros> call, Throwable t) {
            Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            Log.v("otros-*** ===>", t.getMessage());
        }
    }

    private void DividirListas(ArrayList<OtrosParametros> otrosParametros) {
        List<OtrosParametros> cantidades = new ArrayList<>();
        List<OtrosParametros> condicion = new ArrayList<>();
        List<OtrosParametros> lados = new ArrayList<>();
        List<OtrosParametros> condicionEntrada = new ArrayList<>();
        List<OtrosParametros> llaves = new ArrayList<>();

        for (OtrosParametros varOtros : otrosParametros) {
            switch (Integer.parseInt(varOtros.getId())) {
                case 59: {
                    llaves.add(varOtros);
                    break;
                }
                case 61: {
                    lados.add(varOtros);
                    break;
                }
                case 62: {
                    cantidades.add(varOtros);
                    break;
                }
                case 63: {
                    condicion.add(varOtros);
                    break;
                }
                case 64: {
                    condicionEntrada.add(varOtros);
                    break;
                }
            }
        }
        //  Toast.makeText(getContext(), lados.toString(), Toast.LENGTH_LONG).show();
        LlenarCantidades(cantidades);
        LlenarLadosVehiculo(lados);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        int id = v.getId();

        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_contextual_lados_vehiculo, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId())
        {
            case R.id.action_camara: {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
                return true;
                }
        }
        return super.onContextItemSelected(item);


    }

    private void LlenarLadosVehiculo(List<OtrosParametros> lados) {
        int contador = 0;
        layoutLados.setRowCount(lados.size());

        for (OtrosParametros varLados : lados) {
            contador++;
            ImageView imagen = new ImageView(layoutLados.getContext());
            imagen.setPadding(25,25,25,25);
            imagen.setId(contador);
            String url = varLados.getRutaImagen();
            String[] p = url.split("/");
            String imageLink = "https://drive.google.com/uc?export=download&id=" + p[5];
            Picasso.with(getContext())
                    .load(imageLink)
                    .error(R.mipmap.ic_launcher)
                    .into(imagen);

            layoutLados.addView(imagen);

            registerForContextMenu(imagen);


        }
    }


    private void LlenarCantidades(List<OtrosParametros> cantidades) {
        int contador = 0;
        layoutCantidades.setRowCount(cantidades.size());
        for (OtrosParametros varCantidad : cantidades) {
            contador++;
            EditText etxtCantidades = new EditText(layoutCantidades.getContext());
            etxtCantidades.setHint(varCantidad.getValor());
            etxtCantidades.setId(contador);
            etxtCantidades.setInputType(InputType.TYPE_CLASS_NUMBER);
            etxtCantidades.setPadding(15, 15, 15, 15);
            layoutCantidades.addView(etxtCantidades);
        }
    }

}
