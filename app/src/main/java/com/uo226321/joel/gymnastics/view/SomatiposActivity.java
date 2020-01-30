package com.uo226321.joel.gymnastics.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.uo226321.joel.gymnastics.R;
import com.uo226321.joel.gymnastics.model.Usuario;
import com.uo226321.joel.gymnastics.view.Adapters.CardPagerAdapter;

import java.util.List;

public class SomatiposActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private CardPagerAdapter mCardAdapter;
    private List<String> somatipos;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_somatipos);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();
        usuario = (Usuario) bundle.get("usuario");

        mCardAdapter = new CardPagerAdapter(usuario);
        mCardAdapter.addCardItem(new CardItem(R.string.ectomorfo, R.drawable.ectomorfo));
        mCardAdapter.addCardItem(new CardItem(R.string.mesomorfo, R.drawable.mesomorfo));
        mCardAdapter.addCardItem(new CardItem(R.string.endomorfo, R.drawable.endomorfo));

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setCurrentItem(1);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, FormActivity.class);
        intent.putExtra("usuario", usuario);
        startActivity(intent);
        finish();
    }


    @Override
    public void onResume() {
        super.onResume();

    }
}
