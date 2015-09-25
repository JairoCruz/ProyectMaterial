package com.example.tse.proyectmaterial;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.tse.proyectmaterial.adapter.AdapterRecyclerItemAnimations;

public class ActivityRecyclerItemAnimation extends ActionBarActivity {

    private EditText mInput;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private AdapterRecyclerItemAnimations mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_item_animation);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mInput = (EditText) findViewById(R.id.text_input);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerAnimatedItems);
        mAdapter = new AdapterRecyclerItemAnimations(this);
        // Con esta linea estoy animando my recycler view
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(1000);
        animator.setRemoveDuration(1000);
        mRecyclerView.setItemAnimator(animator);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addItem(View view){
        if(mInput.getText() != null){
            String text = mInput.getText().toString();
            if (text != null && text.trim().length() > 0){
                mAdapter.addItem(mInput.getText().toString());
            }
        }
    }


}
