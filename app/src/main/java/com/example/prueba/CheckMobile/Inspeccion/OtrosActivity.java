package com.example.prueba.CheckMobile.Inspeccion;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.prueba.CheckMobile.OtrosParametros.AdapterOtrosParametros;
import com.example.prueba.CheckMobile.OtrosParametros.OtrosParametros;
import com.example.prueba.CheckMobile.OtrosParametros.OtrosParametrosResponse;
import com.example.prueba.CheckMobile.R;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import retrofit2.Call;
import retrofit2.Response;
import static android.app.Activity.RESULT_OK;

public class OtrosActivity extends Fragment {
    GridLayout layoutCantidades;
    GridLayout layoutLados;
    SeekBar sbCombustible;
    TextView txtSeekBar;
    SeekBar sbAceite;
    TextView txtAceite;
    RadioGroup rgLLaves;
    Button btnUpLoad;
    private Timer timer = new Timer();
    private final long DELAY = 0;
    int idPictureLados;
    String tipoLlave, descLlave, nivelCombustible, nivelAceite;
    String cantAlfombra, cantLlaves, cantGato, cantAlicate, cantLlaveRueda, noBateria;
    sendOtros mListener;
    private static final int TAKE_PICTURE = 1;
    final int CAMERA_REQUEST = 13323;
    private Uri imageUri;
    CameraPhoto cameraPhoto;
    List<String> photoToUpload = new ArrayList<>();
    List<Integer> lado = new ArrayList<>();
    DrawingView mDrawingView;
    boolean actualizar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof sendOtros) {
            mListener = (sendOtros) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface sendOtros {
        void sendTipoLlave(String llave);

        void sendDescLlave(String descLlave);

        void sendNivelCombustible(String nivelComb);

        void sendNivelAceite(String nivelAceite);

        void sendCantAlfombra(String cantAlfombra);

        void sendCantLlave(String cantLlave);

        void sendCantGato(String cantGato);

        void sendCantAlicate(String cantAlicate);

        void sendLlaveRueda(String cantLlaveRueda);

        void sendNoBateria(String noBateria);

        void sendImageRuta(List<String> imageRuta);

        void sendLadoId(List<Integer> lado);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = inflater.inflate(R.layout.activity_otros, container, false);
        layoutCantidades = (GridLayout) rootView.findViewById(R.id.layoutCantidades);

        txtSeekBar = (TextView) rootView.findViewById(R.id.txtProgress);
        sbCombustible = (SeekBar) rootView.findViewById(R.id.seekBarCombustible);
        sbAceite = (SeekBar) rootView.findViewById(R.id.seekBarAceite);
        txtAceite = (TextView) rootView.findViewById(R.id.txtProgressAceite);
        layoutLados = (GridLayout) rootView.findViewById(R.id.layoutLadosVehiculo);
        rgLLaves = (RadioGroup) rootView.findViewById(R.id.rgLLaves);
        btnUpLoad = (Button) rootView.findViewById(R.id.btnUpLoad);

        txtSeekBar.setText("Nivel --> " + sbCombustible.getProgress() + "/" + sbCombustible.getMax());
        txtAceite.setText("Nivel -->" + sbAceite.getProgress());

        ObtenerDatosOtrosParametros("59,61,62,63,64");

        ObtenerTipoLlave();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            tipoLlave = bundle.getString("LLAVE");
           // Toast.makeText(getContext(), tipoLlave, Toast.LENGTH_SHORT).show();
            if (tipoLlave.contains("15")) {
                RadioButton rb = (RadioButton) rgLLaves.findViewById(R.id.rbLlaveSmart);
                rb.setChecked(true);
            } else {
                RadioButton rb = (RadioButton) rgLLaves.findViewById(R.id.rbLlaveNormal);
                rb.setChecked(true);
            }
        }


        cameraPhoto = new CameraPhoto(getContext());


        sbCombustible.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txtSeekBar.setText("Nivel --> " + sbCombustible.getProgress() + "/" + sbCombustible.getMax());
                nivelCombustible = String.valueOf(sbCombustible.getProgress());
                if (mListener != null) {
                    Log.d("TAG", "Fragment ==> " + nivelCombustible);
                    mListener.sendNivelCombustible(nivelCombustible);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        sbAceite.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txtAceite.setText("Nivel --> " + sbAceite.getProgress());
                nivelAceite = String.valueOf(sbAceite.getProgress());
                if (mListener != null) {
                    Log.d("TAG", "Fragment ==> " + nivelAceite);
                    mListener.sendNivelAceite(nivelAceite);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return rootView;

    }

    /*
        private void subirImagen() {
            btnUpLoad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                     for(int i= 0; i<photoToUpload.size(); i++)
                     {
                         Bitmap bitmap = ImageLoader.init().from(photoToUpload.get(i)).requestSize(1024, 1024).getBitmap();
                         String encodedImage = ImageBase64.encode(bitmap);
                         Log.d("TAG==>", encodedImage);
                       //  Toast.makeText(getContext(), encodedImage, Toast.LENGTH_SHORT).show();
                         HashMap<String , String> postData = new HashMap<String, String>();
                         postData.put("image", encodedImage);
                         PostResponseAsyncTask task = new PostResponseAsyncTask(getActivity(), postData, new AsyncResponse() {
                             @Override
                             public void processFinish(String s) {
                                        if(s.contains("uploaded_sucessful"))
                                        {
                                            Toast.makeText(getContext(), s.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            Toast.makeText(getContext(), "Error al cargar imagenes", Toast.LENGTH_SHORT).show();
                                        }
                             }
                         });
                         task.execute("http://10.0.0.2:9999/news/upload.php");
                         task.setEachExceptionsHandler(new EachExceptionsHandler() {
                             @Override
                             public void handleIOException(IOException e) {
                                 Toast.makeText(getContext(), "No puede conectarse al servidor", Toast.LENGTH_SHORT).show();
                             }

                             @Override
                             public void handleMalformedURLException(MalformedURLException e) {
                                 Toast.makeText(getContext(), "Error de URL ", Toast.LENGTH_SHORT).show();
                             }

                             @Override
                             public void handleProtocolException(ProtocolException e) {
                                 Toast.makeText(getContext(), "Error  de Protocolo ", Toast.LENGTH_SHORT).show();
                             }

                             @Override
                             public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                                 Toast.makeText(getContext(), "Error  de condificacion ", Toast.LENGTH_SHORT).show();
                             }
                         });
                     }
                    } catch (FileNotFoundException e) {
                        Toast.makeText(getContext(), "Algo ha fallado al obtener Foto", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });

        }
    */
    private void ObtenerTipoLlave() {

        rgLLaves.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rbLlaveSmart: {
                        tipoLlave = "1";
                        descLlave = "Inteligente";
                        if (mListener != null) {
                            Log.d("TAG", "Fragment ==> " + tipoLlave);
                            mListener.sendTipoLlave(tipoLlave);
                            mListener.sendDescLlave(descLlave);
                        }
                        break;

                    }
                    case R.id.rbLlaveNormal: {
                        tipoLlave = "2";
                        descLlave = "Normal";
                        if (mListener != null) {
                            Log.d("TAG", "Fragment ==> " + tipoLlave);
                            mListener.sendTipoLlave(tipoLlave);
                            mListener.sendDescLlave(descLlave);
                        }
                        break;
                    }
                }
            }
        });

    }

    private void ObtenerDatosOtrosParametros(String s) {


        Call<OtrosParametros> call = AdapterOtrosParametros.getServiceOtros("id_lista", s).getOtrosParametros();
        call.enqueue(new OtrosCallback());


    }

    private class OtrosCallback implements retrofit2.Callback<OtrosParametros> {
        @Override
        public void onResponse(Call<OtrosParametros> call, Response<OtrosParametros> response) {
            if (response.isSuccessful()) {

                OtrosParametrosResponse otrosParametros = response.body();
                DividirListas(otrosParametros.getOtrosParametros());

            } else {
                Toast.makeText(getContext(), "Error en el formato de respuesta de parametros", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<OtrosParametros> call, Throwable t) {
            Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            Log.v("otros-*** ===>", t.getMessage());
        }
    }

    private void DividirListas(ArrayList<OtrosParametros> otrosParametros) {
        List<OtrosParametros> cantidades = new ArrayList<>();
        List<OtrosParametros> condicion = new ArrayList<>();
        List<OtrosParametros> lados = new ArrayList<>();
        List<OtrosParametros> condicionEntrada = new ArrayList<>();
        List<OtrosParametros> llaves = new ArrayList<>();

        for (OtrosParametros varOtros : otrosParametros) {
            switch (Integer.parseInt(varOtros.getId())) {
                case 59: {
                    llaves.add(varOtros);
                    break;
                }
                case 61: {
                    lados.add(varOtros);
                    break;
                }
                case 62: {
                    cantidades.add(varOtros);
                    break;
                }
                case 63: {
                    condicion.add(varOtros);
                    break;
                }
                case 64: {
                    condicionEntrada.add(varOtros);
                    break;
                }
            }
        }
        //  Toast.makeText(getContext(), lados.toString(), Toast.LENGTH_LONG).show();
        LlenarCantidades(cantidades);
        LlenarLadosVehiculo(lados);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_contextual_lados_vehiculo, menu);
        idPictureLados = v.getId();

        if (lado.contains(idPictureLados)) {
            int index = lado.indexOf(idPictureLados);
            lado.add(index, idPictureLados);

        } else {
            lado.add(idPictureLados);
        }

        if (mListener != null) {
            mListener.sendLadoId(lado);
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_camara: {
                try {
                    startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA_REQUEST);
                    cameraPhoto.addToGallery();
                } catch (IOException e) {
                    Toast.makeText(getContext(), "Algo ha fallado al tomar Foto", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                //   takePhoto();
                return true;
            }
            case R.id.action_editar: { //layoutLados.removeView(imageView);
//                mDrawingView = new DrawingView(layoutLados.getContext());
//               mDrawingView.setId(idPictureLados);
//                mDrawingView.setMinimumWidth(w);
//                mDrawingView.setMinimumHeight(h);
//                LayoutInflater inflater = LayoutInflater.from(imageView.getContext());
//                inflater.inflate(R.layout.screen_drawing_room, null);
//              LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.view_drawing_pad);
                layoutLados.addView(mDrawingView);
                return true;
            }
        }
        return super.onContextItemSelected(item);
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);
        startActivityForResult(intent, TAKE_PICTURE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //  super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                String PhotoPath = cameraPhoto.getPhotoPath();

                switch (idPictureLados) {
                    case 1: {
                        photoToUpload.add(PhotoPath);
                        break;
                    }
                    case 2: {
                        photoToUpload.add(PhotoPath);
                        break;
                    }
                    case 3: {
                        photoToUpload.add(PhotoPath);
                        break;
                    }
                    case 4: {
                        photoToUpload.add(PhotoPath);
                        break;
                    }
                    case 5: {
                        photoToUpload.add(PhotoPath);
                        break;
                    }
                }
                if (mListener != null) {
                    mListener.sendImageRuta(photoToUpload);
                }
                ImageView imageView = (ImageView) layoutLados.findViewById(idPictureLados);
                try {
                    Bitmap bitmap = ImageLoader.init().from(PhotoPath).requestSize(256, 256).getBitmap();
                    imageView.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.d("TAG==>", PhotoPath);
                }

            }
        }


       /* switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getActivity().getContentResolver().notifyChange(selectedImage, null);
                    ImageView imageView = (ImageView) layoutLados.findViewById(Integer.parseInt("1"));
                    ContentResolver cr = getActivity().getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);

                        imageView.setImageBitmap(bitmap);
                        Toast.makeText(getContext(), selectedImage.toString(),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }*/
    }

    private void LlenarLadosVehiculo(List<OtrosParametros> lados) {
        layoutLados.setRowCount(lados.size());
        for (OtrosParametros varLados : lados) {
            ImageView imagen = new ImageView(layoutLados.getContext());
            imagen.setPadding(25, 25, 25, 25);
            imagen.setId(Integer.parseInt(varLados.getValor()));
            String url = varLados.getRutaImagen();
            String[] p = url.split("/");
            String imageLink = "https://drive.google.com/uc?export=download&id=" + p[5];
            Picasso.with(getContext())
                    .load(imageLink)
                    .error(R.mipmap.ic_launcher)
                    .into(imagen);
            layoutLados.addView(imagen);
            registerForContextMenu(imagen);
        }

    }

    private void LlenarCantidades(final List<OtrosParametros> cantidades) {
        layoutCantidades.setRowCount(cantidades.size());
        EditText etxtCantidades = null;
        for (OtrosParametros varCantidad : cantidades) {
            etxtCantidades = new EditText(layoutCantidades.getContext());
            etxtCantidades.setHint(varCantidad.getDescripcion());
            etxtCantidades.setId(Integer.parseInt(varCantidad.getValor()));
            etxtCantidades.setInputType(InputType.TYPE_CLASS_NUMBER);
            etxtCantidades.setPadding(15, 15, 15, 15);
            layoutCantidades.addView(etxtCantidades);
            final EditText finalEtxtCantidades = etxtCantidades;
            etxtCantidades.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (timer != null)
                        timer.cancel();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (editable.length() >= 1) {
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                getActivity().runOnUiThread(new Runnable() {
                                    public void run() {

                                        switch (finalEtxtCantidades.getId()) {
                                            case 1: {
                                                cantAlfombra = finalEtxtCantidades.getText().toString();
                                                if (mListener != null) {
                                                    Log.d("TAG", "Fragment ==> " + cantAlfombra);
                                                    mListener.sendCantAlfombra(cantAlfombra);

                                                }
                                                break;
                                            }
                                            case 2: {
                                                cantLlaves = finalEtxtCantidades.getText().toString();

                                                if (mListener != null) {
                                                    Log.d("TAG", "Fragment ==> " + cantLlaves);
                                                    mListener.sendCantLlave(cantLlaves);
                                                }
                                                break;
                                            }

                                            case 3: {
                                                cantGato = finalEtxtCantidades.getText().toString();
                                                if (mListener != null) {
                                                    Log.d("TAG", "Fragment ==> " + cantGato);
                                                    mListener.sendCantGato(cantGato);
                                                }
                                                break;
                                            }
                                            case 4: {
                                                cantAlicate = finalEtxtCantidades.getText().toString();
                                                if (mListener != null) {
                                                    Log.d("TAG", "Fragment ==> " + cantAlicate);
                                                    mListener.sendCantAlicate(cantAlicate);

                                                }
                                                break;
                                            }
                                            case 5: {
                                                cantLlaveRueda = finalEtxtCantidades.getText().toString();
                                                if (mListener != null) {
                                                    Log.d("TAG", "Fragment ==> " + cantLlaveRueda);
                                                    mListener.sendLlaveRueda(cantLlaveRueda);

                                                }

                                                break;
                                            }
                                            case 6: {
                                                noBateria = finalEtxtCantidades.getText().toString();
                                                if (mListener != null) {
                                                    Log.d("TAG", "Fragment ==> " + noBateria);
                                                    mListener.sendNoBateria(noBateria);
                                                }
                                                break;
                                            }
                                        }

                                    }

                                });
                            }


                        }, DELAY);
                    }
                }
            });

            switch (etxtCantidades.getId()) {
                case 1: {

                    if (cantAlfombra != null) {
                        etxtCantidades.setText(cantAlfombra);
                    }
                    break;

                }
                case 2: {

                    if (cantLlaves != null) {
                        etxtCantidades.setText(cantLlaves);
                    }
                    break;

                }
                case 3: {
                    if (cantGato != null) {
                        etxtCantidades.setText(cantGato);
                    }
                    break;

                }
                case 4: {
                    if (cantAlicate != null) {
                        etxtCantidades.setText(cantAlicate);
                    }
                    break;
                }
                case 5: {
                    if (cantLlaveRueda != null) {
                        etxtCantidades.setText(cantLlaveRueda);
                    }
                    break;
                }
                case 6: {

                    if (noBateria != null) {
                        etxtCantidades.setText(noBateria);
                    }
                    break;

                }
            }
        }
    }
}



