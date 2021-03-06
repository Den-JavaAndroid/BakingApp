package ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.denx7.ui.R;

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


public class RecipeListFragment extends Fragment {

    private RestClient restClient;
    private ArrayList<Recipe> recipes = new ArrayList<>();

    ReciepsAdapter.ItemClickListener itemClickListener;

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
                ReciepsAdapter reciepsAdapter = new ReciepsAdapter(getContext(), recipes, (ReciepsAdapter.ItemClickListener) getActivity());
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

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            itemClickListener = (ReciepsAdapter.ItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ItemClickListener");
        }
    }







}
