package com.example.tse.proyectmaterial.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tse.proyectmaterial.R;
import com.example.tse.proyectmaterial.model.Information;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Marhinita on 29/8/2015.
 */
public class InformationAdapter extends RecyclerView.Adapter <InformationAdapter.MyViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    // Los datos para llenar mi recyclerView
    List<Information> data = Collections.emptyList();

    public InformationAdapter(Context context, List<Information> data) {
        this.context = context;
       inflater =  LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.information_row, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {

        Information current = data.get(i);
        viewHolder.title.setText(current.title);
        viewHolder.icon.setImageResource(current.iconId);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void delete(int position){
        //para eliminar un dato de mi arreglo
        data.remove(position);
        // esto me le permite indicarle a mi adapter que hasido eliminado un item
        notifyItemRemoved(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
            //Establesco un onClickListener al Icon
            icon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Toast.makeText(context, "Item position" + getPosition(), Toast.LENGTH_SHORT).show();
            delete(getPosition());
        }
    }
}
