package com.example.prueba.CheckMobile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.prueba.CheckMobile.Actualizaciones.FiltroInspeccionActivity;
import com.example.prueba.CheckMobile.Actualizaciones.FiltroOrdenTrabajoActivity;
import com.example.prueba.CheckMobile.Actualizaciones.FiltroVehiculoActivity;
import com.example.prueba.CheckMobile.Inspeccion.InspeccionVehiculo;
import com.example.prueba.CheckMobile.MenuPrincipal.GreenAdapterInspeccion;
import com.example.prueba.CheckMobile.MenuPrincipal.GreenAdapterOrden;
import com.example.prueba.CheckMobile.MenuPrincipal.Tab1Inspecciones;
import com.example.prueba.CheckMobile.MenuPrincipal.Tab2OrdenesTrabajo;
import com.example.prueba.CheckMobile.OrdenTrabajo.OrdenTrabajoEnc;
import com.example.prueba.CheckMobile.Usuario.LoginActivity;
import com.example.prueba.CheckMobile.Utils.DashBoardInspeccionActivity;
import com.example.prueba.CheckMobile.Utils.DashBoardOrdenActivity;
import com.example.prueba.CheckMobile.Utils.FullscreenActivity;
import com.example.prueba.CheckMobile.Utils.HistoricoVehiculoActivity;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.example.prueba.CheckMobile.Utils.Constantes.IPSERVIDOR;
import static com.example.prueba.CheckMobile.Utils.Constantes.PUERTO;


public class MainActivity extends AppCompatActivity implements Tab1Inspecciones.sendData, Tab2OrdenesTrabajo.sendDataOrden, NavigationView.OnNavigationItemSelectedListener {
    /*Declaraciones*/

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private String baseUrl;
    private String idUsuario, nombreusuario;
    RecyclerView recyclerViewInspeccion;
    RecyclerView recyclerViewOrden;
    ArrayList<InspeccionVehiculo> inspeccionVehiculos = new ArrayList<InspeccionVehiculo>();
    ArrayList<OrdenTrabajoEnc> ordenTrabajo = new ArrayList<OrdenTrabajoEnc>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

//        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("EXIT", false)) {
//            System.exit(0);
//
//        }
//        LinearLayout layoutHeader = (LinearLayout) findViewById(R.id.layoutHeader);
//        TextView txtNombreUsuario = (TextView) layoutHeader.findViewById(R.id.textViewUsuario);
//        TextView txtEmpresa = (TextView) layoutHeader.findViewById(R.id.textViewEmpresa);
//        Intent intent = getIntent();
//        Bundle extra = intent.getExtras();
//
//        if (extra != null) {
//            txtNombreUsuario.setText(extra.getString("NOMBREUSUARIO"));
//
//        }
//        txtEmpresa.setText("Empresa X");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public OkHttpClient.Builder httpCliente() {
        OkHttpClient.Builder httpClient;
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient = new OkHttpClient().newBuilder();
        //add logging as last interceptor
        httpClient.addInterceptor(logging); //<- this is the important line;
        setBaseUrl(IPSERVIDOR + ":" + PUERTO + "/");

        return httpClient;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        //else {
//            super.onBackPressed();
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_salir) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            this.finish();
            startActivity(intent);
            return true;

        } else if (id == R.id.action_about) {
            Intent intent = new Intent(MainActivity.this, FullscreenActivity.class);
            startActivity(intent);


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
                    switch (mViewPager.getCurrentItem()) {
                        case 0: {
                            final ArrayList<InspeccionVehiculo> filterList = new ArrayList<InspeccionVehiculo>();
                            for (int i = 0; i < inspeccionVehiculos.size(); i++) {
                                final String text = inspeccionVehiculos.get(i).getNombre_vehiculo().toUpperCase();
                                if (text.contains(newText)) {
                                    filterList.add(inspeccionVehiculos.get(i));
                                }
                            }
                            GreenAdapterInspeccion madapter = (GreenAdapterInspeccion) recyclerViewInspeccion.getAdapter();
                            madapter.setFilter(filterList);
                            break;
                        }
                        case 1: {
                            final ArrayList<OrdenTrabajoEnc> filterList = new ArrayList<OrdenTrabajoEnc>();
                            for (int i = 0; i < ordenTrabajo.size(); i++) {
                                final String text = ordenTrabajo.get(i).getNombreCliente().toUpperCase();
                                if (text.contains(newText)) {
                                    filterList.add(ordenTrabajo.get(i));
                                }
                            }
                            GreenAdapterOrden madapter = (GreenAdapterOrden) recyclerViewOrden.getAdapter();
                            madapter.setFilter(filterList);
                        }
                    }

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

    public String formatearParametro(String parametro1, String valor1, String parametro2, String valor2, String parametro3, String valor3) {
        String param;
        param = "{ '" + parametro1 + "' :" + " '" + valor1 + "', " +
                "'" + parametro2 + "' :" + " '" + valor2 + "', " +
                "'" + parametro3 + "' :" + " '" + valor3 + "' }";

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

    @Override
    public void sendRecycleList(RecyclerView recyclerView) {
        this.recyclerViewOrden = recyclerView;
    }

    @Override
    public void sendOrdenList(ArrayList<OrdenTrabajoEnc> listaOrden) {
        this.ordenTrabajo = listaOrden;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            case R.id.nav_titular: {
                Intent intent = new Intent(MainActivity.this, FiltroVehiculoActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.nav_inspeccion: {
                Intent intent = new Intent(MainActivity.this, FiltroInspeccionActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_orden: {
                Intent intent = new Intent(MainActivity.this, FiltroOrdenTrabajoActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_consulta_inspecciones: {
                Intent intent = new Intent(MainActivity.this, DashBoardInspeccionActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_consulta_ordenes: {
                Intent intent = new Intent(MainActivity.this, DashBoardOrdenActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_historico:
                Intent intent = new Intent(MainActivity.this, HistoricoVehiculoActivity.class);
                startActivity(intent);
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

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
//                case 2:
//
//
//                    Tab3Vehiculos vehiculos = new Tab3Vehiculos();
//                    return vehiculos;

                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
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
//                case 2:
//                    return titulo3;
            }
            return null;
        }
    }
}
