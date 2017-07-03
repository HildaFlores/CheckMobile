package com.example.prueba.CheckMobile.VehiculoMarca;

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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.prueba.CheckMobile.VehiculoMarca.AdapterMarca.insertarMarca;

/**
 * Created by Prueba on 30-jun-17.
 */

public class myDialogMarca extends DialogFragment {
    LayoutInflater inflater;
    View vista;
    private OnCompleteListener mListener;

    public interface OnCompleteListener {
        void onComplete(String idMarca);
         void onCompleteDescripcion(String descripcion);
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
       }


    // make sure the Activity implemented it
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       
        inflater = getActivity().getLayoutInflater();
        vista = inflater.inflate(R.layout.dialog_agregar, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(vista);
        builder.setCancelable(false);
        builder.setPositiveButton("AGREGAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final EditText add = (EditText) vista.findViewById(R.id.etxtAdd);
                mListener.onCompleteDescripcion(add.getText().toString().toUpperCase());
                mListener.onDialogPositiveClick(myDialogMarca.this);

            }
        });
        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onDialogNegativeClick(myDialogMarca.this);
            }
        });
        builder.setTitle("Agregar Marca");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_menu_save));
        return builder.create();
    }



}
