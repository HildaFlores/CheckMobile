package com.example.prueba.CheckMobile.Actualizaciones;

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
import com.example.prueba.CheckMobile.Usuario.AdapterUsuario;
import com.example.prueba.CheckMobile.Usuario.LoginActivity;
import com.example.prueba.CheckMobile.Usuario.Usuario;
import com.example.prueba.CheckMobile.Usuario.UsuarioResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.prueba.CheckMobile.Utils.Constantes.JSON_CLAVE_USUARIO_ADMIN;
import static com.example.prueba.CheckMobile.Utils.Constantes.JSON_USARIO_ADMIN;
import static com.example.prueba.CheckMobile.Utils.Constantes.SUPERVISOR;
import static com.example.prueba.CheckMobile.Utils.Constantes.USER_SUPERVISOR;


/**
 * Created by Prueba on 04-ago-17.
 */

public class myDialogClaveAutorizacion extends DialogFragment {
    LayoutInflater inflater;
    View vista;
    private OnDialogclickListener mListener;
    EditText claveAutorizacion;

    public interface OnDialogclickListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
        void onTextViewClave(String clave);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnDialogclickListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnDialogclickListener");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inflater = getActivity().getLayoutInflater();
        vista = inflater.inflate(R.layout.dialog_clave, null);
        claveAutorizacion = (EditText) vista.findViewById(R.id.etxtClaveAutorizacion);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(vista);
        builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                mListener.onTextViewClave(claveAutorizacion.getText().toString());
                mListener.onDialogPositiveClick(myDialogClaveAutorizacion.this);

            }
        });

        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onDialogNegativeClick(myDialogClaveAutorizacion.this);
            }
        });

        builder.setCancelable(false);
        builder.setTitle("Digite clave de autorización");
        builder.setIcon(R.drawable.icon_password);


        return builder.create();
    }



}

