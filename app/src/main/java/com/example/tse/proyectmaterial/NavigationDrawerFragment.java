package com.example.tse.proyectmaterial;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tse.proyectmaterial.adapter.InformationAdapter;
import com.example.tse.proyectmaterial.model.Information;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment implements InformationAdapter.ClickListener {

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

        // android RecyclerView onClickListener
        adapter.setClickListener(this);
        // le paso a mi recycler mi adapter
        recyclerView.setAdapter(adapter);
        // ahora defino el layout manager para mi recycler
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getActivity(),"onClick" + position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity(),"onLongClick" + position,Toast.LENGTH_SHORT).show();
            }
        }));
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

    @Override
    public void itemClicked(View view, int position) {
        // puedo utilizar la position para determinar que actividad puedo lanzar en base a un item seleccionado del recycler
        Log.e("View", String.valueOf(view));
        Log.e("position", String.valueOf(position));

        // con esto invoco una segunda actividad o en este caso a la subactividad que ya tengo
        startActivity(new Intent(getActivity(), SubActivity.class));

    }




    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{


        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {

            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return super.onSingleTapUp(e);
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    // super.onLongPress(e);
                    View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if(child != null && clickListener != null){
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }

                }
            });
        }

        /**
         * Silently observe and/or take over touch events sent to the RecyclerView
         * before they are handled by either the RecyclerView itself or its child views.
         * <p/>
         * <p>The onInterceptTouchEvent methods of each attached OnItemTouchListener will be run
         * in the order in which each listener was added, before any other touch processing
         * by the RecyclerView itself or child views occurs.</p>
         *
         * @param rv
         * @param e  MotionEvent describing the touch event. All coordinates are in
         *           the RecyclerView's coordinate system.
         * @return true if this OnItemTouchListener wishes to begin intercepting touch events, false
         * to continue with the current behavior and continue observing future events in
         * the gesture.
         */
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(),e.getY());
            if(child != null && clickListener != null && gestureDetector.onTouchEvent(e)){
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        /**
         * Process a touch event as part of a gesture that was claimed by returning true from
         * a previous call to {@link #onInterceptTouchEvent}.
         *
         * @param rv
         * @param e  MotionEvent describing the touch event. All coordinates are in
         */
        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }



    }

    public static interface ClickListener{

        public void onClick(View view, int position);
        public void onLongClick(View view, int position);

    }
}
