package com.example.prueba.CheckMobile.Utils;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prueba.CheckMobile.Actualizaciones.FiltroOrdenTrabajoActivity;
import com.example.prueba.CheckMobile.OrdenTrabajo.AdapterOrdenTrabajo;
import com.example.prueba.CheckMobile.OrdenTrabajo.OrdenTrabajoEnc;
import com.example.prueba.CheckMobile.OrdenTrabajo.OrdenTrabajoEncResponse;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.prueba.CheckMobile.Utils.Constantes.JSON_KEY_FECHA_FINAL;
import static com.example.prueba.CheckMobile.Utils.Constantes.JSON_KEY_FECHA_INICIAL;
import static com.example.prueba.CheckMobile.Utils.Constantes.JSON_KEY_TIPO_TRANS;
import static com.example.prueba.CheckMobile.Utils.Constantes.KEY_TIPO_TRANS_ORDEN;
import static com.example.prueba.CheckMobile.Utils.Constantes.RESPONSE_CODE_OK;
import static com.itextpdf.text.pdf.PdfName.IF;

public class DashBoardOrdenActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_orden);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCOrden);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.containerOrden);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private TextView txtFechaInicial;
        private TextView txtFechaFinal;
        private DatePickerDialog.OnDateSetListener mDateSetListenerIni;
        private DatePickerDialog.OnDateSetListener mDateSetListenerFin;
        private ImageButton mDisplayDateInicial;
        private ImageButton mDisplayDateFinal;
        ArrayList<OrdenTrabajoEnc> ordenes = new ArrayList<>();
        ArrayList<OrdenTrabajoEnc> filtroOrdenes = new ArrayList<>();
        private Integer[] yData;
        PieChart pieChart;
        int sum = 0;
        int section;

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_dash_board_orden, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.txtTitleSection);
            txtFechaInicial = (TextView) rootView.findViewById(R.id.txtOrdenFechaInicial);
            txtFechaFinal = (TextView) rootView.findViewById(R.id.txtOrdenFechaFinal);
            mDisplayDateInicial = (ImageButton) rootView.findViewById(R.id.ibOrdenFechaInicial);
            mDisplayDateFinal = (ImageButton) rootView.findViewById(R.id.ibOrdenFechaFinal);


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
            pieChart = (PieChart) rootView.findViewById(R.id.idPieChartOrden);

            pieChart.setDescription(" ");
            pieChart.setRotationEnabled(true);

            //pieChart.setUsePercentValues(true);
            //pieChart.setHoleColor(Color.BLUE);
            //pieChart.setCenterTextColor(Color.BLACK);
            pieChart.setHoleRadius(65f);
            pieChart.setTransparentCircleAlpha(0);
            llamarDatePicker();

            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                textView.setText(getString(R.string.section_Orden1));
                section = getArguments().getInt(ARG_SECTION_NUMBER);
                ObtenerDatosOrden();


            } else {
                textView.setText(getString(R.string.section_Orden2));
                section = getArguments().getInt(ARG_SECTION_NUMBER);
                ObtenerDatosOrden();
            }


            pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    if (h.getX() == 0.0) {
                        filtroOrdenes = new ArrayList<OrdenTrabajoEnc>();
                        for (OrdenTrabajoEnc orden : ordenes) {
                            if (orden.getKilometraje() >= 1000 && orden.getKilometraje() < 5000) {
                                filtroOrdenes.add(orden);
                            }
                        }
                    }
                    else if (h.getX() == 1.0){
                        filtroOrdenes = new ArrayList<OrdenTrabajoEnc>();
                        for (OrdenTrabajoEnc orden : ordenes) {
                            if (orden.getKilometraje() >= 5000 && orden.getKilometraje() < 10000)  {
                                filtroOrdenes.add(orden);
                            }
                        }
                    }else if(h.getX() == 2.0){
                        filtroOrdenes = new ArrayList<OrdenTrabajoEnc>();
                        for (OrdenTrabajoEnc orden : ordenes) {
                            if (orden.getKilometraje() >= 10000 && orden.getKilometraje() < 40000)  {
                                filtroOrdenes.add(orden);
                            }
                        }
                    }else if (h.getX() == 3.0)
                    {
                        filtroOrdenes = new ArrayList<OrdenTrabajoEnc>();
                        for (OrdenTrabajoEnc orden : ordenes) {
                            if (orden.getKilometraje() >= 40000)  {
                                filtroOrdenes.add(orden);
                            }
                        }
                    }
                    Intent intent = new Intent(getContext(), FiltroOrdenTrabajoActivity.class);
                    intent.putParcelableArrayListExtra("ORDENES", filtroOrdenes);
                    intent.putExtra("CONSULTAR", true);
                    startActivity(intent);
                }

                @Override
                public void onNothingSelected() {

                }
            });
            return rootView;
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
                            getContext(),
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
                            getContext(),
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
                    ObtenerDatosOrden();

                }
            };
        }

        private void ObtenerDatosOrden() {

            Call<OrdenTrabajoEnc> call = AdapterOrdenTrabajo
                    .getApiServiceFecha(JSON_KEY_FECHA_INICIAL, txtFechaInicial.getText().toString(),
                            JSON_KEY_FECHA_FINAL, txtFechaFinal.getText().toString(),
                            JSON_KEY_TIPO_TRANS, KEY_TIPO_TRANS_ORDEN)
                    .getOrdenFecha();
            call.enqueue(new Callback<OrdenTrabajoEnc>() {
                @Override
                public void onResponse(Call<OrdenTrabajoEnc> call, Response<OrdenTrabajoEnc> response) {
                    if (response.isSuccessful()) {
                        OrdenTrabajoEncResponse ordenesResponse = response.body();
                        if (ordenesResponse.getResponseCode().equals(RESPONSE_CODE_OK)) {
                            ordenes = ordenesResponse.getOrdenes();
                            if (section == 1) {
                                distribuirOrdenesPendientes();
                            } else {
                                distribuirOrdenesFinalizadas();
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), "Error en el formato de respuesta de orden", Toast.LENGTH_SHORT).show();
                        Log.v("ORDENES ==>", response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<OrdenTrabajoEnc> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.v("Aqui ===>", t.getMessage());
                }
            });
        }

        private void distribuirOrdenesFinalizadas() {

            int mil = 0;
            int cincoMil = 0;
            int diezMil = 0;
            int cuarentaMil = 0;

            for (OrdenTrabajoEnc orden : ordenes) {
                switch (orden.getEstadoFactura()) {
                    case "F": {
                        if (orden.getKilometraje() >= 1000 && orden.getKilometraje() < 5000) {
                            mil = mil + 1;
                        } else if (orden.getKilometraje() >= 5000 && orden.getKilometraje() < 10000) {
                            cincoMil = cincoMil + 1;
                        } else if (orden.getKilometraje() >= 10000 && orden.getKilometraje() < 40000) {
                            diezMil = diezMil + 1;
                        } else if (orden.getKilometraje() >= 40000) {
                            cuarentaMil = cuarentaMil + 1;
                        }
                        break;
                    }

                }

            }

            yData = new Integer[]{mil, cincoMil, diezMil, cuarentaMil};
            sum = 0;
            addDataSet();
        }

        private void distribuirOrdenesPendientes() {
            int mil = 0;
            int cincoMil = 0;
            int diezMil = 0;
            int cuarentaMil = 0;

            for (OrdenTrabajoEnc orden : ordenes) {
                switch (orden.getEstadoFactura()) {
                    case "V": {
                        if (orden.getKilometraje() >= 1000 && orden.getKilometraje() < 5000) {
                            mil = mil + 1;
                        } else if (orden.getKilometraje() >= 5000 && orden.getKilometraje() < 10000) {
                            cincoMil = cincoMil + 1;
                        } else if (orden.getKilometraje() >= 10000 && orden.getKilometraje() < 40000) {
                            diezMil = diezMil + 1;
                        } else if (orden.getKilometraje() >= 40000){
                            cuarentaMil = cuarentaMil + 1;
                        }
                        break;
                    }

                }

            }

            yData = new Integer[]{mil, cincoMil, diezMil, cuarentaMil};
            sum = 0;
            addDataSet();
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

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
            }
            return null;
        }
    }
}
