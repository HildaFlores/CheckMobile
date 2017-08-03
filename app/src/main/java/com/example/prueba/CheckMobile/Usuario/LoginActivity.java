package com.example.prueba.CheckMobile.Usuario;

import android.app.Activity;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.MainActivity;
import com.example.prueba.CheckMobile.Utils.ConfigurationActivity;
import com.example.prueba.CheckMobile.R;
import com.example.prueba.CheckMobile.Utils.DashBoardOrdenActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.prueba.CheckMobile.Utils.Constantes.JSON_CLAVE_USUARIO_ADMIN;
import static com.example.prueba.CheckMobile.Utils.Constantes.JSON_USARIO_ADMIN;

public class LoginActivity extends AppCompatActivity {


    // UI references.
    private EditText muserView;
    private EditText mPasswordView;
    private ProgressBar progress;
    private String idUsuario, nombreUsuario, ipservidor, puerto, supervisor;
    int requestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);
        // Set up the login form.
        progress = (ProgressBar) findViewById(R.id.progressBarCircle);
        muserView = (EditText) findViewById(R.id.user);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {

                    return true;
                }
                return false;
            }
        });

        progress.setProgress(0);
        Button mEmailSignInButton = (Button) findViewById(R.id.user_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = muserView.getText().toString().toLowerCase();
                String clave = mPasswordView.getText().toString().toLowerCase();
                buscarUsuario(usuario, clave);
            }
        });

    }

    private void buscarUsuario(final String valor1, String valor2) {
        Call<Usuario> callUsuario = AdapterUsuario.getUsuario(JSON_USARIO_ADMIN, valor1, JSON_CLAVE_USUARIO_ADMIN, valor2).getUsuario();
        callUsuario.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    UsuarioResponse usuarioResponse = null;
                    if (response.body() != null) {
                        usuarioResponse = response.body();
                    }
                    if (usuarioResponse.getResponseCode().equals("200") && usuarioResponse.getRows() > 0) {
                        idUsuario = usuarioResponse.getUsuarios().get(0).getId();
                        nombreUsuario = usuarioResponse.getUsuarios().get(0).getNombres() + " " + usuarioResponse.getUsuarios().get(0).getApellidos();
                        new AsyncTask_load().execute();
                        progress.setClickable(false);

                    } else {
                        Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error al validar usuario o contraseña ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("USUARIO==>", t.getMessage());
            }
        });

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
            Toast.makeText(getApplicationContext(), "Bienvenido " + nombreUsuario, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("IDUSUARIO", idUsuario);
            intent.putExtra("NOMBREUSUARIO", nombreUsuario);
            finish();
            startActivity(intent);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//
//            if (data != null) {
//
//                ipservidor = data.getStringExtra("IP");
//                puerto = data.getStringExtra("PUERTO");
//                supervisor = data.getStringExtra("SUPERVISOR");
//
//            }
//            }
//
//
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_salir_login) {
            finish();
            return true;
        } else if (id == R.id.action_settings) {

            Intent intent = new Intent(LoginActivity.this, ConfigurationActivity.class);
            this.finish();
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}





