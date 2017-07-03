package com.example.prueba.CheckMobile.VehiculoModelo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.Vehiculo.AdapterVehiculo;
import com.example.prueba.CheckMobile.VehiculoMarca.Marca;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.prueba.CheckMobile.VehiculoMarca.AdapterMarca.insertarMarca;

/**
 * Created by Prueba on 30-jun-17.
 */

public class myDialogModelo extends DialogFragment {
    LayoutInflater inflater;
    View vista;

    private OnCompleteListenerModelo mListener;

    public interface OnCompleteListenerModelo {
        void onCompleteModelo(String idModelo);
        void onCompleteDescripcionModelo(String descripcion);
        void onDialogModeloPositiveClick(DialogFragment dialog);
        void onDialogModeloNegativeClick(DialogFragment dialog);
    }


    // make sure the Activity implemented it
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListenerModelo) activity;
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
                        mListener.onCompleteDescripcionModelo(add.getText().toString().toUpperCase());
                        mListener.onDialogModeloPositiveClick(myDialogModelo.this);
                                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onDialogModeloNegativeClick(myDialogModelo.this);
                    }
                });

        builder.setTitle("Guardar Modelo");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_menu_save));

        return builder.create();
    }
}
