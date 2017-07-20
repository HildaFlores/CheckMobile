package com.example.prueba.CheckMobile.Actualizaciones;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.example.prueba.CheckMobile.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prueba on 18-jul-17.
 */

public class myDialogLadosVehiculo extends DialogFragment {

    LayoutInflater inflater;
    View vista;
    private OnCompleteListener mListener;
    List<String> listaLados = new ArrayList<>();
    RadioGroup rgLados;

    public interface OnCompleteListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
        void onSendIdLado(int id);
        void onSendDescLado(String descripcion);
    }

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
        vista = inflater.inflate(R.layout.dialog_lados_vehiculo, null);
        rgLados = (RadioGroup) vista.findViewById(R.id.rgLadosVehiculo);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(vista);
        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onDialogNegativeClick(myDialogLadosVehiculo.this);
            }
        });

        builder.setCancelable(false);
        builder.setTitle("Seleccione el lado del vehiculo");
        builder.setIcon(R.drawable.icon_veh);

        listaLados.add("Izquierda");
        listaLados.add("Derecha");
        listaLados.add("Delantero");
        listaLados.add("Detrás");
        listaLados.add("Encima");

        for(int i = 0; i < listaLados.size(); i++)
        {
            RadioButton rbLado = new RadioButton(rgLados.getContext());
            rbLado.setText(listaLados.get(i));
            rbLado.setId(i+1);
            rbLado.setTextSize(15);
            rgLados.addView(rbLado);

            rbLado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        mListener.onDialogPositiveClick(myDialogLadosVehiculo.this);
                        mListener.onSendIdLado(compoundButton.getId());
                        mListener.onSendDescLado(compoundButton.getText().toString());
                    }
                }
            });
        }

        return builder.create();
    }
}
