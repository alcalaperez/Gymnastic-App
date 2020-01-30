package com.uo226321.joel.gymnastics.client.endpoints;

import android.os.AsyncTask;

import com.uo226321.joel.gymnastics.viewmodel.MainViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class FindMusculosEndpoint extends AsyncTask<Call, Void, String> {

    private static final String URI_BOOK = "https://gimnsatics.herokuapp.com/rest/UserServiceRs/";
    private List<String> musculos;
    private MainViewModel mvm;

    public FindMusculosEndpoint(MainViewModel mvm) {
        this.mvm = mvm;

    }


    @Override
    protected String doInBackground(Call[] calls) {
        try {
            Call<List<String>> gu = calls[0];
            Response s = gu.execute();
            musculos = (List<String>) s.body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        mvm.callActivityMusculos((ArrayList<String>) musculos);

    }


}