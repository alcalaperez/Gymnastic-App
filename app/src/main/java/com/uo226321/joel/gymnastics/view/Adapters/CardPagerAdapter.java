package com.uo226321.joel.gymnastics.view.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.uo226321.joel.gymnastics.R;
import com.uo226321.joel.gymnastics.model.Usuario;
import com.uo226321.joel.gymnastics.view.CardItem;
import com.uo226321.joel.gymnastics.view.RegisterActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;
    private Usuario usuario;

    public CardPagerAdapter(Usuario usuario) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        this.usuario = usuario;
    }

    public void addCardItem(CardItem item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.horizontal_item, container, false);
        container.addView(view);
        bind(container.getContext(), mData.get(position), position, view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(final Context cardPagerContext, CardItem item, final int position, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.textViewTitleCard);
        ImageView image = (ImageView) view.findViewById(R.id.imageCard);

        titleTextView.setText(item.getTitle());
        image.setImageResource(item.getImage());

        RecyclerView verticalRecycler = (RecyclerView) view.findViewById(R.id.vertical_list);
        verticalRecycler.setLayoutManager(new LinearLayoutManager(cardPagerContext, LinearLayoutManager.VERTICAL, false));

        RecyclerViewAdapterVertical verticalAdapter = new RecyclerViewAdapterVertical();

        if(position== 0) {
            verticalAdapter.setData(position, Arrays.asList("Mantenimiento", "Aumento de masa muscular")); // List of Strings
        } else if(position== 1) {
            verticalAdapter.setData(position, Arrays.asList("Aumento de masa muscular", "Mantenimiento")); // List of Strings
        } else {
            verticalAdapter.setData(position, Arrays.asList("Perdida de grasa")); // List of Strings
        }

        verticalRecycler.setAdapter(verticalAdapter);

        // Adding on item click listener to RecyclerView.
        verticalRecycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(cardPagerContext, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }
            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {
                View childView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (childView != null && gestureDetector.onTouchEvent(motionEvent)) {
                    int recyclerViewItemPosition = Recyclerview.getChildAdapterPosition(childView);
                    Intent intent = new Intent(cardPagerContext, RegisterActivity.class);
                    intent.putExtra("somatipo", position);
                    intent.putExtra("objetivo", recyclerViewItemPosition);

                    SharedPreferences prefs =
                            cardPagerContext.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();

                    if(position == 0) {
                        editor.putString("Complexión", "Ectomorfo");
                        usuario.setSomatotipo("Ectomorfo");

                        if(recyclerViewItemPosition == 0) {
                            editor.putString("Objetivo", "Mantenimiento");
                            usuario.setObjetivo("Mantenimiento");
                        } else {
                            editor.putString("Objetivo", "Ganancia muscular");
                            usuario.setObjetivo("Ganancia muscular");

                        }
                    } else if (position == 1) {
                        editor.putString("Complexión", "Mesomorfo");
                        usuario.setSomatotipo("Mesomorfo");

                        if(recyclerViewItemPosition == 0) {
                            editor.putString("Objetivo", "Ganancia muscular");
                            usuario.setObjetivo("Ganancia muscular");

                        } else {
                            editor.putString("Objetivo", "Mantenimiento");
                            usuario.setObjetivo("Mantenimiento");

                        }

                    } else if (position == 2) {
                        editor.putString("Complexión", "Endomorfo");
                        editor.putString("Objetivo", "Pérdida de grasa");
                        usuario.setSomatotipo("Endomorfo");
                        usuario.setObjetivo("Pérdida de peso");

                    }

                    editor.commit();
                    intent.putExtra("usuario", usuario);

                    cardPagerContext.startActivity(intent);
                    ((Activity)(cardPagerContext)).finish();
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }

}
