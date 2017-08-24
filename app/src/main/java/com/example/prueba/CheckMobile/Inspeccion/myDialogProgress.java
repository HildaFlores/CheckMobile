package com.example.prueba.CheckMobile.Inspeccion;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.prueba.CheckMobile.MainActivity;
import com.example.prueba.CheckMobile.R;

/**
 * Created by Prueba on 04-jul-17.
 */

public class myDialogProgress extends DialogFragment {
    LayoutInflater inflater;
    View vista;
    ProgressBar progress;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        inflater = getActivity().getLayoutInflater();
        vista = inflater.inflate(R.layout.dialog_progress, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(vista);
        progress = (ProgressBar) vista.findViewById(R.id.progressGuardar);
        new AsyncTask_load().execute();
        progress.setClickable(false);
        return builder.create();
    }

    public class AsyncTask_load extends AsyncTask<Void, Integer, Void> {

        int progreso;


        @Override
        protected void onPreExecute() {
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            while (progreso < 100) {
                progreso++;
                publishProgress(progreso);
                SystemClock.sleep(10);
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {

            progress.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            progress.setVisibility(View.INVISIBLE);


        }


    }


}
