package com.example.tse.proyectmaterial;

import android.content.ComponentName;
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
import android.view.View;
import android.widget.ImageView;

import com.example.tse.proyectmaterial.extras.SortListener;
import com.example.tse.proyectmaterial.fragment.FragmentBoxOffice;
import com.example.tse.proyectmaterial.fragment.FragmentSearch;
import com.example.tse.proyectmaterial.fragment.FragmentUpcoming;
import com.example.tse.proyectmaterial.fragment.MyFragment;
import com.example.tse.proyectmaterial.logging.L;
import com.example.tse.proyectmaterial.services.MyService;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;

/**
 * Created by TSE on 03/09/2015.
 */
public class ActivityUsingTabLibrary extends ActionBarActivity implements MaterialTabListener, View.OnClickListener {

    private static final int JOB_ID = 100;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private MaterialTabHost tabHost;

    private ViewPagerAdapter adapter;


    private static final String TAG_SORT_NAME ="sortName";
    private static final String TAG_SORT_DATE ="sortDate";
    private static final String TAG_SORT_RATINGS ="sortRatings";


    // estas variables las utilizo con tabs diferentes
    public static final int MOVIES_SEARCH_RESULTS = 0;
    public static final int MOVIES_HITS= 1;
    public static final int MOVIES_UPCOMING = 2;

    private JobScheduler mJobScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mJobScheduler = JobScheduler.getInstance(this);
        constructJob();

        setContentView(R.layout.activity_using_tab_library);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
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

        builderFAB();

    }

    private void constructJob(){
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(this, MyService.class));
        builder.setPeriodic(2000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true);

        mJobScheduler.schedule(builder.build());
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

    private void builderFAB(){
        // Este codigo es para utilizar un boton flotante
        // con la ayuda de una libreria
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_action_new);

        // con este codigo ya aparece el boton flotante no he tenido que agregar ningun elemento en el layout
        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(imageView)
                .setBackgroundDrawable(R.drawable.selector_button_red)
                .build();

        // con esto estoy creando 3 iconos para que aparescan como un sub menu cuando se de click en el boton
        ImageView iconSortName = new ImageView(this);
        iconSortName.setImageResource(R.mipmap.ic_add_shopping_cart_white);

        ImageView iconSortDate = new ImageView(this);
        iconSortDate.setImageResource(R.mipmap.ic_alarm_on_white);

        ImageView iconSortRatings = new ImageView(this);
        iconSortRatings.setImageResource(R.mipmap.ic_home_white);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_sub_button_gray));
        // con esto los agrego
        SubActionButton buttonSortName = itemBuilder.setContentView(iconSortName).build();
        SubActionButton buttonSortDate = itemBuilder.setContentView(iconSortDate).build();
        SubActionButton buttonSortRatings = itemBuilder.setContentView(iconSortRatings).build();

        buttonSortName.setTag(TAG_SORT_NAME);
        buttonSortDate.setTag(TAG_SORT_DATE);
        buttonSortRatings.setTag(TAG_SORT_RATINGS);

        buttonSortName.setOnClickListener(this);
        buttonSortDate.setOnClickListener(this);
        buttonSortRatings.setOnClickListener(this);

        // con esto se agregan al boton
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonSortName)
                .addSubActionView(buttonSortDate)
                .addSubActionView(buttonSortRatings)
                .attachTo(actionButton)
                .build();

    }

    @Override
    public void onClick(View v) {
        Fragment fragment = (Fragment) adapter.instantiateItem(viewPager, viewPager.getCurrentItem());
        if (fragment instanceof SortListener){

            if (v.getTag().equals(TAG_SORT_NAME)){
                // L.t(this,"sort name was clicked");
                ((SortListener) fragment).onSortByName();

            }
            if (v.getTag().equals(TAG_SORT_DATE)){
                // L.t(this, "sort date was clicked");
                ((SortListener) fragment).onSortByDate();
            }
            if (v.getTag().equals(TAG_SORT_RATINGS)){
                // L.t(this,"sort ratings was clicked");
                ((SortListener) fragment).onSortByRating();
            }
        }

    }
}
