package com.example.prueba.CheckMobile.Utils;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.Actualizaciones.FiltroInspeccionActivity;
import com.example.prueba.CheckMobile.Inspeccion.AdapterInspeccion;
import com.example.prueba.CheckMobile.Inspeccion.InspeccionVehiculo;
import com.example.prueba.CheckMobile.Inspeccion.InspeccionVehiculoResponse;
import com.example.prueba.CheckMobile.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.prueba.CheckMobile.Utils.Constantes.JSON_KEY_FECHA_FINAL;
import static com.example.prueba.CheckMobile.Utils.Constantes.JSON_KEY_FECHA_INICIAL;
import static com.example.prueba.CheckMobile.Utils.Constantes.RESPONSE_CODE_OK;
import static com.itextpdf.text.pdf.PdfName.ca;


public class DashBoardInspeccionActivity extends AppCompatActivity {
    private static String TAG = "DashBoardInspeccionActivity";
    private TextView txtFechaInicial;
    private TextView txtFechaFinal;
    private DatePickerDialog.OnDateSetListener mDateSetListenerIni;
    private DatePickerDialog.OnDateSetListener mDateSetListenerFin;
    private ImageButton mDisplayDateInicial;
    private ImageButton mDisplayDateFinal;

    private ArrayList<InspeccionVehiculo> inspecciones = new ArrayList<InspeccionVehiculo>();
    private ArrayList<InspeccionVehiculo> filtroInspecciones;

    private Integer[] yData;
    PieChart pieChart;
    int sum = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_inspeccion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDashBoard);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtFechaInicial = (TextView) findViewById(R.id.txtFechaInicial);
        txtFechaFinal = (TextView) findViewById(R.id.txtFechaFinal);
        mDisplayDateInicial = (ImageButton) findViewById(R.id.ibFechaInicial);
        mDisplayDateFinal = (ImageButton) findViewById(R.id.ibFechaFinal);
        String monthComplete = null;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (month <= 9) {
            monthComplete = "0" + month;
        }
        txtFechaInicial.setText("01/" + monthComplete + "/" + year);
        txtFechaFinal.setText(day + "/" + monthComplete + "/" + year);
        ObtenerDatosInspeccion();
        llamarDatePicker();
        pieChart = (PieChart) findViewById(R.id.idPieChart);

        pieChart.setDescription(" ");
        pieChart.setRotationEnabled(true);

        //pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(65f);
        pieChart.setTransparentCircleAlpha(0);

       // addDataSetInicial();


        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

//                for(int i = 0; i < yData.length; i++){
//                    if(yData[i] == Float.parseFloat()){
//                        pos1 = i;
//                        break;
//                    }
//                }
                 if (h.getX() == 0.0){
                    filtroInspecciones = new ArrayList<InspeccionVehiculo>();
                    for (InspeccionVehiculo ins : inspecciones) {
                        if(ins.getEstado_inspeccion().equals("F"))
                        {
                            filtroInspecciones.add(ins);
                        }
                    }

                }
                else if (h.getX() == 1.0)
                {
                    filtroInspecciones = new ArrayList<InspeccionVehiculo>();
                    for (InspeccionVehiculo ins : inspecciones) {
                        if(ins.getEstado_inspeccion().equals("P") && ins.getDias() <31)
                        {
                            filtroInspecciones.add(ins);
                        }
                    }
                }
                else if (h.getX() == 2.0)
                {
                    filtroInspecciones = new ArrayList<InspeccionVehiculo>();
                    for (InspeccionVehiculo ins : inspecciones) {
                        if(ins.getEstado_inspeccion().equals("P") && ins.getDias()>=31)
                        {
                            filtroInspecciones.add(ins);
                        }
                    }
                }
                else if (h.getX() == 3.0)
                {
                    filtroInspecciones = new ArrayList<InspeccionVehiculo>();
                    for (InspeccionVehiculo ins : inspecciones) {
                        if(ins.getEstado_inspeccion().equals("I"))
                        {
                            filtroInspecciones.add(ins);
                        }
                    }
                }

                Log.d("TAG","==> " + filtroInspecciones.size());
                Intent intent = new Intent(DashBoardInspeccionActivity.this, FiltroInspeccionActivity.class);
                intent.putParcelableArrayListExtra("INSPECCIONES", filtroInspecciones);
                intent.putExtra("CONSULTAR", true);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }

    private void addDataSetInicial() {

        yData = new Integer[]{1};

        ArrayList<PieEntry> yEntrys = new ArrayList<>();

        for (int i = 0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i], i));
        }

        pieChart.setCenterText("Total\n" + String.valueOf(sum));
        pieChart.setCenterTextSize(50);
        pieChart.setCenterTextColor(Color.parseColor("#1b5e20"));

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, " ");
        PieData pieData = new PieData(pieDataSet);
        pieDataSet.setSliceSpace(1);
        pieDataSet.setValueTextSize(20);
        pieDataSet.setValueTextColor(Color.WHITE);

//        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#ea80fc"));
        pieDataSet.setColors(colors);

        pieChart.setData(pieData);
        pieChart.invalidate();

    }

    private void ObtenerDatosInspeccion() {

        Call<InspeccionVehiculo> call = AdapterInspeccion
                .getApiServicePorFecha(JSON_KEY_FECHA_INICIAL, txtFechaInicial.getText().toString(), JSON_KEY_FECHA_FINAL, txtFechaFinal.getText().toString())
                .getInspeccionesPorFecha();
        call.enqueue(new Callback<InspeccionVehiculo>() {
            @Override
            public void onResponse(Call<InspeccionVehiculo> call, Response<InspeccionVehiculo> response) {
                if (response.isSuccessful()) {
                    InspeccionVehiculoResponse inspeccion = response.body();
                    if (inspeccion.getResponseCode().equals(RESPONSE_CODE_OK)) {
                        inspecciones = inspeccion.getInspecciones();
                        distribuirInspecciones();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error en el formato de respuesta de inspeccion", Toast.LENGTH_SHORT).show();
                    Log.v("INSPECCION ==>", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<InspeccionVehiculo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.v("Aqui ===>", t.getMessage());
            }
        });
    }

    private void distribuirInspecciones() {
        int insFinalizadas = 0;
        int insVencidas = 0;
        int insPendientes = 0;
        int insNulas = 0;

        for (InspeccionVehiculo ins : inspecciones) {
            switch (ins.getEstado_inspeccion()) {
                case "P": {

                    if (ins.getDias() >= 31) {
                        insVencidas = insVencidas + 1;
                    } else {
                        insPendientes = insPendientes + 1;
                    }

                    break;

                }
                case "F": {
                    insFinalizadas = insFinalizadas + 1;
                    break;
                }
                case "I": {
                    insNulas = insNulas + 1;
                    break;
                }
            }

        }

        yData = new Integer[]{insFinalizadas, insPendientes, insVencidas, insNulas};
        sum = 0;
        addDataSet();
    }


    private void llamarDatePicker() {

        mDisplayDateInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        DashBoardInspeccionActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListenerIni,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListenerIni = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                txtFechaInicial.setText(date);
            }
        };


        mDisplayDateFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        DashBoardInspeccionActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListenerFin,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListenerFin = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                txtFechaFinal.setText(date);
                ObtenerDatosInspeccion();
            }
        };

    }

    private void addDataSet() {

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
//        ArrayList<String> xEntrys = new ArrayList<>();

        for (int i = 0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i], i));
        }

//        for(int i = 1; i < xData.length; i++){
//            xEntrys.add(xData[i]);
//        }

        for (int i = 0; i < yData.length; i++) {
            sum = sum + yData[i];
        }
        pieChart.setCenterText("Total\n" + String.valueOf(sum));
        pieChart.setCenterTextSize(50);
        pieChart.setCenterTextColor(Color.parseColor("#1b5e20"));


        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, " ");
        PieData pieData = new PieData(pieDataSet);
        pieDataSet.setSliceSpace(1);
        pieDataSet.setValueTextSize(20);
        pieDataSet.setValueTextColor(Color.WHITE);

//        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#03a9f4"));
        colors.add(Color.parseColor("#ffab91"));
        colors.add(Color.parseColor("#e57373"));
        colors.add(Color.parseColor("#66000000"));

        pieDataSet.setColors(colors);

        pieChart.getLegend().setEnabled(false);


//        //add legend to chart
//        Legend legend = pieChart.getLegend();
//        legend.setForm(Legend.LegendForm.SQUARE);
//        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
//        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        legend.setFormSize(40);

        //create pie data object

        pieChart.setData(pieData);
        pieChart.invalidate();

    }
}
