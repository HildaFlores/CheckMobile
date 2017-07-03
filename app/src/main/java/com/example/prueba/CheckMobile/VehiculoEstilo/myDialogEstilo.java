package com.example.prueba.CheckMobile.VehiculoEstilo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.VehiculoModelo.AdapterModelo;
import com.example.prueba.CheckMobile.VehiculoModelo.Modelo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Prueba on 30-jun-17.
 */

public class myDialogEstilo extends DialogFragment {
    LayoutInflater inflater;
    View vista;


    private OnCompleteListenerEstilo mListener;

    public interface OnCompleteListenerEstilo {
        void onCompleteEstilo(String idModelo);
        void onCompleteDescripcionEstilo(String descripcion);
        void onDialogEstiloPositiveClick(DialogFragment dialog);
        void onDialogEstiloNegativeClick(DialogFragment dialog);
    }


    // make sure the Activity implemented it
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListenerEstilo) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inflater = getActivity().getLayoutInflater();
        vista = inflater.inflate(R.layout.dialog_agregar, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(vista)
                .setCancelable(false)
                .setPositiveButton("AGREGAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final EditText add = (EditText) vista.findViewById(R.id.etxtAdd);
                       // mListener.onCompleteEstilo(p[1]);
                        mListener.onCompleteDescripcionEstilo(add.getText().toString().toUpperCase());
                        mListener.onDialogEstiloPositiveClick(myDialogEstilo.this);
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       mListener.onDialogEstiloNegativeClick(myDialogEstilo.this);
                    }
                });
        builder.setTitle("Guardar Estilo");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_menu_save));
        return builder.create();
    }
}
