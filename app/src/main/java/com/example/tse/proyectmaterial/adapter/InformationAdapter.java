package com.example.tse.proyectmaterial.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tse.proyectmaterial.R;
import com.example.tse.proyectmaterial.SubActivity;
import com.example.tse.proyectmaterial.model.Information;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Marhinita on 29/8/2015.
 */
public class InformationAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    private Context context;
    private ClickListener clickListener;
    private LayoutInflater inflater;
    // Los datos para llenar mi recyclerView
    List<Information> data = Collections.emptyList();

    private static final int TYPE_HEAD = 0;
    private static final int TYPE_ITEM = 1;

    public InformationAdapter(Context context, List<Information> data) {
        this.context = context;
       inflater =  LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        if (viewType == TYPE_HEAD){
            View view = inflater.inflate(R.layout.drawer_header, viewGroup, false);
            HeaderHolder holder = new HeaderHolder(view);
            return holder;
        }else{
            View view = inflater.inflate(R.layout.information_row, viewGroup, false);
            ItemHolder holder = new ItemHolder(view);
            return holder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof HeaderHolder){

        }else{
            ItemHolder itemHolder = (ItemHolder)viewHolder;
            Information current = data.get(position - 1);
            itemHolder.title.setText(current.title);
            itemHolder.icon.setImageResource(current.iconId);

        }


    }


    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return TYPE_HEAD;
        }else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    public void delete(int position){
        //para eliminar un dato de mi arreglo
        data.remove(position);
        // esto me le permite indicarle a mi adapter que hasido eliminado un item
        notifyItemRemoved(position);
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView title;
        ImageView icon;

        public ItemHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
            //Establesco un onClickListener al Icon
            // icon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Toast.makeText(context, "Item position" + getPosition(), Toast.LENGTH_SHORT).show();
            // delete(getPosition());


            if(clickListener != null){
                clickListener.itemClicked(v, getPosition());
            }
        }
    }


    public interface ClickListener{

        public void itemClicked(View view, int position);

    }



    class HeaderHolder extends RecyclerView.ViewHolder{




        public HeaderHolder(View itemView) {
            super(itemView);


        }

    }
}
