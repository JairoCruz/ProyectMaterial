package com.example.tse.proyectmaterial;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tse.proyectmaterial.adapter.InformationAdapter;
import com.example.tse.proyectmaterial.model.Information;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {

    private RecyclerView recyclerView;


    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private ActionBarDrawerToggle mdrawerToggle;
    private DrawerLayout mdrawerLayout;

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    private InformationAdapter adapter;

    private View containerView;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    // este metodo lo utilizo para recuperar datos para el recycler view
    public static List<Information> getData(){
        List<Information> data = new ArrayList<>();
        int[] icon = {R.mipmap.ic_add_shopping_cart_white,R.mipmap.ic_alarm_on_white,R.mipmap.ic_aspect_ratio_white,R.mipmap.ic_build_white};
        String[] title = {"Shopping cart","Alarm","Ratio","Herramientas"};

        for (int i = 0; i < title.length && i < icon.length; i++){
            Information current = new Information();
            current.iconId = icon[i];
            current.title = title[i];
            data.add(current);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        if(savedInstanceState != null){
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Esto lo sustituyo para poder utilizar el recycler view
        // return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer,container,false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        // para que se muestre mi adapter
        adapter = new InformationAdapter(getActivity(), getData());
        // le paso a mi recycler mi adapter
        recyclerView.setAdapter(adapter);
        // ahora defino el layout manager para mi recycler
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolBar) {
        containerView = getActivity().findViewById(fragmentId);
        mdrawerLayout = drawerLayout;
        mdrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout,toolBar, R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // Este metodo me sirve cuando mi toolbar es visible y el navigation drawer se muestra, al aparecer
                // el drawer el toolbar se ira oscureciendo haciendo mas enfacies en el drawer, y luego cuando se
                // cierre el drawer toolbar volvera a su color por defecto.
                if (slideOffset < 0.6){
                    toolBar.setAlpha(1- slideOffset);
                }

            }
        };

        if(!mUserLearnedDrawer && !mFromSavedInstanceState){
            mdrawerLayout.openDrawer(containerView);
        }

        mdrawerLayout.setDrawerListener(mdrawerToggle);
        mdrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mdrawerToggle.syncState();
            }
        });
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }


    public static String readFromPreferences(Context context, String preferenceName, String defaulValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
       return sharedPreferences.getString(preferenceName,defaulValue);
    }
}
