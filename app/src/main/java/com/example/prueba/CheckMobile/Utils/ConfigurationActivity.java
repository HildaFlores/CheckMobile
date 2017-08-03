package com.example.prueba.CheckMobile.Utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.prueba.CheckMobile.MainActivity;
import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.Usuario.LoginActivity;

import static com.example.prueba.CheckMobile.Utils.Constantes.IPSERVIDOR;
import static com.example.prueba.CheckMobile.Utils.Constantes.PUERTO;
import static com.example.prueba.CheckMobile.Utils.Constantes.SUPERVISOR;

public class ConfigurationActivity extends AppCompatActivity {

    EditText txtIp;
    EditText txtPuerto;
    EditText txtSupervisor;
    Button botonAceptar;
    Button botonCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarConfig);
        setSupportActionBar(toolbar);

        txtIp = (EditText) findViewById(R.id.etxtDireccionServidor);
        txtPuerto = (EditText) findViewById(R.id.etxtPuerto);
        txtSupervisor = (EditText) findViewById(R.id.etxtSupervisor);
        botonAceptar = (Button) findViewById(R.id.botonAceptar);
        botonCancelar = (Button) findViewById(R.id.botonCancelar);

        txtIp.setText(IPSERVIDOR);
        txtPuerto.setText(PUERTO);
        txtSupervisor.setText(SUPERVISOR);

        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfigurationActivity.this, LoginActivity.class);
                IPSERVIDOR = txtIp.getText().toString();
                PUERTO = txtPuerto.getText().toString();
                SUPERVISOR = txtSupervisor.getText().toString();
                startActivity(intent);
                finish();
            }
        });

        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfigurationActivity.this, LoginActivity.class);
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }
}
