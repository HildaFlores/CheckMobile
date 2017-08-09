package com.example.prueba.CheckMobile.Utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.Usuario.AdapterUsuario;
import com.example.prueba.CheckMobile.Usuario.LoginActivity;
import com.example.prueba.CheckMobile.Usuario.Usuario;
import com.example.prueba.CheckMobile.Usuario.UsuarioResponse;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.prueba.CheckMobile.Utils.Constantes.ID_SUPERVISOR;
import static com.example.prueba.CheckMobile.Utils.Constantes.IPSERVIDOR;
import static com.example.prueba.CheckMobile.Utils.Constantes.PUERTO;
import static com.example.prueba.CheckMobile.Utils.Constantes.RESPONSE_CODE_OK;
import static com.example.prueba.CheckMobile.Utils.Constantes.SUPERVISOR;
import static com.example.prueba.CheckMobile.Utils.Constantes.USER_SUPERVISOR;

public class ConfigurationActivity extends AppCompatActivity {

    EditText txtIp;
    EditText txtPuerto;
    Spinner spinnerSupervisor;
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
        spinnerSupervisor = (Spinner) findViewById(R.id.spSupervisor);
        botonAceptar = (Button) findViewById(R.id.botonAceptar);
        botonCancelar = (Button) findViewById(R.id.botonCancelar);

        txtIp.setText(IPSERVIDOR);
        txtPuerto.setText(PUERTO);
        obtenerDatosSupervisor();


        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfigurationActivity.this, LoginActivity.class);
                IPSERVIDOR = txtIp.getText().toString();
                PUERTO = txtPuerto.getText().toString();
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

    private void obtenerDatosSupervisor() {
        Call<Usuario> callUsario = AdapterUsuario.getSupervisor().getPersonal();
        callUsario.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful())
                {
                    UsuarioResponse usuarioResponse = response.body();
                    if (usuarioResponse.getResponseCode().equals(RESPONSE_CODE_OK))
                    {
                        poblarSpinnerUsuarios(usuarioResponse.getUsuarios());
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Error al traer datos de usuarios", Toast.LENGTH_SHORT).show();
                    }
                }else
                {
                    Toast.makeText(getApplicationContext(), "Error en respuesta de usuarios", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: "  + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR==>", t.getMessage() + "\n" + t.getLocalizedMessage());
            }
        });



    }

    private void poblarSpinnerUsuarios(final ArrayList<Usuario> usuarios) {

        List<String> listaUsuarios = new ArrayList<>();

        for(Usuario user : usuarios)
        {
            listaUsuarios.add(user.getNombres().toUpperCase()  + " " +  user.getApellidos().toUpperCase());

        }
        final ArrayAdapter<String> madapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_style, listaUsuarios);
        spinnerSupervisor.setAdapter(madapter);

        for(int j=0; j<usuarios.size(); j++)
        {
            if(usuarios.get(j).getId().equals(ID_SUPERVISOR)){
               spinnerSupervisor.setSelection(j);
            }
        }


        spinnerSupervisor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String user = madapter.getItem(i).toUpperCase();
                SUPERVISOR = user;
                for(int j=0; j<usuarios.size(); j++)
                {
                    if((usuarios.get(j).getNombres().toUpperCase() + " " + usuarios.get(j).getApellidos().toUpperCase()).equals(user)){
                        ID_SUPERVISOR = usuarios.get(j).getId();
                        USER_SUPERVISOR = usuarios.get(j).getUsuario();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
