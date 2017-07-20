package com.example.prueba.CheckMobile.MenuPrincipal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.prueba.CheckMobile.MainActivity;
import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.Usuario.LoginActivity;

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


        MainActivity main = new MainActivity();
        txtIp.setText(main.ipservidor);
        txtPuerto.setText(main.puerto);

        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfigurationActivity.this, LoginActivity.class);
                intent.putExtra("IP", txtIp.getText().toString());
                intent.putExtra("PUERTO", txtPuerto.getText().toString());
                intent.putExtra("SUPERVISOR", txtSupervisor.getText().toString());
                setResult(RESULT_OK, intent);
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
