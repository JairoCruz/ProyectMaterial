package com.example.tse.proyectmaterial.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tse.proyectmaterial.R;

/**
 * Created by TSE on 03/09/2015.
 */

// separe la clase que estaba en MainActivity para que este disponible para cualquiera que la quiera utilizar

public  class MyFragment extends Fragment {
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
