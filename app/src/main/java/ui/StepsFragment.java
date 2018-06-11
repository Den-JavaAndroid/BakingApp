package ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.denx7.ui.R;

import java.util.List;

import adapters.StepsAdapter;
import butterknife.ButterKnife;
import keys.IntentKeys;
import recipes.Ingredient;
import recipes.Recipe;


public class StepsFragment extends Fragment {

    private Recipe recipe;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_steps, container, false);
        ButterKnife.bind(this, view);

        TextView ingredients = view.findViewById(R.id.ingredients);
        RecyclerView recipeStepsRecyclerView = view.findViewById(R.id.recipe_steps);
        StepsAdapter stepsAdapter = new StepsAdapter(getContext(), recipe.getSteps(),(StepsAdapter.ItemClickListener) getActivity());
        recipeStepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recipeStepsRecyclerView.setAdapter(stepsAdapter);
        ingredients.append("Ingredients list:\r\n");
        for(Ingredient ingredient: recipe.getIngredients()){
            ingredients.append("\u2022 " + ingredient.getIngredient() + "\n");
        }
        return view;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
