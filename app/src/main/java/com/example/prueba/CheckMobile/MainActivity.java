package com.example.prueba.CheckMobile;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import com.example.prueba.CheckMobile.Inspeccion.InspeccionVehiculo;
import com.example.prueba.CheckMobile.MenuPrincipal.GreenAdapterInspeccion;
import com.example.prueba.CheckMobile.MenuPrincipal.Tab1Inspecciones;
import com.example.prueba.CheckMobile.MenuPrincipal.Tab2OrdenesTrabajo;
import com.example.prueba.CheckMobile.MenuPrincipal.Tab3Vehiculos;
import java.util.ArrayList;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;



public class MainActivity extends AppCompatActivity implements Tab1Inspecciones.sendData {
    /*Declaraciones*/

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    private String baseUrl;
    private String idUsuario;
    RecyclerView recyclerViewInspeccion;
    ArrayList<InspeccionVehiculo> inspeccionVehiculos = new ArrayList<InspeccionVehiculo>();
    int indexClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public OkHttpClient.Builder httpCliente() {
        OkHttpClient.Builder httpClient;
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient = new OkHttpClient().newBuilder();
        //add logging as last interceptor
        httpClient.addInterceptor(logging); //<- this is the important line;
        setBaseUrl("http://192.168.2.19:4567/");
        //192.168.0.109 //192.168.1.92//10.0.0.185  192.168.2.19
        return httpClient;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_salir) {
            finish();
            return true;
        } else if (id == R.id.action_search) {
            SearchView search = (SearchView) MenuItemCompat.getActionView(item);
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    newText = newText.toUpperCase();
                    final ArrayList<InspeccionVehiculo> filterList = new ArrayList<InspeccionVehiculo>();
                    for (int i = 0; i < inspeccionVehiculos.size(); i++) {
                        final String text = inspeccionVehiculos.get(i).getNombre_vehiculo().toUpperCase();
                        if (text.contains(newText)) {
                            filterList.add(inspeccionVehiculos.get(i));
                        }
                    }
                    GreenAdapterInspeccion madapter = (GreenAdapterInspeccion) recyclerViewInspeccion.getAdapter();
                    madapter.setFilter(filterList);
                    /*
                    recyclerViewInspeccion.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    GreenAdapterInspeccion madapter = new GreenAdapterInspeccion(filterList.size(),filterList,MainActivity.this);
                    recyclerViewInspeccion.setAdapter(madapter);
                    madapter.notifyDataSetChanged();*/

                    return true;
                }
            });
            return false;
        }


        return super.onOptionsItemSelected(item);
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String formatearParametro(String parametro, String valor) {

        String param;
        param = "{ '" + parametro + "' :" + "'" + valor + "' }";

        return param;
    }

    public String formatearParametro(String parametro1, String valor1, String parametro2, String valor2) {
        String param;
        param = "{ '" + parametro1 + "' :" + "'" + valor1 + "', " +
                "'" + parametro2 + "' :" + " '" + valor2 + "' }";

        return param;

    }

    @Override
    public void sendAdapter(RecyclerView recyclerView) {
        this.recyclerViewInspeccion = recyclerView;
    }

    @Override
    public void sendList(ArrayList<InspeccionVehiculo> inspecciones) {
        this.inspeccionVehiculos = inspecciones;
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Tab1Inspecciones inspecciones = new Tab1Inspecciones();
                    return inspecciones;

                case 1:
                    Tab2OrdenesTrabajo ordenesTrabajo = new Tab2OrdenesTrabajo();
                    return ordenesTrabajo;
                case 2:


                    Tab3Vehiculos vehiculos = new Tab3Vehiculos();
                    return vehiculos;

                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String titulo1 = getString(R.string.Section1);
            String titulo2 = getString(R.string.Section2);
            String titulo3 = getString(R.string.Section3);

            switch (position) {
                case 0:
                    return titulo1;
                case 1:
                    return titulo2;
                case 2:
                    return titulo3;
            }
            return null;
        }
    }
}
