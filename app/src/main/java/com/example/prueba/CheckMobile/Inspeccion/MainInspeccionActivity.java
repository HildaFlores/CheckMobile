package com.example.prueba.CheckMobile.Inspeccion;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.prueba.CheckMobile.R;

public class MainInspeccionActivity extends AppCompatActivity {
    private mSectionsPagerAdapterInspeccion mSectionsPagerAdapterInspeccion;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inspeccion);

        mSectionsPagerAdapterInspeccion = new mSectionsPagerAdapterInspeccion(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container2);
        mViewPager.setAdapter(mSectionsPagerAdapterInspeccion);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs2);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main_inspeccion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class mSectionsPagerAdapterInspeccion extends FragmentPagerAdapter {

        public mSectionsPagerAdapterInspeccion(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    InspeccionGeneralActivity servicio = new InspeccionGeneralActivity();
                    return servicio;

                case 1:
                    InspeccionLucesActivity Luces = new InspeccionLucesActivity();
                    return Luces;

                case 2:

                    InspeccionAccesoriosActivity accesorios = new InspeccionAccesoriosActivity();
                    return accesorios;
                case 3:
                    OtrosActivity otros = new OtrosActivity();
                    return otros;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String titulo1 = getString(R.string.section1Inspeccion);
            String titulo2 = getString(R.string.section2Inspeccion);
            String titulo3 = getString(R.string.section3Inspeccion);
            String titulo4 = getString(R.string.section4Inspeccion);

            switch (position) {
                case 0:
                    return titulo1;
                case 1:
                    return titulo2;
                case 2:
                    return titulo3;
                case 3:
                    return titulo4;
            }
            return null;
        }
    }

}
