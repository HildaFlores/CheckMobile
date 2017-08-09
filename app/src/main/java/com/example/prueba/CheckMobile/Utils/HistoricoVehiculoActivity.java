package com.example.prueba.CheckMobile.Utils;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.example.prueba.CheckMobile.R;

import java.util.List;

public class HistoricoVehiculoActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_vehiculo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarHistorico);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container3);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsHistorico);
        tabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_buscar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_buscar) {
            SearchView search = (SearchView) MenuItemCompat.getActionView(item);
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    tab1HistoricoVehiculo tab1 = new tab1HistoricoVehiculo();
                    tab2HistoricoVehiculo tab2 = new tab2HistoricoVehiculo();
                    tab3HistoricoVehiculo tab3 = new tab3HistoricoVehiculo();
                    Bundle argumentos = new Bundle();
                    Log.d("QUERTY", "==> "+ query);
                    argumentos.putString("REFERENCIA",query);
                    tab1.setArguments(argumentos);
                    tab2.setArguments(argumentos);
                    tab3.setArguments(argumentos);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container3, tab1);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    tab1HistoricoVehiculo tab1HistoricoVehiculo = new tab1HistoricoVehiculo();
                    return tab1HistoricoVehiculo;

                case 1:
                    tab2HistoricoVehiculo tab2HistoricoVehiculo = new tab2HistoricoVehiculo();
                    return tab2HistoricoVehiculo;
                case 2:
                    tab3HistoricoVehiculo tab3HistoricoVehiculo = new tab3HistoricoVehiculo();
                    return tab3HistoricoVehiculo;

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
            switch (position) {
                case 0:
                    return "Inspecciones";
                case 1:
                    return "Ordenes";
                case 2:
                    return "Mantenimientos";
            }
            return null;
        }
    }
}
