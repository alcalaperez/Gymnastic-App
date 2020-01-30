package com.uo226321.joel.gymnastics.view.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.uo226321.joel.gymnastics.R;

import java.util.List;

/**
 * Created by Juned on 3/27/2017.
 */

public class RecyclerViewAdapterVertical extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mDataList;
    public int position;

    public void setData(int position, List<String> data) {
        if (mDataList != data) {
            this.position = position;
            mDataList = data;
            notifyDataSetChanged();
        }
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        public ItemViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.textview1);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.vertical_item, parent, false);
        ItemViewHolder holder = new ItemViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder rawHolder, int position) {
        ItemViewHolder holder = (ItemViewHolder) rawHolder;
        holder.text.setText(mDataList.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

}

