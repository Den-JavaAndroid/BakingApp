package com.example.denx7.bakingapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import adapters.ReciepsAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import custom.GridAutofitLayoutManager;
import recipes.Recipe;
import retrofit.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReciepsFragment extends Fragment {

    private RestClient restClient;
    private ArrayList<Recipe> recipes = new ArrayList<>();

    @BindView(R.id.recipes)
    RecyclerView recyclerView;

    @BindView(R.id.progress_load_recipes)
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipes_list, container, false);

        ButterKnife.bind(this, view);


        recyclerView.setLayoutManager(new GridAutofitLayoutManager(getActivity(), 800));
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        restClient = new RestClient();
        restClient.getReceips().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.i("INFO", "Responce body: " + response.body().toString());
                recipes = (ArrayList<Recipe>) response.body();
                ReciepsAdapter reciepsAdapter = new ReciepsAdapter(getContext(), recipes);
//        reciepsAdapter.setClickListener(ReciepsFragment.this);
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(reciepsAdapter);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e("ERROR", "Error fetching response: \r\n" + t.getMessage());
            }
        });



        return view;
    }
}
