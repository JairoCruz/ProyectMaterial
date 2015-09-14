package com.example.tse.proyectmaterial;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tse.proyectmaterial.fragment.FragmentBoxOffice;
import com.example.tse.proyectmaterial.fragment.FragmentSearch;
import com.example.tse.proyectmaterial.fragment.FragmentUpcoming;
import com.example.tse.proyectmaterial.fragment.MyFragment;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by TSE on 03/09/2015.
 */
public class ActivityUsingTabLibrary extends ActionBarActivity implements MaterialTabListener {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private MaterialTabHost tabHost;


    // estas variables las utilizo con tabs diferentes
    public static final int MOVIES_SEARCH_RESULTS = 0;
    public static final int MOVIES_HITS= 1;
    public static final int MOVIES_UPCOMING = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_tab_library);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                tabHost.setSelectedNavigationItem(position);

            }
        });

        for (int i = 0; i < adapter.getCount(); i ++){
            tabHost.addTab(
                    tabHost.newTab()
            .setIcon(adapter.getIcon(i))
            .setTabListener(this));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub, menu);
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
        }
        // Esta es la accion para que mi boton me regrese a la actividad principal
        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {

        viewPager.setCurrentItem(materialTab.getPosition());

    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }



    private class ViewPagerAdapter extends FragmentStatePagerAdapter{

        int icon[] = {R.mipmap.ic_work_white_24dp, R.mipmap.ic_home_white, R.mipmap.ic_build_white};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // con esto hago que se muestren 3 frament diferentes cada uno con su respectivo layout fragment
            Fragment fragment = null;
            switch (position){
                case MOVIES_SEARCH_RESULTS:
                    fragment = FragmentSearch.newInstance("", "");
                    break;
                case MOVIES_HITS:
                    fragment = FragmentBoxOffice.newInstance("", "");
                    break;
                case MOVIES_UPCOMING:
                    fragment = FragmentUpcoming.newInstance("", "");
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tabs)[position];

        }

        private Drawable getIcon(int position){
            return getResources().getDrawable(icon[position]);
        }
    }
}
