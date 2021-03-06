package com.example.tse.proyectmaterial;


import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.tse.proyectmaterial.extras.SortListener;
import com.example.tse.proyectmaterial.fragment.FragmentSearch;
import com.example.tse.proyectmaterial.fragment.NavigationDrawerFragment;
import com.example.tse.proyectmaterial.fragment.Tab1;
import com.example.tse.proyectmaterial.fragment.Tab2;
import com.example.tse.proyectmaterial.fragment.Tab3;
import com.example.tse.proyectmaterial.logging.L;
import com.example.tse.proyectmaterial.services.MyService;
import com.example.tse.proyectmaterial.tabs.SlidingTabLayout;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.os.Handler;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabListener;
import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;

public class MainActivity extends ActionBarActivity{

    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    private static final int JOB_ID = 100;
    private static final long POLLY_FREQUENCY = 28800000;
    private JobScheduler mJobScheduler;

    public static final int TITULO_1 = 0;
    public static final int TITULO_2 = 1;
    public static final int TITULO_3 = 2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //aca solo cambio el layout principal que mostrara, es cual es un drawer que no oculta el toolbar
        setContentView(R.layout.activity_main);

        mJobScheduler = JobScheduler.getInstance(this);
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                contructJob();
            }
        }, 30000);

        // Para empezar a utilizar mi ToolBar personalizado
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Configuracion para poder utilizar el NavigationDrawer
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer,(DrawerLayout)findViewById(R.id.drawer_layout), toolbar);

        // con esto utilizo las Tabs
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        //aca agrego mi tab layout personalizado
        mTabs.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
        mTabs.setDistributeEvenly(true);

        mTabs.setBackgroundColor(getResources().getColor(R.color.primaryColor));
        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.colorAccent));

        mTabs.setViewPager(mPager);




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
        }
        if(id == R.id.navigate){
            // con esto lanzo una actividad, cuando alguien hace click sobre mi toolbar icon
            startActivity(new Intent(this, SubActivity.class));
        }
        if(id == R.id.Tab_with_library){
            startActivity(new Intent(this, ActivityUsingTabLibrary.class));
        }
        if(id== R.id.VectorAndroidExample){
            startActivity(new Intent(this,ActivityVectorTest.class));
        }
        if(id == R.id.RecyclerItemAnimator){
            startActivity(new Intent(this, ActivityRecyclerItemAnimation.class));
        }


        return super.onOptionsItemSelected(item);
    }



    private void contructJob(){
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(this, MyService.class));
        builder.setPeriodic(POLLY_FREQUENCY)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true);
        mJobScheduler.schedule(builder.build());
    }


    // Esta clase esta relacionada con el uso de los tabs
    class MyPagerAdapter extends FragmentStatePagerAdapter{

       // String[] tabs;
        // Estoy trabajando ahora con tabs con iconos
        int icon[] = {R.mipmap.ic_work_white_24dp, R.mipmap.ic_home_white, R.mipmap.ic_build_white};
        String[] tabText = getResources().getStringArray(R.array.tabs);

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            // tabs = getResources().getStringArray(R.array.tabs);
            tabText = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
           /* MyFragment myFragment = MyFragment.getInstance(position);
            return myFragment;*/
            Fragment fragment = null;
            switch (position){
                case TITULO_1:
                    fragment = Tab1.newInstance("","");
                    break;
                case TITULO_2:
                    fragment = Tab2.newInstance("","");
                    break;
                case TITULO_3:
                    fragment = Tab3.newInstance("","");
            }
            return fragment;



        }

        @Override
        public CharSequence getPageTitle(int position) {
            // return tabs[position];
            Drawable drawable = getResources().getDrawable(icon[position]);
            drawable.setBounds(0, 0, 36, 36);
            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString = new SpannableString(" ");
            spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
    // Esta clase tambien esta relacionada al uso de los Tabs
    public static class MyFragment extends Fragment{
        private TextView textView;
        public static MyFragment getInstance(int position){
            MyFragment myFragment = new MyFragment();
            Bundle args = new Bundle();
            // con esto le estoy pasando argumentos a mi fragment
            args.putInt("position", position);
            myFragment.setArguments(args);
            return myFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragment_my,container,false);
            textView = (TextView) layout.findViewById(R.id.position);
            Bundle bundle = getArguments();
            if(bundle != null){
                textView.setText("The Page seleccionada es " + bundle.getInt("position"));
            }
            return layout;
        }
    }

    // con este codigo al momento de seleccionar algo en el drawer me coloca en la tab conespondiente
    public void onDrawerItemClicked(int index){
        mPager.setCurrentItem(index);
    }
}
