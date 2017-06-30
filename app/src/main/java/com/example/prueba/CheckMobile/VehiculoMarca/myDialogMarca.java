package com.example.prueba.CheckMobile.VehiculoMarca;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;

import com.example.prueba.CheckMobile.R;

/**
 * Created by Prueba on 30-jun-17.
 */

public class myDialogMarca extends DialogFragment {
    LayoutInflater inflater;
    View vista;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inflater = getActivity().getLayoutInflater();
        vista = inflater.inflate(R.layout.dialog_agregar_marca, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(vista)
                .setCancelable(false)
                .setPositiveButton("AGREGAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.setTitle("Guardar");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_menu_save));
        return builder.create();
    }


}
