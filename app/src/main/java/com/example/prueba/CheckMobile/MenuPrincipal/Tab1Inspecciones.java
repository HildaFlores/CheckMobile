package com.example.prueba.CheckMobile.MenuPrincipal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.Vehiculo.VehiculoActivity;

/**
 * Created by Prueba on 26-may-17.
 */

public class Tab1Inspecciones extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1_inspecciones, container, false);


        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.flotanteInspeccion);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tab1Inspecciones.this.getContext(), VehiculoActivity.class);
                startActivity(intent);
            }
        });


        return rootView;
    }




}
