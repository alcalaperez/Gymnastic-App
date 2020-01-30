package com.uo226321.joel.gymnastics.view.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.uo226321.joel.gymnastics.R;
import com.uo226321.joel.gymnastics.model.Serie;
import com.uo226321.joel.gymnastics.view.ListadoRutinaFragment;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link ListadoRutinaFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyListadoRutinaRecyclerViewAdapter extends RecyclerView.Adapter<MyListadoRutinaRecyclerViewAdapter.ViewHolder> {

    private List<Serie> mValues;
    private final ListadoRutinaFragment.OnListFragmentInteractionListener mListener;
    private SharedPreferences prefs;

    public MyListadoRutinaRecyclerViewAdapter(List<Serie> items, ListadoRutinaFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.serie_card_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Picasso.with(holder.mView.getContext()).load(mValues.get(position).getEjercicio().getFoto1()).resize(600, 400)
                .centerInside().into(holder.imagenEjercicio);
        holder.mIdView.setText(mValues.get(position).getEjercicioId());
        holder.series.setText(mValues.get(position).getSeries() + " series");
        holder.repeticiones.setText(mValues.get(position).getRepeticiones() + " repeticiones");
        holder.descanso.setText(mValues.get(position).getDescanos() + " segundos descanso");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView series;
        public final TextView repeticiones;
        public final TextView descanso;

        public final ImageView imagenEjercicio;

        public Serie mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            imagenEjercicio = (ImageView) view.findViewById(R.id.imageViewEjercicio);
            series = (TextView) view.findViewById(R.id.series);
            repeticiones = (TextView) view.findViewById(R.id.repeticiones);
            descanso = (TextView) view.findViewById(R.id.descanso);

            prefs = mView.getContext().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }



}
